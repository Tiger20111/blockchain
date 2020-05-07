package block;

import encoder.Sha256;

import java.util.Date;

public class Block {

    private Integer id;
    private String hash;
    private Integer previousId;
    private String data;
    private long timeStamp;

    //Block Constructor.
    public Block(String data, Integer previousId ) {
        this.data = data;
        this.previousId = previousId;
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
                previousId +
                        Long.toString(timeStamp) +
                        data
        );
    }

    public Integer getId() {
        return id;
    }


}