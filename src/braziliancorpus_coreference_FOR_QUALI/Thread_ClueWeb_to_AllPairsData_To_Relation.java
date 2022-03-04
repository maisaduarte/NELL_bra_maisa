/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package braziliancorpus_coreference_FOR_QUALI;

import UPClueWeb.*;
import UPClueWeb.ObjectENContextOccurrence;
import java.util.ArrayList;
import java.util.List;
import utilit.UtilitBD_AllPairsDataToCategory;

/**
 *
 * @author MaisaDuarte
 */
public class Thread_ClueWeb_to_AllPairsData_To_Relation extends Thread {

    List<ObjectENsRelationContextOccurrence> ListAllPairsData = new ArrayList();

    public Thread_ClueWeb_to_AllPairsData_To_Relation(List<ObjectENsRelationContextOccurrence> ListAllPairsData) {
        this.ListAllPairsData = ListAllPairsData;

    }

    @Override
    public void run() {
        UtilitBD_AllPairsDataToCategory UtilitBD_AllPairsDataToCategory = new UtilitBD_AllPairsDataToCategory();
               
        if (ListAllPairsData.size() > 0) {
            UtilitBD_AllPairsDataToCategory.InsertClueWeb_To_RelationSemantic(ListAllPairsData);
        }
        
    }
}
