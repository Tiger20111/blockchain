package block;

import block.mine.Utils;
import encoder.Sha256;
import transaction.Transaction;

import java.util.ArrayList;
import java.util.Date;

public class Block {

    //Block Constructor.
    public Block(String data, Integer previousId, ArrayList<Transaction> transactions) {
        this.data = data;
        this.previousId = previousId;
        this.timeStamp = new Date().getTime();
        this.difficulty = 100;
        this.transactions = transactions;
        this.hash = calculateHash();
        mineBlock();
    }


    public String calculateHash() {
        return Sha256.getHash(
                previousId +
                        Long.toString(timeStamp) +
                        data +
                        id +
                        nonce +
                        Utils.getStringTransactions(transactions)
        );
    }

    public void mineBlock() {
        System.out.println("Start mine block");
        String target = Utils.getDificultyString(difficulty); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public Integer getId() {
        return id;
    }
    public Integer getPreviousId() {
        return previousId;
    }

    public String getData() {
        return data;
    }

    public String getHash() {
        return hash;
    }

    private Integer id;
    private String hash;
    private Integer previousId;
    private String data;
    private long timeStamp;
    private int nonce = 0;
    private int difficulty;
    ArrayList<Transaction> transactions;
}