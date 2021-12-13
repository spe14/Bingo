import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;



public class PlayerCard extends BingoCard {
    String name = "Player";
    ArrayList<Integer> savedNums = new ArrayList<Integer>();
    LinkedList<Integer> selectedNums = new LinkedList<Integer>();
    
    private BufferedWriter bw;
    
    public PlayerCard() {
        super();
        
        setA(50);
        setB(100);
        newCard();
    }

    /**
     * Colors the square if it has been selected/unselected
     * @param x
     * @param y
     */
    public void colorSquare(int x, int y) {
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                BingoSpace bs = this.getSpace(row, col);
                if (row == 2 && col == 2) {
                    
                } else if (bs.contains(x, y)) {
                    if (bs.getSelected()) {
                        bs.setSelected(false);
                    } else {
                        bs.setSelected(true);
                    }
                    break;
                }
            }
        }
        repaint();
    }

    /**
     * If a number has been called and the space has been selected, the space is found
     */
    public void isCalled() {
        for (int n : BingoNumbers.nums) {
            for (int col = 0; col < 5; col++) {
                for (int row = 0; row < 5; row++) {
                    if (n == getSpace(row, col).getNum()) {
                        getSpace(row, col).setFound(true);
                    }
                }
            }
        }
    }
    
    /**
     * Sets the username to the given name
     * @param s - given username
     */
    public void setPlayerName(String s) {
        name = s;
    }
    
    /**
     * Gets the player's username
     * @return player username
     */
    public String getPlayerName() {
        return name;
    }
    
    /**
     * Saves the current player card to a file
     * @throws IOException
     */
    public void save() throws IOException {
        bw = new BufferedWriter(new FileWriter("savedPlayer.txt"));
        
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (row == 2 && col == 2) {
                    bw.write("FS ");
                } else {
                    int n = getSpace(row, col).getNum();
                    if (getSpace(row, col).getSelected()) {
                        bw.write(n + ",S ");
                    } else {
                        bw.write(n + " ");
                    }
                }
            }
            bw.newLine();
        }
        bw.close();
        System.out.println("saved game to file"); 
    }
    
    /**
     * Loads the saved player card back onto the game screen
     * @throws IOException
     */
    public void load() throws IOException {
        FileReader frp = new FileReader("savedPlayer.txt");
        BufferedReader brp = new BufferedReader(frp);
        String linep = brp.readLine();
        
        while (linep != null) {
            String[] s = linep.split(" ");
            for (int i = 0; i < s.length; i++) {
                if (s[i].contains(",S")) {
                    String s2 = s[i].replace(",S", "");
                    int n = Integer.parseInt(s2);
                    savedNums.add(n);
                    selectedNums.add(n);
                } else if (s[i].equals("FS")) {
                    savedNums.add(0);
                } else {
                    int n = Integer.parseInt(s[i]);
                    savedNums.add(n);
                }
            }
            linep = brp.readLine();
        }
        
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                if (row == 2 && col == 2) {
                    getSpace(row, col).setSelected(true);
                } else {
                    int p = savedNums.get((row * 5) + col);
                    getSpace(row, col).setNum(p);
                    if (selectedNums.contains(p)) {
                        getSpace(row, col).setSelected(true);
                    }
                }
            }
        }
        
        frp.close();
        brp.close();
        System.out.println("player game loaded from file");
    }
    
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Color ps = new Color(152, 251, 152); //light green
        Color pw = new Color(0, 255, 127); //dark green
        
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                BingoSpace bs = getSpace(row, col);
                g2.draw(bs);
                
                if (bs.getSelected()) {
                    g2.setColor(ps);
                    if (bs.isWin()) {
                        g2.setColor(pw);
                    }
                    g2.fill(bs);
                    g2.setColor(Color.BLACK);
                    g2.draw(bs);
                }
                
                if (row == 2 && col == 2) {
                    
                } else {
                    int num = getSpace(row, col).getNum();
                    int x = (int)bs.getX() + 15;
                    int y = (int)bs.getY() + 30 + (60 / 8);
                    if (num < 10) {
                        g2.drawString(" " + num + "", x, y);
                    } else {
                        g2.drawString(num + "", x, y);
                    }
                }
                
                if (row == 0) {
                    g2.drawString(BINGO[col], (int)bs.getX() + 10, (int)bs.getY() - 15);
                }
                
                if (row == 4 && col == 1) {
                    g2.drawString(getPlayerName(), 170, (int)bs.getY() + 100);
                }
            }
        }
        
        g2.setColor(pw);
        g2.drawString(getStatus(), 50, 500);
    }
}
