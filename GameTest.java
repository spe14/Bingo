import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class GameTest {
    
    @Test
    public void testSetNumGetNum() {
        BingoSpace bs = new BingoSpace(0, 0, 5);
        bs.setNum(10);
        assertEquals(bs.getNum(), 10);
    }
    
    @Test
    public void testSetWinGetWin() {
        BingoSpace bs = new BingoSpace(0, 0, 5);
        bs.setWin(false);
        assertFalse(bs.isWin());
    }
    
    @Test
    public void testSetFoundGetFound() {
        BingoSpace bs = new BingoSpace(0, 0, 5);
        bs.setFound(false);
        assertFalse(bs.getFound());
    }

    @Test
    public void testSetSelectedGetSelected() {
        BingoSpace bs = new BingoSpace(0, 0, 5);
        bs.setSelected(true);
        assertTrue(bs.getSelected());
    }
    
    @Test
    public void testNewCardWithinRange() {
        BingoCard bc = new BingoCard();
        bc.newCard();
        boolean less = false;
        for (int row = 0; row < 5; row++) {
            if (bc.getSpace(row, 0).getNum() < 16 && bc.getSpace(row, 0).getNum() > 0) {
                less = true;
            } else {
                less = false;
            }
        }
        
        for (int row = 0; row < 5; row++) {
            if (bc.getSpace(row, 1).getNum() < 31 && bc.getSpace(row, 1).getNum() > 15) {
                less = true;
            } else {
                less = false;
            }
        }
        
        for (int row = 0; row < 5; row++) {
            if (bc.getSpace(row, 2).getNum() < 46 && bc.getSpace(row, 2).getNum() > 30) {
                less = true;
            } else {
                less = false;
            }
        }
        
        for (int row = 0; row < 5; row++) {
            if (bc.getSpace(row, 3).getNum() < 61 && bc.getSpace(row, 3).getNum() > 45) {
                less = true;
            } else {
                less = false;
            }
        }
        
        for (int row = 0; row < 5; row++) {
            if (bc.getSpace(row, 4).getNum() < 76 && bc.getSpace(row, 4).getNum() > 60) {
                less = true;
            } else {
                less = false;
            }
        }
        
        assertTrue(less);
    }
    
    @Test
    public void testCheckVerticalBingo() {
        BingoCard bc = new BingoCard();
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                bc.newSpace(row, col);
                bc.getSpace(row, col).setNum(col + 1);
            }
        }
        
        for (int r = 0; r < 5; r++) {
            bc.getSpace(r, 2).setFound(true);
        }
        
        assertTrue(bc.checkBingo());
        
    }
    
    @Test
    public void testCheckHorizontalBingo() {
        BingoCard bc = new BingoCard();
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                bc.newSpace(row, col);
                bc.getSpace(row, col).setNum(col + 1);
            }
        }
        
        for (int c = 0; c < 5; c++) {
            bc.getSpace(2, c).setFound(true);
        }
        
        assertTrue(bc.checkBingo());
    }
    
    @Test
    public void testCheckDiagonalBingo() {
        BingoCard bc = new BingoCard();
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                bc.newSpace(row, col);
                bc.getSpace(row, col).setNum(col + 1);
            }
        }
        
        for (int i = 0; i < 5; i++) {
            bc.getSpace(i, i).setFound(true);
        }
        
        assertTrue(bc.checkBingo());
    }
    
    @Test
    public void testResetWin() {
        BingoCard bc = new BingoCard();
        boolean b = true;
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                bc.newSpace(row, col);
                bc.getSpace(row, col).setNum(col + 1);
            }
        }
        
        for (int i = 0; i < 5; i++) {
            bc.getSpace(i, 0).setFound(true);
        }
        assertTrue(bc.checkBingo());
        bc.resetWin();
        
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                b = bc.getSpace(row, col).isWin();
            }
        }
        assertFalse(b);
        
    }
    
    @Test
    public void testColorSquare() {
        PlayerCard pc = new PlayerCard();
        boolean s = true;
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                pc.newSpace(row, col);
                pc.getSpace(row, col).setNum(col + 1);
                if (row == 2) {
                    pc.getSpace(row, col).setSelected(false);
                }
            }
        }
        
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                pc.colorSquare(0, 0);
                if (row == 2) {
                    if (pc.getSpace(row, col).getSelected()) {
                        s = false;
                    }
                }
            }
        }
        assertTrue(s);
    }
    
    @Test
    public void testBingoCardOutOfBounds() {
        BingoCard bc = new BingoCard();
        assertThrows(Exception.class, () -> bc.isFound(5, 6, 10));
    }
    
    @Test
    public void testdrawNumber() {
        BingoNumbers n = new BingoNumbers();
        n.generateNumber();
        n.generateNumber();
        n.drawNumber();
        n.drawNumber();
        assertFalse(n.getNumsList().isEmpty());
        assertEquals(2, n.getNumsList().size());
    }
    
    @Test
    public void testDrawNumberEmpty() {
        BingoNumbers n = new BingoNumbers();
        assertEquals(-1, n.drawNumber());
        assertTrue(n.getNumsList().isEmpty());
    }
    
}
