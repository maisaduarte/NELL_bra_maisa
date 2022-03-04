/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import java.io.*;
import java.nio.channels.FileChannel;
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
public class PagesParser extends Thread {

    protected String MainDirectory;
    protected String pathFile;
    protected String File;

    public PagesParser(String MainDirectory, String pathFile, String File) {
        this.MainDirectory = MainDirectory;
        this.pathFile = pathFile;
        this.File = File;
    }

    @Override
    public void run() {
        try {
            ReadDirectory(MainDirectory, pathFile, File);
            System.out.println("Sistema finalizado com sucesso!");
        } catch (IOException ex) {
            Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void ReadDirectory(String MainDir, String pathFile, String File) throws IOException {
        File diretorio = new File(MainDir);
        File[] arquivos = diretorio.listFiles();
        List ListUnLabeledSentences = new ArrayList(); //Lista de sentenças de uma página
        List<ObjectOf3Strings> AllSentencesList = new ArrayList<>(); //Lista de sentenças etiquetadas de uma página
        List ListExtractedContext = new ArrayList(); //Lista de sentenças ref. aos índices


        if (arquivos != null) {
            int length = arquivos.length;

            for (int i = 0; i < length; ++i) {
                File f = arquivos[i];

                if (f.isFile()) {

                    System.out.println("Diretorio: " + f.getName());
                    AllSentencesList = ReadFilePage(f.toString());

                    SaveFile(Main.FileReady, f.getName());

                    //função para etiquetar lista de sentenças
                    Tagger(AllSentencesList);

                    //remove frases sem etiquetação geradas erroneamente pelo Parser
                    RemoveSentencesWithWrongTokens(AllSentencesList); //Lembrar q usa sem retorn, mas a lista atualiza

                    //função para limpar etiquetas de lista NAO "taggeada"
                    RemovePunctionRest(AllSentencesList);

                    //Limpa lista etiquetada para verificar quais co-ocorrem com a lista de sementes
                    ClearLabeledList(AllSentencesList);

                    //Monta/Salva lista de contextos
                    GetContexts(AllSentencesList);

                    //Revê e limpa lista que possui registros em branco
                    RemoveWrongRegister(AllSentencesList);

                    //Lê do arquivo a lista de contextos
                    ListExtractedContext = PagesParser.ReadTXTtoList(Main.ContextosFile);

                    //Adiciona a uma lista todas as combinações possíveis
                    AddOnAllPairsData(AllSentencesList, ListExtractedContext);

                    System.out.println("Sistema irá salvar listas" + "\n");
                    //Salva Lista Sem Etiquetação
                    SaveListInFile(pathFile + "Unlabeled/" + File, ListUnLabeledSentences, true);

                    //Salva Lista Com Etiquetação
                    SaveObjectListInFile(pathFile + "Labeled/" + File, AllSentencesList, true);
                    System.out.println("Sistema salvou listas" + "\n");

                    MoveTXT(f.getPath(), f.getName());
                    System.out.println("Sistema recortou para /Copy a pagina trabalhada" + "\n");

                    ListExtractedContext.clear();
                    AllSentencesList.clear();
                    ListUnLabeledSentences.clear();

                    System.out.println("Diretorio: " + f.getName());

                } else if (f.isDirectory()) {
                    ReadDirectory(f.toString(), pathFile, File);
                    System.out.println("Diretorio: " + f.getName());
                }
            }
        }
    }

    public static void RemoveWrongRegister(List<ObjectOf3Strings> ListSentences) {
        int i = 0;
        while (ListSentences.size() > i) {
            ListSentences.get(i).setLabeled(ListSentences.get(i).getLabeled().trim());
            ListSentences.get(i).setLabeledCleaned(ListSentences.get(i).getLabeledCleaned().trim());
            ListSentences.get(i).setUnLabeled(ListSentences.get(i).getUnLabeled().trim());
            if ((ListSentences.get(i).getLabeled().toString().isEmpty()) || (ListSentences.get(i).getLabeledCleaned().toString().isEmpty()) || ListSentences.get(i).getUnLabeled().toString().isEmpty()) {
                ListSentences.remove(i);
                i--;
            }
            i++;
        }
    }

    public static void RemoveSentencesWithWrongTokens(List<ObjectOf3Strings> ListSentLabeled) {
        int i = 0;
        int y = 0;
        while (i < ListSentLabeled.size()) {
            String aux[] = ListSentLabeled.get(i).getLabeled().toString().split(" ");
            y = 0;
            while (y < aux.length) {
                Pattern pattern = Pattern.compile("/{1}");
                Matcher matcher = pattern.matcher(aux[y].toString());
                if (!matcher.find()) {
                    y = aux.length;
                    ListSentLabeled.remove(i);
                    i--;
                }
                y++;
            }
            i++;
        }
        i = 0;
    }

    public static void RemovePunctionRest(List<ObjectOf3Strings> ListSentences) {
        int i = 0;
        while (i < ListSentences.size()) {
            ListSentences.get(i).setUnLabeled(clearSentenceAfterTagger(ListSentences.get(i).getUnLabeled().toString()));
            i++;
        }
    }

    public static void MoveTXT(String Path, String Name) throws FileNotFoundException, IOException {
        FileChannel destChannel;
        try (FileChannel oriChannel = new FileInputStream(Path).getChannel()) {
            System.out.println(Main.pathJava + Main.CpyFiles + Name);
            destChannel = new FileOutputStream(Main.pathJava + Main.CpyFiles + Name).getChannel();
            destChannel.transferFrom(oriChannel, 0, oriChannel.size());
        }
        destChannel.close();

        Main.deleteSimpleFile(Path);

        System.out.println(Path + "/" + Name);

    }

    public static void AddOnAllPairsData(List<ObjectOf3Strings> ListCompleteSentences, List ListExtractedContext) {
        List ListALLPairsToData = new ArrayList();
        int i = 0;
        int y = 0;
        int w = 0;
        String side = "";
        String SentToSplit[];
        String NGram = "";
        String ProvNGram = "";

        while (i < ListExtractedContext.size()) {
            y = 0;
            while (y < ListCompleteSentences.size()) {

                Pattern pattern = Pattern.compile("\\b(" + ListExtractedContext.get(i).toString().substring(ListExtractedContext.get(i).toString().indexOf(",") + 1) + ")\\b");
                Matcher matcher = pattern.matcher(ListCompleteSentences.get(y).UnLabeled.toString());
                boolean remove = false;
                while ((matcher.find()) && (remove == false)) {

                    side = null;
                    side = ListExtractedContext.get(i).toString().substring(0, ListExtractedContext.get(i).toString().indexOf(","));

                    if (side.equalsIgnoreCase("R")) {

                        // + 1 significa o espaço entre as sentenças
                        String auxText = ListCompleteSentences.get(y).UnLabeled.toString().substring(matcher.end());
                        System.out.println(auxText);
                        auxText = auxText.trim();
                        if (!auxText.isEmpty()) {
                            // -1 devido a diferença entre trabalhar com tamanho de vetor e indice
                            SentToSplit = null;
                            SentToSplit = (auxText.trim().split(" "));

                            w = 0;
                            NGram = null;
                            NGram = "";

                            while ((w < SentToSplit.length) && (w < 5)) {
                                NGram = NGram + " " + SentToSplit[w].toString();
                                NGram = NGram.trim();
                                ProvNGram = ListExtractedContext.get(i).toString() + "," + NGram;
                                ProvNGram = ProvNGram.trim();
                                w++;
                            }
                        }
                    } else if (side.equalsIgnoreCase("L")) {
                        SentToSplit = ListCompleteSentences.get(y).UnLabeled.toString().substring(0, matcher.start()).split(" ");
                        w = 0;
                        int wx = SentToSplit.length - 1;
                        NGram = "";
                        while ((wx >= 0) && (w < 5)) {
                            NGram = SentToSplit[wx].toString() + " " + NGram;
                            NGram = NGram.trim();

                            ProvNGram = ListExtractedContext.get(i).toString() + "," + NGram;

                            System.out.println(ProvNGram);
                            ProvNGram = ProvNGram.trim();
                            w++;
                            wx--;
                        }
                    }
                    //Procura etiqueta do contexto extraído
                    String Label = ReGetLabelsInTheList(ListCompleteSentences.get(y).getLabeled(), ListExtractedContext.get(i).toString().substring(ListExtractedContext.get(i).toString().indexOf(",") + 1));

                    if (Label.isEmpty()) {
                        Label = SimpleTagger(ListExtractedContext.get(i).toString().substring(ListExtractedContext.get(i).toString().indexOf(",") + 1));
                        Label = clearSentenceAfterTagger(Label); //remove pontuações que foram necessárias para a etiquetação. Ex: .,!?
                        Label = UnderlineRemove(Label);
                        Label = OnlyLabel(Label);
                    }
                    if ((ProvNGram.isEmpty())
                            || ((side.equalsIgnoreCase("R")) && (matcher.end() == (ListCompleteSentences.get(y).UnLabeled.toString().length() - 1)))
                            || ((side.equalsIgnoreCase("L")) && (matcher.start() == 0))) {
                        ListCompleteSentences.remove(y);
                        remove = true;
                        y--;
                    } else {
                        ListALLPairsToData.add(Label + "," + ProvNGram);
                        SaveListInFile(Main.ALLPairsToData, ListALLPairsToData, true);
                        ListALLPairsToData.clear();
                        ProvNGram = "";
                    }

                }

                y++;
            }
            i++;

        }
    }

    public static String OnlyLabel(String Labeled) {
        String[] aux = Labeled.split(" ");
        String Label = "";
        int i = 0;
        while (aux.length > i) {
            Label = Label + " " + aux[i].substring(aux[i].toString().indexOf("/") + 1);
            i++;
        }
        return Label.trim();
    }

    public static String SimpleTagger(String sentence) {
        Runtime rt = Runtime.getRuntime();
        String line = null;
        try {
            String[] cmd = {"/bin/sh", "-c", "echo \"" + sentence + "\" | bash run-Tagger.sh "};
            Process pr = rt.exec(cmd);
            try (BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()))) {
                line = input.readLine();
                line = line.trim();
                System.out.println(line);

                if (line != null) {

                    line = line.trim();
                    if ((line.contains("//")) || (line.contains("*"))) {
                        String Aux[] = line.split(" ");
                        int x = 0;
                        while (x < Aux.length) {
                            if (Aux[x].contains("//")) {
                                Aux[x] = "";
                            }
                            x++;
                        }
                        line = ListToString(Aux);
                        if ((line.contains("//")) || (line.contains("*"))) {
                            x = 0;
                            Aux = line.split(" ");
                            while (x < Aux.length) {
                                if (Aux[x].contains("*")) {
                                    Aux[x] = "";
                                }
                                x++;
                            }
                            line = ListToString(Aux);
                        }
                    }
                    System.out.println(line);

                }

                pr.destroy();
            }
        } catch (IOException ex) {
            Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;

    }

    public static String ReGetLabelsInTheList(String LabeledSentence, String SentenceInput) {
        String SentenceLabel = "";
        String aux = "";
        String[] input = SentenceInput.split(" ");
        int y = 0;
        while (input.length - 1 > y) {
            aux = (aux + "(" + input[y].trim() + "/[a-zA-Z]+ )").trim();
            y++;
        }
        aux = (aux + "(" + input[y].trim() + "/[a-zA-Z]+)").trim();
        aux = aux.trim();
        System.out.println(aux);

        Pattern pattern = Pattern.compile(aux);
        Matcher matcher = pattern.matcher(LabeledSentence);

        while (matcher.find()) {
            int i = 1;
            while (matcher.groupCount() >= i) {
                System.out.println(SentenceLabel = (SentenceLabel + " " + matcher.group(i).substring(matcher.group(i).indexOf("/") + 1)).trim());
                i++;
            }
        }
        return SentenceLabel;
    }

    public static void SaveFile(String File, String Add) {
        try {
            try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(/*
                     * Directory +
                     */File, Main.FinalFile), "UTF-8"))) {
                out.write(Add + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(
                    "Erro para salvar no arquivo " + File + " - 12 -");
        }
    }

