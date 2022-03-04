/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class TOLearnCOUPLED_EN_ConfidenceCalculated implements Comparable<TOLearnCOUPLED_EN_ConfidenceCalculated> {

    String ENLeft;
    String ENRight;
    int positive_occurrence;
    int positive_co_occurrence;
    double positive_confidence;
    int negative_occurrence;
    int negative_co_occurrence;
    double negative_confidence;
    double confidence;

    public TOLearnCOUPLED_EN_ConfidenceCalculated(String ENLeft, String ENRight, int positive_occurrence, int positive_co_occurrence, double positive_confidence) {
        this.ENLeft = ENLeft;
        this.ENRight = ENRight;
        this.positive_occurrence = positive_occurrence;
        this.positive_co_occurrence = positive_co_occurrence;
        this.positive_confidence = positive_confidence;
    }

    public String getENLeft() {
        return ENLeft;
    }

    public void setENLeft(String ENLeft) {
        this.ENLeft = ENLeft;
    }

    public String getENRight() {
        return ENRight;
    }

    public void setENRight(String ENRight) {
        this.ENRight = ENRight;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
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

    public TOLearnCOUPLED_EN_ConfidenceCalculated(String ENLeft, String ENRight, int positive_occurrence, int positive_co_occurrence, double positive_confidence, int negative_occurrence, int negative_co_occurrence, double negative_confidence, double confidence) {
        this.ENLeft = ENLeft;
        this.ENRight = ENRight;
        this.positive_occurrence = positive_occurrence;
        this.positive_co_occurrence = positive_co_occurrence;
        this.positive_confidence = positive_confidence;
        this.negative_occurrence = negative_occurrence;
        this.negative_co_occurrence = negative_co_occurrence;
        this.negative_confidence = negative_confidence;
        this.confidence = confidence;
    }


    public TOLearnCOUPLED_EN_ConfidenceCalculated() {
    }

        @Override
    public int compareTo(TOLearnCOUPLED_EN_ConfidenceCalculated o) {
        if (this.confidence > o.confidence) {
            return -1;
        }
        if (this.confidence < o.confidence) {
            return 1;
        }
        return 0;
    }
}
