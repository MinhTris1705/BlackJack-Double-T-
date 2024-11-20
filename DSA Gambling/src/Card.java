import javax.swing.*;
import java.awt.*;

public class Card {
    private String value;
    private String suit;
    private Image image;

    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
        this.image = loadImage(value, suit);  // Load the image for the card based on its value and suit
    }

    public String getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    // Method to get the numeric value of the card
    public int getNumericValue() {
        if ("JQK".contains(value)) {
            return 10;  // Jack, Queen, King all are valued at 10
        } else if (value.equals("A")) {
            return 11;  // Ace initially counts as 11
        } else {
            return Integer.parseInt(value);  // Numeric cards (2-10)
        }
    }

    // Checks if the card is an Ace
    public boolean isAce() {
        return value.equals("A");
    }

    // Load the image for the card based on its value and suit
    private Image loadImage(String value, String suit) {
        String suitAbbreviation = getSuitAbbreviation(suit);  // Get the suit abbreviation (C, D, H, S)
        String imageName = value + "-" + suitAbbreviation + ".png";  // Create the image name, e.g., "2-C.png"
        // Use getClass().getResource() to load the image
        ImageIcon icon = new ImageIcon(getClass().getResource("cards/" + imageName));  // Load the image from the resources folder
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

    // Return the image of the card
    public Image getImage() {
        return image;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}
