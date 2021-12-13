import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Container;

import java.awt.event.MouseListener;
import java.io.IOException;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;


public class Game extends JFrame implements Runnable {
    public static final int WIDTH = 900;
    public static final int LENGTH = 600;

    private JPanel panel;
    private JButton reset;
    private JButton bingo;
    private JButton nextNum;
    private JButton username;
    private JButton save;
    private JButton load;
    private Container layout;

    private BingoNumbers bingoNumbers;
    private PlayerCard playerCard;
    private ComputerCard computerCard;
    private final String instructions;

    private MouseListener mouseListener;
    private boolean winner = false;

    /**
     * Constructs the game window
     */
    public Game() {
        setSize(WIDTH, LENGTH);

        panel = new JPanel();

        //JButtons
        reset = new JButton("Reset");
        bingo = new JButton("Bingo!");
        nextNum = new JButton("Call Next Number");
        username = new JButton("Change Username");
        save = new JButton("Save Game");
        load = new JButton("Load Saved Game");
        
        instructions = "How To Play Bingo:\n You will be playing against a computer in this game.\n"
                + " The objective of this game is to get 5 in a row, horizontally, vertically, or "
                + "diagonally.\n"
                + " As numbers are called, you will select a square on your bingo card if you have "
                + "the number that was called.\n If you think you have a Bingo, click the Bingo "
                + "button at the bottom of the screen and you will get a message if you have a "
                + "winning sequence.\n You can also set and change your username at any point "
                + "during the game or save a game for later.\n To access a saved game, just "
                + "press load saved game at the bottom of the screen.\n Good luck!";
        
        JOptionPane.showMessageDialog(panel, instructions, "Instructions",
                JOptionPane.PLAIN_MESSAGE);
        
        panel.add(username);
        username.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog(panel, "Please enter a username", 
                        "Username", JOptionPane.PLAIN_MESSAGE);
                playerCard.setPlayerName(s);
                
                playerCard.repaint();
                layout.repaint();
                
            }
        });
        
        panel.add(save);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    playerCard.save();
                } catch (IOException e1) {
                    
                }
                
                try {
                    computerCard.save();
                } catch (IOException e1) {
                    
                }
                playerCard.newCard();
                computerCard.newCard();
                bingoNumbers.getNumsList().clear();
                winner = false;
                
                computerCard.colorSquare();
                playerCard.repaint();
                computerCard.repaint();
                bingoNumbers.repaint();
                layout.repaint();
            }
        });
        
        panel.add(load);
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    playerCard.load();
                } catch (IOException e1) {
                    
                }
                try {
                    computerCard.load();
                } catch (IOException e1) {
                    
                }
                
                computerCard.colorSquare();
                playerCard.repaint();
                computerCard.repaint();
                bingoNumbers.repaint();
                layout.repaint();
            }
        });
        

        panel.add(reset);
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerCard.newCard();
                computerCard.newCard();
                bingoNumbers.getNumsList().clear();
                winner = false;
                
                computerCard.colorSquare();
                playerCard.repaint();
                computerCard.repaint();
                bingoNumbers.repaint();
                layout.repaint();
            }
        });
        
        panel.add(bingo);
        bingo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!winner) {
                    if (playerCard.checkBingo()) {
                        playerCard.setStatus("WINNER: " + playerCard.getPlayerName());
                        winner = true;
                    } else {
                        playerCard.setStatus("Sorry, you don't have bingo.");
                    }
                }
                
                computerCard.colorSquare();
                playerCard.repaint();
                computerCard.repaint();
                bingoNumbers.repaint();
                layout.repaint();
            }
        });
        
        panel.add(nextNum);
        nextNum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!winner) {
                    bingoNumbers.generateNumber();
                    playerCard.setStatus("");
                    computerCard.colorSquare();   
                    playerCard.isCalled();         
                    if (computerCard.checkBingo()) {
                        computerCard.setStatus("WINNER: COMPUTER");
                        winner = true;
                    }
                }
                
                computerCard.colorSquare();
                playerCard.repaint();
                computerCard.repaint();
                bingoNumbers.repaint();
                layout.repaint();
            }
        });

        layout = this.getContentPane();
        layout.add(panel, "South");
        setVisible(true);

        playerCard = new PlayerCard();
        computerCard = new ComputerCard();
        bingoNumbers = new BingoNumbers();
        
        add(playerCard); 
        setVisible(true);

        add(computerCard);
        setVisible(true);

        add(bingoNumbers);
        setVisible(true);

        playerCard.addMouseListener(mouseListener);
        setVisible(true);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                playerCard.colorSquare(x, y); 
            }
        });
    }
    
    public void run() {
        
    }
    
    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
        
        SwingUtilities.invokeLater(game);
    }
}