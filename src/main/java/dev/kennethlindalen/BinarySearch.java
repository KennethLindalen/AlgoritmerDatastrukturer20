package dev.kennethlindalen;

public class BinarySearch {

    public static void main(String[] args){

    }

    public static int rekursivBinarySearch(int[] list, int key) {
        int low = 0;
        int high = list.length - 1;
        return rekursivBinarySearch(list, key, low, high);
    }
    public static int rekursivBinarySearch(int[] list, int key, int low, int high) {
//        Base case
        if (low > high) {
            return - low - 1;
        }
        int mid = (low + high) / 2;
        if (key < list[mid])
            return rekursivBinarySearch(list, key, low, mid - 1);
        else if (key == list[mid])
            return mid;
        else
            return rekursivBinarySearch(list, key, mid + 1, high);
    }

}
