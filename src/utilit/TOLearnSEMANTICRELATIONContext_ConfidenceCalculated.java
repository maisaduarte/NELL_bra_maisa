/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class TOLearnSEMANTICRELATIONContext_ConfidenceCalculated implements Comparable<TOLearnSEMANTICRELATIONContext_ConfidenceCalculated> {

    
    String SemanticRelationContext;
      int positive_occurrence;
    int positive_co_occurrence;
    double positive_confidence;
    int negative_occurrence;
    int negative_co_occurrence;
    double negative_confidence;
    double confidence;

    public TOLearnSEMANTICRELATIONContext_ConfidenceCalculated(String SemanticRelationContext, int positive_occurrence, int positive_co_occurrence, double positive_confidence) {
        this.SemanticRelationContext = SemanticRelationContext;
        this.positive_occurrence = positive_occurrence;
        this.positive_co_occurrence = positive_co_occurrence;
        this.positive_confidence = positive_confidence;
    }
       
    public void setSemanticRelationContext(String SemanticRelationContext) {
        this.SemanticRelationContext = SemanticRelationContext;
    }

    public String getSemanticRelationContext() {
        return SemanticRelationContext;
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

    public TOLearnSEMANTICRELATIONContext_ConfidenceCalculated() {
    }

    public TOLearnSEMANTICRELATIONContext_ConfidenceCalculated(String SemanticRelationContext, int positive_occurrence, int positive_co_occurrence, double positive_confidence, int negative_occurrence, int negative_co_occurrence, double negative_confidence, double confidence) {
        this.SemanticRelationContext = SemanticRelationContext;
        this.positive_occurrence = positive_occurrence;
        this.positive_co_occurrence = positive_co_occurrence;
        this.positive_confidence = positive_confidence;
        this.negative_occurrence = negative_occurrence;
        this.negative_co_occurrence = negative_co_occurrence;
        this.negative_confidence = negative_confidence;
        this.confidence = confidence;
    }

   
    
    @Override
    public int compareTo(TOLearnSEMANTICRELATIONContext_ConfidenceCalculated o) {
        if (this.confidence < o.confidence) {
            return -1;
        }
        if (this.confidence < o.confidence) {
            return 1;
        }
        return 0;
    }
}
