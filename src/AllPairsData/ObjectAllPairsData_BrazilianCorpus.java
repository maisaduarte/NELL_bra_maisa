/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AllPairsData;

/**
 *
 * @author MaisaDuarte
 */
public class ObjectAllPairsData_BrazilianCorpus {

    String Context;
    String EN;
    int Occurrence;
    int IDEN;
    int IDContext;

    public int getIDEN() {
        return IDEN;
    }

    public void setIDEN(int IDEN) {
        this.IDEN = IDEN;
    }

    public int getIDContext() {
        return IDContext;
    }

    public void setIDContext(int IDContext) {
        this.IDContext = IDContext;
    }

    public ObjectAllPairsData_BrazilianCorpus(String Context, String EN, int Occurrence, int IDEN, int IDContext) {
        this.Context = Context;
        this.EN = EN;
        this.Occurrence = Occurrence;
        this.IDEN = IDEN;
        this.IDContext = IDContext;
    }
   
    public ObjectAllPairsData_BrazilianCorpus() {
    }

    public ObjectAllPairsData_BrazilianCorpus(String Context, String EN, int Occurrence) {
        this.Context = Context;
        this.EN = EN;
        this.Occurrence = Occurrence;
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

    public int getOccurrence() {
        return Occurrence;
    }

    public void setOccurrence(int Occurrence) {
        this.Occurrence = Occurrence;
    }
}
