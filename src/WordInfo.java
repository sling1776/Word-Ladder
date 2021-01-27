public class WordInfo {
    public String word;
    public int moves;
    public String history;
    public WordInfo parent;

    public WordInfo(String word, int moves, String history){
        this.word = word;
        this.moves = moves;
        this.history = history;
    }

    public WordInfo(String word, int moves, WordInfo parent){
        this.word = word;
        this.moves = moves;
        this.parent = parent;
    }

    public String toString(){
        return "Word " + word    + " Moves " +moves  + " History [" + history + "]";
    }

    public String finalResult(){ return "Path: " + history + " " + "Moves: " + moves;}


    /////////////These are for the pointer method rather than the string method.
    public String finalResult(boolean parent){
        return getParent();
    }
     public String getParent(){
        if(this.parent == null){
            return this.word;
        }
        return this.parent.getParent() + " " + this.word;
     }
}


