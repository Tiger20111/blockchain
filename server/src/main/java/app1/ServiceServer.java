package app1;

import account.Wallet;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

@Component
public class ServiceServer {
    ServiceServer(){
        wallets = new HashMap<>();
    }

    String createNewWallet(String name, String publicKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        Wallet wallet = new Wallet(name, 0.0, publicKey);
        wallets.put(name, wallet);
        return "Created";
    }

    private HashMap<String, Wallet> wallets;
}
