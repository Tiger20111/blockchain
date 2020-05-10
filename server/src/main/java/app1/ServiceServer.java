package app1;

import account.Wallet;
import banks.Bank;
import block.Block;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;
import transaction.Transaction;
import validation.SmartContract;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ServiceServer {
    ServiceServer(){
        wallets = new HashMap<>();
        banks = new HashMap<>();
        pendingTransactionsUser = new HashMap<>();
        finishedTransactionsUser = new HashMap<>();
        rejectedTransactionsUser = new HashMap<>();
        pendingTransactionsBank = new HashMap<>();
        finishedTransactionsBank = new HashMap<>();
        rejectedTransactionsBank = new HashMap<>();
        blocks = new HashMap<>();
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    String createNewWallet(String name, String publicKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        Wallet wallet = new Wallet(name, 0.0, publicKey);
        wallets.put(name, wallet);
        return "Created";
    }

    String miniBlock() {
        ArrayList<Transaction> validTransactionUser = getValidUserTransaction();
        ArrayList<Transaction> validTransactionBank = getValidBankTransaction();
        ArrayList<Transaction> validTransaction;
        if (validTransactionUser != null) {
            validTransaction = new ArrayList<>(validTransactionUser);
        } else {
            validTransaction = new ArrayList<>();
        }
        if (validTransactionBank != null) {
            validTransaction.addAll(validTransactionBank);
        }


        if (validTransaction.isEmpty()) {
            return "No valid transaction to mine";
        }

        if (validTransactionUser != null && !validTransactionUser.isEmpty()) {
            SmartContract.executeTransactionUser(wallets, validTransactionUser);
        }
        if (validTransactionBank != null && !validTransactionBank.isEmpty()) {
            SmartContract.executeTransactionBank(banks, wallets, validTransactionBank);
        }

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        int previousId = 0;
        Block block = new Block(timeStamp, previousId, validTransaction);
        blocks.put(block.getId(), block);
        return "Compete";
    }



    private ArrayList<Transaction> getValidUserTransaction() {
        System.out.println("Start validation user transactions");
        ArrayList<Transaction> validTransaction = new ArrayList<>();
        ArrayList<Integer> invalidIdTransactions = new ArrayList<>();
        if (pendingTransactionsUser.isEmpty()) {
            return null;
        }
        Set<Map.Entry<Integer,Transaction>> setTransaction = pendingTransactionsUser.entrySet();
        for (Map.Entry<Integer,Transaction> entry : setTransaction) {
            boolean valid = SmartContract.validTransactionUser(wallets, entry.getValue());
            if (valid) {
                validTransaction.add(entry.getValue());
            } else {
                invalidIdTransactions.add(entry.getKey());
                rejectedTransactionsUser.put(entry.getKey(), entry.getValue());
            }
        }
        for (Integer id : invalidIdTransactions) {
            pendingTransactionsUser.remove(id);
        }
        return validTransaction;
    }

    private ArrayList<Transaction> getValidBankTransaction() {
        System.out.println("Start validation bank transactions");
        ArrayList<Transaction> validTransaction = new ArrayList<>();
        ArrayList<Integer> invalidIdTransactions = new ArrayList<>();
        if (pendingTransactionsBank.isEmpty()) {
            return null;
        }
        Set<Map.Entry<Integer,Transaction>> setTransaction = pendingTransactionsBank.entrySet();
        for (Map.Entry<Integer,Transaction> entry : setTransaction) {
            boolean valid = SmartContract.validTransactionBank(banks, wallets, entry.getValue());
            if (valid) {
                validTransaction.add(entry.getValue());
            } else {
                invalidIdTransactions.add(entry.getKey());
                rejectedTransactionsBank.put(entry.getKey(), entry.getValue());
            }
        }
        for (Integer id : invalidIdTransactions) {
            pendingTransactionsBank.remove(id);
        }
        return validTransaction;
    }

    String createNewBank(String name) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        Bank bank = new Bank(name, 1000000.0);
        banks.put(name, bank);
        return "Created";
    }

    String pushTransaction(String transactionStr) throws IOException, ClassNotFoundException {
        Transaction transaction = decodeTransaction( transactionStr );
        System.out.println("Transaction: " + transactionStr + " was pushed");
        pendingTransactionsUser.put(transaction.getTransactionId(), transaction);
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
        String hashN = "";
        String hash = "";
        Integer nonce = 0;
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
                case "hashN":
                    hashN = valueS;
                    break;
                case "nonce":
                    nonce = Integer.parseInt(valueS);
                    break;
                case "hash":
                    hash = valueS;
                    break;
                default:
                    throw new RuntimeException("Error key: " + key);
            }
        }
        return new Transaction(transactionId, sender, recipient, digitalSignature, value, hashN, nonce, hash);
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
        Transaction transaction = new Transaction(bank, to, "", amount);
        pendingTransactionsBank.put(transaction.getTransactionId(), transaction);
        return "Complete";
    }

    private HashMap<String, Wallet> wallets;
    private HashMap<String, Bank> banks;
    private HashMap<Integer, Transaction> finishedTransactionsUser;
    private HashMap<Integer, Transaction> pendingTransactionsUser;
    private HashMap<Integer, Transaction> rejectedTransactionsUser;
    private HashMap<Integer, Transaction> pendingTransactionsBank;
    private HashMap<Integer, Transaction> finishedTransactionsBank;
    private HashMap<Integer, Transaction> rejectedTransactionsBank;
    private HashMap<Integer, Block> blocks;
}
