/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RTWP;

/**
 *
 * @author MaisaDuarte
 */
public class ResultOfAllPairsDataWithNegativeExamples implements Comparable<ResultOfAllPairsDataWithNegativeExamples> {

    int idPairs;
    int codToLearn;
    String ToLearn;
    String side;
    String tag;
    int positive_occurrence;
    int positive_co_occurrence;
    int negative_occurrence;
    int negative_co_occurrence;
    double positive_confidence;
    double negative_confidence;
    double confidence;

    public ResultOfAllPairsDataWithNegativeExamples(int idPairs, int codToLearn, String ToLearn, String side, String tag, int positive_occurrence, int positive_co_occurrence, int negative_occurrence, int negative_co_occurrence, double positive_confidence, double negative_confidence, double confidence) {
        this.idPairs = idPairs;
        this.codToLearn = codToLearn;
        this.ToLearn = ToLearn;
        this.side = side;
        this.tag = tag;
        this.positive_occurrence = positive_occurrence;
        this.positive_co_occurrence = positive_co_occurrence;
        this.negative_occurrence = negative_occurrence;
        this.negative_co_occurrence = negative_co_occurrence;
        this.positive_confidence = positive_confidence;
        this.negative_confidence = negative_confidence;
        this.confidence = confidence;
    }

    public String getToLearn() {
        return ToLearn;
    }

    public void setToLearn(String ToLearn) {
        this.ToLearn = ToLearn;
    }

    public int getCodToLearn() {
        return codToLearn;
    }

    public void setCodToLearn(int codToLearn) {
        this.codToLearn = codToLearn;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public int getIdPairs() {
        return idPairs;
    }

    public void setIdPairs(int idPairs) {
        this.idPairs = idPairs;
    }

    public int getNegative_co_occurrence() {
        return negative_co_occurrence;
    }

    public void setNegative_co_occurrence(int negative_co_occurrence) {
        this.negative_co_occurrence = negative_co_occurrence;
    }

    public double getNegative_confidence() {
        return negative_confidence;
    }

    public void setNegative_confidence(double negative_confidence) {
        this.negative_confidence = negative_confidence;
    }

    public int getNegative_occurrence() {
        return negative_occurrence;
    }

    public void setNegative_occurrence(int negative_occurrence) {
        this.negative_occurrence = negative_occurrence;
    }

    public int getPositive_co_occurrence() {
        return positive_co_occurrence;
    }

    public void setPositive_co_occurrence(int positive_co_occurrence) {
        this.positive_co_occurrence = positive_co_occurrence;
    }

    public double getPositive_confidence() {
        return positive_confidence;
    }

    public void setPositive_confidence(double positive_confidence) {
        this.positive_confidence = positive_confidence;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    

    public ResultOfAllPairsDataWithNegativeExamples() {
    }

    @Override
    public int compareTo(ResultOfAllPairsDataWithNegativeExamples o) {
        if (this.confidence < o.confidence) {
            return -1;
        }
        if (this.confidence < o.confidence) {
            return 1;
        }
        return 0;
    }
}
