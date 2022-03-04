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
public class GetBrazilianCorpus_Thread extends Thread {

    private String FileToExtract;
    private String seed;
    private String side;
    private String StringBKP;
    //static String path = "allpairsdata_braziliancorpus2";
    static String BDtoBKP = "D:\\UFSCar\\Doutorado\\RTWP\\Doutorado\\CorpusToThreads\\";
    private String NameBDThread;

    public String getNameBDThread() {
        return NameBDThread;
    }

    public void setNameBDThread(String NameBDThread) {
        this.NameBDThread = NameBDThread;
    }

    public String getFileToExtract() {
        return FileToExtract;
    }

    public void setFileToExtract(String FileToExtract) {
        this.FileToExtract = FileToExtract;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getStringBKP() {
        return StringBKP;
    }

    public void setStringBKP(String StringBKP) {
        this.StringBKP = StringBKP;
    }

    public GetBrazilianCorpus_Thread() {
    }

    @Override
    public void run() {
        System.out.println(FileToExtract);
        List SentenceList = ReadTXTtoList(FileToExtract);
        clearPhraseAndSelectENAndContextAndSaveInDB(SentenceList, seed, side);
        //UtilitBD_AllPairsDataToCategory.ToDoBKP(BDtoBKP, path, StringBKP);
        //     UtilitBD_AllPairsDataToCategory.ToDoBKP(BDtoBKP, path, StringBKP);
    }

    public List ReadTXTtoList(String file) {
        FileInputStream in = null;
        List ListSentences = new ArrayList(); //Lista de sentenças de uma página
        try {
            in = new FileInputStream(file);
            String line = "";
            BufferedInputStream BR = null;
            BR = new BufferedInputStream(in);
            //Scanner scan = new Scanner(new File(FileToExtract));
            CharsetMatch cm = null;
            CharsetDetector cd = new CharsetDetector();
            cd.setText(BR);
            cm = cd.detect();
            Scanner scan = new Scanner(BR, "UTF-8");
            System.out.println("Lendo arquivo " + FileToExtract + " para vetor");
            scan.useDelimiter("[\n\r]+");
            //scan.useDelimiter("[\t\n\r]+");
            int lineStart = 0;
            while (scan.hasNext()) {
                line = scan.next();
                lineStart++;
                // System.out.println(line);
                if (lineStart >= 4) {
                    if (!line.isEmpty()) {
                        line = line.substring(line.indexOf(" "));
                        line = line.substring(line.indexOf(" "));
                        line = ClearSentences.clearSentencePunctuation(line);
                        line = RemoveDuplicateBlanks(line);
                        line = line.trim();
                        ListSentences.add(line);
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(" Aqui 12");
            Logger.getLogger(GetBrazilianCorpus_Thread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(GetBrazilianCorpus_Thread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ListSentences;
    }

    public void clearPhraseAndSelectENAndContextAndSaveInDB(List ListOfSentence, String SentenceKey, String LR) {

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
        UtilitBD_AllPairsDataToCategory.setDATABASE_URL(NameBDThread);
        while (ListOfSentence.size() > i) {

            Context = ListOfSentence.get(i).toString().substring(ListOfSentence.get(i).toString().indexOf("< " + SentenceKey + " >") + ("< " + SentenceKey + " >").length());
            Context = SentenceKey + " " + Context;
            Context = RemoveDuplicateBlanks(Context);
            Context = Context.trim();
            ListOfContext = GetContext(ListOfContext, Context, LR);

            System.out.println("---------------------Início Sentença---------------------");
            System.out.println(ListOfSentence.get(i).toString());
            System.out.println(Context);

            if (ListOfContext.size() > 0) {

                EN = ListOfSentence.get(i).toString().substring(0, ListOfSentence.get(i).toString().indexOf("< " + SentenceKey + " >"));
                EN = RemoveDuplicateBlanks(EN);
                EN = EN.trim();
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

    public List GetContext(List ListOfContext, String Context, String LR) {

        Context = ClearSentences.clearSentencePunctuation(Context);
        Context = RemoveDuplicateBlanks(Context);
        if (!Context.isEmpty()) {
            if (LR.equalsIgnoreCase("ENL_CR")) {
                Context = ClearSentences.CutPunctuation_toEN_CutRight(Context);
                Context = RemoveDuplicateBlanks(Context);
                if (!Context.isEmpty()) {
                    String SplitContext[] = Context.split(" ");
                    int y = 0;
                    String ContextTemp = "";
                    if (SplitContext.length > 1) {
                        ContextTemp = ContextTemp + " " + SplitContext[y];
                        y = 1;
                        while ((y < SplitContext.length) && (y < 5)) {
                            ContextTemp = ContextTemp + " " + SplitContext[y];
                            ContextTemp = RemoveDuplicateBlanks(ContextTemp);
                            ListOfContext.add(ContextTemp.trim());
                            y++;
                        }
                    }
                }
            } else if (LR.equalsIgnoreCase("ENR_CL")) {
                Context = ClearSentences.CutPunctuation_toEN_CutLeft(Context);
                Context = Context.trim();
                Context = RemoveDuplicateBlanks(Context);
                if (!Context.isEmpty()) {
                    String SplitContext[] = Context.split(" ");
                    int y = SplitContext.length;
                    String ContextTemp = "";
                    if (SplitContext.length > 1) {
                        ContextTemp = ContextTemp + " " + SplitContext[y];
                        y--;
                        while (y >= 0) {
                            ContextTemp = SplitContext[y] + " " + ContextTemp;
                            ContextTemp = RemoveDuplicateBlanks(ContextTemp);
                            ListOfContext.add(ContextTemp.trim());
                            y--;
                        }
                    }
                }
            }
        }
        return ListOfContext;
    }

    public String GetEN(String EN, String LR) {

        if (LR.equalsIgnoreCase("ENR_CL")) {
            EN = ClearSentences.CutPunctuation_toEN_CutRight(EN);
        } else if (LR.equalsIgnoreCase("ENL_CR")) {
            EN = ClearSentences.CutPunctuation_toEN_CutLeft(EN);
        }

        EN = RemoveDuplicateBlanks(EN);

        AllPairsData_New_ToCartegoryLearning.ListStopWords = PagesParser.ReadTXTtoListSIMPLE(Main.StopWords);
        Boolean IsName = IsName(EN);
        EN = RemoveDuplicateBlanks(EN);
        EN = (AllPairsData_New_ToCartegoryLearning.RemoveSTWInEnd(EN, IsName)).trim();
        
        if (EN.length() < 2){
            EN = "";
        }

        return EN;
    }

    public String RemoveDuplicateBlanks(String str) {
        String patternStr = "\\s+";
        String replaceStr = " ";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll(replaceStr).trim();
    }

    public String CutPunctuation_ToLeft(String line) {

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

        line = RemoveDuplicateBlanks(line);
        line = line.trim();
        return line;
    }

    public String CutPunctuation_ToRight(String line) {

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

        line = RemoveDuplicateBlanks(line);
        line = line.trim();
        return line.trim();
    }

    public boolean IsName(String IsName) {
        Pattern pattern = Pattern.compile("([A-ZA-ZÉÚÍÓÁÈÙÌÒÀÕÃÑÊÛÎÔÂËYÜÏÖÄ])");
        Matcher matcher = pattern.matcher(IsName);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
}
