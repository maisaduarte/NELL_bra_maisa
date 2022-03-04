/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RTWP;

/**
 *
 * @author MaisaDuarte
 */
public class Categories {

    public Categories() {
    }
    int idCategory;
    String Category;
    String IsNotNegativeFor;
    boolean isName;

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public void setIsNotNegativeFor(String IsNotNegativeFor) {
        this.IsNotNegativeFor = IsNotNegativeFor;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

   
    public String getCategory() {
        return Category;
    }

    public String getIsNotNegativeFor() {
        return IsNotNegativeFor;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public boolean isIsName() {
        return isName;
    }

    public void setIsName(boolean isName) {
        this.isName = isName;
    }

    public Categories(int idCategory, String Category, String IsNotNegativeFor, boolean isName) {
        this.idCategory = idCategory;
        this.Category = Category;
        this.IsNotNegativeFor = IsNotNegativeFor;
        this.isName = isName;
    }

}
