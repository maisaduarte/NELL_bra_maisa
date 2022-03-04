/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UPClueWeb;

/**
 *
 * @author MaisaDuarte
 */
public class ObjectENContextOccurrence {

    String EN;
    String Context;
    String side;
    int Occurrence;

    public String getEN() {
        return EN;
    }

    public void setEN(String EN) {
        this.EN = EN;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String Context) {
        this.Context = Context;
    }

    public int getOccurrence() {
        return Occurrence;
    }

    public void setOccurrence(int Occurrence) {
        this.Occurrence = Occurrence;
    }

    public ObjectENContextOccurrence() {
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public ObjectENContextOccurrence(String EN, String Context, String side, int Occurrence) {
        this.EN = EN;
        this.Context = Context;
        this.side = side;
        this.Occurrence = Occurrence;
    }
}
