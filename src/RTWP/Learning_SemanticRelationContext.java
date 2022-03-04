/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RTWP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utilit.*;

/**
 *
 * @author MaisaDuarte
 */
public class Learning_SemanticRelationContext extends Thread {

    static int countSemanticRelationContextLearned;
    int IdCategory;

    public Learning_SemanticRelationContext(int IdCategory) {
        this.IdCategory = IdCategory;
    }

    @Override
    public void run() {
        LearningSemanticRelationContext();
    }

    public synchronized void CountSemanticRelationContext(int countContext) {
        countSemanticRelationContextLearned = countSemanticRelationContextLearned + countContext;
    }

    public static void LearningSemanticRelationContext() {
        
         UtilitBD_AllPairsDataToSemanticRelation UtilitBD_AllPairsDataToSemanticRelation = new UtilitBD_AllPairsDataToSemanticRelation();
        UtilitBD_RTWP_TOSemanticRelation UtilitBD_RTWP_TOSemanticRelation = new UtilitBD_RTWP_TOSemanticRelation();
        
        
        List<SemanticRelationCategories> SemanticRelationCategoriesList = new ArrayList<>();
        List<ENCoupleSeeds> ENCoupleList = new ArrayList<>();
        int ModMax = 3;

        //Para aprendizado de EN's
        List<TOLearnSEMANTICRELATIONContext_ConfidenceCalculated> TOLearnSEMANTICRELATIONContext_ConfidenceCalculated = new ArrayList<>();
        List<TOLearnSEMANTICRELATIONContext_CandidatesTOPromote> TOLearnSEMANTICRELATIONContext_CandidatesTOPromote = new ArrayList<>();

      // UtilitBD_RTWP_TOSemanticRelation.connect();
        UtilitBD_RTWP_TOSemanticRelation.GetSemanticRelationCategories();
        //UtilitBD_RTWP_TOSemanticRelation.disconnect();

        int countCategories = 0;

        while (SemanticRelationCategoriesList.size() > countCategories) {
            System.out.println(SemanticRelationCategoriesList.get(countCategories).getIdSemanticRelationCategories());

            //APRENDIZADO DE Context's
            System.out.println("Aprendizado de Semantic Relation Context \n");
            System.out.println("Categoria de aprendizado:"
                    + SemanticRelationCategoriesList.get(countCategories).getIdSemanticRelationCategories() + " " + SemanticRelationCategoriesList.get(countCategories).getSemanticRelationCategories() + "\n");

            //Seleciona a lista de EN para aprender Context's
            //UtilitBD_RTWP_TOSemanticRelation.connect();
            System.out.println("Selecionando lista de ENs para aprender Contexts");
            ENCoupleList = UtilitBD_RTWP_TOSemanticRelation.GetCOUPLED_ENToList(SemanticRelationCategoriesList.get(countCategories).getIdSemanticRelationCategories());
            //UtilitBD_RTWP_TOSemanticRelation.disconnect();

            //Seleciona até 100 Context's candidatos (por categoria) a serem promovidos - classificados pelo confidence "prévio" - Sem exemplos negativos
            //TEM O GROUP
            System.out.println("Selecionando ate 100 Contexts candidatos (por categoria) a serem promovidos - classificados pelo confidence");
            UtilitBD_AllPairsDataToSemanticRelation.connect();
            TOLearnSEMANTICRELATIONContext_ConfidenceCalculated = UtilitBD_AllPairsDataToSemanticRelation.GetListSEMANTICRELATIONContextCandidate(ENCoupleList);
            System.out.println("Sementes Candidatas de CONTEXTs: " + TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.size());

            //Verifica se há Contextos para serem aprendidos
            if (TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.size() > 0) {

                //Guarda Lista para saber quem são os contextos responsáveis pela promoção
                System.out.println("Guardando lista de pares responsáveis pela promoção");
                TOLearnSEMANTICRELATIONContext_CandidatesTOPromote = UtilitBD_AllPairsDataToSemanticRelation.GetListSEMANTIRELATIONContextCandidate_WithALLCOUPLE_ENsSEMANTIRELATIONContextsRelations(ENCoupleList, TOLearnSEMANTICRELATIONContext_ConfidenceCalculated);
                UtilitBD_AllPairsDataToSemanticRelation.disconnect();

                if (CategoriesLearning.TSystem == 1) {
                    //Aplicação de Exemplos Negativos - já possui banco embutido
                    System.out.println("Aplicando exemplos negativos");
                    TOLearnSEMANTICRELATIONContext_ConfidenceCalculated = UtilitBD_RTWP_TOSemanticRelation.ApplyingNegativeExamplesTOLearnContext(SemanticRelationCategoriesList.get(countCategories).getIdSemanticRelationCategories(), TOLearnSEMANTICRELATIONContext_ConfidenceCalculated);
                }

                //Calcula a confiança
                System.out.println("Calculando CONFIDENCE");
                CalculatingConfidenceTOLearnSemanticRelationContext(TOLearnSEMANTICRELATIONContext_ConfidenceCalculated);

                if (TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.size() > 0) {

                    countSemanticRelationContextLearned = countSemanticRelationContextLearned + TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.size();

                    //Ordena confidece
                    System.out.println("Ordenando CONFIDENCEs");
                    Collections.sort(TOLearnSEMANTICRELATIONContext_ConfidenceCalculated);

                    //Salva no banco as Context's e atualiza os já existentes que estão dentro de //1/3
                    System.out.println("Salvando/Atualizando no banco as promoções");
                    //UtilitBD_RTWP_TOSemanticRelation.connect();
                    UtilitBD_RTWP_TOSemanticRelation.PromotingSemanticRelationContext(SemanticRelationCategoriesList.get(countCategories).idSemanticRelationCategories, SemanticRelationCategoriesList.get(countCategories).codCategoryLeft, SemanticRelationCategoriesList.get(countCategories).codCategoryRight,
                            UtilitBD_RTWP_TOSemanticRelation.GetIteration(), ModMax, TOLearnSEMANTICRELATIONContext_ConfidenceCalculated, TOLearnSEMANTICRELATIONContext_CandidatesTOPromote);
                    //UtilitBD_RTWP_TOSemanticRelation.disconnect();
                    System.out.println("Encerradas alterações no banco \n");
                    //27/03/2012
                }
            }
            countCategories++;
        }
    }

