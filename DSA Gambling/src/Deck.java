import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        // Build deck
        for (String suit : suits) {
            for (String value : values) {
                cards.add(new Card(value, suit));
            }
        }
    }

    // Shuffle deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Draw a card from top of deck
    public Card draw() {
        if (cards.isEmpty()) {
            return new Card("None", "None");
        }
        return cards.remove(cards.size() - 1);
    }

}