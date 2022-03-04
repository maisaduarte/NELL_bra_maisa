/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class SemanticRelationContext {

    int idSemanticRelationContext;
    String SemanticRelationContext;
    int codSemanticRelationCategory;
    String form;
    int codCategoryLeft;
    int codCategoryRight;
    int positive_occurrence;
    int positive_co_occurrence;
    int negative_occurrence;
    int negative_co_occurrence;
    double confidence;
    int usedInIteration;
    int learnedInIteration;

    public SemanticRelationContext(int idSemanticRelationContext, String SemanticRelationContext, int codSemanticRelationCategory, String form, int codCategoryLeft, int codCategoryRight, int positive_occurrence, int positive_co_occurrence, int negative_occurrence, int negative_co_occurrence, double confidence, int usedInIteration, int learnedInIteration) {
        this.idSemanticRelationContext = idSemanticRelationContext;
        this.SemanticRelationContext = SemanticRelationContext;
        this.codSemanticRelationCategory = codSemanticRelationCategory;
        this.form = form;
        this.codCategoryLeft = codCategoryLeft;
        this.codCategoryRight = codCategoryRight;
        this.positive_occurrence = positive_occurrence;
        this.positive_co_occurrence = positive_co_occurrence;
        this.negative_occurrence = negative_occurrence;
        this.negative_co_occurrence = negative_co_occurrence;
        this.confidence = confidence;
        this.usedInIteration = usedInIteration;
        this.learnedInIteration = learnedInIteration;
    }

    public String getSemanticRelationContext() {
        return SemanticRelationContext;
    }

    public void setSemanticRelationContext(String SemanticRelationContext) {
        this.SemanticRelationContext = SemanticRelationContext;
    }

    public int getCodCategoryLeft() {
        return codCategoryLeft;
    }

    public void setCodCategoryLeft(int codCategoryLeft) {
        this.codCategoryLeft = codCategoryLeft;
    }

    public int getCodCategoryRight() {
        return codCategoryRight;
    }

    public void setCodCategoryRight(int codCategoryRight) {
        this.codCategoryRight = codCategoryRight;
    }

    public int getCodSemanticRelationCategory() {
        return codSemanticRelationCategory;
    }

    public void setCodSemanticRelationCategory(int codSemanticRelationCategory) {
        this.codSemanticRelationCategory = codSemanticRelationCategory;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public int getIdSemanticRelationContext() {
        return idSemanticRelationContext;
    }

    public void setIdSemanticRelationContext(int idSemanticRelationContext) {
        this.idSemanticRelationContext = idSemanticRelationContext;
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

    public SemanticRelationContext() {
    }
    
}
