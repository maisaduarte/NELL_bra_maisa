/*
 * To change this template, choose Tools | Templates
 * and open the template InUnLabeled the editor.
 */
package parser;

import AllPairsData.AllPairsData_New_ToCartegoryLearning;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author MaisaDuarte
 */
public class PagesParserTOLearningSemanticRelation extends Thread {

    protected String MainDirectory;
    protected String pathFileLabeled;
    protected String pathFileUnLabeled;
    protected String pathFileRelationSeeds;

    public PagesParserTOLearningSemanticRelation(String MainDirectory, String pathFileLabeled, String pathFileUnLabeled, String pathFileRelationSeeds) {
        this.MainDirectory = MainDirectory;
        this.pathFileLabeled = pathFileLabeled;
        this.pathFileUnLabeled = pathFileUnLabeled;
        this.pathFileRelationSeeds = pathFileRelationSeeds;
    }

    @Override
    public void run() {
        try {
            ReadDirectory(MainDirectory, pathFileLabeled, pathFileUnLabeled, pathFileRelationSeeds);
            System.out.println("Sistema finalizado com sucesso!");
        } catch (IOException ex) {
            Logger.getLogger(PagesParserTOLearningSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void ReadDirectory(String MainDirectory, String pathFileLabeled, String pathFileUnLabeled, String pathFileRelationSeeds) throws IOException {

        List<ObjectOf4Strings> ObjectOf4Strings = new ArrayList();
        List ListSeed = PagesParser.ReadTXTtoList(pathFileRelationSeeds);

        CharsetMatch cm = null;
        CharsetDetector cd = new CharsetDetector();

        FileInputStream InUnLabeled = null;
        InUnLabeled = new FileInputStream(pathFileUnLabeled);
        String lineUnLabeled = "";
        BufferedInputStream BRUnLabeled = null;
        BRUnLabeled = new BufferedInputStream(InUnLabeled);
        cd.setText(BRUnLabeled);
        cm = cd.detect();

        Scanner scanUnLabeled = new Scanner(BRUnLabeled, cm.getName());
        scanUnLabeled.useDelimiter("[\n]+");

        FileInputStream inLabeled = null;
        inLabeled = new FileInputStream(pathFileLabeled);
        String lineLabeled = "";
        BufferedInputStream BRLabeled = null;
        BRLabeled = new BufferedInputStream(inLabeled);
        cd = new CharsetDetector();
        cd.setText(BRLabeled);
        cm = cd.detect();
        Scanner scanLabeled = new Scanner(BRLabeled, cm.getName());
        scanLabeled.useDelimiter("[\n]+");

        while ((scanUnLabeled.hasNext()) && (scanLabeled.hasNext())) {

            lineUnLabeled = scanUnLabeled.next();
            lineUnLabeled = lineUnLabeled.trim();

            lineLabeled = scanLabeled.next();
            lineLabeled = lineLabeled.trim();

            if (lineLabeled.trim().split(" ").length == lineUnLabeled.trim().split(" ").length) {
                System.out.println(lineLabeled);
                System.out.println(lineUnLabeled);

                int w = 0;
                while (ListSeed.size() > w) {
                    ObjectOf4Strings = ReGetENsPairsInTheList(lineLabeled, ListSeed.get(w).toString(), lineUnLabeled, ObjectOf4Strings);
                    w++;
                }

                if ((ObjectOf4Strings.size() >= 100) || ((!scanLabeled.hasNext()) && ((!scanUnLabeled.hasNext())))) {
                    utilit.UtilitBD_AllPairsDataToSemanticRelation.connect();
                    int index = 0;
                    while (ObjectOf4Strings.size() > index) {
                        //testar aqui - 06/05/2012
                        utilit.UtilitBD_AllPairsDataToSemanticRelation.ResolveDB_Update_OR_Insert(ObjectOf4Strings.get(index).getRelation(), ObjectOf4Strings.get(index).getENLeft(), ObjectOf4Strings.get(index).getENRight(), ObjectOf4Strings.get(index).getPattern(), 1);
                        index++;
                    }
                    ObjectOf4Strings.clear();
                    utilit.UtilitBD_AllPairsDataToSemanticRelation.disconnect();
                }
            }
        }
    }

    public static List<ObjectOf4Strings> ReGetENsPairsInTheList(String LabeledSentence, String SentenceInput, String UnLabeled, List<ObjectOf4Strings> ObjectOf4Strings) {
        String aux = "";
        String[] input = SentenceInput.trim().split(" ");
        String TempLeft = "";
        String TempRight = "";
        int y = 0;
        while (input.length > y) {
            aux = (aux + " " + "([a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄ]*/" + input[y].trim() + ")").trim();
            y++;
        }
        aux = "(\\b" + aux.trim() + "\\b)";

        Pattern pattern = Pattern.compile(aux);
        Matcher matcher = pattern.matcher(LabeledSentence);

        while (matcher.find()) {
            
                       
            //Busca a Relação Semântica
            int qtSpaceBefore = (LabeledSentence.substring(0, matcher.start())).trim().split(" ").length;
            int qtSpaceWithSeed = (LabeledSentence.substring(0, matcher.end())).trim().split(" ").length;
            String tempUnLabeled[] = UnLabeled.trim().split(" ");
            String tempRelation = "";
            while (qtSpaceBefore < qtSpaceWithSeed) {
                tempRelation = tempRelation + " " + tempUnLabeled[qtSpaceBefore];
                qtSpaceBefore++;
            }
            System.out.println("Contexto de Relação Semântica = " + tempRelation + "," + SentenceInput);
            //Fim Busca Relação Semântica
            tempRelation = tempRelation.trim();

            //Busca EN Left
            if ((matcher.start() > 0) && (matcher.end() < LabeledSentence.length())) {
                TempLeft = "";
                int NGram = 5;
                qtSpaceBefore = (LabeledSentence.substring(0, matcher.start())).trim().split(" ").length;
                while ((NGram > 0) && (qtSpaceBefore > 0)) {
                    TempLeft = tempUnLabeled[qtSpaceBefore - 1] + " " + TempLeft;
                    NGram--;
                    qtSpaceBefore--;
                }
                TempLeft = TempLeft.trim();
                //Fim Busca EN Left

                //Busca EN Right           
                TempRight = "";
                NGram = 5;
                int qtSpaceAfter = (LabeledSentence.substring(matcher.end())).trim().split(" ").length;
                int index = qtSpaceWithSeed;
                while ((NGram > 0) && (qtSpaceAfter > 0)) {
                    TempRight = TempRight + " " + tempUnLabeled[index];
                    index++;
                    NGram--;
                    qtSpaceAfter--;
                }
                TempRight = TempRight.trim();
                //Fim Busca EN Right
                System.out.println("-------------Provisório------------ ");
                System.out.println("Relation -> " + tempRelation);
                System.out.println("EN-Left -> " + TempLeft);
                System.out.println("EN-Right -> " + TempRight);
                System.out.println("-----------X------------X---------- ");

                boolean IsName = false;

                AllPairsData_New_ToCartegoryLearning.ListStopWords = PagesParser.ReadTXTtoListSIMPLE(Main.StopWords);
                IsName = IsNameLessOneInSentence(TempLeft);
                TempLeft = (AllPairsData_New_ToCartegoryLearning.RemoveSTWInEnd(TempLeft, IsName)).trim();

                IsName = IsNameLessOneInSentence(TempRight);
                TempRight = (AllPairsData_New_ToCartegoryLearning.RemoveSTWInEnd(TempRight, IsName)).trim();

                tempRelation = tempRelation.trim();

                System.out.println("-------------Final------------ ");
                System.out.println("Relation -> " + tempRelation);
                System.out.println("EN-Left -> " + TempLeft);
                System.out.println("EN-Right -> " + TempRight);
                System.out.println("-----------X------------X---------- ");

                if ((!TempLeft.isEmpty()) && (!TempRight.isEmpty())) {
                    ObjectOf4Strings.add(new ObjectOf4Strings(tempRelation, SentenceInput, TempLeft, TempRight));
                }
            }
        }
        return ObjectOf4Strings;
    }

    public static void SaveFile(String File, String Add) {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(/*
                     * Directory +
                     */File, Main.FinalFile), "UTF-8"));
            out.write(Add + "\n");
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(PagesParserTOLearningSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(
                    "Erro para salvar no arquivo " + File + " - 12 -");
        }
    }

    public static void SaveListInFile(String File, List ListToAdd, Boolean FinalFile) {
        try {
            int i = 0;
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(File, FinalFile), "UTF-8"));
            while (i < ListToAdd.size()) {
                System.out.println(ListToAdd.get(i).toString());
                out.write(ListToAdd.get(i).toString() + "\n");
                i++;
            }
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(PagesParserTOLearningSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(
                    "Erro para salvar no arquivo " + File + " - 12 -");
        }
    }

