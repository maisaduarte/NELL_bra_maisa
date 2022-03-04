/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class TOLearnCOUPLED_EN_CandidatesTOPromote {

    String ENLeft;
    String ENRight;
    String SemanticRelationContext;
    int occurrence;

    public String getENLeft() {
        return ENLeft;
    }

    public void setENLeft(String ENLeft) {
        this.ENLeft = ENLeft;
    }

    public String getENRight() {
        return ENRight;
    }

    public void setENRight(String ENRight) {
        this.ENRight = ENRight;
    }

    public String getSemanticRelationContext() {
        return SemanticRelationContext;
    }

    public void setSemanticRelationContext(String SemanticRelationContext) {
        this.SemanticRelationContext = SemanticRelationContext;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    public TOLearnCOUPLED_EN_CandidatesTOPromote() {
    }

    public TOLearnCOUPLED_EN_CandidatesTOPromote(String ENLeft, String ENRight, String SemanticRelationContext, int occurrence) {
        this.ENLeft = ENLeft;
        this.ENRight = ENRight;
        this.SemanticRelationContext = SemanticRelationContext;
        this.occurrence = occurrence;
    }  
    
}
