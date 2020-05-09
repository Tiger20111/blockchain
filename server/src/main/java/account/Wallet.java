package account;


import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {

    public Wallet(Integer balance) {
        this.balance = balance;
    }



    private PrivateKey privateKey;
    private PublicKey publicKey;
    private Integer id;
    private Integer balance;
}
