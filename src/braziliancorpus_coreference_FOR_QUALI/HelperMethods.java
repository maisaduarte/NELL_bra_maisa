/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package braziliancorpus_coreference_FOR_QUALI;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author MaisaDuarte
 */
public class HelperMethods {

    static List ListStopWords = new ArrayList(); //Lista de Stop Words

    public HelperMethods() {
        ListStopWords.add("a");
        ListStopWords.add("à");
        ListStopWords.add("ah");
        ListStopWords.add("ai");
        ListStopWords.add("algo");
        ListStopWords.add("alguém");
        ListStopWords.add("algum");
        ListStopWords.add("alguma");
        ListStopWords.add("algumas");
        ListStopWords.add("alguns");
        ListStopWords.add("alô");
        ListStopWords.add("ambos");
        ListStopWords.add("ante");
        ListStopWords.add("ao");
        ListStopWords.add("apenas");
        ListStopWords.add("após");
        ListStopWords.add("aquela");
        ListStopWords.add("aquelas");
        ListStopWords.add("aquele");
        ListStopWords.add("aqueles");
        ListStopWords.add("aquilo");
        ListStopWords.add("as");
        ListStopWords.add("às");
        ListStopWords.add("até");
        ListStopWords.add("bis");
        ListStopWords.add("cada");
        ListStopWords.add("certa");
        ListStopWords.add("certas");
        ListStopWords.add("certo");
        ListStopWords.add("certos");
        ListStopWords.add("chi");
        ListStopWords.add("com");
        ListStopWords.add("comigo");
        ListStopWords.add("como");
        ListStopWords.add("conforme");
        ListStopWords.add("conosco");
        ListStopWords.add("consigo");
        ListStopWords.add("contigo");
        ListStopWords.add("contra");
        ListStopWords.add("convosco");
        ListStopWords.add("cuja");
        ListStopWords.add("cujas");
        ListStopWords.add("cujo");
        ListStopWords.add("cujos");
        ListStopWords.add("da");
        ListStopWords.add("das");
        ListStopWords.add("de");
        ListStopWords.add("dela");
        ListStopWords.add("delas");
        ListStopWords.add("dele");
        ListStopWords.add("deles");
        ListStopWords.add("desde");
        ListStopWords.add("dessa");
        ListStopWords.add("dessas");
        ListStopWords.add("desse");
        ListStopWords.add("desses");
        ListStopWords.add("disso");
        ListStopWords.add("desta");
        ListStopWords.add("destas");
        ListStopWords.add("deste");
        ListStopWords.add("destes");
        ListStopWords.add("disto");
        ListStopWords.add("daquela");
        ListStopWords.add("daquelas");
        ListStopWords.add("daquele");
        ListStopWords.add("daqueles");
        ListStopWords.add("daquilo");
        ListStopWords.add("do");
        ListStopWords.add("dos");
        ListStopWords.add("e");
        ListStopWords.add("eia");
        ListStopWords.add("ela");
        ListStopWords.add("elas");
        ListStopWords.add("ele");
        ListStopWords.add("eles");
        ListStopWords.add("em");
        ListStopWords.add("embora");
        ListStopWords.add("enquanto");
        ListStopWords.add("entre");
        ListStopWords.add("essa");
        ListStopWords.add("essas");
        ListStopWords.add("esse");
        ListStopWords.add("esses");
        ListStopWords.add("esta");
        ListStopWords.add("este");
        ListStopWords.add("estes");
        ListStopWords.add("estou");
        ListStopWords.add("eu");
        ListStopWords.add("hem");
        ListStopWords.add("hum");
        ListStopWords.add("ih");
        ListStopWords.add("isso");
        ListStopWords.add("isto");
        ListStopWords.add("lhe");
        ListStopWords.add("lhes");
        ListStopWords.add("logo");
        ListStopWords.add("mais");
        ListStopWords.add("mas");
        ListStopWords.add("me");
        ListStopWords.add("menos");
        ListStopWords.add("mesma");
        ListStopWords.add("mesmas");
        ListStopWords.add("mesmo");
        ListStopWords.add("mesmos");
        ListStopWords.add("meu");
        ListStopWords.add("meus");
        ListStopWords.add("mim");
        ListStopWords.add("minha");
        ListStopWords.add("minhas");
        ListStopWords.add("muita");
        ListStopWords.add("muitas");
        ListStopWords.add("muito");
        ListStopWords.add("muitos");
        ListStopWords.add("na");
        ListStopWords.add("nada");
        ListStopWords.add("nas");
        ListStopWords.add("nela");
        ListStopWords.add("nelas");
        ListStopWords.add("nele");
        ListStopWords.add("neles");
        ListStopWords.add("nem");
        ListStopWords.add("nenhum");
        ListStopWords.add("nenhuma");
        ListStopWords.add("nenhumas");
        ListStopWords.add("nenhuns");
        ListStopWords.add("ninguém");
        ListStopWords.add("no");
        ListStopWords.add("nos");
        ListStopWords.add("nós");
        ListStopWords.add("nossa");
        ListStopWords.add("nossas");
        ListStopWords.add("nosso");
        ListStopWords.add("nossos");
        ListStopWords.add("nela");
        ListStopWords.add("nelas");
        ListStopWords.add("nele");
        ListStopWords.add("neles");
        ListStopWords.add("nessa");
        ListStopWords.add("nessas");
        ListStopWords.add("nesse");
        ListStopWords.add("nesses");
        ListStopWords.add("nisso");
        ListStopWords.add("nesta");
        ListStopWords.add("nestas");
        ListStopWords.add("neste");
        ListStopWords.add("nestes");
        ListStopWords.add("nisto");
        ListStopWords.add("naquela");
        ListStopWords.add("naquelas");
        ListStopWords.add("naquele");
        ListStopWords.add("naqueles");
        ListStopWords.add("naquilo");
        ListStopWords.add("o");
        ListStopWords.add("ó");
        ListStopWords.add("ô");
        ListStopWords.add("oba");
        ListStopWords.add("oh");
        ListStopWords.add("olá");
        ListStopWords.add("onde");
        ListStopWords.add("opa");
        ListStopWords.add("ora");
        ListStopWords.add("os");
        ListStopWords.add("ou");
        ListStopWords.add("outra");
        ListStopWords.add("outras");
        ListStopWords.add("outrem");
        ListStopWords.add("outro");
        ListStopWords.add("outros");
        ListStopWords.add("para");
        ListStopWords.add("per");
        ListStopWords.add("perante");
        ListStopWords.add("pois");
        ListStopWords.add("por");
        ListStopWords.add("porém");
        ListStopWords.add("porque");
        ListStopWords.add("portanto");
        ListStopWords.add("pouca");
        ListStopWords.add("poucas");
        ListStopWords.add("pouco");
        ListStopWords.add("poucos");
        ListStopWords.add("próprios");
        ListStopWords.add("psit");
        ListStopWords.add("psiu");
        ListStopWords.add("quais");
        ListStopWords.add("quaisquer");
        ListStopWords.add("qual");
        ListStopWords.add("qualquer");
        ListStopWords.add("quando");
        ListStopWords.add("quanta");
        ListStopWords.add("quantas");
        ListStopWords.add("quanto");
        ListStopWords.add("quantos");
        ListStopWords.add("que");
        ListStopWords.add("quem");
        ListStopWords.add("se");
        ListStopWords.add("sem");
        ListStopWords.add("seu");
        ListStopWords.add("seus");
        ListStopWords.add("si");
        ListStopWords.add("sob");
        ListStopWords.add("sobre");
        ListStopWords.add("sua");
        ListStopWords.add("suas");
        ListStopWords.add("talvez");
        ListStopWords.add("tanta");
        ListStopWords.add("tantas");
        ListStopWords.add("tanto");
        ListStopWords.add("tantos");
        ListStopWords.add("te");
        ListStopWords.add("teu");
        ListStopWords.add("teus");
        ListStopWords.add("ti");
        ListStopWords.add("toda");
        ListStopWords.add("todas");
        ListStopWords.add("todo");
        ListStopWords.add("todos");
        ListStopWords.add("trás");
        ListStopWords.add("tu");
        ListStopWords.add("tua");
        ListStopWords.add("tuas");
        ListStopWords.add("tudo");
        ListStopWords.add("ué");
        ListStopWords.add("uh");
        ListStopWords.add("ui");
        ListStopWords.add("um");
        ListStopWords.add("uma");
        ListStopWords.add("umas");
        ListStopWords.add("uns");
        ListStopWords.add("vária");
        ListStopWords.add("várias");
        ListStopWords.add("vário");
        ListStopWords.add("vários");
        ListStopWords.add("você");
        ListStopWords.add("vós");
        ListStopWords.add("vossa");
        ListStopWords.add("vossas");
        ListStopWords.add("vosso");
        ListStopWords.add("vossos");
        ListStopWords.add("-");
        ListStopWords.add("=");
        ListStopWords.add("+");
        ListStopWords.add("&");
        ListStopWords.add("*");
        ListStopWords.add("_");
        ListStopWords.add("#");
        ListStopWords.add("@");
        ListStopWords.add("!");
        ListStopWords.add("?");
        ListStopWords.add("\"");
        ListStopWords.add("|");
        ListStopWords.add(";");
        ListStopWords.add(":");
        ListStopWords.add("[");
        ListStopWords.add("]");
        ListStopWords.add("}");
        ListStopWords.add("{");
        ListStopWords.add("/");
        ListStopWords.add("\\");
        ListStopWords.add(".");
        ListStopWords.add(",");
        ListStopWords.add("–");
        ListStopWords.add("|");
        ListStopWords.add("<");
        ListStopWords.add(">");
        ListStopWords.add("não");
        ListStopWords.add("sim");
        ListStopWords.add("também");
        ListStopWords.add("'");
        ListStopWords.add("´");
        ListStopWords.add("`");
        ListStopWords.add("é");
        ListStopWords.add("ser");
        ListStopWords.add("estar");
        ListStopWords.add("haver");
        ListStopWords.add("vez");
        ListStopWords.add("maioria");
        ListStopWords.add("era");
        ListStopWords.add("será");
        ListStopWords.add("sendo");
        ListStopWords.add("viria");
        ListStopWords.add("pode");
        ListStopWords.add("foi");
        ListStopWords.add("tê-lo");
        ListStopWords.add("sendo");
        ListStopWords.add("faz");
        ListStopWords.add("começa");
        ListStopWords.add("tem");
        ListStopWords.add("época");
        ListStopWords.add("hábito");
        ListStopWords.add("iria");
        ListStopWords.add("já");
        ListStopWords.add("atende");

    }

