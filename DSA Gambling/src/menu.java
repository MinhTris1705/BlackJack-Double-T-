import javax.swing.*;
import java.awt.*;


public class menu {
    JFrame frame;

    public menu() {
        frame = new JFrame("Blackjack Menu");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(53, 101, 77));

        // Title
        JLabel title = new JLabel("Blackjack Double 2T", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.LIGHT_GRAY);
        title.setBounds(200, 80, 400, 50);
        frame.add(title);

        // Buttons
        JButton singlePlayerButton = new JButton("Single Player");
        JButton multiPlayerButton = new JButton("Multiple Players");
        JButton quitButton = new JButton("Quit");

        // Style Buttons
        styleButton(singlePlayerButton, 300, 200, 200, 50);
        styleButton(multiPlayerButton, 300, 300, 200, 50);
        styleButton(quitButton, 300, 400, 200, 50);

        // Add Button Actions
        singlePlayerButton.addActionListener(e -> {
            frame.dispose();
            new BlackJackGame(1);
        });

        multiPlayerButton.addActionListener(e -> {
            frame.dispose();
            new BlackJackGame(4); // 4 Players in Multiplayer Mode
        });

        quitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private void styleButton(JButton button, int x, int y, int width, int height) {
        button.setBounds(x, y, width, height);
        button.setFocusable(false);
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(button);
    }
}
