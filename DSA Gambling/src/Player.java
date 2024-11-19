import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int handValue;
    private int aceCount;  // To handle Ace values (1 or 11)

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.handValue = 0;
        this.aceCount = 0;
    }

    // Add a card to the player's hand
    public void addCard(Card card) {
        hand.add(card);
        handValue += card.getNumericValue();  // Add the numeric value of the card
        if (card.isAce()) {
            aceCount++;
        }
        reduceAceValue();  // Adjust for Ace value if over 21
    }

    // Adjust the Ace value if the hand value exceeds 21
    private void reduceAceValue() {
        while (handValue > 21 && aceCount > 0) {
            handValue -= 10;  // Convert Ace from 11 to 1
            aceCount--;
        }
    }

    public int getHandValue() {
        return handValue;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public boolean isBusted() {
        return handValue > 21;
    }

    // Print the player's hand for debugging
    public void printHand() {
        System.out.println(name + "'s Hand: " + hand);
        System.out.println("Hand Value: " + handValue);
    }
}
