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
public class Learning_EN extends Thread {

    static int countENLearned;
    int IdCategory;
    boolean isName;

    public Learning_EN(int IdCategory, boolean isName) {
        this.IdCategory = IdCategory;
        this.isName = isName;
    }

    @Override
    public void run() {
        LearningCategories();
    }

    public synchronized void CountENLearned(int countEN) {
        countENLearned = countENLearned + countEN;
    }

    public void LearningCategories() {

        UtilitBD_AllPairsDataToCategory UtilitBD_AllPairsDataToCategory = new UtilitBD_AllPairsDataToCategory();
        UtilitBD_RTWP_TOCategory UtilitBD_RTWP_TOCategory = new UtilitBD_RTWP_TOCategory();

        List<Context> ContextList = new ArrayList<>();
        List<ExtStopWords> ExtStopWords = new ArrayList<>();

        //Para aprendizado de EN's
        List<TOLearnEN_ConfidenceCalculated> TOLearnEN_ConfidenceCalculated = new ArrayList<>();
        List<TOLearnEN_CandidatesTOPromote> TOLearnEN_CandidatesTOPromote = new ArrayList<>();

        //UtilitBD_RTWP_TOCategory.connect();
        ExtStopWords = UtilitBD_RTWP_TOCategory.UpVectorStopWords(ExtStopWords);
        //UtilitBD_RTWP_TOCategory.disconnect();

        //APRENDIZADO DE EN's
        System.out.println("Aprendizado de EN \n");

        //Seleciona a lista de contextos para aprender EN's
        //qUtilitBD_RTWP_TOCategory.connect();
        System.out.println("Selecionando lista de Contexts para aprender ENs");
        ContextList = UtilitBD_RTWP_TOCategory.GetContextToList(IdCategory);
        int limit_to_getENCandidates = (UtilitBD_RTWP_TOCategory.GetIteration() * 30) + 60;
        //UtilitBD_RTWP_TOCategory.disconnect();

        //Seleciona EN's candidatas (por categoria) a serem promovidas - classificadas pelo confidence "prévio" - Sem exemplos negativos
        //TEM O GROUP
        System.out.println("Selecionando (até 30* it) + 60  candidatas (por categoria) a serem promovidos - classificados pelo confidence");
        //UtilitBD_AllPairsDataToCategory.connect();
        TOLearnEN_ConfidenceCalculated = UtilitBD_AllPairsDataToCategory.GetListENCandidate(ContextList, limit_to_getENCandidates);
        System.out.println("Sementes Candidatas de ENs: " + TOLearnEN_ConfidenceCalculated.size());
        // UtilitBD_AllPairsDataToCategory.disconnect();

        if (isName == true) {
            TOLearnEN_ConfidenceCalculated = RemoveRegisterIfIsNotNAME(TOLearnEN_ConfidenceCalculated, ExtStopWords);
        }

        //Verifica se há ENs para serem aprendidas
        if (TOLearnEN_ConfidenceCalculated.size() > 0) {

            //Guarda Lista para saber quem são os contextos responsáveis pela promoção
            System.out.println("Guardando lista de pares responsáveis pela promoção");
            //UtilitBD_AllPairsDataToCategory.connect();
            TOLearnEN_CandidatesTOPromote = UtilitBD_AllPairsDataToCategory.GetListENCandidate_WithALLENsContextsRelations(ContextList, TOLearnEN_ConfidenceCalculated);
            //UtilitBD_AllPairsDataToCategory.disconnect();

            if (CategoriesLearning.TSystem == 1) {
                //Aplicação de Exemplos Negativos - já possui banco embutido
                System.out.println("Aplicando exemplos negativos");
                TOLearnEN_ConfidenceCalculated = UtilitBD_RTWP_TOCategory.ApplyingNegativeExamplesTOLearnEN_ONLY_BRAZILIAN_CORPUS(IdCategory, TOLearnEN_ConfidenceCalculated);
            }
            //Calcula a confiança
            System.out.println("Calculando CONFIDENCE");
            CalculatingConfidenceTOLearnEN(TOLearnEN_ConfidenceCalculated);

            if (TOLearnEN_ConfidenceCalculated.size() > 0) {

                CountENLearned(TOLearnEN_ConfidenceCalculated.size());

                //Ordena confidece
                System.out.println("Ordenando CONFIDENCEs");
                Collections.sort(TOLearnEN_ConfidenceCalculated);

                //Salva no banco as EN's e atualiza as já existentes (que não são contadas) até o valor de Number_Candidate_toPromote_NE
                System.out.println("Salvando/Atualizando no banco as promoções");
                //UtilitBD_RTWP_TOCategory.connect();
                UtilitBD_RTWP_TOCategory.PromotingEN(IdCategory, UtilitBD_RTWP_TOCategory.GetIteration(),
                        CategoriesLearning.Number_Candidate_toPromote_NE, TOLearnEN_ConfidenceCalculated, TOLearnEN_CandidatesTOPromote);
                //UtilitBD_RTWP_TOCategory.disconnect();
                System.out.println("Encerradas alterações no banco \n");
                //07/11/20012
            }
        }
    }
//Corta Lista
 /*   public List<TOLearnEN_ConfidenceCalculated> CutListEN(List<TOLearnEN_ConfidenceCalculated> TOLearnEN_ConfidenceCalculated) {
     int i = TOLearnEN_ConfidenceCalculated.size() - 1;
     while (TOLearnEN_ConfidenceCalculated.size() > CategoriesLearning.CutListNegative_toLearningNE) {
     TOLearnEN_ConfidenceCalculated.remove(i);
     i--;
     }
     return TOLearnEN_ConfidenceCalculated;
     }
     */

