/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RTWP;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilit.UtilitBD_AllPairsDataToCategory;
import utilit.UtilitBD_AllPairsDataToSemanticRelation;
import utilit.UtilitBD_RTWP_TOSemanticRelation;

/**
 *
 * @author MaisaDuarte
 */
public class CreateSemanticRelationContextThreads_ToLearning {

    public static void CreateSemanticRelationContextThreads_ToLearning() {

        UtilitBD_AllPairsDataToSemanticRelation UtilitBD_AllPairsDataToSemanticRelation = new UtilitBD_AllPairsDataToSemanticRelation();
        UtilitBD_RTWP_TOSemanticRelation UtilitBD_RTWP_TOSemanticRelation = new UtilitBD_RTWP_TOSemanticRelation();
        UtilitBD_RTWP_TOSemanticRelation.connect();
        UtilitBD_AllPairsDataToSemanticRelation.connect();

        List<SemanticRelationCategories> SemanticRelationCategoriesList = new ArrayList<>();
        SemanticRelationCategoriesList = UtilitBD_RTWP_TOSemanticRelation.GetSemanticRelationCategories();
       
        Learning_SemanticRelationContext L1 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(0).idSemanticRelationCategories);
        L1.start();

        Learning_SemanticRelationContext L2 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(1).idSemanticRelationCategories);
        L2.start();

        Learning_SemanticRelationContext L3 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(2).idSemanticRelationCategories);
        L3.start();

        Learning_SemanticRelationContext L4 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(3).idSemanticRelationCategories);
        L4.start();

        Learning_SemanticRelationContext L5 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(4).idSemanticRelationCategories);
        L5.start();

        Learning_SemanticRelationContext L6 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(5).idSemanticRelationCategories);
        L6.start();

        Learning_SemanticRelationContext L7 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(6).idSemanticRelationCategories);
        L7.start();

        Learning_SemanticRelationContext L8 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(7).idSemanticRelationCategories);
        L8.start();

        Learning_SemanticRelationContext L9 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(8).idSemanticRelationCategories);
        L9.start();

        Learning_SemanticRelationContext L10 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(9).idSemanticRelationCategories);
        L10.start();

        Learning_SemanticRelationContext L11 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(10).idSemanticRelationCategories);
        L11.start();

        Learning_SemanticRelationContext L12 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(11).idSemanticRelationCategories);
        L12.start();

        Learning_SemanticRelationContext L13 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(12).idSemanticRelationCategories);
        L13.start();

        Learning_SemanticRelationContext L14 = new Learning_SemanticRelationContext(SemanticRelationCategoriesList.get(13).idSemanticRelationCategories);
        L14.start();


        /*if (L1.isAlive() || L2.isAlive() 
         || L3.isAlive() || L4.isAlive()
         || L5.isAlive() || L6.isAlive()
         || L7.isAlive() || L8.isAlive()
         || L9.isAlive() || L10.isAlive()
         || L11.isAlive() || L12.isAlive()
         || L14.isAlive() || L15.isAlive()
         || L16.isAlive()
         ){
                    
         }*/
        try {

            L1.join();
            L2.join();
            L3.join();
            L4.join();
            L5.join();
            L6.join();
            L7.join();
            L8.join();
            L9.join();
            L10.join();
            L11.join();
            L12.join();
            L14.join();
            
             UtilitBD_RTWP_TOSemanticRelation.disconnect();
             UtilitBD_RTWP_TOSemanticRelation.disconnect();

        } catch (InterruptedException ex) {
            Logger.getLogger(CreateSemanticRelationContextThreads_ToLearning.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Socorro em CreateSemanticRelationContextThreads_ToLearning");
        }
    }
}
