/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author MaisaDuarte
 */
public class AdaptationsToPortuguese {

    public static String AdaptationsToPortuguese(String sentenceLabeled) {

        //Contracted forms

        //Token-ambiguous contracted forms
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)consigo_V", "consigo/V");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)com_PREP si/PRS", "consigo/PRS");

        sentenceLabeled = sentenceLabeled.replaceAll("(?i)desse_V", "desse/V");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_PREP esse/POSS", "desse/POSS");

        sentenceLabeled = sentenceLabeled.replaceAll("(?i)desses_V", "desses/V");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_PREP esses/POSS", "desses/POSS");

        sentenceLabeled = sentenceLabeled.replaceAll("(?i)deste_V", "deste/V");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_PREP este/POSS", "deste/POSS");

        sentenceLabeled = sentenceLabeled.replaceAll("(?i)destes_V", "destes/V");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_PREP estes/POSS", "destes/POSS");

        sentenceLabeled = sentenceLabeled.replaceAll("(?i)mas_CJ", "mas/CJ");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)me_CL as/CL", "mas/CL");

        sentenceLabeled = sentenceLabeled.replaceAll("(?i)no_CL", "no/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_PREP o/DA", "no/PREP");

        sentenceLabeled = sentenceLabeled.replaceAll("(?i)nos_CL", "nos/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_PREP os/DA", "nos/PREP");

        sentenceLabeled = sentenceLabeled.replaceAll("(?i)na_CL", "na/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_PREP a/DA", "na/PREP");

        sentenceLabeled = sentenceLabeled.replaceAll("(?i)nas_CL", "nas/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_PREP as/DA", "nas/PREP");

        sentenceLabeled = sentenceLabeled.replaceAll("(?i)pela_V", "pela/V");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)por_PREP a/DA", "pela/PREP");

        sentenceLabeled = sentenceLabeled.replaceAll("(?i)pelas_V", "pelas/V");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)por_PREP as/DA", "pelas/PREP");

        sentenceLabeled = sentenceLabeled.replaceAll("(?i)pelo_V", "pelo/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)por_PREP o/DA", "pelo/PREP");

        //"Normal" two-token contracted forms
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP entre/PREP", "dentre/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP estes/POSS", "destes/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/PREP o/DA", "ao/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/PREP a/DA", "à/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/PREP os/DA", "aos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/PREP as/DA", "às/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/PREP aquele/DEM", "àquele/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/PREP aqueles/DEM", "àqueles/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/PREP aquela/DEM", "àquela/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/PREP aquelas/DEM", "àquelas/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)aquele_/DEM outro/ADJ", "aqueloutro/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)aqueles_/DEM outros/ADJ", "aqueloutros/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)aquela_/DEM outra/ADJ", "aqueloutra/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)aquelas_/DEM outras/ADJ", "aqueloutras/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/PREP onde/REL", "aonde/REL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)com_/PREP mim/PRS", "comigo/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)com_/PREP ti/PRS", "contigo/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)com_/PREP nós/PRS", "connosco/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)com_/PREP vós/PRS", "convosco/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP o/DA", "do/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP os/DA", "dos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP a/DA", "da/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP as/DA", "das/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP um/IA", "dum/IA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP uns/IA", "duns/IA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP uma/IA", "duma/IA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP umas/IA", "dumas/IA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP ele/PRS", "dele/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP eles/PRS", "deles/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP ela/PRS", "dela/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP elas/PRS", "delas/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP isso/DEM", "disso/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP essa/DEM", "dessa/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP essas/DEM", "dessas/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP isto/DEM", "disto/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP esta/DEM", "desta/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP estas/DEM", "destas/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP aquele/DEM", "daquele/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP aqueles/DEM", "daqueles/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP aquela/DEM", "daquela/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP aquelas/DEM", "daquelas/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP aquilo/DEM", "daquilo/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP outrem/IN", "doutrem/IND");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP acolá/ADV", "dacolá/ADV");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP aí/ADV", "daí/ADV");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP além/ADV", "dalém/ADV");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP ali/ADV", "dali/ADV");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP aquém/ADV", "daquém/ADV");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP aqui/ADV", "daqui/ADV");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP outro/ADJ", "doutro/ADJ");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP outros/ADJ", "doutros/ADJ");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP outra/ADJ", "doutra/ADJ");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP outras/ADJ", "doutras/ADJ");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP onde/REL", "donde/REL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP um/IA", "num/IA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP uns/IA", "nuns/IA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP uma/IA", "numa/IA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP umas/IA", "numas/IA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP isto/DEM", "nisto/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP este/DEM", "neste/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP estes/DEM", "nestes/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP esta/DEM", "nesta/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP estas/DEM", "nestas/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP isso/DEM", "nisso/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP esse/DEM", "nesse/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP esses/DEM", "nesses/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP essa/DEM", "nessa/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP essas/DEM", "nessas/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP aquilo/DEM", "naquilo/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP aquele/DEM", "naquele/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP aqueles/DEM", "naqueles/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP aquela/DEM", "naquela/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP aquelas/DEM", "naquelas/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP outrem/IN", "noutrem/IND");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP outro/ADJ", "noutro/ADJ");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP outros/ADJ", "noutros/ADJ");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP outra/ADJ", "noutra/ADJ");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP outras/ADJ", "noutras/ADJ");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP ele/PRS", "nele/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP eles/PRS", "neles/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP ela/PRS", "nela/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP elas/PRS", "nelas/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP algum/QD", "dalgum/QD");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP alguns/QD", "dalguns/QD");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP alguma/QD", "dalguma/QD");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP algumas/QD", "dalgumas/QD");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP algum/QD", "nalgum/QD");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP alguns/QD", "nalguns/QD");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP alguma/QD", "nalguma/QD");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP algumas/QD", "nalgumas/QD");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)por_/PREP os/DA", "pelos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)para_/PREP o/DA", "pr'ò/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)para_/PREP os/DA", "pr'òs/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)para_/PREP o/DA", "prò/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)para_/PREP os/DA", "pròs/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)para_/PREP a/DA", "prà/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)para_/PREP as/DA", "pràs/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)para_/PREP a/DA", "pr'à/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)para_/PREP as/DA", "pr'às/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)me_/CL o/CL", "mo/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)me_/CL os/CL", "mos/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)me_/CL a/CL", "ma/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)te_/CL o/CL", "to/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)te_/CL os/CL", "tos/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)te_/CL a/CL", "ta/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)te_/CL as/CL", "tas/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)te_/V a/DA", "ta/DA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)te_/V as/DA", "tas/DA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)te_/V o/DA", "to/DA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)te_/V os/DA", "tos/DA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)lhe_/CL o/CL", "lho/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)lhe_/CL os/CL", "lhos/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)lhe_/CL a/CL", "lha/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)lhe_/CL as/CL", "lhas/CL");

        //The "ambiguous" contractions hash
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)com_/PREP si/PRS", "consigo/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP esse/DEM", "desse/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP esses/DEM", "desses/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP este/DEM", "deste/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PREP estes/DEM", "destes/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)me_/CL as/CL", "mas/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP a/DA", "na/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP as/DA", "nas/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP o/DA", "no/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP os/DA", "nos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)por_/PREP a/DA", "pela/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)por_/PREP as/DA", "pelas/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)por_/PREP o/DA", "pelo/PREP");

        //One-token contracted forms (single word short forms)
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_PREP", "d'/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_PREP", "n'/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)por_PREP", "pl'/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)para_PREP", "pra/PREP");

        //Adaptation - are not in the lists
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/INF o/CL", "do/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/INF os/CL", "dos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/INF a/CL", "da/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/INF as/CL", "das/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/INF o/DA", "do/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/INF os/DA", "dos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/INF a/DA", "da/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/INF as/DA", "das/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/LADV1 o/LADV2", "ao/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/V o/CL", "ao/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/V os/CL", "aos/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/V o/DA", "ao/DA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/V os/DA", "aos/DA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)te_/VAUX o/CL", "tão/ADV");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)te_/V o/DA", "to/DA");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)te_/V o/CL", "to/CL");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)te_/PREP o/DA", "to/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP o/DA", "no/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP os/DA", "nos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP a/DA", "na/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP as/DA", "nas/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP uma/UM", "nenhuma/QNT");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/PREP um/UM", "nenhum/QNT");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V aí/ADV", "daí/ADV");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V o/CL", "do/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V os/CL", "dos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V o/DA", "do/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V os/DA", "dos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V o/PREP", "do/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V os/PREP", "dos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V a/CL", "da/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V as/CL", "das/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V a/DA", "da/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V as/DA", "das/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V a/PREP", "da/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V as/PREP", "das/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/CJ esta/DEM", "desta/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/CJ este/DEM", "deste/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/CJ estas/DEM", "destas/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/CJ estes/DEM", "destes/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V esta/DEM", "desta/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V este/DEM", "deste/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V estas/DEM", "destas/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V estes/DEM", "destes/DEM");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/LPREP2 a/DA", "da/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/LPREP2 as/DA", "das/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/LPREP2 os/DA", "dos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/LPREP2 o/DA", "do/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/LADV1 a/LADV2", "da/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/LADV1 as/LADV2", "das/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/LADV1 o/LADV2", "dos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/LADV1 os/LADV2", "do/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/V um/UM", "num/ADV");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)em_/V uma/UM", "numa/ADV");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/CN a/PREP", "da/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/CN as/PREP", "das/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/CN o/PREP", "do/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/CN os/PREP", "dos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PNT o/DA", "do/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PNT os/DA", "dos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PNT a/DA", "da/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/PNT as/DA", "das/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V ele/PRS", "dele/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V eles/PRS", "deles/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V ela/PRS", "dela/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/V elas/PRS", "delas/PRS");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/LPREP3 o/DA", "do/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/LPREP3 os/DA", "dos/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/LPREP3 a/DA", "a/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)de_/LPREP3 a/DA", "as/PREP");
        sentenceLabeled = sentenceLabeled.replaceAll("(?i)a_/V a/DA", "à/DA");

        return sentenceLabeled;
    }

    public static String UnderlineRemove(String line) {
        int Aux1 = 0;
        int Aux2 = 0;
        int Aux3 = 0;
        String Aux4 = "";

        while (line.indexOf("_ ") > -1) {
            Aux1 = line.indexOf("_ ");
            Aux2 = line.indexOf("/", Aux1);
            if (Aux2 == -1) {
                line = "";
            } else {
                line = line.substring(0, Aux1) + (line.substring(Aux2));
                line = line.trim();
            }
        }
        while (line.indexOf("_") > -1) {
            Aux1 = line.indexOf("_");
            //System.out.println(line.substring(Aux1, Aux1 + 5));
            Aux2 = line.indexOf(" ", Aux1 + 1);
            if (Aux2 == -1) {
                Aux2 = Aux1;
            }
            //System.out.println(line.substring(Aux2, Aux2 + 5));
            Aux3 = line.indexOf(" ", Aux2 + 1);
            //System.out.println(line.substring(Aux3, Aux3 + 5));
            if (Aux3 == -1) {
                Aux3 = line.length();
            }
            Aux4 = line.substring(Aux2, Aux3);
            line = line.replaceFirst(Aux4, "");
            line = line.replaceFirst("_", "");
            line = line.trim();
        }
        /*  while (line.indexOf("-") > -1) {
         Aux1 = line.indexOf("-");
         Aux2 = line.indexOf(" ", Aux1 + 1);
         if (Aux2 == -1) {
         Aux2 = Aux1;
         }
         Aux3 = line.indexOf(" ", Aux2 + 1);
         if (Aux3 == -1) {
         Aux3 = line.length();
         }
         Aux4 = line.substring(Aux2, Aux3);
         line = line.replaceFirst(Aux4, "");
         line = line.replaceFirst("-", "");
         line = line.trim();
         }*/
        return line;
    }

    public static String RemoveSentencesWithWrongTokens(String LabeledSentence) {

        int y = 0;
        LabeledSentence = LabeledSentence.replace("	", " ");
        String aux[] = LabeledSentence.split(" ");
        y = 0;
        while (y < aux.length) {
            Pattern pattern = Pattern.compile("(\\w+)(/)(\\w+){1}");
            Matcher matcher = pattern.matcher(aux[y].toString());
            if (!matcher.find()) {
                aux[y] = "";
            }
            y++;
        }

        y = 0;
        LabeledSentence = "";
        while (y < aux.length) {
            LabeledSentence = LabeledSentence + " " + aux[y];
            y++;
        }

        return LabeledSentence.trim();
    }

    public static String RemoveDuplicateBlanks(String str) {
        String patternStr = "\\s+";
        String replaceStr = " ";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll(replaceStr).trim();
    }

    public static String RemoveTitleOfSentence(String LabeledSentence) {

        int iStart = LabeledSentence.indexOf("[*");
        int iFinal = LabeledSentence.indexOf("*]");
        if ((iStart > -1) && (iFinal > -1)) {
            LabeledSentence = LabeledSentence.substring(iFinal, LabeledSentence.length() - 1);
        }

        iStart = LabeledSentence.indexOf("*-*");
        iFinal = LabeledSentence.indexOf(":*");
        if ((iStart > -1) && (iFinal > -1)) {
            LabeledSentence = LabeledSentence.substring(iFinal, LabeledSentence.length() - 1);
        }

        return LabeledSentence.trim();
    }

    public static String MarkSentenceEnd(String sentence) {

        //replacing of sentence punctuation thad indicates the end phase
        sentence = sentence.replaceAll("\\.|\\?|!|;", "#");
        while (sentence.contains("##")) {
            sentence = sentence.replaceAll("[#]#", "#");
        }
        sentence = AdaptationsToPortuguese.RemoveDuplicateBlanks(sentence).trim();
        return sentence;
    }

    public static String PutOriginalPhraseInSentence(String Original, String Tagged) {
        String SentenceMisted = "";
        String[] StrOriginal = Original.split(" ");
        String[] StrTagged = Tagged.split(" ");

        if (StrOriginal.length == StrTagged.length) {
            int i = 0;
            while (i < StrOriginal.length) {
                SentenceMisted = SentenceMisted + " " + StrOriginal[i] + "/" + OnlyLabel(StrTagged[i]).trim();
                i++;
            }
        } else {
            SentenceMisted = Tagged;
        }

        return SentenceMisted;
    }

    public static String OnlyLabel(String Labeled) {
        String[] aux = Labeled.split(" ");
        String Label = "";
        int i = 0;
        while (aux.length > i) {
            Label = Label + " " + aux[i].substring(aux[i].toString().indexOf("/") + 1);
            i++;
        }
        return Label.trim();
    }
    
    public static int GetCaseSensitiveWord(String sentence) {
        //1 -> The char is lowercase
        //5 -> The char is uppercase 

        if (sentence.length() == 1) {
            if ((sentence.charAt(0) >= 'a') && (sentence.charAt(0) <= 'z')) {
                return 1;
            } else {
                return 5;
            }
        } else if ((sentence.charAt(0) >= 'a') && (sentence.charAt(0) <= 'z')) {
            if ((sentence.charAt(1) >= 'a') && (sentence.charAt(1) <= 'z')) {
                return 55;
            } else {
                return 15;
            }
        } else {
            if ((sentence.charAt(1) >= 'a') && (sentence.charAt(1) <= 'z')) {
                return 51;
            } else {
                return 55;
            }
        }
    }
}