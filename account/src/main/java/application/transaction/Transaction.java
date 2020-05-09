package application.transaction;

import application.encoder.Sha256;

public class Transaction {
    public Transaction(String from, String to, String digitalSignature, Double value) {
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

    public String getTransactionId() {
        return transactionId;
    }

    private String transactionId;
    private String digitalSignature;
    private String sender;
    private String recipient;
    private Double value;
}
