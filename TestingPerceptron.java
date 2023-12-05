package fosalgo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class TestingPerceptron {

    public static void main(String[] args) {
        File fileModel = new File("src/fosalgo/model.txt");
        File fileTesting = new File("src/fosalgo/datatesting.txt");

        double theta = 0;
        int inSize = 0;
        int outSize = 0;

        HashMap<Character, int[]> encodeTarget = null;//encode target 
        double[][] W = null;//Model / Bobot / Weight        
        ArrayList<Data> dataTesting = null;

        //Baca model dari file    
        try {
            File file = fileModel;
            Scanner sc = new Scanner(file);
            String baris = sc.nextLine();
            inSize = Integer.parseInt(baris.split(":")[1]);//size of input 

            baris = sc.nextLine();
            outSize = Integer.parseInt(baris.split(":")[1]);//size of output

            baris = sc.nextLine();
            theta = Double.parseDouble(baris.split(":")[1]);

            sc.nextLine();//skip line CHARACTER:

            //encode target       
            encodeTarget = new HashMap<>();//encode target 
            for (int j = 0; j < outSize; j++) {
                baris = sc.nextLine();
                String[] kolom = baris.split(":");
                char huruf = kolom[0].charAt(0);
                String[] sTarget = kolom[1].split(";");
                int[] target = new int[sTarget.length];
                for (int k = 0; k < sTarget.length; k++) {
                    target[k] = Integer.parseInt(sTarget[k]);
                }
                encodeTarget.put(huruf, target);
            }

            sc.nextLine();//skip line WEIGHTS:

            W = new double[inSize][outSize];//W = weights = model hasil latih
            for (int i = 0; i < inSize; i++) {
                baris = sc.nextLine();
                String[] kolom = baris.split(";");
                for (int j = 0; j < outSize; j++) {
                    W[i][j] = Double.parseDouble(kolom[j]);
                }
                System.out.println(Arrays.toString(W[i]));
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }//baca MODEL Selesai

        //baca data testing dari file
        try {
            File file = fileTesting;
            Scanner sc = new Scanner(file);
            dataTesting = new ArrayList<>();
            while (sc.hasNextLine()) {
                String baris = sc.nextLine();
                String[] kolom = baris.split(":");
                char huruf = kolom[0].charAt(0);
                String[] sPola = kolom[1].split(";");
                //konversi sPola ke array integer dengan menambahkan konstan 1 di elemen pertama
                int[] pola = new int[sPola.length + 1];
                pola[0] = 1;//konstan 1 akan berpasangan dengan bias
                int k = 1;
                for (int i = 0; i < sPola.length; i++) {
                    pola[k] = Integer.parseInt(sPola[i]);
                    k++;
                }
                Data data = new Data(huruf, pola);
                dataTesting.add(data);
            }//end of while
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }//end of baca datatesting dari file
        
        //TESTING PERCEPTRON-----------------------------------_----------------
        if (W != null && dataTesting != null) {
            int t = 0;
            for (Data d : dataTesting) {
                int[] X = d.pola;
                char hurufTarget = d.huruf;
                if (X.length == W.length) {
                     System.out.println("---------------------------------------------------------------------");
                    System.out.println("Test-" + (t++));
                    System.out.println("Pola                      : " + Arrays.toString(X));
                    System.out.println("Target Testing            : " + hurufTarget);
                    
                    //SUMMATION
                    //Hitung net
                    double[] net = new double[outSize];
                    for (int i = 0; i < inSize; i++) {
                        int Xi = X[i];
                        for (int j = 0; j < outSize; j++) {
                            double Wij = W[i][j];
                            double XiWij = Xi * Wij;
                            net[j] += XiWij;
                        }
                    }//end of hitung net
                    
                    //ACTIVATION
                    int[] y = new int[outSize];
                    for (int j = 0; j < outSize; j++) {
                        if (net[j] > theta) {
                            y[j] = 1;
                        } else if (net[j] >= -theta) {
                            y[j] = 0;
                        } else {
                            y[j] = -1;
                        }
                    }
                    
                    //RECOGNIZE
                    for (Character c : encodeTarget.keySet()) {
                        int[] target = encodeTarget.get(c);
                        int equals = 0;
                        for (int j = 0; j < outSize; j++) {
                            if (target[j] != y[j]) {
                                break;
                            } else {
                                equals++;
                            }
                        }
                        if (equals == outSize) {
                            System.out.println("Hasil Recognize Perceptron: " + c);
                            System.out.print("Kesimpulan                : ");
                            if(c.equals((Character)hurufTarget)){
                                System.out.println("TRUE");
                            }else{
                                System.out.println("FALSE");
                            }
                            break;
                        }
                    }
                    
                }//end of if (X.length == W.length) {
            }
        }
    }
}
