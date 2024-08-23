import java.sql.*;
import javax.sql.*;
import javax.sql.rowset.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.jdbc.JDBCCategoryDataset;

@SuppressWarnings("unused")
public class Connect extends JFrame {
    private Font labelFont = new Font("Algerian", Font.BOLD, 30);
    private Font contentFont = new Font("Lucida", Font.BOLD, 20);
    private Font textFont = new Font("Lucida", Font.BOLD, 15);
    private JSplitPane mainSPane, rightSPane, centerSPane;
    private JPanel left, right, top, bottom;
    private JLabel greet, cont, month, inc, tmp1, tmp2, tmp3, foodExp, entExp, rentExp, miscExp;
    private JTextArea monthField, incField, foodField, entField, rentField, miscField;
    private JButton incBtn, expBtn, Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec, comp;
    private int id = 0;

    private String m, i;

    private ChartPanel cpanel;
    private JFreeChart bChart;
    private Connect() throws SQLException{
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Your Personal Budget Tracker");
        setSize(1100, 700);
        setResizable(false);
        getContentPane().setLayout(new GridLayout());
        getContentPane().setBackground(Color.white);
        getContentPane().setForeground(Color.BLACK);

        DriverManager.registerDriver(new org.sqlite.JDBC());
        Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\shami\\Java project\\code\\sqlite-jdbc-3.46.0.1\\src\\main\\java\\userdata.db");
        connection.setAutoCommit(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing (WindowEvent event){
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
                System.exit(0);
            }
        });

        mainSPane = new JSplitPane();
        left = new JPanel();
        left.setMaximumSize(new Dimension(250, 700));
        left.setMinimumSize(new Dimension(250, 700));
        left.setBackground(Color.YELLOW);
        rightSPane = new JSplitPane();        
        getContentPane().add(mainSPane);
        mainSPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        mainSPane.setDividerLocation(250);
        mainSPane.setLeftComponent(left);
        mainSPane.setRightComponent(rightSPane);

        right = new JPanel();
        right.setMaximumSize(new Dimension(150, 700));
        right.setMinimumSize(new Dimension(150, 700));
        right.setBackground(Color.WHITE);
        centerSPane = new JSplitPane();
        rightSPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        rightSPane.setDividerLocation(550);
        rightSPane.setLeftComponent(centerSPane);
        rightSPane.setRightComponent(right);
                
        top = new JPanel();
        top.setMaximumSize(new Dimension(550, 350));
        top.setMinimumSize(new Dimension(550, 350));
        top.setBackground(Color.WHITE);
        bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        centerSPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        centerSPane.setDividerLocation(150);
        centerSPane.setTopComponent(top);
        centerSPane.setBottomComponent(bottom);

        /* Layout done */
        greet = new JLabel("Welcome User !");
        left.add(greet);
        greet.setFont(labelFont);

        cont = new JLabel("Start Your Tracking");
        left.add(cont);
        cont.setFont(contentFont);

        month = new JLabel("Enter Month name (mmm or month):");
        month.setForeground(Color.DARK_GRAY);
        left.add(month);
        monthField = new JTextArea(1, 15);
        monthField.setBackground(Color.WHITE);
        monthField.setFont(textFont);
        left.add(monthField);
        inc = new JLabel("Enter income:");
        inc.setForeground(Color.DARK_GRAY);
        left.add(inc);
        incField = new JTextArea(1, 15);
        incField.setBackground(Color.WHITE);
        incField.setFont(textFont);
        left.add(incField);

        tmp1 = new JLabel("");
        tmp1.setFont(contentFont);
        top.add(tmp1);
        tmp2 = new JLabel("");
        tmp2.setFont(contentFont);
        top.add(tmp2);
        tmp3 = new JLabel("");
        tmp3.setFont(contentFont);
        top.add(tmp3);

        incBtn = new JButton("Add");
        incBtn.setFont(textFont);
        incBtn.setBackground(Color.BLACK);
        incBtn.setForeground(Color.YELLOW);
        top.setBackground(Color.CYAN);           
        
