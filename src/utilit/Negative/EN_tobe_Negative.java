/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit.Negative;

/**
 *
 * @author MaisaDuarte
 */
public class EN_tobe_Negative {

    int IDNE;
    String NE;
    double confidence;
    int codCategory;

    public int getIDNE() {
        return IDNE;
    }

    public void setIDNE(int IDNE) {
        this.IDNE = IDNE;
    }

    public String getNE() {
        return NE;
    }

    public void setNE(String NE) {
        this.NE = NE;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public int getCodCategory() {
        return codCategory;
    }

    public void setCodCategory(int codCategory) {
        this.codCategory = codCategory;
    }

    public EN_tobe_Negative() {
    }

    public EN_tobe_Negative(int IDNE, String NE, double confidence, int codCategory) {
        this.IDNE = IDNE;
        this.NE = NE;
        this.confidence = confidence;
        this.codCategory = codCategory;
    }

     
}
