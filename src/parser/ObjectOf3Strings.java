/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author MaisaDuarte
 */
public class ObjectOf3Strings {

    String UnLabeled;
    String Labeled;
    String LabeledCleaned;

    public String getLabeled() {
        return Labeled;
    }

    public void setLabeled(String Labeled) {
        this.Labeled = Labeled;
    }

    public String getLabeledCleaned() {
        return LabeledCleaned;
    }

    public void setLabeledCleaned(String LabeledCleaned) {
        this.LabeledCleaned = LabeledCleaned;
    }

    public String getUnLabeled() {
        return UnLabeled;
    }

    public void setUnLabeled(String UnLabeled) {
        this.UnLabeled = UnLabeled;
    }

    public ObjectOf3Strings() {
    }

    public ObjectOf3Strings(String UnLabeled, String Labeled, String LabeledCleaned) {
        this.UnLabeled = UnLabeled;
        this.Labeled = Labeled;
        this.LabeledCleaned = LabeledCleaned;
    }


}
