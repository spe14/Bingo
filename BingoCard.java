import javax.swing.JComponent;
import java.util.Random;

public class BingoCard extends JComponent {
    private BingoSpace[][] card;
    private boolean gameOver;
    private int a; //amount to indent x value
    private int b; //amount to indent y value
    public static final String[] BINGO = {"B", "I", "N", "G", "O"};
    private String status;
    
    private Random generator = new Random();
    /**
     * Bingo card constructor
     */
    public BingoCard() {
        card = new BingoSpace[5][5];
        status = "";
    }
    
    public boolean isFound(int num, int r, int c) {
        for (int row = 0; row < r; row++) {
            if (num == card[row][c].getNum()) {
                return true;
            }
        }
        return false;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setA(int v) {
        a = v;
    }
    
    public void setB(int v) {
        b = v;
    }
    
    /**
     * Returns the BingoSpace at a given row and column of the BingoCard
     * @param r - given row
     * @param c - given column
     * @return - returns BingoSpace at row and column
     */
    public BingoSpace getSpace(int r, int c) {
        return card[r][c];
    }
    
    /**
     * Creates a new BingoSpace at the given position in the BingoCard
     * @param r - the intended row
     * @param c - the intended column
     */
    public void newSpace(int r, int c) {
        card[r][c] = new BingoSpace(0, 0, 60);
    }
    
    /**
     * Creates a new Bingo Card with randomly generated numbers in each BingoSpace
     */
    public void newCard() {
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                card[row][col] = new BingoSpace(col * 60 + a, row * 60 + b, 60);
                int num = (15 * col) + (generator.nextInt(15) + 1);
                if (row == 2 && col == 2) {
                    card[2][2].setFound(true);
                    card[2][2].setSelected(true);
                } else {
                    card[row][col].setNum(num);
                    while (isFound(num, row, col)) {
                        num = (15 * col) + (generator.nextInt(15) + 1);
                        card[row][col].setNum(num);
                    }
                }
            }
        }
        status = "";
    }
    
    /**
     * Places a chip on a bingo space if the value called matches the value in the space
     * @param value - the called value
     * @param c - column number
     * @param r - row number
     * @return true if chip can be placed successfully, false otherwise
     */
    public boolean placeChip(int value) {
        if (gameOver) {
            return false;
        } else {
            for (int col = 0; col < 5; col++) {
                for (int row = 0; row < 5; row++) {
                    if (value == card[col][row].getNum()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    
    /**
     * Checks the card to determine if there is a bingo (5 horizontal, vertical, or diagonal)
     * @return true if there is a bingo, false otherwise
     */
    public boolean checkBingo() {
        int b;
        //checks for vertical win
        for (int col = 0; col < 5; col++) {
            b = 0;
            for (int row = 0; row < 5; row++) {
                if (card[row][col].getFound()) {
                    card[row][col].setWin(true);
                    b++;
                }
                if (b == 5) {
                    
                    gameOver = true;
                    return true;
                }
            }
            resetWin();
            
        }
        
        //checks for horizontal win
        for (int row = 0; row < 5; row++) {
            b = 0;
            for (int col = 0; col < 5; col++) {
                if (card[row][col].getFound()) {
                    card[row][col].setWin(true);
                    b++;
                }
                if (b == 5) {
                    
                    gameOver = true;
                    return true;
                }
            }
            resetWin();
            
        }
        
        //checks for diagonal win (top left to bottom right)
        for (int i = 0; i < 5; i++) {
            b = 0;
            if (i == 2) {
                card[2][2].setWin(true);
            }
            if (card[i][i].getFound()) {
                card[i][i].setWin(true);
                b++;
            }
            if (b == 5) {
                gameOver = true;
                return true;
            }
            
        }
        resetWin();
        
        //checks for diagonal win (bottom left to top right)
        for (int i = 4; i >= 0; i--) {
            b = 0;
            if (card[i][i].getFound()) {
                card[i][i].setWin(true);
                b++;
            }
            if (b == 5) {
                gameOver = true;
                return true;
            }
            
        }
        resetWin();
        return false;
    }
    
    /**
     * Resets the bingoCard to remove all winning space
     */
    public void resetWin() {
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                card[row][col].setWin(false);
            }
        }
    }
    
    /**
     * Sets the new status message
     * @param newStatus - new message
     */
    public void setStatus(String newStatus) {
        status = newStatus;
    }

}   

