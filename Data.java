package fosalgo;

import java.util.Arrays;

public class Data {
    char huruf;
    int[] pola;
    int[] target;

    public Data(char huruf, int[] pola) {
        this.huruf = huruf;
        this.pola = pola;
    }
    
    public String toString() {
        return "{character: " + huruf + "; pola: " + Arrays.toString(pola) + "; target: " + Arrays.toString(target) + "}\n";
    }
}
