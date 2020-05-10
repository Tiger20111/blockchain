package validation;

import account.Wallet;
import banks.Bank;
import transaction.Transaction;

import java.util.ArrayList;
import java.util.HashMap;

public class SmartContract {
    public static boolean validTransactionUser(HashMap<String, Wallet> wallets, Transaction transaction) {

        String hashTarget = new String(new char[transaction.getDifficulty()]).replace('\0', '0');
        if(!transaction.getHash().substring( 0, transaction.getDifficulty()).equals(hashTarget)) {
            System.out.println("This block hasn't been mined");
            return false;
        }

        if (!wallets.containsKey(transaction.getRecipient()) && !wallets.containsKey(transaction.getSender())) {
            System.out.println("Transaction with id: " + transaction.getTransactionId() + " is invalid");
            return false;
        }
        if (wallets.get(transaction.getSender()).getBalance() < transaction.getValue()) {
            System.out.println("Transaction with id: " + transaction.getTransactionId() + " is invalid");
            return false;
        }
        System.out.println("Transaction with id: " + transaction.getTransactionId() + " is valid");
        return true;
    }

    public static boolean executeTransactionUser(HashMap<String, Wallet> wallets, ArrayList<Transaction> validTransaction) {
        for (Transaction transaction : validTransaction) {
            Wallet from = wallets.get(transaction.getSender());
            Wallet to = wallets.get(transaction.getRecipient());
            from.writeOff(transaction.getValue());
            to.replenishment(transaction.getValue());
        }
        return true;
    }

    public static boolean validTransactionBank(HashMap<String, Bank> banks,
                                               HashMap<String, Wallet> wallets,
                                               Transaction transaction) {
        if (!wallets.containsKey(transaction.getRecipient()) && !banks.containsKey(transaction.getSender())) {
            System.out.println("Transaction with id: " + transaction.getTransactionId() + " is invalid");
            return false;
        }
        if (banks.get(transaction.getSender()).getBalance() < transaction.getValue()) {
            System.out.println("Transaction with id: " + transaction.getTransactionId() + " is invalid");
            return false;
        }
        return true;
    }

    public static boolean executeTransactionBank(HashMap<String, Bank> banks,
                                                 HashMap<String, Wallet> wallets,
                                                 ArrayList<Transaction> validTransaction) {
        for (Transaction transaction : validTransaction) {
            Bank from = banks.get(transaction.getSender());
            Wallet to = wallets.get(transaction.getRecipient());
            from.writeOff(transaction.getValue());
            to.replenishment(transaction.getValue());
        }
        return true;
    }

}
