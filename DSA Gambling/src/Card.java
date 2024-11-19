public class Card {
    private String value;
    private String suit;

    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    public String getValue() {
        return value;
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

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}
