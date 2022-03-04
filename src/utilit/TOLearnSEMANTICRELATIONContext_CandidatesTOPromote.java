/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class TOLearnSEMANTICRELATIONContext_CandidatesTOPromote {

    String SemanticRelationContext;
    String ENLEft;
    String ENRight;
      int occurrence;

    public TOLearnSEMANTICRELATIONContext_CandidatesTOPromote() {
    }

    public TOLearnSEMANTICRELATIONContext_CandidatesTOPromote(String SemanticRelationContext, String ENLEft, String ENRight, int occurrence) {
        this.SemanticRelationContext = SemanticRelationContext;
        this.ENLEft = ENLEft;
        this.ENRight = ENRight;
        this.occurrence = occurrence;
    }


    public String getENLEft() {
        return ENLEft;
    }

    public void setENLEft(String ENLEft) {
        this.ENLEft = ENLEft;
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
}