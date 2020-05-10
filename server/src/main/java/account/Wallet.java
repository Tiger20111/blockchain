package account;


import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Wallet {

    public Wallet(String name, Double balance, String publicKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        this.balance = balance;
        this.name = name;
        this.publicKey = decodeKeyFromString(publicKey);
    }

    public String getName() {
        return name;
    }

    public Double getBalance() {
        return balance;
    }

    public Integer getId() {
        return id;
    }

    PublicKey decodeKeyFromString(String publicKeyStr) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        publicKeyStr = publicKeyStr.replace('$', '/');
        //publicKeyStr = publicKeyStr.replace('#', '+');
        KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC");
        Base64.Decoder decoder = Base64.getDecoder();

        return keyFactory.generatePublic(new X509EncodedKeySpec(decoder.decode(publicKeyStr)));
    }

    private String name;
    private PublicKey publicKey;
    private Integer id;
    private Double balance;
}
