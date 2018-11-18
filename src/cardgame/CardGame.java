package cardgame;
import java.util.*;
import java.io.*;

public class CardGame {

    // Creating our global variables
    static int nofPlayers;
    static ArrayList<Card> cards = new ArrayList<>();
    static ArrayList<Player> playersList = new ArrayList<>();
    static ArrayList<Deck> decksList = new ArrayList<>();
    static boolean endGame = false;
    static int whoWon;
    static int rounds = 0;
    static Scanner in = new Scanner(System.in);



    /**
     * This method distributes the pack between the payers first and then
     * the decks using the setInitialHand method from Player class.
     * It does this in a round robin fashion.
     * @param cards this is the pack that is given
     * @param players the list of players
     * @param decks the list of decks
     * @param n the number of players it needs to iterate over
     */

    static void cardDistribution(ArrayList<Card> cards, ArrayList<Player> players, ArrayList<Deck> decks, int n)
    {
        // iterates through each player and deck
        for (int i = 0; i < n; i++)
        {
            // difference between each card in pack that is handed out is n (nofplayers)
            players.get(i).setInitialHand(cards.get(i), cards.get(i + n), cards.get(i + 2*n), cards.get(i + 3*n));
            decks.get(i).setInitialHand(cards.get(4*n + i), cards.get(i + 5*n), cards.get(i + 6*n), cards.get(i + 7*n));
        }
    }

    /**
     * This method creates the players and decks by making their objects
     * @param n number of players is passed in which is the same as the number of decks
     */
    static void creatingPlayersDecks(int n)
    {
        for (int i = 0; i < n; i++)
        {
            playersList.add(new Player(i+1));
            decksList.add(new Deck(i+1));
        }
    }

    /**
     * This method gets user input for the number of players and catches exception is they enter
     * an invalid input
     * @return number of players if its a valid input. This is then put into getNoOfPlayers method.
     */
    static int getIntInput()
    {
        int input = 0;
        try {
//            input = in.nextInt();
            input = 3;
        } catch (InputMismatchException e) {}
        return input;
    }

    /**
     * This method gets user input for the path of pack
     * @return path of pack. This is then put into the getPath method.
     */
    static String getStringInput()
    {
//        String input = in.next();
        String input = "pack.txt";
        return input;
    }

    /**
     * Checks to see if number of players is greater than 0 and prints out error
     * message if the number of players doesn't meet the requirements
     * @param nofplayers number of players which is equal to number of decks
     * @return True if the number is a positive integer, False otherwise.
     */
    static boolean getNoOfPlayers(int nofplayers){

        nofPlayers = nofplayers;
        /*
           Ensures a positive integer is typed for number of players
        */
        if (nofPlayers < 1)
        {
            System.out.println("Invalid number of players: Please enter a positive integer");
        }else{
            return true;
        }
        return false;
    }

    /**
     * This methods takes in the path as a parameter and checks if its valid. It
     * does a few tests including checking if the pack is too long or short and if
     * the pack contains an incorrect denomination such as a negative number.
     * @param pathRoute User input of where the pack is stored
     * @return True if file passes all of these checks, False otherwise
     */

    static boolean getPath(String pathRoute){
        int nofCards = 8 * nofPlayers, value;
        boolean isFile = true, negativeNumber = false, incorrectValue = false;

        File inputFile = new File(pathRoute);
        /*
            Tries finding the file and adding each value to a cards ArrayList,
            catches if the file doesn't exist or has an incorrect value in it
         */

        try {
            Scanner input = new Scanner(inputFile);
            // Iterates through each line of the file
            while (input.hasNextLine()) {
                value = Integer.valueOf(input.nextLine());
                if (value > 0)
                {
                    cards.add(new Card(value));
                }
                else {
                    negativeNumber = true;
                    break;
                }
            }

        } catch (NumberFormatException e) {
            incorrectValue = true;
        } catch (Exception e) {
            System.out.println("Invalid pack: The system cannot find the file specified");
            isFile = false;
        }
        /*
            Many checks taking place to ensure a correct error message is printed onto
            the screen
         */
        if (incorrectValue)
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
        /*
            If it passes all of the checks above and it has the right number of cards
            and the file exists, it returns True
         */
        else if (cards.size() == nofCards && isFile)
        {
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        System.out.println("Please enter number of players: ");
        if(getNoOfPlayers(getIntInput())){
            System.out.println("Enter the path of the file (location of the file): ");
            if(getPath(getStringInput())){
                // If true for both then creates the players and distributes the cards.
                creatingPlayersDecks(nofPlayers);
                cardDistribution(cards, playersList, decksList, nofPlayers);

                /*
                    Once players and decks have an initial hand, it iterates through each player
                    and creates + starts threads.
                 */
                for (int i = 0; i < nofPlayers; i ++) {
                    Thread thread = new Thread(playersList.get(i));
                    thread.start();
                }
            }
        }
    }
}