package block.mine;

import transaction.Transaction;

import java.util.ArrayList;

public class Utils {
    public static String getDificultyString(int difficulty) {
        return new String(new char[difficulty]).replace('\0', '0');
    }

    public static String getStringTransactions(ArrayList<Transaction> transactions) {
        StringBuilder transactionsStr = new StringBuilder();
        int numTransactions = 0;
        for (Transaction transaction : transactions) {
            if (numTransactions > 0) {
                transactionsStr.append("+");
            }
            transactionsStr.append(transaction);
            numTransactions += 1;
        }
        return String.valueOf(transactionsStr);
    }
}
