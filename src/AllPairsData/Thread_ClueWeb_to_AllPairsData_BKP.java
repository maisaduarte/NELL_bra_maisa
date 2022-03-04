/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AllPairsData;

import UPClueWeb.ObjectENContextOccurrence;
import java.util.ArrayList;
import java.util.List;
import utilit.UtilitBD_AllPairsDataToCategory;

/**
 *
 * @author MaisaDuarte
 */
public class Thread_ClueWeb_to_AllPairsData_BKP extends Thread {

    List<ObjectENContextOccurrence> ListAllPairsData = new ArrayList();

    public Thread_ClueWeb_to_AllPairsData_BKP(List<ObjectENContextOccurrence> ListAllPairsData) {
        this.ListAllPairsData = ListAllPairsData;

    }

    @Override
    public void run() {
        UtilitBD_AllPairsDataToCategory UtilitBD_AllPairsDataToCategory = new UtilitBD_AllPairsDataToCategory();
        UtilitBD_AllPairsDataToCategory.setDATABASE_URL("jdbc:mysql://rtw.ml.cmu.edu:3306/corpusbr");
        UtilitBD_AllPairsDataToCategory.setUser("corpusbr");
        UtilitBD_AllPairsDataToCategory.setPassword("!rbsuproc!55555");        
        
        UtilitBD_AllPairsDataToCategory.connect();
        if (ListAllPairsData.size() > 0) {
            UtilitBD_AllPairsDataToCategory.InsertClueWeb_To_Category(ListAllPairsData);
        }
        UtilitBD_AllPairsDataToCategory.disconnect();
    }
}
