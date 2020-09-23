package dev.kennethlindalen;

public class TowerOfHanoi {

    public static void main(String[] args){
        int n = 4; // Number of disks
        towerOfHanoi(n, 'A', 'C', 'B');
    }
    static void towerOfHanoi(int n, char fra, char til, char mellom) {
//        Base case
        if (n == 1) {
            System.out.println("Flytt plate " + n + " fra " +  fra + " til " + til);
            return;
        }
        towerOfHanoi(n-1, fra, mellom, til);
        System.out.println("Flytt plate " + n + " fra " +  fra + " til " + til);
        towerOfHanoi(n-1, mellom, til, fra);
    }

}
