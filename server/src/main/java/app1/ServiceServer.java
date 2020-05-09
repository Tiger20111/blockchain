package app1;

import account.Wallet;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

@Component
public class ServiceServer {
    ServiceServer(){
        wallets = new HashMap<>();
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    String createNewWallet(String name, String publicKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        Wallet wallet = new Wallet(name, 0.0, publicKey);
        wallets.put(name, wallet);
        return "Created";
    }

    Double getBalance(String name) {
        if (!wallets.containsKey(name)) {
            return -1.0;
        }
        return wallets.get(name).getBalance();
    }

    private HashMap<String, Wallet> wallets;
}
