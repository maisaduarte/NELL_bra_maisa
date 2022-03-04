package parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MaisaDuarte
 */
public class Main {

    static boolean FinalFile = true;
    static String pathSeed = "";
    static List ListSeed = new ArrayList();
    public static String pathJava = System.getProperty("user.dir");
    static String ErrorsFile = "FileErrors_Vetor.txt";
    static String ContextosFile = "ContextsList.txt";
    public static String ALLPairsToData = "AllPairsData.txt";
    static String FileReady = "FileReady.txt";
    public static String StopWords = "StopWords.txt";
    static String CpyFiles = "/Copy/";

    public static void main(String[] args) {

        System.out.println("Bem-vindo!");
        System.out.println("Parser desenvolvido por Maisa Duarte. Com orientação de Estevam Hruschka - UFSCar - 2011");
        String path1 = pathJava + "/Corpus/CHAVEFolha";
        String path2 = pathJava + "/Corpus/CHAVEPublico";
        String pathResult = pathJava + "/Result";
        String pathFile1 = "CHAVEFolha.txt";
        String pathFile2 = "CHAVEPublico.txt";
        String FinalFileUser = null;
        pathSeed = pathJava + "/Seed/Seed.txt";

        //sobe a lista de conhecimento prévio
        ListSeed = PagesParser.ReadTXTtoList(pathSeed);

        /*deleteSimpleFile("FileErrors_Vetor.txt");
         deleteSimpleFile("AllPairsData.txt");
         deleteSimpleFile("ContextsList.txt");


         /*  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
         try {
         System.out.println("Digite: \n" + "(Y) para criar novo arquivo de PairsData \n"
         + "(N) para continuar escrevendo em um arquivo já existente\n");
         FinalFileUser = reader.readLine();
         System.out.println("Digite 1º diretorio dos arquivos a serem processados: ");
         path1 = reader.readLine();
         System.out.println("Digite 2º diretorio dos arquivos a serem processados: ");
         path1 = reader.readLine(); 
         System.out.println("Digite o diretorio que armazenará os resultados: ");
         pathResult = reader.readLine(); 
         System.out.println("Digite o nome do arquivo 1 de resultados: ");
         pathFile1 = reader.readLine(); 
         System.out.println("Digite o nome do arquivo 2 de resultados: ");
         pathFile2 = reader.readLine(); 
         System.out.println("Digite o caminho\nome do arquivo de sementes: ");
         pathSeed = reader.readLine();         
        
         } catch (IOException ex) {
         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
        //Diretórios
        path1 = (path1.replace("\\", "/")).trim() + "/";
        path2 = (path2.replace("\\", "/")).trim() + "/";
        pathResult = (pathResult.replace("\\", "/")).trim() + "/";

        //arquivos
        pathFile1 = (pathFile1.replace("\\", "/")).trim();
        pathFile2 = (pathFile2.replace("\\", "/")).trim();
        FinalFileUser = "y";

        /*  if (FinalFileUser.equalsIgnoreCase("y")) {
         FinalFile = false;
         } else if (FinalFileUser.equalsIgnoreCase("n")) {
         FinalFile = false;
         } else {
         System.exit(0);
         }
         */
        System.out.println("Diretorio escolhido foram: \n" + path1 + " - 1 -\n  e " + path2 + "\n");
        /*
         File dir = new File(pathResult);
         if (dir.exists()) {
         if (!deleteDir(dir)) {
         System.exit(0);
         }
         }

         dir = new File(pathResult + "Labeled");
         if (dir.mkdirs()) {
         System.out.println("Diretorio criado com sucesso!");
         } else {
         System.out.println("Erro ao criar diretorio!");
         }
         dir = new File(pathResult + "Unlabeled");
         if (dir.mkdirs()) {
         System.out.println("Diretorio criado com sucesso!");
         } else {
         System.out.println("Erro ao criar diretorio!");
         }
         /*        CreateNewDocs(pathResult + "Result/Labeled/" + pathFile1);
         CreateNewDocs(pathResult + "Result/Unlabeled/" + pathFile1);
         CreateNewDocs(pathResult + "Result/Labeled/" + pathFile2);
         CreateNewDocs(pathResult + "Result/Unlabeled/" + pathFile2);*/

        PagesParser PParser1 = new PagesParser(path1, pathResult, pathFile1);
        PParser1.start();
        /*
         * TIRAR ESSES COMENTÁRIOS CASE O PROGRAMA FINALIZE CORRETAMENTE
         * 
         PagesParser PParser2 = new PagesParser(path2, pathResult, pathFile2);
         PParser2.start();
         */

    }

    public static void deleteSimpleFile(String file) {
        File simpleFile = new File(file);
        if (simpleFile.exists()) {
            simpleFile.delete();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void CreateNewDocs(String doc) {
        FileInputStream arq = null;
        try {
            arq = new FileInputStream(doc);
            try {
                arq.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void SaveListInFile(String Directory, String File, boolean FinalFile, List ListSent) {
        try {
            boolean out = false;
            File file = new File(Directory + "/" + File);
            if (!file.exists()) {
                FileOutputStream outF;
                PrintStream fileSaida;
                try {
                    outF = new FileOutputStream(file);
                    fileSaida = new PrintStream(outF);
                    fileSaida.close();
                    outF.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String lineTemp = "";
            int i = 0;
            while (ListSent.size() > i) {
                bf = new BufferedReader(new FileReader(file));
                out = false;
                lineTemp = bf.readLine();
                while (((lineTemp) != null) && (out == false)) {
                    if (lineTemp.equalsIgnoreCase(ListSent.get(i).toString())) {
                        ListSent.remove(i);
                        out = true;
                        i--;
                    } else {
                        lineTemp = bf.readLine();
                    }
                }
                bf.close();
                i++;
            }
            i = 0;
            BufferedWriter BuffR = null;
            BuffR = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, FinalFile)));
            while (i < ListSent.size()) {
                BuffR.write(ListSent.get(i).toString() + "\n");
                i++;
            }
            BuffR.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(
                    "Erro para salvar no arquivo " + File + " - 12 -");
        }
    }

    public static boolean ExistInList(List SentencesSent, String SentToAdd) {
        int i = 0;
        while (SentencesSent.size() > i) {
            if (SentencesSent.get(i).toString().equalsIgnoreCase(SentToAdd)) {
                return true;
            }
            i++;
        }
        return false;
    }

    public static void SaveLineInFile(String Directory, String File, boolean FinalFile, String line) {
        try {
            int i = 0;
            try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Directory + File, FinalFile),"UTF-8"))) {
                out.write(line);
            }
            i++;

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(
                    "Erro para salvar no arquivo " + File + " - 12 -");
        }
    }

    public static void SaveLineInFile(String Directory, String File, boolean FinalFile, String line, String coding) {
        try {
            int i = 0;
            try (OutputStreamWriter out = new OutputStreamWriter((new FileOutputStream(Directory + File, FinalFile)), coding)) {
                out.write(line);
            }
            i++;

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(
                    "Erro para salvar no arquivo " + File + " - 12 -");
        }
    }
}
