import javax.swing.*;
import java.awt.*;

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

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
