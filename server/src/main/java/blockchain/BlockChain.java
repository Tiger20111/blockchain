package blockchain;

import account.Wallet;
import block.Block;
import java.util.HashMap;

public class BlockChain {
    BlockChain() {
        blocks = new HashMap<>();
    }

    public void setBlock(Block block) {
        blocks.put(block.getId(), block);
    }

    public Block getBlock(Integer id) {
        return blocks.get(id);
    }

    private HashMap<Integer, Block> blocks; //можно в бд запихать, тот же h2
    private HashMap<Integer, Wallet> accounts; //можно в бд запихать, тот же h2
}
