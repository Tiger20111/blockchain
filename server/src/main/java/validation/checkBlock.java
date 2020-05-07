package validation;

import block.Block;
import blockchain.BlockChain;

public class checkBlock {
    public static Boolean isBlockValid(Block currentBlock, BlockChain blockChain) {

        if (!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
            System.out.println("Current Hashes not equal");
            return false;
        }

        Block previousBlock = blockChain.getBlock(currentBlock.getPreviousId());
        if (previousBlock == null) {
            System.out.println("Block have not parent");
        }

        String hashTarget = new String(new char[currentBlock.getDifficulty()]).replace('\0', '0');
        if(!currentBlock.getHashNonce().substring( 0, currentBlock.getDifficulty()).equals(hashTarget)) {
            System.out.println("#This block hasn't been mined");
            return false;
        }

        return true;
    }
}
