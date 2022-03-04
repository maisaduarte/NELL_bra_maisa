/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit.Negative;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MaisaDuarte
 */
public class Context_tobe_Negative {

    int IDContext;
    String Context;
    double confidence;
    int codCategory;
    String side;

    public Context_tobe_Negative() {
    }

    public Context_tobe_Negative(int IDContext, String Context, double confidence, int codCategory, String side) {
        this.IDContext = IDContext;
        this.Context = Context;
        this.confidence = confidence;
        this.codCategory = codCategory;
        this.side = side;
    }
   
    public int getIDContext() {
        return IDContext;
    }

    public void setIDContext(int IDContext) {
        this.IDContext = IDContext;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String Context) {
        this.Context = Context;
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

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    
    }
