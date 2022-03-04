/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

import UPClueWeb.ObjectENContextOccurrence;
import UPClueWeb.ObjectENsRelationContextOccurrence;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author MaisaDuarte
 */
public class UtilitBD_AllPairsDataToCategory {

    private static Connection conn;
    private Statement stm;
    private ResultSet rs = null;
    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String DATABASE_URL = "jdbc:mysql://localhost:3306/AllPairsData_ClueWeb";
    private static String user = "root";
    private static String password = "root";

    public static void setDATABASE_URL(String DATABASE_URL) {
        UtilitBD_AllPairsDataToCategory.DATABASE_URL = DATABASE_URL;
    }

    public static String getJDBC_DRIVER() {
        return JDBC_DRIVER;
    }

    public static void setJDBC_DRIVER(String JDBC_DRIVER) {
        UtilitBD_AllPairsDataToCategory.JDBC_DRIVER = JDBC_DRIVER;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        UtilitBD_AllPairsDataToCategory.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UtilitBD_AllPairsDataToCategory.password = password;
    }

    public String getDATABASE_URL() {
        return DATABASE_URL;
    }

    public static void connect() {
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

    public static void disconnect() {

        try {
            if (!conn.isClosed()) {
                conn.close();
                // System.out.println("Encerrada Conexão!");
            }
        } catch (SQLException ex) {
        }
    }

    public void insertEN(String EN, String tag, int ocurrence) {
        String sql = "";
        sql = " insert into EN (EN,occurrence,tag, co_occurrence) values (\"" + EN + "\"," + ocurrence + ",\"" + tag + "\", 1)";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 1 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public void insertGenericEN_BRCorpus(String multipleEN) {
        String sql = "";
        sql = " insert into EN (EN,occurrence,co_occurrence) values " + multipleEN;
        try {
            //  System.out.println(sql);
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 1 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public void insertContext(String Context, String side, String tag, int occurrence) {
        String sql = "";
        sql = " insert into Context (Context,side,occurrence,tag,co_occurrence) values (\"" + Context + "\",\"" + side + "\"," + occurrence + ",\"" + tag + "\", 1 )";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 2 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public void insertGenericContext_BRCorpus(String MultipleContext) {
        String sql = "";
        sql = " insert into Context (Context,side,occurrence,co_occurrence) values " + MultipleContext;
        try {
            //   System.out.println(sql);
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 2 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public void insertPairs(int codContext, int codEN, int occurrence) {
        String sql = "";
        sql = " insert into Pairs(codContext,codEN,occurrence) values (" + codContext + "," + codEN + "," + occurrence + ")";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 3 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public void insertGenericPairs_BRCorpus(String multipleInsert) {
        String sql = "";
        sql = " insert into Pairs(codContext,codEN,occurrence) values " + multipleInsert;
        try {
            // System.out.println(sql);
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 3 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public int GetIteration() {
        rs = null;
        String sql = " select iteration from iteration";
        int iteration = 0;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            iteration = rs.getInt("Iteration");
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToCategory.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return iteration;
    }

    public List<TOLearnEN_CandidatesTOPromote> GetListENCandidate_WithALLENsContextsRelations(List<Context> ListContext, List<TOLearnEN_ConfidenceCalculated> TOLearnEN_ConfidenceCalculated) {
        List<TOLearnEN_CandidatesTOPromote> TOLearnEN_CandidatesTOPromote = new ArrayList<>();
        rs = null;
        int i = 0;

        String sql = "SELECT EN, context, side, occurrence as positive_occurrence"
                + " FROM pairs_to_category "
                + " where ((Context = \"" + ListContext.get(i).getContext() + "\" and side in (\"" + ListContext.get(i).getSide() + "\"))";
        i = 1;
        String Aux = " ";
        while (ListContext.size() > i) {
            Aux = Aux + " or (Context = \"" + ListContext.get(i).getContext() + "\" and side in (\"" + ListContext.get(i).getSide() + "\")) ";
            i++;
        }
        sql = (sql + " " + Aux + " )").trim();

        i = 0;
        Aux = "";
        Aux = " and EN in (\"" + TOLearnEN_ConfidenceCalculated.get(i).getEN() + "\"";
        i = 1;
        while (TOLearnEN_ConfidenceCalculated.size() > i) {
            Aux = Aux + ",\"" + TOLearnEN_ConfidenceCalculated.get(i).getEN() + "\"";
            i++;
        }
        Aux = Aux + ")";
        sql = (sql + " " + Aux).trim();

        //System.out.println(sql);
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                TOLearnEN_CandidatesTOPromote.add(new TOLearnEN_CandidatesTOPromote(rs.getString("EN"), rs.getString("Context"), rs.getString("side"), rs.getInt("Positive_Occurrence")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToCategory.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return TOLearnEN_CandidatesTOPromote;
    }

    public List<TOLearnContext_CandidatesTOPromote> GetListContextCandidate_WithALLENsContextsRelations(List<EN> ListEN, List<TOLearnContext_ConfidenceCalculated> TOLearnContext_ConfidenceCalculated) {
        List<TOLearnContext_CandidatesTOPromote> TOLearnContext_CandidatesTOPromote = new ArrayList<>();
        rs = null;
        int i = 0;

        String sql = "SELECT EN, context, side, occurrence as positive_occurrence"
                + " FROM pairs_to_category "
                + " where (EN = \"" + ListEN.get(i).getEN() + "\"";
        i = 1;
        String Aux = " ";
        while (ListEN.size() > i) {
            Aux = Aux + " or (EN = \"" + ListEN.get(i).getEN() + "\") ";
            i++;
        }
        sql = (sql + " " + Aux + ")").trim();

        i = 0;
        Aux = "";
        Aux = " and ((Context = \"" + TOLearnContext_ConfidenceCalculated.get(i).getContext() + "\" and side in (\"" + TOLearnContext_ConfidenceCalculated.get(i).getSide() + "\"))";
        i = 1;
        while (TOLearnContext_ConfidenceCalculated.size() > i) {
            Aux = Aux + " or " + "(Context = \"" + TOLearnContext_ConfidenceCalculated.get(i).getContext() + "\" and side in (\"" + TOLearnContext_ConfidenceCalculated.get(i).getSide() + "\"))";
            i++;
        }
        Aux = Aux + ")";
        sql = (sql + " " + Aux).trim();

        // System.out.println(sql);
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                TOLearnContext_CandidatesTOPromote.add(new TOLearnContext_CandidatesTOPromote(rs.getString("context"),
                        rs.getString("side"), rs.getString("EN"), rs.getInt("positive_occurrence")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToCategory.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return TOLearnContext_CandidatesTOPromote;
    }

    public List<TOLearnEN_ConfidenceCalculated> GetListENCandidate(List<Context> ENorContext, int limit) {
        List<TOLearnEN_ConfidenceCalculated> TOLearnEN_ConfidenceCalculated = new ArrayList<>();
        rs = null;
        int i = 0;

        String sql = " SELECT count(en)as positive_Co_occurrence, sum(pairs_to_category.occurrence) as positive_occurrence,EN, "
                + " log10(sum(pairs_to_category.occurrence)) * log10(count(en)) as positive_confidence "
                + " FROM pairs_to_category "
                + " where Context = \"" + ENorContext.get(i).getContext() + "\"and side = (\"" + ENorContext.get(i).getSide() + "\") ";
        i = 1;
        String Aux = " ";
        while (ENorContext.size() > i) {
            Aux = Aux + " or (Context = \"" + ENorContext.get(i).getContext() + "\" and side in (\"" + ENorContext.get(i).getSide() + "\")) ";
            i++;
        }
        sql = (sql + " " + Aux).trim();
        sql = (sql + " group by en "
                + " having log10(sum(pairs_to_category.occurrence)) * log10(count(en)) > 0 "
                + " order by positive_confidence desc " + limit).trim();
        System.out.println(sql);
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                TOLearnEN_ConfidenceCalculated.add(new TOLearnEN_ConfidenceCalculated(rs.getString("EN"), rs.getInt("positive_occurrence"), rs.getInt("Positive_co_Occurrence"), rs.getDouble("positive_confidence")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToCategory.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }

        return TOLearnEN_ConfidenceCalculated;
    }

    public List<TOLearnContext_ConfidenceCalculated> GetListContextCandidate(List<EN> ENorContext) {
        List<TOLearnContext_ConfidenceCalculated> TOLearnContext_ConfidenceCalculated = new ArrayList<>();
        rs = null;
        int i = 0;
        String sql = " SELECT count(context)as positive_Co_occurrence, sum(pairs_to_category.occurrence) as positive_occurrence,Context, "
                + " log10(sum(pairs_to_category.occurrence)) * log10(count(context)) as positive_confidence "
                + " FROM pairs_to_category "
                + " where en in (\"" + ENorContext.get(i).getEN() + "\"";
        i = 1;
        String Aux = " ";
        while (ENorContext.size() > i) {
            Aux = Aux + " , \"" + ENorContext.get(i).getEN() + "\" ";
            i++;
        }
        Aux = Aux + ")";
        sql = (sql + " " + Aux).trim();
        sql = (sql + " group by context "
                + " having (Positive_Co_occurrence >= 2) "
                + " order by positive_confidence desc Limit 100").trim();
        System.out.println(sql);
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                TOLearnContext_ConfidenceCalculated.add(new TOLearnContext_ConfidenceCalculated(rs.getString("Context"), rs.getString("side"), rs.getInt("positive_occurrence"), rs.getInt("positive_co_occurrence"), rs.getDouble("positive_confidence")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }

        return TOLearnContext_ConfidenceCalculated;
    }

    public int GetCod(String select, String from, String where) {
        rs = null;
        String sql = " select id" + select + " from " + from + " where " + where;
        //System.out.println(sql);
        int cod = 0;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            if (rs.getRow() > 0) {
                cod = rs.getInt("id" + select);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToCategory.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return cod;
    }

    public int GetQTCod(String select, String from, String where) {
        rs = null;
        String sql = " select count(" + select + ") from " + from + " where " + where;
        //System.out.println(sql);
        int cod = 0;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            cod = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToCategory.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return cod;
    }

    public String GetName(String select, String from, String where) {
        rs = null;
        String sql = " select " + select + " from " + from + " where " + where;
        // System.out.println(sql);
        String cod = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            cod = rs.getString(select);
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToCategory.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return cod;
    }

    public void ResolveDB_Update_OR_Insert(String Context, String side, String EN, String tag, int positive_occurrence) {

        rs = null;

        int IDExistContext = GetCod("Context", "Context", " Context = \"" + Context + "\"" + " and side = \"" + side + "\"");
        int IDExistEN = GetCod("EN", "EN", " EN = \"" + EN + "\"");

        if ((IDExistContext != 0) && (IDExistEN != 0)) { //Quando EN e Contexto existem
            int IDPairs = GetCod("Pairs", "Pairs", " codContext = " + IDExistContext + " and codEN = " + IDExistEN);
            if (IDPairs != 0) {
                UpdateQT_EN_OR_Context("Context", IDExistContext, 1, 0);
                UpdateQT_EN_OR_Context("EN", IDExistEN, 1, 0);
                UpdatePairs(IDExistContext, IDExistEN, 1);
            } else {
                UpdateQT_EN_OR_Context("Context", IDExistContext, 1, 1);
                UpdateQT_EN_OR_Context("EN", IDExistEN, 1, 1);
                insertPairs(IDExistContext, IDExistEN, 1);
            }
        } else if ((IDExistContext == 0) && (IDExistEN == 0)) { //Quando EN e Contexto não existem
            insertContext(Context, side, tag, 1);
            insertEN(EN, tag, 1);
            IDExistContext = GetCod("Context", "Context", " Context = \"" + Context + "\"" + " and side = \"" + side + "\"");
            IDExistEN = GetCod("EN", "EN", " EN = \"" + EN + "\"");
            insertPairs(IDExistContext, IDExistEN, 1);
        } else if ((IDExistContext == 0) && (IDExistEN != 0)) { //Quando a EN existe
            insertContext(Context, side, tag, 1);
            IDExistContext = GetCod("Context", "Context", " Context = \"" + Context + "\"" + " and side = \"" + side + "\"");
            UpdateQT_EN_OR_Context("EN", IDExistEN, 1, 1);
            insertPairs(IDExistContext, IDExistEN, 1);
        } else if ((IDExistContext != 0) && (IDExistEN == 0)) { //Quando o context existe
            insertEN(EN, tag, 1);
            IDExistEN = GetCod("EN", "EN", " EN = \"" + EN + "\"");
            insertPairs(IDExistContext, IDExistEN, 1);
        }
    }

    public void insertPairs_fromClueWeb_AllPairsData_To_Category(String EN, String Context, String side, int Occurrence) {
        //Método para extração de Context e NEs do txt enviado pelo Bryan da CMU, e adição no allpairsdata com estrutura "pobre" (dados duplicados, mas de melhor acesso)
        String sql = "";
        sql = " insert into pairs_to_category(en, context, side, occurrence) values (\""
                + EN + "\"," + "\"" + Context + "\"," + "\"" + side + "\"," + Occurrence + ")";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 3 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public void insertPairs_fromClueWeb_AllPairsData_To_SemanticRelation(String EN_Left, String EN_Right, String semanticrelation, int Occurrence) {
        //Método para extração de Context e NEs do txt enviado pelo Bryan da CMU, e adição no allpairsdata com estrutura "pobre" (dados duplicados, mas de melhor acesso)
        String sql = "";
        sql = " insert into pairs_to_relation(en_left,en_right, semanticrelation, occurrence) values (\""
                + EN_Left + "\"," + "\"" + EN_Right + "\"," + "\"" + semanticrelation + "\"," + Occurrence + ")";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 3 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public void InsertClueWeb_To_Category(List<ObjectENContextOccurrence> ListAllPairsData) {
        int i = 0;
        while (ListAllPairsData.size() > i) {
            System.out.println(ListAllPairsData.get(i).getEN() + " # "
                    + ListAllPairsData.get(i).getContext() + " # "
                    + ListAllPairsData.get(i).getSide() + " # "
                    + ListAllPairsData.get(i).getOccurrence());
            insertPairs_fromClueWeb_AllPairsData_To_Category(ListAllPairsData.get(i).getEN(),
                    ListAllPairsData.get(i).getContext(),
                    ListAllPairsData.get(i).getSide(),
                    ListAllPairsData.get(i).getOccurrence());
            i++;
        }

    }

    public void InsertClueWeb_To_RelationSemantic(List<ObjectENsRelationContextOccurrence> ListAllPairsData) {
        int i = 0;
        while (ListAllPairsData.size() > i) {
            System.out.println(ListAllPairsData.get(i).getEN_Left() + " # "
                    + ListAllPairsData.get(i).getRelationContext() + " # "
                    + ListAllPairsData.get(i).getEN_Right() + " # "
                    + ListAllPairsData.get(i).getOccurrence());
            insertPairs_fromClueWeb_AllPairsData_To_SemanticRelation(ListAllPairsData.get(i).getEN_Left(),
                    ListAllPairsData.get(i).getRelationContext(),
                    ListAllPairsData.get(i).getEN_Right(),
                    ListAllPairsData.get(i).getOccurrence());
            i++;
        }

    }

    public void UpdateQT_EN_OR_Context(String EN_OR_Context, int codEN_OR_Context, int occurrence, int co_occurrence) {
        rs = null;
        String sql = "";
        sql = " update " + EN_OR_Context + " set occurrence = (occurrence + " + occurrence + "), co_occurrence = (co_occurrence + " + co_occurrence + ") where id" + EN_OR_Context + " = " + codEN_OR_Context;
        //System.out.println(sql);
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro na atualização:" + e.getMessage());
            System.out.println(sql);
        }

    }

    public void UpdatePairs(int codContext, int codEN, int ocurrence) {
        rs = null;
        String sql = "";
        sql = " update pairs set occurrence = (occurrence + " + ocurrence + ") where codContext = " + codContext + " and codEN = " + codEN;
        //System.out.println(sql);
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro na atualização:" + e.getMessage());
            System.out.println(sql);
        }
    }

    public void GetOccurrenceAndCo_Occurrence_NEGATIVE(String sql, int OccurrenceAndCo_Occurrence[]) {
        rs = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            OccurrenceAndCo_Occurrence[0] = rs.getInt("Occurrence");
            OccurrenceAndCo_Occurrence[1] = rs.getInt("Co_Occurrence");
            rs = null;
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void ToDoBKP(String bd, String path, String Where) {
        try {
            System.out.println("cmd /c \"mysqldump -u root " + bd + "  > " + path + "\\" + bd + ".sql");
            Runtime.getRuntime().exec("cmd /c \"mysqldump -u root " + bd + "  > " + path + "\\" + bd + "_" + Where + ".sql");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
