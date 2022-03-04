package AllPairsData;


import java.util.ArrayList;
import java.util.List;
import parser.PagesParser;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MaisaDuarte
 */
public class ClearTextSEEDs {

    public static void main(String[] args) {
        List Aux = new ArrayList();
        Aux = PagesParser.ClearLabeledDoc_CompleteWithUnderline("D:\\UFSCar\\AllPairsData\\padroes - Leonardo\\seed_complete.txt",0);
        //Aux = PagesParser.CountGramsSentence(Aux);
        PagesParser.SaveListInFile("D:\\UFSCar\\AllPairsData\\padroes - Leonardo\\SEED_out_leonardo.txt", Aux, true);
    }
}
