package cardgame;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class CardGame {

    public static void main(String[] args) {
        System.out.println("pls enter number of players: ");
        Scanner in = new Scanner(System.in);
        int nofPlayers = in.nextInt();

        if (nofPlayers > 0) {
            // number of cards, value of cards
            int nofCards = 8 * nofPlayers, index = 0, value;
            // isFile is true when the input file exists; false if it doesn't
            // negativenumber is true when the number of players is negative
            boolean isFile = true, negativenumber = false;

            System.out.println("Enter the path of the file (location of the file): ");
            String pathroute = in.next();
            File inputFile = new File(pathroute);

            ArrayList<Card> cards = new ArrayList<>(32);

            try {
                // We check if the pack is valid and enter new card objects into the cards ArrayList
                // We create the card objects by using a constructor and passing 'value' as argument
                Scanner input = new Scanner(inputFile);
                while (input.hasNextLine()) {
                    value = input.nextInt();
                    if (value > 0)
                    {
                        Card cardInstance = new Card(value);
                        cards.set(index, cardInstance);
                        index++;
                    }
                    else{
                        System.out.println("Invalid pack: Your pack contains a negative denomination");
                        negativenumber = true;
                        break;
                    }
                }

                // all the code yall
                //CardDistribution(cards, nofCards, nofPlayers);


            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Invalid pack: Pack is too long");
                System.out.println(e.toString());
            }
            catch (Exception e)
            {
                System.out.println("Invalid pack: The input file was not found");
                System.out.println(e.toString());
                isFile = false;
            }
            if (isFile && index != nofCards && !negativenumber)
            {
                System.out.println("Invalid pack: Pack is too short");
            }
        } else if(nofPlayers == 0 ) {
            System.out.println("Game has ended; no players playing");
        } else{
            System.out.println("Invalid number of players: Please enter a positive number");
        }
    }


    public static void CardDistribution(ArrayList<Card> cards, int numCards, int n){
//        int player[] = new int[n];
//        int playerCards [] = new int[4];
//
//        for (int index = 0; index <=noofCards; index++){
//            for (int i = 0; player[i] < 4; i++){
//
//            }
//        }
    }
}
