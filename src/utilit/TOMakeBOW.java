/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

import java.sql.*;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author MaisaDuarte
 */
public class TOMakeBOW {

    List ListBOW = new ArrayList();

    public void TOMakeBOW() {
        Connection conn1 = null;
        Connection conn2 = null;
        Statement stm = null;
        ResultSet rs = null;
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DATABASE_URL1 = "";
        String DATABASE_URL2 = "";

        DATABASE_URL1 = "jdbc:mysql://localhost:3306/rtwp_phd_nc_13_3_freefilter";
        DATABASE_URL2 = "jdbc:mysql://localhost:3306/rtwp_phd_nc_3_13_freefilter";

        String user = "root";
        String password = "";

        try {
            Class.forName(JDBC_DRIVER);
            conn1 = DriverManager.getConnection(DATABASE_URL1, user, password);
            conn2 = DriverManager.getConnection(DATABASE_URL2, user, password);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(TOMakeBOW.class.getName()).log(Level.SEVERE, null, ex);
        }

        rs = null;
        String sql = "select * from en where codCategory = 1 order by en";
        try {
            stm = conn1.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                ListBOW.add(rs.getString("EN"));
            }
            conn1.close();

            int i = 0;
            while (ListBOW.size() > i) {
                System.out.println(ListBOW.get(i).toString());
                i++;
            }

            stm = conn2.createStatement();
            rs = stm.executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                if (ListBOW.indexOf(rs.getString("EN")) == -1) {
                    ListBOW.add(rs.getString("EN"));
                } else {
                }
            }
            conn1.close();

        } catch (SQLException ex) {
            Logger.getLogger(TOMakeBOW.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs.close();
            stm.close();
            conn2.close();
        } catch (SQLException ex) {
            Logger.getLogger(TOMakeBOW.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        TOMakeBOW TMBOW = new TOMakeBOW();
        TMBOW.TOMakeBOW();
    }
}
