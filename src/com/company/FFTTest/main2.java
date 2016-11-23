package com.company.FFTTest;

/**
 * Created by user on 23.11.16.
 */
public class main2 {

    public static void main(String[] args) {
        int[] histogram = new int[13];

        for (int i = 0; i < 13; i++) {
            histogram[i] = i;
        }

        System.out.println("Histogram of rolls:" );
        printHistogram(histogram);
    }


    public static void printHistogram(int[] array) {
        for (int range = 0; range < array.length; range++) {
            String label = range + " : ";
            System.out.println(label + convertToStars(array[range]));
        }
    }

    public static String convertToStars(int num) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < num; j++) {
            builder.append('*');
        }
        return builder.toString();
    }

}
