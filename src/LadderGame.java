import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.util.ArrayList;

public class LadderGame {
    static int MaxWordSize = 15;
    ArrayList<String>[] allList;  // Array of ArrayLists of words of each length.
    Random random;

    public LadderGame(String file) {
        random = new Random();
        allList = new ArrayList[MaxWordSize];
        for (int i = 0; i < MaxWordSize; i++)
            allList[i] = new ArrayList<String>();

        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNext()) {
                String word = reader.next();
                if (word.length()< MaxWordSize) allList[word.length()].add(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> findOneOffs(String word){
        ArrayList<String> words = new ArrayList<String>();
        char[] charWord = word.toCharArray();
        for (char character:charWord) {
            char temp = character;
            //change every character and check for validity
            for (character = 'a'; character < 'z'; character++) {
                if (searchAllList(Arrays.toString(charWord))){ //if word in dictionary(valid)
                    words.add(Arrays.toString(charWord));           //add it to the words list
                }
            }
            character = temp;
        }
        return words;
    }


    public ArrayList<String> findOneOffs(ArrayList<String> listOfWords){
        ArrayList<String> words = new ArrayList<String>();
        for (String word: listOfWords) {
            char[] charWord = word.toCharArray();
            for (char character:charWord) {
                char temp = character;
                //change every character and check for validity
                for (character = 'a'; character < 'z'; character++) {
                    if (searchAllList(Arrays.toString(charWord))){ //if word in dictionary(valid)
                        words.add(Arrays.toString(charWord));           //add it to the words list
                    }
                }
                character = temp;
            }
        }
        return words;
    }


    public boolean searchAllList(String inquiry){
        int len = inquiry.length();
        for (String word:allList[len]) {
            if(word.equals(inquiry)){
                return true;
            }
        }
        return false;
    }


    public void play(String a, String b) {
        //MyQueue<WordInfo> q  = 
        if (a.length() != b.length()){
            System.out.println("Words are not the same length");
            return;
         }
        if (a.length()  >= MaxWordSize) return;
        ArrayList list = new ArrayList();
        ArrayList<String> l = allList[a.length()];
        list = (ArrayList) l.clone();
        System.out.println("Seeking a solution from " + a + " ->" + b + " Size of List " + list.size());
        
        // Solve the word ladder problem
      
    }


    public void play(int len) {
       if (len >= MaxWordSize) return;
        ArrayList<String> list = allList[len];
        String a = list.get(random.nextInt(list.size()));
        String b = list.get(random.nextInt(list.size()));
        play(a, b);

    }

}
