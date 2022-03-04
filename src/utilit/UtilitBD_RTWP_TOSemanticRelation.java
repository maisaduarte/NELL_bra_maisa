/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

import RTWP.*;
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
public class UtilitBD_RTWP_TOSemanticRelation {

    private  Connection conn;
    private  Statement stm;
    private  ResultSet rs = null;
    private  String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    protected  String DATABASE_URL = "jdbc:mysql://localhost:3306/";
    private static String user = "root";
    private static String password = "";

    public String getJDBC_DRIVER() {
        return JDBC_DRIVER;
    }

    public void setJDBC_DRIVER(String JDBC_DRIVER) {
        this.JDBC_DRIVER = JDBC_DRIVER;
    }

    public String getDATABASE_URL() {
        return DATABASE_URL;
    }

    public void setDATABASE_URL(String DATABASE_URL) {
        this.DATABASE_URL = DATABASE_URL;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        UtilitBD_RTWP_TOSemanticRelation.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UtilitBD_RTWP_TOSemanticRelation.password = password;
    }

  

    public  void connect() {
        try {
            if ((conn == null) || (conn.isClosed())) {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DATABASE_URL, user, password);
                //System.out.println("Conexao efetuada com sucesso!!!");
            } else {
                // System.out.println("Conexao já aberta!!!");
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao iniciar o sistema! - 3");
            System.exit(1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocoreu um erro ao iniciar o sistema! - 4");
            System.exit(1);
        }
    }

    public  void disconnect() {
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

    public List<SemanticRelationCategories> GetSemanticRelationCategories() {

        List<SemanticRelationCategories> SemanticRelationCategoriesList = new ArrayList<>();
        String sql = " select A.idSemanticRelationCategory, "
                + " A.SemanticRelationCategory,A.codcategoryLeft,B.isname, "
                + " A.codCategoryRight,C.isname, A.IsNotNegativeFor from SemanticRelationCategory as A,"
                + " (select idSemanticRelationCategory, SemanticRelationCategory,category,codCategoryLeft,isname "
                + " from SemanticRelationCategory as SCat "
                + " join Category on idCategory = codCategoryLeft) as B, "
                + " (select idSemanticRelationCategory, SemanticRelationCategory,category,codCategoryRight,isname "
                + " from SemanticRelationCategory as SCat "
                + " join Category on idCategory = codCategoryRight) as C "
                + " where (A.idSemanticRelationCategory = B.idSemanticRelationCategory) "
                + " and (A.idSemanticRelationCategory = C.idSemanticRelationCategory)";
        rs = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {


                SemanticRelationCategoriesList.add(new SemanticRelationCategories(rs.getInt("idSemanticRelationCategory"),
                        rs.getString("SemanticRelationCategory"), rs.getInt("codcategoryLeft"),
                        rs.getInt("codcategoryRight"), rs.getBoolean("B.isname"), rs.getBoolean("C.isname"), rs.getString("IsNotNegativeFor")));
            }
            rs = null;
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SemanticRelationCategoriesList;
    }

    public  List<SemanticRelationContext> GetSemanticRelationCategoryToNegativeList(int codSemanticRelationCategory) {
        List<SemanticRelationContext> SemanticRelationContext = new ArrayList();
        rs = null;
        String sql = "SELECT * from semanticrelationcontext "
                + " where codSemanticRelationCategory not " + GetSemanticRelationToNegativeExamples(codSemanticRelationCategory);
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                SemanticRelationContext.add(new SemanticRelationContext(rs.getInt("idSemanticRelationContext"), rs.getString("SemanticRelationContext"),
                        rs.getInt("codSemanticRelationCategory"), rs.getString("form"), rs.getInt("codCategoryLeft"),
                        rs.getInt("codCategoryRight"), rs.getInt("positive_occurrence"), rs.getInt("positive_co_occurrence"),
                        rs.getInt("Negative_occurrence"), rs.getInt("Negative_co_occurrence"), rs.getDouble("confidence"),
                        rs.getInt("usedInIteration"), rs.getInt("LearnedInIteration")));
            }

        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SemanticRelationContext;
    }

    public List<ENCoupleSeeds> GetENcoupleToNegativeList(int codSemanticRelationCategory) {
        List<ENCoupleSeeds> ENCoupleSeeds = new ArrayList();
        rs = null;
        String sql = " select C. idenCouple, C.codSemanticRelationCategory, "
                + " A.codENLeft, A.en as ENLeft,B.codenRight, B.EN as ENRight, "
                + " A.codCategory as codCategoryLeft, B.codCategory as codCategoryRight "
                + " from encouple as C, "
                + " (select codSemanticRelationCategory,en,codCategory,codenLeft "
                + " from  encouple join en on iden = encouple.codenLeft) as A, "
                + " (select codSemanticRelationCategory,en,codCategory,codenRight "
                + " from  encouple join en on iden = encouple.codenRight "
                + " join SemanticRelationCategory as T on T.idSemanticRelationCategory = codSemanticRelationCategory) as B "
                + " where B.codenRight = C.codenRight and A.codenLeft = C.codenLeft "
                + " and A.codSemanticRelationCategory = B.codSemanticRelationCategory "
                + " and A.codSemanticRelationCategory = C.codSemanticRelationCategory "
                + " and A.codSemanticRelationCategory not " + GetSemanticRelationToNegativeExamples(codSemanticRelationCategory);
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                ENCoupleSeeds.add(new ENCoupleSeeds(rs.getInt("idENCouple"),
                        rs.getString("ENLeft"), rs.getString("ENRight"),
                        rs.getInt("codENLeft"), rs.getInt("codENRight"),
                        rs.getInt("codCategoryLeft"), rs.getInt("codCategoryRight"),
                        rs.getInt("codSemanticRelationCategory")));
            }

            System.out.println(sql);
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ENCoupleSeeds;
    }

