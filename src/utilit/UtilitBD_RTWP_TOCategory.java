/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

import RTWP.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utilit.Negative.Context_tobe_Negative;
import utilit.Negative.EN_tobe_Negative;

/**
 *
 * @author MaisaDuarte
 */
public class UtilitBD_RTWP_TOCategory {

    private static Connection conn;
    private static Statement stm;
    private ResultSet rs = null;
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String DATABASE_URL = "jdbc:mysql://localhost:3306/rtwp_phd_www_Coupled_teste";
    private static String user = "root";
    private static String password = "root";

    public String getDATABASE_URL() {
        return DATABASE_URL;
    }

    public void setDATABASE_URL(String DATABASE_URL) {
        this.DATABASE_URL = DATABASE_URL;
    }

    public void connect() {
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

    public void disconnect() {
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

    public List<Categories> GetCategories() {

        List<Categories> CategoriesList = new ArrayList<>();
        String sql = " select * from category order by idCategory";
        rs = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                CategoriesList.add(new Categories(rs.getInt("idCategory"), rs.getString("Category"), rs.getString("IsNotNegativeFor"), rs.getBoolean("IsName")));
            }
            rs = null;
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return CategoriesList;
    }

    public boolean Sampling(double probability) {
        double sort = (Math.random() * 100);
        if (sort <= probability) {
            return true;
        } else {
            return false;
        }
    }

    public double GetProbability(double confidence, double borderConfidende) {
        double probability = 0;
        probability = (100 * (confidence)) / borderConfidende;
        return probability;
    }

    public List<Context_tobe_Negative> GetContextToNegativeList_Part2(List<Context> ListContext, List CategoriesNegatives) {

        int cont_qtCat = 0;
        List<Context_tobe_Negative> Context_tobe_Negative = new ArrayList<>();
        int i = 0;
        Collections.sort(ListContext);
        int Index_ListCand_Neg = 0;

        //Infere a probabidade MÁXIMA (BORDER CONFIDENCE) para sementes MENOS AS QUE NÃO foram alteradas durante as execuções (VALORES DE 100 DE CONFIDENCE)
        while (CategoriesNegatives.size() > cont_qtCat) {
            double confidence = GetMaxConfidence_Of_NegativeExamples_ToLearningEN(CategoriesNegatives.get(cont_qtCat).toString());
            if (confidence > 0) {
                Index_ListCand_Neg = 0;
                while (ListContext.size() > Index_ListCand_Neg) {
                    if (ListContext.get(Index_ListCand_Neg).getCodCategory() == Integer.parseInt(CategoriesNegatives.get(cont_qtCat).toString())) {
                        ListContext.get(Index_ListCand_Neg).setBorderConfidence(confidence);
                    }
                    Index_ListCand_Neg++;
                }
            }
            cont_qtCat++;
        }
        Index_ListCand_Neg = 0;

        //Infere 90% de probabidade para sementes dadas ao sistema e que NÃO foram alteradas durante as execuções
        while (ListContext.size() > Index_ListCand_Neg) {
            if (ListContext.get(Index_ListCand_Neg).getConfidence() == 100) {
                ListContext.get(Index_ListCand_Neg).setBorderConfidence(90);
                ListContext.get(Index_ListCand_Neg).setIsANegativeSeed(Sampling(ListContext.get(Index_ListCand_Neg).getBorderConfidence()));
            } else {
                ListContext.get(Index_ListCand_Neg).setIsANegativeSeed(Sampling(GetProbability(ListContext.get(Index_ListCand_Neg).getConfidence(), ListContext.get(Index_ListCand_Neg).getBorderConfidence())));
                ListContext.get(Index_ListCand_Neg).setIsANegativeSeed(Sampling(ListContext.get(Index_ListCand_Neg).getBorderConfidence()));
            }
            Index_ListCand_Neg++;
        }
        i = 0;
        //Seleciona os exemplos negativos até o valor fixado em CategoriesLearning.CutListNegative_toLearningNE

        int countGetCategories = 0;
        //Seleciona os primeios de cada categoria
        while ((Context_tobe_Negative.size() < CategoriesLearning.CutListNegative_toLearningNE)
                && (!ListContext.isEmpty())) {
            Index_ListCand_Neg = 0;
            cont_qtCat = 0;
            if (countGetCategories == 0) {
                while (CategoriesNegatives.size() > cont_qtCat) {
                    Index_ListCand_Neg = 0;
                    int codCat = Integer.parseInt(CategoriesNegatives.get(cont_qtCat).toString());
                    while (ListContext.size() > Index_ListCand_Neg) {
                        if (ListContext.get(Index_ListCand_Neg).getCodCategory() == codCat) {
                            Context_tobe_Negative.add(new Context_tobe_Negative(
                                    ListContext.get(Index_ListCand_Neg).getIdcontext(),
                                    ListContext.get(Index_ListCand_Neg).getContext(),
                                    ListContext.get(Index_ListCand_Neg).getConfidence(),
                                    ListContext.get(Index_ListCand_Neg).getCodCategory(),
                                    ListContext.get(Index_ListCand_Neg).getSide()));
                            ListContext.remove(Index_ListCand_Neg);
                            Index_ListCand_Neg = ListContext.size();
                        }
                        Index_ListCand_Neg++;
                    }
                    cont_qtCat++;
                }
                countGetCategories++;
            } else {
                //Seleciona o restante de cada categoria
                while ((CategoriesNegatives.size() > cont_qtCat) && (Context_tobe_Negative.size() < CategoriesLearning.CutListNegative_toLearningNE)
                        && (!ListContext.isEmpty())) {
                    Index_ListCand_Neg = 0;
                    int codCat = Integer.parseInt(CategoriesNegatives.get(cont_qtCat).toString());
                    while ((ListContext.size() > Index_ListCand_Neg) && (Context_tobe_Negative.size() < CategoriesLearning.CutListNegative_toLearningNE)
                            && (!ListContext.isEmpty())) {
                        if (ListContext.get(Index_ListCand_Neg).getCodCategory() == codCat) {
                            if (ListContext.get(Index_ListCand_Neg).IsANegativeSeed == true) {
                                Context_tobe_Negative.add(new Context_tobe_Negative(
                                        ListContext.get(Index_ListCand_Neg).getIdcontext(),
                                        ListContext.get(Index_ListCand_Neg).getContext(),
                                        ListContext.get(Index_ListCand_Neg).getConfidence(),
                                        ListContext.get(Index_ListCand_Neg).getCodCategory(),
                                        ListContext.get(Index_ListCand_Neg).getSide()));
                            }
                            ListContext.remove(Index_ListCand_Neg);
                            Index_ListCand_Neg = ListContext.size();
                        }
                        Index_ListCand_Neg++;
                    }
                    cont_qtCat++;
                }
                Index_ListCand_Neg++;
            }
        }
        i = 0;

        return Context_tobe_Negative;
    }

    public List<EN_tobe_Negative> GetENToNegativeList_Part2(List<EN> ListEN, List CategoriesNegatives) {

        int cont_qtCat = 0;
        List<EN_tobe_Negative> EN_tobe_Negative = new ArrayList<>();
        int i = 0;
        Collections.sort(ListEN);
        int Index_ListCand_Neg = 0;

        //Infere a probabidade MÁXIMA (BORDER CONFIDENCE) para sementes MENOS AS QUE NÃO foram alteradas durante as execuções (VALORES DE 100 DE CONFIDENCE)
        while (CategoriesNegatives.size() > cont_qtCat) {
            double confidence = GetMaxConfidence_Of_NegativeExamples_ToLearningContext(CategoriesNegatives.get(cont_qtCat).toString());
            if (confidence > 0) {
                Index_ListCand_Neg = 0;
                while (ListEN.size() > Index_ListCand_Neg) {
                    if (ListEN.get(Index_ListCand_Neg).getCodCategory() == Integer.parseInt(CategoriesNegatives.get(cont_qtCat).toString())) {
                        ListEN.get(Index_ListCand_Neg).setBorderConfidence(confidence);
                    }
                    Index_ListCand_Neg++;
                }
            }
            cont_qtCat++;
        }
        Index_ListCand_Neg = 0;

        //Infere 90% de probabidade para sementes dadas ao sistema e que NÃO foram alteradas durante as execuções
        while (ListEN.size() > Index_ListCand_Neg) {
            if (ListEN.get(Index_ListCand_Neg).getConfidence() == 100) {
                ListEN.get(Index_ListCand_Neg).setBorderConfidence(90);
                ListEN.get(Index_ListCand_Neg).setIsANegativeSeed(Sampling(ListEN.get(Index_ListCand_Neg).getBorderConfidence()));
            } else {
                ListEN.get(Index_ListCand_Neg).setIsANegativeSeed(Sampling(GetProbability(ListEN.get(Index_ListCand_Neg).getConfidence(), ListEN.get(Index_ListCand_Neg).getBorderConfidence())));
                ListEN.get(Index_ListCand_Neg).setIsANegativeSeed(Sampling(ListEN.get(Index_ListCand_Neg).getBorderConfidence()));
            }
            Index_ListCand_Neg++;
        }
        i = 0;

        //Seleciona os exemplos negativos até o valor fixado em CategoriesLearning.CutListNegative_toLearningContext
        int countGetCategories = 0;

        //Seleciona os primeios de cada categoria
        while ((EN_tobe_Negative.size() < CategoriesLearning.CutListNegative_toLearningContext)
                && (!ListEN.isEmpty())) {
            Index_ListCand_Neg = 0;
            cont_qtCat = 0;
            if (countGetCategories == 0) {
                while (CategoriesNegatives.size() > cont_qtCat) {
                    Index_ListCand_Neg = 0;
                    int codCat = Integer.parseInt(CategoriesNegatives.get(cont_qtCat).toString());
                    while (ListEN.size() > Index_ListCand_Neg) {
                        if (ListEN.get(Index_ListCand_Neg).getCodCategory() == codCat) {
                            EN_tobe_Negative.add(new EN_tobe_Negative(
                                    ListEN.get(Index_ListCand_Neg).getIdEN(),
                                    ListEN.get(Index_ListCand_Neg).getEN(),
                                    ListEN.get(Index_ListCand_Neg).getConfidence(),
                                    ListEN.get(Index_ListCand_Neg).getCodCategory()));
                            ListEN.remove(Index_ListCand_Neg);
                            Index_ListCand_Neg = ListEN.size();
                        }
                        Index_ListCand_Neg++;
                    }
                    cont_qtCat++;
                }
                countGetCategories++;
            } else {
                //Seleciona o restante de cada categoria
                while ((CategoriesNegatives.size() > cont_qtCat) && (EN_tobe_Negative.size() < CategoriesLearning.CutListNegative_toLearningContext)
                        && (!ListEN.isEmpty())) {
                    Index_ListCand_Neg = 0;
                    int codCat = Integer.parseInt(CategoriesNegatives.get(cont_qtCat).toString());
                    while ((ListEN.size() > Index_ListCand_Neg) && (EN_tobe_Negative.size() < CategoriesLearning.CutListNegative_toLearningContext)
                            && (!ListEN.isEmpty())) {
                        if (ListEN.get(Index_ListCand_Neg).getCodCategory() == codCat) {
                            if (ListEN.get(Index_ListCand_Neg).IsANegativeSeed == true) {
                                EN_tobe_Negative.add(new EN_tobe_Negative(
                                        ListEN.get(Index_ListCand_Neg).getIdEN(),
                                        ListEN.get(Index_ListCand_Neg).getEN(),
                                        ListEN.get(Index_ListCand_Neg).getConfidence(),
                                        ListEN.get(Index_ListCand_Neg).getCodCategory()));
                            }
                            ListEN.remove(Index_ListCand_Neg);
                            Index_ListCand_Neg = ListEN.size();
                        }
                        Index_ListCand_Neg++;
                    }
                    cont_qtCat++;
                }
                Index_ListCand_Neg++;
            }
        }
        i = 0;
        return EN_tobe_Negative;
    }

    public List<Context> GetContextToNegativeList_Part1(int codCategory) {
        List<Context> ListContext = new ArrayList();
        rs = null;
        int iteration = GetIteration();
        System.out.println("Exemplos negativos ATÉ iteration -> " + iteration);

        String sql = "SELECT * from Context "
                + " where (LearnedInIteration < " + iteration + ") "
                + " and codCategory not " + GetCategoriesToNegativeExamples(codCategory)
                + " and confidence > 0 "
                + " order by codCategory, confidence desc";
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                ListContext.add(new Context(rs.getInt("idcontext"), rs.getString("Context"),
                        rs.getInt("positive_occurrence"), rs.getInt("codCategory"), rs.getString("side"),
                        rs.getDouble("confidence"), rs.getInt("positive_co_occurrence"),
                        rs.getInt("Negative_occurrence"), rs.getInt("Negative_co_occurrence"),
                        rs.getInt("usedInIteration"), rs.getInt("LearnedInIteration")));
            }

        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Anda logo!");
        }
        return ListContext;
    }

    public List<EN> GetENToNegativeList_Part1(int codCategory) {
        List<EN> ListEN = new ArrayList();
        rs = null;
        int iteration = GetIteration();
        System.out.println("Exemplos negativos ATÉ iteration -> " + iteration);
        String sql = "SELECT * "
                + " FROM EN "
                + " where (LearnedInIteration < " + iteration + ") "
                + " and codCategory not " + GetCategoriesToNegativeExamples(codCategory)
                + " and confidence > 0 "
                + " order by codCategory, confidence desc";
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                ListEN.add(new EN(rs.getInt("idEN"), rs.getString("EN"),
                        rs.getInt("positive_occurrence"), rs.getInt("codCategory"),
                        rs.getDouble("confidence"),  rs.getInt("positive_co_occurrence"),
                        rs.getInt("Negative_occurrence"), rs.getInt("Negative_co_occurrence"),
                        rs.getInt("usedInIteration"), rs.getInt("LearnedInIteration")));
            }

            System.out.println(sql);
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListEN;
    }

    public List<Context> GetContextToList(int codCategory) {
        List<Context> ListContext = new ArrayList();
        rs = null;
        String sql = "select * from Context "
                + " where codCategory = " + codCategory;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                ListContext.add(new Context(rs.getInt("idcontext"), rs.getString("Context"),
                        rs.getInt("positive_occurrence"), rs.getInt("codCategory"), rs.getString("side"),
                        rs.getDouble("confidence"), rs.getInt("positive_co_occurrence"),
                        rs.getInt("Negative_occurrence"), rs.getInt("Negative_co_occurrence"),
                        rs.getInt("usedInIteration"), rs.getInt("LearnedInIteration")));
            }

        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListContext;
    }

    public List<EN> GetENToList(int codCategory) {
        List<EN> ListEN = new ArrayList();
        rs = null;
        String sql = "SELECT * "
                + " FROM EN "
                + " where codCategory = " + codCategory;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                ListEN.add(new EN(rs.getInt("idEN"), rs.getString("EN"),
                        rs.getInt("positive_occurrence"), rs.getInt("codCategory"),
                        rs.getDouble("confidence"), rs.getInt("positive_co_occurrence"),
                        rs.getInt("Negative_occurrence"), rs.getInt("Negative_co_occurrence"),
                        rs.getInt("usedInIteration"), rs.getInt("LearnedInIteration")));
            }

        } catch (SQLException ex) {
            System.out.println("ERRO na query: \n" + sql);
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListEN;
    }

    public List<TOLearnEN_ConfidenceCalculated> ApplyingNegativeExamplesTOLearnEN_ONLY_BRAZILIAN_CORPUS(int idCategory, List<TOLearnEN_ConfidenceCalculated> TOLearnEN_ConfidenceCalculated) {

        UtilitBD_AllPairsDataToCategory UtilitBD_AllPairsDataToCategory = new UtilitBD_AllPairsDataToCategory();

        String sql = "";
        int i = 0;

        UtilitBD_RTWP_TOCategory UtilitBD_RTWP_TOCategory = new UtilitBD_RTWP_TOCategory();
        //UtilitBD_RTWP_TOCategory.connect();
        List<Context> ListContext = new ArrayList();
        ListContext = UtilitBD_RTWP_TOCategory.GetContextToNegativeList_Part1(idCategory);

        List<Context_tobe_Negative> Context_tobe_Negative = new ArrayList();
        List CategoriesNegatives = new ArrayList();
        CategoriesNegatives = UtilitBD_RTWP_TOCategory.GetCategories_INVERT_NegativeExamples(idCategory);
        Context_tobe_Negative = UtilitBD_RTWP_TOCategory.GetContextToNegativeList_Part2(ListContext, CategoriesNegatives);
        //UtilitBD_RTWP_TOCategory.disconnect();

        while (TOLearnEN_ConfidenceCalculated.size() > i) {

            String general = "SELECT sum(occurrence) as Occurrence, count(pairs_to_category.idpairs_to_category) as Co_Occurrence FROM pairs_to_category "
                    + " where (EN in (\"" + TOLearnEN_ConfidenceCalculated.get(i).getEN() + "\")) and (";
            int y = 0;
            int w = 0;
            String Aux = "";
            int SumOccurrence = 0;
            int SumCo_Occurrence = 0;
            while (Context_tobe_Negative.size() > y) {
                if (w == 0) {
                    Aux = Aux + "( context = \"" + Context_tobe_Negative.get(y).getContext() + "\")";
                } else if ((w <= 300) || ((Context_tobe_Negative.size() + 1) == y)) {
                    Aux = "( context = \"" + Context_tobe_Negative.get(y).getContext() + "\") or " + Aux;
                }
                if ((w == 100) || ((Context_tobe_Negative.size() - 1) == y)) {
                    Aux = Aux + ")";
                    sql = general + Aux;
                    System.out.println(sql);

                    //Faz Query no banco de AllPairsData0
                    //UtilitBD_AllPairsDataToCategory.connect();
                    int[] OccurrenceAndCo_Occurrence = new int[2];
                    UtilitBD_AllPairsDataToCategory.GetOccurrenceAndCo_Occurrence_NEGATIVE(sql, OccurrenceAndCo_Occurrence);
                   //UtilitBD_AllPairsDataToCategory.disconnect();

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
            TOLearnEN_ConfidenceCalculated.get(i).setNegative_occurrence(SumOccurrence);
            TOLearnEN_ConfidenceCalculated.get(i).setNegative_co_occurrence(SumCo_Occurrence);
            i++;
        }
        return TOLearnEN_ConfidenceCalculated;
    }

    public List<TOLearnContext_ConfidenceCalculated> ApplyingNegativeExamplesTOLearnContext_ONLY_BRAZILIAN_CORPUS(int idCategory, List<TOLearnContext_ConfidenceCalculated> TOLearnContext_ConfidenceCalculated) {

        UtilitBD_AllPairsDataToCategory UtilitBD_AllPairsDataToCategory = new UtilitBD_AllPairsDataToCategory();

        String sql = "";
        int i = 0;

        UtilitBD_RTWP_TOCategory UtilitBD_RTWP_TOCategory = new UtilitBD_RTWP_TOCategory();
        //UtilitBD_RTWP_TOCategory.connect();
        List<EN> ListEN = new ArrayList();
        ListEN = UtilitBD_RTWP_TOCategory.GetENToNegativeList_Part1(idCategory);

        List<EN_tobe_Negative> EN_tobe_Negative = new ArrayList();
        List CategoriesNegatives = new ArrayList();
        CategoriesNegatives = UtilitBD_RTWP_TOCategory.GetCategories_INVERT_NegativeExamples(idCategory);
        EN_tobe_Negative = UtilitBD_RTWP_TOCategory.GetENToNegativeList_Part2(ListEN, CategoriesNegatives);
        //UtilitBD_RTWP_TOCategory.disconnect();

        while (TOLearnContext_ConfidenceCalculated.size() > i) {

            String general = "SELECT sum(pairs_to_category.occurrence) as Occurrence, count(pairs_to_category.idpairs_to_category) as Co_Occurrence FROM pairs_to_category "
                    + " where (Context = \"" + TOLearnContext_ConfidenceCalculated.get(i).getContext() + "\") and  EN in(";
            int y = 0;
            int w = 0;
            String Aux = "";
            int SumOccurrence = 0;
            int SumCo_Occurrence = 0;
            while (EN_tobe_Negative.size() > y) {

                Aux = Aux + "\"" + EN_tobe_Negative.get(y).getNE() + "\" ";

                if ((w == 300) || ((EN_tobe_Negative.size() - 1) == y)) {

                    Aux = Aux + ")";

                    sql = general + Aux;
                    System.out.println(sql);

                    //Faz Query no banco de AllPairsData
                    //UtilitBD_AllPairsDataToCategory.connect();
                    int[] OccurrenceAndCo_Occurrence = new int[2];
                    UtilitBD_AllPairsDataToCategory.GetOccurrenceAndCo_Occurrence_NEGATIVE(sql, OccurrenceAndCo_Occurrence);
                    //UtilitBD_AllPairsDataToCategory.disconnect();

                    //Mermoriza contagem e co-ocorrência
                    SumOccurrence = SumOccurrence + OccurrenceAndCo_Occurrence[0]; //Occurrence
                    SumCo_Occurrence = SumCo_Occurrence + OccurrenceAndCo_Occurrence[1]; //Co-Occurrence
                    w = -1;
                    Aux = "";
                    sql = "";
                } else {
                    Aux = Aux + ",";
                }
                w++;
                y++;
            }
            TOLearnContext_ConfidenceCalculated.get(i).setNegative_occurrence(SumOccurrence);
            TOLearnContext_ConfidenceCalculated.get(i).setNegative_co_occurrence(SumCo_Occurrence);
            i++;
        }
        return TOLearnContext_ConfidenceCalculated;
    }

    public List<TOLearnEN_ConfidenceCalculated> ApplyingNegativeExamplesTOLearnEN(int idCategory, List<TOLearnEN_ConfidenceCalculated> TOLearnEN_ConfidenceCalculated) {

        UtilitBD_AllPairsDataToCategory UtilitBD_AllPairsDataToCategory = new UtilitBD_AllPairsDataToCategory();

        String sql = "";
        int i = 0;

        UtilitBD_RTWP_TOCategory UtilitBD_RTWP_TOCategory = new UtilitBD_RTWP_TOCategory();
        //UtilitBD_RTWP_TOCategory.connect();
        List<Context> ListContext = new ArrayList();
        ListContext = UtilitBD_RTWP_TOCategory.GetContextToNegativeList_Part1(idCategory);

        List<Context_tobe_Negative> Context_tobe_Negative = new ArrayList();
        List CategoriesNegatives = new ArrayList();
        CategoriesNegatives = UtilitBD_RTWP_TOCategory.GetCategories_INVERT_NegativeExamples(idCategory);
        Context_tobe_Negative = UtilitBD_RTWP_TOCategory.GetContextToNegativeList_Part2(ListContext, CategoriesNegatives);
        //UtilitBD_RTWP_TOCategory.disconnect();

        while (TOLearnEN_ConfidenceCalculated.size() > i) {

            String general = "SELECT sum(pairs_to_category.occurrence) as Occurrence, count(pairs_to_category.idpairs_to_category) as Co_Occurrence FROM pairs_to_category "
                    + " where (EN in (\"" + TOLearnEN_ConfidenceCalculated.get(i).getEN() + "\")) and (";
            int y = 0;
            int w = 0;
            String Aux = "";
            int SumOccurrence = 0;
            int SumCo_Occurrence = 0;
            while (Context_tobe_Negative.size() > y) {
                if (w == 0) {
                    Aux = Aux + "( context = \"" + Context_tobe_Negative.get(y).getContext() + "\" and side in(\""
                            + Context_tobe_Negative.get(y).getSide() + "\"))";
                } else if ((w <= 300) || ((Context_tobe_Negative.size() + 1) == y)) {
                    Aux = "( context = \"" + Context_tobe_Negative.get(y).getContext() + "\" and "
                            + " side in(\"" + Context_tobe_Negative.get(y).getSide() + "\")) or " + Aux;
                }
                if ((w == 100) || ((Context_tobe_Negative.size() - 1) == y)) {
                    Aux = Aux + ")";
                    sql = general + Aux;
                    //System.out.println(sql);

                    //Faz Query no banco de AllPairsData0
                    //UtilitBD_AllPairsDataToCategory.connect();
                    int[] OccurrenceAndCo_Occurrence = new int[2];
                    UtilitBD_AllPairsDataToCategory.GetOccurrenceAndCo_Occurrence_NEGATIVE(sql, OccurrenceAndCo_Occurrence);
                    //UtilitBD_AllPairsDataToCategory.disconnect();

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
            TOLearnEN_ConfidenceCalculated.get(i).setNegative_occurrence(SumOccurrence);
            TOLearnEN_ConfidenceCalculated.get(i).setNegative_co_occurrence(SumCo_Occurrence);
            i++;
        }
        return TOLearnEN_ConfidenceCalculated;
    }

    public List<TOLearnContext_ConfidenceCalculated> ApplyingNegativeExamlesTOLearnContext(int idCategory, List<TOLearnContext_ConfidenceCalculated> TOLearnContext_ConfidenceCalculated) {

        UtilitBD_AllPairsDataToCategory UtilitBD_AllPairsDataToCategory = new UtilitBD_AllPairsDataToCategory();

        String sql = "";
        int i = 0;

        UtilitBD_RTWP_TOCategory UtilitBD_RTWP_TOCategory = new UtilitBD_RTWP_TOCategory();
        //UtilitBD_RTWP_TOCategory.connect();
        List<EN> ListEN = new ArrayList();
        ListEN = UtilitBD_RTWP_TOCategory.GetENToNegativeList_Part1(idCategory);

        List<EN_tobe_Negative> EN_tobe_Negative = new ArrayList();
        List CategoriesNegatives = new ArrayList();
        CategoriesNegatives = UtilitBD_RTWP_TOCategory.GetCategories_INVERT_NegativeExamples(idCategory);
        EN_tobe_Negative = UtilitBD_RTWP_TOCategory.GetENToNegativeList_Part2(ListEN, CategoriesNegatives);
        //UtilitBD_RTWP_TOCategory.disconnect();

        while (TOLearnContext_ConfidenceCalculated.size() > i) {

            String general = "SELECT sum(pairs_to_category.occurrence) as Occurrence, count(pairs_to_category.idpairs_to_category) as Co_Occurrence FROM pairs_to_category "
                    + " where (Context = \"" + TOLearnContext_ConfidenceCalculated.get(i).getContext() + "\") and  EN in(";
            int y = 0;
            int w = 0;
            String Aux = "";
            int SumOccurrence = 0;
            int SumCo_Occurrence = 0;
            while (EN_tobe_Negative.size() > y) {

                Aux = Aux + "\"" + EN_tobe_Negative.get(y).getNE() + "\" ";

                if ((w == 300) || ((EN_tobe_Negative.size() - 1) == y)) {

                    Aux = Aux + ")";

                    sql = general + Aux;
                    //System.out.println(sql);

                    //Faz Query no banco de AllPairsData
                    //UtilitBD_AllPairsDataToCategory.connect();
                    int[] OccurrenceAndCo_Occurrence = new int[2];
                    UtilitBD_AllPairsDataToCategory.GetOccurrenceAndCo_Occurrence_NEGATIVE(sql, OccurrenceAndCo_Occurrence);
                    //UtilitBD_AllPairsDataToCategory.disconnect();

                    //Mermoriza contagem e co-ocorrência
                    SumOccurrence = SumOccurrence + OccurrenceAndCo_Occurrence[0]; //Occurrence
                    SumCo_Occurrence = SumCo_Occurrence + OccurrenceAndCo_Occurrence[1]; //Co-Occurrence
                    w = -1;
                    Aux = "";
                    sql = "";
                } else {
                    Aux = Aux + ",";
                }
                w++;
                y++;
            }
            TOLearnContext_ConfidenceCalculated.get(i).setNegative_occurrence(SumOccurrence);
            TOLearnContext_ConfidenceCalculated.get(i).setNegative_co_occurrence(SumCo_Occurrence);
            i++;
        }
        return TOLearnContext_ConfidenceCalculated;
    }

    public String GetCategoriesToNegativeExamples(int idCategory) {
        String IsNotNegativeFor = "";
        String sql = " select IsNotNegativeFor from category where idCategory = " + idCategory;
        rs = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            IsNotNegativeFor = rs.getString("IsNotNegativeFor");
            rs = null;
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return IsNotNegativeFor;
    }

    public List GetCategories_INVERT_NegativeExamples(int idCategory) {
        List Categories = new ArrayList();
        String sql = " select idCategory from category where idCategory not " + GetCategoriesToNegativeExamples(idCategory);
        rs = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                Categories.add(rs.getInt("idCategory"));
            }
            rs = null;
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Categories;
    }

    public double GetMaxConfidence_Of_NegativeExamples_ToLearningEN(String codCategory) {
        double Confidence = 0;
        String sql = " select max(confidence) as confidence from Context "
                + " where codCategory = " + codCategory + " and confidence != 100";
        rs = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            Confidence = rs.getDouble("confidence");
            rs = null;
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Confidence;
    }

    public double GetMaxConfidence_Of_NegativeExamples_ToLearningContext(String codCategory) {
        double Confidence = 0;
        String sql = " select max(confidence) as confidence from EN "
                + " where codCategory = " + codCategory + " and confidence != 100";
        rs = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            Confidence = rs.getDouble("confidence");
            rs = null;
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Confidence;
    }

    public void insertEN(String EN, int codcategory, double confidence, int positive_occurrence,
            int positive_co_occurrence, int negative_occurrence, int negative_co_occurrence, int learnedInIteration) {
        String sql = "";
        sql = " insert into EN (EN, codcategory,confidence,positive_occurrence,positive_co_occurrence, "
                + " negative_occurrence,negative_co_occurrence,usedInIteration,learnedInIteration) "
                + " values (\"" + EN + "\"," + codcategory + "," + confidence + "," + positive_occurrence
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

    public void insertContext(String Context, String side, int codcategory, double confidence, int positive_occurrence,
            int positive_co_occurrence, int negative_occurrence, int negative_co_occurrence, int learnedInIteration) {
        String sql = "";
        sql = " insert into Context (Context, side, codcategory,confidence,positive_occurrence,positive_co_occurrence, "
                + " negative_occurrence,negative_co_occurrence,usedInIteration,learnedInIteration) "
                + " values (\"" + Context + "\",\"" + side + "\"," + codcategory + "," + confidence + "," + positive_occurrence
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

    public void PromotingEN(int codCategory, int iteration, int countENLearned, List<TOLearnEN_ConfidenceCalculated> TOLearnEN_ConfidenceCalculated, List<TOLearnEN_CandidatesTOPromote> TOLearnEN_CandidatesTOPromote) {

        int i = 0;
        int idENorContext = 0;
        int maxCandidates = 0;

        /* if ((TOLearnEN_ConfidenceCalculated.size() / countENLearned) <= 2) {
         countENLearned = TOLearnEN_ConfidenceCalculated.size();
         } else {
         countENLearned = TOLearnEN_ConfidenceCalculated.size() / countENLearned;
         }
         */
        while (TOLearnEN_ConfidenceCalculated.size() > i) {
            idENorContext = IsThereENorContext("EN", TOLearnEN_ConfidenceCalculated.get(i).getEN(), codCategory, "");
            if ((idENorContext == 0) && (maxCandidates < countENLearned)) {
                insertEN(TOLearnEN_ConfidenceCalculated.get(i).getEN(), codCategory, TOLearnEN_ConfidenceCalculated.get(i).getConfidence(),
                        TOLearnEN_ConfidenceCalculated.get(i).getPositive_occurrence(),
                        TOLearnEN_ConfidenceCalculated.get(i).getPositive_co_occurrence(), TOLearnEN_ConfidenceCalculated.get(i).getNegative_occurrence(),
                        TOLearnEN_ConfidenceCalculated.get(i).getNegative_co_occurrence(), iteration);
                maxCandidates++;
                //Insere todos os pares formados com a EN aprendida (ou seja, os pares de co-orrência)
                Add_inBD_The_EN_Existing_inLIST(TOLearnEN_ConfidenceCalculated.get(i).getEN(), codCategory, TOLearnEN_CandidatesTOPromote);
            } else if (idENorContext != 0) {

                int[] PositiveAndNegativeOccurrenceAndCo_Occurrence = new int[4];
                int control = 0;
                control = Search_inBD_AndRemove_inLIST_EN_Existing(TOLearnEN_ConfidenceCalculated.get(i).getEN(), codCategory, TOLearnEN_CandidatesTOPromote, PositiveAndNegativeOccurrenceAndCo_Occurrence);
                if (control == 1) {
                    GetPositiveAndNegativeOccurrenceAndCo_Occurrence__TOLearnEN(TOLearnEN_ConfidenceCalculated.get(i).getEN(), codCategory, PositiveAndNegativeOccurrenceAndCo_Occurrence);

                    PositiveAndNegativeOccurrenceAndCo_Occurrence[2] = TOLearnEN_ConfidenceCalculated.get(i).getNegative_occurrence();
                    PositiveAndNegativeOccurrenceAndCo_Occurrence[3] = TOLearnEN_ConfidenceCalculated.get(i).getNegative_co_occurrence();

                    double PositiveConfidence = 0;
                    double NegativeConfidence = 0;
                    double confidence = 0;

                    if ((PositiveAndNegativeOccurrenceAndCo_Occurrence[0] > 0) && (PositiveAndNegativeOccurrenceAndCo_Occurrence[1] > 0)) {
                        PositiveConfidence = Double.valueOf(String.format("%.2f", ((Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[0])) * (Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[1])))).replace(",", "."));
                    } else {
                        PositiveConfidence = 0;
                    }
                    if ((PositiveAndNegativeOccurrenceAndCo_Occurrence[2] > 0) && (PositiveAndNegativeOccurrenceAndCo_Occurrence[3] > 0)) {
                        NegativeConfidence = Double.valueOf(String.format("%.2f", ((Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[2])) * (Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[3])))).replace(",", "."));
                    } else {
                        NegativeConfidence = 0;
                    }
                    confidence = PositiveConfidence - NegativeConfidence;

                    UpdateEN(TOLearnEN_ConfidenceCalculated.get(i).getEN(), codCategory, PositiveAndNegativeOccurrenceAndCo_Occurrence[0],
                            PositiveAndNegativeOccurrenceAndCo_Occurrence[1], PositiveAndNegativeOccurrenceAndCo_Occurrence[2],
                            PositiveAndNegativeOccurrenceAndCo_Occurrence[3], confidence);

                    TOLearnEN_ConfidenceCalculated.remove(i);
                    i--;
                }
                //maxCandidates = TOLearnEN_ConfidenceCalculated.size() / countENLearned;
            }
            i++;
        }
    }

    public void PromotingContext(int codCategory, int iteration, int ModMax, List<TOLearnContext_ConfidenceCalculated> TOLearnContext_ConfidenceCalculated, List<TOLearnContext_CandidatesTOPromote> TOLearnContext_CandidatesTOPromote) {
        int i = 0;
        int idENorContext = 0;
        int maxCandidates = 0;
        while ((TOLearnContext_ConfidenceCalculated.size() > i)) {
            idENorContext = IsThereENorContext("Context", TOLearnContext_ConfidenceCalculated.get(i).getContext(), codCategory, " and side = \"" + TOLearnContext_ConfidenceCalculated.get(i).getSide() + "\"");
            if ((idENorContext == 0) && (maxCandidates < ModMax)) {
                insertContext(TOLearnContext_ConfidenceCalculated.get(i).getContext(), TOLearnContext_ConfidenceCalculated.get(i).getSide(), codCategory, TOLearnContext_ConfidenceCalculated.get(i).getConfidence(),
                        TOLearnContext_ConfidenceCalculated.get(i).getPositive_occurrence(),
                        TOLearnContext_ConfidenceCalculated.get(i).getPositive_co_occurrence(), TOLearnContext_ConfidenceCalculated.get(i).getNegative_occurrence(),
                        TOLearnContext_ConfidenceCalculated.get(i).getNegative_co_occurrence(), iteration);
                maxCandidates++;
                //Insere todos os pares formados com a Context aprendidos (ou seja, os pares de co-orrência)
                Add_inBD_The_Context_Existing_inLIST(TOLearnContext_ConfidenceCalculated.get(i).getContext(), TOLearnContext_ConfidenceCalculated.get(i).getSide(), codCategory, TOLearnContext_CandidatesTOPromote);
            } else if (idENorContext != 0) {

                int[] PositiveAndNegativeOccurrenceAndCo_Occurrence = new int[4];
                int control = 0;
                control = Search_inBD_AndRemove_inLIST_Context_Existing(TOLearnContext_ConfidenceCalculated.get(i).getContext(), TOLearnContext_ConfidenceCalculated.get(i).getSide(), codCategory, TOLearnContext_CandidatesTOPromote, PositiveAndNegativeOccurrenceAndCo_Occurrence);
                if (control == 1) {
                    GetPositiveAndNegativeOccurrenceAndCo_Occurrence_TOLearnCONTEXT(TOLearnContext_ConfidenceCalculated.get(i).getContext(), TOLearnContext_ConfidenceCalculated.get(i).getSide(), codCategory, PositiveAndNegativeOccurrenceAndCo_Occurrence);

                    PositiveAndNegativeOccurrenceAndCo_Occurrence[2] = TOLearnContext_ConfidenceCalculated.get(i).getNegative_occurrence();
                    PositiveAndNegativeOccurrenceAndCo_Occurrence[3] = TOLearnContext_ConfidenceCalculated.get(i).getNegative_co_occurrence();

                    double PositiveConfidence = 0;
                    double NegativeConfidence = 0;
                    double confidence = 0;

                    if ((PositiveAndNegativeOccurrenceAndCo_Occurrence[0] > 0) && (PositiveAndNegativeOccurrenceAndCo_Occurrence[1] > 0)) {
                        PositiveConfidence = Double.valueOf(String.format("%.2f", ((Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[0])) * (Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[1])))).replace(",", "."));
                    } else {
                        PositiveConfidence = 0;
                    }
                    if ((PositiveAndNegativeOccurrenceAndCo_Occurrence[2] > 0) && (PositiveAndNegativeOccurrenceAndCo_Occurrence[3] > 0)) {
                        NegativeConfidence = Double.valueOf(String.format("%.2f", ((Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[2])) * (Math.log10(PositiveAndNegativeOccurrenceAndCo_Occurrence[3])))).replace(",", "."));
                    } else {
                        NegativeConfidence = 0;
                    }
                    confidence = PositiveConfidence - NegativeConfidence;

                    UpdateContext(TOLearnContext_ConfidenceCalculated.get(i).getContext(), TOLearnContext_ConfidenceCalculated.get(i).getSide(), codCategory, PositiveAndNegativeOccurrenceAndCo_Occurrence[0],
                            PositiveAndNegativeOccurrenceAndCo_Occurrence[1], PositiveAndNegativeOccurrenceAndCo_Occurrence[2],
                            PositiveAndNegativeOccurrenceAndCo_Occurrence[3], confidence);
                    TOLearnContext_ConfidenceCalculated.remove(i);
                    i--;
                }
            }

            i++;
        }
    }

    public void Add_inBD_The_EN_Existing_inLIST(String EN, int codCategory, List<TOLearnEN_CandidatesTOPromote> TOLearnEN_CandidatesTOPromote) {
        int i = 0;
        while (i < TOLearnEN_CandidatesTOPromote.size()) {
            if (TOLearnEN_CandidatesTOPromote.get(i).getEN().equalsIgnoreCase(EN)) {
                insertPairs(TOLearnEN_CandidatesTOPromote.get(i).getContext(), TOLearnEN_CandidatesTOPromote.get(i).getSide(), EN, TOLearnEN_CandidatesTOPromote.get(i).getOccurrence(), codCategory);
            }
            i++;
        }
    }

    public void Add_inBD_The_Context_Existing_inLIST(String Context, String side, int codCategory, List<TOLearnContext_CandidatesTOPromote> TOLearnContext_CandidatesTOPromote) {
        int i = 0;
        while (i < TOLearnContext_CandidatesTOPromote.size()) {
            if ((TOLearnContext_CandidatesTOPromote.get(i).getContext().equalsIgnoreCase(Context)) && (TOLearnContext_CandidatesTOPromote.get(i).getSide().equalsIgnoreCase(side))) {
                insertPairs(Context, side, TOLearnContext_CandidatesTOPromote.get(i).getEN(), TOLearnContext_CandidatesTOPromote.get(i).getOccurrence(), codCategory);
            }
            i++;
        }
    }

    public int Search_inBD_AndRemove_inLIST_EN_Existing(String EN, int codCategory, List<TOLearnEN_CandidatesTOPromote> TOLearnEN_CandidatesTOPromote, int PositiveAndNegativeOccurrenceAndCo_Occurrence[]) {
        int i = 0;
        int control = 0;
        while (i < TOLearnEN_CandidatesTOPromote.size()) {
            if (TOLearnEN_CandidatesTOPromote.get(i).getEN().equalsIgnoreCase(EN)) {
                int idPairs = IsTherePairs(EN, TOLearnEN_CandidatesTOPromote.get(i).getContext(), TOLearnEN_CandidatesTOPromote.get(i).getSide(), codCategory);
                if (idPairs == 0) {
                    insertPairs(TOLearnEN_CandidatesTOPromote.get(i).getContext(), TOLearnEN_CandidatesTOPromote.get(i).getSide(), EN, TOLearnEN_CandidatesTOPromote.get(i).getOccurrence(), codCategory);
                    PositiveAndNegativeOccurrenceAndCo_Occurrence[0] = PositiveAndNegativeOccurrenceAndCo_Occurrence[0] + TOLearnEN_CandidatesTOPromote.get(i).getOccurrence();
                    PositiveAndNegativeOccurrenceAndCo_Occurrence[1] = PositiveAndNegativeOccurrenceAndCo_Occurrence[1] + 1;
                    control = 1;
                }
            }
            i++;
        }
        return control;
    }

    public int Search_inBD_AndRemove_inLIST_Context_Existing(String Context, String side, int codCategory, List<TOLearnContext_CandidatesTOPromote> TOLearnContext_CandidatesTOPromote, int PositiveAndNegativeOccurrenceAndCo_Occurrence[]) {
        int i = 0;
        int control = 0;
        while (i < TOLearnContext_CandidatesTOPromote.size()) {
            if ((TOLearnContext_CandidatesTOPromote.get(i).getContext().equalsIgnoreCase(Context)) && (TOLearnContext_CandidatesTOPromote.get(i).getSide().equalsIgnoreCase(side))) {
                int idPairs = IsTherePairs(TOLearnContext_CandidatesTOPromote.get(i).getEN(), Context, side, codCategory);
                if (idPairs == 0) {
                    insertPairs(Context, side, TOLearnContext_CandidatesTOPromote.get(i).getEN(), TOLearnContext_CandidatesTOPromote.get(i).getOccurrence(), codCategory);
                    PositiveAndNegativeOccurrenceAndCo_Occurrence[0] = PositiveAndNegativeOccurrenceAndCo_Occurrence[0] + TOLearnContext_CandidatesTOPromote.get(i).getOccurrence();
                    PositiveAndNegativeOccurrenceAndCo_Occurrence[1] = PositiveAndNegativeOccurrenceAndCo_Occurrence[1] + 1;
                    control = 1;
                }
            }
            i++;
        }
        return control;
    }

    public void insertPairs(int codContext, int codEN, int occurrence, int codCategory) {
        String sql = "";
        sql = " insert into Pairs(codContext,codEN,occurrence,codCategory) values (" + codContext + "," + codEN + "," + occurrence + "," + codCategory + ")";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 3 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public void insertPairs(String Context, String side, String EN, int occurrence, int codCategory) {
        String sql = "";
        sql = "insert into Pairs(codContext,codEN,occurrence,codCategory) "
                + " values ((select idContext from context "
                + " where context = \"" + Context + "\" and side = \"" + side + "\" and codCategory = " + codCategory + "), "
                + " (select idEN from en where EN = \"" + EN + "\" and codCategory = " + codCategory + ")," + occurrence + "," + codCategory + ")";
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 3 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public void UpdatePairs(int idPairs, int occurrence) {
        String sql = "";
        sql = "update Pairs set(occurrence = occurence + " + occurrence + ") where idPairs = " + idPairs;
        try {
            stm = conn.createStatement();
            int rows = stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            System.out.println("Erro 3 na inserção de " + sql + "\n" + e.getMessage());
            System.out.println(sql);
        }
    }

    public int IsTherePairs(String EN, String Context, String side, int codCategory) {
        rs = null;
        String sql = " select idpairs from pairs "
                + " join context on idcontext = codcontext "
                + " join en on iden = codEN "
                + " where EN = \"" + EN + "\" and "
                + " Context = \"" + Context + "\" and side in (\"T\",\"" + side + "\")  and "
                + " pairs.codCategory = " + codCategory;
        //System.out.println(sql);
        int idENorContext = 0;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.last();
            if (rs.getRow() == 0) {
                return 0;
            } else {
                rs.first();
                idENorContext = rs.getInt("idpairs");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return idENorContext;
    }

    public int IsThereENorContext(String Type, String ENorContext, int codCategory, String more) {
        rs = null;
        String sql = " select id" + Type + " from " + Type + " where " + Type + " = \"" + ENorContext + "\" and codCategory = " + codCategory + more;
        int idENorContext = 0;
        try {
            //System.out.println(sql);
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
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return idENorContext;
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
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
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

    public void UpdateContext(String Context, String side, int codCategory, int positive_occurrence, int positive_co_occurrence, int Negative_occurrence, int Negative_co_occurrence, double confidence) {
        rs = null;
        String sql = "";
        sql = " update Context set "
                + " positive_occurrence = " + positive_occurrence + ", "
                + " positive_co_occurrence = " + positive_co_occurrence + ", "
                + "Negative_occurrence = " + Negative_occurrence + ", "
                + " Negative_co_occurrence = " + Negative_co_occurrence + ", "
                + " confidence = " + confidence
                + " where Context = \"" + Context + "\" and side = \"" + side + "\" and codCategory = " + codCategory;
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

    /*
     * public static void UpdateIteration() { rs = null; String sql = ""; sql =
     * " update iteration set iteration = (iteration + 1), dateANDtime =
     * (NOW())"; try { stm = conn.createStatement(); int rows =
     * stm.executeUpdate(sql); stm.close(); } catch (SQLException e) {
     * System.out.println("Erro na atualização:" + e.getMessage());
     * System.out.println(sql); } }
     */
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
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sql);
        }
        return iteration;
    }

    public void GetPositiveAndNegativeOccurrenceAndCo_Occurrence__TOLearnEN(String EN, int codCategory, int PositiveAndNegativeOccurrenceAndCo_Occurrence[]) {
        rs = null;
        String sql = "";
        try {
            sql = "Select * from en where en = \"" + EN + "\" and codCategory =" + codCategory;
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            PositiveAndNegativeOccurrenceAndCo_Occurrence[0] = PositiveAndNegativeOccurrenceAndCo_Occurrence[0] + rs.getInt("Positive_Occurrence");
            PositiveAndNegativeOccurrenceAndCo_Occurrence[1] = PositiveAndNegativeOccurrenceAndCo_Occurrence[1] + rs.getInt("Positive_Co_Occurrence");
            rs = null;
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GetPositiveAndNegativeOccurrenceAndCo_Occurrence_TOLearnCONTEXT(String Context, String side, int codCategory, int PositiveAndNegativeOccurrenceAndCo_Occurrence[]) {
        rs = null;
        String sql = "";
        try {
            sql = "Select * from Context where context = \"" + Context + "\" and side =  \"" + side + "\"  and codCategory =" + codCategory;
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.first();
            PositiveAndNegativeOccurrenceAndCo_Occurrence[0] = PositiveAndNegativeOccurrenceAndCo_Occurrence[0] + rs.getInt("Positive_Occurrence");
            PositiveAndNegativeOccurrenceAndCo_Occurrence[1] = PositiveAndNegativeOccurrenceAndCo_Occurrence[1] + rs.getInt("Positive_Co_Occurrence");
            rs = null;
        } catch (SQLException ex) {
            System.out.println("ERRO na query: " + sql);
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UtilitBD_RTWP_TOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListStopWords;
    }
}
