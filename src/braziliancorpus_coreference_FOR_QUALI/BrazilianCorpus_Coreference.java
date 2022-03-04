/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package braziliancorpus_coreference_FOR_QUALI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maisa Duarte
 */
public class BrazilianCorpus_Coreference {

    int ind;

    public static void main(String[] args) {
        BrazilianCorpus_Coreference Exec = new BrazilianCorpus_Coreference();
        String path = "D:\\UFSCar\\AllPairsData\\relation\\brasileiro\\tagged";
        File dir = new File(path);

        String[] children = dir.list();
        if (children == null) {
            // Either dir does not exist or is not a directory  
        } else {
            for (int i = 0; i < children.length; i++) {
                // Get filename of file or directory  
                // String ToExtract = "< " + children[ind] + " >";
                System.out.println(children[i]);
                Exec.ReadFile(children[i], path);
               
            }
        }
    }

    public void ENsPairExtraction(String line, String seed) {


        HelperMethods HelperMethods = new HelperMethods();

        String ContextRel = seed;
        seed = HelperMethods.RemoveDuplicateBlanks("< " + seed + " >");
        line = line.substring(line.indexOf(" ")).replace(" /", "/").replace("\\ /V", "").replace(",/VIRG", "").replace("“", "").replace("”", "").replace("\"", "").replace("'", "");
        line = HelperMethods.RemoveDuplicateBlanks(line.replace(" /", ""));
        line = line.replace("\\/V QUOTE", "");

        // System.out.println(line);

        String ENPair_left = HelperMethods.RemoveDuplicateBlanks(line.substring(0, line.indexOf("<")));
        String ENPair_right = HelperMethods.RemoveDuplicateBlanks(line.substring(line.indexOf(">") + 1));

        ENPair_left = HelperMethods.GetEN(ENPair_left, 2);
        if (!ENPair_left.isEmpty()) {
            ENPair_right = HelperMethods.GetEN(ENPair_right, 3);
        }
        if (!ENPair_left.isEmpty() && !ENPair_right.isEmpty()) {
            System.out.println(ind + "        " + ENPair_left + " _" + ContextRel + "_ " + ENPair_right);
            WriteFile(ENPair_left + " _" + ContextRel + "_ " + ENPair_right, true);
            ind++;
        }

    }

    public void ReadFile(String fileName, String path) {
        try {
             ind = 1;
            BufferedReader in = new BufferedReader(new FileReader(path + "\\" + fileName));
            String line;
            while (in.ready()) {
                line = in.readLine();
                ENsPairExtraction(line, fileName.substring(0, fileName.indexOf(".")));
            }
            in.close();
        } catch (IOException e) {
        }
    }

    public void WriteFile(String ToWrite, boolean bool) {

        BufferedWriter bw;
        try {

            bw = new BufferedWriter(new FileWriter("D:\\UFSCar\\AllPairsData\\relation\\all-pairs-brasileiro-coreference.txt", bool));
            bw.write(ToWrite + "\n");
            bw.close();

        } catch (IOException ex) {
            Logger.getLogger(BrazilianCorpus_Coreference.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
