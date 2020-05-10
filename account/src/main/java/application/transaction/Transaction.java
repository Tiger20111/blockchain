package application.transaction;

import application.encoder.Sha256;
import application.utils.Utils;

import java.io.Serializable;
import java.util.Random;

public class Transaction implements Serializable {
    public Transaction(String from, String to, String digitalSignature, Double value) {
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.digitalSignature = digitalSignature;
        Random random = new Random();
        this.transactionId = random.nextInt(10000);
        this.hash = calculateHash();
        mineTransaction();
    }

    public Transaction(Integer transactionId, String from, String to, String digitalSignature, Double value, String hash, Integer nonce) {
        this.transactionId = transactionId;
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.digitalSignature = digitalSignature;
        this.hash = hash;
        this.nonce = nonce;
    }

    private String calculateHash() {
        return Sha256.getHash(
                transactionId +
                        sender +
                        recipient +
                        value +
                        digitalSignature +
                        nonce
        );
    }

    public Double getValue() {
        return value;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void mineTransaction() {
        System.out.println("Start mine Transaction");
        String target = Utils.getDificultyString(difficulty); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Transaction Mined!!! : " + hash + " nonce: " + nonce);
    }

    private Integer transactionId = 0;
    private String digitalSignature;
    private String sender;
    private String recipient;
    private Double value;
    private String hash;
    private Integer nonce = 0;
    private Integer difficulty = 5;

    @Override
    public String toString() {
        return "transactionId=" + transactionId.toString() + "-" +
                "digitalSignature=" + digitalSignature + "-" +
                "sender=" + sender + "-" +
                "recipient=" + recipient + "-" +
                "value=" + value +
                "hash=" + hash +
                "nonce=" + nonce;
    }
}
