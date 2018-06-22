package Customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.awt.event.ActionEvent;
import com.eltima.components.ui.DatePicker;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class Center extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField startTextField;
    private JTextField arrivelTextField;
    private JButton exchangeButton,confirmButton,cancelButton,button,button_1,button_2,button_3,button_4;
    private JLabel nameLabel,usernameLabel,sexLabel,telLable;
    private JTable table, table_1;
    private DefaultTableModel tableModel,tableModel_1;
    final DatePicker datepick;
    private String userid,todaydate,limitdate;

    //建立数据库的链接
    DbConnect dbConnect = new DbConnect();
    Connection conn = dbConnect.connect();

    /**
     * Create the frame.
     */
    public Center(String userid) {
        this.userid = userid;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 200, 850, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        //售票中心
        JPanel ticketsPanel = new JPanel();
        tabbedPane.addTab("售票中心", null, ticketsPanel, null);
        ticketsPanel.setLayout(new BorderLayout(0, 0));

        JPanel choosePanel = new JPanel();
        choosePanel.setBackground(new Color(238, 238, 238));
        ticketsPanel.add(choosePanel, BorderLayout.NORTH);

        //出发城市输入框
        JLabel label = new JLabel("出发城市：");
        choosePanel.add(label);
        startTextField = new JTextField();
        choosePanel.add(startTextField);
        startTextField.setColumns(10);

        exchangeButton = new JButton("换");
        exchangeButton.addActionListener(this);
        choosePanel.add(exchangeButton);

        //到达城市输入框
        JLabel label_1 = new JLabel("到达城市：");
        choosePanel.add(label_1);
        arrivelTextField = new JTextField();
        choosePanel.add(arrivelTextField);
        arrivelTextField.setColumns(10);

        //日期选择框
        JPanel datePanel = new JPanel();
        datepick = getDatePicker();
        JLabel label_2 = new JLabel("日期：");
        choosePanel.add(label_2);
        datePanel.add(datepick,BorderLayout.CENTER);
        choosePanel.add(datePanel);
        this.todaydate = datepick.getText();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(datepick.getText());
            this.limitdate = getLimitdate(date);
        }catch (Exception e){
            e.printStackTrace();
        }
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
        tableModel.addColumn("航班编号");
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


        //个人中心
        JPanel personalPanel = new JPanel();
        tabbedPane.addTab("个人中心", null, personalPanel, null);

        JLabel label_3 = new JLabel("个人信息");

        JLabel label_4 = new JLabel("姓名：");
        nameLabel = new JLabel("name");

        JLabel label_5 = new JLabel("电话：");
        telLable = new JLabel("tel");

        button_1 = new JButton("修改个人信息");
        button_2 = new JButton("修改登录密码");
        button_1.addActionListener(this);
        button_2.addActionListener(this);

        JLabel label_7 = new JLabel("用户名：");
        usernameLabel = new JLabel("username");

        JLabel label_8 = new JLabel("性别：");
        sexLabel = new JLabel("sex");

        JScrollPane scrollPane_1 = new JScrollPane();

        JLabel label_6 = new JLabel("订票信息");

        button_3 = new JButton("改签");
        button_4 = new JButton("退票");
        button_3.addActionListener(this);
        button_4.addActionListener(this);

        GroupLayout gl_personalPanel = new GroupLayout(personalPanel);
        gl_personalPanel.setHorizontalGroup(
                gl_personalPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_personalPanel.createSequentialGroup()
                                .addGroup(gl_personalPanel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_personalPanel.createSequentialGroup()
                                                .addGap(164)
                                                .addGroup(gl_personalPanel.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_personalPanel.createSequentialGroup()
                                                                .addGap(12)
                                                                .addGroup(gl_personalPanel.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(label_7)
                                                                        .addComponent(label_4))
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addGroup(gl_personalPanel.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(nameLabel)
                                                                        .addComponent(usernameLabel))
                                                                .addGap(279)
                                                                .addGroup(gl_personalPanel.createParallelGroup(Alignment.LEADING)
                                                                        .addGroup(gl_personalPanel.createSequentialGroup()
                                                                                .addComponent(label_8)
                                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                                .addComponent(sexLabel))
                                                                        .addGroup(gl_personalPanel.createSequentialGroup()
                                                                                .addComponent(label_5)
                                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                                .addComponent(telLable))))
                                                        .addGroup(gl_personalPanel.createSequentialGroup()
                                                                .addGap(94)
                                                                .addComponent(button_1)
                                                                .addGap(59)
                                                                .addComponent(button_2))))
                                        .addGroup(Alignment.TRAILING, gl_personalPanel.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE))
                                        .addGroup(gl_personalPanel.createSequentialGroup()
                                                .addGap(300)
                                                .addComponent(button_3)
                                                .addGap(59)
                                                .addComponent(button_4))
                                        .addGroup(gl_personalPanel.createSequentialGroup()
                                                .addGap(382)
                                                .addGroup(gl_personalPanel.createParallelGroup(Alignment.TRAILING)
                                                        .addComponent(label_6)
                                                        .addComponent(label_3))))
                                .addContainerGap())
        );
        gl_personalPanel.setVerticalGroup(
                gl_personalPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_personalPanel.createSequentialGroup()
                                .addGap(27)
                                .addComponent(label_3)
                                .addGap(7)
                                .addGroup(gl_personalPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(nameLabel)
                                        .addComponent(label_4)
                                        .addComponent(label_8)
                                        .addComponent(sexLabel))
                                .addGap(15)
                                .addGroup(gl_personalPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(usernameLabel)
                                        .addComponent(label_7)
                                        .addComponent(label_5)
                                        .addComponent(telLable))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(gl_personalPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(button_1)
                                        .addComponent(button_2))
                                .addGap(25)
                                .addComponent(label_6)
                                .addGap(18)
                                .addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
                                .addGap(3)
                                .addGroup(gl_personalPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(button_4)
                                        .addComponent(button_3))
                                .addContainerGap())
        );

        table_1 = new JTable();
        tableModel_1 = (DefaultTableModel) table_1.getModel();
        tableModel_1.addColumn("出行日期");
        tableModel_1.addColumn("航空公司");
        tableModel_1.addColumn("航班编号");
        tableModel_1.addColumn("飞机型号");
        tableModel_1.addColumn("起飞机场");
        tableModel_1.addColumn("降落机场");
        tableModel_1.addColumn("起飞时间");
        tableModel_1.addColumn("降落时间");
        tableModel_1.addColumn("单张票价");
        tableModel_1.addColumn("购买数量");

        scrollPane_1.setViewportView(table_1);
        personalPanel.setLayout(gl_personalPanel);

        showpersonalinformation();

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
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

    //返回距离近今天七天后的日期
    public String getLimitdate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//date 换成已经已知的Date对象
        cal.add(Calendar.HOUR_OF_DAY, +168); // 七天后
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(cal.getTime());
    }

    public void showpersonalinformation(){
        try {
            String sqlforuser = "SELECT * from UserInformation WHERE User_ID = ?";
            PreparedStatement preparedStatementforuser = conn.prepareStatement(sqlforuser);
            preparedStatementforuser.setString(1, userid);
            ResultSet res = preparedStatementforuser.executeQuery();

            if(res.next()) {
                String name = res.getString("User_Name");
                String username = res.getString("User_ID");
                int sexid = res.getInt("User_Sex");
                String sex = "";
                switch (sexid) {
                    case 0:
                        sex = "未设置";
                        break;
                    case 1:
                        sex = "男";
                        break;
                    case 2:
                        sex = "女";
                        break;
                    default:
                        ;
                }
                String tel = res.getString("User_Tel");
                nameLabel.setText(name);
                usernameLabel.setText(username);
                sexLabel.setText(sex);
                telLable.setText(tel);
                preparedStatementforuser.close();
            }

            //清除表中原有的项
            for (int index = tableModel_1.getRowCount() - 1; index >= 0; index--) {
                tableModel_1.removeRow(index);
            }
            table_1.repaint();

            String sqlfortickets = "SELECT * from BookTicketsInformation WHERE User_ID = ?";
            PreparedStatement preparedStatementfortickets = conn.prepareStatement(sqlfortickets);
            preparedStatementfortickets.setString(1,userid);
            ResultSet resultSet = preparedStatementfortickets.executeQuery();

            while(resultSet.next()) {

                String traveldate = resultSet.getString("Travel_Date");
                int ticketsnum = resultSet.getInt("Tickets_Num");
                String airlinecode = resultSet.getString("AirlineCode");

                String sqlforplane = "SELECT * from AirlineDate WHERE AirlineCode = ?";
                PreparedStatement preparedStatementforplane = conn.prepareStatement(sqlforplane);
                preparedStatementforplane.setString(1,airlinecode);
                ResultSet result = preparedStatementforplane.executeQuery();

                if(result.next()){
                    String airlinecompany = result.getString("Company");
                    String planemode = result.getString("Mode");
                    String startdrome = result.getString("StartDrome");
                    String arrivedrome = result.getString("ArriveDrome");
                    String starttime = result.getString("StartTime");
                    String arrivetime = result.getString("ArriveTime");
                    int price = result.getInt("AirlinePrice");

                    Vector vector = new Vector();
                    vector.add(traveldate);
                    vector.add(airlinecompany);
                    vector.add(airlinecode);
                    vector.add(planemode);
                    vector.add(startdrome);
                    vector.add(arrivedrome);
                    vector.add(starttime);
                    vector.add(arrivetime);
                    vector.add(price);
                    vector.add(ticketsnum);

                   // System.out.println("订票数：" + ticketsnum);
                    tableModel_1.addRow(vector);

                    table_1.repaint();
                }
                preparedStatementforplane.close();
            }
            preparedStatementfortickets.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showsearchresult(){

        for (int index = tableModel.getRowCount() - 1; index >= 0; index--) {
            tableModel.removeRow(index);
        }
        //清除表中原有的项。
        table.repaint();

        //mark用来标记是否有直达航班
        boolean mark = false;
        String startcity = startTextField.getText().toString();
        String arrivecity = arrivelTextField.getText().toString();

        try {
            String start = "",arrive = "";
            //查找出发城市缩写
            String sqlforstart = "SELECT * from Domestic WHERE Address_cn = ?";
            PreparedStatement preparedStatementforstart = conn.prepareStatement(sqlforstart);
            preparedStatementforstart.setString(1, startcity);
            ResultSet res_1 = preparedStatementforstart.executeQuery();
            if(res_1.next()) {
                start = res_1.getString("Abbreviation");
            }
            preparedStatementforstart.close();
            //查找到达城市缩写
            String sqlforarrive = "SELECT * from Domestic WHERE Address_cn = ?";
            PreparedStatement preparedStatementforarrive = conn.prepareStatement(sqlforarrive);
            preparedStatementforarrive.setString(1, arrivecity);
            ResultSet res_2 = preparedStatementforarrive.executeQuery();
            if(res_2.next()) {
                arrive = res_2.getString("Abbreviation");
            }
            preparedStatementforarrive.close();

            //查找满足起始城市和到达城市的航班
            String sqlforplane = "SELECT * from AirlineDate WHERE startCity = ? AND lastCity = ?";
            PreparedStatement preparedStatementforplane = conn.prepareStatement(sqlforplane);
            preparedStatementforplane.setString(1, start);
            preparedStatementforplane.setString(2, arrive);
            ResultSet res_3 = preparedStatementforplane.executeQuery();

            while(res_3.next()){
                //存在直达航班
                mark = true;
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

            //没有直达
            if(!mark){
                findinterchangeairline(start,arrive);
            }

        }catch (Exception ee){
            ee.printStackTrace();
        }
    }

    //无直达航班时，寻找中转站
    public void findinterchangeairline(String start,String arrive){
        try {
            String sqlforstartcity = "SELECT * from AirlineDate WHERE startCity = ?";
            PreparedStatement preparedStatementforstartcity = conn.prepareStatement(sqlforstartcity);
            preparedStatementforstartcity.setString(1, start);
            ResultSet res_5 = preparedStatementforstartcity.executeQuery();

            String sqlforarrivecity = "SELECT * from AirlineDate WHERE lastCity = ?";
            PreparedStatement preparedStatementforarrivecity = conn.prepareStatement(sqlforarrivecity);
            preparedStatementforarrivecity.setString(1, arrive);
            ResultSet res_6 = preparedStatementforarrivecity.executeQuery();

            ResultSet resultSet = res_6;

            while (res_5.next()) {

                String interchangecity = res_5.getString("lastCity");
                String arrivetime_ = res_5.getString("ArriveTime");

                while (res_6.next()) {
                    String starttime_ = res_6.getString("StartTime");
                    int k = starttime_.compareTo(arrivetime_);
                    //找到中转站且第一趟飞机到达中转站的时间比第二趟飞机从中转站起飞要早，则满足条件
                    if (res_6.getString("startCity").equals(interchangecity) && k > 0) {
                        //显示第一躺航班
                        String company = res_5.getString("Company");
                        String airlinecode = res_5.getString("AirlineCode");
                        String mode = res_5.getString("Mode");
                        String startdrome = res_5.getString("StartDrome");
                        String arrivedrome = res_5.getString("ArriveDrome");
                        String starttime = res_5.getString("StartTime");
                        String arrivetime = res_5.getString("ArriveTime");
                        int price = res_5.getInt("AirlinePrice");
                        int seats = 0;
                        //查找该日期的航班剩余票数
                        String sqlforseats = "SELECT * from SeatsRemain WHERE AirlineCode = ? AND Date = ?";
                        PreparedStatement preparedStatementforseats = conn.prepareStatement(sqlforseats);
                        preparedStatementforseats.setString(1, airlinecode);
                        preparedStatementforseats.setString(2, datepick.getText());
                        ResultSet res_7 = preparedStatementforseats.executeQuery();
                        if(res_7.next()) {
                            seats = res_7.getInt("Seats_Remain");
                        }

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

                        //显示第二趟航班
                        company = res_6.getString("Company");
                        airlinecode = res_6.getString("AirlineCode");
                        mode = res_6.getString("Mode");
                        startdrome = res_6.getString("StartDrome");
                        arrivedrome = res_6.getString("ArriveDrome");
                        starttime = res_6.getString("StartTime");
                        arrivetime = res_6.getString("ArriveTime");
                        price = res_6.getInt("AirlinePrice");
                        seats = 0;
                        //查找该日期的航班剩余票数
                        sqlforseats = "SELECT * from SeatsRemain WHERE AirlineCode = ? AND Date = ?";
                        preparedStatementforseats = conn.prepareStatement(sqlforseats);
                        preparedStatementforseats.setString(1, airlinecode);
                        preparedStatementforseats.setString(2, datepick.getText());
                        ResultSet res_8 = preparedStatementforseats.executeQuery();
                        if(res_8.next()) {
                            seats = res_8.getInt("Seats_Remain");
                        }
                        preparedStatementforseats.close();

                        //将该航班信息显示出来
                        vector = new Vector();
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
                }
                //恢复res_6结果集
                res_6 = resultSet;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void actionPerformed(ActionEvent e){

        //交换出发城市和到达城市
        if(e.getSource() == exchangeButton) {
            String startcity = startTextField.getText().toString();
            String arrivecity = arrivelTextField.getText().toString();
            startTextField.setText(arrivecity);
            arrivelTextField.setText(startcity);
            this.repaint();
        }

        //查找航班
        if(e.getSource() == button){
            String startcity = startTextField.getText();
            String arrivecity = arrivelTextField.getText();

            if(!startcity.equals("")&&!arrivecity.equals("")) {
                int p = todaydate.compareTo(datepick.getText());
                int q = limitdate.compareTo(datepick.getText());
                if (p <= 0 && q >= 0)
                    showsearchresult();
                else {
                    for (int index = tableModel.getRowCount() - 1; index >= 0; index--) {
                        tableModel.removeRow(index);
                    }
                    //清除表中原有的项。
                    table.repaint();
                    if(p > 0)
                        JOptionPane.showMessageDialog(null, "日期无效，请重新选择！");
                    else if(q < 0)
                        JOptionPane.showMessageDialog(null, "只能购买七天内的机票！");
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"请填写完整出发城市和到达城市再重试！");
            }
        }

        //订票
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
                    new OrderTicketsWindow(traveldate, airlinecode, startcity, arrivecity, userid, ticketsremain)
                            .addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosed(WindowEvent e) {
                                showpersonalinformation();
                                showsearchresult();
                             }
                    });
                }
            }
            else
                JOptionPane.showMessageDialog(null,"请先选择航班再进行操作！");
        }

        //修改个人信息
        if(e.getSource() == button_1){
            new RevisePersonalInformation(userid).addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    showpersonalinformation();
                }
            });
        }

        //修改登录密码
        if(e.getSource() == button_2){
            new RevisePasswordWindow(userid).addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Center.super.dispose();
                    new Login();
                }
            });
        }

        //改签
        if(e.getSource()==button_3){
            if(table_1.getSelectedRow() != -1){
                String airlinecode = tableModel_1.getValueAt(table_1.getSelectedRow(), 2).toString();
                int ticketsnum =  Integer.valueOf(tableModel_1.getValueAt(table_1.getSelectedRow(), 9).toString());
                String traveldate = tableModel_1.getValueAt(table_1.getSelectedRow(), 0).toString();

                new AlterTicketsWindow(userid,traveldate,airlinecode,ticketsnum).addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        showpersonalinformation();
                    }
                });
            }
            else
                JOptionPane.showMessageDialog(null,"请先选择航班再进行操作");

        }

        //退票
        if(e.getSource() == button_4){
            if(table_1.getSelectedRow() != -1) {
                String airlinecode = tableModel_1.getValueAt(table_1.getSelectedRow(), 2).toString();
                int ticketsnum =  Integer.valueOf(tableModel_1.getValueAt(table_1.getSelectedRow(), 9).toString());
                String traveldate = tableModel_1.getValueAt(table_1.getSelectedRow(), 0).toString();
                String startdrome = tableModel_1.getValueAt(table_1.getSelectedRow(), 4).toString();
                String arrivedrome = tableModel_1.getValueAt(table_1.getSelectedRow(), 5).toString();

                //实时更新显示的信息
                new ReturnTicketsWindow(traveldate,airlinecode,startdrome,arrivedrome,userid,ticketsnum).
                        addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        showpersonalinformation();
                    }
                });
            }
            else
                JOptionPane.showMessageDialog(null,"请先选择航班再进行操作");
        }

    }
}
