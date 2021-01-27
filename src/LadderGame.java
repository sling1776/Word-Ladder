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
/*
* FindOneOffs: Will find words by changing the word itself and then checking the dictionary for each combination.
* @author Spencer Lingwall
* */
    public void findOneOffs( WordInfo wordInfo, MyQueue<WordInfo> queue, ArrayList<String> dictionary){
        char[] wordChars = wordInfo.word.toCharArray();
        int len = wordChars.length;
        for(int i = 0; i< len; i++){
            char temp = wordChars[i];
            String word = new String(wordChars);
            for(char character = 'a'; character <= 'z'; character++){
                wordChars[i] = character;
                word = new String(wordChars);
                for(String dicWord: dictionary) {
                    if (dicWord.equals(word)) {
                        WordInfo newWord = new WordInfo(word,
                                wordInfo.moves + 1,
                                wordInfo.history + " " + word);
                        queue.addEnd(newWord);
                        dictionary.remove(dicWord);
                        break;
                    }
                }
            }
            wordChars[i] = temp;
        }
    }

/*
* FindOneOffs_dicSearch: Will find words by searching the dictionary once for all words that are one off.
*       This one appears to be 16- 17 times faster than the other.
* @author Spencer Lingwall
* */
    public void findOneOffs_dicSearch( WordInfo wordInfo, MyQueue<WordInfo> queue, ArrayList<String> dictionary){
        char[] startWord = wordInfo.word.toCharArray();
        for (int j = 0; j< dictionary.size(); j++) {
            String word = dictionary.get(j);
            char[] checkWord = word.toCharArray();
            int offCount = 0;
            for (int i = 0; i < startWord.length; i++){
                if(startWord[i] != checkWord[i]){
                    offCount++;
                }
            }
            if(offCount==1){
                //String Method: See also Line 152
//                WordInfo newWord = new WordInfo(word,
//                        wordInfo.moves + 1,
//                        wordInfo.history + " " + word);
                //Pointer Method
                WordInfo newWord = new WordInfo(word, wordInfo.moves+1, wordInfo);

                queue.addEnd(newWord);
                dictionary.remove(word);
            }
        }
    }


    public void listWords(int numWords, int lenWords){
        ArrayList<String> list = allList[lenWords];
        for(int i = 0; i< numWords; i++){
            System.out.println(list.get(i));
        }
    }


    public void play(String a, String b) {
        MyQueue<WordInfo> q  = new MyQueue<WordInfo>();
        if (a.length() != b.length()){
            System.out.println("Words are not the same length");
            return;
         }
        if (a.length()  >= MaxWordSize) return;
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> l = allList[a.length()];
        list = (ArrayList<String>)l.clone();
        boolean ainDictionary = false;
        boolean binDictionary = false;
        for(int i = 0; i< list.size(); i++){
            if (list.get(i).equals(a)){
                ainDictionary = true;
            }
            if (list.get(i).equals(b)){
                binDictionary = true;
            }
            if(ainDictionary && binDictionary){
                break;
            }
        }
        if(!ainDictionary){
            System.out.println("Word: \"" + a + "\" Not found in dictionary.\n");
            return;
        }
        if(!binDictionary){
            System.out.println("Word: \"" + b + "\" Not found in dictionary.\n");
            return;
        }
        System.out.println("Seeking a solution from " + a + " ->" + b + " Size of List " + list.size());


        /*
        * To The Reader: From here on is my solution to the word ladder problem. You will notice many commented
        * out lines of code. this is to enable a testing of efficiency. I believe I currently have the most efficient
        * way that I know of to do this problem un-commented. To test it yourself you will need to comment out the
        * current line that is used and un-comment the desired line.
        * @author Spencer Lingwall
        * */

        //Test: Work Backwards: 139 & 147 or 140 & 148
        q.addEnd(new WordInfo(a,0,a));
//        q.addEnd(new WordInfo(b,0,b)); // attempt to work backwards-- See also line 148

        int enqueues = 0;
        while(!q.isEmpty()){
            MyQueue<WordInfo>.Node word = q.pop();
//            System.out.println(word.value.toString()); //This line is for debugging. It displays every step in process

            if(word.value.word.equals(b)){
//            if(word.value.word.equals(a)){

                //Test: How the history works:
                // String Method: See also line 75
//                System.out.println(word.value.finalResult());
                //Pointer Method:
                System.out.println(word.value.finalResult(true));
                System.out.println("Enqueues: " + enqueues + "\n");
                break;
            }
            //Test: changing the word vs searching the dictionary
//            findOneOffs(word.value, q, list); //This one is significantly slower.
            findOneOffs_dicSearch(word.value, q, list);
            enqueues ++;
        }
        if(q.isEmpty()){
            System.out.println("No Solution for " + a + " -> " + b + " : Exhausted all possibilities.\n");
        }
      
    }


    public void play(int len) {
       if (len >= MaxWordSize) return;
        ArrayList<String> list = allList[len];
        String a = list.get(random.nextInt(list.size()));
        String b = list.get(random.nextInt(list.size()));
        play(a, b);

    }

}