    public static void SaveListInFile(String File, List ListToAdd, Boolean FinalFile) {
        try {
            int i = 0;
            try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(File, FinalFile), "UTF-8"))) {
                while (i < ListToAdd.size()) {
                    System.out.println(ListToAdd.get(i).toString());
                    out.write(ListToAdd.get(i).toString() + "\n");
                    i++;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(
                    "Erro para salvar no arquivo " + File + " - 12 -");
        }
    }

    public static void SaveObjectListInFile(String File, List<ObjectOf3Strings> ListToAdd, Boolean FinalFile) {
        try {
            int i = 0;
            try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(File, FinalFile), "UTF-8"))) {
                while (i < ListToAdd.size()) {
                    out.write(ListToAdd.get(i).getLabeled().toString() + "\n");
                    i++;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(
                    "Erro para salvar no arquivo " + File + " - 12 -");
        }
    }

    public static void Tagger(List<ObjectOf3Strings> ListSentences) {

        try {
            Runtime rt = Runtime.getRuntime();
            System.out.println(System.getProperty("user.dir"));

            int i = 0;
            while (i < ListSentences.size()) {

                String InSentence; //Sentença original
                InSentence = ListSentences.get(i).getUnLabeled().toString();
                InSentence = InSentence.trim();
                System.out.println(InSentence);

                if (!InSentence.isEmpty()) {

                    String[] cmd = {"/bin/sh", "-c", "echo \"" + InSentence + "\" | bash run-Tagger.sh "};

                    Process pr = rt.exec(cmd);
                    try (BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()))) {
                        String line;
                        line = input.readLine();
                        line = clearSentenceAfterTagger(line); //remove pontuações que foram necessárias para a etiquetação. Ex: .,!?
                        line = UnderlineRemove(line); //remove detalhes de etiquetação para DAS, NAS, etc. as quais todas sao identificadas com "_"

                        line = line.trim();
                        System.out.println(line);

                        if (line != null) {

                            line = line.trim();
                            if ((line.contains("//")) || (line.contains("*"))) {
                                String Aux[] = line.split(" ");
                                int x = 0;
                                while (x < Aux.length) {
                                    if (Aux[x].contains("//")) {
                                        Aux[x] = "";
                                    }
                                    x++;
                                }
                                line = ListToString(Aux);
                                if ((line.contains("//")) || (line.contains("*"))) {
                                    x = 0;
                                    Aux = line.split(" ");
                                    while (x < Aux.length) {
                                        if (Aux[x].contains("*")) {
                                            Aux[x] = "";
                                        }
                                        x++;
                                    }
                                    line = ListToString(Aux);
                                }
                            }
                            System.out.println(line);
                            ListSentences.get(i).setLabeled(line);
                        }

                        int exitVal = pr.waitFor();
                        if (exitVal != 0) {
                            System.out.println("Exited with error code" + exitVal);
                        } else {
                            System.out.println("Arquivo Etiquetado");
                        }
                    }
                    pr.destroy();
                }
                i++;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("erro in the Tagger");
            Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
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

    public static List<ObjectOf3Strings> ReadFilePage(String file) {
        List<ObjectOf3Strings> ListSentences = new ArrayList<>();
        try {
            String line;
            boolean get = true;
            boolean save = false;
            boolean auxSave = false;
            FileInputStream in = new FileInputStream(file);
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
            while (scan.hasNext()) {

                line = scan.next();
                line = line.trim();

                System.out.println(line);

                if (!line.isEmpty()) {
                    if ((line.indexOf("<TEXT>") > -1) && (!((line.indexOf("</TEXT")) > -1))) {
                        line = line.substring(line.indexOf("<TEXT>") + "<TEXT>".length());
                        save = true;
                    } else if ((!(line.indexOf("<TEXT>") > -1)) && ((line.indexOf("</TEXT")) > -1)) {
                        line = line.substring(0, line.indexOf("</TEXT>"));
                        auxSave = true;
                        save = true;
                    }
                    if (save == true) {
                        while ((get || save) && (!line.isEmpty())) {
                            line = line.trim();
                            int idPt = WhatIsTheIndex(line) + 1;

                            if (idPt == 0) {
                                if ((idPt == -1) && (!(line.indexOf("</TEXT>") > -1))) {
                                    idPt = line.length();
                                } else if ((idPt == -1) && (line.indexOf("</TEXT>") > -1)) {
                                    idPt = line.indexOf("</TEXT>");
                                    get = false;
                                } else {
                                    idPt = line.length();
                                    get = false;
                                }
                            }

                            if ((idPt == 0) && (line.length() > 0)) {
                                idPt = line.length();
                            }
                            line = line.trim();
                            if (!line.isEmpty()) {
                                String sentence = (line.substring(0, idPt));
                                sentence = clearSentenceBeforeTagger(sentence);
                                sentence = sentence.trim();
                                if (!sentence.isEmpty()) {
                                    ListSentences.add(new ObjectOf3Strings(sentence, "", ""));
                                }
                                if (line.length() != idPt) {
                                    line = line.substring(idPt + 1);
                                } else {
                                    line = "";
                                }

                            } else {
                                get = false;
                            }
                        }
                        if (auxSave) {
                            auxSave = false;
                            save = false;
                        }
                    }
                }
            }
            scan.close();
            BR.close();
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListSentences;
    }

    public static String clearSentenceBeforeTagger(String sentence) {

        sentence = sentence.replace(" – ", " ");
        sentence = sentence.replace(" –", " ");
        sentence = sentence.replace("– ", " ");
        sentence = sentence.replace(" - ", " ");
        sentence = sentence.replace(" -", " ");
        sentence = sentence.replace("- ", " ");
        sentence = sentence.replace("\"", " ");
        sentence = sentence.replace("\\", " ");
        sentence = sentence.replace("/", " ");
        sentence = sentence.replace("//", " ");
        sentence = sentence.replace("'", "");
        sentence = sentence.replace("`", "");
        sentence = sentence.replace("'", "");
        sentence = sentence.replace("[", "");
        sentence = sentence.replace("]", "");
        sentence = sentence.replace("{", "");
        sentence = sentence.replace("}", "");
        sentence = sentence.replace("*", "");
        sentence = sentence.replace("=", "");
        sentence = sentence.replace("+", "");
        sentence = sentence.replace("*", "");
        sentence = sentence.replace("#", "");

        sentence = sentence.trim();

        return sentence;
    }

    public static String clearSentenceAfterTagger(String sentence) {

        sentence = sentence.replace("\\*(/PNT", "");
        sentence = sentence.replace(")/PNT", "");
        sentence = sentence.replace("...", "");
        sentence = sentence.replace(",", "");
        sentence = sentence.replace(":", "");
        sentence = sentence.replace(";", "");
        sentence = sentence.replace("!", "");
        sentence = sentence.replace(".", "");
        sentence = sentence.replace("?", "");
        sentence = sentence.replace("(", "");
        sentence = sentence.replace(")", "");
        sentence = sentence.replace("[", "");
        sentence = sentence.replace("]", "");

        sentence = sentence.trim();

        return sentence;
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
            while (scan.hasNext()) {
                line = scan.next();
                line = UnderlineRemove(line);
                if (!line.isEmpty()) {
                    line = line.trim();
                    ListSentences.add(line);
                }
            }
        } catch (IOException ex) {
            System.out.println(" Aqui 12");
            Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ListSentences;
    }

    public static List ReadTXTtoListSIMPLE(String file) {
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
            while (scan.hasNext()) {
                line = scan.next();
                line = line.trim();
                //System.out.println(line + " Aqui 13");
                ListSentences.add(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ListSentences;
    }

    public static int WhatIsTheIndex(String line) {
        int idPt = 0;
        int idPt1 = line.indexOf("! ");
        int idPt2 = line.indexOf(". ");
        int idPt3 = line.indexOf("? ");

        if ((idPt1 > -1) && (idPt2 > -1) && (idPt3 > -1)) {
            if ((idPt1 > idPt2) && (idPt1 > idPt2)) {
                idPt = idPt2;
            } else if ((idPt2 > idPt1) && (idPt3 > idPt1)) {
                idPt = idPt1;
            } else if ((idPt1 > idPt3) && (idPt2 > idPt3)) {
                idPt = idPt3;
            }
        } else if ((idPt1 > -1) && (idPt2 > -1)) {
            if (idPt1 > idPt2) {
                idPt = idPt2;
            } else {
                idPt = idPt1;
            }
        } else if ((idPt1 > -1) && (idPt3 > -1)) {
            if (idPt1 > idPt3) {
                idPt = idPt3;
            } else {
                idPt = idPt1;
            }
        } else if ((idPt3 > -1) && (idPt2 > -1)) {
            if (idPt3 > idPt2) {
                idPt = idPt2;
            } else {
                idPt = idPt3;
            }
        } else if ((idPt1 == -1) && (idPt2 == -1) && (idPt3 > -1)) {
            idPt = idPt3;
        } else if ((idPt1 == -1) && (idPt3 == -1) && (idPt2 > -1)) {
            idPt = idPt2;
        } else if ((idPt2 == -1) && (idPt3 == -1) && (idPt1 > -1)) {
            idPt = idPt1;
        } else {
            idPt = -1;
        }
        return idPt;
    }

    public static List ClearLabeledDoc(String file, int identif) {
        //identif == 1 significa que é um arquivo de sementes previamente dado, que possui antes da vírgula o local, ex: R,Fui/V...
        List ListPhrases = new ArrayList();
        String local = "";
        try {
            FileInputStream in = new FileInputStream(file);
            try (Scanner scan = new Scanner(in)) {
                scan.useDelimiter("\n");
                String line;
                int indexSpace = 0;
                String TempLine = null;
                while (scan.hasNext()) {
                    line = scan.next();
                    if (identif == 1) {
                        local = line.substring(0, line.indexOf(",") + 1);
                        line = line.substring(line.indexOf(","));
                    }
                    line = UnderlineRemove(line);
                    line = line.trim();
                    indexSpace = 0;
                    TempLine = null;
                    TempLine = "";


                    while ((line.indexOf("/") > -1)) {

                        if ((!(line.indexOf(" ") > -1)) && (!(line.indexOf("/") > -1)) && ((line.indexOf("/") < line.length()))) {
                            indexSpace = line.length();
                        } else if ((line.indexOf(" ") > -1) && (line.indexOf("/") > -1)) {
                            indexSpace = line.indexOf(" ");
                        } else if (line.indexOf("/") > -1) {
                            indexSpace = line.length();
                        } else {
                            indexSpace = -1;
                        }
                        line = line.trim();
                        if ((!line.isEmpty()) && (indexSpace > -1)) {
                            TempLine = TempLine + (line.substring(line.indexOf("/") + 1, indexSpace)) + " ";
                            line = line.trim();
                            if (line.indexOf(" ") > -1) {
                                line = line.substring(indexSpace + 1);
                                line = line.trim();
                            } else if (!(indexSpace == line.length())) {
                                line = line.substring(indexSpace + 1);
                            } else {
                                indexSpace = 0;
                                line = "";
                            }
                        }
                    }
                    if (!TempLine.isEmpty()) {
                        TempLine = TempLine.trim();
                        ListPhrases.add(local + TempLine);
                    }
                }
            }
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListPhrases;
    }

    public static List ClearLabeledDoc_CompleteWithUnderline(String file, int identif) {
        //identif == 1 significa que é um arquivo de sementes previamente dado, que possui antes da vírgula o local, ex: R,Fui/V...
        List ListPhrases = new ArrayList();
        String local = "";
        String Sentence = "";

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PagesParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Scanner scan = new Scanner(in)) {
            scan.useDelimiter("\n");

            while (scan.hasNext()) {
                String RegExp = ("(_/|/)([a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄ0-9]+)");
                Pattern pattern = Pattern.compile(RegExp);
                Matcher matcher = pattern.matcher(scan.nextLine());
                String seed = "";
                while (matcher.find()) {
                    seed = seed + " " + matcher.group();
                }
                ListPhrases.add(seed);
            }
        }
        return ListPhrases;
    }

    public static String UnderlineRemove(String line) {
        int Aux1 = 0;
        int Aux2 = 0;
        int Aux3 = 0;
        String Aux4 = "";

        while (line.indexOf("_ ") > -1) {
            Aux1 = line.indexOf("_ ");
            Aux2 = line.indexOf("/", Aux1);
            if (Aux2 == -1) {
                line = "";
            } else {
                line = line.substring(0, Aux1) + (line.substring(Aux2));
                line = line.trim();
            }
        }
        while (line.indexOf("_") > -1) {
            Aux1 = line.indexOf("_");
            Aux2 = line.indexOf(" ", Aux1 + 1);
            if (Aux2 == -1) {
                Aux2 = Aux1;
            }
            Aux3 = line.indexOf(" ", Aux2 + 1);
            if (Aux3 == -1) {
                Aux3 = line.length();
            }
            Aux4 = line.substring(Aux2, Aux3);
            line = line.replaceFirst(Aux4, "");
            line = line.replaceFirst("_", "");
            line = line.trim();
        }
        while (line.indexOf("-") > -1) {
            Aux1 = line.indexOf("-");
            Aux2 = line.indexOf(" ", Aux1 + 1);
            if (Aux2 == -1) {
                Aux2 = Aux1;
            }
            Aux3 = line.indexOf(" ", Aux2 + 1);
            if (Aux3 == -1) {
                Aux3 = line.length();
            }
            Aux4 = line.substring(Aux2, Aux3);
            line = line.replaceFirst(Aux4, "");
            line = line.replaceFirst("-", "");
            line = line.trim();
        }
        return line;
    }

    public static int SearchData(String sentence) {
        int out = -1;
        Pattern SearchData = Pattern.compile("(\\d\\d)/(\\d\\d)/(\\d\\d)");
        Matcher m = SearchData.matcher(sentence);
        while (m.find()) {
            System.out.println(sentence.indexOf(m.group()));
            System.out.println(m.group());
            out = sentence.indexOf(m.group());
        }
        return out;
    }

    public static void ClearLabeledList(List<ObjectOf3Strings> LabeledSent) {
        int i = 0;
        String line;
        String TempLine = null;
        while (i < LabeledSent.size()) {
            line = LabeledSent.get(i).getLabeled().toString();
            line = line.trim();
            TempLine = null;
            TempLine = "";
            System.out.println(line);
            while (line.indexOf("/") > -1) {
                System.out.println(TempLine);
                if (line.indexOf(" ") > -1) {
                    TempLine = TempLine + " " + (line.substring(line.indexOf("/") + 1, line.indexOf(" "))) + " ";
                    line = line.substring(line.indexOf(" ") + 1);
                    line = line.trim();
                    TempLine = TempLine.trim();
                } else {
                    TempLine = TempLine + " " + (line.substring(line.indexOf("/") + 1));
                    line = "";
                    TempLine = TempLine.trim();
                }
            }
            if (!TempLine.isEmpty()) {
                LabeledSent.get(i).setLabeledCleaned(TempLine);
            }
            i++;
        }
    }

    public static List CountGramsSentence(List Sentence) {
        int i = 0;
        int count = 0;
        while (i < Sentence.size()) {
            count = Sentence.get(i).toString().split(",").length;
            if (count < 5) {
                while (count < 5) {
                    Sentence.set(i, Sentence.get(i).toString().concat(","));
                    count++;
                }
            }
            i++;
        }
        return Sentence;
    }

    public static void GetContexts(List<ObjectOf3Strings> ListSentences) {

        int i = 0;
        int y = 0;
        List ListWordsSentences = new ArrayList();
        i = 0;
        while (i < Main.ListSeed.size()) {
            y = 0;
            while (y < ListSentences.size()) {
                String aux[] = Main.ListSeed.get(i).toString().trim().split(" ");
                if (aux.length > 2) {

                    Pattern pattern = Pattern.compile("\\b(" + Main.ListSeed.get(i).toString().substring(Main.ListSeed.get(i).toString().indexOf(",") + 1).toString().trim() + "\\b)");
                    Matcher matcher = pattern.matcher(ListSentences.get(y).getLabeledCleaned().toString());

                    while (matcher.find()) {
                        int AuxCountSpaceBefore = ListSentences.get(y).getLabeledCleaned().toString().substring(0, matcher.start()).split(" ").length;
                        if (AuxCountSpaceBefore == 1) {
                            AuxCountSpaceBefore = 0;
                        }
                        int CountSpace = Main.ListSeed.get(i).toString().split(" ").length + AuxCountSpaceBefore;

                        String Context = "";

                        String wa[] = ListSentences.get(y).getLabeled().toString().trim().split(" ");
                        String SubString[] = ListSentences.get(y).getUnLabeled().toString().trim().split(" ");

                        if (wa.length != SubString.length) {
                            Main.SaveLineInFile(Main.pathJava, Main.ErrorsFile, true, ListSentences.get(y).Labeled.toString().trim() + "\n" + ListSentences.get(y).UnLabeled.toString().trim() + '\n');
                        } else {

                            while (AuxCountSpaceBefore < CountSpace) {
                                Context = Context + " " + SubString[AuxCountSpaceBefore].toString();
                                AuxCountSpaceBefore++;
                            }
                            String temp = Main.ListSeed.get(i).toString().substring(0, Main.ListSeed.get(i).toString().indexOf(",") + 1) + Context.trim();
                            if (!Main.ExistInList(ListWordsSentences, temp)) {
                                ListWordsSentences.add(temp);
                            }
                            System.out.println(Main.ListSeed.get(i).toString().substring(0, Main.ListSeed.get(i).toString().indexOf(",") + 1) + Context.trim());
                            System.out.println();
                        }
                    }
                }
                y++;
            }
            i++;
        }
        Main.SaveListInFile(Main.pathJava, Main.ContextosFile, true, ListWordsSentences);
    }
}
