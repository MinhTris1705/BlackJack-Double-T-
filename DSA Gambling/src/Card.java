import javax.swing.*;
import java.awt.*;

public class Card {
    private String value;
    private String suit;
    private Image image;

    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
        this.image = loadImage(value, suit);
    }
    public int getNumericValue() {
        if ("JQK".contains(value)) {
            return 10;
        } else if (value.equals("A")) {
            return 11;
        } else {
            return Integer.parseInt(value);
        }
    }

    public boolean isAce() {
        return value.equals("A");
    }

    private Image loadImage(String value, String suit) {
        String suitAbbreviation = getSuitAbbreviation(suit);
        String imageName = value + "-" + suitAbbreviation + ".png";
        // Use getClass().getResource() to load the image
        ImageIcon icon = new ImageIcon(getClass().getResource("cards/" + imageName));
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Error loading image: " + imageName);
        }
        return icon.getImage();
    }
    private String getSuitAbbreviation(String suit) {
        switch (suit) {
            case "Clubs":
                return "C";
            case "Diamonds":
                return "D";
            case "Hearts":
                return "H";
            case "Spades":
                return "S";
            default:
                return "";
        }
    }

    public Image getImage() {
        return image;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}