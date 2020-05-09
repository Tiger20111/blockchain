package application.acc;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Account {
    public Account(String name) {
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

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getName() {
        return name;
    }

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String name;
}
