package cardgame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Taster {
    public static void main(String[] args) throws IOException {
        int x = 0;
        List<List<String>> lists = new ArrayList<List<String>>();
        while (x < 5) {

            for (int i = 0; i < 3; i++) {
                List<String> result = new ArrayList<String>();
                result.add("Hello");
                lists.add(result);
            }
            x++;

        }

        System.out.println(lists.get(5));

    }

//    private static BufferedWriter writer(int i) throws IOException {
//        return new BufferedWriter(new FileWriter("Hello"+i+".txt"));
//    }
//    private static void addString(BufferedWriter bw) throws IOException {
//
//        bw.write("HELLOOOO");
//        bw.newLine();
//        bw.write("dfe");
//        System.out.println("Done");
//
//
//    }
}









