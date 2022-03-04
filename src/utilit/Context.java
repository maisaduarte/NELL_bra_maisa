/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class Context implements Comparable<Context> {

    int idcontext;
    String context;
    int positive_occurrence;
    int codCategory;
    String side;
    double confidence;
    int positive_co_occurrence;
    int negative_occurrence;
    int negative_co_occurrence;
    int usedInIteration;
    int learnedInIteration;
    double borderConfidence;
    boolean IsANegativeSeed;

    public boolean isIsANegativeSeed() {
        return IsANegativeSeed;
    }

    public void setIsANegativeSeed(boolean IsANegativeSeed) {
        this.IsANegativeSeed = IsANegativeSeed;
    }
   
    public double getBorderConfidence() {
        return borderConfidence;
    }

    public void setBorderConfidence(double borderConfidence) {
        this.borderConfidence = borderConfidence;
    }
       
    public Context(int idcontext, String context, int codCategory, String side, double confidence) {
        this.idcontext = idcontext;
        this.context = context;
        this.codCategory = codCategory;
        this.side = side;
        this.confidence = confidence;
    }

    public int getPositive_co_occurrence() {
        return positive_co_occurrence;
    }

    public void setPositive_co_occurrence(int positive_co_occurrence) {
        this.positive_co_occurrence = positive_co_occurrence;
    }

    public int getCodCategory() {
        return codCategory;
    }

    public void setCodCategory(int codCategory) {
        this.codCategory = codCategory;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getIdcontext() {
        return idcontext;
    }

    public void setIdcontext(int idcontext) {
        this.idcontext = idcontext;
    }

    public int getLearnedInIteration() {
        return learnedInIteration;
    }

    public void setLearnedInIteration(int learnedInIteration) {
        this.learnedInIteration = learnedInIteration;
    }

    public int getNegative_co_occurrence() {
        return negative_co_occurrence;
    }

    public void setNegative_co_occurrence(int negative_co_occurrence) {
        this.negative_co_occurrence = negative_co_occurrence;
    }

    public int getNegative_occurrence() {
        return negative_occurrence;
    }

    public void setNegative_occurrence(int negative_occurrence) {
        this.negative_occurrence = negative_occurrence;
    }

    public int getPositive_occurrence() {
        return positive_occurrence;
    }

    public void setPositive_occurrence(int positive_occurrence) {
        this.positive_occurrence = positive_occurrence;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getUsedInIteration() {
        return usedInIteration;
    }

    public void setUsedInIteration(int usedInIteration) {
        this.usedInIteration = usedInIteration;
    }

    public Context(int idcontext, String context, int occurrence, int codCategory, String side, double confidence, int co_occurrence, int negative_occurrence, int negative_co_occurrence, int usedInIteration, int learnedInIteration) {
        this.idcontext = idcontext;
        this.context = context;
        this.positive_occurrence = occurrence;
        this.codCategory = codCategory;
        this.side = side;
        this.confidence = confidence;
         this.positive_co_occurrence = co_occurrence;
        this.negative_occurrence = negative_occurrence;
        this.negative_co_occurrence = negative_co_occurrence;
        this.usedInIteration = usedInIteration;
        this.learnedInIteration = learnedInIteration;
    }

    public Context() {
    }

    @Override
    public int compareTo(Context o) {
        if (this.confidence > o.confidence) {
            return -1;
        }
        if (this.confidence < o.confidence) {
            return 1;
        }
        return 0;
    }
}
