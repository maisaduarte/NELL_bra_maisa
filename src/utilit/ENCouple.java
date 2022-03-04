/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class ENCouple {

    int idENCouple;
    EN ENLeft = new EN();
    EN ENRight = new EN();
    int positive_occurrence;
    int positive_co_occurrence;
    int negative_occurrence;
    int negative_co_occurrence;
    double confidence;
    int usedInIteration;
    int learnedInIteration;

    public EN getENLeft() {
        return ENLeft;
    }

    public void setENLeft(EN ENLeft) {
        this.ENLeft = ENLeft;
    }

    public EN getENRight() {
        return ENRight;
    }

    public void setENRight(EN ENRight) {
        this.ENRight = ENRight;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public int getIdENCouple() {
        return idENCouple;
    }

    public void setIdENCouple(int idENCouple) {
        this.idENCouple = idENCouple;
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

    public int getPositive_co_occurrence() {
        return positive_co_occurrence;
    }

    public void setPositive_co_occurrence(int positive_co_occurrence) {
        this.positive_co_occurrence = positive_co_occurrence;
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

    public ENCouple() {
    }

    public ENCouple(int idENCouple, int positive_occurrence, int positive_co_occurrence, int negative_occurrence, int negative_co_occurrence, double confidence, int usedInIteration, int learnedInIteration) {
        this.idENCouple = idENCouple;
        this.positive_occurrence = positive_occurrence;
        this.positive_co_occurrence = positive_co_occurrence;
        this.negative_occurrence = negative_occurrence;
        this.negative_co_occurrence = negative_co_occurrence;
        this.confidence = confidence;
        this.usedInIteration = usedInIteration;
        this.learnedInIteration = learnedInIteration;
    }

 
}
