import java.sql.*;

import java.awt.*;
//import java.awt.event.*;

import javax.swing.*;

//import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;

class BarCreate extends JFrame{
    public BarCreate(int id, Connection con) throws SQLException{
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Expenditure");
        setSize(800, 500);
        setResizable(false);
        getContentPane().setLayout(new GridLayout());
        getContentPane().setBackground(Color.white);
        getContentPane().setForeground(Color.BLACK);

        // DriverManager.registerDriver(new org.sqlite.JDBC());
        // Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\shami\\Java project\\code\\sqlite-jdbc-3.46.0.1\\src\\main\\java\\userdata.db");
        // connection.setAutoCommit(false);
        // addWindowListener(new WindowAdapter() {
        //     public void windowClosing (WindowEvent event){
        //         try {
        //             connection.close();
        //         } catch (SQLException e) {
        //             System.out.println(e);
        //         }
        //         System.exit(0);
        //     }
        // });

        try{
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM expen WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            DefaultCategoryDataset jdataset = new DefaultCategoryDataset();

                //System.out.println(rs.getInt("food") + " is food.");
                jdataset.addValue(rs.getDouble("food"), "Expense", "Food");
                //System.out.println(rs.getInt("entertainment"));
                jdataset.addValue(rs.getDouble("entertainment"), "Expense", "Entertainment");
                //System.out.println(rs.getInt("rent"));
                jdataset.addValue(rs.getDouble("rent"), "Expense", "Rent");
                //System.out.println(rs.getInt("miscellaneous"));
                jdataset.addValue(rs.getDouble("miscellaneous"), "Expense", "Miscellaneous");

            double expen = rs.getDouble("food") + rs.getDouble("entertainment") + rs.getDouble("rent") + rs.getDouble("miscellaneous");

            PreparedStatement stmt2 = con.prepareStatement("SELECT * FROM income WHERE id = ?");
            stmt2.setInt(1, id);
            rs = stmt2.executeQuery();
            String title = "Monthly Expenditure and Savings for " + rs.getString("month");

            jdataset.addValue(rs.getDouble("salary") - expen, "Savings", "Savings");
                
            JFreeChart bChart = ChartFactory.createBarChart(title, "Category", "Amount", jdataset, PlotOrientation.VERTICAL, true, true, false);
            ChartPanel cpanel = new ChartPanel(bChart);
            cpanel.setVisible(true);
            add(cpanel);
            setContentPane(cpanel);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}   

