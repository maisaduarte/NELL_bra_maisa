/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AllPairsData;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import parser.Main;
import parser.PagesParser;
import utilit.UtilitBD_AllPairsDataToCategory;

public class AllPairsData_Old {

    static List ListStopWords = new ArrayList(); //Lista de Stop Words
    static List AllPairsData_tobeProcessed = new ArrayList(); //Lista de Stop Words

    public static void PreProcessedText() {
        ListStopWords = PagesParser.ReadTXTtoListSIMPLE(Main.StopWords);
        System.out.println(Main.pathJava + "\\" + "AllPairsData_Old.txt");
        AllPairsData_tobeProcessed = ReadTXTtoListOBject(Main.pathJava + "\\" + "AllPairsData_Old.txt");

           }

    public static void main(String[] args) {
        PreProcessedText();
    }

    public static List ReadTXTtoListOBject(String File) {
        int ContGeral = -1;
        int ContEspecific = -1;
        int ContEspecificTemp = -1;
        String side = "";
        String Context;
        String EN;
        String tag;
        FileInputStream in = null;
        List<ObjectAllPairsData> ListObject = new ArrayList(); //Lista de seleção de  sentença de uma página
        List<ObjectAllPairsData> ListObjectCumulate = new ArrayList(); //Lista de sentenças de uma página

        try {
            in = new FileInputStream(File);
            String line = "";
            BufferedInputStream BR = null;
            BR = new BufferedInputStream(in);

            CharsetMatch cm = null;
            CharsetDetector cd = new CharsetDetector();
            cd.setText(BR);
            cm = cd.detect();
            Scanner scan = new Scanner(BR, cm.getName());
            scan.useDelimiter("[\n]+");
            
            UtilitBD_AllPairsDataToCategory UtilitBD_AllPairsDataToCategory = new UtilitBD_AllPairsDataToCategory();

            while (scan.hasNext()) {

                line = scan.next();
                line = line.trim();
                System.out.println(line);

                /*
                 * ContGeral = Integer.parseInt(line.substring(0,
                 * line.indexOf(","))); line = line.substring(line.indexOf(",")
                 * + 1);
                 *
                 * ContEspecific = Integer.parseInt(line.substring(0,
                 * line.indexOf(","))); line = line.substring(line.indexOf(",")
                 * + 1);
                 *
                 * side = String.valueOf(line.charAt(line.indexOf(",") - 1));
                 * line = line.substring(line.indexOf(",") + 1);
                 *
                 * Context = line.substring(0, line.indexOf(",")); line =
                 * line.substring(line.indexOf(",") + 1);
                 *
                 * EN = line.substring(0, line.indexOf(",")); line =
                 * line.substring(line.indexOf(",") + 1);
                 *
                 * tag = line;
                 */

                ContGeral = Integer.parseInt(line.substring(0, line.indexOf(",")));
                line = line.substring(line.indexOf(",") + 1);

                ContEspecific = Integer.parseInt(line.substring(0, line.indexOf(",")));
                line = line.substring(line.indexOf(",") + 1);

                side = String.valueOf(line.charAt(line.indexOf(",") - 1));
                line = line.substring(line.indexOf(",") + 1);

                Context = line.substring(0, line.indexOf(","));
                line = line.substring(line.indexOf(",") + 1);

                EN = line;
                //Remover e colocar o debaixo, esse lê um registro a cada até 5 


                if (ContEspecific > ContEspecificTemp) {
                    ListObject.add(new ObjectAllPairsData(ContGeral, ContEspecific, side, Context, EN, null));
                    ContEspecificTemp = ContEspecific;
                } else if ((ContEspecific == 0) || (scan.hasNext() == false)) {
                    int i = 0;
                    Pattern pattern = Pattern.compile("[A-Z]+");
                    Matcher matcher = pattern.matcher(ListObject.get(ListObject.size() - 1).getEN().toString());
                    Boolean auxName = false;
                    if (matcher.find()) {
                        auxName = true;
                    }

                    ListObject.get(ListObject.size() - 1).setEN(RemoveSTWInEnd(ListObject.get(ListObject.size() - 1).EN.toString(), auxName));
                    if (ListObject.get(ListObject.size() - 1).getEN().isEmpty()) {
                        ListObject.clear();
                    }

                     //Até aqui   e o } lá em baixo!

                    /*
                     * Colocar essa parte para código atualizado, em que só é
                     * necessário ler uma linha
                     *
                     * ListObject.add(new ObjectAllPairsData(ContGeral,
                     * ContEspecific, side, Context, EN, null));
                     *
                     * Pattern pattern = Pattern.compile("[A-Z]+"); Matcher
                     * matcher =
                     * pattern.matcher(ListObject.get(ListObject.size() -
                     * 1).getEN().toString()); Boolean auxName = false; if
                     * (matcher.find()) { auxName = true; }
                     *
                     * ListObject.get(ListObject.size() -
                     * 1).setEN(RemoveSTWInEnd(ListObject.get(ListObject.size()
                     * - 1).EN.toString(), auxName)); if
                     * (ListObject.get((ListObject.size() -
                     * 1)).getEN().isEmpty()) {
                     * ListObject.remove((ListObject.size() - 1)); }
                     */
                    //último 1 é ref ao etiquetador - verificar
                    if (!ListObject.isEmpty()) {
                        ListObjectCumulate.add(new ObjectAllPairsData(0, 0, ListObject.get(ListObject.size() - 1).side.toString(),
                                ListObject.get(ListObject.size() - 1).Context.toString(), ListObject.get(ListObject.size() - 1).EN.toString(), null));
                    }

                    if ((ListObjectCumulate.size() == 100) || (!scan.hasNext())) {
                        try {
                            UtilitBD_AllPairsDataToCategory.connect();
                            int y = 0;
                            while (y < ListObjectCumulate.size()) {
                                UtilitBD_AllPairsDataToCategory.ResolveDB_Update_OR_Insert(ListObjectCumulate.get(y).Context.toString(),
                                        ListObjectCumulate.get(y).side.toString(), ListObjectCumulate.get(y).EN.toString(), /*
                                         * tag
                                         */ null, 1);
                                y++;
                            }
                        } catch (Exception e) {
                            System.out.println("vai tomar café pq lascou!");
                        } finally {
                            ListObjectCumulate.clear();
                            ListObjectCumulate = null;
                            ListObjectCumulate = new ArrayList(); //Lista de sentenças de uma página
                            UtilitBD_AllPairsDataToCategory.disconnect();
                            System.gc();
                        }
                    }
                    ListObject.clear();
                    ListObject = null;
                    ListObject = new ArrayList(); //Lista de seleção de  sentença de uma página
                    ContEspecificTemp = 0; // remover
                    ListObject.add(new ObjectAllPairsData(ContGeral, ContEspecific, side, Context, EN, null));

                } //REmover tb!
            }
        } catch (IOException ex) {
            Logger.getLogger(AllPairsData_Old.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(AllPairsData_Old.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ListObject;
    }

    public static boolean SearchStopWords(String word) {
        boolean resp = false;
        int sizeVet;
        int index = 0;
        sizeVet = ListStopWords.size();

        if (word.matches("^[0-9]*$")) { // verifica se é um número (somente números, 3M por exemplo irá passar)
            resp = true;
        } else if (sizeVet > 0) {
            while (index < sizeVet) {
                if (ListStopWords.get(index).toString().equalsIgnoreCase(word)) {
                    resp = true;
                    index = ListStopWords.size();
                } else {
                    index++;
                }
            }
        }
        word = null;
        return resp;
    }

    public static boolean IsName(String IsName) {
        boolean result = false;
        char IsNameOrginal = IsName.charAt(0);
        char IsNameAltered = Character.toUpperCase(IsNameOrginal);
        if (IsNameOrginal == IsNameAltered) {
            result = true;
        } else {
            result = false;
        }
        IsName = null;
        return result;
    }

    public static String RemoveSTWInEnd(String aux, boolean IsName) {
        String[] auxCath = aux.split(" ");
        String resp = "";
        int y = 0;
        int i = 0;
        boolean contin = true;

        while (contin) {
            if (y < auxCath.length) {
                if (SearchStopWords(auxCath[y]) == true) {
                    contin = true;
                    auxCath[y] = "";
                } else if ((IsName(auxCath[y]) == false) && (IsName == true)) {
                    contin = true;
                    auxCath[y] = "";
                } else {
                    contin = false;
                }
                y++;
            } else {
                contin = false;
            }
        }

        contin = true;
        y = auxCath.length - 1;
        while (contin) {
            if (y >= 0) {
                if (SearchStopWords(auxCath[y]) == true) {
                    contin = true;
                    auxCath[y] = "";
                } else if ((IsName(auxCath[y]) == false) && (IsName == true)) {
                    contin = true;
                    auxCath[y] = "";
                } else {
                    contin = false;
                }
                y--;
            } else {
                contin = false;
            }
        }

        i = 0;
        while (i < auxCath.length) {
            resp = resp + " " + auxCath[i];
            i++;
        }

        aux = null;
        auxCath = null;

        resp = resp.trim();
        return resp;
    }
}
