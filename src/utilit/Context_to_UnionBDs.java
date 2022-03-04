/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class Context_to_UnionBDs {
    String Context;
    int IDContext;
    int Occurrence;
    String side;

    public void setSide(String side) {
        this.side = side;
    }

    public String getSide() {
        return side;
    }
    

    public String getContext() {
        return Context;
    }

    public void setContext(String Context) {
        this.Context = Context;
    }

    public int getIDContext() {
        return IDContext;
    }

    public void setIDContext(int IDContext) {
        this.IDContext = IDContext;
    }

    public int getOccurrence() {
        return Occurrence;
    }

    public void setOccurrence(int Occurrence) {
        this.Occurrence = Occurrence;
    }

    public Context_to_UnionBDs(String Context, int IDContext, int Occurrence, String side) {
        this.Context = Context;
        this.IDContext = IDContext;
        this.Occurrence = Occurrence;
        this.side = side;
    }

    public Context_to_UnionBDs() {
    }

    

          
}
