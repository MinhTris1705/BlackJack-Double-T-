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

        playAgainButton.addActionListener(e -> {
            frame.dispose(); // Close result board
            parentGame.getFrame().dispose(); // Dispose of the BlackJackGame frame
            new BlackJackGame(parentGame.getPlayerCount()); // Start a new game
        });

        exitToMenuButton.addActionListener(e -> {
            frame.dispose();      // Close result board
            if (parentGame != null) {
                parentGame.dispose(); // Dispose of the current game safely
            }
            new menu();           // Open the menu
        });

        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitToMenuButton);

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
            for (int i = 0; i < players.size(); i++) {
                ranking.append(i + 1).append(". ").append(players.get(i).getName())
                        .append(" - Hand Value: ").append(players.get(i).getHandValue());

                if (players.get(i).isBusted()) {
                    ranking.append(" (Busted)");
                }
                ranking.append("\n");
            }
            resultArea.append(ranking.toString());
        }

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Sort and rank players by hand value
    private void rankPlayers(ArrayList<Player> players) {
        int n = players.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                int handValueI = players.get(maxIndex).getHandValue();
                int handValueJ = players.get(j).getHandValue();

                // If player is busted, treat their value as lowest priority
                if (players.get(maxIndex).isBusted()) {
                    handValueI = -1;
                }
                if (players.get(j).isBusted()) {
                    handValueJ = -1;
                }

                if (handValueJ > handValueI) {
                    maxIndex = j;
                }
            }
            // Swap the players
            Player temp = players.get(maxIndex);
            players.set(maxIndex, players.get(i));
            players.set(i, temp);
        }
    }
}
