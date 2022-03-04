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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import utilit.Context_to_UnionBDs;
import utilit.EN_to_UnionBDs;
import utilit.Relation_to_UnionBDs;
import utilit.UtilitBD_AllPairsDataToCategory;

/**
 *
 * @author MaisaDuarte
 */
public class UnionBDs {

    private Connection conn;
    private Statement stm;
    private Statement stm1;
    private Statement stm2;
    private ResultSet rs = null;
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String DATABASE_URL = "jdbc:mysql://localhost:3306/AllPairsData_BrazilianCorpusComplete";
    private static String user = "root";
    private static String password = "root";

    public void connect() {
        try {
            if ((conn == null) || (conn.isClosed())) {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DATABASE_URL, user, password);
                //System.out.println("Conexao efetuada com sucesso!!!");
            } else {
                //System.out.println("Conexao já aberta!!!");
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao iniciar o sistema! - 3");
            System.exit(1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocoreu um erro ao iniciar o sistema! - 4");
            System.exit(1);
        }
    }

    public void disconnect() {
        rs = null;
        try {
            if (!conn.isClosed()) {
                stm.close();
                stm1.close();
                stm2.close();
                conn.close();
                // System.out.println("Encerrada Conexão!");
            }
        } catch (SQLException ex) {
        }
    }

    public static void main(String[] args) {
        UnionBDs UnionBDs = new UnionBDs();
        UnionBDs.connect();

        UtilitBD_AllPairsDataToCategory UtilitBD_AllPairsDataToCategory = new UtilitBD_AllPairsDataToCategory();
        UtilitBD_AllPairsDataToCategory.setDATABASE_URL("AllPairsData_BrazilianCorpusComplete");
        UtilitBD_AllPairsDataToCategory.connect();

        ResultSet rsAllBDs = null;
        ResultSet rsAllENs = null;
        ResultSet rsAllContexts = null;

        ResultSet rsAllENsSaved = null;
        ResultSet rsAllContextsSaved = null;

        String sql_Relations = "select EN, Context, sum(occurrence) as Occurrence, side  from "
                + " (select e_a_0.Context.side, e_a_0.EN.en, e_a_0.Context.context, e_a_0.pairs.occurrence from e_a_0.pairs "
                + " join e_a_0.EN on e_a_0.EN.idEN = e_a_0.pairs.codEN "
                + " join e_a_0.Context on e_a_0.Context.idContext = e_a_0.pairs.codContext union all "
                + " select e_a_1.Context.side, e_a_1.EN.en, e_a_1.Context.context, e_a_1.pairs.occurrence from e_a_1.pairs "
                + " join e_a_1.EN on e_a_1.EN.idEN = e_a_1.pairs.codEN "
                + " join e_a_1.Context on e_a_1.Context.idContext = e_a_1.pairs.codContext union all "
                + " select e_a_2.Context.side, e_a_2.EN.en, e_a_2.Context.context, e_a_2.pairs.occurrence from e_a_2.pairs "
                + " join e_a_2.EN on e_a_2.EN.idEN = e_a_2.pairs.codEN "
                + " join e_a_2.Context on e_a_2.Context.idContext = e_a_2.pairs.codContext union all "
                + " select e_a_3.Context.side, e_a_3.EN.en, e_a_3.Context.context, e_a_3.pairs.occurrence from e_a_3.pairs "
                + " join e_a_3.EN on e_a_3.EN.idEN = e_a_3.pairs.codEN "
                + " join e_a_3.Context on e_a_3.Context.idContext = e_a_3.pairs.codContext union all "
                + " select e_o_0.Context.side, e_o_0.EN.en, e_o_0.Context.context, e_o_0.pairs.occurrence from e_o_0.pairs "
                + " join e_o_0.EN on e_o_0.EN.idEN = e_o_0.pairs.codEN "
                + " join e_o_0.Context on e_o_0.Context.idContext = e_o_0.pairs.codContext union all "
                + " select e_o_1.Context.side, e_o_1.EN.en, e_o_1.Context.context, e_o_1.pairs.occurrence from e_o_1.pairs "
                + " join e_o_1.EN on e_o_1.EN.idEN = e_o_1.pairs.codEN "
                + " join e_o_1.Context on e_o_1.Context.idContext = e_o_1.pairs.codContext union all "
                + " select e_o_2.Context.side, e_o_2.EN.en, e_o_2.Context.context, e_o_2.pairs.occurrence from e_o_2.pairs "
                + " join e_o_2.EN on e_o_2.EN.idEN = e_o_2.pairs.codEN "
                + " join e_o_2.Context on e_o_2.Context.idContext = e_o_2.pairs.codContext union all "
                + " select e_o_3.Context.side, e_o_3.EN.en, e_o_3.Context.context, e_o_3.pairs.occurrence from e_o_3.pairs "
                + " join e_o_3.EN on e_o_3.EN.idEN = e_o_3.pairs.codEN "
                + " join e_o_3.Context on e_o_3.Context.idContext = e_o_3.pairs.codContext union all "
                + " select e_um_0.Context.side, e_um_0.EN.en, e_um_0.Context.context, e_um_0.pairs.occurrence from e_um_0.pairs "
                + " join e_um_0.EN on e_um_0.EN.idEN = e_um_0.pairs.codEN "
                + " join e_um_0.Context on e_um_0.Context.idContext = e_um_0.pairs.codContext union all "
                + " select e_um_1.Context.side, e_um_1.EN.en, e_um_1.Context.context, e_um_1.pairs.occurrence from e_um_1.pairs "
                + " join e_um_1.EN on e_um_1.EN.idEN = e_um_1.pairs.codEN "
                + " join e_um_1.Context on e_um_1.Context.idContext = e_um_1.pairs.codContext union all "
                + " select e_um_2.Context.side, e_um_2.EN.en, e_um_2.Context.context, e_um_2.pairs.occurrence from e_um_2.pairs "
                + " join e_um_2.EN on e_um_2.EN.idEN = e_um_2.pairs.codEN "
                + " join e_um_2.Context on e_um_2.Context.idContext = e_um_2.pairs.codContext union all "
                + " select e_um_3.Context.side, e_um_3.EN.en, e_um_3.Context.context, e_um_3.pairs.occurrence from e_um_3.pairs "
                + " join e_um_3.EN on e_um_3.EN.idEN = e_um_3.pairs.codEN  "
                + " join e_um_3.Context on e_um_3.Context.idContext = e_um_3.pairs.codContext union all  "
                + " select e_uma_0.Context.side, e_uma_0.EN.en, e_uma_0.Context.context, e_uma_0.pairs.occurrence from e_uma_0.pairs "
                + " join e_uma_0.EN on e_uma_0.EN.idEN = e_uma_0.pairs.codEN "
                + " join e_uma_0.Context on e_uma_0.Context.idContext = e_uma_0.pairs.codContext union all "
                + " select e_uma_1.Context.side, e_uma_1.EN.en, e_uma_1.Context.context, e_uma_1.pairs.occurrence from e_uma_1.pairs "
                + " join e_uma_1.EN on e_uma_1.EN.idEN = e_uma_1.pairs.codEN "
                + " join e_uma_1.Context on e_uma_1.Context.idContext = e_uma_1.pairs.codContext union all "
                + " select e_uma_2.Context.side, e_uma_2.EN.en, e_uma_2.Context.context, e_uma_2.pairs.occurrence from e_uma_2.pairs "
                + " join e_uma_2.EN on e_uma_2.EN.idEN = e_uma_2.pairs.codEN "
                + " join e_uma_2.Context on e_uma_2.Context.idContext = e_uma_2.pairs.codContext union all "
                + " select e_uma_3.Context.side, e_uma_3.EN.en, e_uma_3.Context.context, e_uma_3.pairs.occurrence from e_uma_3.pairs "
                + " join e_uma_3.EN on e_uma_3.EN.idEN = e_uma_3.pairs.codEN "
                + " join e_uma_3.Context on e_uma_3.Context.idContext = e_uma_3.pairs.codContext ) as allp "
                + " group by EN,Context,side "
                + " order by EN,Context";

        String sql_ENs = " select EN, sum(Occurrence) as Occurrence from "
                + " (select e_a_0.EN.en, e_a_0.Context.context, e_a_0.pairs.occurrence from e_a_0.pairs "
                + " join e_a_0.EN on e_a_0.EN.idEN = e_a_0.pairs.codEN "
                + " join e_a_0.Context on e_a_0.Context.idContext = e_a_0.pairs.codContext union all "
                + " select e_a_1.EN.en, e_a_1.Context.context, e_a_1.pairs.occurrence from e_a_1.pairs "
                + " join e_a_1.EN on e_a_1.EN.idEN = e_a_1.pairs.codEN "
                + " join e_a_1.Context on e_a_1.Context.idContext = e_a_1.pairs.codContext union all "
                + " select e_a_2.EN.en, e_a_2.Context.context, e_a_2.pairs.occurrence from e_a_2.pairs "
                + " join e_a_2.EN on e_a_2.EN.idEN = e_a_2.pairs.codEN "
                + " join e_a_2.Context on e_a_2.Context.idContext = e_a_2.pairs.codContext union all "
                + " select e_a_3.EN.en, e_a_3.Context.context, e_a_3.pairs.occurrence from e_a_3.pairs "
                + " join e_a_3.EN on e_a_3.EN.idEN = e_a_3.pairs.codEN "
                + " join e_a_3.Context on e_a_3.Context.idContext = e_a_3.pairs.codContext union all "
                + " select e_o_0.EN.en, e_o_0.Context.context, e_o_0.pairs.occurrence from e_o_0.pairs "
                + " join e_o_0.EN on e_o_0.EN.idEN = e_o_0.pairs.codEN "
                + " join e_o_0.Context on e_o_0.Context.idContext = e_o_0.pairs.codContext union all "
                + " select e_o_1.EN.en, e_o_1.Context.context, e_o_1.pairs.occurrence from e_o_1.pairs "
                + " join e_o_1.EN on e_o_1.EN.idEN = e_o_1.pairs.codEN "
                + " join e_o_1.Context on e_o_1.Context.idContext = e_o_1.pairs.codContext union all "
                + " select e_o_2.EN.en, e_o_2.Context.context, e_o_2.pairs.occurrence from e_o_2.pairs "
                + " join e_o_2.EN on e_o_2.EN.idEN = e_o_2.pairs.codEN "
                + " join e_o_2.Context on e_o_2.Context.idContext = e_o_2.pairs.codContext union all "
                + " select e_o_3.EN.en, e_o_3.Context.context, e_o_3.pairs.occurrence from e_o_3.pairs "
                + " join e_o_3.EN on e_o_3.EN.idEN = e_o_3.pairs.codEN "
                + " join e_o_3.Context on e_o_3.Context.idContext = e_o_3.pairs.codContext union all "
                + " select e_um_0.EN.en, e_um_0.Context.context, e_um_0.pairs.occurrence from e_um_0.pairs "
                + " join e_um_0.EN on e_um_0.EN.idEN = e_um_0.pairs.codEN "
                + " join e_um_0.Context on e_um_0.Context.idContext = e_um_0.pairs.codContext union all "
                + " select e_um_1.EN.en, e_um_1.Context.context, e_um_1.pairs.occurrence from e_um_1.pairs "
                + " join e_um_1.EN on e_um_1.EN.idEN = e_um_1.pairs.codEN "
                + " join e_um_1.Context on e_um_1.Context.idContext = e_um_1.pairs.codContext union all "
                + " select e_um_2.EN.en, e_um_2.Context.context, e_um_2.pairs.occurrence from e_um_2.pairs "
                + " join e_um_2.EN on e_um_2.EN.idEN = e_um_2.pairs.codEN "
                + " join e_um_2.Context on e_um_2.Context.idContext = e_um_2.pairs.codContext union all "
                + " select e_um_3.EN.en, e_um_3.Context.context, e_um_3.pairs.occurrence from e_um_3.pairs"
                + " join e_um_3.EN on e_um_3.EN.idEN = e_um_3.pairs.codEN "
                + " join e_um_3.Context on e_um_3.Context.idContext = e_um_3.pairs.codContext union all "
                + " select e_uma_0.EN.en, e_uma_0.Context.context, e_uma_0.pairs.occurrence from e_uma_0.pairs"
                + " join e_uma_0.EN on e_uma_0.EN.idEN = e_uma_0.pairs.codEN "
                + " join e_uma_0.Context on e_uma_0.Context.idContext = e_uma_0.pairs.codContext union all "
                + " select e_uma_1.EN.en, e_uma_1.Context.context, e_uma_1.pairs.occurrence from e_uma_1.pairs "
                + " join e_uma_1.EN on e_uma_1.EN.idEN = e_uma_1.pairs.codEN "
                + " join e_uma_1.Context on e_uma_1.Context.idContext = e_uma_1.pairs.codContext union all "
                + " select e_uma_2.EN.en, e_uma_2.Context.context, e_uma_2.pairs.occurrence from e_uma_2.pairs "
                + " join e_uma_2.EN on e_uma_2.EN.idEN = e_uma_2.pairs.codEN "
                + " join e_uma_2.Context on e_uma_2.Context.idContext = e_uma_2.pairs.codContext union all "
                + " select e_uma_3.EN.en, e_uma_3.Context.context, e_uma_3.pairs.occurrence from e_uma_3.pairs "
                + " join e_uma_3.EN on e_uma_3.EN.idEN = e_uma_3.pairs.codEN "
                + " join e_uma_3.Context on e_uma_3.Context.idContext = e_uma_3.pairs.codContext ) as allp "
                + " group by EN"
                + " order by EN";

        String sql_Context = "select Context, sum(occurrence) as Occurrence, side  from "
                + " (select e_a_0.Context.side, e_a_0.EN.en, e_a_0.Context.context, e_a_0.pairs.occurrence from e_a_0.pairs "
                + " join e_a_0.EN on e_a_0.EN.idEN = e_a_0.pairs.codEN "
                + " join e_a_0.Context on e_a_0.Context.idContext = e_a_0.pairs.codContext union all "
                + " select e_a_1.Context.side, e_a_1.EN.en, e_a_1.Context.context, e_a_1.pairs.occurrence from e_a_1.pairs "
                + " join e_a_1.EN on e_a_1.EN.idEN = e_a_1.pairs.codEN "
                + " join e_a_1.Context on e_a_1.Context.idContext = e_a_1.pairs.codContext union all "
                + " select e_a_2.Context.side, e_a_2.EN.en, e_a_2.Context.context, e_a_2.pairs.occurrence from e_a_2.pairs "
                + " join e_a_2.EN on e_a_2.EN.idEN = e_a_2.pairs.codEN "
                + " join e_a_2.Context on e_a_2.Context.idContext = e_a_2.pairs.codContext union all "
                + " select e_a_3.Context.side, e_a_3.EN.en, e_a_3.Context.context, e_a_3.pairs.occurrence from e_a_3.pairs "
                + " join e_a_3.EN on e_a_3.EN.idEN = e_a_3.pairs.codEN "
                + " join e_a_3.Context on e_a_3.Context.idContext = e_a_3.pairs.codContext union all "
                + " select e_o_0.Context.side, e_o_0.EN.en, e_o_0.Context.context, e_o_0.pairs.occurrence from e_o_0.pairs "
                + " join e_o_0.EN on e_o_0.EN.idEN = e_o_0.pairs.codEN "
                + " join e_o_0.Context on e_o_0.Context.idContext = e_o_0.pairs.codContext union all "
                + " select e_o_1.Context.side, e_o_1.EN.en, e_o_1.Context.context, e_o_1.pairs.occurrence from e_o_1.pairs "
                + " join e_o_1.EN on e_o_1.EN.idEN = e_o_1.pairs.codEN "
                + " join e_o_1.Context on e_o_1.Context.idContext = e_o_1.pairs.codContext union all "
                + " select e_o_2.Context.side, e_o_2.EN.en, e_o_2.Context.context, e_o_2.pairs.occurrence from e_o_2.pairs "
                + " join e_o_2.EN on e_o_2.EN.idEN = e_o_2.pairs.codEN "
                + " join e_o_2.Context on e_o_2.Context.idContext = e_o_2.pairs.codContext union all "
                + " select e_o_3.Context.side, e_o_3.EN.en, e_o_3.Context.context, e_o_3.pairs.occurrence from e_o_3.pairs "
                + " join e_o_3.EN on e_o_3.EN.idEN = e_o_3.pairs.codEN "
                + " join e_o_3.Context on e_o_3.Context.idContext = e_o_3.pairs.codContext union all "
                + " select e_um_0.Context.side, e_um_0.EN.en, e_um_0.Context.context, e_um_0.pairs.occurrence from e_um_0.pairs "
                + " join e_um_0.EN on e_um_0.EN.idEN = e_um_0.pairs.codEN "
                + " join e_um_0.Context on e_um_0.Context.idContext = e_um_0.pairs.codContext union all "
                + " select e_um_1.Context.side, e_um_1.EN.en, e_um_1.Context.context, e_um_1.pairs.occurrence from e_um_1.pairs "
                + " join e_um_1.EN on e_um_1.EN.idEN = e_um_1.pairs.codEN "
                + " join e_um_1.Context on e_um_1.Context.idContext = e_um_1.pairs.codContext union all "
                + " select e_um_2.Context.side, e_um_2.EN.en, e_um_2.Context.context, e_um_2.pairs.occurrence from e_um_2.pairs "
                + " join e_um_2.EN on e_um_2.EN.idEN = e_um_2.pairs.codEN "
                + " join e_um_2.Context on e_um_2.Context.idContext = e_um_2.pairs.codContext union all "
                + " select e_um_3.Context.side, e_um_3.EN.en, e_um_3.Context.context, e_um_3.pairs.occurrence from e_um_3.pairs "
                + " join e_um_3.EN on e_um_3.EN.idEN = e_um_3.pairs.codEN  "
                + " join e_um_3.Context on e_um_3.Context.idContext = e_um_3.pairs.codContext union all  "
                + " select e_uma_0.Context.side, e_uma_0.EN.en, e_uma_0.Context.context, e_uma_0.pairs.occurrence from e_uma_0.pairs "
                + " join e_uma_0.EN on e_uma_0.EN.idEN = e_uma_0.pairs.codEN "
                + " join e_uma_0.Context on e_uma_0.Context.idContext = e_uma_0.pairs.codContext union all "
                + " select e_uma_1.Context.side, e_uma_1.EN.en, e_uma_1.Context.context, e_uma_1.pairs.occurrence from e_uma_1.pairs "
                + " join e_uma_1.EN on e_uma_1.EN.idEN = e_uma_1.pairs.codEN "
                + " join e_uma_1.Context on e_uma_1.Context.idContext = e_uma_1.pairs.codContext union all "
                + " select e_uma_2.Context.side, e_uma_2.EN.en, e_uma_2.Context.context, e_uma_2.pairs.occurrence from e_uma_2.pairs "
                + " join e_uma_2.EN on e_uma_2.EN.idEN = e_uma_2.pairs.codEN "
                + " join e_uma_2.Context on e_uma_2.Context.idContext = e_uma_2.pairs.codContext union all "
                + " select e_uma_3.Context.side, e_uma_3.EN.en, e_uma_3.Context.context, e_uma_3.pairs.occurrence from e_uma_3.pairs "
                + " join e_uma_3.EN on e_uma_3.EN.idEN = e_uma_3.pairs.codEN "
                + " join e_uma_3.Context on e_uma_3.Context.idContext = e_uma_3.pairs.codContext ) as allp "
                + " group by Context,side "
                + " order by Context ";


        try {

            List TempList = new ArrayList<>();
            int i = 0;
            String Temp = "";

            UnionBDs.stm = UnionBDs.conn.createStatement();
            UnionBDs.stm1 = UnionBDs.conn.createStatement();
            UnionBDs.stm2 = UnionBDs.conn.createStatement();

            TempList.clear();
            rsAllENs = UnionBDs.stm.executeQuery(sql_ENs);
            rsAllENs.last();
            int totale = rsAllENs.getRow();
            rsAllENs.beforeFirst();
            i = 0;

            while (rsAllENs.next()) {
                TempList.add("("
                        + "\"" + rsAllENs.getString("EN") + "\""
                        + ","
                        + rsAllENs.getInt("Occurrence")
                        + ","
                        + "0"
                        + ")");
                if ((TempList.size() == 20000) || (totale == rsAllENs.getRow())) {
                    Temp = "";
                    i = 0;
                    Temp = TempList.get(i).toString();
                    i++;
                    while (i < TempList.size()) {
                        Temp = Temp + " , " + TempList.get(i).toString();
                        i++;
                    }
                    Temp = Temp.trim();
                    UtilitBD_AllPairsDataToCategory.insertGenericEN_BRCorpus(Temp);
                    Temp = "";
                    TempList.clear();
                }
                System.out.println(" Total de ENs :" + totale + " atual : " + rsAllENs.getRow());
            }
            rsAllENs = null;
            rsAllContexts = UnionBDs.stm.executeQuery(sql_Context);
            rsAllContexts.last();
            int totalc = rsAllContexts.getRow();
            rsAllContexts.beforeFirst();
            i = 0;
            while (rsAllContexts.next()) {
                TempList.add("("
                        + "\"" + rsAllContexts.getString("Context") + "\""
                        + ","
                        + "\"" + rsAllContexts.getString("side") + "\""
                        + ","
                        + rsAllContexts.getInt("Occurrence")
                        + ","
                        + "0"
                        + ")");

                if ((TempList.size() == 20000) || (totalc == rsAllContexts.getRow())) {
                    Temp = "";
                    i = 0;
                    Temp = TempList.get(i).toString();
                    i++;
                    while (i < TempList.size()) {
                        Temp = Temp + " , " + TempList.get(i).toString();
                        i++;
                    }
                    Temp = Temp.trim();
                    UtilitBD_AllPairsDataToCategory.insertGenericContext_BRCorpus(Temp);
                    Temp = "";
                    TempList.clear();
                }
                System.out.println(" Total de Contextos :" + totalc + " atual : " + rsAllContexts.getRow());
            }
            rsAllContexts = null;


            rsAllENsSaved = UnionBDs.stm1.executeQuery("select * from EN order by idEN");
            rsAllENsSaved.beforeFirst();
            List<EN_to_UnionBDs> EN_to_UnionBDs = new ArrayList<>();
            while (rsAllENsSaved.next()) {
                EN_to_UnionBDs.add(new EN_to_UnionBDs(rsAllENsSaved.getString("EN"), rsAllENsSaved.getInt("IDEN"), 0));
            }

            rsAllContextsSaved = UnionBDs.stm2.executeQuery("select * from Context order by idContext");
            rsAllContextsSaved.beforeFirst();
            List<Context_to_UnionBDs> Context_to_UnionBDs = new ArrayList<>();
            while (rsAllContextsSaved.next()) {
                Context_to_UnionBDs.add(new Context_to_UnionBDs(rsAllContextsSaved.getString("Context"), rsAllContextsSaved.getInt("idContext"), 0, rsAllContextsSaved.getString("Side")));
            }

            System.out.println(sql_Relations);
            rsAllBDs = UnionBDs.stm.executeQuery(sql_Relations);
            rsAllBDs.last();
            int totalr = rsAllBDs.getRow();
            System.out.println(totalr);
            rsAllBDs.beforeFirst();
            List<Relation_to_UnionBDs> Relation_to_UnionBDs = new ArrayList<>();
            Temp = "";

            Collator collator = Collator.getInstance(new Locale("pt", "BR"));
            collator.setStrength(Collator.PRIMARY);
            i = 0;
            int x = 0;
            int y = 0;
            while (rsAllBDs.next()) {

                x = 0;
                while (collator.compare(rsAllBDs.getString("EN").toString(), EN_to_UnionBDs.get(x).getEN()) != 0) {
                    x++;
                }

                y = 0;
                while (collator.compare(rsAllBDs.getString("Context").toString(), Context_to_UnionBDs.get(y).getContext()) != 0) {
                    y++;
                }

                Relation_to_UnionBDs.add(new Relation_to_UnionBDs(EN_to_UnionBDs.get(i).getIDEN(), Context_to_UnionBDs.get(i).getIDContext(), rsAllBDs.getInt("Occurrence")));
            }
            i = 0;
            while (i < Relation_to_UnionBDs.size()) {
                TempList.add("("
                        + Relation_to_UnionBDs.get(i).getIdContext()
                        + ","
                        + Relation_to_UnionBDs.get(i).getIdEN()
                        + ","
                        + Relation_to_UnionBDs.get(i).getOccurrence()
                        + ")");

                if ((TempList.size() == 1000) || (i + 1 == Relation_to_UnionBDs.size())) {
                    Temp = "";
                    x = 0;
                    Temp = TempList.get(x).toString();
                    x++;
                    while (x < TempList.size()) {
                        Temp = Temp + " , " + TempList.get(x).toString();
                        x++;
                    }
                    Temp = Temp.trim();
                    UtilitBD_AllPairsDataToCategory.insertGenericPairs_BRCorpus(Temp);
                    TempList.clear();
                    Temp = "";
                    System.out.println(" Total de relações :" + totalr + " atual : " + i);
                }
                i++;
            }
            rsAllBDs = null;
            rsAllENsSaved = null;
            rsAllContextsSaved = null;

            UtilitBD_AllPairsDataToCategory.disconnect();
            UnionBDs.disconnect();


        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToCategory.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Algum SQL na Union de DBs");
        }
    }

    public static List ReadTXTtoListOBject(String File, List<EN_to_UnionBDs> EN_to_UnionBDs, List<Context_to_UnionBDs> Context_to_UnionBDs) {

        int Occurrence;
        String Context;
        String EN;
        FileInputStream in = null;
        List<ObjectAllPairsData_BrazilianCorpus> ListObject = new ArrayList();
        Collator collator = Collator.getInstance(new Locale("pt", "BR"));
        collator.setStrength(Collator.PRIMARY);

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
            scan.useDelimiter("[\n\r]+");

            while (scan.hasNext()) {

                line = scan.next();
                line = line.trim();
                System.out.println(line);

                Context = line.substring(0, line.indexOf(","));
                line = line.substring(line.indexOf(",") + 1);

                EN = line.substring(0, line.indexOf(","));
                line = line.substring(line.indexOf(",") + 1);

                Occurrence = Integer.valueOf(line);

                ListObject.add(new ObjectAllPairsData_BrazilianCorpus(Context, EN, Occurrence));

                if ((ListObject.size() == 1000) || (!scan.hasNext())) {
                    int i = 0;
                    while (EN_to_UnionBDs.isEmpty() && (Context_to_UnionBDs.isEmpty())) {
                        int w = 0;
                        while (collator.compare(ListObject.get(i).getEN(), EN_to_UnionBDs.get(w).getEN())!=0)  {
                            w++;
                        }
                      // parei aqui 29/10/12  ListObject.get(i).setIDEN(Temp.get(i).getIDEN());
                        i++;
                    }

                    try {

                        int y = 0;
                        while (y < ListObject.size()) {

                            y++;
                        }
                    } catch (Exception e) {
                        System.out.println("vai tomar café pq lascou!");
                    } finally {
                        ListObject.clear();
                        System.gc();
                    }
                }
            }
            ListObject.clear();
            ListObject = null;

        } catch (IOException ex) {
            Logger.getLogger(UnionBDs.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(UnionBDs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ListObject;
    }
}
