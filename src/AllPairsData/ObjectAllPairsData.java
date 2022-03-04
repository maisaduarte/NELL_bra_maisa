/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AllPairsData;

/**
 *
 * @author MaisaDuarte
 */
public class ObjectAllPairsData {
    int ContGeral;
    int ContEspecific;
    String side;
    String Context;
    String EN;
    String Tag;

    public int getContEspecific() {
        return ContEspecific;
    }

    public void setContEspecific(int ContEspecific) {
        this.ContEspecific = ContEspecific;
    }

    public int getContGeral() {
        return ContGeral;
    }

    public void setContGeral(int ContGeral) {
        this.ContGeral = ContGeral;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String Context) {
        this.Context = Context;
    }

    public String getEN() {
        return EN;
    }

    public void setEN(String EN) {
        this.EN = EN;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String Tag) {
        this.Tag = Tag;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public ObjectAllPairsData(int ContGeral, int ContEspecific, String side, String Context, String EN, String Tag) {
        this.ContGeral = ContGeral;
        this.ContEspecific = ContEspecific;
        this.side = side;
        this.Context = Context;
        this.EN = EN;
        this.Tag = Tag;
    }

    public ObjectAllPairsData() {
    }

   }