        right.setBackground(Color.GREEN);
        foodExp = new JLabel("Enter expenditure in food:");
        foodExp.setForeground(Color.DARK_GRAY);
        foodExp.setVisible(true);
        right.add(foodExp);
        foodField = new JTextArea(1, 15);
        foodField.setBackground(Color.WHITE);
        foodField.setFont(textFont);
        foodField.setVisible(true);
        right.add(foodField);
        entExp = new JLabel("Enter expenditure in entertainment:");
        entExp.setForeground(Color.DARK_GRAY);
        entExp.setVisible(true);
        right.add(entExp);
        entField = new JTextArea(1, 15);
        entField.setBackground(Color.WHITE);
        entField.setFont(textFont);
        entField.setVisible(true);
        right.add(entField);
        rentExp = new JLabel("Enter expenditure in bills:");
        rentExp.setForeground(Color.DARK_GRAY);
        rentExp.setVisible(true);
        right.add(rentExp);
        rentField = new JTextArea(1, 15);
        rentField.setBackground(Color.WHITE);
        rentField.setFont(textFont);
        rentField.setVisible(true);
        right.add(rentField);
        miscExp = new JLabel("Enter miscellaneous expenditure:");
        miscExp.setForeground(Color.DARK_GRAY);
        miscExp.setVisible(true);
        right.add(miscExp);
        miscField = new JTextArea(1, 15);
        miscField.setBackground(Color.WHITE);
        miscField.setFont(textFont);
        miscField.setVisible(true);
        right.add(miscField);

        expBtn = new JButton("Add");
        expBtn.setFont(textFont);
        expBtn.setBackground(Color.BLACK);
        expBtn.setForeground(Color.GREEN);
        expBtn.setVisible(true);

        greet = new JLabel("Your Expense Report till the current month :     ");
        greet.setFont(contentFont);
        greet.setVisible(false);
        bottom.add(greet);

        Jan = new JButton("January");
        Jan.setFont(textFont);
        Jan.setBackground(Color.DARK_GRAY);
        Jan.setForeground(Color.WHITE);
        Jan.setVisible(false);
        bottom.add(Jan);

        Feb = new JButton("February");
        Feb.setFont(textFont);
        Feb.setBackground(Color.DARK_GRAY);
        Feb.setForeground(Color.WHITE);
        Feb.setVisible(false);
        bottom.add(Feb);

        Mar = new JButton("March");
        Mar.setFont(textFont);
        Mar.setBackground(Color.DARK_GRAY);
        Mar.setForeground(Color.WHITE);
        Mar.setVisible(false);
        bottom.add(Mar);

        Apr = new JButton("April");
        Apr.setFont(textFont);
        Apr.setBackground(Color.DARK_GRAY);
        Apr.setForeground(Color.WHITE);
        Apr.setVisible(false);
        bottom.add(Apr);

        May = new JButton("May");
        May.setFont(textFont);
        May.setBackground(Color.DARK_GRAY);
        May.setForeground(Color.WHITE);
        May.setVisible(false);
        bottom.add(May);

        Jun = new JButton("Jun");
        Jun.setFont(textFont);
        Jun.setBackground(Color.DARK_GRAY);
        Jun.setForeground(Color.WHITE);
        Jun.setVisible(false);
        bottom.add(Jun);

        Jul = new JButton("July");
        Jul.setFont(textFont);
        Jul.setBackground(Color.DARK_GRAY);
        Jul.setForeground(Color.WHITE);
        Jul.setVisible(false);
        bottom.add(Jul);

        Aug = new JButton("August");
        Aug.setFont(textFont);
        Aug.setBackground(Color.DARK_GRAY);
        Aug.setForeground(Color.WHITE);
        Aug.setVisible(false);
        bottom.add(Aug);

