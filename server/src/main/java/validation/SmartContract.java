package validation;

import account.Wallet;
import transaction.Transaction;

import java.util.ArrayList;
import java.util.HashMap;

public class SmartContract {
    public static boolean validTransaction(Transaction transaction) {
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
