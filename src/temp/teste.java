/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import parser.Main;

/**
 *
 * @author MaisaDuarte
 */
public class teste {

    private byte[] warcContent = null;

    public void setWarcContent(byte[] warcContent) {
        this.warcContent = warcContent;
    }

    public byte[] getWarcContent() {
        return warcContent;
    }

    public String getContentOfAllCharset() {

        BufferedInputStream BR_temp = new BufferedInputStream(new ByteArrayInputStream(warcContent));

        CharsetDetector cd = new CharsetDetector();
        cd.setText(warcContent);
        CharsetMatch cm = cd.detect();

        String retString = "";
        try (Scanner scan = new Scanner(BR_temp, cm.getName())) {
            scan.useDelimiter("[\n\r]+");
            while (scan.hasNext()) {
                retString = (retString.trim() + " " + scan.next().trim());
            }
            scan.close();
        }
        return retString;
    }

    public static String convertUNICODE(String str) {
        StringBuffer ostr = new StringBuffer();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            /* caracter precisa ser convertido para unicode? */
            if ((ch >= 0x0020) && (ch <= 0x007e)) {
                /* não */
                ostr.append(ch);
            } else {
                /* sim */
                ostr.append("\\u"); /* formato de unicode padrão */
                /* pega o valor hexadecimal do caracter */
                String hex = Integer.toHexString(str.charAt(i) & 0xFFFF);
                for (int j = 0; j < 4 - hex.length(); j++) {
                    /* concatena o zero porque o unicode requer 4 digitos */
                    ostr.append("0");
                }
                ostr.append(hex.toLowerCase());
            }
        }
        return (new String(ostr));
    }

    public void executeThis() {

        FileInputStream in = null;
        try {
            in = new FileInputStream("D:\\UFSCar\\Doutorado\\RTWP\\Codigo\\NELL_bra_05\\saida_iso_part_text.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(teste.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedInputStream BR = null;
        BR = new BufferedInputStream(in);

        CharsetDetector cd = new CharsetDetector();
        try {
            cd.setText(BR);
        } catch (IOException ex) {
            Logger.getLogger(teste.class.getName()).log(Level.SEVERE, null, ex);
        }
        CharsetMatch cm = cd.detect();

        Scanner scan = new Scanner(BR, cm.getName());
        scan.useDelimiter("[\n]+");

        //scan.useDelimiter("[\t\n\r]+");
        String line = "";
        while (scan.hasNext()) {
            line = line + " " + scan.next();
        }
        warcContent = line.getBytes();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        teste teste = new teste();
        teste.executeThis();
       // System.out.println(teste.getContentUTF8());

    }

    public static String converteroTrem(String sentence) {

        sentence = sentence.replace("\u00e1", "á");
        sentence = sentence.replace("\u00e0", "à");
        sentence = sentence.replace("\u00e2", "â");
        sentence = sentence.replace("\u00e3", "ã");
        sentence = sentence.replace("\u00e4", "ä");
        sentence = sentence.replace("\u00c1", "Á");
        sentence = sentence.replace("\u00c0", "À");
        sentence = sentence.replace("\u00c2", "Â");
        sentence = sentence.replace("\u00c3", "Ã");
        sentence = sentence.replace("\u00c4", "Ä");
        sentence = sentence.replace("\u00e9", "é");
        sentence = sentence.replace("\u00e8", "è");
        sentence = sentence.replace("\u00ea", "ê");
        sentence = sentence.replace("\u00ea", "ê");
        sentence = sentence.replace("\u00c9", "É");
        sentence = sentence.replace("\u00c8", "È");
        sentence = sentence.replace("\u00ca", "Ê");
        sentence = sentence.replace("\u00cb", "Ë");
        sentence = sentence.replace("\u00ed", "í");
        sentence = sentence.replace("\u00ec", "ì");
        sentence = sentence.replace("\u00ee", "î");
        sentence = sentence.replace("\u00ef", "ï");
        sentence = sentence.replace("\u00cd", "Í");
        sentence = sentence.replace("\u00cc", "Ì");
        sentence = sentence.replace("\u00ce", "Î");
        sentence = sentence.replace("\u00cf", "Ï");
        sentence = sentence.replace("\u00f3", "ó");
        sentence = sentence.replace("\u00f2", "ò");
        sentence = sentence.replace("\u00f4", "ô");
        sentence = sentence.replace("\u00f5", "õ");
        sentence = sentence.replace("\u00f6", "ö");
        sentence = sentence.replace("\u00d3", "Ó");
        sentence = sentence.replace("\u00d2", "Ò");
        sentence = sentence.replace("\u00d4", "Ô");
        sentence = sentence.replace("\u00d5", "Õ");
        sentence = sentence.replace("\u00d6", "Ö");
        sentence = sentence.replace("\u00fa", "ú");
        sentence = sentence.replace("\u00f9", "ù");
        sentence = sentence.replace("\u00fb", "û");
        sentence = sentence.replace("\u00fc", "ü");
        sentence = sentence.replace("\u00da", "Ú");
        sentence = sentence.replace("\u00d9", "Ù");
        sentence = sentence.replace("\u00db", "Û");
        sentence = sentence.replace("\u00e7", "ç");
        sentence = sentence.replace("\u00c7", "Ç");
        sentence = sentence.replace("\u00f1", "ñ");
        sentence = sentence.replace("\u00d1", "Ñ");
        sentence = sentence.replace("\u0026", "&");
        sentence = sentence.replace("\u0027", "'");

        return sentence;
    }

    public static String geraCodigoUnicode(char letra) {
        String hexa = Integer.toHexString((int) letra);

        String prefix;
        if (hexa.length() == 1) {
            prefix = "\\u000";
        } else if (hexa.length() == 2) {
            prefix = "\\u00";
        } else if (hexa.length() == 3) {
            prefix = "\\u0";
        } else {
            prefix = "\\u";
        }

        return prefix + hexa;
    }
}