    public List<TOLearnEN_ConfidenceCalculated> RemoveRegisterIfIsNotNAME(List<TOLearnEN_ConfidenceCalculated> TOLearnEN_ConfidenceCalculated, List<ExtStopWords> ExtStopWords) {
        int i = 0;
        while (i < TOLearnEN_ConfidenceCalculated.size()) {
            if (IsName(TOLearnEN_ConfidenceCalculated.get(i).getEN()) == true) {
                RemoveSTWInFin(TOLearnEN_ConfidenceCalculated.get(i).getEN(), true, ExtStopWords);
            } else {
                TOLearnEN_ConfidenceCalculated.remove(i);
                i--;
            }
            i++;
        }
        i = 0;
        while (i < TOLearnEN_ConfidenceCalculated.size()) {
            System.out.println(i + " = " + TOLearnEN_ConfidenceCalculated.get(i).getEN());
            i++;
        }
        return TOLearnEN_ConfidenceCalculated;
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

    public static void CalculatingConfidenceTOLearnEN(List<TOLearnEN_ConfidenceCalculated> TOLearnEN_ConfidenceCalculated) {
        int i = 0;
        double confidence = 0;
        while (TOLearnEN_ConfidenceCalculated.size() > i) {
            int Occurrence = TOLearnEN_ConfidenceCalculated.get(i).getNegative_co_occurrence();
            int Co_Occurrence = TOLearnEN_ConfidenceCalculated.get(i).getNegative_occurrence();
            if (Occurrence <= 0 && Co_Occurrence <= 0) {
                confidence = 0;
            } else {
                confidence = Double.valueOf(String.format("%.2f", ((Math.log10(Co_Occurrence)) * (Math.log10(Occurrence)))).replace(",", "."));
                TOLearnEN_ConfidenceCalculated.get(i).setNegative_confidence(confidence);
            }
            confidence = 0;
            confidence = TOLearnEN_ConfidenceCalculated.get(i).getPositive_confidence() - TOLearnEN_ConfidenceCalculated.get(i).getNegative_confidence();
            if (confidence > 0) {
                TOLearnEN_ConfidenceCalculated.get(i).setConfidence(confidence);
                System.out.println("Context: " + TOLearnEN_ConfidenceCalculated.get(i).getEN() + " Confidence: " + confidence);
            } else {
                TOLearnEN_ConfidenceCalculated.remove(i);
                i--;
            }
            i++;
        }
    }

    public static void updateUsedInIteration(List<Context> ContextList) {
        int i = 0;
        UtilitBD_RTWP_TOCategory UtilitBD_RTWP_TOCategory = new UtilitBD_RTWP_TOCategory();
        UtilitBD_RTWP_TOCategory.connect();
        while (i < ContextList.size()) {
            UtilitBD_RTWP_TOCategory.UpdateUsedInIteration("Context", ContextList.get(i).getIdcontext(), UtilitBD_RTWP_TOCategory.GetIteration());
            i++;
        }
        UtilitBD_RTWP_TOCategory.disconnect();
    }
}
