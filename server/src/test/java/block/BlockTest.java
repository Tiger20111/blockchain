package block;

import org.junit.Test;

public class BlockTest {
    
@Test public void testCreateBlock() {
    String data = "07.05.2020";
    Block block = new Block(data, null);
    System.out.println("Hash block: " + block.getHash());
}
}