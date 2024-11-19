import javax.swing.*;
import java.awt.*;

public class menu {
    private JFrame frame;

    public menu() {
        // Create frame
        frame = new JFrame("Blackjack Menu");
        frame.setSize(500, 400); // Adjusted size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Using absolute layout for better control

        // Set background color
        frame.getContentPane().setBackground(new Color(34, 139, 34)); // Green background

        // Add title
        JLabel titleLabel = new JLabel("BLACKJACK-2T");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE); // White text color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(50, 30, 400, 50); // Position and size
        frame.add(titleLabel);

        // Create buttons
        JButton singlePlayerButton = createStyledButton("Single Player");
        singlePlayerButton.setBounds(150, 100, 200, 50);

        JButton multiplayerButton = createStyledButton("Multiple Players");
        multiplayerButton.setBounds(150, 180, 200, 50);

        JButton quitButton = createStyledButton("Quit Game");
        quitButton.setBounds(150, 260, 200, 50);

        // Button actions
        singlePlayerButton.addActionListener(e -> {
            frame.dispose(); // Close menu window
            JOptionPane.showMessageDialog(null, "Single mode not implemented yet!"); // Placeholder
        });

        multiplayerButton.addActionListener(e -> {
            frame.dispose(); // Close menu window
            JOptionPane.showMessageDialog(null, "Multiplayer mode not implemented yet!"); // Placeholder
        });

        quitButton.addActionListener(e -> System.exit(0)); // Exit the game

        // Add buttons to frame
        frame.add(singlePlayerButton);
        frame.add(multiplayerButton);
        frame.add(quitButton);

        // Center the window and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Helper method to style buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(255, 215, 0)); // Gold background
        button.setForeground(Color.BLACK); // Black text
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Black border
        return button;
    }
}
