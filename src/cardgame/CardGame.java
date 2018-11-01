package cardgame;
import java.util.Scanner;
import java.io.*;

public class CardGame {
    public static void main(String[] args) {
        System.out.println("pls enter number of players: ");
        Scanner in = new Scanner(System.in);
        int nofPlayers = in.nextInt();
        if (nofPlayers > 0){
            int nofCards = 8 * nofPlayers, index = 0, value;
            boolean isFile = true;
            System.out.println("Enter the path of the file (location of the file): ");
            String pathroute = in.next();
            boolean negativenumber = false;
            File inputFile = new File(pathroute);
            int [] cards = new int [nofCards];
            try {
                // Here we check if the pack is valid and enter the values of the cards into cards array
                Scanner input = new Scanner(inputFile);
                while (input.hasNextLine()) {
                    value = input.nextInt();
                    if (value > 0)
                    {
                        cards[index] = value;
                        index++;
                    }
                    else{
                        System.out.println("Your pack contains a negative denomination");
                        negativenumber = true;
                        break;
                    }


                }
//            CardDistribution(cards,nofCards,nofPlayers);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Invalid pack: Pack is too long");
                System.out.println(e.toString());
            }
            catch (Exception e)
            {
                System.out.println("The input file was not found");
                System.out.println(e.toString());
                isFile = false;
            }
            if (isFile && index != nofCards && !negativenumber)
            {
                System.out.println("Invalid pack: Pack is too short");
            }
        }else if(nofPlayers == 0 ) {
            System.out.println("Game has ended. No players playing.");
        }else{
            System.out.println("Please enter a positive number");
        }
        //hello this is a try




    }

    private static void CardDistribution(int cards[], int noofCards, int n){
        int player[] = new int[n];
        int playerCards [] = new int[4];

        for (int index = 0; index <=noofCards; index++){
            for (int i = 0; player[i] < 4; i++){

            }

        }

    }

}
