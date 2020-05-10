package application.transaction;

import application.encoder.Sha256;

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
    }

    public Transaction(Integer transactionId, String from, String to, String digitalSignature, Double value) {
        this.transactionId = transactionId;
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.digitalSignature = digitalSignature;
    }

    private String calculateHash() {
        return Sha256.getHash(
                sender +
                        recipient +
                        Double.toString(value) +
                        digitalSignature //Тут надо подправить логику
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

    private Integer transactionId = 0;
    private String digitalSignature;
    private String sender;
    private String recipient;
    private Double value;

    @Override
    public String toString() {
        return "transactionId=" + transactionId.toString() + "-" +
                "digitalSignature=" + digitalSignature + "-" +
                "sender=" + sender + "-" +
                "recipient=" + recipient + "-" +
                "value=" + value;
    }
}
