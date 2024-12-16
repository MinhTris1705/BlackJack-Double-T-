import java.util.ArrayList;
public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int handValue;
    private int aceCount;
    private int rank;

    public Player(String name){
        this.name=name;
        this.hand= new ArrayList<>();
        this.handValue=0;
        this.aceCount=0;
    }

    public void addCard(Card card) {
        if(hand.size() > 5){
            return;
        }
        hand.add(card);
        handValue += card.getNumericValue();
        if (card.isAce()){
            aceCount++;
        }
        reduceAceValue();
    }

    private void reduceAceValue(){
        while(handValue > 21 && aceCount > 0){
            handValue -= 10;
            aceCount--;
        }
    }

    public boolean hasBlackjack() {
        if (hand.size() == 2) {
            boolean hasAce = false;
            boolean hasTenValueCard = false;
            for (Card card : hand) {
                if (card.isAce()) {
                    hasAce = true;
                } else if (card.getNumericValue() == 10) {
                    hasTenValueCard = true;
                }
                if (hasAce && hasTenValueCard) {
                    return true;
                }
            }
        }
        return false; // Not a blackjack
    }

    public int getHandValue(){
        return handValue;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public String getName(){
        return name;
    }

    public boolean isBusted(){
        return handValue > 21;
    }

    public void printHand(){
        System.out.println(name+ "s Hand: " + hand);
        System.out.println("Hand Value: " + handValue);
    }

    public int getRank(){
        return rank;
    }

    public void setRank(int rank){
        this.rank = rank;
    }
}
