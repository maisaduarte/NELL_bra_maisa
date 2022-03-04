/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

import AllPairsData.ObjectAllPairsData_BrazilianCorpus;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author MaisaDuarte
 */
public class WriteFileThread extends Thread {

    public synchronized void WriteFileThread(List<ObjectAllPairsData_BrazilianCorpus> ObjectAllPairsData_BrazilianCorpus) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("export_braziliancorpus.txt"));
            int i = 0;
            out.write("INSERT INTO `pairs` (`codContext`,`codEN`,`occurrence`) VALUES ");
            while (i < ObjectAllPairsData_BrazilianCorpus.size() - 1) {
                out.write("(" + ObjectAllPairsData_BrazilianCorpus.get(i).getContext()
                        + "," + ObjectAllPairsData_BrazilianCorpus.get(i).getEN()
                        + "," + ObjectAllPairsData_BrazilianCorpus.get(i).getOccurrence() + "),");
                i++;
            }
            out.write("(" + ObjectAllPairsData_BrazilianCorpus.get(i).getContext()
                    + "," + ObjectAllPairsData_BrazilianCorpus.get(i).getEN()
                    + "," + ObjectAllPairsData_BrazilianCorpus.get(i).getOccurrence() + ");");
            out.close();
        } catch (IOException e) {
        } finally {
            System.gc();
        }

    }
}