    public static void SaveObjectListInFile(String File, List<ObjectOf3Strings> ListToAdd, Boolean FinalFile) {
        try {
            int i = 0;
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(File, FinalFile), "UTF-8"));
            while (i < ListToAdd.size()) {
                out.write(ListToAdd.get(i).getLabeled().toString() + "\n");
                i++;
            }
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(PagesParserTOLearningSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(
                    "Erro para salvar no arquivo " + File + " - 12 -");
        }
    }

    public static String ListToString(String ListT[]) {
        String Temp = "";
        int x = 0;
        while (x < ListT.length) {
            Temp = Temp + " " + ListT[x].toString();
            Temp = Temp.trim();
            x++;
        }
        return Temp;
    }

    public static List ReadTXTtoListSIMPLE(String file) {
        FileInputStream in = null;
        List ListSentences = new ArrayList(); //Lista de sentenças de uma página
        try {
            in = new FileInputStream(file);
            String line = "";
            BufferedInputStream BR = null;
            BR = new BufferedInputStream(in);
            //Scanner scanUnLabeled = new Scanner(new File(file));
            CharsetMatch cm = null;
            CharsetDetector cd = new CharsetDetector();
            cd.setText(BR);
            cm = cd.detect();
            Scanner scan = new Scanner(BR, cm.getName());
            scan.useDelimiter("[\n]+");
            //scan.useDelimiter("[\t\n\r]+");
            while (scan.hasNext()) {
                line = scan.next();
                line = line.trim();
                //System.out.println(lineUnLabeled + " Aqui 13");
                ListSentences.add(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(PagesParserTOLearningSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(PagesParserTOLearningSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ListSentences;
    }

    public static boolean IsNameLessOneInSentence(String Sentence) {
        Pattern patternIsName = Pattern.compile("([A-ZA-ZÉÚÍÓÁÈÙÌÒÀÕÃÑÊÛÎÔÂËYÜÏÖÄ])");
        Matcher matcherIsName = patternIsName.matcher(Sentence.substring(1));
        if (matcherIsName.find()) {
            return true;
        } else {
            return false;
        }
    }
}
