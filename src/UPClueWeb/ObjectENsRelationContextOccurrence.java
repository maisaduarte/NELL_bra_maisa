/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UPClueWeb;

/**
 *
 * @author MaisaDuarte
 */
public class ObjectENsRelationContextOccurrence {

    String EN_Left;
    String EN_Right;
    String RelationContext;
    int Occurrence;

    public ObjectENsRelationContextOccurrence(String EN_Left, String EN_Right, String RelationContext, int Occurrence) {
        this.EN_Left = EN_Left;
        this.EN_Right = EN_Right;
        this.RelationContext = RelationContext;
        this.Occurrence = Occurrence;
    }

    public String getEN_Left() {
        return EN_Left;
    }

    public void setEN_Left(String EN_Left) {
        this.EN_Left = EN_Left;
    }

    public String getEN_Right() {
        return EN_Right;
    }

    public void setEN_Right(String EN_Right) {
        this.EN_Right = EN_Right;
    }

    public String getRelationContext() {
        return RelationContext;
    }

    public void setRelationContext(String RelationContext) {
        this.RelationContext = RelationContext;
    }

    public int getOccurrence() {
        return Occurrence;
    }

    public void setOccurrence(int Occurrence) {
        this.Occurrence = Occurrence;
    }

   
}
