package transaction;

import encoder.Sha256;

public class Transaction {
    public Transaction(Integer from, Integer to, String digitalSignature, float value) {
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.digitalSignature = digitalSignature;
    }

    private String calculateHash() {
        return Sha256.getHash(
                Integer.toString(sender) +
                        Integer.toString(recipient) +
                        Float.toString(value) +
                        digitalSignature //Тут надо подправить логику
        );
    }

    private String transactionId;
    private String digitalSignature;
    private Integer sender;
    private Integer recipient;
    private float value;
}