    public List<SemanticRelationContext> GetSemanticRelationContextToList(int codSemanticRelationCategory) {
        List<SemanticRelationContext> ListSemanticRelationContext = new ArrayList();
        rs = null;
        String sql = "select * from SemanticRelationContext "
                + " where codSemanticRelationCategory = " + codSemanticRelationCategory;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                ListSemanticRelationContext.add(new SemanticRelationContext(rs.getInt("idSemanticRelationContext"), rs.getString("SemanticRelationContext"),
                        rs.getInt("codSemanticRelationCategory"), rs.getString("form"), rs.getInt("codCategoryLeft"), rs.getInt("codCategoryRight"),
                        rs.getInt("positive_occurrence"), rs.getInt("positive_co_occurrence"),
                        rs.getInt("Negative_occurrence"), rs.getInt("Negative_co_occurrence"),
                        rs.getDouble("confidence"), rs.getInt("usedInIteration"), rs.getInt("LearnedInIteration")));
            }
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListSemanticRelationContext;
    }

    public List<ENCoupleSeeds> GetCOUPLED_ENToList(int codSemanticRelationCategory) {
        List<ENCoupleSeeds> ListENCoupleSeeds = new ArrayList();
        rs = null;
        String sql = "select C. idenCouple, C.codSemanticRelationCategory, A.codENLeft,"
                + " A.en as ENLeft,B.en as ENRight, B.codENRight, A.codCategory as codCategoryLeft,"
                + " B.codCategory as codCategoryRight "
                + " from encouple as C, "
                + " (select idencouple,codSemanticRelationCategory,en,codCategory,codenLeft "
                + " from  encouple join en on iden = encouple.codenLeft) as A, "
                + " (select idencouple, codSemanticRelationCategory,en,codCategory,codenRight "
                + " from  encouple join en on iden = encouple.codenRight "
                + " join SemanticRelationCategory as T on T.idSemanticRelationCategory = codSemanticRelationCategory) as B "
                + " where A.idencouple = B.idencouple and A.idencouple = C.idencouple and "
                + " B.codenRight = C.codenRight and A.codenLeft = C.codenLeft "
                + " and B.codenRight = C.codenRight and A.codenLeft = C.codenLeft "
                + " and A.codSemanticRelationCategory = B.codSemanticRelationCategory "
                + " and A.codSemanticRelationCategory = C.codSemanticRelationCategory "
                + " and A.codSemanticRelationCategory = " + codSemanticRelationCategory;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                ListENCoupleSeeds.add(new ENCoupleSeeds(rs.getInt("idENCouple"), rs.getString("ENLeft"), rs.getString("ENRight"),
                        rs.getInt("codENLeft"), rs.getInt("codENRight"),
                        rs.getInt("codCategoryLeft"), rs.getInt("codCategoryRight"), rs.getInt("codSemanticRelationCategory")));
            }

        } catch (SQLException ex) {
            System.out.println("ERRO na query: \n" + sql);
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListENCoupleSeeds;
    }

    public List<TOLearnCOUPLED_EN_ConfidenceCalculated> ApplyingNegativeExamplesTOLearnCOUPLED_EN(int idSemanticRelationCategory, List<TOLearnCOUPLED_EN_ConfidenceCalculated> TOLearnCOUPLED_EN_ConfidenceCalculated) {
        
        UtilitBD_RTWP_TOSemanticRelation UtilitBD_RTWP_TOSemanticRelation = new UtilitBD_RTWP_TOSemanticRelation();
        
        String sql = "";
        int i = 0;

        UtilitBD_RTWP_TOSemanticRelation.connect();
        List<SemanticRelationContext> ListSemanticRelationContext = new ArrayList();
        ListSemanticRelationContext = GetSemanticRelationCategoryToNegativeList(idSemanticRelationCategory);
        UtilitBD_RTWP_TOSemanticRelation.disconnect();

        while (TOLearnCOUPLED_EN_ConfidenceCalculated.size() > i) {

            String general = "SELECT sum(occurrence) as Occurrence, count(idsemanticrelationpairs) as Co_Occurrence "
                    + " FROM semanticrelationpairs "
                    + " where ((ENLeft  = \"" + TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENLeft() + "\") and (ENRight  = \"" + TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENRight() + "\")) and (";
            int y = 0;
            int w = 0;
            String Aux = "";
            int SumOccurrence = 0;
            int SumCo_Occurrence = 0;
            while (ListSemanticRelationContext.size() > y) {
                if (w == 0) {
                    Aux = Aux + "( SemanticRelationContext = \"" + ListSemanticRelationContext.get(y).getSemanticRelationContext() + "\")";
                } else if ((w <= 100) || ((ListSemanticRelationContext.size() + 1) == y)) {
                    Aux = "( SemanticRelationContext = \"" + ListSemanticRelationContext.get(y).getSemanticRelationContext() + "\" ) or " + Aux;
                }
                if ((w == 100) || ((ListSemanticRelationContext.size() - 1) == y)) {
                    Aux = Aux + ")";
                    sql = general + Aux;
                    System.out.println(sql);

                    //Faz Query no banco de AllPairsData
                    UtilitBD_AllPairsDataToSemanticRelation.connect();
                    int[] OccurrenceAndCo_Occurrence = new int[2];
                    UtilitBD_AllPairsDataToSemanticRelation.GetOccurrenceAndCo_Occurrence_NEGATIVE(sql, OccurrenceAndCo_Occurrence);
                    UtilitBD_AllPairsDataToSemanticRelation.disconnect();

                    //Mermoriza contagem e co-ocorrência
                    SumOccurrence = SumOccurrence + OccurrenceAndCo_Occurrence[0]; //Occurrence
                    SumCo_Occurrence = SumCo_Occurrence + OccurrenceAndCo_Occurrence[1]; //Co-Occurrence
                    w = -1;
                    Aux = "";
                    sql = "";
                }
                w++;
                y++;
            }
            TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).setNegative_occurrence(SumOccurrence);
            TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).setNegative_co_occurrence(SumCo_Occurrence);
            i++;
        }
        return TOLearnCOUPLED_EN_ConfidenceCalculated;
    }

    public  List<TOLearnSEMANTICRELATIONContext_ConfidenceCalculated> ApplyingNegativeExamplesTOLearnContext(int idSemanticRelationCategory, List<TOLearnSEMANTICRELATIONContext_ConfidenceCalculated> TOLearnSEMANTICRELATIONContext_ConfidenceCalculated) {
        
        UtilitBD_RTWP_TOSemanticRelation UtilitBD_RTWP_TOSemanticRelation = new UtilitBD_RTWP_TOSemanticRelation();
        
        String sql = "";
        int i = 0;
        UtilitBD_RTWP_TOSemanticRelation.connect();
        List<ENCoupleSeeds> ENCoupleSeeds = new ArrayList();
        ENCoupleSeeds = GetENcoupleToNegativeList(idSemanticRelationCategory);
        UtilitBD_RTWP_TOSemanticRelation.disconnect();

        while (TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.size() > i) {

            String general = "SELECT sum(occurrence) as Occurrence, count(idsemanticrelationpairs) as Co_Occurrence "
                    + " FROM semanticrelationpairs "
                    + " where semanticRelationContext = \"" + TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getSemanticRelationContext() + "\" ";
            int y = 0;
            int w = 0;
            String Aux = "";
            int SumOccurrence = 0;
            int SumCo_Occurrence = 0;
            while (ENCoupleSeeds.size() > y) {
                if (Aux.isEmpty()) {
                    Aux = Aux + " and ((ENLeft = \"" + ENCoupleSeeds.get(y).getENLeft() + "\" and ENRight = \"" + ENCoupleSeeds.get(y).getENRight() + "\")";
                } else {
                    Aux = Aux + " or (ENLeft = \"" + ENCoupleSeeds.get(y).getENLeft() + "\" and ENRight = \"" + ENCoupleSeeds.get(y).getENRight() + "\")";
                }

                if ((w == 100) || ((ENCoupleSeeds.size() - 1) == y)) {

                    sql = general + Aux + ")";
                    sql = sql.trim();
                    System.out.println(sql);

                    //Faz Query no banco de AllPairsData
                    UtilitBD_AllPairsDataToSemanticRelation.connect();
                    int[] OccurrenceAndCo_Occurrence = new int[2];
                    UtilitBD_AllPairsDataToSemanticRelation.GetOccurrenceAndCo_Occurrence_NEGATIVE(sql, OccurrenceAndCo_Occurrence);
                    UtilitBD_AllPairsDataToSemanticRelation.disconnect();

                    //Mermoriza contagem e co-ocorrência
                    SumOccurrence = SumOccurrence + OccurrenceAndCo_Occurrence[0]; //Occurrence
                    SumCo_Occurrence = SumCo_Occurrence + OccurrenceAndCo_Occurrence[1]; //Co-Occurrence
                    w = -1;
                    Aux = "";
                    sql = "";
                }
                w++;
                y++;
            }
            TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).setNegative_occurrence(SumOccurrence);
            TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).setNegative_co_occurrence(SumCo_Occurrence);
            i++;
        }
        return TOLearnSEMANTICRELATIONContext_ConfidenceCalculated;
    }

    public  String GetSemanticRelationToNegativeExamples(int codSemanticRelationCategory) {
        String IsNotNegativeFor = "";
        String sql = " select IsNotNegativeFor from SemanticRelationCategory where idSemanticRelationCategory = " + codSemanticRelationCategory;
        rs = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            IsNotNegativeFor = rs.getString("IsNotNegativeFor");
            rs = null;
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return IsNotNegativeFor;
    }

    public void insertEN(String EN, int codcategory, double confidence, String tag, int positive_occurrence,
            int positive_co_occurrence, int negative_occurrence, int negative_co_occurrence, int learnedInIteration) {
        String sql = "";
        sql = " insert into EN (EN, codcategory,confidence,tag,positive_occurrence,positive_co_occurrence, "
                + " negative_occurrence,negative_co_occurrence,usedInIteration,learnedInIteration) "
                + " values (\"" + EN + "\"," + codcategory + "," + confidence + ",\"" + tag + "\"," + positive_occurrence
                + "," + positive_co_occurrence + "," + negative_occurrence + "," + negative_co_occurrence + ","
                + 0 + "," + learnedInIteration + ")";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 1 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public  void insertCoupledENs(int codENLeft, int codENRight, int codSemanticRelationCategory, double confidence, String form, int positive_occurrence,
            int positive_co_occurrence, int negative_occurrence, int negative_co_occurrence, int learnedInIteration) {
        String sql = "";
        sql = " insert into Context (codENLeft, codENRight,form, codSemanticReltionCategory,confidence,positive_occurrence,positive_co_occurrence, "
                + " negative_occurrence,negative_co_occurrence,usedInIteration,learnedInIteration) "
                + " values (" + codENLeft + "," + codENRight + ",\"" + form + "\"," + codSemanticRelationCategory + "," + confidence + "," + positive_occurrence
                + "," + positive_co_occurrence + "," + negative_occurrence + "," + negative_co_occurrence + "," + 0 + "," + learnedInIteration + ")";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 1 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public void PromotingCOUPLED_EN(int codSemanticRelationCategory, int codCategoryLeft, int codCategoryRight, int iteration, int ModMax, List<TOLearnCOUPLED_EN_ConfidenceCalculated> TOLearnCOUPLED_EN_ConfidenceCalculated, List<TOLearnCOUPLED_EN_CandidatesTOPromote> TOLearnCOUPLED_EN_CandidatesTOPromote) {

        int i = 0;
        int idENLeft = 0;
        int idENRight = 0;
        int maxCandidates = 0;
        int codENCouple = 0;

        if ((TOLearnCOUPLED_EN_ConfidenceCalculated.size() / ModMax) <= 2) {
            ModMax = TOLearnCOUPLED_EN_ConfidenceCalculated.size();
        } else {
            ModMax = TOLearnCOUPLED_EN_ConfidenceCalculated.size() / ModMax;
        }

        while ((maxCandidates < ModMax) && (TOLearnCOUPLED_EN_ConfidenceCalculated.size() > i)) {
            idENLeft = IsThereEN(TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENLeft(), codCategoryLeft);
            idENRight = IsThereEN(TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENRight(), codCategoryRight);
            if ((idENLeft == 0) || (idENRight == 0)) {
                if (idENLeft == 0) {
                    insertEN(TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENLeft(), codCategoryLeft,
                            TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getConfidence(), "",
                            TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getPositive_occurrence(),
                            TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getPositive_co_occurrence(), TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getNegative_occurrence(),
                            TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getNegative_co_occurrence(), iteration);
                    idENLeft = IsThereEN(TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENLeft(), codCategoryLeft);
                } else {
                    insertEN(TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENRight(), codCategoryRight,
                            TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getConfidence(), "",
                            TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getPositive_occurrence(),
                            TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getPositive_co_occurrence(), TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getNegative_occurrence(),
                            TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getNegative_co_occurrence(), iteration);
                    idENRight = IsThereEN(TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENRight(), codCategoryRight);
                }
                insertCoupledENs(idENLeft, idENRight, codSemanticRelationCategory, TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getConfidence(), "",
                        TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getPositive_occurrence(),
                        TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getPositive_co_occurrence(), TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getNegative_occurrence(),
                        TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getNegative_co_occurrence(), iteration);

                Add_inBD_The_COUPLED_EN_Existing_inLIST(
                        TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENLeft(),
                        TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENRight(),
                        codCategoryLeft,
                        codCategoryRight,
                        codSemanticRelationCategory,
                        TOLearnCOUPLED_EN_CandidatesTOPromote);
                maxCandidates++;
            } else {
                int[] PositiveAndNegativeOccurrenceAndCo_Occurrence = new int[4];
                int control = 0;
                control = Search_inBD_AndRemove_inLIST_COUPLED_EN_Existing(codSemanticRelationCategory, TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENLeft(),
                        TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENRight(), codCategoryLeft, codCategoryRight,
                        TOLearnCOUPLED_EN_CandidatesTOPromote, PositiveAndNegativeOccurrenceAndCo_Occurrence);
                if (control == 0) {
                    GetPositiveAndNegativeOccurrenceAndCo_Occurrence__TOLearnCOUPLED_EN(
                            TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENLeft(),
                            TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getENRight(),
                            codCategoryLeft,
                            codCategoryRight,
                            codSemanticRelationCategory,
                            PositiveAndNegativeOccurrenceAndCo_Occurrence);

                    PositiveAndNegativeOccurrenceAndCo_Occurrence[2] = TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getNegative_occurrence();
                    PositiveAndNegativeOccurrenceAndCo_Occurrence[3] = TOLearnCOUPLED_EN_ConfidenceCalculated.get(i).getNegative_co_occurrence();

                    double PositiveConfidence = 0;
                    double NegativeConfidence = 0;
                    double confidence = 0;

                    if ((PositiveAndNegativeOccurrenceAndCo_Occurrence[0] > 0) && (PositiveAndNegativeOccurrenceAndCo_Occurrence[1] > 0)) {
                        PositiveConfidence = Double.valueOf(String.format("%.2f", ((Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[0])) + (Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[1])))).replace(",", "."));
                    } else {
                        PositiveConfidence = 0;
                    }
                    if ((PositiveAndNegativeOccurrenceAndCo_Occurrence[2] > 0) && (PositiveAndNegativeOccurrenceAndCo_Occurrence[3] > 0)) {
                        NegativeConfidence = Double.valueOf(String.format("%.2f", ((Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[2])) + (Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[3])))).replace(",", "."));
                    } else {
                        NegativeConfidence = 0;
                    }
                    confidence = PositiveConfidence - NegativeConfidence;

                    //verificar aqui!!!!!

                    codENCouple = GetCod("ENCouple", "ENCouple", " codENLeft = " + idENLeft + " and codENRight = " + idENRight);

                    UpdateCOUPLED_EN(codENCouple, codSemanticRelationCategory, PositiveAndNegativeOccurrenceAndCo_Occurrence[0],
                            PositiveAndNegativeOccurrenceAndCo_Occurrence[1], PositiveAndNegativeOccurrenceAndCo_Occurrence[2],
                            PositiveAndNegativeOccurrenceAndCo_Occurrence[3], confidence);
                }

                TOLearnCOUPLED_EN_ConfidenceCalculated.remove(i);

                i--;
            }
            i++;
        }
    }

    public void PromotingSemanticRelationContext(int codSemanticRelationCategory, int CodCategoryLeft, int CodCategoryRight, int iteration, int ModMax, List<TOLearnSEMANTICRELATIONContext_ConfidenceCalculated> TOLearnSEMANTICRELATIONContext_ConfidenceCalculated, List<TOLearnSEMANTICRELATIONContext_CandidatesTOPromote> TOLearnSEMANTICRELATIONContext_CandidatesTOPromote) {
        int i = 0;
        int idENorContext = 0;
        int maxCandidates = 0;
        while ((maxCandidates < ModMax) && (TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.size() > i)) {
            idENorContext = IsThereENorContext("SEMANTICRELATIONContext", TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getSemanticRelationContext(), codSemanticRelationCategory, "");
            if (idENorContext == 0) {
                insertSemanticRelationContext(CodCategoryLeft, CodCategoryRight, TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getSemanticRelationContext(), codSemanticRelationCategory, TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getConfidence(),
                    TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getPositive_occurrence(),
                        TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getPositive_co_occurrence(), TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getNegative_occurrence(),
                        TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getNegative_co_occurrence(), iteration);
                maxCandidates++;

                //Insere todos os pares formados com a Context aprendidos (ou seja, os pares de co-orrência)
                Add_inBD_The_SEMANTIC_RELATION_Context_Existing_inLIST(TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getSemanticRelationContext(), CodCategoryLeft, CodCategoryRight, codSemanticRelationCategory, TOLearnSEMANTICRELATIONContext_CandidatesTOPromote);
            } else {

                int[] PositiveAndNegativeOccurrenceAndCo_Occurrence = new int[4];
                int control = 0;
                //continuar aqui 27/05
                control = Search_inBD_AndRemove_inLIST_SEMANTIC_RELATION_Context_Existing(codSemanticRelationCategory, TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getSemanticRelationContext(), CodCategoryLeft, CodCategoryRight, TOLearnSEMANTICRELATIONContext_CandidatesTOPromote, PositiveAndNegativeOccurrenceAndCo_Occurrence);
                if (control == 1) {
                    GetPositiveAndNegativeOccurrenceAndCo_Occurrence_TOLearnSEMANTICRELATION_CONTEXT(TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getSemanticRelationContext(), codSemanticRelationCategory, PositiveAndNegativeOccurrenceAndCo_Occurrence);

                    PositiveAndNegativeOccurrenceAndCo_Occurrence[2] = TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getNegative_occurrence();
                    PositiveAndNegativeOccurrenceAndCo_Occurrence[3] = TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getNegative_co_occurrence();

                    double PositiveConfidence = 0;
                    double NegativeConfidence = 0;
                    double confidence = 0;

                    if ((PositiveAndNegativeOccurrenceAndCo_Occurrence[0] > 0) && (PositiveAndNegativeOccurrenceAndCo_Occurrence[1] > 0)) {
                        PositiveConfidence = Double.valueOf(String.format("%.2f", ((Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[0])) + (Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[1])))).replace(",", "."));
                    } else {
                        PositiveConfidence = 0;
                    }
                    if ((PositiveAndNegativeOccurrenceAndCo_Occurrence[2] > 0) && (PositiveAndNegativeOccurrenceAndCo_Occurrence[3] > 0)) {
                        NegativeConfidence = Double.valueOf(String.format("%.2f", ((Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[2])) + (Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[3])))).replace(",", "."));
                    } else {
                        NegativeConfidence = 0;
                    }
                    confidence = PositiveConfidence - NegativeConfidence;

                    UpdateSemanticRelationContext(TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.get(i).getSemanticRelationContext(), codSemanticRelationCategory, PositiveAndNegativeOccurrenceAndCo_Occurrence[0],
                            PositiveAndNegativeOccurrenceAndCo_Occurrence[1], PositiveAndNegativeOccurrenceAndCo_Occurrence[2],
                            PositiveAndNegativeOccurrenceAndCo_Occurrence[3], confidence);
                    TOLearnSEMANTICRELATIONContext_ConfidenceCalculated.remove(i);
                    i--;
                }
            }

            i++;
        }
    }

    public void Add_inBD_The_COUPLED_EN_Existing_inLIST(String ENLeft, String ENRight, int codCategoryLeft, int codCategoryRight, int codSemanticRelationCategory, List<TOLearnCOUPLED_EN_CandidatesTOPromote> TOLearnCOUPLED_EN_CandidatesTOPromote) {
        int i = 0;
        while (i < TOLearnCOUPLED_EN_CandidatesTOPromote.size()) {
            if (TOLearnCOUPLED_EN_CandidatesTOPromote.get(i).getENLeft().equalsIgnoreCase(ENLeft) && (TOLearnCOUPLED_EN_CandidatesTOPromote.get(i).getENRight().equalsIgnoreCase(ENRight))) {
                int codENCouple = IsThereSemanticRelationCOUPLED_EN(ENLeft, ENRight, codCategoryLeft, codCategoryRight, codSemanticRelationCategory);
                int codSemanticRelationContext = GetCod("SemanticRelationContext", "SemanticRelationContext", "SemanticRelationContext = \"" + TOLearnCOUPLED_EN_CandidatesTOPromote.get(i).getSemanticRelationContext() + "\"");
                insertSemanticRelationPairs(codENCouple, codSemanticRelationContext, TOLearnCOUPLED_EN_CandidatesTOPromote.get(i).getOccurrence(), codSemanticRelationCategory);
            }
            i++;
        }
    }

    public void Add_inBD_The_SEMANTIC_RELATION_Context_Existing_inLIST(String SemanticRelationContext, int codCategoryLeft, int codCategoryRight, int codSemanticRelationCategory, List<TOLearnSEMANTICRELATIONContext_CandidatesTOPromote> TOLearnSEMANTICRELATIONContext_CandidatesTOPromote) {
        int i = 0;
        while (i < TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.size()) {
            if (TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getSemanticRelationContext().equalsIgnoreCase(SemanticRelationContext)) {
                int codENCouple = IsThereSemanticRelationCOUPLED_EN(TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getENLEft(), TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getENRight(), codCategoryLeft, codCategoryRight, codSemanticRelationCategory);
                int codSemanticRelationContext = GetCod("SemanticRelationContext", "SemanticRelationContext", "SemanticRelationContext = \"" + TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getSemanticRelationContext() + "\"");
                insertSemanticRelationPairs(codENCouple, codSemanticRelationContext, TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getOccurrence(), codSemanticRelationCategory);
            }
            i++;
        }
    }

    public int Search_inBD_AndRemove_inLIST_COUPLED_EN_Existing(int codSemanticRelationCategory, String ENLeft,
            String ENRight, int codCategoryLeft, int codCategoryRight,
            List<TOLearnCOUPLED_EN_CandidatesTOPromote> TOLearnCOUPLED_EN_CandidatesTOPromote,
            int PositiveAndNegativeOccurrenceAndCo_Occurrence[]) {
        int i = 0;
        int control = 0;
        while (i < TOLearnCOUPLED_EN_CandidatesTOPromote.size()) {
            if ((TOLearnCOUPLED_EN_CandidatesTOPromote.get(i).getENLeft().equalsIgnoreCase(ENLeft)) && (TOLearnCOUPLED_EN_CandidatesTOPromote.get(i).getENRight().equalsIgnoreCase(ENRight))) {
                int idSemanticRelationPairs = IsThereSemanticRelationPAIRS(ENLeft, ENRight, codCategoryLeft, codCategoryRight, TOLearnCOUPLED_EN_CandidatesTOPromote.get(i).getSemanticRelationContext(), codSemanticRelationCategory);
                if (idSemanticRelationPairs == 0) {
                    int codENCouple = IsThereSemanticRelationCOUPLED_EN(ENLeft, ENRight, codCategoryLeft, codCategoryRight, codSemanticRelationCategory);
                    int codSemanticRelationContext = GetCod("SemanticRelationContext", "SemanticRelationContext", "SemanticRelationContext = \"" + TOLearnCOUPLED_EN_CandidatesTOPromote.get(i).getSemanticRelationContext() + "\"");
                    insertSemanticRelationPairs(codENCouple, codSemanticRelationContext, TOLearnCOUPLED_EN_CandidatesTOPromote.get(i).getOccurrence(), codSemanticRelationCategory);
                    PositiveAndNegativeOccurrenceAndCo_Occurrence[0] = PositiveAndNegativeOccurrenceAndCo_Occurrence[0] + TOLearnCOUPLED_EN_CandidatesTOPromote.get(i).getOccurrence();
                    PositiveAndNegativeOccurrenceAndCo_Occurrence[1] = PositiveAndNegativeOccurrenceAndCo_Occurrence[1] + 1;
                    control = 1;

                }
            }
            i++;
        }
        return control;
    }

    public int Search_inBD_AndRemove_inLIST_SEMANTIC_RELATION_Context_Existing(int codSemanticRelationCategory, String SemanticRelationContext, int codCategoryLeft, int codCategoryRight, List<TOLearnSEMANTICRELATIONContext_CandidatesTOPromote> TOLearnSEMANTICRELATIONContext_CandidatesTOPromote, int PositiveAndNegativeOccurrenceAndCo_Occurrence[]) {
        int i = 0;
        int control = 0;
        while (i < TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.size()) {
            if ((TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getSemanticRelationContext().equalsIgnoreCase(SemanticRelationContext))) {
                int idSemanticRelationPairs = IsThereSemanticRelationPAIRS(TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getENLEft(), TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getENRight(), codCategoryLeft, codCategoryRight, TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getSemanticRelationContext(), codSemanticRelationCategory);
                if (idSemanticRelationPairs == 0) {
                    int codENCouple = IsThereSemanticRelationCOUPLED_EN(TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getENLEft(), TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getENRight(), codCategoryLeft, codCategoryRight, codSemanticRelationCategory);
                    int codSemanticRelationContext = GetCod("SemanticRelationContext", "SemanticRelationContext", "SemanticRelationContext = \"" + TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getSemanticRelationContext() + "\"");
                    insertSemanticRelationPairs(codENCouple, codSemanticRelationContext, TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getOccurrence(), codSemanticRelationCategory);
                    PositiveAndNegativeOccurrenceAndCo_Occurrence[0] = PositiveAndNegativeOccurrenceAndCo_Occurrence[0] + TOLearnSEMANTICRELATIONContext_CandidatesTOPromote.get(i).getOccurrence();
                    PositiveAndNegativeOccurrenceAndCo_Occurrence[1] = PositiveAndNegativeOccurrenceAndCo_Occurrence[1] + 1;
                    control = 1;
                }
            }
            i++;
        }
        return control;
    }

    public void insertSemanticRelationPairs(int codENCouple, int codSemanticRelationContext, int occurrence, int codSemanticRelationCategory) {

        String sql = "";
        sql = "insert into SemanticRelationPairs(codENCouple,codSemanticRelationContext,occurrence,codSemanticRelationCategory) "
                + " values (" + codENCouple + "," + codSemanticRelationContext + "," + occurrence + "," + codSemanticRelationCategory + ")";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 3 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public void UpdateSemanticRelationPairs(int idSemanticRelationPairs, int occurrence) {
        String sql = "";
        sql = "update SemanticRelationPairs set(occurrence = occurence + " + occurrence + ") where idSemanticRelationPairs = " + idSemanticRelationPairs;
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 3 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public int IsThereEN(String EN, int codCategory) {
        rs = null;
        String sql = " select idEN from EN where EN = \"" + EN + "\" and codCategory = " + codCategory;
        int idENorContext = 0;
        try {
            System.out.println(sql);
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.last();
            if (rs.getRow() == 0) {
                return 0;
            } else {
                rs.first();
                idENorContext = rs.getInt("idEN");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return idENorContext;
    }

    public int IsThereSemanticRelationCOUPLED_EN(String ENLeft, String ENRight, int codCategoryLeft, int codCategoryRight, int codSemanticRelationCategory) {
        rs = null;
        String sql = " select distinct C. idenCouple from encouple as C,"
                + " (select codSemanticRelationCategory,en,codCategory,codenLeft "
                + " from  encouple join en on iden = encouple.codenLeft) as A, "
                + " (select codSemanticRelationCategory,en,codCategory,codenRight "
                + " from  encouple join en on iden = encouple.codenRight "
                + " join SemanticRelationCategory as T on T.idSemanticRelationCategory = codSemanticRelationCategory) as B "
                + " where B.codenRight = C.codenRight and A.codenLeft = C.codenLeft "
                + " and A.codSemanticRelationCategory = B.codSemanticRelationCategory "
                + " and A.codSemanticRelationCategory = C.codSemanticRelationCategory "
                + " and A.codSemanticRelationCategory = " + codSemanticRelationCategory
                + " and A.en = \"" + ENLeft + "\" and B.en = \"" + ENRight + "\" "
                + " and A.codCategory = " + codCategoryLeft + " and B.codCategory = " + codCategoryRight;
        int idCoupledEN = 0;
        try {
            System.out.println(sql);
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.last();
            if (rs.getRow() == 0) {
                return 0;
            } else {
                rs.first();
                idCoupledEN = rs.getInt("idencouple");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return idCoupledEN;
    }

    public int IsThereSemanticRelationPAIRS(String ENLeft, String ENRight, int codCategoryLeft, int codCategoryRight, String semanticrelationcontext, int codSemanticRelationCategory) {
        rs = null;
        String sql = "SELECT A.idsemanticrelationpairs "
                + " FROM semanticrelationpairs as A,semanticrelationcontext, "
                + " (select EN,codCategory "
                + " from encouple join EN on iden = codenLeft) as B,"
                + " (select EN,codCategory "
                + " from encouple join EN on iden = codenRight) as C"
                + " where semanticrelationcontext = \"" + semanticrelationcontext + "\""
                + " and semanticrelationcontext.codSemanticRelationCategory = " + codSemanticRelationCategory
                + " and semanticrelationcontext.idsemanticrelationcontext = A.codsemanticrelationcontext "
                + " and idsemanticrelationcontext = A.codSemanticRelationContext "
                + " and B.en = \"" + ENLeft + "\" and C.en = \"" + ENRight + "\" "
                + " and B.codCategory = " + codCategoryLeft + " and C.codCategory = " + codCategoryRight;
        int idsemanticrelationpairs = 0;
        try {
            System.out.println(sql);
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.last();
            if (rs.getRow() == 0) {
                return 0;
            } else {
                rs.first();
                idsemanticrelationpairs = rs.getInt("idsemanticrelationpairs");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return idsemanticrelationpairs;
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
            Logger.getLogger(UtilitBD_AllPairsDataToSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return cod;
    }

    public void UpdateEN(String EN, int codCategory, int positive_occurrence, int positive_co_occurrence, int Negative_occurrence, int Negative_co_occurrence, double confidence) {
        rs = null;
        String sql = "";
        sql = " update EN set "
                + " positive_occurrence = " + positive_occurrence + ", "
                + " positive_co_occurrence = " + positive_co_occurrence + ", "
                + "Negative_occurrence = " + Negative_occurrence + ", "
                + " Negative_co_occurrence = " + Negative_co_occurrence + ", "
                + " confidence = " + confidence
                + " where EN = \"" + EN + "\" and codCategory = " + codCategory;
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

    public void UpdateCOUPLED_EN(int codENCouple, int codSemanticRelationCategory, int positive_occurrence, int positive_co_occurrence, int Negative_occurrence, int Negative_co_occurrence, double confidence) {
        rs = null;
        String sql = "";
        sql = " update ENCouple set "
                + " positive_occurrence = " + positive_occurrence + ", "
                + " positive_co_occurrence = " + positive_co_occurrence + ", "
                + " Negative_occurrence = " + Negative_occurrence + ", "
                + " Negative_co_occurrence = " + Negative_co_occurrence + ", "
                + " confidence = " + confidence
                + " where idENCouple = " + codENCouple + " and codSemanticRelationCategory = " + codSemanticRelationCategory;
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

    public void UpdateSemanticRelationContext(String Context, int codSemanticRelationCategory, int positive_occurrence, int positive_co_occurrence, int Negative_occurrence, int Negative_co_occurrence, double confidence) {
        rs = null;
        String sql = "";
        sql = " update SemanticRelationContext set "
                + " positive_occurrence = " + positive_occurrence + ", "
                + " positive_co_occurrence = " + positive_co_occurrence + ", "
                + " Negative_occurrence = " + Negative_occurrence + ", "
                + " Negative_co_occurrence = " + Negative_co_occurrence + ", "
                + " confidence = " + confidence
                + " where SemanticRelationContext = \"" + Context + "\"  and codSemanticRelationCategory = " + codSemanticRelationCategory;
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

    public void UpdateUsedInIteration(String Type, int ENorContext, int iteration) {
        rs = null;
        String sql = "";
        sql = " update " + Type + " set UsedInIteration = (" + iteration + ") where id" + Type + " = " + ENorContext;
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro na atualização:" + e.getMessage());
            System.out.println(sql);
        }
    }

    public void insertIteration() {
        int iteration = GetIteration() + 1;
        String sql = "";
        sql = " insert into iteration(iteration,dateANDtime) values (" + iteration + ",(NOW()))";
        try {
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
        String sql = " select max(Iteration) as iteration from iteration";
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

    public void GetPositiveAndNegativeOccurrenceAndCo_Occurrence__TOLearnCOUPLED_EN(String ENLeft, String ENRight, int codCategoryLeft, int codCategoryRight, int codSemanticRelationCategory, int PositiveAndNegativeOccurrenceAndCo_Occurrence[]) {
        rs = null;
        String sql = "";
        try {
            sql = " select distinct C. positive_occurrence,C.positive_co_occurrence from encouple as C, "
                    + " (select codSemanticRelationCategory,en,codCategory,codenLeft "
                    + " from  encouple join en on iden = encouple.codenLeft) as A,  "
                    + " (select codSemanticRelationCategory,en,codCategory,codenRight "
                    + " from  encouple join en on iden = encouple.codenRight "
                    + " join SemanticRelationCategory as T "
                    + " on T.idSemanticRelationCategory = codSemanticRelationCategory) as B "
                    + " where A.codSemanticRelationCategory = B.codSemanticRelationCategory "
                    + " and A.codSemanticRelationCategory = C.codSemanticRelationCategory "
                    + " and A.codSemanticRelationCategory = " + codSemanticRelationCategory
                    + " and A.en = \"" + ENLeft + "\" and B.en = \"" + ENRight + "\" "
                    + " and A.codCategory = " + codCategoryLeft + " and B.codCategory = " + codCategoryRight;
            System.out.println(sql);

            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            PositiveAndNegativeOccurrenceAndCo_Occurrence[0] = PositiveAndNegativeOccurrenceAndCo_Occurrence[0] + rs.getInt("Positive_Occurrence");
            PositiveAndNegativeOccurrenceAndCo_Occurrence[1] = PositiveAndNegativeOccurrenceAndCo_Occurrence[1] + rs.getInt("Positive_Co_Occurrence");
            rs = null;
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GetPositiveAndNegativeOccurrenceAndCo_Occurrence_TOLearnSEMANTICRELATION_CONTEXT(String SemanticRelationContext, int codSemanticRelationCategory, int PositiveAndNegativeOccurrenceAndCo_Occurrence[]) {
        rs = null;
        String sql = "";
        try {
            sql = "Select * from SemanticRelationContext where SemanticRelationContext = \"" + SemanticRelationContext + "\"  and codSemanticRelationCategory =" + codSemanticRelationCategory;
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            PositiveAndNegativeOccurrenceAndCo_Occurrence[0] = PositiveAndNegativeOccurrenceAndCo_Occurrence[0] + rs.getInt("Positive_Occurrence");
            PositiveAndNegativeOccurrenceAndCo_Occurrence[1] = PositiveAndNegativeOccurrenceAndCo_Occurrence[1] + rs.getInt("Positive_Co_Occurrence");
            rs = null;
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ExtStopWords> UpVectorStopWords(List<ExtStopWords> ListStopWords) {
        try {
            String sql = "";
            String word = "";
            int idWord;

            sql = " select * from stopwords";
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();

            while (rs.next()) {
                word = rs.getString("word");
                idWord = rs.getInt("idStopWords");
                ListStopWords.add(new ExtStopWords(word, idWord));
            }
            word = null;
            sql = null;
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_RTWP_TOSemanticRelation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListStopWords;
    }

    public int IsThereENorContext(String Type, String ENorContext, int codSEMANTICRELATIONCategory, String more) {
        rs = null;
        String sql = " select id" + Type + " from " + Type + " where " + Type + " = \"" + ENorContext + "\" and codSEMANTICRELATIONCategory = " + codSEMANTICRELATIONCategory + more;
        int idENorContext = 0;
        try {
            System.out.println(sql);
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.last();
            if (rs.getRow() == 0) {
                return 0;
            } else {
                rs.first();
                idENorContext = rs.getInt("id" + Type);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_AllPairsDataToCategory.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return idENorContext;
    }

    public void insertSemanticRelationContext(int codCategoryLeft, int codCategoryRight, String SemanticRelationContext, int codSemanticRelationCategory, double confidence, int positive_occurrence,
            int positive_co_occurrence, int negative_occurrence, int negative_co_occurrence, int learnedInIteration) {
        String sql = "";
        sql = " insert into SemanticRelationContext (codCategoryRight,codCategoryLeft,SemanticRelationContext, codSemanticRelationCategory,confidence,form,positive_occurrence,positive_co_occurrence, "
                + " negative_occurrence,negative_co_occurrence,usedInIteration,learnedInIteration) "
                + " values (" + codCategoryRight + "," + codCategoryLeft + "\"" + SemanticRelationContext + "," + codSemanticRelationCategory + "," + confidence + positive_occurrence
                + "," + positive_co_occurrence + "," + negative_occurrence + "," + negative_co_occurrence + "," + 0 + "," + learnedInIteration + ")";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 1 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }
}
