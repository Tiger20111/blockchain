package validation;

import account.Wallet;
import transaction.Transaction;

import java.util.ArrayList;
import java.util.HashMap;

public class SmartContract {
    public static boolean validTransaction(HashMap<String, Wallet> wallets, Transaction transaction) {
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

    public static boolean executeTransaction(HashMap<String, Wallet> wallets, ArrayList<Transaction> validTransaction) {
        for (Transaction transaction : validTransaction) {
            Wallet from = wallets.get(transaction.getSender());
            Wallet to = wallets.get(transaction.getRecipient());
            from.writeOff(transaction.getValue());
            to.replenishment(transaction.getValue());
        }
        return true;
    }
}
