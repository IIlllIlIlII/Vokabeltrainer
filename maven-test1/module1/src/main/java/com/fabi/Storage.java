package com.fabi;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
    //HashMap
    private static HashMap<String, String> storage = new HashMap<>();
    private static List<String> wordList;



    public static boolean addWords(String word1, String word2) {
        if (storage.containsKey(word1)) {
            return false;
        } else {
            storage.put(word1, word2);
            return true;   
        }
    }


    public static String getRandomWord() {
        wordList = new ArrayList<>(storage.keySet());

        if (wordList.isEmpty()) {
            return null;
        } else {
            Random random = new Random();
            int i = random.nextInt(0, wordList.size());
            return wordList.get(i);
        }
    }

    
    public static String getSolution(String word1) {
        return storage.get(word1);
        }


}
