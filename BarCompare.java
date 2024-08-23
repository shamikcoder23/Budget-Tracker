import java.sql.*;

import java.awt.*;

import javax.swing.*;

import org.jfree.data.category.DefaultCategoryDataset;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;

public class BarCompare extends JFrame{
    public BarCompare(int id, Connection con) throws SQLException{
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Expenditure");
        setSize(1800, 500);
        setResizable(false);
        getContentPane().setLayout(new GridLayout());
        getContentPane().setBackground(Color.white);
        getContentPane().setForeground(Color.BLACK);

        try{
            DefaultCategoryDataset jdataset = new DefaultCategoryDataset(); 
            int i = 1;           
            while(i <= id){
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM expen WHERE id = ?");
                stmt.setInt(1, i);
                ResultSet rs = stmt.executeQuery();
                PreparedStatement stmt2 = con.prepareStatement("SELECT * FROM income WHERE id = ?");
                stmt2.setInt(1, i);
                ResultSet rs1 = stmt2.executeQuery();
                jdataset.addValue(rs.getDouble("food"), "Food", rs1.getString("month"));
                jdataset.addValue(rs.getDouble("entertainment"), "Entertainment", rs1.getString("month"));
                jdataset.addValue(rs.getDouble("rent"), "Rent", rs1.getString("month"));
                jdataset.addValue(rs.getDouble("miscellaneous"), "Miscellaneous", rs1.getString("month"));
                double expen = rs.getDouble("food") + rs.getDouble("entertainment") + rs.getDouble("rent") + rs.getDouble("miscellaneous");
                jdataset.addValue(rs1.getDouble("salary") - expen, "Savings", rs1.getString("month"));
                i++;
            }
                   
            JFreeChart bChart = ChartFactory.createBarChart("Expenditure for each month", "Month", "Expense", jdataset, PlotOrientation.VERTICAL, true, true, false);
            ChartPanel cpanel = new ChartPanel(bChart);
            cpanel.setVisible(true);
            add(cpanel);
            setContentPane(cpanel);
            
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }    
}
