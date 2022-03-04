/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

import AllPairsData.ObjectAllPairsData_BrazilianCorpus;
import java.util.List;

/**
 *
 * @author MaisaDuarte
 */
public class WriteInBDThread extends Thread {

    public synchronized void WriteFileThread(List<ObjectAllPairsData_BrazilianCorpus> ObjectAllPairsData_BrazilianCorpus) {

        UtilitBD_AllPairsDataToCategory UtilitBD_AllPairsDataToCategory = new UtilitBD_AllPairsDataToCategory();
        UtilitBD_AllPairsDataToCategory.connect();

        String Temp = "";
        int x = 0;
        Temp = ("(" + ObjectAllPairsData_BrazilianCorpus.get(x).getContext()
                + "," + ObjectAllPairsData_BrazilianCorpus.get(x).getEN()
                + "," + ObjectAllPairsData_BrazilianCorpus.get(x).getOccurrence() + "),");
        x++;
        while (x < ObjectAllPairsData_BrazilianCorpus.size() - 1) {
            Temp = ("(" + ObjectAllPairsData_BrazilianCorpus.get(x).getContext()
                    + "," + ObjectAllPairsData_BrazilianCorpus.get(x).getEN()
                    + "," + ObjectAllPairsData_BrazilianCorpus.get(x).getOccurrence() + "),");
            x++;
        }
        Temp = ("(" + ObjectAllPairsData_BrazilianCorpus.get(x).getContext()
                + "," + ObjectAllPairsData_BrazilianCorpus.get(x).getEN()
                + "," + ObjectAllPairsData_BrazilianCorpus.get(x).getOccurrence() + ");");
        Temp = Temp.trim();

        UtilitBD_AllPairsDataToCategory.insertGenericPairs_BRCorpus(Temp);
        UtilitBD_AllPairsDataToCategory.disconnect();
        System.gc();
    }
}
