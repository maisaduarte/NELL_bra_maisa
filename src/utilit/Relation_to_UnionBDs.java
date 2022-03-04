/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class Relation_to_UnionBDs {

    int idEN;
    int idContext;
    int occurrence;

    public int getIdEN() {
        return idEN;
    }

    public void setIdEN(int idEN) {
        this.idEN = idEN;
    }

    public int getIdContext() {
        return idContext;
    }

    public void setIdContext(int idContext) {
        this.idContext = idContext;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    public Relation_to_UnionBDs() {
    }

    public Relation_to_UnionBDs(int idEN, int idContext, int occurrence) {
        this.idEN = idEN;
        this.idContext = idContext;
        this.occurrence = occurrence;
    }
}
