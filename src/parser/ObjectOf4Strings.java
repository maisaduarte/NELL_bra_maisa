/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author MaisaDuarte
 */
public class ObjectOf4Strings {

    String Relation;
    String pattern;
    String ENLeft;
    String ENRight;

    public ObjectOf4Strings(String Relation, String pattern, String ENLeft, String ENRight) {
        this.Relation = Relation;
        this.pattern = pattern;
        this.ENLeft = ENLeft;
        this.ENRight = ENRight;
    }

    public ObjectOf4Strings() {
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
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

    public String getRelation() {
        return Relation;
    }

    public void setRelation(String Relation) {
        this.Relation = Relation;
    }

   }