    public boolean IsName(String IsName) {

        Pattern pattern = Pattern.compile("([A-ZA-ZÉÚÍÓÁÈÙÌÒÀÕÃÑÊÛÎÔÂËYÜÏÖÄÇ])");
        Matcher matcher = pattern.matcher(IsName);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public String RemoveDuplicateBlanks(String str) {
        String patternStr = "\\s+";
        String replaceStr = " ";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll(replaceStr).trim();
    }

    public String GetEN(String EN, int LR) {
        //2 -> EN in the Left
        //3 -> EN in the Right

        if (LR == 2) {
            EN = CutPunctuation_toEN_CutLeft(EN);
            EN = CutToken(EN, LR);

            if (!EN.trim().isEmpty()) {
                EN = CutWhenTwoOrMoreTokensIsUnnecessary(EN, 2);
            }

        } else if (LR == 3) {
            EN = CutPunctuation_toEN_CutRight(EN);
            EN = CutToken(EN, LR);

            if (!EN.trim().isEmpty()) {
                EN = CutWhenTwoOrMoreTokensIsUnnecessary(EN, 3);
            }
        }

        EN = RemoveDuplicateBlanks(EN);

        if (!EN.isEmpty()) {

            EN = GetOriginalSentence(EN);

            if (!EN.isEmpty()) {

                Boolean IsName = IsName(EN.substring(1));
                EN = RemoveDuplicateBlanks(EN);
                EN = (RemoveSTWInEnd(EN, IsName)).trim();

                if (EN.length() < 2) {
                    EN = "";
                }
            }
        }
        return EN;
    }

    public String GetOriginalSentence(String sentence) {

        String aux = "";
        int i = 0;
        String TempSentence[] = sentence.split(" ");
        while (i < TempSentence.length) {
            aux = aux + " " + TempSentence[i].substring(0, TempSentence[i].indexOf("/"));
            i++;
        }
        return RemoveDuplicateBlanks(aux);
    }

    public String CutWhenTwoOrMoreTokensIsUnnecessary(String sentence, int LR) {

        String bkp = sentence;
        String TempSentence[] = sentence.split(" ");
        String AuxSentence = "";
        String RegExp = ("(\\b)(([a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõçãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄÇ0-9-\\\\$%&+,\\.@-]+)(((/)(ADV|PREP|DET|CARD|I|SENT|QUOTE|PRP|P|CONJ))))|([a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõçãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄÇ0-9-\\\\$%&+,@-]+)(_/)[A-ZA]+");
        int i = 0;

        if (TempSentence.length > 3) {
            Pattern pattern = Pattern.compile(RegExp);

            while (i < TempSentence.length) {
                Matcher matcher = pattern.matcher(TempSentence[i]);
                if (matcher.find()) {
                    TempSentence[i] = "#";
                }
                i++;
            }

            i = 0;
            while (i < TempSentence.length) {
                AuxSentence = AuxSentence + " " + TempSentence[i];
                i++;
            }

            AuxSentence = RemoveDuplicateBlanks(AuxSentence);

            while (AuxSentence.startsWith("#") || AuxSentence.endsWith("#")) {
                if (AuxSentence.length() == 1) {
                    AuxSentence = "";
                } else {
                    if (AuxSentence.startsWith("#")) {
                        AuxSentence = AuxSentence.substring(1);
                    } else if (AuxSentence.endsWith("#")) {
                        AuxSentence = AuxSentence.substring(0, (AuxSentence.length() - 1));
                    }
                }
                AuxSentence = RemoveDuplicateBlanks(AuxSentence);
            }

            if (AuxSentence.contains("# # #")) {
                if (LR == 2) {
                    sentence = AuxSentence.substring(AuxSentence.indexOf("# # #") + ("# # #").length());
                } else if (LR == 3) {
                    sentence = AuxSentence.substring(0, AuxSentence.indexOf("#"));
                }
            } else if (AuxSentence.contains("# #")) {
                if (LR == 2) {
                    sentence = AuxSentence.substring(AuxSentence.indexOf("# #") + ("# #").length());
                } else if (LR == 3) {
                    sentence = AuxSentence.substring(0, AuxSentence.indexOf("#"));
                }
            }

        }
        sentence = RemoveDuplicateBlanks(sentence);
        if (sentence.trim().isEmpty()) {
            sentence = bkp;
        }
        return sentence.trim();
    }

    public String CutPunctuation_toEN_CutRight(String sentence) {

        String CutEN_Rigth = sentence.trim();
        String RegExp = ("(\\b)([a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõçãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄÇ0-9-\\$%&+,\\.@-]+(/|_/))([A-ZA]+)(\\b)");

        String TempEN[] = CutEN_Rigth.split(" ");

        int Ngrama = 0;
        String TempEN5 = "";
        int Ti = 0;

        while ((Ngrama < 5) && (Ti < TempEN.length)) {

            Pattern pattern = Pattern.compile(RegExp);
            Matcher matcher = pattern.matcher(TempEN[Ti]);

            if (matcher.find()) {

                if (TempEN[Ti].contains("QUOTE")) {
                    Ngrama--;
                } else {
                    TempEN5 = TempEN5 + " " + TempEN[Ti];
                    if (TempEN[Ti].contains("_/")) {
                        Ngrama--;
                    }
                }
                Ngrama++;
                Ti++;
            } else {

                Pattern pattern1 = Pattern.compile("([?|\\!|\\.|;])");
                Matcher matcher1 = pattern1.matcher(TempEN[Ti]);

                if (matcher1.find()) {
                    Ngrama = 5;
                } else {
                    Ngrama++;
                    Ti++;
                }
            }
        }
        return CutEN_Rigth = TempEN5.trim();
    }

    public String CutPunctuation_toEN_CutLeft(String sentence) {

        String CutEN_Left = sentence.trim();
        String RegExp = ("(\\b)([a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõçãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄÇ0-9-\\$%&+,\\.@-]+(/|_/))([A-ZA]+)(\\b)");

        String TempEN[] = CutEN_Left.split(" ");

        int Ti = TempEN.length - 1;
        int Ngrama = 5;
        String TempEN5 = "";

        while ((Ngrama > 0) && (Ti > -1)) {

            Pattern pattern = Pattern.compile(RegExp);
            Matcher matcher = pattern.matcher(TempEN[Ti]);

            if (matcher.find()) {
                if (TempEN[Ti].contains("QUOTE")) {
                    Ngrama++;
                } else {
                    TempEN5 = TempEN[Ti] + " " + TempEN5;
                    if (TempEN[Ti].contains("_/")) {
                        Ngrama++;
                    }
                }
                Ti--;
                Ngrama--;
            } else {
                Pattern pattern1 = Pattern.compile("([?|\\!|\\.|;])");
                Matcher matcher1 = pattern1.matcher(TempEN[Ti]);

                if (matcher1.find()) {
                    Ngrama = 0;
                } else {
                    Ti--;
                    Ngrama--;
                }
            }
        }
        return CutEN_Left = TempEN5.trim();
    }

    public String CutToken(String sentence, int LR) {

        String CutEN_Left = sentence.trim();
        String RegExp = ("(\\b)(([a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõçãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄÇ0-9-\\\\$%&+,\\.@-]+)(((/)(ADV|PREP|DET|CARD|I|SENT|QUOTE|PRP|P|CONJ))))|([a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõçãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄÇ0-9-\\\\$%&+,@-]+)(_/)[A-ZA]+");

        String TempEN[] = CutEN_Left.split(" ");

        int Ti = TempEN.length - 1;
        String TempEN5 = "";

        //To Left
        while (Ti > -1) {

            Pattern pattern = Pattern.compile(RegExp);
            Matcher matcher = pattern.matcher(TempEN[Ti]);

            if (matcher.find()) {
                TempEN[Ti] = "";
            } else {
                Ti = -1;
            }
            Ti--;
        }

        //To Right
        Ti = 0;
        while (Ti < TempEN.length) {

            Pattern pattern = Pattern.compile(RegExp);
            Matcher matcher = pattern.matcher(TempEN[Ti]);

            if (matcher.find()) {
                TempEN[Ti] = "";
            } else {
                Ti = TempEN.length;
            }
            Ti++;
        }

        int i = 0;
        while (i < TempEN.length) {
            if (!TempEN[i].equals("")) {
                TempEN5 = TempEN5 + " " + TempEN[i];
            }
            i++;
        }
        if (LR == 2) {
            TempEN5 = TreatmentOfVerb_ToLeft(TempEN5);
        } else if (LR == 3) {
            TempEN5 = TreatmentOfVerb_ToRight(TempEN5);

        }
        if (TempEN5.isEmpty()) {
            TempEN5 = sentence;
        }
        return TempEN5.trim();
    }

    public String TreatmentOfVerb_ToRight(String CutEN) {

        CutEN = CutEN.trim();
        String RegExp = ("(/V)\\b");
        String TempEN[] = CutEN.split(" ");
        int Ti = 0;
        String TempEN5 = CutEN;

        if ((TempEN.length > 1) && (TempEN5.contains("/V") && (!TempEN[0].contains("/V")))) {
            TempEN5 = "";

            //To Right
            while (Ti < TempEN.length) {

                Pattern pattern = Pattern.compile(RegExp);
                Matcher matcher = pattern.matcher(TempEN[Ti]);

                if (matcher.find()) {
                    if (Ti == 0) {
                        TempEN5 = CutEN;
                    }
                    Ti = TempEN.length;
                } else {
                    TempEN5 = (TempEN5 + " " + TempEN[Ti]).trim();
                }
                Ti++;
            }
        }
        return TempEN5.trim();
    }

    public String TreatmentOfVerb_ToLeft(String CutEN) {

        CutEN = CutEN.trim();
        String RegExp = ("(/V)\\b");
        String TempEN[] = CutEN.split(" ");
        int Ti = 0;
        String TempEN5 = CutEN;

        if ((TempEN.length > 1) && (TempEN5.contains("/V") && (!TempEN[0].contains("/V")))) {
            TempEN5 = "";

            //To Left
            Pattern pattern = Pattern.compile(RegExp);
            Matcher matcher = pattern.matcher(TempEN[Ti]);

            if (matcher.find() && (Ti == 0)) {
                if (Ti == 0) {
                    TempEN5 = CutEN;
                }
            } else {

                Ti = TempEN.length - 1;
                while (Ti > -1) {

                    Pattern pattern_int = Pattern.compile(RegExp);
                    Matcher matcher_int = pattern_int.matcher(TempEN[Ti]);

                    if (matcher_int.find()) {
                        Ti = -1;
                    } else {
                        TempEN5 = (TempEN[Ti] + " " + TempEN5).trim();
                    }
                    Ti--;
                }
            }
        }
        return TempEN5.trim();
    }

    public String Cut_Comma(String sentence, int LR) {

        String result = "";
        String TempSplit[] = sentence.split(" ");
        int i = 0;

        if (TempSplit.length > 1) {
            if ((LR == 2) && (TempSplit[TempSplit.length - 1].equalsIgnoreCase(",*//PNT"))) {
                while (i < TempSplit.length - 1) {
                    result = result + TempSplit[i] + " ";
                    i++;
                }
            } else if ((LR == 3) && (TempSplit[0].equalsIgnoreCase(",*//PNT"))) {
                i = 1;
                while (i < TempSplit.length) {
                    result = result + TempSplit[i] + " ";
                    i++;
                }
            } else {
                result = sentence;
            }

        } else {
            result = sentence;
        }

        return result.trim();
    }

    public String GetPNM_toLeft(String AuxLeft) {
        String namedEntity_Temp = "";
        boolean found = false;	//found named entity

        String AuxLeftSplit[] = AuxLeft.split(" ");
        int q = AuxLeftSplit.length - 1;
        int nGram = 0;

        while ((q >= 0) && (nGram < 5)) {

            String RegExp = ("(\\b)([a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõçãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄÇ0-9-\\$%&+,\\.@-]+(/|_/))([A-ZA]+)(\\b)");
            Pattern pattern = Pattern.compile(RegExp);
            Matcher matcher = pattern.matcher(AuxLeftSplit[q]);

            if (matcher.find()) {

                if (AuxLeftSplit[q].contains("_/")) {
                    nGram--;
                }
                if ((AuxLeftSplit[q].contains("/PNM")) || (found)) {
                    if (AuxLeftSplit[q].contains("/PNM")) {
                        namedEntity_Temp = AuxLeftSplit[q] + " " + namedEntity_Temp;
                        found = true;
                    } else {
                        namedEntity_Temp = AuxLeftSplit[q] + " " + namedEntity_Temp;
                    }

                    if (found && (nGram >= 3) && (!AuxLeftSplit[q + 1].contains("/PNM")) && (!AuxLeftSplit[q].contains("/PNM"))) {
                        nGram = 5;
                    }
                }

                q--;
                nGram++;
            } else {
                nGram = 5;
            }

        }
        return namedEntity_Temp;
    }

    public String GetPNM_toRight(String AuxRight) {

        String namedEntity_Temp = "";
        boolean found = false;	//found named entity

        String AuxRightSplit[] = AuxRight.split(" ");
        int q = 0;
        int nGram = 0;

        while ((q < AuxRightSplit.length) && (nGram < 5)) {

            String RegExp = ("(\\b)([a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõçãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄÇ0-9-\\$%&+,\\.@-]+(/|_/))([A-ZA]+)(\\b)");
            Pattern pattern = Pattern.compile(RegExp);
            Matcher matcher = pattern.matcher(AuxRightSplit[q]);

            if (matcher.find()) {

                if (AuxRightSplit[q].contains("_/")) {
                    nGram--;
                }
                if (AuxRightSplit[q].contains("/PNM") || (found)) {
                    if (AuxRightSplit[q].contains("/PNM")) {
                        namedEntity_Temp = namedEntity_Temp + AuxRightSplit[q] + " ";
                        found = true;
                    } else {
                        namedEntity_Temp = namedEntity_Temp + AuxRightSplit[q] + " ";
                    }
                    if ((nGram >= 3) && (!AuxRightSplit[q - 1].contains("/PNM")) && (!AuxRightSplit[q].contains("/PNM"))) {
                        nGram = 5;
                    }
                }
                q++;
                nGram++;
            } else {
                nGram = 5;
            }
        }
        return namedEntity_Temp;
    }

    public String RemoveSTWInEnd(String aux, boolean IsName) {
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
        if (!resp.isEmpty()) {
            auxCath = RemoveDuplicateBlanks(resp).split(" ");
            resp = "";
            if ((IsName) && (auxCath.length >= 3)) {
                i = 1;
                while (i < auxCath.length - 1) {
                    if ((!(IsNameInTheFirstCaracter(auxCath[i])) && !(IsNameInTheFirstCaracter(auxCath[i + 1])))
                            || (((auxCath[i - 1]).equalsIgnoreCase("$")) && !(IsNameInTheFirstCaracter(auxCath[i])))) {
                        auxCath[i] = "$";
                    }
                    i++;
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
            while (resp.contains("$")) {
                resp = resp.substring(resp.indexOf("$") + 1);
            }
        }
        return resp.trim();
    }

    public String GetPNM_Delimited(String sentence) {
        String RegExp = ("\\b[a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõçãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄÇ0-9-\\$%&+,\\.@-]+(/PNM)\\b");
        Pattern pattern = Pattern.compile(RegExp);
        Matcher matcher = pattern.matcher(sentence);
        int start = -10;
        int end = 0;
        while (matcher.find()) {
            if (start == -10) {
                start = matcher.start();
            }
            end = matcher.end();
        }
        if (start == -10) {
            start = 0;
        }
        sentence = sentence.substring(start, end);
        return sentence.trim();
    }

    public boolean IsNameInTheFirstCaracter(String IsName) {
        Pattern pattern = Pattern.compile("(\\b)([A-ZA-ZÉÚÍÓÁÈÙÌÒÀÕÃÑÊÛÎÔÂËYÜÏÖÄÇ])(\\b)");
        Matcher matcher = pattern.matcher(IsName.substring(0, 1));
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean SearchStopWords(String word) {
        boolean resp = false;
        int sizeVet;
        int index = 0;
        sizeVet = ListStopWords.size();

        if (word.matches("^[0-9]*$")) { // verifica se é um número (somente números, 3M por exemplo irá passar)
            resp = true;
        } else if (sizeVet > 0) {
            resp = ListStopWords.contains(word.toLowerCase());
        }
        word = null;
        return resp;
    }

    public static String RegexSentence(String context) {
        String contextAux[] = context.split(" ");
        String contextOut = "";
        int i = 0;
        while (i < contextAux.length) {
            contextOut = contextOut + ("([a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõçãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄÇ0-9-\\$%&+,\\.@-]+)(" + contextAux[i] + ") ");
            i++;
        }
        return "(\\b)" + contextOut.trim() + "(\\b)";
    }

    public static boolean SentenceHaveTokenIncomplete(String sentenceLeft, String sentenceRight) {
        //Used when a seed to start or finish with a token of contractions 
        String AuxSentenceLeft[] = sentenceLeft.split(" ");
        String AuxSentenceRight[] = sentenceRight.split(" ");

        boolean HaveTokenIncomplete = false;

        if (AuxSentenceLeft[AuxSentenceLeft.length - 1].contains("_/")) {
            HaveTokenIncomplete = true;
        }

        if (AuxSentenceRight[0].contains("_/")) {
            HaveTokenIncomplete = true;
        }

        return HaveTokenIncomplete;
    }

    public List suchAsTreatment(String sentence) {

        List sentenceList = new ArrayList();
        String Aux[] = sentence.split(" ");
        boolean stop = false;
        String temp = "";
        int i = 0;

        while ((i > -1) && (i < Aux.length)) {

            String MarkRegExp = ("\\B[\\\\.\\\\!\\\\?]+");
            Pattern Markpattern = Pattern.compile(MarkRegExp);
            Matcher Markmatcher = Markpattern.matcher(Aux[i]);

            if (Markmatcher.find()) {
                i = Aux.length;
            } else {

                String RegExp = ("(,\\*//PNT|PNM|/CJ|ADJ|CN|EADR|NP|PADR|V)\\b");
                Pattern pattern = Pattern.compile(RegExp);
                Matcher matcher = pattern.matcher(Aux[i]);

                String SubRegExp = ("(,\\*//PNT|/CJ)\\b");
                Pattern Subpattern = Pattern.compile(SubRegExp);
                Matcher SubMatcher = Subpattern.matcher(Aux[i]);

                if (!matcher.find()) {
                    if (stop == true) {
                        i = -2;
                    } else {
                        stop = true;
                    }
                } else {
                    stop = false;
                }
                if (i != -2) {
                    if (SubMatcher.find()) {
                        temp = temp + "#" + " ";
                    } else {
                        temp = temp + Aux[i] + " ";
                    }
                }
                i++;
            }
        }
        if (!temp.isEmpty()) {

            i = 0;
            if (temp.contains("#")) {
                Aux = temp.split("#");
                while (i < Aux.length) {
                    if (!Aux[i].trim().isEmpty()) {
                        sentenceList.add(CutToken(Aux[i].trim(), 3));
                    }
                    i++;
                }
            } else {
                sentenceList.add(CutToken(temp.trim(), 3));
            }
        }
        return sentenceList;
    }
}
