package dev.kennethlindalen;

public class SelectionSort {
    public static void main(String[] args) {

    }

    //  Iterativ metode:
    public static void IterativSelectionSort(int[] tab) {
        for (int i = 0; i < tab.length; i++) {
            int minpos = i;
            for (int j = i + 1; j < tab.length; j++)
                if (tab[j] < tab[minpos]) minpos = j;
            swap(tab, i, minpos);
        }
    }

    //    Drivermetode:
    public static void RekurivSelectionSort(int[] tab){
        RekurivSelectionSort(tab, 0);
    }

    //    Rekursiv metode:
    public static void RekurivSelectionSort(int[] tab, int startpos) {
        // Base case
        if (startpos >= tab.length - 1) {
            return;
        }
        int minpos = startpos;
        for (int i = startpos + 1; i < tab.length; i++)
            if (tab[i] < tab[minpos]) minpos = i;
        swap(tab, startpos, minpos);
        RekurivSelectionSort(tab, startpos + 1);
    }


    public static void swap(int[] t, int i, int j) {
        int tmp = t[i];
        t[i] = t[j];
        t[j] = tmp;
    }


}
