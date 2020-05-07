package block;

import encoder.Sha256;

import java.util.Date;

public class Block {

    //Block Constructor.
    public Block(String data, Integer previousId ) {
        this.data = data;
        this.previousId = previousId;
        this.timeStamp = new Date().getTime();
        this.difficulty = 5;
        this.hash = calculateHash();
        mineBlock();
    }


    public String calculateHash() {
        return Sha256.getHash(
                previousId +
                        Long.toString(timeStamp) +
                        data
        );
    }

    public void mineBlock() {
        hashNonce = hash;
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!hashNonce.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hashNonce = calculateHash();
        }
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

    public String getHashNonce() {
        return hashNonce;
    }

    public String getHash() {
        return hash;
    }

    private Integer id;
    private String hash;
    private String hashNonce;
    private Integer previousId;
    private String data;
    private long timeStamp;
    private int nonce;
    private int difficulty;
}