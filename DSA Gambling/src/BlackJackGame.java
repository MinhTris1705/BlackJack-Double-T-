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

        // Check for blackjack after dealing initial cards
        for (Player player : players) {
            if (player.hasBlackjack()) {
                String message = player.getName() + " has a Blackjack and wins!";
                new ResultBoard(message, this);
                return; // End the game immediately if there's a blackjack
            }

            if (player.hasDoubleAces()) {
                // Disable "Hit" button for players with double Aces
                String message = player.getName() + " has Double Aces and wins!";
                new ResultBoard(message, this);
                // Disable hit and stay buttons for this player
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                break;
            }
        }
        if (dealer.hasBlackjack()) {
            String message = "Dealer has a Blackjack and wins!";
            new ResultBoard(message, this);
            return; // End the game immediately if the dealer has a blackjack
        }

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

        // Check for Double Aces first (Player vs Dealer)
        boolean dealerHasDoubleAces = dealer.hasDoubleAces();
        for (Player player : players) {
            boolean playerHasDoubleAces = player.hasDoubleAces();

            if (playerHasDoubleAces && dealerHasDoubleAces) {
                result.append(player.getName()).append(" ties with Dealer (Both have Double Aces)!\n");
            } else if (playerHasDoubleAces) {
                result.append(player.getName()).append(" wins with Double Aces!\n");
            } else if (dealerHasDoubleAces) {
                result.append(player.getName()).append(" loses! Dealer wins with Double Aces.\n");
            }
        }

        // Then check for Five Cards Rule (Player vs Dealer)
        boolean dealerHasFiveCards = dealer.getHand().size() == 5 && dealer.getHandValue() <= 21;
        for (Player player : players) {
            boolean playerHasFiveCards = player.getHand().size() == 5 && player.getHandValue() <= 21;

            if (playerHasFiveCards && !dealerHasFiveCards) {
                result.append(player.getName()).append(" wins with 5 cards!\n");
            } else if (!playerHasFiveCards && dealerHasFiveCards) {
                result.append(player.getName()).append(" loses! Dealer wins with 5 cards!\n");
            } else if (playerHasFiveCards && dealerHasFiveCards) {
                result.append(player.getName()).append(" ties with Dealer (Both have 5 cards ≤ 21)!\n");
            }
        }

        // Now check for Blackjack (Player vs Dealer)
        boolean dealerBlackjack = dealer.hasBlackjack();
        for (Player player : players) {
            boolean playerBlackjack = player.hasBlackjack();

            if (playerBlackjack && dealerBlackjack) {
                result.append(player.getName()).append(" ties with Dealer (Both have Blackjack).\n");
            } else if (playerBlackjack) {
                result.append(player.getName()).append(" wins with a Blackjack!\n");
            } else if (dealerBlackjack) {
                result.append(player.getName()).append(" loses! Dealer has a Blackjack.\n");
            }
        }

        // Finally, check for Busted or Standard Score Comparison (After the previous rules)
        boolean dealerBusted = dealer.isBusted();
        int dealerHandValue = dealer.getHandValue();

        for (Player player : players) {
            boolean playerBusted = player.isBusted();
            int playerHandValue = player.getHandValue();

            // Player with 5 cards should win over someone with a higher score, unless they busted
            if (!playerBusted && player.getHand().size() == 5 && playerHandValue <= 21) {
                result.append(player.getName()).append(" wins with 5 cards! (Better rank)\n");
            } else if (!dealerBusted && dealer.getHand().size() == 5 && dealerHandValue <= 21) {
                result.append(player.getName()).append(" loses! Dealer wins with 5 cards!\n");
            }

            // Standard Score Comparison (after ranking by 5 cards, Double Aces, and Blackjack)
            if (playerBusted && dealerBusted) {
                result.append(player.getName()).append(" and Dealer tie (Both busted)!\n");
            } else if (playerBusted) {
                result.append(player.getName()).append(" loses! (Busted)\n");
            } else if (dealerBusted) {
                result.append(player.getName()).append(" wins! Dealer is busted.\n");
            } else if (playerHandValue > dealerHandValue) {
                result.append(player.getName()).append(" wins with a higher score!\n");
            } else if (playerHandValue < dealerHandValue) {
                result.append(player.getName()).append(" loses! Dealer has a higher score.\n");
            } else {
                result.append(player.getName()).append(" ties with Dealer (Equal scores).\n");
            }
        }

        // Display the result message
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
