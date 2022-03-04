/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RTWP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utilit.EN;
import utilit.TOLearnContext_CandidatesTOPromote;
import utilit.TOLearnContext_ConfidenceCalculated;
import utilit.UtilitBD_AllPairsDataToCategory;
import utilit.UtilitBD_RTWP_TOCategory;

/**
 *
 * @author MaisaDuarte
 */
public class Learning_Context extends Thread {

    static int countContextLearned;
    int IdCategory;

    public Learning_Context(int IdCategory) {
        this.IdCategory = IdCategory;
    }

    @Override
    public void run() {
        LearningCategories();
    }

    public synchronized void CountContextLearned(int countContext) {
        countContextLearned = countContextLearned + countContext;
    }

    public void LearningCategories() {

        UtilitBD_AllPairsDataToCategory UtilitBD_AllPairsDataToCategory = new UtilitBD_AllPairsDataToCategory();
        UtilitBD_RTWP_TOCategory UtilitBD_RTWP_TOCategory = new UtilitBD_RTWP_TOCategory();

        List<EN> ENList = new ArrayList<>();

        //Para aprendizado de Context's
        List<TOLearnContext_ConfidenceCalculated> TOLearnContext_ConfidenceCalculated = new ArrayList<>();
        List<TOLearnContext_CandidatesTOPromote> TOLearnContext_CandidatesTOPromote = new ArrayList<>();


        //APRENDIZADO DE Context's
        System.out.println("Aprendizado de Context \n");

        //Seleciona a lista de EN para aprender Context's
        //UtilitBD_RTWP_TOCategory.connect();
        System.out.println("Selecionando lista de ENs para aprender Contexts");
        ENList = UtilitBD_RTWP_TOCategory.GetENToList(IdCategory);
       // UtilitBD_RTWP_TOCategory.disconnect();

        //Seleciona Context's candidatos (por categoria) a serem promovidos - classificados pelo confidence "prévio" - Sem exemplos negativos
        //TEM O GROUP
        System.out.println("Selecionando ate 100 Contexts candidatos (por categoria) a serem promovidos - classificados pelo confidence");
        //UtilitBD_AllPairsDataToCategory.connect();
        TOLearnContext_ConfidenceCalculated = UtilitBD_AllPairsDataToCategory.GetListContextCandidate(ENList);
        System.out.println("Sementes Candidatas de CONTEXTs: " + TOLearnContext_ConfidenceCalculated.size());
        //UtilitBD_AllPairsDataToCategory.disconnect();

        //Verifica se há Contextos para serem aprendidos
        if (TOLearnContext_ConfidenceCalculated.size() > 0) {

            //Guarda Lista para saber quem são os contextos responsáveis pela promoção
            System.out.println("Guardando lista de pares responsáveis pela promoção");
           // UtilitBD_AllPairsDataToCategory.connect();
            TOLearnContext_CandidatesTOPromote = UtilitBD_AllPairsDataToCategory.GetListContextCandidate_WithALLENsContextsRelations(ENList, TOLearnContext_ConfidenceCalculated);
            //UtilitBD_AllPairsDataToCategory.disconnect();

            if (CategoriesLearning.TSystem == 1) {
                //Aplicação de Exemplos Negativos - já possui banco embutido
                System.out.println("Aplicando exemplos negativos");
                TOLearnContext_ConfidenceCalculated = UtilitBD_RTWP_TOCategory.ApplyingNegativeExamplesTOLearnContext_ONLY_BRAZILIAN_CORPUS(IdCategory, TOLearnContext_ConfidenceCalculated);
            }
            //Calcula a confiança
            System.out.println("Calculando CONFIDENCE");
            CalculatingConfidenceTOLearnContext(TOLearnContext_ConfidenceCalculated);

            if (TOLearnContext_ConfidenceCalculated.size() > 0) {

                CountContextLearned(TOLearnContext_ConfidenceCalculated.size());
                int i = 0;
                System.out.println("Antes de ser ordenado!");
                while (i < TOLearnContext_ConfidenceCalculated.size()) {
                    System.out.println(TOLearnContext_ConfidenceCalculated.get(i).getContext() + "," + TOLearnContext_ConfidenceCalculated.get(i).getPositive_confidence() + "," + TOLearnContext_ConfidenceCalculated.get(i).getNegative_confidence() + "," + TOLearnContext_ConfidenceCalculated.get(i).getConfidence());
                    i++;
                }

                //Ordena confidece
                System.out.println("Ordenando CONFIDENCEs");
                Collections.sort(TOLearnContext_ConfidenceCalculated);
                i = 0;
                System.out.println("Ordenado!");
                while (i < TOLearnContext_ConfidenceCalculated.size()) {
                    System.out.println(TOLearnContext_ConfidenceCalculated.get(i).getContext() + "," + TOLearnContext_ConfidenceCalculated.get(i).getPositive_confidence() + "," + TOLearnContext_ConfidenceCalculated.get(i).getNegative_confidence() + "," + TOLearnContext_ConfidenceCalculated.get(i).getConfidence());
                    i++;
                }

                //Salva no banco os Context's e atualiza os já existentes (que não são contados) até o valor de Number_Candidate_toPromote_Context
                System.out.println("Salvando/Atualizando no banco as promoções");
               // UtilitBD_RTWP_TOCategory.connect();
                UtilitBD_RTWP_TOCategory.PromotingContext(IdCategory, UtilitBD_RTWP_TOCategory.GetIteration(),
                        CategoriesLearning.Number_Candidate_toPromote_Context, TOLearnContext_ConfidenceCalculated, TOLearnContext_CandidatesTOPromote);
                //UtilitBD_RTWP_TOCategory.disconnect();
                System.out.println("Encerradas alterações no banco \n");
                //07/11/20012
            }
        }

    }

