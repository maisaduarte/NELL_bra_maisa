/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AllPairsData;

/**
 *
 * @author MaisaDuarte
 */
public class StartThread {

    public static void main(String[] args) {

        String pathComum = "D:\\UFSCar\\Doutorado\\RTWP\\Doutorado\\CorpusToThreads\\";


        GetBrazilianCorpus_Thread GBRTh1 = new GetBrazilianCorpus_Thread();
         GBRTh1.setFileToExtract(pathComum + "conc (é o)_0.txt");
         GBRTh1.setNameBDThread("E_O_0");
         GBRTh1.setSeed("é o");
         GBRTh1.setSide("ENL_CR");
         GBRTh1.setStringBKP("_E_O_0");
         GBRTh1.setPriority(Thread.MAX_PRIORITY);
         GBRTh1.start();

         GetBrazilianCorpus_Thread GBRTh2 = new GetBrazilianCorpus_Thread();
         GBRTh2.setFileToExtract(pathComum + "conc (é o)_1.txt");
         GBRTh2.setNameBDThread("E_O_1");
         GBRTh2.setSeed("é o");
         GBRTh2.setSide("ENL_CR");
         GBRTh2.setStringBKP("_E_O_1");
         GBRTh2.setPriority(Thread.MAX_PRIORITY);
         GBRTh2.start();
         
         GetBrazilianCorpus_Thread GBRTh3 = new GetBrazilianCorpus_Thread();
         GBRTh3.setFileToExtract(pathComum + "conc (é o)_2.txt");
         GBRTh3.setNameBDThread("E_O_2");
         GBRTh3.setSeed("é o");
         GBRTh3.setSide("ENL_CR");
         GBRTh3.setStringBKP("_E_O_2");
         GBRTh3.setPriority(Thread.MAX_PRIORITY);
         GBRTh3.start();

         GetBrazilianCorpus_Thread GBRTh4 = new GetBrazilianCorpus_Thread();
         GBRTh4.setFileToExtract(pathComum + "conc (é o)_3.txt");
         GBRTh4.setNameBDThread("E_O_3");
         GBRTh4.setSeed("é o");
         GBRTh4.setSide("ENL_CR");
         GBRTh4.setStringBKP("_E_O_3");
         GBRTh4.setPriority(Thread.MAX_PRIORITY);
         GBRTh4.start();

         GetBrazilianCorpus_Thread GBRTh5 = new GetBrazilianCorpus_Thread();
         GBRTh5.setFileToExtract(pathComum + "conc (é a)_0.txt");
         GBRTh5.setNameBDThread("E_A_0");
         GBRTh5.setSeed("é a");
         GBRTh5.setSide("ENL_CR");
         GBRTh5.setStringBKP("_E_A_0");
         GBRTh5.setPriority(Thread.MAX_PRIORITY);
         GBRTh5.start();

         GetBrazilianCorpus_Thread GBRTh6 = new GetBrazilianCorpus_Thread();
         GBRTh6.setFileToExtract(pathComum + "conc (é a)_1.txt");
         GBRTh6.setNameBDThread("E_A_1");
         GBRTh6.setSeed("é a");
         GBRTh6.setSide("ENL_CR");
         GBRTh6.setStringBKP("_E_A_1");
         GBRTh6.setPriority(Thread.MAX_PRIORITY);
         GBRTh6.start();

         GetBrazilianCorpus_Thread GBRTh7 = new GetBrazilianCorpus_Thread();
         GBRTh7.setFileToExtract(pathComum + "conc (é a)_2.txt");
         GBRTh7.setNameBDThread("E_A_2");
         GBRTh7.setSeed("é a");
         GBRTh7.setSide("ENL_CR");
         GBRTh7.setStringBKP("_E_A_2");
         GBRTh7.setPriority(Thread.MAX_PRIORITY);
         GBRTh7.start();
        
        GetBrazilianCorpus_Thread GBRTh8 = new GetBrazilianCorpus_Thread();
        GBRTh8.setFileToExtract(pathComum + "conc (é a)_3.txt");
        GBRTh8.setNameBDThread("E_A_3");
        GBRTh8.setSeed("é a");
        GBRTh8.setSide("ENL_CR");
        GBRTh8.setStringBKP("_E_A_3");
        GBRTh8.setPriority(Thread.MAX_PRIORITY);
        GBRTh8.start();
 
        GetBrazilianCorpus_Thread GBRTh9 = new GetBrazilianCorpus_Thread();
        GBRTh9.setFileToExtract(pathComum + "conc (é um)_0.txt");
        GBRTh9.setNameBDThread("E_UM_0");
        GBRTh9.setSeed("é um");
        GBRTh9.setSide("ENL_CR");
        GBRTh9.setStringBKP("_E_UM_0");
        GBRTh9.setPriority(Thread.MAX_PRIORITY);
        GBRTh9.start();

        GetBrazilianCorpus_Thread GBRTh10 = new GetBrazilianCorpus_Thread();
        GBRTh10.setFileToExtract(pathComum + "conc (é um)_1.txt");
        GBRTh10.setNameBDThread("E_UM_1");
        GBRTh10.setSeed("é um");
        GBRTh10.setSide("ENL_CR");
        GBRTh10.setStringBKP("_E_UM_1");
        GBRTh10.setPriority(Thread.MAX_PRIORITY);
        GBRTh10.start();

        GetBrazilianCorpus_Thread GBRTh11 = new GetBrazilianCorpus_Thread();
        GBRTh11.setFileToExtract(pathComum + "conc (é um)_2.txt");
        GBRTh11.setNameBDThread("E_UM_2");
        GBRTh11.setSeed("é um");
        GBRTh11.setSide("ENL_CR");
        GBRTh11.setStringBKP("_E_UM_2");
        GBRTh11.setPriority(Thread.MAX_PRIORITY);
        GBRTh11.start();

        GetBrazilianCorpus_Thread GBRTh12 = new GetBrazilianCorpus_Thread();
        GBRTh12.setFileToExtract(pathComum + "conc (é um)_3.txt");
        GBRTh12.setNameBDThread("E_UM_3");
        GBRTh12.setSeed("é um");
        GBRTh12.setSide("ENL_CR");
        GBRTh12.setStringBKP("_E_UM_3");
        GBRTh12.setPriority(Thread.MAX_PRIORITY);
        GBRTh12.start();

        GetBrazilianCorpus_Thread GBRTh13 = new GetBrazilianCorpus_Thread();
        GBRTh13.setFileToExtract(pathComum + "conc (é uma)_0.txt");
        GBRTh13.setNameBDThread("E_UMA_0");
        GBRTh13.setSeed("é uma");
        GBRTh13.setSide("ENL_CR");
        GBRTh13.setStringBKP("_E_UMA_0");
        GBRTh13.setPriority(Thread.MAX_PRIORITY);
        GBRTh13.start();

        GetBrazilianCorpus_Thread GBRTh14 = new GetBrazilianCorpus_Thread();
        GBRTh14.setFileToExtract(pathComum + "conc (é uma)_1.txt");
        GBRTh14.setNameBDThread("E_UMA_1");
        GBRTh14.setSeed("é uma");
        GBRTh14.setSide("ENL_CR");
        GBRTh14.setStringBKP("_E_UMA_1");
        GBRTh14.setPriority(Thread.MAX_PRIORITY);
        GBRTh14.start();

        GetBrazilianCorpus_Thread GBRTh15 = new GetBrazilianCorpus_Thread();
        GBRTh15.setFileToExtract(pathComum + "conc (é uma)_2.txt");
        GBRTh15.setNameBDThread("E_UMA_2");
        GBRTh15.setSeed("é uma");
        GBRTh15.setSide("ENL_CR");
        GBRTh15.setStringBKP("_E_UMA_2");
        GBRTh15.setPriority(Thread.MAX_PRIORITY);
        GBRTh15.start();

        GetBrazilianCorpus_Thread GBRTh16 = new GetBrazilianCorpus_Thread();
        GBRTh16.setFileToExtract(pathComum + "conc (é uma)_3.txt");
        GBRTh16.setNameBDThread("E_UMA_3");
        GBRTh16.setSeed("é uma");
        GBRTh16.setSide("ENL_CR");
        GBRTh16.setStringBKP("_E_UMA_3");
        GBRTh16.setPriority(Thread.MAX_PRIORITY);
        GBRTh16.start();
    }
}
