import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MaxFeeTxHandler {

    private UTXOPool utxoPool;

    /**
     * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
     * constructor.
     */
    public MaxFeeTxHandler(UTXOPool utxoPool) {
        this.utxoPool = new UTXOPool(utxoPool);
    }

    /**
     * @return true if:
     * (1) all outputs claimed by {@code tx} are in the current UTXO pool,
     * (2) the signatures on each input of {@code tx} are valid,
     * (3) no UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     *     values; and false otherwise.
     */
    public boolean isValidTx(Transaction tx) {
        UTXOPool uniqueUTXO = new UTXOPool();
        double inputSum = 0;
        double outputSum = 0;

        for (int i = 0; i < tx.numInputs(); i++) {
            Transaction.Input in = tx.getInput(i);
            //Check that output is in UTXO pool
            UTXO utxo = new UTXO(in.prevTxHash, in.outputIndex);
            Transaction.Output output = utxoPool.getTxOutput(utxo);
            if (output == null) {
                return false;
            }

            //Check that signature is valid
            byte[] message = tx.getRawDataToSign(i);
            if (!Crypto.verifySignature(output.address, message, in.signature)) {
                return false;
            }

            //Check that no UTXO is claimed multiple times by tx
            if (uniqueUTXO.contains(utxo)) {
                return false;
            }
            else {
                uniqueUTXO.addUTXO(utxo, output);
                inputSum += output.value;
            }
        }

        //Check that all output values are non-negative
        for (Transaction.Output output : tx.getOutputs()) {
            if (output.value < 0) {
                return false;
            }

            outputSum += output.value;
        }

        //Check that input values are >= output values
        return outputSum <= inputSum;
    }

    /*
     Returns transactions with maximum fee
     General idea - sort first, then analyze
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        Set<Transaction> sortedTransactions = new TreeSet<>((tx1, tx2) -> {
            double tx1Fee = getTxFee(tx1);
            double tx2Fee = getTxFee(tx2);
            return Double.valueOf(tx2Fee).compareTo(tx1Fee);
        });

        Collections.addAll(sortedTransactions, possibleTxs);
        List<Transaction> result = new ArrayList<>();

        for (Transaction tx : sortedTransactions) {
            if (isValidTx(tx)) {
                result.add(tx);

                //Remove previous utxo from pool as it is now a proper input instead of unspent
                for (Transaction.Input in : tx.getInputs()) {
                    UTXO utxo = new UTXO(in.prevTxHash, in.outputIndex);
                    utxoPool.removeUTXO(utxo);
                }

                //Add new utxo to pool
                for (int i = 0; i < tx.numOutputs(); i++) {
                    UTXO utxo = new UTXO(tx.getHash(), i);
                    utxoPool.addUTXO(utxo, tx.getOutput(i));
                }
            }
        }

        Transaction[] validTransactions = new Transaction[result.size()];
        return result.toArray(validTransactions);
    }

    private double getTxFee(Transaction tx) {
        double inputSum = 0;
        double outputSum = 0;

        for (Transaction.Input in : tx.getInputs()) {
            UTXO utxo = new UTXO(in.prevTxHash, in.outputIndex);
            if (!utxoPool.contains(utxo) || !isValidTx(tx)) {
                continue;
            }
            Transaction.Output output = utxoPool.getTxOutput(utxo);
            inputSum += output.value;
        }

        for (Transaction.Output out : tx.getOutputs()) {
            outputSum += out.value;
        }

        return inputSum - outputSum;
    }
}
