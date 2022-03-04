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
public class Learning_CoupledEN {

    static int countCOUPLED_ENLearned;

    public static void Learning_CoupledEN() {
        UtilitBD_AllPairsDataToSemanticRelation UtilitBD_AllPairsDataToSemanticRelation = new UtilitBD_AllPairsDataToSemanticRelation();
        UtilitBD_RTWP_TOSemanticRelation UtilitBD_RTWP_TOSemanticRelation = new UtilitBD_RTWP_TOSemanticRelation();
        UtilitBD_RTWP_TOSemanticRelation.connect();
        UtilitBD_AllPairsDataToSemanticRelation.connect();


        List<SemanticRelationCategories> SemanticRelationCategoriesList = new ArrayList<>();
        List<SemanticRelationContext> SemanticRelationContextList = new ArrayList<>();
        int ModMax = 3;
        List<ExtStopWords> ExtStopWords = new ArrayList<>();

        //Para aprendizado de EN's
        List<TOLearnCOUPLED_EN_ConfidenceCalculated> TOLearnCOUPLED_EN_ConfidenceCalculated = new ArrayList<>();
        List<TOLearnCOUPLED_EN_CandidatesTOPromote> TOLearnCOUPLED_EN_CandidatesTOPromote = new ArrayList<>();

        //UtilitBD_RTWP_TOSemanticRelation.connect();
        SemanticRelationCategoriesList = UtilitBD_RTWP_TOSemanticRelation.GetSemanticRelationCategories();
        ExtStopWords = UtilitBD_RTWP_TOSemanticRelation.UpVectorStopWords(ExtStopWords);
        //UtilitBD_RTWP_TOSemanticRelation.disconnect();

        //Aqui 08/05/2012

        int countCategories = 0;

        while (SemanticRelationCategoriesList.size() > countCategories) {


            //APRENDIZADO DE COUPLED EN's
            System.out.println("Aprendizado de EN \n");
            System.out.println("Categoria de aprendizado:"
                    + SemanticRelationCategoriesList.get(countCategories).getIdSemanticRelationCategories() + " " + SemanticRelationCategoriesList.get(countCategories).getSemanticRelationCategories() + "\n");

            //Seleciona a lista de contextos para aprender EN's
            //UtilitBD_RTWP_TOSemanticRelation.connect();
            System.out.println("Selecionando lista de Contexts para aprender ENs");
            SemanticRelationContextList = UtilitBD_RTWP_TOSemanticRelation.GetSemanticRelationContextToList(SemanticRelationCategoriesList.get(countCategories).getIdSemanticRelationCategories());
            //UtilitBD_RTWP_TOSemanticRelation.disconnect();

            //Seleciona até 200 EN's candidatas (por categoria) a serem promovidas - classificadas pelo confidence "prévio" - Sem exemplos negativos
            //TEM O GROUP
            System.out.println("Selecionando ate 200 COUPLED ENs candidatas (por categoria) a serem promovidos - classificados pelo confidence");
            //utilit.UtilitBD_AllPairsDataToSemanticRelation.connect();
            TOLearnCOUPLED_EN_ConfidenceCalculated = utilit.UtilitBD_AllPairsDataToSemanticRelation.GetList_COUPLED_EN_Candidate(SemanticRelationContextList);
            System.out.println("Sementes Candidatas de COUPLED ENs: " + TOLearnCOUPLED_EN_ConfidenceCalculated.size());

            if ((SemanticRelationCategoriesList.get(countCategories).IsNameLeft == true) || ((SemanticRelationCategoriesList.get(countCategories).IsNameRight == true))) {
                RemoveRegisterIfIsNotNAME(TOLearnCOUPLED_EN_ConfidenceCalculated, ExtStopWords);
            }

            //Verifica se há ENs para serem aprendidas
            if (TOLearnCOUPLED_EN_ConfidenceCalculated.size() > 0) {

                //Guarda Lista para saber quem são os contextos responsáveis pela promoção
                System.out.println("Guardando lista de pares responsáveis pela promoção");
                TOLearnCOUPLED_EN_CandidatesTOPromote = utilit.UtilitBD_AllPairsDataToSemanticRelation.GetListENCandidate_WithALLENsContextsRelations(SemanticRelationContextList, TOLearnCOUPLED_EN_ConfidenceCalculated);
                //UtilitBD_AllPairsDataToSemanticRelation.disconnect();

                if (CategoriesLearning.TSystem == 1) {
                    //Aplicação de Exemplos Negativos - já possui banco embutido
                    System.out.println("Aplicando exemplos negativos");
                    TOLearnCOUPLED_EN_ConfidenceCalculated = UtilitBD_RTWP_TOSemanticRelation.ApplyingNegativeExamplesTOLearnCOUPLED_EN(SemanticRelationCategoriesList.get(countCategories).getIdSemanticRelationCategories(), TOLearnCOUPLED_EN_ConfidenceCalculated);
                }
                //Calcula a confiança
                System.out.println("Calculando CONFIDENCE");
                CalculatingConfidenceTOLearnCOUPLED_EN(TOLearnCOUPLED_EN_ConfidenceCalculated);

                if (TOLearnCOUPLED_EN_ConfidenceCalculated.size() > 0) {

                    countCOUPLED_ENLearned = countCOUPLED_ENLearned + TOLearnCOUPLED_EN_ConfidenceCalculated.size();

                    //Ordena confidece
                    System.out.println("Ordenando CONFIDENCEs");
                    Collections.sort(TOLearnCOUPLED_EN_ConfidenceCalculated);

                    //Salva no banco as EN's e atualiza as já existentes que estão dentro de 1/3
                    System.out.println("Salvando/Atualizando no banco as promoções");
                    //UtilitBD_RTWP_TOSemanticRelation.connect();
                    UtilitBD_RTWP_TOSemanticRelation.PromotingCOUPLED_EN(SemanticRelationCategoriesList.get(countCategories).getIdSemanticRelationCategories(),
                            SemanticRelationCategoriesList.get(countCategories).getCodCategoryLeft(), SemanticRelationCategoriesList.get(countCategories).getCodCategoryRight(),
                            UtilitBD_RTWP_TOSemanticRelation.GetIteration(), ModMax, TOLearnCOUPLED_EN_ConfidenceCalculated, TOLearnCOUPLED_EN_CandidatesTOPromote);
                    //UtilitBD_RTWP_TOSemanticRelation.disconnect();
                    System.out.println("Encerradas alterações no banco \n");
                    //15/05/2012
                }
            }
            countCategories++;
        }
        
    }

