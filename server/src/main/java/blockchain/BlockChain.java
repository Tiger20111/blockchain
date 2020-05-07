package blockchain;

import block.Block;
import java.util.HashMap;

public class BlockChain {
    BlockChain() {
        blocks = new HashMap<>();
    }

    public void setBlock(Block block) {
        blocks.put(block.getId(), block);
    }

    private HashMap<Integer, Block> blocks;
}
