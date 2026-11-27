package dsa.model;

public class entry {
    public String word;
    public double zipf; // frequency
    // def in file

    public entry(String word, double zipf) {
        this.word = word;
        this.zipf = zipf;
    }

    @Override
    public String toString() {
        return word + " " + zipf;
    }
}
