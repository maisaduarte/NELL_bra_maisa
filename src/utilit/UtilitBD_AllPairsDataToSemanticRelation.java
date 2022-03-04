/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

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
public class UtilitBD_AllPairsDataToSemanticRelation {

    private static Connection conn;
    private static Statement stm;
    private static ResultSet rs = null;
    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    protected static String DATABASE_URL = "jdbc:mysql://localhost:3306/";
    private static String user = "root";
    private static String password = "";

    public static String getDATABASE_URL() {
        return DATABASE_URL;
    }

    public static void setDATABASE_URL(String DATABASE_URL) {
        UtilitBD_AllPairsDataToSemanticRelation.DATABASE_URL = DATABASE_URL;
    }

    public static void connect() {
        try {
            if ((conn == null) || (conn.isClosed())) {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DATABASE_URL, user, password);
                System.out.println("Conexao efetuada com sucesso!!!");
            } else {
                System.out.println("Conexao já aberta!!!");
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
        rs = null;
        try {
            if (!conn.isClosed()) {
                stm.close();
                conn.close();
                // System.out.println("Encerrada Conexão!");
            }
        } catch (SQLException ex) {
        }
    }

    public static void insertEN(String EN, int ocurrence) {
        String sql = "";
        sql = " insert into EN (EN,occurrence, co_occurrence) values (\"" + EN + "\"," + ocurrence + ", 1)";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 1 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public static void insertSemanticRelationContext(String semanticrelationcontext, String tag, int occurrence) {
        String sql = "";
        sql = " insert into semanticrelationcontext (semanticrelationcontext,occurrence,tag,co_occurrence) values (\"" + semanticrelationcontext + "\"," + occurrence + ",\"" + tag + "\", 1 )";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 2 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public static void insertSemanticRelationPairs(int codENCouple, int codsemanticrelationcontext, int occurrence) {
        String sql = "";
        sql = " insert into semanticrelationpairs(codENCouple,codsemanticrelationcontext,occurrence) values (" + codENCouple + "," + codsemanticrelationcontext + "," + occurrence + ")";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 3 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public static void insertCoupledENs(int codENLeft, int codENRight, int occurrence) {
        String sql = "";
        sql = " insert into ENCouple(codENLeft,codENRight,occurrence,co_occurrence) values (" + codENLeft + "," + codENRight + "," + occurrence + ",1)";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 3 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public static int GetIteration() {
        rs = null;
        String sql = " select iteration from iteration";
        int iteration = 0;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            iteration = rs.getInt("Iteration");
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return iteration;
    }

    public static List<TOLearnCOUPLED_EN_CandidatesTOPromote> GetListENCandidate_WithALLENsContextsRelations(List<SemanticRelationContext> SemanticRelationContext, List<TOLearnCOUPLED_EN_ConfidenceCalculated> TOLearnCOUPLED_EN_ConfidenceCalculated) {
        List<TOLearnCOUPLED_EN_CandidatesTOPromote> TOLearnCOUPLED_EN_CandidatesTOPromote = new ArrayList<>();
        rs = null;
        int i = 0;

        String sql = "SELECT SemanticRelationContext,ENLeft,ENRight,occurrence "
                + " FROM semanticrelationpairs "
                + " where SemanticRelationContext in (\"" + SemanticRelationContext.get(i).getSemanticRelationContext() + "\"";
        i = 1;
        String Aux = " ";
        while (SemanticRelationContext.size() > i) {
            Aux = Aux + " , \"" + SemanticRelationContext.get(i).getSemanticRelationContext() + "\" ";
            i++;
        }
        sql = (sql + " " + Aux + " )").trim();

        i = 0;
        Aux = "";
        Aux = " and ((ENLeft = \"" + TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENLeft() + "\" and ENRight = \"" + TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENRight() + "\") ";
        i = 1;
        while (TOLearnCOUPLED_EN_ConfidenceCalculated.size() > i) {
            Aux = Aux + " or (ENLeft = \"" + TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENLeft() + "\" and ENRight = \"" + TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENRight() + "\") ";
            i++;
        }
        sql = (sql + " " + Aux + ")").trim();

        System.out.println(sql);
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                TOLearnCOUPLED_EN_CandidatesTOPromote.add(new TOLearnCOUPLED_EN_CandidatesTOPromote(rs.getString("ENLeft"), rs.getString("ENRight"), rs.getString("SemanticRelationContext"), rs.getInt("Occurrence")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return TOLearnCOUPLED_EN_CandidatesTOPromote;
    }

    public static List<TOLearnSEMANTICRELATIONContext_CandidatesTOPromote> GetListSEMANTIRELATIONContextCandidate_WithALLCOUPLE_ENsSEMANTIRELATIONContextsRelations(List<ENCoupleSeeds> ListENCouple, List<TOLearnSEMANTICRELATIONContext_ConfidenceCalculated> TOLearnSEMANTICRELATIONContext_ConfidenceCalculated) {
        List<TOLearnSEMANTICRELATIONContext_CandidatesTOPromote> TOLearnSEMANTICRELATIONContext_CandidatesTOPromote = new ArrayList<>();
        rs = null;
        int i = 0;

        String sql = "SELECT SemanticRelationContext,ENLeft,ENRight,occurrence "
                + " FROM semanticrelationpairs "
                + " where ((ENLeft = \"" + ListENCouple.get(i).getENLeft() + "\" and ENRight = \"" + ListENCouple.get(i).getENRight() + "\")";
        i = 1;
        String Aux = " ";
        while (ListENCouple.size() > i) {
            Aux = Aux + " or (ENLeft = \"" + ListENCouple.get(i).getENLeft() + "\" and ENRight = \"" + ListENCouple.get(i).getENRight() + "\")";
            i++;
        }
        
        Aux = Aux + ")";
        sql = (sql + " " + Aux).trim();

        i = 0;
        Aux = "";
        Aux = " and (semanticrelationContext in (" + TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getSemanticRelationContext();
        i = 1;
        while (TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.size() > i) {
            Aux = Aux + " , \"" + TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getSemanticRelationContext();
            i++;
        }
        sql = (sql + " " + Aux + ")").trim();
        
        System.out.println(sql);
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.add(new TOLearnSEMANTICRELATIONContext_CandidatesTOPromote(rs.getString("SemanticRelationContext"),
                         rs.getString("ENLeft"), rs.getString("ENRight"), rs.getInt("occurrence")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return TOLearnSEMANTICRELATIONContext_CandidatesTOPromote;
    }

    public static List<TOLearnCOUPLED_EN_ConfidenceCalculated> GetList_COUPLED_EN_Candidate(List<SemanticRelationContext> ENorContext) {
        List<TOLearnCOUPLED_EN_ConfidenceCalculated> TOLearnCOUPLED_EN_ConfidenceCalculated = new ArrayList<>();
        rs = null;
        int i = 0;

        String sql = "SELECT count(idpairs_to_relation)as positive_Co_occurrence,"
                + " sum(occurrence) as positive_occurrence,ENLeft,semanticrelationcontext,"
                + " ENRight,  log10(sum(occurrence)) * log10(count(idpairs_to_relation)) as positive_confidence "
                + " FROM semanticrelationpairs "
                + " where semanticrelationcontext in(" + ENorContext.get(i).getSemanticRelationContext() + "\" ";
        i = 1;
        String Aux = " ";
        while (ENorContext.size() > i) {
            Aux = Aux + ", \"" + ENorContext.get(i).getSemanticRelationContext() + "\" ";
            i++;
        }
        sql = (sql + " " + Aux + ")").trim();
        sql = (sql + " group by ENLeft,ENRight "
                + "having (positive_confidence > 0) "
                + "order by positive_confidence "
                + "desc Limit 200").trim();
        System.out.println(sql);
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                TOLearnCOUPLED_EN_ConfidenceCalculated.add(new TOLearnCOUPLED_EN_ConfidenceCalculated(rs.getString("ENLeft"), rs.getString("ENRight"),
                        rs.getInt("positive_occurrence"), rs.getInt("Positive_co_Occurrence"), rs.getDouble("positive_confidence")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }

        return TOLearnCOUPLED_EN_ConfidenceCalculated;
    }

    public static List<TOLearnSEMANTICRELATIONContext_ConfidenceCalculated> GetListSEMANTICRELATIONContextCandidate(List<ENCoupleSeeds> ENorContext) {
        List<TOLearnSEMANTICRELATIONContext_ConfidenceCalculated> TOLearnSEMANTICRELATIONContext_ConfidenceCalculated = new ArrayList<>();
        rs = null;
        int i = 0;
        String sql = "SELECT count(idpairs_to_relation)as positive_Co_occurrence,"
                + " sum(occurrence) as positive_occurrence,ENLeft,semanticrelationcontext,"
                + " ENRight,  log10(sum(occurrence)) * log10(count(idpairs_to_relation)) as positive_confidence "
                + " FROM semanticrelationpairs "
                + " where ((ENLeft = \"" + ENorContext.get(i).getENLeft() + "\" and ENRight = \"" + ENorContext.get(i).getENRight() + "\")";
        i = 1;
        String Aux = " ";
        while (ENorContext.size() > i) {
            Aux = Aux + " or (ENLeft = \"" + ENorContext.get(i).getENLeft() + "\" and ENRight = \"" + ENorContext.get(i).getENRight() + "\")";
            i++;
        }
        Aux = Aux + ")";
        sql = (sql + " " + Aux).trim();
        sql = (sql + " group by semanticrelationcontext having (Positive_Co_occurrence >= 2) "
                + " order by positive_confidence desc Limit 100").trim();
        System.out.println(sql);
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {

                TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.add(
                        new TOLearnSEMANTICRELATIONContext_ConfidenceCalculated(rs.getString("SemanticRelationContext"),
                        rs.getInt("positive_occurrence"),
                        rs.getInt("positive_co_occurrence"),
                        rs.getDouble("positive_confidence")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }

        return TOLearnSEMANTICRELATIONContext_ConfidenceCalculated;
    }

    public static int GetCod(String select, String from, String where) {
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
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return cod;
    }

    public static int GetQTCod(String select, String from, String where) {
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
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return cod;
    }

    public static String GetName(String select, String from, String where) {
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
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return cod;
    }

    public static void ResolveDB_Update_OR_Insert(String SemanticRelationContext, String ENLeft, String ENRight, String tag, int positive_occurrence) {
        rs = null;

        int IDExistSemanticRelationContext = GetCod("SemanticRelationContext", "SemanticRelationContext", " SemanticRelationContext = \"" + SemanticRelationContext + "\"");
        int IDExistEN1 = GetCod("EN", "EN", " EN = \"" + ENLeft + "\"");
        int IDExistEN2 = GetCod("EN", "EN", " EN = \"" + ENRight + "\"");
        int IDENCoupled = 0;
        int IDExistPairs = 0;

        //Busca IDs daqueles que existem
        if ((IDExistEN1 != 0) && (IDExistEN2 != 0)) {
            IDENCoupled = GetCod("ENCouple", "ENCouple", " codENLeft = " + IDExistEN1 + " and codENRight = " + IDExistEN2);
        }
        if ((IDENCoupled != 0) && (IDExistSemanticRelationContext != 0)) {
            IDExistPairs = GetCod("SemanticRelationPairs", "SemanticRelationPairs", " codSemanticRelationContext = " + IDExistSemanticRelationContext + " and codENCouple = " + IDENCoupled);
        } //Fim Busca IDs

        //COM REGISTROS DE ENS E CONTEXOS DE RELAÇÕES EXISTENTES
        //Incrementa se o par de ENs e relação já existe
        if ((IDExistEN1 != 0) && (IDExistEN2 != 0) && (IDExistSemanticRelationContext != 0)) {
            if (IDExistPairs != 0) {
                UpdatePairs(IDExistPairs, positive_occurrence);
                UpdateQT_EN_OR_Context("SemanticRelationContext", IDExistSemanticRelationContext, 1, 0);
                UpdateQT_EN_OR_Context("EN", IDExistEN1, 1, 0);
                UpdateQT_EN_OR_Context("EN", IDExistEN2, 1, 0);
                UpdateENCoupled(IDENCoupled, positive_occurrence, 0);
            } else if ((IDENCoupled != 0) && (IDExistSemanticRelationContext != 0)) { //Quando o PAR de EN e Contexto de Relação existem, mas o par não
                insertSemanticRelationPairs(IDENCoupled, IDExistSemanticRelationContext, 1);
                UpdateQT_EN_OR_Context("EN", IDExistEN1, 1, 1);
                UpdateQT_EN_OR_Context("EN", IDExistEN2, 1, 1);
                UpdateENCoupled(IDENCoupled, positive_occurrence, 1);
            }
        }//Fim de incrementação
        // Cria par de relação se ENCouple existe e SemanticRelationContext também }
        //Fim de criação de par de relação
        //FIM DE TRATAMENTO COM REGISTROS DE ENS E CONTEXOS DE RELAÇÕES EXISTENTES
        // Cria SemanticRelationContext e ENs daqueles não encontrados
        else {
            if (IDExistSemanticRelationContext == 0) { //Quando EN e Contexto não existem
                insertSemanticRelationContext(SemanticRelationContext, tag, 1);
                IDExistSemanticRelationContext = GetCod("SemanticRelationContext", "SemanticRelationContext", " SemanticRelationContext = \"" + SemanticRelationContext + "\"");
            } else {
                UpdateQT_EN_OR_Context("SemanticRelationContext", IDExistSemanticRelationContext, 1, 1);
            }
            if (IDExistEN1 == 0) {
                insertEN(ENLeft, 1);
                IDExistEN1 = GetCod("EN", "EN", " EN = \"" + ENLeft + "\"");
            } else {
                UpdateQT_EN_OR_Context("EN", IDExistEN1, 1, 1);
            }
            if (IDExistEN2 == 0) {
                insertEN(ENRight, 1);
                IDExistEN2 = GetCod("EN", "EN", " EN = \"" + ENRight + "\"");
            } else {
                UpdateQT_EN_OR_Context("EN", IDExistEN2, 1, 1);
            }
            //Fim Inserção/Atualização de SemanticReationContext e ENs

            //Cria ENPairs - Pares de EN
            //Cria Par (ENsPairs + SemanticRelationContext
            if (IDENCoupled == 0) {
                insertCoupledENs(IDExistEN1, IDExistEN2, positive_occurrence);
                IDENCoupled = GetCod("ENCouple", "ENCouple", " codENLeft = " + IDExistEN1 + " and codENRight = " + IDExistEN2);
            } else {
                UpdateENCoupled(IDENCoupled, positive_occurrence, 1);
            }
            if (IDExistPairs == 0) {
                insertSemanticRelationPairs(IDENCoupled, IDExistSemanticRelationContext, positive_occurrence);
            } // Fim de criação de pares de contextos de relacão
        }
    }

    public static void UpdateQT_EN_OR_Context(String EN_OR_Context, int codEN_OR_Context, int occurrence, int co_occurrence) {
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

    public static void UpdatePairs(int codSemanticRelationPairs, int ocurrence) {
        rs = null;
        String sql = "";
        sql = " update SemanticRelationPairs set occurrence = (occurrence + " + ocurrence + ") where idSemanticRelationPairs = " + codSemanticRelationPairs;
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

    public static void UpdateENCoupled(int codENCouple, int ocurrence, int co_occurrence) {
        rs = null;
        String sql = "";
        sql = " update ENCouple set occurrence = (occurrence + " + ocurrence + "), co_occurrence = (co_occurrence + " + co_occurrence + ") where idencouple = " + codENCouple;
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

    public static void GetOccurrenceAndCo_Occurrence_NEGATIVE(String sql, int OccurrenceAndCo_Occurrence[]) {
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
}
