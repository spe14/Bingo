import java.util.Random;
import java.util.LinkedList;
import javax.swing.*;

import java.awt.*;

public class BingoNumbers extends JComponent {
    public static LinkedList<Integer> nums = new LinkedList<Integer>();
    private Random generator;
    
    public BingoNumbers() {
    }
    
    public LinkedList<Integer> getNumsList() {
        return nums;
    }
    
    public boolean isFound(int value) {
        for (int x : nums) {
            if (x == value) {
                return true;
            }
        }
        return false;
    }
    /**
     * Adds a randomly generated number between 1-75 to the list of nums
     */
    public void generateNumber() {
        boolean e = false;
        generator = new Random();
        
        while (!e && nums.size() != 75) {
            int newNum = generator.nextInt(75) + 1;
            if (!isFound(newNum)) {
                nums.add(newNum);
                e = true;
            }
        }
    }
    /**
     * Returns a randomly drawn number
     * @return randomly drawn number or -1 if the list of nums is empty
     */
    public int drawNumber() {
        if (!nums.isEmpty()) {
            return nums.get(nums.size() - 1);
        } else {
            return -1;
        }
    }
    
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        String call = "Calling: ";
        g2.drawString(call, Game.WIDTH / 2 - 85, 70);
        if (!nums.isEmpty()) {
            int n = drawNumber();
            int x = (Game.WIDTH / 2);
            int y = 70;

            if (n <= 15) {
                g2.drawString("B-" + n, x, y);
            } else if (n <= 30) {
                g2.drawString("I-" + n, x, y);
            } else if (n <= 45) {
                g2.drawString("N-" + n, x, y);
            } else if (n <= 60) {
                g2.drawString("G-" + n, x, y);
            } else if (n <= 75) {
                g2.drawString("O-" + n, x, y);
            }
        }
    }
}
