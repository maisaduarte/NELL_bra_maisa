/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RTWP;

/**
 *
 * @author MaisaDuarte
 */
public class SemanticRelationCategories {

    int idSemanticRelationCategories;
    String SemanticRelationCategories;
    int codCategoryLeft;
    int codCategoryRight;
    boolean IsNameLeft;
    boolean IsNameRight;
    String IsNotNegativeFor;

    public boolean isIsNameLeft() {
        return IsNameLeft;
    }

    public void setIsNameLeft(boolean IsNameLeft) {
        this.IsNameLeft = IsNameLeft;
    }

    public boolean isIsNameRight() {
        return IsNameRight;
    }

    public void setIsNameRight(boolean IsNameRight) {
        this.IsNameRight = IsNameRight;
    }

   
    public String getIsNotNegativeFor() {
        return IsNotNegativeFor;
    }

    public void setIsNotNegativeFor(String IsNotNegativeFor) {
        this.IsNotNegativeFor = IsNotNegativeFor;
    }

    public String getSemanticRelationCategories() {
        return SemanticRelationCategories;
    }

    public void setSemanticRelationCategories(String SemanticRelationCategories) {
        this.SemanticRelationCategories = SemanticRelationCategories;
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

    public int getIdSemanticRelationCategories() {
        return idSemanticRelationCategories;
    }

    public void setIdSemanticRelationCategories(int idSemanticRelationCategories) {
        this.idSemanticRelationCategories = idSemanticRelationCategories;
    }

    public SemanticRelationCategories() {
    }

    public SemanticRelationCategories(int idSemanticRelationCategories, String SemanticRelationCategories, int codCategoryLeft, int codCategoryRight, boolean IsNameLeft, boolean IsNameRight, String IsNotNegativeFor) {
        this.idSemanticRelationCategories = idSemanticRelationCategories;
        this.SemanticRelationCategories = SemanticRelationCategories;
        this.codCategoryLeft = codCategoryLeft;
        this.codCategoryRight = codCategoryRight;
        this.IsNameLeft = IsNameLeft;
        this.IsNameRight = IsNameRight;
        this.IsNotNegativeFor = IsNotNegativeFor;
    }
    
}
