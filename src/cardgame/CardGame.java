package cardgame;
import java.util.*;
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
            ArrayList<Card> cards = new ArrayList<>(nofCards);

            try {
                // We check if the pack is valid and enter new card objects into the cards ArrayList
                // We create the card objects by using a constructor and passing 'value' as argument



                Scanner input = new Scanner(inputFile);
                while (input.hasNextLine()) {
                    value = input.nextInt();
                    if (value > 0) //IS 0 NEGATIVE OR POSITIVE?
                    {
                        Card cardInstance = new Card(value);
                        cards.add(index, cardInstance);
                        index++;
                    }
                    else{
                        System.out.println("Invalid pack: Your pack contains a negative denomination");
                        negativenumber = true;
                        break;
                    }
                }
                if (cards.size() > nofCards){
                    throw new ArrayIndexOutOfBoundsException();
                }else if(cards.size() < nofCards) {
                    System.out.println("Invalid pack: Pack is too short");
                }


                // all the code yall
                //CardDistribution(cards, nofCards, nofPlayers);

                // creating objects players and decks

                ArrayList<Player> playersList = new ArrayList<>(nofPlayers);
                ArrayList<Deck> decksList = new ArrayList<>(nofPlayers);

                for (int i = 0; i < nofPlayers; i++)
                {
                    playersList.add(new Player(i+1));
                }

                for (int i = 0; i < nofPlayers; i++)
                {
                    decksList.add(new Deck(i+1));
                }

                CardDistribution(cards, playersList, decksList, nofPlayers);

                boolean endGame = false;

                while (!endGame)
                {
                    for (int i = 0; i < nofPlayers; i ++)
                    {
                        Player player = playersList.get(i);
                        Deck deckLeft = decksList.get(i);
                        Deck deckRight = decksList.get((i+1) % nofPlayers);
                        System.out.println();
                        endGame = player.checkHand();
                        if (endGame) {
                            System.out.println("Player " + i+1 + " wins");
                            System.out.println("Player " + i+1 + " exits");
                            break;
                        }
                        player.myAction(deckLeft, deckRight);
                        endGame = player.checkHand();
                        if (endGame) {
                            System.out.println("Player " + (i + 1) + " wins");
                            System.out.println("Player " + (i + 1) + " exits");
                            break;
                        }
                    }
                }

            }

            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Invalid pack: Pack is too long");
                System.out.println(e.toString());
            }catch (InputMismatchException e){
                System.out.println("Invalid pack: Pack contains non-integers");
            }
            catch (Exception e)
            {
                  System.out.println("Invalid pack: The input file was not found");
                  System.out.println(e.toString());
                  isFile = false;
            }
        } else if(nofPlayers == 0 ) {
            System.out.println("Game has ended; no players playing");
        } else{
            System.out.println("Invalid number of players: Please enter a positive number");
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
