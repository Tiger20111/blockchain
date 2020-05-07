package transaction;

import encoder.Sha256;

public class Transaction {
    public Transaction(Integer from, Integer to, String digitalSignature, float value) {
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.digitalSignature = digitalSignature;
    }

    private String calulateHash() {
        return Sha256.getHash(
                Integer.toString(sender) +
                        Integer.toString(reciepient) +
                        Float.toString(value) +
                        digitalSignature //Тут надо подправить логику
        );
    }

    private String transactionId;
    private String digitalSignature;
    private Integer sender;
    private Integer reciepient;
    private float value;
}
