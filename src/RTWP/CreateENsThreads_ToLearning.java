/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RTWP;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilit.UtilitBD_AllPairsDataToCategory;
import utilit.UtilitBD_RTWP_TOCategory;

/**
 *
 * @author MaisaDuarte
 */
public class CreateENsThreads_ToLearning {

    public static void CreateENsThreads_ToLearning() {
        UtilitBD_AllPairsDataToCategory UtilitBD_AllPairsDataToCategory = new UtilitBD_AllPairsDataToCategory();
        UtilitBD_RTWP_TOCategory UtilitBD_RTWP_TOCategory = new UtilitBD_RTWP_TOCategory();
        UtilitBD_RTWP_TOCategory.connect();
        UtilitBD_AllPairsDataToCategory.connect();

        List<Categories> CategoriesList = new ArrayList<>();
        //UtilitBD_RTWP_TOCategory.connect();
        CategoriesList = UtilitBD_RTWP_TOCategory.GetCategories();

        Learning_EN Learning_EN_Cat1 = new Learning_EN(CategoriesList.get(0).idCategory, CategoriesList.get(0).isName);
        Learning_EN_Cat1.start();

        Learning_EN Learning_EN_Cat2 = new Learning_EN(CategoriesList.get(1).idCategory, CategoriesList.get(1).isName);
        Learning_EN_Cat2.start();

        Learning_EN Learning_EN_Cat3 = new Learning_EN(CategoriesList.get(2).idCategory, CategoriesList.get(2).isName);
        Learning_EN_Cat3.start();

        Learning_EN Learning_EN_Cat4 = new Learning_EN(CategoriesList.get(3).idCategory, CategoriesList.get(3).isName);
        Learning_EN_Cat4.start();

        Learning_EN Learning_EN_Cat5 = new Learning_EN(CategoriesList.get(4).idCategory, CategoriesList.get(4).isName);
        Learning_EN_Cat5.start();

        Learning_EN Learning_EN_Cat6 = new Learning_EN(CategoriesList.get(5).idCategory, CategoriesList.get(5).isName);
        Learning_EN_Cat6.start();

        Learning_EN Learning_EN_Cat7 = new Learning_EN(CategoriesList.get(6).idCategory, CategoriesList.get(6).isName);
        Learning_EN_Cat7.start();

        Learning_EN Learning_EN_Cat8 = new Learning_EN(CategoriesList.get(7).idCategory, CategoriesList.get(7).isName);
        Learning_EN_Cat8.start();

        Learning_EN Learning_EN_Cat9 = new Learning_EN(CategoriesList.get(8).idCategory, CategoriesList.get(8).isName);
        Learning_EN_Cat9.start();

        Learning_EN Learning_EN_Cat10 = new Learning_EN(CategoriesList.get(9).idCategory, CategoriesList.get(9).isName);
        Learning_EN_Cat10.start();

        Learning_EN Learning_EN_Cat11 = new Learning_EN(CategoriesList.get(10).idCategory, CategoriesList.get(10).isName);
        Learning_EN_Cat11.start();

        Learning_EN Learning_EN_Cat12 = new Learning_EN(CategoriesList.get(11).idCategory, CategoriesList.get(11).isName);
        Learning_EN_Cat12.start();

        Learning_EN Learning_EN_Cat14 = new Learning_EN(CategoriesList.get(12).idCategory, CategoriesList.get(12).isName);
        Learning_EN_Cat14.start();

        Learning_EN Learning_EN_Cat15 = new Learning_EN(CategoriesList.get(13).idCategory, CategoriesList.get(13).isName);
        Learning_EN_Cat15.start();

        Learning_EN Learning_EN_Cat16 = new Learning_EN(CategoriesList.get(14).idCategory, CategoriesList.get(14).isName);
        Learning_EN_Cat16.start();

        /*if (Learning_EN_Cat1.isAlive() || Learning_EN_Cat2.isAlive() 
         || Learning_EN_Cat3.isAlive() || Learning_EN_Cat4.isAlive()
         || Learning_EN_Cat5.isAlive() || Learning_EN_Cat6.isAlive()
         || Learning_EN_Cat7.isAlive() || Learning_EN_Cat8.isAlive()
         || Learning_EN_Cat9.isAlive() || Learning_EN_Cat10.isAlive()
         || Learning_EN_Cat11.isAlive() || Learning_EN_Cat12.isAlive()
         || Learning_EN_Cat14.isAlive() || Learning_EN_Cat15.isAlive()
         || Learning_EN_Cat16.isAlive()
         ){
                    
         }*/
        try {

            Learning_EN_Cat1.join();
            Learning_EN_Cat2.join();
            Learning_EN_Cat3.join();
            Learning_EN_Cat4.join();
            Learning_EN_Cat5.join();
            Learning_EN_Cat6.join();
            Learning_EN_Cat7.join();
            Learning_EN_Cat8.join();
            Learning_EN_Cat9.join();
            Learning_EN_Cat10.join();
            Learning_EN_Cat11.join();
            Learning_EN_Cat12.join();
            Learning_EN_Cat14.join();
            Learning_EN_Cat15.join();
            Learning_EN_Cat16.join();

            UtilitBD_RTWP_TOCategory.disconnect();
            UtilitBD_AllPairsDataToCategory.disconnect();

        } catch (InterruptedException ex) {
            Logger.getLogger(CreateENsThreads_ToLearning.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Socorro em CreateENsThreads_ToLearning");
        }
    }
}
