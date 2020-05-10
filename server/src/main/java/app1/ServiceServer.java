package app1;

import account.Wallet;
import banks.Bank;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;
import transaction.Transaction;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.HashMap;

@Component
public class ServiceServer {
    ServiceServer(){
        wallets = new HashMap<>();
        banks = new HashMap<>();
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    String createNewWallet(String name, String publicKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        Wallet wallet = new Wallet(name, 0.0, publicKey);
        wallets.put(name, wallet);
        return "Created";
    }

    String createNewBank(String name) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        Bank bank = new Bank(name, 1000000.0);
        banks.put(name, bank);
        return "Created";
    }

    String pushTransaction(String transactionStr) throws IOException, ClassNotFoundException {
        Transaction transaction = decodeTransaction(transactionStr);
        pendingTransaction.put(transaction.getSender(), transaction);
        return "transaction pushed";
    }

    private Transaction decodeTransaction(String transactionStr) throws IOException, ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( transactionStr );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Transaction transaction  = (Transaction) ois.readObject();
        ois.close();
        return transaction;
    }

    Double getBalance(String name) {
        if (!wallets.containsKey(name)) {
            return -1.0;
        }
        return wallets.get(name).getBalance();
    }


    String replenishmentAccount(String bank, String to, Double amount) {
        return "Complete";
    }

    private HashMap<String, Wallet> wallets;
    private HashMap<String, Bank> banks;
    private HashMap<String, Transaction> finishedTransaction;
    private HashMap<String, Transaction> pendingTransaction;
    private HashMap<String, Transaction> rejectedTransaction;
}
