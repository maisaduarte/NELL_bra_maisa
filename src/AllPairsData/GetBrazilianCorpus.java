/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AllPairsData;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import parser.Main;
import parser.PagesParser;
import utilit.UtilitBD_AllPairsDataToCategory;

/**
 *
 * @author MaisaDuarte
 */
public class GetBrazilianCorpus {

    public static void main(String[] args) {
        /*
         * String reg1M = "é um"; String reg1F = "é uma";
         *
         * String reg2M = "é o"; String reg2F = "é a" ;
         *
         * String reg3Sing = "tal como"; String reg3Plu = "tais como";
         *
         * String regHelpGrande = "grande"; String regHelpPequenoM = "pequeno";
         * String regHelpPequenoF = "pequena"; String regHelpBonitoM = "bonito";
         * String regHelpFeioM = "feio"; String regHelpBonitoF = "bonita";
         * String regHelpFeioF = "feia"; String regHelpBem = "bem"; String
         * regHelpPouco = "pouco";
         *
         *
         * String regCat1CityCidade = "cidade"; String regCat1CityMetropole =
         * "metrópole"; String regCat1CityCidadezinha = "cidadezinha";
         *
         *
         *
         * String regNameAtriz = "atriz"; String regNameAtor = "ator";
         */

        GetSentence();
    }

    public static void GetSentence() {
        String file = "D:\\UFSCar\\RTWP\\Doutorado\\Corpus\\conc (é uma).txt";
        List SentenceList = ReadTXTtoList(file);
        clearPhraseAndSelectENAndContextAndSaveInDB(SentenceList, "é uma", "ENL_CR");

        UtilitBD_AllPairsDataToCategory.ToDoBKP("allpairsdata_braziliancorpus2", "D:\\UFSCar\\RTWP\\Doutorado\\Corpus\\", "_E_UMA");

    }

