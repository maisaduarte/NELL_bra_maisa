/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AllPairsData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author MaisaDuarte
 */
public class ClearSentences {

    public static String CutPunctuation_toEN_CutRight(String sentence) {

        sentence = " " + sentence + " ";
        String RegExp = ("- | -|%|_|\"|\\\\|\\/|//|'|`|\\[|\\]|\\{|\\}|\\*|=|\\+|#|:|;|\\(|\\)|<|>|“|”|,|\\.|\\?|!");
        Pattern pattern = Pattern.compile(RegExp);
        Matcher matcher = pattern.matcher(sentence);
        String CutEN_Rigth = "";

        CutEN_Rigth = sentence;

        if (matcher.find()) {
            CutEN_Rigth = sentence.substring(0, matcher.start()).trim();
        }

        sentence = CutEN_Rigth;

        Pattern pattern2 = Pattern.compile("\\b([A-ZÀ-Ú]+)\\w([A-ZÀ-Ú]+)");
        Matcher matcher2 = pattern2.matcher(sentence);
        Pattern pattern1 = Pattern.compile("([a-zà-ú]+)");
        Matcher matcher1 = pattern1.matcher(sentence);

        if (matcher2.find() && (matcher1.find())) {
            CutEN_Rigth = sentence.substring(0, matcher2.start()).trim();
        }
        CutEN_Rigth = CutEN_Rigth.trim();

        String TempEN[] = CutEN_Rigth.split(" ");
        if (TempEN.length > 5) {
            int Ti = 0;
            String TempEN5 = "";
            while (Ti < 5) {
                TempEN5 = TempEN5 + " " + TempEN[Ti];
                Ti++;
            }
        }
        return CutEN_Rigth;
    }

    public static String CutPunctuation_toEN_CutLeft(String sentence) {

        sentence = " " + sentence + " ";
        String RegExp = ("- | -|%|_|\"|\\\\|\\/|//|'|`|\\[|\\]|\\{|\\}|\\*|=|\\+|#|:|;|\\(|\\)|<|>|“|”|,|\\.|\\?|!");
        Pattern pattern = Pattern.compile(RegExp);
        Matcher matcher = pattern.matcher(sentence);
        String CutEN_Left = "";

        CutEN_Left = sentence;

        while (matcher.find()) {
            CutEN_Left = sentence.substring(matcher.end()).trim();
        }

        sentence = CutEN_Left;

        Pattern pattern2 = Pattern.compile("\\b([A-ZÀ-Ú]+)\\w([A-ZÀ-Ú]+)");
        Matcher matcher2 = pattern2.matcher(sentence);
        Pattern pattern1 = Pattern.compile("([a-zà-ú]+)");
        Matcher matcher1 = pattern1.matcher(sentence);

        while (matcher2.find() && (matcher1.find())) {
            CutEN_Left = sentence.substring(matcher2.end()).trim();
        }

        CutEN_Left = CutEN_Left.trim();

        String TempEN[] = CutEN_Left.split(" ");
        if (TempEN.length > 5) {
            int Ti = TempEN.length - 1;
            int Ngrama = 5;
            String TempEN5 = "";
            while (Ngrama > 0) {
                TempEN5 = TempEN[Ti] + " " + TempEN5;
                Ti--;
                Ngrama--;
            }
            CutEN_Left = TempEN5;
        }
        CutEN_Left = CutEN_Left.trim();
        return CutEN_Left;
    }

    public static String ClearPunctuationGeneral_From_ClueWeb(String sentence) {

        sentence = sentence.replace("-", "");
        sentence = sentence.replace("\"", "");
        sentence = sentence.replace("\\", "");
        sentence = sentence.replace("/", "");
        sentence = sentence.replace("//", "");
        sentence = sentence.replace("'", "");
        sentence = sentence.replace("`", "");
        sentence = sentence.replace("'", "");
        sentence = sentence.replace("[", "");
        sentence = sentence.replace("]", "");
        sentence = sentence.replace("{", "");
        sentence = sentence.replace("}", "");
        sentence = sentence.replace("*", "");
        sentence = sentence.replace("=", "");
        sentence = sentence.replace("+", "");
        sentence = sentence.replace("*", "");
        sentence = sentence.replace("#", "");
        sentence = sentence.replace("...", "");
        sentence = sentence.replace(":", "");
        sentence = sentence.replace("(", "");
        sentence = sentence.replace(")", "");
        sentence = sentence.replace(";", "");
        sentence = sentence.replace("<", "");
        sentence = sentence.replace(">", "");
        sentence = sentence.replace("“", "");
        sentence = sentence.replace("”", "");
        sentence = sentence.replace(",", "");
        sentence = sentence.replace(".", "");
        sentence = sentence.replace("!", "");
        sentence = sentence.replace("?", "");
        sentence = sentence.replace("%", "");

        return sentence;
    }

    public static String clearSentencePunctuation(String sentence) {

        sentence = sentence.replace(".", ". ");
        sentence = sentence.replace("!", "! ");
        sentence = sentence.replace("?", "? ");
        sentence = sentence.replace(", <", " <");
        sentence = sentence.replace("> ,", " >");
        sentence = sentence.replace("<", " < ");
        sentence = sentence.replace(">", " > ");

        sentence = sentence.trim();
        sentence = RemoveDuplicateBlanks(sentence);

        return sentence;
    }
    
    public static String RemoveDuplicateBlanks(String str) {
        String patternStr = "\\s+";
        String replaceStr = " ";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll(replaceStr).trim();
    }

    public static void teste(String sentence) {
        sentence = sentence.replace(" – ", " ");
        sentence = sentence.replace(" –", " ");
        sentence = sentence.replace("– ", "");
        sentence = sentence.replace(" - ", " ");
        sentence = sentence.replace(" -", " ");
        sentence = sentence.replace("- ", "");
        sentence = sentence.replace("\"", " ");
        sentence = sentence.replace("\\", " ");
        sentence = sentence.replace("/", " ");
        sentence = sentence.replace("//", " ");
        sentence = sentence.replace("'", "");
        sentence = sentence.replace("`", "");
        sentence = sentence.replace("'", "");
        sentence = sentence.replace("[", "");
        sentence = sentence.replace("]", "");
        sentence = sentence.replace("{", "");
        sentence = sentence.replace("}", "");
        sentence = sentence.replace("*", "");
        sentence = sentence.replace("=", "");
        sentence = sentence.replace("+", "");
        sentence = sentence.replace("*", "");
        sentence = sentence.replace("#", "");
        sentence = sentence.replace("...", "");
        sentence = sentence.replace(":", "");
        sentence = sentence.replace("(", "");
        sentence = sentence.replace(")", "");
        sentence = sentence.replace(";", "");
        sentence = sentence.replace("<", "");
        sentence = sentence.replace(">", "");
        sentence = sentence.replace("“", "");
        sentence = sentence.replace("”", "");
        sentence = sentence.replace(",", "");
    }

    public static void main(String[] args) {
        //System.out.println(clearSentencePunctuation_toEN("E aí? Onde vc esta.... deve ser muito bonito! Mande notícias. Muitos beijos"));
    }
}
