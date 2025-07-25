package com.fabi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
    //HashMap
    private static HashMap<String, String> storage = new HashMap<>();
    private static List<String> wordList;



    public static boolean addWords(String word1, String word2, boolean fromFile) {
        if (storage.containsKey(word1)) {
            return false;
        } else {
            storage.put(word1, word2);

            if (!fromFile) { //Add words to file IF they are NOT SAVED in the FILE already
                saveToFile(word1, word2);
            }

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

    public static void loadFromFile() { //called on start

        File wordstxt = new File("data/words.txt"); //get storage file
        Scanner reader = null;

        String line;
        String[] parts;
        String word_german;
        String word_english;

        try {
            reader = new Scanner(wordstxt);
        } catch (FileNotFoundException e) {
            System.out.println("file: words.txt not found");
        }
        
        while (reader.hasNextLine()) {
            line = reader.nextLine();
            parts = line.split(" # ", 2); //Split line into words
            word_german = parts[0];
            word_english = parts[1];

            addWords(word_german, word_english, true); //pass words into the storage
        }
    }

    public static void saveToFile(String word1, String word2) {

        File wordstxt = new File("data/words.txt"); //get storage file

        try {
            FileWriter writer = new FileWriter(wordstxt, true); //file, append? -> true
            
            writer.write(word1 + " # " + word2 + "\n");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println("Cannot write to file");
        }

    }

}
