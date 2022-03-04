/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class EN_to_UnionBDs {
    
    String EN;
    int IDEN;
    int Occurrence;

    public String getEN() {
        return EN;
    }

    public void setEN(String EN) {
        this.EN = EN;
    }

    public int getIDEN() {
        return IDEN;
    }

    public void setIDEN(int IDEN) {
        this.IDEN = IDEN;
    }

    public int getOccurrence() {
        return Occurrence;
    }

    public void setOccurrence(int Occurrence) {
        this.Occurrence = Occurrence;
    }

    public EN_to_UnionBDs(String EN, int IDEN, int Occurrence) {
        this.EN = EN;
        this.IDEN = IDEN;
        this.Occurrence = Occurrence;
    }

    public EN_to_UnionBDs() {
    }
    
    

}
