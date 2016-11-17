package com.company;

import info.debatty.java.stringsimilarity.JaroWinkler;

/**
 * Created by user on 17.11.16.
 */
public class StringSimilarityTest {
    public static void main (String[]args) {
        JaroWinkler jw = new JaroWinkler();

        // substitution of s and t
        System.out.println(jw.similarity("начать обучение", "начать обучение"));

        // substitution of s and n
        System.out.println(jw.similarity("My string", "My ntrisg"));
    }
}
