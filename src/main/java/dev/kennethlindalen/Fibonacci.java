package dev.kennethlindalen;

public class Fibonacci {
    public static void main(String[] args){

    }

//    Iterativ metode(Anbefalt):
    public static int iterativFib(int n){
        if (n<2){
            return n;
        }
        int a = 0;
        int b = 1;
        int c = a + b;

        while (--n > 1){
            a = b;
            b = c;
            c = a + b;
        }
        return c;
    }
//    Rekursiv metode:
    public static int rekursivFib(int n){
        // Base case
        if (n<2){
            return n;
        }
        return rekursivFib(n-1) + rekursivFib(n-2);
    }
}
