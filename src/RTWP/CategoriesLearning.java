/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RTWP;

import utilit.UtilitBD_RTWP_TOCategory;

/**
 *
 * @author MaisaDuarte
 */
public class CategoriesLearning {

    static int TSystem = 1;
    // TSystem == 1 -> Sistema Acoplado com promoção de 3 Contextos e 1/3 de Contextos
    // TSystem == 2 -> Sistema Não Acoplado com promoção de 3 Contextos e 1/3 de Contextos
    public static int Number_Candidate_toPromote_NE = 30;
    public static int CutListNegative_toLearningNE = 100;
    public static int Number_Candidate_toPromote_Context = 3;
    public static int CutListNegative_toLearningContext = 100;

    public void CategoriesLearning() {

        UtilitBD_RTWP_TOCategory UtilitBD_RTWP_TOCategory = new UtilitBD_RTWP_TOCategory();

        if (TSystem == 1) {
            System.out.println("Aprendizado Acoplado - 3 Contextos e 30 ENs");
            //UtilitBD_RTWP_TOCategory.setDATABASE_URL(UtilitBD_RTWP_TOCategory.getDATABASE_URL() + "rtwp_phd_IEE_Coupled");
            //utilit.UtilitBD_AllPairsDataToCategory.setDATABASE_URL("AllPairsData");
        } else if (TSystem == 2) {
            System.out.println("Aprendizado Não Acoplado - 3 Contextos e 30 ENs");
            //UtilitBD_RTWP_TOCategory.setDATABASE_URL(UtilitBD_RTWP_TOCategory.getDATABASE_URL() + "rtwp_phd_IEE_NOT_Coupled");
            // utilit.UtilitBD_AllPairsDataToCategory.setDATABASE_URL(utilit.UtilitBD_AllPairsDataToCategory.getDATABASE_URL() + "AllPairsData");
        }


        while (true) {

            UtilitBD_RTWP_TOCategory.connect();
            UtilitBD_RTWP_TOCategory.insertIteration();
            UtilitBD_RTWP_TOCategory.disconnect();

            Learning_Context.countContextLearned = 0;
            Learning_EN.countENLearned = 0;

            System.out.println("-------------------Iniciando Aprendizado de Contextos-------------------");
            CreateContextsThreads_ToLearning.CreateContextsThreads_ToLearning();
            System.out.println("-------------------Encerrado Aprendizado de Contextos-------------------");

            System.out.println("-------------------Iniciando Aprendizado de ENs-------------------");
            CreateENsThreads_ToLearning.CreateENsThreads_ToLearning();
            System.out.println("-------------------Encerrado Aprendizado de ENs-------------------");

            if ((Learning_Context.countContextLearned == 0) && (Learning_EN.countENLearned == 0)) {
                break;
            } else if ((Learning_Context.countContextLearned == 0) && (Learning_EN.countENLearned > 0)) {
                CreateContextsThreads_ToLearning.CreateContextsThreads_ToLearning();
            }
            //    i++;
        }
    }
}
