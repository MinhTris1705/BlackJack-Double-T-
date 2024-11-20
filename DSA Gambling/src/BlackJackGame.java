import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BlackJackGame {
    private JFrame frame;
    private JPanel gamePanel, buttonPanel;
    private JButton hitButton, stayButton;
    private Deck deck;
    private ArrayList<Player> players;
    private Player dealer;
    private int currentPlayerIndex;
    private boolean dealerTurn;

    public BlackJackGame(int playerCount) {
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

        // Draw dealer's hand
        g.setColor(Color.WHITE);
        g.drawString("Dealer's Hand: " + (dealerTurn ? dealer.getHandValue() : "???"), x, y);
        y += 20;

        for (Card card : dealer.getHand()) {
            g.drawImage(card.getImage(), x, y, 100, 140, null);  // Draw the card image
            x += 110;  // Space out the cards
        }

        // Reset for player's hand
        x = 20;
        y += 160;  // Leave some space below the dealer's hand

        // Draw each player's hand
        for (Player player : players) {
            g.drawString(player.getName() + "'s Hand: " + player.getHandValue(), x, y);
            y += 20;
            for (Card card : player.getHand()) {
                g.drawImage(card.getImage(), x, y, 100, 140, null);  // Draw the card image
                x += 110;  // Space out the cards
            }
            y += 160;  // Add space between each player's hands
            x = 20;  // Reset x for the next player
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
        for (Player player : players) {
            if (!player.isBusted() && (dealer.isBusted() || player.getHandValue() > dealer.getHandValue())) {
                System.out.println(player.getName() + " wins!");
            } else {
                System.out.println(player.getName() + " loses!");
            }
        }
    }

    private void updateUI() {
        gamePanel.repaint();
        if (dealerTurn || currentPlayerIndex >= players.size()) {
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);
        } else {
            hitButton.setEnabled(true);
            stayButton.setEnabled(true);
        }
    }
}
