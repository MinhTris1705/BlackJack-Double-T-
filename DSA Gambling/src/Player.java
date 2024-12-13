import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int handValue;
    private int aceCount;  // To handle Ace values (1 or 11)
    private int rank;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.handValue = 0;
        this.aceCount = 0;
    }

    // Add a card to the player's hand
    public void addCard(Card card) throws IllegalStateException {
        hand.add(card);
        if (hand.size() > 5) {
            throw new IllegalStateException();
        }
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

    public boolean hasBlackjack() {
        if (hand.size() == 2) { // Blackjack only occurs with exactly 2 cards
            boolean hasAce = false;
            boolean hasTenValueCard = false;

            // Check each card in the hand
            for (Card card : hand) {
                if (card.isAce()) {
                    hasAce = true; // Mark if the card is an Ace
                } else if (card.getNumericValue() == 10) {
                    hasTenValueCard = true; // Mark if the card has a value of 10
                }

                // If both conditions are met, we can stop early
                if (hasAce && hasTenValueCard) {
                    return true;
                }
            }
        }
        return false; // Not a blackjack
    }
    public boolean hasDoubleAces() {
        int aceCount = 0;
        for (Card card : hand) {
            if (card.isAce()) { // Assuming isAce() checks if the card is an Ace
                aceCount++;
            }
        }
        return aceCount == 2; // Return true if there are exactly 2 Aces
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
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}