    public static void CalculatingConfidenceTOLearnContext(List<TOLearnContext_ConfidenceCalculated> TOLearnContext_ConfidenceCalculated) {
        int i = 0;
        double confidence = 0;
        while (TOLearnContext_ConfidenceCalculated.size() > i) {
            int Occurrence = TOLearnContext_ConfidenceCalculated.get(i).getNegative_co_occurrence();
            int Co_Occurrence = TOLearnContext_ConfidenceCalculated.get(i).getNegative_occurrence();
            if (Occurrence <= 0 && Co_Occurrence <= 0) {
                confidence = 0;
            } else {
                confidence = Double.valueOf(String.format("%.2f", ((Math.log10(Co_Occurrence)) * (Math.log10(Occurrence)))).replace(",", "."));
                TOLearnContext_ConfidenceCalculated.get(i).setNegative_confidence(confidence);
            }
            confidence = 0;
            confidence = TOLearnContext_ConfidenceCalculated.get(i).getPositive_confidence() - TOLearnContext_ConfidenceCalculated.get(i).getNegative_confidence();
            if (confidence > 0) {
                TOLearnContext_ConfidenceCalculated.get(i).setConfidence(confidence);
                System.out.println("Context: " + TOLearnContext_ConfidenceCalculated.get(i).getContext() + " Side "
                        + TOLearnContext_ConfidenceCalculated.get(i).getSide() + "Confidence: " + confidence);
            } else {
                TOLearnContext_ConfidenceCalculated.remove(i);
                i--;
            }
            i++;
        }

    }

    public static void updateUsedInIteration(List<EN> ENList) {
        int i = 0;
        UtilitBD_RTWP_TOCategory UtilitBD_RTWP_TOCategory = new UtilitBD_RTWP_TOCategory();
        UtilitBD_RTWP_TOCategory.connect();
        while (i < ENList.size()) {
            UtilitBD_RTWP_TOCategory.UpdateUsedInIteration("EN", ENList.get(i).getIdEN(), UtilitBD_RTWP_TOCategory.GetIteration());
            i++;
        }
        UtilitBD_RTWP_TOCategory.disconnect();
    }
}
