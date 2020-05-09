package banks;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;

public class Bank {
    public Bank(String name, Double balance) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        this.balance = balance;
        generateKeyPair();
        this.name = name;
    }

    private void generateKeyPair() {
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


    private String name;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Integer id;
    private Double balance;
}
