package cardgame;

import java.util.ArrayList;

public class Taster {
    public static void main(String[] args){
        ArrayList<Card> List = new  ArrayList<Card>();
        Card c1 = new Card(1);
        Card c2 = new Card(82);
        Card c3 = new Card(67);
        Card c4 = new Card(5);
        Card c5 = new Card(32);
        Card c6 = new Card(23);

        List.add(c1);
        List.add(c2);
        List.add(c3);
        List.add(c4);
        List.add(c5);
        List.add(c6);

//        for (int i = 0; i < List.size(); i++){
//            System.out.println("Number: " + List.get(i));
//
//        }
//
//            int NewList = List.remove(0);
//            List.add(25);
//        for (int i = 0; i < List.size(); i++){
//            System.out.println("Number: " + List.get(i) + " " +i);
//
//        }
//
//        for (int i = 0; i < List.size(); i++ ){
//            if(List.get(i).equals("21")){
//                int NewList1 = List.remove(i); // we need to apply strategy here
//                System.out.println("NewList " + NewList1);
//                System.out.println(i);
//                break;
//
//            }
//        }


    }
}
