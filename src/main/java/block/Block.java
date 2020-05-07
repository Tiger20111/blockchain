package block;

import encoder.Sha256;

import java.util.Date;

public class Block {

    private String hash;
    private String previousHash;
    private String data;
    private long timeStamp;

    //Block Constructor.
    public Block(String data,String previousHash ) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String getData() {
        return data;
    }

    public String getHash() {
        return hash;
    }

    private String calculateHash() {
        return Sha256.getHash(
                previousHash +
                        Long.toString(timeStamp) +
                        data
        );
    }


}