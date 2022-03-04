/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nell_bra;

import UPClueWeb.GetTXTData_USA_ToCategory;
import RTWP.CategoriesLearning;
import RTWP.SemanticRelationLearning;
import java.util.Scanner;

/**
 *
 * @author MaisaDuarte
 */
public class NELL_bra {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        int codeConf = 0;
        int code = 0;
        int CatorRel = 0;

        Scanner in = new Scanner(System.in);

        while (codeConf == 0) {
            System.out.println("Enter the CODE to run:");
            System.out.println(" - 1 - RTPW / Nell in portuguese");
            System.out.println(" - 2 - Create AllPairsData from TXT to MySQL");

            code = Integer.parseInt(in.nextLine());

            System.out.println("CODE selected: " + code);
            System.out.println("Confirm CODE (1)YES or (2)NO or (9)ToExit:" + code);
            codeConf = Integer.parseInt(in.nextLine());
            System.out.println("Select (1)for Category or (2) for Relation (9)ToExit:" + code);
            CatorRel = Integer.parseInt(in.nextLine());

        }

        String fileToGet = "A";

        if (code == 1) {
            if (CatorRel == 1) {
                CategoriesLearning CL = new CategoriesLearning();
                CL.CategoriesLearning();
            } else if (CatorRel == 2) {
                SemanticRelationLearning SL = new SemanticRelationLearning();
                SL.SemanticRelationLearning();
            }
            in.close();
        } else if (code == 2) {

            while (fileToGet.equalsIgnoreCase("A")) {
                System.out.println("Enter the AllPairsData file directory:");
                fileToGet = in.nextLine();
                /*System.out.println("Enter Database name:");
                 String Database = in.nextLine();
                 System.out.println("Enter Database name:");
                 String password = in.nextLine();*/
                if ((fileToGet.equalsIgnoreCase("exit"))) { // || (Database.equalsIgnoreCase("exit")))) {
                    break;
                }
            }
            in.close();

            GetTXTData_USA_ToCategory CreateAllPairsData = new GetTXTData_USA_ToCategory(fileToGet);
            CreateAllPairsData.GetTXTData_USA();
        }

        System.out.println("Finished!!!");

    }
}
