/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class TOLearnEN_CandidatesTOPromote {

    String EN;
    String context;
    String side;
    int occurrence;

    public String getEN() {
        return EN;
    }

    public void setEN(String EN) {
        this.EN = EN;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public TOLearnEN_CandidatesTOPromote() {
    }

    public TOLearnEN_CandidatesTOPromote(String EN, String context, String side, int occurrence) {
        this.EN = EN;
        this.context = context;
        this.side = side;
        this.occurrence = occurrence;
    }

    
}
