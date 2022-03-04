/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

/**
 *
 * @author MaisaDuarte
 */
public class ENCoupleSeeds {

    int idENCouple;
    String ENLeft;
    String ENRight;
    int codENLeft;
    int codENRight;
    int codCategoryLeft;
    int codCategoryRight;
    int codSemanticRelationCategory;

    public int getCodENLeft() {
        return codENLeft;
    }

    public void setCodENLeft(int codENLeft) {
        this.codENLeft = codENLeft;
    }

    public int getCodENRight() {
        return codENRight;
    }

    public void setCodENRight(int codENRight) {
        this.codENRight = codENRight;
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

    public int getIdENCouple() {
        return idENCouple;
    }

    public void setIdENCouple(int idENCouple) {
        this.idENCouple = idENCouple;
    }

    public ENCoupleSeeds(int idENCouple, String ENLeft, String ENRight, int codENLeft, int codENRight, int codCategoryLeft, int codCategoryRight, int codSemanticRelationCategory) {
        this.idENCouple = idENCouple;
        this.ENLeft = ENLeft;
        this.ENRight = ENRight;
        this.codENLeft = codENLeft;
        this.codENRight = codENRight;
        this.codCategoryLeft = codCategoryLeft;
        this.codCategoryRight = codCategoryRight;
        this.codSemanticRelationCategory = codSemanticRelationCategory;
    }

    public ENCoupleSeeds() {
    }
}
