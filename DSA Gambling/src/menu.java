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
        frame.getContentPane().setBackground(Color.PINK);

        // Title
        JLabel title = new JLabel("BLACKJACK 2T", JLabel.CENTER);
        title.setFont(new Font("Times New Roman\n", Font.BOLD, 50));
        title.setForeground(Color.WHITE);
        frame.add(title);

        JButton singlePlayerButton = new JButton("Single Player");
        JButton multiPlayerButton = new JButton("Multiple Players");
        JButton quitButton = new JButton("Quit");

        frame.add(singlePlayerButton);
        frame.add(multiPlayerButton);
        frame.add(quitButton);

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

        centerElements(title, singlePlayerButton, multiPlayerButton, quitButton);

        frame.setVisible(true);
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                centerElements(title, singlePlayerButton, multiPlayerButton, quitButton);
            }
        });
    }

    private void styleButton(JButton button, int x, int y, int width, int height) {
        button.setBounds(x, y, width, height);
        button.setFocusable(false);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.white);
        button.setFont(new Font("Arial", Font.BOLD, 18));
    }

    private void centerElements(JLabel title, JButton singlePlayerButton, JButton multiPlayerButton, JButton quitButton) {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        title.setBounds((frameWidth - 400) / 2, frameHeight / 6, 400, 50);
        singlePlayerButton.setBounds((frameWidth - 200) / 2, frameHeight / 2 - 100, 200, 50);
        multiPlayerButton.setBounds((frameWidth - 200) / 2, frameHeight / 2, 200, 50);
        quitButton.setBounds((frameWidth - 200) / 2, frameHeight / 2 + 100, 200, 50);
    }
}
