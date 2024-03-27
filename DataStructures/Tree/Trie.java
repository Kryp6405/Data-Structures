package DataStructures.Tree;

import java.util.HashMap;
import java.util.Map;

class Trie {
    private Node root;

    Trie() {
        root = new Node(); 
    }

    public void add(String word){
        Node current = root;

        for(char letter : word.toCharArray()){
            Map<Character,Node> kids = current.getChildren();

            if(kids.containsKey(letter)){
                current = kids.get(letter);
                current.incrementCount();
            } else{                       
                kids.put(letter, new Node());
                current = kids.get(letter);
            }
        }
        
        current.setEndOfWord(true);
    }

    public boolean contains(String word){
        Node current = root;
        for(char letter : word.toCharArray())
        {
            Map<Character,Node> kids = current.children;
            if(kids.containsKey(letter))
                current = kids.get(letter);
            else
                return false;
        }
        return true;
    }

    public char mostLikelyNextChar(String prefix){
        Node current = root;
        for(char letter : prefix.toCharArray()){
            Map<Character,Node> kids = current.children;
            if(kids.containsKey(letter))
                current = kids.get(letter);
            else return '_';
        }
        Map<Character,Node> kids = current.children;
        Map.Entry<Character,Node> maxEntry = null;
        for(Map.Entry<Character,Node> entry: kids.entrySet()){
            if(maxEntry==null||entry.getValue().count>maxEntry.getValue().count)
                maxEntry = entry;
        }
        if(maxEntry == null) return '_';
        return maxEntry.getKey();
    }

	class Node {
        private final Map<Character, Node> children;
        private boolean endOfWord;
        private int count;

        public Node(){
            children = new HashMap<>();
            endOfWord = false;
            count = 1;  
        }

        private Map<Character, Node> getChildren() {
            return children;
        }

        private void incrementCount(){
            count++;
        }

        private int getCount(){
            return count;
        }

        private boolean isEndOfWord() {
            return endOfWord;
        }

        private void setEndOfWord(boolean endOfWord) {
            this.endOfWord = endOfWord;
        }

        public String toString(){
            return "("+count+")";
        }
	}
}