/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class TOLearnContext_CandidatesTOPromote {

    String context;
    String side;
    String EN;
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

    public TOLearnContext_CandidatesTOPromote() {
    }


    public TOLearnContext_CandidatesTOPromote(String context, String side, String EN, int occurrence) {
        this.context = context;
        this.side = side;
        this.EN = EN;
        this.occurrence = occurrence;
    }

  

}
