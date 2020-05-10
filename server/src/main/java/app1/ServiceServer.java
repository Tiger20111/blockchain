package app1;

import account.Wallet;
import banks.Bank;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;
import transaction.Transaction;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

@Component
public class ServiceServer {
    ServiceServer(){
        wallets = new HashMap<>();
        banks = new HashMap<>();
        pendingTransaction = new HashMap<>();
        finishedTransaction = new HashMap<>();
        rejectedTransaction = new HashMap<>();
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
        Transaction transaction = decodeTransaction( transactionStr );
        System.out.println("Transaction: " + transactionStr + " was pushed");
        pendingTransaction.put(transaction.getSender(), transaction);
        return "transaction pushed";
    }

    private Transaction decodeTransaction(String transactionStr) throws IOException, ClassNotFoundException {
        System.out.println("Start decode transaction: " + transactionStr);
        transactionStr = transactionStr.replace('$', '/');
        String[] pairs = transactionStr.split("-");
        int transactionId = 0;
        String digitalSignature = "";
        String sender = "";
        String recipient = "";
        double value = 0.0;
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length != 2) {
                continue;
            }
            String key = keyValue[0];
            String valueS = keyValue[1];

            switch (key) {
                case "transactionId":
                    transactionId = Integer.parseInt(valueS);
                    break;
                case "digitalSignature":
                    digitalSignature = valueS;
                    break;
                case "sender":
                    sender = valueS;
                    break;
                case "recipient":
                    recipient = valueS;
                    break;
                case "value":
                    value = Double.parseDouble(valueS);
                    break;
                default:
                    throw new RuntimeException("Error key: " + key);
            }
        }
        return new Transaction(transactionId, sender, recipient, digitalSignature, value);
    }

    String getBankNames() {
        StringBuilder names = new StringBuilder();
        int num = 0;
        for (String name : banks.keySet()) {
            if (num > 0) {
                names.append(", ");
            }
            names.append(name);
            num++;
        }
        return names.toString();
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
