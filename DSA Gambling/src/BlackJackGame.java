import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BlackJackGame {
    private static BlackJackGame currentGame;
    private JFrame frame;
    private JPanel gamePanel, buttonPanel;
    private JButton hitButton, stayButton;
    private Deck deck;
    private ArrayList<Player> players;
    private Player dealer;
    private int currentPlayerIndex;
    private boolean dealerTurn;

    public BlackJackGame(int playerCount) {
        if (currentGame != null){
            currentGame.dispose();
        }
        currentGame= this; //Set the current game instance
        initializeGame(playerCount);
        setupUI();
        updateUI();
    }

    private void initializeGame(int playerCount) {
        deck = new Deck();
        deck.shuffle();

        players = new ArrayList<>();
        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player("Player " + i));
        }

        dealer = new Player("Dealer");

        // Deal initial cards
        for (Player player : players) {
            player.addCard(deck.draw());
            player.addCard(deck.draw());
        }
        dealer.addCard(deck.draw());
        dealer.addCard(deck.draw());

        currentPlayerIndex = 0;
        dealerTurn = false;
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

    private void drawHands(Graphics g) {
        int x = 20, y = 20;

        // Draw dealer's hand (if it's dealer's turn)
        if (dealerTurn) {
            g.setColor(Color.WHITE);
            g.drawString("Dealer's Hand: " + dealer.getHandValue(), x, y);
            y += 20;
            for (Card card : dealer.getHand()) {
                g.drawImage(card.getImage(), x, y, 100, 140, null);
                x += 110;
            }
        } else {
            // Draw current player's hand
            Player currentPlayer = players.get(currentPlayerIndex);
            g.setColor(Color.WHITE);
            g.drawString(currentPlayer.getName() + "'s Hand: " + currentPlayer.getHandValue(), x, y);
            y += 20;
            for (Card card : currentPlayer.getHand()) {
                g.drawImage(card.getImage(), x, y, 100, 140, null);
                x += 110;
            }
        }
    }

    private void onHit() {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.addCard(deck.draw());

        if (currentPlayer.getHandValue() >= 21) {
            onStay();
        }
        updateUI();
    }

    private void onStay() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            startDealerTurn();
        } else {
            updateUI();
        }
    }

    private void startDealerTurn() {
        dealerTurn = true;
        while (dealer.getHandValue() <= 15) {
            dealer.addCard(deck.draw());
        }
        determineWinner();
        updateUI();
    }

    private void determineWinner() {
        StringBuilder result = new StringBuilder();

        // Check if dealer is busted
        boolean dealerBusted = dealer.isBusted();
        int dealerHandValue = dealer.getHandValue();

        for (Player player : players) {
            boolean playerBusted = player.isBusted();
            int playerHandValue = player.getHandValue();

            // Case when both player and dealer are busted (draw)
            if (playerBusted && dealerBusted) {
                result.append(player.getName()).append(" and Dealer tie!\n");
            }
            // Player wins if dealer is busted or player has a higher hand value
            else if (!playerBusted && (dealerBusted || playerHandValue > dealerHandValue)) {
                result.append(player.getName()).append(" wins!\n");
            }
            // Player loses if dealer wins or player is busted
            else if (playerBusted || playerHandValue < dealerHandValue) {
                result.append(player.getName()).append(" loses!\n");
            }
            // If it's a tie (player and dealer have equal hand value and neither is busted)
            else if (playerHandValue == dealerHandValue) {
                result.append(player.getName()).append(" and Dealer tie!\n");
            }
        }

        // Update the result message
        new ResultBoard(result.toString(), this);
    }


    public int getPlayerCount() {
        return players.size();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void dispose() {
        if (frame != null) {
            frame.dispose();  // Safely dispose of the current frame
            frame = null;     // Reset frame to avoid reusing an uninitialized object
        }
        currentGame = null;   // Reset the static reference
    }

    private void updateUI() {
        if (gamePanel != null) {
            gamePanel.repaint();
        }
        if (dealerTurn || currentPlayerIndex >= players.size()) {
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);
        } else {
            hitButton.setEnabled(true);
            stayButton.setEnabled(true);
        }
    }
    public JFrame getFrame() {
        return frame;
    }

}
