import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class BlackJackGame {
    private static BlackJackGame currentGame;
    private JFrame frame;
    private JPanel gamePanel, buttonPanel;
    private JButton hitButton, stayButton;
    private Deck deck;

    public BlackJackGame(int playerCount) {
        if (currentGame != null){
            currentGame.dispose();
        }
        currentGame= this; //Set the current game instance
        setupUI();
        updateUI();
    }

    private void setupUI() {
        frame = new JFrame("Blackjack Game");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Center the window on the screen
        frame.setLocationRelativeTo(null);

        // Game Panel
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(53, 101, 77));
                drawHands(g);
            }
        };
        frame.add(gamePanel, BorderLayout.CENTER);

        // Button Panel
        buttonPanel = new JPanel();
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        hitButton.addActionListener(e -> onHit());
        stayButton.addActionListener(e -> onStay());

        frame.setVisible(true);
    }
    private void updateUI() {
    /*    if (gamePanel != null) {
            gamePanel.repaint();
        }
        if (dealerTurn || currentPlayerIndex >= players.size()) {
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);
        } else {
            hitButton.setEnabled(true);
            stayButton.setEnabled(true);
        } */
    }
    public JFrame getFrame() {
        return frame;
    }

    private void drawHands(Graphics g) {
    }

    private void onHit() {
        updateUI();
    }

    private void onStay() {
        updateUI();
    }

    private void startDealerTurn() {
    }

    private void determineWinner() {
    }

    public void dispose() {
        if (frame != null) {
            frame.dispose();  // Safely dispose of the current frame
            frame = null;     // Reset frame to avoid reusing an uninitialized object
        }
        currentGame = null;   // Reset the static reference
    }

}


