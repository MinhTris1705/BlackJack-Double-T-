import javax.swing.*;
import java.awt.*;

public class ResultBoard {
    public ResultBoard(String message, BlackJackGame parentGame) {
        JFrame frame = new JFrame("Game Result");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Message Label
        JLabel resultLabel = new JLabel(message, JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(resultLabel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton playAgainButton = new JButton("Play Again");
        JButton exitToMenuButton = new JButton("Exit to Menu");

        // Button Actions
        playAgainButton.addActionListener(e -> {
            frame.dispose();
            new BlackJackGame(parentGame.getPlayerCount());
        });

        exitToMenuButton.addActionListener(e -> {
            frame.dispose();
            new menu();
        });

        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitToMenuButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
