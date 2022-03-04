/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UPClueWeb;

import AllPairsData.ClearSentences;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilit.UtilitBD_AllPairsDataToCategory;

/**
 *
 * @author MaisaDuarte
 */
public class GetTXTData_USA_ToCategory {

    String fileToGet;

    public GetTXTData_USA_ToCategory(String fileToGet) {
        this.fileToGet = fileToGet;
    }

    public void GetTXTData_USA() {
        try {
            FileInputStream in = null;
            String[] brokenSentence = null;
            String sentence = "";
            String side = "";
            String EN = "";
            String Context = "";
            int Occurrence;
            List<ObjectENContextOccurrence> ListAllPairsData = new ArrayList();
            try {
                in = new FileInputStream(fileToGet);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GetTXTData_USA_ToCategory.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedInputStream BR = null;
            BR = new BufferedInputStream(in);

            //Reconhece a codificação - caso não etiquete, veja se a codificação extraída é em UTF-8
            //Caso o texto seja de fixa codificação/não precise deste teste, esse código pode ser reduzido - leitura de arquivo
            CharsetMatch cm = null;
            CharsetDetector cd = new CharsetDetector();
            cd.setText(BR);
            cm = cd.detect();

            Scanner scan = new Scanner(BR, cm.getName());
            scan.useDelimiter("[\n]+");
            int i = 0;

            while (scan.hasNext()) {
                System.out.println("____________________________");
                System.out.println(i++);

                System.out.println(sentence = scan.next());

                //sentence = ClearSentences.ClearPunctuationGeneral_From_ClueWeb(sentence);

                sentence = sentence.trim();
                sentence = sentence.replace("	", ",");
               sentence = sentence.replace("  ", ",");

                sentence = sentence.trim();
                brokenSentence = sentence.split(",");

                if (brokenSentence.length == 3) {

                    EN = ClearSentences.RemoveDuplicateBlanks(brokenSentence[0].trim());
                    Context = (ClearSentences.RemoveDuplicateBlanks(brokenSentence[1].trim()));
                    Occurrence = Integer.valueOf((brokenSentence[2]).trim());

                    if (Context.substring(0, 1).equalsIgnoreCase("_")) {
                        side = "L";
                    } else if ((Context.substring(Context.length() - 1)).equalsIgnoreCase("_")) {
                        side = "R";
                    } else {
                        //Fazer implementação qdo necessário!!!!!!!!
                    }

                    Context = Context.replace("_", "").trim();

                    if ((Context.length() > 2) && (EN.length() > 1) && (!side.isEmpty()) && (Occurrence > 0)) {

                        System.out.println("EN      - " + EN);
                        System.out.println("CONTEXT - " + Context);
                        System.out.println("SIDE    - " + side);
                        System.out.println("OCCUR   - " + Occurrence);

                        ListAllPairsData.add(new ObjectENContextOccurrence(EN, Context, side, Occurrence));
                        side = "";
                        EN = "";
                        Context = "";
                        Occurrence = 0;

                        if ((ListAllPairsData.size() == 1000) || (!scan.hasNext())) {
                            UtilitBD_AllPairsDataToCategory.setDATABASE_URL("jdbc:mysql://rtw.ml.cmu.edu:3306/corpusbr");
                            UtilitBD_AllPairsDataToCategory.setUser("corpusbr");
                            UtilitBD_AllPairsDataToCategory.setPassword("!rbsuproc!55555");
                            UtilitBD_AllPairsDataToCategory.connect();

                            if (ListAllPairsData.size() == 1000) {

                                Thread_ClueWeb_to_AllPairsData_To_Category TheadClueWeb1 = new Thread_ClueWeb_to_AllPairsData_To_Category(ListAllPairsData.subList(0, 100));
                                TheadClueWeb1.start();

                                Thread_ClueWeb_to_AllPairsData_To_Category TheadClueWeb2 = new Thread_ClueWeb_to_AllPairsData_To_Category(ListAllPairsData.subList(100, 200));
                                TheadClueWeb2.start();

                                Thread_ClueWeb_to_AllPairsData_To_Category TheadClueWeb3 = new Thread_ClueWeb_to_AllPairsData_To_Category(ListAllPairsData.subList(200, 300));
                                TheadClueWeb3.start();

                                Thread_ClueWeb_to_AllPairsData_To_Category TheadClueWeb4 = new Thread_ClueWeb_to_AllPairsData_To_Category(ListAllPairsData.subList(300, 400));
                                TheadClueWeb4.start();

                                Thread_ClueWeb_to_AllPairsData_To_Category TheadClueWeb5 = new Thread_ClueWeb_to_AllPairsData_To_Category(ListAllPairsData.subList(400, 500));
                                TheadClueWeb5.start();

                                Thread_ClueWeb_to_AllPairsData_To_Category TheadClueWeb6 = new Thread_ClueWeb_to_AllPairsData_To_Category(ListAllPairsData.subList(500, 600));
                                TheadClueWeb6.start();

                                Thread_ClueWeb_to_AllPairsData_To_Category TheadClueWeb7 = new Thread_ClueWeb_to_AllPairsData_To_Category(ListAllPairsData.subList(600, 700));
                                TheadClueWeb7.start();

                                Thread_ClueWeb_to_AllPairsData_To_Category TheadClueWeb8 = new Thread_ClueWeb_to_AllPairsData_To_Category(ListAllPairsData.subList(700, 800));
                                TheadClueWeb8.start();

                                Thread_ClueWeb_to_AllPairsData_To_Category TheadClueWeb9 = new Thread_ClueWeb_to_AllPairsData_To_Category(ListAllPairsData.subList(800, 900));
                                TheadClueWeb9.start();

                                Thread_ClueWeb_to_AllPairsData_To_Category TheadClueWeb10 = new Thread_ClueWeb_to_AllPairsData_To_Category(ListAllPairsData.subList(900, 1000));
                                TheadClueWeb10.start();
                                try {
                                    TheadClueWeb1.join();
                                    TheadClueWeb2.join();
                                    TheadClueWeb3.join();
                                    TheadClueWeb4.join();
                                    TheadClueWeb5.join();
                                    TheadClueWeb6.join();
                                    TheadClueWeb7.join();
                                    TheadClueWeb8.join();
                                    TheadClueWeb9.join();
                                    TheadClueWeb10.join();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(GetTXTData_USA_ToCategory.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                UtilitBD_AllPairsDataToCategory.disconnect();
                                ListAllPairsData.clear();
                            } else {
                                Thread_ClueWeb_to_AllPairsData_To_Category TheadClueWeb1 = new Thread_ClueWeb_to_AllPairsData_To_Category(ListAllPairsData);
                                TheadClueWeb1.start();
                                TheadClueWeb1.join();
                            }
                            UtilitBD_AllPairsDataToCategory.disconnect();
                        }
                        
                    }
                } else {
                    System.out.println("MAIOR ou MENOR que tamanho 3!");
                }
            }
            scan.close();
            BR.close();
        } catch (IOException ex) {
            Logger.getLogger(GetTXTData_USA_ToCategory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GetTXTData_USA_ToCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