        Sep = new JButton("September");
        Sep.setFont(textFont);
        Sep.setBackground(Color.DARK_GRAY);
        Sep.setForeground(Color.WHITE);
        Sep.setVisible(false);
        bottom.add(Sep);

        Oct = new JButton("October");
        Oct.setFont(textFont);
        Oct.setBackground(Color.DARK_GRAY);
        Oct.setForeground(Color.WHITE);
        Oct.setVisible(false);
        bottom.add(Oct);

        Nov = new JButton("November");
        Nov.setFont(textFont);
        Nov.setBackground(Color.DARK_GRAY);
        Nov.setForeground(Color.WHITE);
        Nov.setVisible(false);
        bottom.add(Nov);
        
        Dec = new JButton("December");        
        Dec.setFont(textFont);        
        Dec.setBackground(Color.DARK_GRAY);        
        Dec.setForeground(Color.WHITE);        
        Dec.setVisible(false);
        bottom.add(Dec);

        comp= new JButton("Compare between Months");        
        comp.setFont(textFont);        
        comp.setBackground(Color.DARK_GRAY);        
        comp.setForeground(Color.WHITE);        
        comp.setVisible(false);
        bottom.add(comp);

        incBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){                     
                m = monthField.getText();

                if(m.equalsIgnoreCase("Jan") || m.equalsIgnoreCase("January"))
                    id = 1;
                if(m.equalsIgnoreCase("Feb") || m.equalsIgnoreCase("February"))
                    id = 2;
                if(m.equalsIgnoreCase("Mar") || m.equalsIgnoreCase("March"))
                    id = 3;
                if(m.equalsIgnoreCase("Apr") || m.equalsIgnoreCase("April"))
                    id = 4;
                if(m.equalsIgnoreCase("May"))
                    id = 5;
                if(m.equalsIgnoreCase("Jun") || m.equalsIgnoreCase("June"))
                    id = 6;
                if(m.equalsIgnoreCase("Jul") || m.equalsIgnoreCase("July"))
                    id = 7;
                if(m.equalsIgnoreCase("Aug") || m.equalsIgnoreCase("August"))
                    id = 8;
                if(m.equalsIgnoreCase("Sep") || m.equalsIgnoreCase("September"))
                    id = 9;
                if(m.equalsIgnoreCase("Oct") || m.equalsIgnoreCase("October"))
                    id = 10;
                if(m.equalsIgnoreCase("Nov") || m.equalsIgnoreCase("November"))
                    id = 11;
                if(m.equalsIgnoreCase("Dec") || m.equalsIgnoreCase("December"))
                    id = 12;

                i = incField.getText();
                tmp1.setText("Your income in month " + m + " was Rs. " + i); 
                month.setVisible(true);
                inc.setText("Enter income");
                monthField.setVisible(true);
                incField.setVisible(true);
                incBtn.setVisible(true);
                expBtn.setVisible(true);

                try{
                    PreparedStatement stmt = connection.prepareStatement("INSERT INTO income(id, month, salary) VALUES (?, ?, ?)");
                    stmt.setInt(1, id);
                    stmt.setString(2, m);
                    stmt.setInt(3, Integer.parseInt(i));
                    stmt.executeUpdate();
                    PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM income");
                    ResultSet rs = stmt2.executeQuery();
                    while (rs.next()) {
                        tmp1.setText("Your income in month " + rs.getString("month") + " was Rs. " + rs.getInt("salary"));
                    }
                }
                catch(SQLException exception){
                    exception.printStackTrace();
                }
            }
        });

        expBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){  
                expBtn.setVisible(false);
                String f = foodField.getText();
                String en = entField.getText();
                String r = rentField.getText();
                String mi = miscField.getText();
                int expenditure = Integer.parseInt(f) + Integer.parseInt(en) + Integer.parseInt(r) + Integer.parseInt(mi);
                tmp2.setText("Your total expenditure was Rs. " + expenditure);
                tmp3.setText("Net Savings = Rs. " + (Integer.parseInt(i) - expenditure));
                foodExp.setVisible(true);
                entExp.setVisible(true);
                //rentExp.setText("Enter expenditure in bills");
                miscExp.setVisible(true);
                foodField.setVisible(true);
                entField.setVisible(true);
                rentField.setVisible(true);
                miscField.setVisible(true);  
                
                if(id >= 1){    
                    greet.setVisible(true); 
                    Jan.setVisible(true);               
                    if(id >= 2){
                        Feb.setVisible(true);
                        if(id >= 3){
                            Mar.setVisible(true);
                            if(id >= 4){
                                Apr.setVisible(true);
                                if(id >= 5){
                                    May.setVisible(true);
                                    if(id >= 6){
                                        Jun.setVisible(true);
                                        if(id >= 7){
                                            Jul.setVisible(true);
                                            if(id >= 8){
                                                Aug.setVisible(true);
                                                if(id >= 9){
                                                    Sep.setVisible(true);
                                                    if(id >= 10){
                                                        Oct.setVisible(true);
                                                        if(id >= 11){
                                                            Nov.setVisible(true);
                                                            if(id >= 12)
                                                                Dec.setVisible(true);
                                                        }                                                        
                                                    }                                                    
                                                }                                                
                                            }                                            
                                        }                                        
                                    }                                    
                                }                                
                            }                            
                        }       
                        comp.setVisible(true);                
                    }                    
                }
                            
                try{
                    PreparedStatement stmt = connection.prepareStatement("INSERT INTO expen(id, food, entertainment, rent, miscellaneous) VALUES (?, ?, ?, ?, ?)");
                    stmt.setInt(1, id);
                    stmt.setInt(2, Integer.parseInt(f));
                    stmt.setInt(3, Integer.parseInt(en));
                    stmt.setInt(4, Integer.parseInt(r));
                    stmt.setInt(5, Integer.parseInt(mi));
                    stmt.executeUpdate();
                    // PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM expen");
                    // ResultSet rs = stmt2.executeQuery();
                    // while (rs.next()) {
                    //     System.out.println("expenditures are " + rs.getInt("food") + " for food, " + rs.getString("entertainment") + " for entertainment, " + rs.getString("rent") + " for rent, " + rs.getString("miscellaneous") + " for misc.");
                    // }
                }
                catch(SQLException exception){
                    exception.printStackTrace();
                }
            }
        });               
         
        left.add(incBtn);
        right.add(expBtn);

        Jan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    BarCreate b = new BarCreate(id, connection);
                    b.setVisible(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }            
        });

        Feb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    BarCreate b = new BarCreate(id, connection);
                    b.setVisible(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }            
        });

        Mar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    BarCreate b = new BarCreate(id, connection);
                    b.setVisible(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }            
        });

        Apr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    BarCreate b = new BarCreate(id, connection);
                    b.setVisible(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }            
        });

        May.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    BarCreate b = new BarCreate(id, connection);
                    b.setVisible(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }            
        });

        Jun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    BarCreate b = new BarCreate(id, connection);
                    b.setVisible(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }            
        });

        Jul.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    BarCreate b = new BarCreate(id, connection);
                    b.setVisible(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }            
        });

        Aug.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    BarCreate b = new BarCreate(id, connection);
                    b.setVisible(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }            
        });

        Sep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    BarCreate b = new BarCreate(id, connection);
                    b.setVisible(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }            
        });

        Oct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    BarCreate b = new BarCreate(id, connection);
                    b.setVisible(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }            
        });

        Nov.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    BarCreate b = new BarCreate(id, connection);
                    b.setVisible(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }            
        });

        Dec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    BarCreate b = new BarCreate(id, connection);
                    b.setVisible(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }            
        });

        comp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    BarCompare b = new BarCompare(id, connection);
                    b.setVisible(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }            
        });
    }
    public static void main(String args[]) throws SQLException {
        Connect frame = new Connect();
        frame.setVisible(true);
    }
}