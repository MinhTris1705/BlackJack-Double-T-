import javax.swing.*;
import java.awt.*;


public class menu {
    JFrame frame;

    public menu() {
        frame = new JFrame("BLACKJACK");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(53, 101, 77));

        // Title
        JLabel title = new JLabel("BLACKJACK 2T", JLabel.CENTER);
        title.setFont(new Font("Times New Roman\n", Font.BOLD, 50));
        title.setForeground(Color.WHITE);
        title.setBounds(200, 80, 400, 50);
        frame.add(title);

        JButton singlePlayerButton = new JButton("Single Player");
        JButton multiPlayerButton = new JButton("Multiple Players");
        JButton quitButton = new JButton("Quit");

        styleButton(singlePlayerButton, 300, 200, 200, 50);
        styleButton(multiPlayerButton, 300, 300, 200, 50);
        styleButton(quitButton, 300, 400, 200, 50);

        singlePlayerButton.addActionListener(e -> {
            frame.dispose();
            new BlackJackGame(1);
        });

        multiPlayerButton.addActionListener(e -> {
            frame.dispose();
            new BlackJackGame(4);
        });

        quitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private void styleButton(JButton button, int x, int y, int width, int height) {
        button.setBounds(x, y, width, height);
        button.setFocusable(false);
        button.setBackground(Color.YELLOW);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(button);
    }
}