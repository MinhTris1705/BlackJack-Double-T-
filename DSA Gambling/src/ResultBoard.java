import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

public class ResultBoard {
    public ResultBoard(){
        JFrame frame = new JFrame("Game Result: ");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

    }

    public ResultBoard(String message, BlackJackGame blackJackGame) {
    }

    private void rankPlayers(ArrayList<Player> players){
        int n = players.size();
        for(int i = 0; i < n - 1; i++){
            int maxIndex = i;
            for (int j= i + 1; j < n ; j++){
                int handValueI = players.get(maxIndex).getHandValue();
                int handValueJ = players.get(j).getHandValue();

                if(players.get(maxIndex).isBusted()){
                    handValueI = -1;
                }
                if(players.get(j).isBusted()){
                    handValueJ =-1;
                }

                if (handValueJ> handValueI){
                    maxIndex =j;
                }
            }

            Player temp= players.get(maxIndex);
            players.set(maxIndex, players.get(i));
            players.set(i,temp);
        }
    }
}
