import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

public class ResultBoard {
    public ResultBoard(String message,BlackJackGame parentGame){
        JFrame frame = new JFrame("Game Result: ");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        int lines = message.split("\n").length;
        frame.setSize(400, Math.min(200 + lines * 20, 600));
        frame.setLocationRelativeTo(null);

        JTextArea resultArea = new JTextArea(message);
        resultArea.setFont(new Font("Arial", Font.BOLD, 16));
        resultArea.setEditable(false);
        resultArea.setBackground(frame.getBackground());
        resultArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        frame.add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton playAgainButton = new JButton("Play Again");
        JButton exitToMenuButton = new JButton("Exit to Menu");


        playAgainButton.addActionListener(e -> {
            frame.dispose();
            parentGame.dispose();
            new BlackJackGame(parentGame.getPlayerCount());
        });


        exitToMenuButton.addActionListener(e -> {
            frame.dispose();
            parentGame.dispose();
            new menu();
        });

        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitToMenuButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        if (parentGame.getPlayerCount() == 1) {
            Player singlePlayer = parentGame.getPlayers().get(0);
            String handValueMessage = "\nHand Value: " + singlePlayer.getHandValue();
            if (singlePlayer.isBusted()) {
                handValueMessage += " (Busted)";
            }
            resultArea.append(handValueMessage);
        }

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

    private static final int BUSTED_HAND_VALUE = Integer.MIN_VALUE;

    private void rankPlayers(ArrayList<Player> players) {
        int n = players.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                Player p1 = players.get(maxIndex);
                Player p2 = players.get(j);

                int handValue1;
                int handValue2;

                if (p1.isBusted()) {
                    handValue1 = BUSTED_HAND_VALUE;
                } else {
                    handValue1 = p1.getHandValue();
                }

                if (p2.isBusted()) {
                    handValue2 = BUSTED_HAND_VALUE;
                } else {
                    handValue2 = p2.getHandValue();
                }

                if (handValue2 > handValue1) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                Player temp = players.get(i);
                players.set(i, players.get(maxIndex));
                players.set(maxIndex, temp);
            }
        }
        int rank = 1;
        for (int i = 0; i < players.size(); i++) {
            if (i > 0) {
                Player previousPlayer = players.get(i - 1);
                Player currentPlayer = players.get(i);
                int previousValue;
                int currentValue;

                if (previousPlayer.isBusted()) {
                    previousValue = BUSTED_HAND_VALUE;
                } else {
                    previousValue = previousPlayer.getHandValue();
                }

                if (currentPlayer.isBusted()) {
                    currentValue = BUSTED_HAND_VALUE;
                } else {
                    currentValue = currentPlayer.getHandValue();
                }

                if (currentValue != previousValue) {
                    rank = i + 1;
                }
            }
            players.get(i).setRank(rank);
        }
    }
}
