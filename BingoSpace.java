import java.awt.Rectangle;


public class BingoSpace extends Rectangle {
    private int num; // number contained in the space
    private boolean found; // is true if the value has been correctly identified
    private boolean selected; // is true if the player has selected the space
    private boolean win; // is true if the space belongs to a bingo
    
    /**
     * BingoSpace constructor
     * @param x - x coordinate of upper left corner
     * @param y - y coordinate of upper left corner
     * @param width - width (and height) of square
     */
    public BingoSpace(int x, int y, int width) {
        super(x, y, width, width);
        found = false;
        selected = false;
        win = false;
    }
    
    /**
     * Sets a new number value
     * @param newNum - the new number to set num equal to
     */
    public void setNum(int newNum) {
        num = newNum;
    }
    
    /**
     * Gets the value of num
     * @return the number contained in the space
     */
    public int getNum() {
        return num;
    }
    
    /**
     * Sets the space as part of a winning sequence if it results in a Bingo
     */
    public void setWin(boolean v) {
        win = v;
    }
    
    /**
     * Determines if a space is part of a winning sequence or not
     * @return true if space belongs to a Bingo, false otherwise
     */
    public boolean isWin() {
        return win;
    }
    
    /**
     * Changes the value of found to the opposite of what it currently is
     */
    public void setFound(boolean v) {
        found = v;
    }
    
    /**
     * Gets the value of found
     * @return the value of found
     */
    public boolean getFound() {
        return found;
    }
    
    /**
     * Changes the value of selected to the opposite of what it currently is
     */
    public void setSelected(boolean v) {
        selected = v;
    }
    
    /**
     * Determines if a space has been selected by the player
     * @return true if the space is selected, false otherwise
     */
    public boolean getSelected() {
        return selected;
    }
    
    
}