    public static void RemoveRegisterIfIsNotNAME(List<TOLearnCOUPLED_EN_ConfidenceCalculated> TOLearnCOUPLED_EN_ConfidenceCalculated, List<ExtStopWords> ExtStopWords) {
        int i = 0;
        while (i < TOLearnCOUPLED_EN_ConfidenceCalculated.size()) {
            if (IsName(TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENLeft()) == true) {
                RemoveSTWInFin(TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENLeft(), true, ExtStopWords);
            } else {
                TOLearnCOUPLED_EN_ConfidenceCalculated.remove(i);
            }
            i++;
        }
        i = 0;
        while (i < TOLearnCOUPLED_EN_ConfidenceCalculated.size()) {
            if (IsName(TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENRight()) == true) {
                RemoveSTWInFin(TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENRight(), true, ExtStopWords);
            } else {
                TOLearnCOUPLED_EN_ConfidenceCalculated.remove(i);
            }
            i++;
        }
    }

    public static boolean IsName(String IsName) {
        boolean result = false;
        char IsNameOrginal = IsName.charAt(0);
        char IsNameAltered = Character.toUpperCase(IsNameOrginal);
        if (IsNameOrginal == IsNameAltered) {
            result = true;
        } else {
            result = false;
        }
        IsName = null;
        return result;
    }

    public static String RemoveSTWInFin(String aux, boolean IsName, List<ExtStopWords> ExtStopWords) {
        String[] auxCath = aux.split(" ");
        String resp = "";
        int y = 0;
        int i = 0;
        boolean contin = true;

        while (contin) {
            if (y < auxCath.length) {
                if (SearchStopWords(auxCath[y], ExtStopWords) == true) {
                    contin = true;
                    auxCath[y] = "";
                } else if ((IsName(auxCath[y]) == false) && (IsName == true)) {
                    contin = true;
                    auxCath[y] = "";
                } else {
                    contin = false;
                }
                y++;
            } else {
                contin = false;
            }
        }

        contin = true;
        y = auxCath.length - 1;
        while (contin) {
            if (y >= 0) {
                if (SearchStopWords(auxCath[y], ExtStopWords) == true) {
                    contin = true;
                    auxCath[y] = "";
                } else if ((IsName(auxCath[y]) == false) && (IsName == true)) {
                    contin = true;
                    auxCath[y] = "";
                } else {
                    contin = false;
                }
                y--;
            } else {
                contin = false;
            }
        }

        i = 0;
        while (i < auxCath.length) {
            resp = resp + " " + auxCath[i];
            i++;
        }

        aux = null;
        auxCath = null;

        resp = resp.trim();
        return resp;
    }

    public static boolean SearchStopWords(String word, List<ExtStopWords> ExtStopWords) {
        boolean resp = false;
        int sizeVet;
        int index = 0;
        sizeVet = ExtStopWords.size();

        if (word.matches("^[0-9]*$")) { // verifica se é um número (somente números, 3M por exemplo irá passar)
            resp = true;
        } else if (sizeVet > 0) {
            while (index < sizeVet) {
                if (ExtStopWords.get(index).getWord().toString().equalsIgnoreCase(word)) {
                    resp = true;
                    index = ExtStopWords.size();
                } else {
                    index++;
                }
            }
        }
        word = null;
        return resp;
    }

    public static void CalculatingConfidenceTOLearnCOUPLED_EN(List<TOLearnCOUPLED_EN_ConfidenceCalculated> TOLearnCOUPLED_EN_ConfidenceCalculated) {
        int i = 0;
        double confidence = 0;
        while (TOLearnCOUPLED_EN_ConfidenceCalculated.size() > i) {
            int Occurrence = TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getNegative_co_occurrence();
            int Co_Occurrence = TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getNegative_occurrence();
            if (Occurrence <= 0 && Co_Occurrence <= 0) {
                confidence = 0;
            } else {
                System.out.println((Math.log10(Co_Occurrence)) + (Math.log10(Occurrence)));
                confidence = Double.valueOf(String.format("%.2f", ((Math.log10(Co_Occurrence)) + (Math.log10(Occurrence)))).replace(",", "."));
                TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).setNegative_confidence(confidence);
            }
            confidence = 0;
            confidence = TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getPositive_confidence() - TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getNegative_confidence();
            if (confidence > 0) {
                TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).setConfidence(confidence);

            } else {
                TOLearnCOUPLED_EN_ConfidenceCalculated.remove(i);
                i--;
            }
            i++;
        }
    }

    public static void updateUsedInIteration(List<Context> ContextList) {
        int i = 0;
        while (i < ContextList.size()) {
            //UtilitBD_RTWP_TOSemanticRelation.UpdateUsedInIteration("Context", ContextList.get(i).getIdcontext(), UtilitBD_RTWP_TOSemanticRelation.GetIteration());
            i++;
        }
    }
}
