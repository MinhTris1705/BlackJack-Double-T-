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
        resultArea.setEditable(false); // Disable editing
        resultArea.setBackground(frame.getBackground()); // Match frame background
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
            Player singlePlayer = parentGame.getPlayers().get(0); // Get the single player
            String handValueMessage = "\nHand Value: " + singlePlayer.getHandValue();
            if (singlePlayer.isBusted()) {
                handValueMessage += " (Busted)";
            }
            resultArea.append(handValueMessage); // Append directly to the JTextArea
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

    private void rankPlayers(ArrayList<Player> players){
        int n = players.size();
        for(int i = 0; i < n - 1; i++){
            int maxIndex = i;
            for (int j= i + 1; j < n ; j++){
                int handValueI = players.get(maxIndex).getHandValue();
                int handValueJ = players.get(j).getHandValue();

                if(players.get(maxIndex).isBusted()){
                    handValueI = -1;
                }
                if(players.get(j).isBusted()){
                    handValueJ =-1;
                }

                if (handValueJ> handValueI){
                    maxIndex =j;
                }
            }

            Player temp= players.get(maxIndex);
            players.set(maxIndex, players.get(i));
            players.set(i,temp);
        }
    }
}
