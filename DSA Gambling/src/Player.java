import java.util.ArrayList;
public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int handValue;
    private int aceCount;

    public Player(String name){
        this.name=name;
        this.hand= new ArrayList<>();
        this.handValue=0;
        this.aceCount=0;
    }

    public void addCard(Card card){
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
}
