/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RTWP;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilit.UtilitBD_AllPairsDataToSemanticRelation;
import utilit.UtilitBD_RTWP_TOSemanticRelation;

/**
 *
 * @author MaisaDuarte
 */
public class CreateCoupleENThreads_ToLearning {

    public static void CreateCoupleENThreads_ToLearning() {
        UtilitBD_AllPairsDataToSemanticRelation UtilitBD_AllPairsDataToSemanticRelation = new UtilitBD_AllPairsDataToSemanticRelation();
        UtilitBD_RTWP_TOSemanticRelation UtilitBD_RTWP_TOSemanticRelation = new UtilitBD_RTWP_TOSemanticRelation();
        UtilitBD_AllPairsDataToSemanticRelation.connect();
        UtilitBD_RTWP_TOSemanticRelation.connect();

        List<SemanticRelationCategories> SemanticRelationCategories = new ArrayList<>();
        //UtilitBD_RTWP_TOCategory.connect();
        SemanticRelationCategories = UtilitBD_RTWP_TOSemanticRelation.GetSemanticRelationCategories();

        Learning_SemanticRelationContext L_SR1 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR1.start();

        Learning_SemanticRelationContext L_SR2 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR2.start();

        Learning_SemanticRelationContext L_SR3 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR3.start();

        Learning_SemanticRelationContext L_SR4 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR4.start();

        Learning_SemanticRelationContext L_SR5 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR5.start();

        Learning_SemanticRelationContext L_SR6 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR6.start();

        Learning_SemanticRelationContext L_SR7 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR7.start();

        Learning_SemanticRelationContext L_SR8 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR8.start();

        Learning_SemanticRelationContext L_SR9 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR9.start();

        Learning_SemanticRelationContext L_SR10 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR10.start();

        Learning_SemanticRelationContext L_SR11 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR11.start();
        
        Learning_SemanticRelationContext L_SR12 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR12.start();
        
        Learning_SemanticRelationContext L_SR13 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR13.start();
        
        Learning_SemanticRelationContext L_SR14 = new Learning_SemanticRelationContext(SemanticRelationCategories.get(0).idSemanticRelationCategories);
        L_SR14.start();



        /*if (Learning_EN_Cat1.isAlive() || Learning_EN_Cat2.isAlive() 
         || Learning_EN_Cat3.isAlive() || Learning_EN_Cat4.isAlive()
         || Learning_EN_Cat5.isAlive() || Learning_EN_Cat6.isAlive()
         || Learning_EN_Cat7.isAlive() || Learning_EN_Cat8.isAlive()
         || Learning_EN_Cat9.isAlive() || Learning_EN_Cat10.isAlive()
         || Learning_EN_Cat11.isAlive() || Learning_EN_Cat12.isAlive()
         || Learning_EN_Cat14.isAlive() || Learning_EN_Cat15.isAlive()
         || Learning_EN_Cat16.isAlive()
         ){
                    
         }*/
        try {

            L_SR1.join();
            L_SR2.join();
            L_SR3.join();
            L_SR4.join();
            L_SR5.join();
            L_SR6.join();
            L_SR8.join();
            L_SR9.join();
            L_SR10.join();
            L_SR11.join();
            L_SR12.join();
            L_SR13.join();
            L_SR14.join();            

            UtilitBD_RTWP_TOSemanticRelation.disconnect();
            UtilitBD_AllPairsDataToSemanticRelation.disconnect();

        } catch (InterruptedException ex) {
            Logger.getLogger(CreateCoupleENThreads_ToLearning.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Socorro em CreateENsThreads_ToLearning");
        }
    }
}
