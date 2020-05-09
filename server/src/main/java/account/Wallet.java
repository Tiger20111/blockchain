package account;


import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallet {

    public Wallet(Integer balance) {
        generateKeyPair();
        this.balance = balance;
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();

        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private Integer id;
    private Integer balance;
}