    public static void CalculatingConfidenceTOLearnSemanticRelationContext(List<TOLearnSEMANTICRELATIONContext_ConfidenceCalculated> TOLearnSEMANTICRELATIONContext_ConfidenceCalculated) {
        int i = 0;
        double confidence = 0;
        while (TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.size() > i) {
            int Occurrence = TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getNegative_co_occurrence();
            int Co_Occurrence = TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getNegative_occurrence();
            if (Occurrence <= 0 && Co_Occurrence <= 0) {
                confidence = 0;
            } else {
                confidence = Double.valueOf(String.format("%.2f", ((Math.log10(Co_Occurrence)) + (Math.log10(Occurrence)))).replace(",", "."));
                TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).setNegative_confidence(confidence);
            }
            confidence = 0;
            confidence = TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getPositive_confidence() - TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getNegative_confidence();
            if (confidence > 0) {
                TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).setConfidence(confidence);
                System.out.println("Context: " + TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getSemanticRelationContext()
                        + "Confidence: " + confidence);
            } else {
                TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.remove(i);
                i--;
            }
            i++;
        }

    }

    public static void updateUsedInIteration(List<EN> ENList) {
        int i = 0;
        while (i < ENList.size()) {
            //UtilitBD_RTWP_TOSemanticRelation.UpdateUsedInIteration("EN", ENList.get(i).getIdEN(), UtilitBD_RTWP_TOSemanticRelation.GetIteration());
            i++;
        }
    }
}
