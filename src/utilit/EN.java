/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class EN implements Comparable<EN> {

    int idEN;
    String EN;
    int positive_occurrence;
    int codCategory;
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

    public String getEN() {
        return EN;
    }

    public void setEN(String EN) {
        this.EN = EN;
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

    public int getIdEN() {
        return idEN;
    }

    public void setIdEN(int idEN) {
        this.idEN = idEN;
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

    public void setNegative_co_occurrence(int negativeCoOccurrence) {
        this.negative_co_occurrence = negativeCoOccurrence;
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

    public int getUsedInIteration() {
        return usedInIteration;
    }

    public void setUsedInIteration(int usedInIteration) {
        this.usedInIteration = usedInIteration;
    }

    public EN(int idEN, String EN, int occurrence, int codCategory, double confidence, int coOccurrence, int negativeOccurrence, int negativeCoOccurrence, int usedInIteration, int learnedInIteration) {
        this.idEN = idEN;
        this.EN = EN;
        this.positive_occurrence = occurrence;
        this.codCategory = codCategory;
        this.confidence = confidence;
        this.positive_co_occurrence = coOccurrence;
        this.negative_occurrence = negativeOccurrence;
        this.negative_co_occurrence = negativeCoOccurrence;
        this.usedInIteration = usedInIteration;
        this.learnedInIteration = learnedInIteration;
    }

    public EN() {
    }

    @Override
    public int compareTo(EN o) {
        if (this.confidence > o.confidence) {
            return -1;
        }
        if (this.confidence < o.confidence) {
            return 1;
        }
        return 0;
    }
}
