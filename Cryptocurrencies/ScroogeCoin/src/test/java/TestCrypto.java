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

/**
 * Examples of usage of Crypto class
 */
public class TestCrypto {

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
    public void testCryptoWithValidSignature() throws InvalidKeyException, SignatureException {
        String message = "I'm a message!";
        signature.initSign(scroogePrivateKey);
        signature.update(message.getBytes());
        byte[] signedBytes = signature.sign();

        boolean isValid = Crypto.verifySignature(scroogePublicKey, message.getBytes(), signedBytes);
        Assert.assertTrue("Should be a valid signature", isValid);
    }

    @Test
    public void testCryptoWithInvalidSignature() throws InvalidKeyException, SignatureException {
        String message = "I'm a message!";
        signature.initSign(scroogePrivateKey);
        signature.update(message.getBytes());
        byte[] signedBytes = signature.sign();

        boolean isValid = Crypto.verifySignature(scroogePublicKey, "I'm a different message!".getBytes(), signedBytes);
        Assert.assertFalse("Should be an invalid signature", isValid);
    }
}
