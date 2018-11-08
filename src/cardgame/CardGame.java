package cardgame;
import java.util.*;
import java.io.*;

public class CardGame {

    static int nofPlayers;
    static ArrayList<Card> cards = new ArrayList<>();
    static int nofCards;
    static ArrayList<Player> playersList = new ArrayList<>(nofPlayers);
    static ArrayList<Deck> decksList = new ArrayList<>(nofPlayers);
    static boolean endGame = false;
    static int whoWon;

    public static void main(String[] args) {
        System.out.println("pls enter number of players: ");
        Scanner in = new Scanner(System.in);
        nofPlayers = in.nextInt();
        nofCards = 8 * nofPlayers;
        int value;
        boolean isFile = true, negativeNumber = false, incorrectValue = false;

        if (nofPlayers <= 0) {
            System.out.println("Invalid number of players: Please enter a positive integer");
        }
        else {
            // number of cards, index in the ArrayList, value of cards

            // isFile is true when the input file exists; false if it doesn't
            // negativenumber is true when one of the values is negative
            System.out.println("Enter the path of the file (location of the file): ");
            String pathRoute = in.next();
            File inputFile = new File(pathRoute);

            try {
                // We check if the pack is valid and enter new card objects into the cards ArrayList
                // We create the card objects by using a constructor and passing 'value' as argument

                Scanner input = new Scanner(inputFile);
                while (input.hasNextLine()) {
                    value = Integer.valueOf(input.nextLine());
                    if (value > 0) //IS 0 NEGATIVE OR POSITIVE?
                    {
                        cards.add(new Card(value));
                    }
                    else if (value <= 0){
                        negativeNumber = true;
                        break;
                    }
                }
            }
            catch (NumberFormatException e)
            {
                incorrectValue = true;
            }
            catch (Exception e)
            {
                  System.out.println("Invalid pack: " + e.toString());
                  isFile = false;
            }

            if (cards.size() == nofCards && isFile && !negativeNumber && !incorrectValue)
            {
                for (int i = 0; i < nofPlayers; i++)
                {
                    playersList.add(new Player(i+1));
                }

                for (int i = 0; i < nofPlayers; i++)
                {
                    decksList.add(new Deck(i+1));
                }

                CardDistribution(cards, playersList, decksList, nofPlayers);

                for (int i = 0; i < nofPlayers; i ++)
                {

                    Thread thread = new Thread(playersList.get(i));
                    thread.start();
                }
            }
            else if (incorrectValue)
            {
                System.out.println("Invalid pack: Pack contains incorrect value");
            }
            else if (negativeNumber)
            {
                System.out.println("Invalid pack: Your pack contains a negative denomination");
            }
            else if (cards.size() < nofCards && isFile)
            {
                System.out.println("Invalid pack: pack is too short");
            }
            else if (cards.size() > nofCards)
            {
                System.out.println("Invalid pack: pack is too long");
            }
        }
    }


    public static void CardDistribution(ArrayList<Card> cards, ArrayList<Player> players, ArrayList<Deck> decks, int n)
    {
        for (int i = 0; i < n; i++)
        {
            players.get(i).setInitialHand(cards.get(i), cards.get(i + n), cards.get(i + 2*n), cards.get(i + 3*n));
        }
        for (int i = 0; i < n; i++)
        {
            decks.get(i).setInitialHand(cards.get(4*n + i), cards.get(i + 5*n), cards.get(i + 6*n), cards.get(i + 7*n));
        }
    }

    public void createOutputFile (int nofplayers) throws IOException {
        for (int i = 0; i < nofplayers; i++){
            String fileName = "Player" + (i+1) +"_output.txt";
            BufferedWriter writer = new BufferedWriter((new FileWriter(fileName)));
        }
    }
}
