import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class TestTransaction {

    private KeyPairGenerator keyGen;
    private PrivateKey scroogePrivateKey;
    private PublicKey scroogePublicKey;
    private Signature signature;

    @Before
    public void setUp() throws NoSuchAlgorithmException {
        keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.generateKeyPair();
        scroogePrivateKey = keyPair.getPrivate();
        scroogePublicKey = keyPair.getPublic();

        signature = Signature.getInstance("SHA256withRSA");
    }

    @Test
    public void testCreateSimpleTransaction() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        //Emit coins
        Transaction transaction = new Transaction();
        //Creating 10 coins
        transaction.addOutput(10, scroogePublicKey);

        Transaction anotherTransaction = new Transaction();
        anotherTransaction.addInput(transaction.getHash(), 0);

        byte[] message = anotherTransaction.getRawDataToSign(0);
        signature.initSign(scroogePrivateKey);
        signature.update(message);
        byte[] signedTransaction = signature.sign();
        anotherTransaction.getInput(0).addSignature(signedTransaction);

        boolean isValid = Crypto.verifySignature(scroogePublicKey, anotherTransaction.getRawDataToSign(0), anotherTransaction.getInput(0).signature);
        Assert.assertTrue(isValid);
    }

    @Test
    public void testModificationOfTransactionAfterSigning() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        //Emit coins
        Transaction transaction = new Transaction();
        //Creating 10 coins
        transaction.addOutput(10, scroogePublicKey);

        Transaction anotherTransaction = new Transaction();
        anotherTransaction.addInput(transaction.getHash(), 0);

        byte[] message = anotherTransaction.getRawDataToSign(0);
        signature.initSign(scroogePrivateKey);
        signature.update(message);
        byte[] signedTransaction = signature.sign();
        anotherTransaction.getInput(0).addSignature(signedTransaction);

        //And now we modify a transaction after its being signed
        anotherTransaction.addOutput(5, scroogePublicKey);

        boolean isValid = Crypto.verifySignature(scroogePublicKey, anotherTransaction.getRawDataToSign(0), anotherTransaction.getInput(0).signature);
        Assert.assertFalse(isValid);
    }

}
