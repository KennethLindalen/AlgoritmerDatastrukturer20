package dev.kennethlindalen;

public class Fakultet {
    public static void main(String[] args){
        System.out.println(fakultet(5));
//        i.e. 5*4*3*2*1=120
    }

    public static int fakultet(int n){
        if(n == 1){
            return 1;
        }else{
            return n*fakultet(n-1);
        }

    }
}
