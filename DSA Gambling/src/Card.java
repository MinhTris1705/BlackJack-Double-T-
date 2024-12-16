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

    public String getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public int getNumericValue() {
        if ("JQK".contains(value)) {
            return 10;  // Jack, Queen, King = 10
        } else if (value.equals("A")) {
            return 11;  // Ace initially counts as 11
        } else {
            return Integer.parseInt(value);  // Numeric cards (2-10)
        }
    }

    public boolean isAce() {
        return value.equals("A");
    }

    private Image loadImage(String value, String suit) {
        String suitAbbreviation = getSuitAbbreviation(suit);  // Get the suit (C, D, H, S)
        String imageName = value + "-" + suitAbbreviation + ".png";  // Create the image name
        // Use getClass().getResource() to load the image
        ImageIcon icon = new ImageIcon(getClass().getResource("cards/" + imageName));
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Error loading image: " + imageName);
        }
        return icon.getImage();
    }

    // Convert suit name to abbreviation
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