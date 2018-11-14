package cardgame;
import java.util.*;
import java.io.*;

public class CardGame {

    static int nofPlayers;
    private static ArrayList<Card> cards = new ArrayList<>();
    static ArrayList<Player> playersList = new ArrayList<>();
    static ArrayList<Deck> decksList = new ArrayList<>();
    static Boolean[] dead = new Boolean[nofPlayers];
    static boolean endGame = false;
    static int whoWon;
    static int rounds = 0;
    static int whoWonOutputSize;
    static ArrayList<Boolean> hadTurn = new ArrayList<>();

    public static void main(String[] args) {
        //Gets input
        System.out.println("pls enter number of players: ");
        Scanner in = new Scanner(System.in);
        nofPlayers = 4;
                //in.nextInt();
        int nofCards = 8 * nofPlayers;
        int value;
        boolean isFile = true, negativeNumber = false, incorrectValue = false;
        /*
            Ensures a positive integer is typed for number of players
         */
        if (nofPlayers <= 0 ) {
            System.out.println("Invalid number of players: Please enter a positive integer");
        }
        else {
            // number of cards, index in the ArrayList, value of cards

            // isFile is true when the input file exists; false if it doesn't
            // negativenumber is true when one of the values is negative
            System.out.println("Enter the path of the file (location of the file): ");
            String pathRoute = in.next();
            File inputFile = new File("pack.txt");
                    //pathRoute);

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
                    else {
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



            // the actual game



            if (cards.size() == nofCards && isFile && !negativeNumber && !incorrectValue)
            {
                // creating players and decks
                for (int i = 0; i < nofPlayers; i++)
                {
                    playersList.add(new Player(i+1));

                }

                for (int i = 0; i < nofPlayers; i++)
                {
                    decksList.add(new Deck(i+1));
                }

                CardDistribution(cards, playersList, decksList, nofPlayers);

                // starting threads for each player
                for (int i = 0; i < nofPlayers; i ++) {
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

    /**
     * This method distributes the pack between the payers first and then
     * the decks using the setInitialHand method from Player class.
     * It does this in a round robin fashion.
     * @param cards this is the pack that is given
     * @param players the list of players
     * @param decks the list of decks
     * @param n the number of players it needs to iterate over
     */
    public static void CardDistribution(ArrayList<Card> cards, ArrayList<Player> players, ArrayList<Deck> decks, int n)
    {
        // iterates through each player and deck
        for (int i = 0; i < n; i++)
        {
            // difference between each card in pack that is handed out is n (nofplayers)
            players.get(i).setInitialHand(cards.get(i), cards.get(i + n), cards.get(i + 2*n), cards.get(i + 3*n));
            decks.get(i).setInitialHand(cards.get(4*n + i), cards.get(i + 5*n), cards.get(i + 6*n), cards.get(i + 7*n));
        }
    }




}


