/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AllPairsData;

import java.io.IOException;
import parser.PagesParserTOLearningSemanticRelation;

/**
 *
 * @author MaisaDuarte
 */
public class AllPairsDataToRelationSemanticLearning {

    public static void main(String[] args) throws IOException {
        String MainDirectory = System.getProperty("user.dir");
        String pathFileLabeled = MainDirectory + "\\Result\\Labeled\\CHAVEFolha.txt";
        String pathFileUnLabeled = MainDirectory + "\\Result\\UnLabeled\\CHAVEFolha.txt";
        String pathFileRelationSeeds = MainDirectory + "\\SeedsRelation.txt";
        PagesParserTOLearningSemanticRelation.ReadDirectory(MainDirectory, pathFileLabeled, pathFileUnLabeled, pathFileRelationSeeds);
    }
}
