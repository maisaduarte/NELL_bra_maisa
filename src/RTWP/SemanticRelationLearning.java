/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RTWP;

import utilit.UtilitBD_RTWP_TOSemanticRelation;

/**
 *
 * @author MaisaDuarte
 */
public class SemanticRelationLearning {

    static int TSystem = 1;

    // TSystem == 1 -> Sistema Acoplado com promoção de 3 Contextos e 1/3 de Contextos
    // TSystem == 2 -> Sistema Não Acoplado com promoção de 3 Contextos e 1/3 de Contextos
    public void SemanticRelationLearning() {

        UtilitBD_RTWP_TOSemanticRelation UtilitBD_RTWP_TOSemanticRelation = new UtilitBD_RTWP_TOSemanticRelation();

        if (TSystem == 1) {
            System.out.println("Aprendizado Acoplado - 3 Semantic Relation Context e 1/3 de ENs Coupled");
            //utilit.UtilitBD_RTWP_TOSemanticRelation.setDATABASE_URL(utilit.UtilitBD_RTWP_TOSemanticRelation.getDATABASE_URL() + "rtwp_phd");
            utilit.UtilitBD_AllPairsDataToSemanticRelation.setDATABASE_URL(utilit.UtilitBD_AllPairsDataToSemanticRelation.getDATABASE_URL() + "allpairsdata_srelation2");
        } /*
         * else if (TSystem == 2) { System.out.println("Aprendizado Não Acoplado
         * - 3 Contextos e 1/3 de ENs");
         * utilit.UtilitBD_RTWP_TOCategory.setDATABASE_URL(utilit.UtilitBD_RTWP_TOCategory.getDATABASE_URL()
         * + "rtwp_phd_NC_3_13_WF");
         * utilit.UtilitBD_AllPairsDataToCategory.setDATABASE_URL(utilit.UtilitBD_AllPairsDataToCategory.getDATABASE_URL()
         * + "AllPairsData");
         }
         */

        //    int countIterationsIsAvaliable = 5;
        // int i = 0;

        while (true) {

            UtilitBD_RTWP_TOSemanticRelation.connect();
            UtilitBD_RTWP_TOSemanticRelation.insertIteration();
            UtilitBD_RTWP_TOSemanticRelation.disconnect();

            Learning_SemanticRelationContext.countSemanticRelationContextLearned = 0;
            Learning_CoupledEN.countCOUPLED_ENLearned = 0;

            System.out.println("-------------------Iniciando Aprendizado de Semantic Relation Context-------------------");
            CreateSemanticRelationContextThreads_ToLearning.CreateSemanticRelationContextThreads_ToLearning();
            System.out.println("-------------------Encerrado Aprendizado de Semantic Relation Context-------------------");

            System.out.println("-------------------Iniciando Aprendizado de Couple ENs-------------------");
            CreateCoupleENThreads_ToLearning.CreateCoupleENThreads_ToLearning();
            System.out.println("-------------------Encerrado Aprendizado de Couple ENs-------------------");

            if ((Learning_SemanticRelationContext.countSemanticRelationContextLearned == 0) && (Learning_EN.countENLearned == 0)) {
                break;
            } else if ((Learning_SemanticRelationContext.countSemanticRelationContextLearned == 0) && (Learning_EN.countENLearned > 0)) {
                CreateSemanticRelationContextThreads_ToLearning.CreateSemanticRelationContextThreads_ToLearning();
            }
        }
        
        
    }
}
