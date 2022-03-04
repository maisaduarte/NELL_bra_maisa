/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilit;

/**
 *
 * @author Maisa Duarte
 */
public class ExtStopWords {

    private String word;
    private int idWord;

    public ExtStopWords(String word, int idWord) {
        this.word = word;
        this.idWord = idWord;
    }

    public ExtStopWords() {
    }

    public int getIdWord() {
        return idWord;
    }

    public void setIdWord(int idWord) {
        this.idWord = idWord;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
