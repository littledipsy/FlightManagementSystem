package Customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.eltima.components.ui.DatePicker;

public class AlterTicketsWindow extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField startTextField;
    private JTextField arrivelTextField;
    private JButton confirmButton,cancelButton,button;;
    private JTable table;
    private DefaultTableModel tableModel;
    final DatePicker datepick;
    private String userid,airlinecode,formertraveldate,startcity,arrivecity;
    private int ticketsnum;

    DbConnect dbConnect = new DbConnect();
    Connection conn = dbConnect.connect();

    /**
     * Create the frame.
     */

    public AlterTicketsWindow(String userid,String traveldate,String airlinecode,int ticketsnum) {

        super("改签");
        this.userid = userid;
        this.formertraveldate = traveldate;
        this.airlinecode = airlinecode;
        this.ticketsnum = ticketsnum;


        setBounds(400, 200, 850, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);


        //售票中心
        JPanel ticketsPanel = new JPanel();
        contentPane.add(ticketsPanel);
        ticketsPanel.setLayout(new BorderLayout(0, 0));

        JPanel choosePanel = new JPanel();
        choosePanel.setBackground(new Color(238, 238, 238));
        ticketsPanel.add(choosePanel, BorderLayout.NORTH);

        //出发城市输入框
        JLabel label = new JLabel("出发城市：");
        choosePanel.add(label);
        startTextField = new JTextField();
        startTextField.setColumns(10);
        choosePanel.add(startTextField);


        //到达城市输入框
        JLabel label_1 = new JLabel("到达城市：");
        choosePanel.add(label_1);
        arrivelTextField = new JTextField();
        arrivelTextField.setColumns(10);
        choosePanel.add(arrivelTextField);

        try {
            String sqlforcity = "SELECT * FROM AirlineDate WHERE AirlineCode = ?";
            PreparedStatement preparedStatementforcity = conn.prepareStatement(sqlforcity);
            preparedStatementforcity.setString(1, airlinecode);
            System.out.println(airlinecode);

            ResultSet res = preparedStatementforcity.executeQuery();
            if(res.next()){
                this.startcity = res.getString("startCity");
                this.arrivecity = res.getString("lastCity");
                System.out.println(startcity + "  " + arrivecity);
            }
            preparedStatementforcity.close();

            String start = "",arrive = "";
            //查找出发城市缩写
            String sqlforstart = "SELECT * from Domestic WHERE Abbreviation = ?";
            PreparedStatement preparedStatementforstart = conn.prepareStatement(sqlforstart);
            preparedStatementforstart.setString(1, startcity);
            ResultSet res_1 = preparedStatementforstart.executeQuery();
            if(res_1.next()) {
                start = res_1.getString("Address_cn");
                startTextField.setText(start);
                startTextField.setEditable(false);
            }
            preparedStatementforstart.close();
            //查找到达城市缩写
            String sqlforarrive = "SELECT * from Domestic WHERE Abbreviation = ?";
            PreparedStatement preparedStatementforarrive = conn.prepareStatement(sqlforarrive);
            preparedStatementforarrive.setString(1, arrivecity);
            ResultSet res_2 = preparedStatementforarrive.executeQuery();
            if(res_2.next()) {
                arrive = res_2.getString("Address_cn");
                arrivelTextField.setText(arrive);
                arrivelTextField.setEditable(false);
            }

            preparedStatementforarrive.close();

        }catch (Exception e){
            e.printStackTrace();
        }


        //日期选择框
        JPanel datePanel = new JPanel();
        //ticketsPanel.add(datePanel, BorderLayout.CENTER);

        datepick = getDatePicker();
        JLabel label_2 = new JLabel("日期：");
        choosePanel.add(label_2);
        datePanel.add(datepick,BorderLayout.CENTER);
        choosePanel.add(datePanel);

        //查询按钮
        button = new JButton("查询");
        button.addActionListener(this);
        choosePanel.add(button);

        //查询结果
        JScrollPane scrollPane = new JScrollPane();
        ticketsPanel.add(scrollPane, BorderLayout.CENTER);


        table = new JTable();
        tableModel = (DefaultTableModel) table.getModel();
        tableModel.addColumn("航空公司");
        tableModel.addColumn("航线编号");
        tableModel.addColumn("飞机型号");
        tableModel.addColumn("起飞机场");
        tableModel.addColumn("降落机场");
        tableModel.addColumn("起飞时间");
        tableModel.addColumn("降落时间");
        tableModel.addColumn("单张票价");
        tableModel.addColumn("剩余座位");
        scrollPane.setViewportView(table);

        //操作
        JPanel operatePanel = new JPanel();
        ticketsPanel.add(operatePanel, BorderLayout.SOUTH);
        confirmButton = new JButton("确定");
        confirmButton.addActionListener(this);
        operatePanel.add(confirmButton);
        cancelButton = new JButton("取消");
        cancelButton.addActionListener(this);
        operatePanel.add(cancelButton);

        setDefaultCloseOperation(1);
        setResizable(false);
        setVisible(true);
    }



    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){

            for (int index = tableModel.getRowCount() - 1; index >= 0; index--) {
                tableModel.removeRow(index);
            }
            //清除表中原有的项。
            table.repaint();
            try{
                String sqlforplane = "SELECT * from AirlineDate WHERE startCity = ? AND lastCity = ?";
                PreparedStatement preparedStatementforplane = conn.prepareStatement(sqlforplane);
                preparedStatementforplane.setString(1, startcity);
                preparedStatementforplane.setString(2, arrivecity);
                ResultSet res_3 = preparedStatementforplane.executeQuery();

                while(res_3.next()){
                    String company = res_3.getString("Company");
                    String airlinecode = res_3.getString("AirlineCode");
                    String mode = res_3.getString("Mode");
                    String startdrome = res_3.getString("StartDrome");
                    String arrivedrome = res_3.getString("ArriveDrome");
                    String starttime = res_3.getString("StartTime");
                    String arrivetime = res_3.getString("ArriveTime");
                    int price = res_3.getInt("AirlinePrice");
                    int seats = 0;
                    //查找该日期的航班剩余票数
                    String sqlforseats = "SELECT * from SeatsRemain WHERE AirlineCode = ? AND Date = ?";
                    PreparedStatement preparedStatementforseats = conn.prepareStatement(sqlforseats);
                    preparedStatementforseats.setString(1, airlinecode);
                    preparedStatementforseats.setString(2, datepick.getText());
                    ResultSet res_4 = preparedStatementforseats.executeQuery();
                    if(res_4.next()) {
                        seats = res_4.getInt("Seats_Remain");
                    }
                    preparedStatementforseats.close();

                    //将该航班信息显示出来
                    Vector vector = new Vector();
                    vector.add(company);
                    vector.add(airlinecode);
                    vector.add(mode);
                    vector.add(startdrome);
                    vector.add(arrivedrome);
                    vector.add(starttime);
                    vector.add(arrivetime);
                    vector.add(price);
                    vector.add(seats);
                    tableModel.addRow(vector);
                }
                preparedStatementforplane.close();

            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
        if(e.getSource() == confirmButton){
            if(table.getSelectedRow() != -1) {
                String airlinecode = tableModel.getValueAt(table.getSelectedRow(), 1).toString();
                int ticketsremain =  Integer.valueOf(tableModel.getValueAt(table.getSelectedRow(), 8).toString());
                //System.out.println(ticketsremain);
                String traveldate = datepick.getText();
                String startcity = startTextField.getText();
                String arrivecity = arrivelTextField.getText();
                if(ticketsremain == 0)
                    JOptionPane.showMessageDialog(null,"该航班已满仓，请选择别的航班！");
                else {
                    //实时更新显示的信息
                    new OrderTicketsWindow(traveldate,formertraveldate, airlinecode, startcity, arrivecity, userid, ticketsremain,ticketsnum,0)
                            .addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosed(WindowEvent e) {
                                    AlterTicketsWindow.super.dispose();
                                }
                            });;
                }
            }
            else
                JOptionPane.showMessageDialog(null,"请先选择航班再进行操作！");
        }

    }

    private static DatePicker getDatePicker() {
        final DatePicker datepick;
        // 格式
        String DefaultFormat = "yyyy-MM-dd";
        // 当前时间
        Date date = new Date();
        // 字体
        Font font = new Font("Times New Roman", Font.BOLD, 14);

        Dimension dimension = new Dimension(177, 24);

        int[] hilightDays = { 1, 3, 5, 7 };

        int[] disabledDays = { 4, 6, 5, 9 };
        //构造方法（初始时间，时间显示格式，字体，控件大小）
        datepick = new DatePicker(date, DefaultFormat, font, dimension);

        // 设置一个月份中需要高亮显示的日子
        //datepick.setHightlightdays(hilightDays, Color.red);
        // 设置一个月份中不需要的日子，呈灰色显示
        //datepick.setDisableddays(disabledDays);
        // 设置国家
        datepick.setLocale(Locale.CANADA);
        // 设置时钟面板可见
        // datepick.setTimePanleVisible(true);
        return datepick;
    }

}
