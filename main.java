import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;  // Import the Scanner class
import java.io.File;
import java.io.IOException;

public class main {
    static boolean[] scannedBooks;
    static boolean[] scannedLibraries;

    static int[] scoreBooks;
    static int sum;
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Scanner myObj = new Scanner(file);  // Create a Scanner object
        String tmp = myObj.nextLine();
        String[] trozos = tmp.split(" ");
        int books = Integer.parseInt(trozos[0]);
        scannedBooks = new boolean[books];

        for (int i = 0; i < books; i++) {
            scannedBooks[i] = true;
        }


        int Nlibraries = Integer.parseInt(trozos[1]);
        scannedLibraries = new boolean[Nlibraries];

        for (int i = 0; i < Nlibraries; i++) {
            scannedLibraries[i] = true;
        }
        int days = Integer.parseInt(trozos[2]);
        scoreBooks = new int[books];
        trozos = myObj.nextLine().split(" ");
        for (int i = 0; i < books; i++) {
            scoreBooks[i] = Integer.parseInt(trozos[i]);
        }

         sum = 0;
        for (int i=0; i<books; i++)
            sum += scoreBooks[i];

        sum =  sum/books;

        int libraries[][] = new int[Nlibraries][3];
        int[] librariesBooks[] = new int[Nlibraries][];
        for (int i = 0; i < Nlibraries; i++) {
            trozos = myObj.nextLine().split(" ");
            int NbooksLib = Integer.parseInt(trozos[0]);
            int singUp = Integer.parseInt(trozos[1]);
            int scanSpeed = Integer.parseInt(trozos[2]);
            libraries[i][0] = NbooksLib;
            libraries[i][1] = singUp;
            libraries[i][2] = scanSpeed;

            trozos = myObj.nextLine().split(" ");
            Integer booksLibI[] = new Integer[NbooksLib];
            for (int j = 0; j < NbooksLib; j++) {
                booksLibI[j] = Integer.parseInt(trozos[j]);
            }
            Arrays.sort(booksLibI, new Comparator<Integer>() {
                public int compare(Integer idx1, Integer idx2) {
                    return Double.compare(scoreBooks[idx2], scoreBooks[idx1]);
                }
            });

            librariesBooks[i] = new int[NbooksLib];
            int[] booksLib = new int[NbooksLib];
            for (int j = 0; j < NbooksLib; j++) {
                booksLib[j] = booksLibI[j];
            }
            System.arraycopy(booksLib, 0, librariesBooks[i], 0, booksLib.length);
        }
        int libTotal = 0;
        int bestLib = 0;
        while (bestLib != -1) {
            bestLib = -1;
            double punt = 0;
            for (int i = 0; i < Nlibraries; i++) {
                if (scannedLibraries[i]) {
                    double libPunt = puntLib(libraries[i], librariesBooks[i], days);
                    if (libPunt > punt) {
                        bestLib = i;
                        punt = libPunt;
                    }
                }
            }
            if (bestLib != -1) {
                scannedLibraries[bestLib] = false;
                int[] bestBooks = totalLibs(libraries[bestLib], librariesBooks[bestLib], days);
                days -= libraries[bestLib][1];
                System.out.println(bestLib + " " + bestBooks.length);
                for (int j = 0; j < bestBooks.length; j++) {
                    System.out.print(bestBooks[j] + " ");
                }
                System.out.println();
                libTotal++;

            }
        }
        System.out.print(libTotal);
        //printLib(libraries);
        //printLib(librariesBooks);
    }

    public static double puntLib(int[] libParams, int[] booksLib, int daysLeft) {
        double a = 0;
        int scanDays = daysLeft - libParams[1];
        if (scanDays <= 0) {
            return 0;
        }
        int totalNBooks = scanDays * libParams[2];
        int score = 0;
        int pos = 0;
        int i = 0;
        int restDays = 0;
        while (i < totalNBooks && pos < booksLib.length) {
            int tmp = booksLib[pos];
            if (scannedBooks[tmp]) {
                score += scoreBooks[booksLib[pos]];
                i++;
            }
            restDays = scanDays - (i/libParams[2]);
            a = (score*7)/(double)(restDays/8000);
            pos++;
        }
        return a;
    }

    public static int[] totalLibs(int[] libParams, int[] booksLib, int daysLeft) {
        int scanDays = daysLeft - libParams[1];
        if (scanDays <= 0) {
            return new int[0];
        }
        int totalNBooks = scanDays * libParams[2];
        int score = 0;
        int pos = 0;
        int i = 0;
        ArrayList<Integer> books = new ArrayList<Integer>();
        while (i < totalNBooks && pos < booksLib.length) {
            int tmp = booksLib[pos];
            if (scannedBooks[tmp]) {
                books.add(booksLib[pos]);
                scannedBooks[booksLib[pos]] = false;
                i++;
            }
            pos++;
        }
        Integer[] tmp = books.toArray(new Integer[books.size()]);
        int[] ret = new int[tmp.length];
        for (int j = 0; j < ret.length; j++) {
            ret[j] = tmp[j];
        }
        return ret;
    }


    public static void printLib(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(i);
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