    public static List ReadTXTtoList(String file) {
        FileInputStream in = null;
        List ListSentences = new ArrayList(); //Lista de sentenças de uma página
        try {
            in = new FileInputStream(file);
            String line = "";
            BufferedInputStream BR = null;
            BR = new BufferedInputStream(in);
            //Scanner scan = new Scanner(new File(file));
            CharsetMatch cm = null;
            CharsetDetector cd = new CharsetDetector();
            cd.setText(BR);
            cm = cd.detect();
            Scanner scan = new Scanner(BR, cm.getName());
            scan.useDelimiter("[\n]+");
            //scan.useDelimiter("[\t\n\r]+");
            int lineStart = 0;
            while (scan.hasNext()) {
                line = scan.next();
                lineStart++;
                //System.out.println(lineStart);
                if (lineStart >= 4) {
                    if (!line.isEmpty()) {
                        line = line.substring(line.indexOf(" "));
                        line = line.substring(line.indexOf(" "));
                        line = ClearSentences.clearSentencePunctuation(line);
                        line = ClearSentences.RemoveDuplicateBlanks(line);
                        line = line.trim();
                        ListSentences.add(line);
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(" Aqui 12");
            Logger.getLogger(GetBrazilianCorpus.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(GetBrazilianCorpus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ListSentences;
    }

    public static void clearPhraseAndSelectENAndContextAndSaveInDB(List ListOfSentence, String SentenceKey, String LR) {

        List ListOfContext = new ArrayList();
        List<ObjectOf2Strings> ListOfENContext = new ArrayList<>();
        int i = 0;
        int y = 0;
        String Context;
        String EN;
        String side = "";

        if (LR.equalsIgnoreCase("ENL_CR")) {
            side = "L";
        } else if (LR.equalsIgnoreCase("ENR_CL")) {
            side = "R";
        }

        UtilitBD_AllPairsDataToCategory UtilitBD_AllPairsDataToCategory = new UtilitBD_AllPairsDataToCategory();

        while (ListOfSentence.size() > i) {

            Context = ListOfSentence.get(i).toString().substring(ListOfSentence.get(i).toString().indexOf("< " + SentenceKey + " >") + ("< " + SentenceKey + " >").length());
            Context = SentenceKey + " " + Context;
            Context = ClearSentences.RemoveDuplicateBlanks(Context);
            Context = Context.trim();
            ListOfContext = GetContext(ListOfContext, Context, LR);

            System.out.println("---------------------Início Sentença---------------------");
            System.out.println(ListOfSentence.get(i).toString());
            System.out.println(Context);

            if (ListOfContext.size() > 0) {

                EN = ListOfSentence.get(i).toString().substring(0, ListOfSentence.get(i).toString().indexOf("< " + SentenceKey + " >"));
                EN = EN.trim();
                EN = GetEN(EN, LR);
                EN = GetEN(EN, LR);
                System.out.println(EN);
                System.out.println("---------------------Fim Sentença---------------------");

                if ((EN.length() > 0) && (ListOfContext.size() > 0)) {
                    y = 0;
                    while (ListOfContext.size() > y) {
                        ListOfENContext.add(new ObjectOf2Strings(EN, ListOfContext.get(y).toString()));
                        y++;
                    }
                    if ((ListOfENContext.size() >= 100) || (ListOfSentence.size() == (i + 1))) {
                        int x = 0;
                        try {
                            UtilitBD_AllPairsDataToCategory.connect();
                            while (ListOfENContext.size() > x) {
                                UtilitBD_AllPairsDataToCategory.ResolveDB_Update_OR_Insert(ListOfENContext.get(x).Context.toString(),
                                        side, ListOfENContext.get(x).EN.toString(),/*
                                         * tag
                                         */ null, 1);
                                x++;
                            }
                        } catch (Exception e) {
                        } finally {
                            ListOfENContext.clear();
                            UtilitBD_AllPairsDataToCategory.disconnect();
                            System.gc();
                        }
                    }
                } else {
                    System.out.println("---------------------Faltou EN!!!---------------------");
                }
            } else {
                System.out.println("---------------------Faltou Contexto!!!---------------------");
            }
            ListOfContext.clear();
            i++;
        }
    }

    public static List GetContext(List ListOfContext, String Context, String LR) {

        Context = ClearSentences.clearSentencePunctuation(Context);
        Context = ClearSentences.RemoveDuplicateBlanks(Context);
        if (!Context.isEmpty()) {
            if (LR.equalsIgnoreCase("ENL_CR")) {
                Context = ClearSentences.CutPunctuation_toEN_CutRight(Context);
                Context = ClearSentences.RemoveDuplicateBlanks(Context);
                if (!Context.isEmpty()) {
                    String SplitContext[] = Context.split(" ");
                    int y = 0;
                    String ContextTemp = "";
                    if (SplitContext.length > 1) {
                        ContextTemp = ContextTemp + " " + SplitContext[y];
                        y = 1;
                        while ((y < SplitContext.length) && (y < 5)) {
                            ContextTemp = ContextTemp + " " + SplitContext[y];
                            ContextTemp = ClearSentences.RemoveDuplicateBlanks(ContextTemp);
                            ListOfContext.add(ContextTemp.trim());
                            y++;
                        }
                    }
                }
            } else if (LR.equalsIgnoreCase("ENR_CL")) {
                Context = ClearSentences.CutPunctuation_toEN_CutLeft(Context);
                Context = Context.trim();
                Context = ClearSentences.RemoveDuplicateBlanks(Context);
                if (!Context.isEmpty()) {
                    String SplitContext[] = Context.split(" ");
                    int y = SplitContext.length;
                    String ContextTemp = "";
                    if (SplitContext.length > 1) {
                        ContextTemp = ContextTemp + " " + SplitContext[y];
                        y--;
                        while (y >= 0) {
                            ContextTemp = SplitContext[y] + " " + ContextTemp;
                            ContextTemp = ClearSentences.RemoveDuplicateBlanks(ContextTemp);
                            ListOfContext.add(ContextTemp.trim());
                            y--;
                        }
                    }
                }
            }
        }
        return ListOfContext;
    }

    public static String GetEN(String EN, String LR) {

        if (LR.equalsIgnoreCase("ENR_CL")) {
            EN = ClearSentences.CutPunctuation_toEN_CutRight(EN);
        } else if (LR.equalsIgnoreCase("ENL_CR")) {
            EN = ClearSentences.CutPunctuation_toEN_CutLeft(EN);
        }

        EN = ClearSentences.RemoveDuplicateBlanks(EN);

        AllPairsData_New_ToCartegoryLearning.ListStopWords = PagesParser.ReadTXTtoListSIMPLE(Main.StopWords);
        Boolean IsName = IsName(EN);
        EN = ClearSentences.RemoveDuplicateBlanks(EN);
        EN = (AllPairsData_New_ToCartegoryLearning.RemoveSTWInEnd(EN, IsName)).trim();

        return EN;
    }

    

    public static String CutPunctuation_ToLeft(String line) {

        int idPt1 = line.indexOf("!");
        if (idPt1 > -1) {
            line = line.substring(idPt1 + 1);
        }
        int idPt2 = line.indexOf(".");

        if (idPt2 > -1) {
            line = line.substring(idPt2 + 1);
        }

        int idPt3 = line.indexOf("?");
        if (idPt3 > -1) {
            line = line.substring(idPt3 + 1);
        }

        int idPt4 = line.indexOf(":");
        if (idPt4 > -1) {
            line = line.substring(idPt4 + 1);
        }

        line = ClearSentences.RemoveDuplicateBlanks(line);
        line = line.trim();
        return line;
    }

    public static String CutPunctuation_ToRight(String line) {

        int idPt1 = line.indexOf("!");
        if (idPt1 > -1) {
            line = line.substring(0, idPt1);
        }

        int idPt2 = line.indexOf(".");
        if (idPt2 > -1) {
            line = line.substring(0, idPt2);
        }

        int idPt3 = line.indexOf("?");
        if (idPt3 > -1) {
            line = line.substring(0, idPt3);
        }
        int idPt4 = line.indexOf(":");
        if (idPt4 > -1) {
            line = line.substring(0, idPt4);
        }

        line = ClearSentences.RemoveDuplicateBlanks(line);
        line = line.trim();
        return line.trim();
    }

    public static boolean IsName(String IsName) {
        Pattern pattern = Pattern.compile("([A-ZA-ZÉÚÍÓÁÈÙÌÒÀÕÃÑÊÛÎÔÂËYÜÏÖÄ])");
        Matcher matcher = pattern.matcher(IsName);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
}
