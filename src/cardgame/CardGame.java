package cardgame;
import java.util.*;
import java.io.*;

public class CardGame {

    static int nofPlayers;
    private static ArrayList<Card> cards = new ArrayList<>();
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


    static void creatingPlayersDecks(int n)
    {
        for (int i = 0; i < n; i++)
        {
            playersList.add(new Player(i+1));
            decksList.add(new Deck(i+1));
        }
    }


    static int getIntInput()
    {
        int input = 0;
        try {
            input = in.nextInt();
        } catch (InputMismatchException e) {
                //System.out.println("Invalid number of players: Please enter a positive integer");
        }
        return input;
    }


    static String getStringInput()
    {
        String input = in.next();
        return input;
    }


    static boolean getNoOfPlayers(int nofplayers){
        /*
            Ensures a positive integer is typed for number of players
    */
        nofPlayers = nofplayers;

        if (nofPlayers < 1)
        {
            System.out.println("Invalid number of players: Please enter a positive integer");
        }else{
            return true;
        }
        return false;
    }

    static boolean getPath(String pathRoute){
        cards.clear();
        int nofCards = 8 * nofPlayers, value;
        boolean isFile = true, negativeNumber = false, incorrectValue = false;

        File inputFile = new File(pathRoute);

        try {
            Scanner input = new Scanner(inputFile);

            while (input.hasNextLine()) {
                value = Integer.valueOf(input.nextLine());
                if (value > 0) // can 0 be in a pack or nah
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
        else if (cards.size() == nofCards && isFile){

            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        System.out.println("pls enter number of players: ");
        if(getNoOfPlayers(getIntInput())){
            System.out.println("Enter the path of the file (location of the file): ");
            if(getPath(getStringInput())){

                creatingPlayersDecks(nofPlayers);
                cardDistribution(cards, playersList, decksList, nofPlayers);

                for (int i = 0; i < nofPlayers; i ++) {
                    Thread thread = new Thread(playersList.get(i));
                    thread.start();
                }
            }
        }
    }
}


