package transaction;

import block.mine.Utils;
import encoder.Sha256;

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
        this.hashN = calculateHash();
        this.hash = hashN;
        mineTransaction();
    }

    public Transaction(Integer transactionId, String from, String to, String digitalSignature, Double value, String hashN, Integer nonce, String hash) {
        this.transactionId = transactionId;
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.digitalSignature = digitalSignature;
        this.hashN = hashN;
        this.nonce = nonce;
        this.hash = hash;
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

    public String calculateHashNonce(Integer nonceNew) {
        return Sha256.getHash(
                transactionId +
                        sender +
                        recipient +
                        value +
                        digitalSignature +
                        nonceNew
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

    public Integer getNonce() {
        return nonce;
    }

    public String getHashN() {
        return hashN;
    }

    public String getHash() {
        return hash;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void mineTransaction() {
        System.out.println("Start mine Transaction");
        String target = Utils.getDificultyString(difficulty); //Create a string with difficulty * "0"
        while (!hashN.substring(0, difficulty).equals(target)) {
            nonce++;
            hashN = calculateHash();
        }
        System.out.println("Transaction Mined!!! : " + hashN + " nonce: " + nonce);
    }

    private Integer transactionId = 0;
    private String digitalSignature;
    private String sender;
    private String recipient;
    private Double value;
    private String hashN;
    private String hash;
    private Integer nonce = 0;
    private Integer difficulty = 5;

    @Override
    public String toString() {
        return "transactionId=" + transactionId.toString() + "-" +
                "digitalSignature=" + digitalSignature + "-" +
                "sender=" + sender + "-" +
                "recipient=" + recipient + "-" +
                "value=" + value + "-" +
                "hashN=" + hashN + "-" +
                "nonce=" + nonce + "-" +
                "hash=" + hash;
    }
}

