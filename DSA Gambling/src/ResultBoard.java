import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ResultBoard {
    public ResultBoard(String message, BlackJackGame parentGame) {
        JFrame frame = new JFrame("Game Result");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Dynamically adjust the size based on the message length
        int lines = message.split("\n").length;
        frame.setSize(400, Math.min(200 + lines * 20, 600));
        frame.setLocationRelativeTo(null);

        // Message Area
        JTextArea resultArea = new JTextArea(message);
        resultArea.setFont(new Font("Arial", Font.BOLD, 16));
        resultArea.setEditable(false); // Disable editing
        resultArea.setBackground(frame.getBackground()); // Match frame background
        resultArea.setMargin(new Insets(10, 10, 10, 10));

        // Add scroll pane if the message is too long
        JScrollPane scrollPane = new JScrollPane(resultArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton playAgainButton = new JButton("Play Again");
        JButton exitToMenuButton = new JButton("Exit to Menu");

        // Play Again Button Action
        playAgainButton.addActionListener(e -> {
            frame.dispose(); // Close the result board
            parentGame.dispose(); // Dispose of the current game completely
            new BlackJackGame(parentGame.getPlayerCount()); // Start a new game
        });

        // Exit to Menu Button Action
        exitToMenuButton.addActionListener(e -> {
            frame.dispose(); // Close the result board
            parentGame.dispose(); // Dispose of the current game completely
            new menu(); // Open the menu
        });

        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitToMenuButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Append hand value for single-player mode
        if (parentGame.getPlayerCount() == 1) {
            Player singlePlayer = parentGame.getPlayers().get(0); // Get the single player
            String handValueMessage = "\nHand Value: " + singlePlayer.getHandValue();
            if (singlePlayer.isBusted()) {
                handValueMessage += " (Busted)";
            }
            resultArea.append(handValueMessage); // Append directly to the JTextArea
        }

        // If multiple players, rank them
        ArrayList<Player> players = parentGame.getPlayers();
        if (players.size() > 1) {
            rankPlayers(players);
            StringBuilder ranking = new StringBuilder("\nPlayer Rankings:\n");
            for (Player player : players) {
                ranking.append(player.getRank()).append(". ").append(player.getName())
                        .append(" - Hand Value: ").append(player.getHandValue());

                if (player.isBusted()) {
                    ranking.append(" (Busted)");
                }
                ranking.append("\n");
            }
            resultArea.append(ranking.toString());
        }

        frame.setVisible(true);
    }

    // Sort and rank players by hand value
    private void rankPlayers(ArrayList<Player> players) {
        // Sort players by hand value (descending). Busted players are treated as lowest priority.
        players.sort((p1, p2) -> {
            int handValue1;
            int handValue2;

            if (p1.isBusted()) {
                handValue1 = -1;
            } else {
                handValue1 = p1.getHandValue();
            }

            if (p2.isBusted()) {
                handValue2 = -1;
            } else {
                handValue2 = p2.getHandValue();
            }

            return Integer.compare(handValue2, handValue1); // Descending order
        });

        // Assign ranks, ensuring ties get the same rank
        int rank = 1;
        for (int i = 0; i < players.size(); i++) {
            if (i > 0) {
                Player previousPlayer = players.get(i - 1);
                Player currentPlayer = players.get(i);

                int previousValue;
                int currentValue;

                if (previousPlayer.isBusted()) {
                    previousValue = -1;
                } else {
                    previousValue = previousPlayer.getHandValue();
                }

                if (currentPlayer.isBusted()) {
                    currentValue = -1;
                } else {
                    currentValue = currentPlayer.getHandValue();
                }

                // If the current player has a different score, increase the rank
                if (currentValue != previousValue) {
                    rank = i + 1;
                }
            }
            players.get(i).setRank(rank); // Set the rank for the player
        }
    }
}
