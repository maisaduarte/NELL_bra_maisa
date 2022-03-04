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
public class GetTXTData_USA_ToRelation {

    String fileToGet;

    public GetTXTData_USA_ToRelation(String fileToGet) {
        this.fileToGet = fileToGet;
    }

    public void GetTXTData_USA() {
        try {
            FileInputStream in = null;
            String[] brokenSentence = null;
            String sentence = "";
            String EN_Left = "";
            String EN_Right = "";
            String RelationContext = "";
            int Occurrence;
            List<ObjectENsRelationContextOccurrence> ListAllPairsData = new ArrayList();
            try {
                in = new FileInputStream(fileToGet);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GetTXTData_USA_ToRelation.class.getName()).log(Level.SEVERE, null, ex);
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
                //sentence = sentence.replace("  ", ",");
                sentence = sentence.replace("	", ",");

                sentence = sentence.trim();
                brokenSentence = sentence.split(",");

                if (brokenSentence.length == 4) {

                    EN_Left = ClearSentences.RemoveDuplicateBlanks(brokenSentence[0].trim());
                    RelationContext = ClearSentences.RemoveDuplicateBlanks(brokenSentence[1].trim());
                    EN_Right = ClearSentences.RemoveDuplicateBlanks(brokenSentence[2].trim());
                    Occurrence = Integer.valueOf((brokenSentence[3]).trim());

                    RelationContext = RelationContext.replace("_", "").trim();

                    if (!EN_Left.isEmpty() && !EN_Right.isEmpty() && !RelationContext.isEmpty() && Occurrence > 0) {
                        ListAllPairsData.add(new ObjectENsRelationContextOccurrence(EN_Left, EN_Right, RelationContext, Occurrence));
                    }
                    EN_Left = "";
                    EN_Right = "";
                    RelationContext = "";
                    Occurrence = 0;

                    if ((ListAllPairsData.size() == 1000) || (!scan.hasNext())) {
                        UtilitBD_AllPairsDataToCategory.setDATABASE_URL("jdbc:mysql://rtw.ml.cmu.edu:3306/corpusbr");
                        UtilitBD_AllPairsDataToCategory.setUser("corpusbr");
                        UtilitBD_AllPairsDataToCategory.setPassword("!rbsuproc!55555");
                        UtilitBD_AllPairsDataToCategory.connect();

                        if (ListAllPairsData.size() == 1000) {

                            Thread_ClueWeb_to_AllPairsData_To_Relation TheadClueWeb1 = new Thread_ClueWeb_to_AllPairsData_To_Relation(ListAllPairsData.subList(0, 100));
                            TheadClueWeb1.start();

                            Thread_ClueWeb_to_AllPairsData_To_Relation TheadClueWeb2 = new Thread_ClueWeb_to_AllPairsData_To_Relation(ListAllPairsData.subList(100, 200));
                            TheadClueWeb2.start();

                            Thread_ClueWeb_to_AllPairsData_To_Relation TheadClueWeb3 = new Thread_ClueWeb_to_AllPairsData_To_Relation(ListAllPairsData.subList(200, 300));
                            TheadClueWeb3.start();

                            Thread_ClueWeb_to_AllPairsData_To_Relation TheadClueWeb4 = new Thread_ClueWeb_to_AllPairsData_To_Relation(ListAllPairsData.subList(300, 400));
                            TheadClueWeb4.start();

                            Thread_ClueWeb_to_AllPairsData_To_Relation TheadClueWeb5 = new Thread_ClueWeb_to_AllPairsData_To_Relation(ListAllPairsData.subList(400, 500));
                            TheadClueWeb5.start();

                            Thread_ClueWeb_to_AllPairsData_To_Relation TheadClueWeb6 = new Thread_ClueWeb_to_AllPairsData_To_Relation(ListAllPairsData.subList(500, 600));
                            TheadClueWeb6.start();

                            Thread_ClueWeb_to_AllPairsData_To_Relation TheadClueWeb7 = new Thread_ClueWeb_to_AllPairsData_To_Relation(ListAllPairsData.subList(600, 700));
                            TheadClueWeb7.start();

                            Thread_ClueWeb_to_AllPairsData_To_Relation TheadClueWeb8 = new Thread_ClueWeb_to_AllPairsData_To_Relation(ListAllPairsData.subList(700, 800));
                            TheadClueWeb8.start();

                            Thread_ClueWeb_to_AllPairsData_To_Relation TheadClueWeb9 = new Thread_ClueWeb_to_AllPairsData_To_Relation(ListAllPairsData.subList(800, 900));
                            TheadClueWeb9.start();

                            Thread_ClueWeb_to_AllPairsData_To_Relation TheadClueWeb10 = new Thread_ClueWeb_to_AllPairsData_To_Relation(ListAllPairsData.subList(900, 1000));
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
                                Logger.getLogger(GetTXTData_USA_ToRelation.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            UtilitBD_AllPairsDataToCategory.disconnect();
                            ListAllPairsData.clear();
                        } else {
                            Thread_ClueWeb_to_AllPairsData_To_Relation TheadClueWeb1 = new Thread_ClueWeb_to_AllPairsData_To_Relation(ListAllPairsData);
                            TheadClueWeb1.start();
                            TheadClueWeb1.join();
                        }
                        UtilitBD_AllPairsDataToCategory.disconnect();
                    }

                } else {
                    System.out.println("MAIOR ou MENOR que tamanho 3!");
                }
            }
            scan.close();
            BR.close();


        } catch (IOException ex) {
            Logger.getLogger(GetTXTData_USA_ToRelation.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GetTXTData_USA_ToRelation.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
