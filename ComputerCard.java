import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Color;

public class ComputerCard extends BingoCard {
    LinkedList<Integer> selectedNums = new LinkedList<Integer>();
    ArrayList<Integer> savedNums = new ArrayList<Integer>();
    
    private BufferedWriter bw;
    
    public ComputerCard() {
        super();     
        
        setA(520);
        setB(100);      
        newCard();
    }

    
    public void colorSquare() {
        for (int n : BingoNumbers.nums) {
            for (int col = 0; col < 5; col++) {
                for (int row = 0; row < 5; row++) {
                    if (n == getSpace(row, col).getNum()) {
                        getSpace(row, col).setFound(true);
                        getSpace(row, col).setSelected(true);
                    }
                }
            }
        }
    }
    
    public void save() throws IOException {
        bw = new BufferedWriter(new FileWriter("savedComputer.txt"));
        
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
        System.out.println("saved computer game to file"); 
    }
    
    public void load() throws IOException {
        FileReader frc = new FileReader("savedComputer.txt");
        BufferedReader brc = new BufferedReader(frc);
        String linec = brc.readLine();
        
        while (linec != null) {
            String[] s = linec.split(" ");
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
            linec = brc.readLine();
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
        
        frc.close();
        brc.close();
        System.out.println("computer game loaded from file");
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        Color cs = new Color(173, 216, 230); //light blue
        Color cw = new Color(30, 144, 255); //dark blue
        
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                BingoSpace bs = getSpace(row, col);
                g2.draw(bs);
                
                if (bs.getSelected()) {
                    g2.setColor(cs);
                    if (bs.isWin()) {
                        g2.setColor(cw);
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
                    g2.drawString("Computer", 640, (int)bs.getY() + 100);
                }
            }
        }
        
        g2.setColor(cw);
        g2.drawString(getStatus(), 545, 500);
    
    }
}
