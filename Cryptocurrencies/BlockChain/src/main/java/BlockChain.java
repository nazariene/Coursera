// Block Chain should maintain only limited block nodes to satisfy the functions
// You should not have all the blocks added to the block chain in memory 
// as it would cause a memory overflow.


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockChain {
    public static final int CUT_OFF_AGE = 10;

    protected BlockNode maxHeightBlockNode;
    protected TransactionPool txPool;

    protected Map<ByteArrayWrapper, BlockNode> nodeMap;

    private class BlockNode {

        Block block;
        BlockNode parent;
        List<BlockNode> children = new ArrayList<>();
        int height;
        UTXOPool utxoPool;

        BlockNode(Block block, BlockNode parent, UTXOPool utxoPool) {
            this.block = block;
            this.parent = parent;
            this.utxoPool = utxoPool;
            if (parent != null) {
                height = parent.height + 1;
                parent.children.add(this);
            }
        }

        public UTXOPool getUTXOPool() {
            return new UTXOPool(utxoPool);
        }
    }

    /**
     * create an empty block chain with just a genesis block. Assume {@code genesisBlock} is a valid
     * block
     */
    public BlockChain(Block genesisBlock) {
        txPool = new TransactionPool();
        nodeMap = new HashMap<>();

        UTXOPool utxoPool = new UTXOPool();
        addCoinbaseToPool(genesisBlock, utxoPool);
        BlockNode genesisNode = new BlockNode(genesisBlock, null, utxoPool);
        maxHeightBlockNode = genesisNode;
        nodeMap.put(new ByteArrayWrapper(genesisBlock.getHash()), genesisNode);
    }

    private void addCoinbaseToPool(Block genesisBlock, UTXOPool utxoPool) {
        for (int i = 0; i < genesisBlock.getCoinbase().numOutputs(); i++) {
            utxoPool.addUTXO(new UTXO(genesisBlock.getCoinbase().getHash(), i), genesisBlock.getCoinbase().getOutput(i));
        }
    }


    /**
     * Get the maximum height block
     */
    public Block getMaxHeightBlock() {
        return maxHeightBlockNode.block;
    }

    /**
     * Get the UTXOPool for mining a new block on top of max height block
     */
    public UTXOPool getMaxHeightUTXOPool() {
        return maxHeightBlockNode.utxoPool;
    }

    /**
     * Get the transaction pool to mine a new block
     */
    public TransactionPool getTransactionPool() {
        return txPool;
    }

    /**
     * Add {@code block} to the block chain if it is valid. For validity, all transactions should be
     * valid and block should be at {@code height > (maxHeight - CUT_OFF_AGE)}.
     * <p>
     * <p>
     * For example, you can try creating a new block over the genesis block (block height 2) if the
     * block chain height is {@code <=
     * CUT_OFF_AGE + 1}. As soon as {@code height > CUT_OFF_AGE + 1}, you cannot create a new block
     * at height 2.
     *
     * @return true if block is successfully added
     */
    public boolean addBlock(Block block) {
        BlockNode parentNode = getParentBlockNodeFromBlock(block);
        if (parentNode == null) {
            return false;
        }

        int height = parentNode.height + 1;
        if (height <= maxHeightBlockNode.height - CUT_OFF_AGE) {
            return false;
        }

        TxHandler txHandler = new TxHandler(parentNode.getUTXOPool());
        Transaction[] blockTx = block.getTransactions().toArray(new Transaction[0]);
        Transaction[] validatedTx = txHandler.handleTxs(blockTx);
        if (blockTx.length != validatedTx.length) {
            return false;
        }

        UTXOPool utxoPool = txHandler.getUTXOPool();
        addCoinbaseToPool(block, utxoPool);

        BlockNode newNode = new BlockNode(block, parentNode, utxoPool);
        nodeMap.put(new ByteArrayWrapper(block.getHash()), newNode);

        if (height > maxHeightBlockNode.height) {
            maxHeightBlockNode = newNode;
        }

        return true;
    }

    /**
     * Add a transaction to the transaction pool
     */
    public void addTransaction(Transaction tx) {
        txPool.addTransaction(tx);
    }


    private BlockNode getParentBlockNodeFromBlock(Block block) {
        byte[] prevTxHash = block.getPrevBlockHash();
        if (prevTxHash == null) {
            return null;
        }

        return nodeMap.get(new ByteArrayWrapper(prevTxHash));
    }
}