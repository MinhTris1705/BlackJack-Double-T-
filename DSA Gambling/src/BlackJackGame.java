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
        if (currentGame != null) {
            currentGame.dispose();
        }
        currentGame = this;
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

        for (Player player : players) {
            player.addCard(deck.draw());
            player.addCard(deck.draw());
        }

        dealer.addCard(deck.draw());
        dealer.addCard(deck.draw());

        if (dealer.hasBlackjack()) {
            String message = "Dealer has a Blackjack and wins!";
            new ResultBoard(message, this);
            return;
        }

        currentPlayerIndex = 0;
        dealerTurn = false;
    }

    private void setupUI() {
        frame = new JFrame("Blackjack Game");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(53, 101, 77));
                drawHands(g);
            }
        };
        frame.add(gamePanel, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener(e -> onHit());
        stayButton.addActionListener(e -> onStay());

        frame.setVisible(true);
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

    private void drawHands(Graphics g) {
        int x = 20, y = 20;
        if (dealerTurn) {
            g.setColor(Color.WHITE);
            g.drawString("Dealer's Hand: " + dealer.getHandValue(), x, y);
            y += 20;
            for (Card card : dealer.getHand()) {
                g.drawImage(card.getImage(), x, y, 100, 140, null);
                x += 110;
            }
        } else {
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

        if (!dealerBlackjack) {
            boolean dealerBusted = dealer.isBusted();
            int dealerHandValue = dealer.getHandValue();

            for (Player player : players) {
                if (player.hasBlackjack()) {
                    continue;
                }

                boolean playerBusted = player.isBusted();
                int playerHandValue = player.getHandValue();

                if (playerBusted) {
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
        }
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
            frame.dispose();
            frame = null;
        }
        currentGame = null;
    }

}