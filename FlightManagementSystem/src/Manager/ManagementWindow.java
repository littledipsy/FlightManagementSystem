package Manager;

import Customer.DbConnect;
import java.awt.*;
import java.awt.event.ActionEvent;
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
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.eltima.components.ui.DatePicker;

public class ManagementWindow extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField airlineCodeTextField;
    private JButton reviseButton,deleteButton,button,submitButton,cancelButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField airlineCompanyTextField,codeTextField;
    private JTextField startCityTextField,arriveCityTextField;
    private JTextField startDromeTextField,arriveDromeTextField;
    private JTextField startTimeTextField,arriveTimeTextField;
    private JTextField airlineModeTextField,ticketPriceTextField;
    private JTextField seatsNumberTextField;
    private String date;
    final DatePicker datepick;


    //建立数据库的链接
    DbConnect dbConnect = new DbConnect();
    Connection conn = dbConnect.connect();

    /**
     * Create the frame.
     */
    public ManagementWindow() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 200, 850, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        //获取今天日期
        datepick = getDatePicker();
        this.date = datepick.getText();

        //查询中心
        JPanel searchPanel = new JPanel();
        tabbedPane.addTab("查询航班", null, searchPanel, null);
        searchPanel.setLayout(new BorderLayout(0, 0));

        JPanel choosePanel = new JPanel();
        choosePanel.setBackground(new Color(238, 238, 238));
        searchPanel.add(choosePanel, BorderLayout.NORTH);

        //航班编号输入框
        JLabel label = new JLabel("请输入航班编号：");
        choosePanel.add(label);
        airlineCodeTextField = new JTextField();
        choosePanel.add(airlineCodeTextField);
        airlineCodeTextField.setColumns(10);

        //查询按钮
        button = new JButton("查询");
        button.addActionListener(this);
        choosePanel.add(button);

        //查询结果
        JScrollPane scrollPane = new JScrollPane();
        searchPanel.add(scrollPane, BorderLayout.CENTER);

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
        tableModel.addColumn("机舱座位");
        scrollPane.setViewportView(table);

        //操作
        JPanel operatePanel = new JPanel();
        searchPanel.add(operatePanel, BorderLayout.SOUTH);
        reviseButton = new JButton("修改航班");
        reviseButton.addActionListener(this);
        operatePanel.add(reviseButton);
        deleteButton = new JButton("删除航班");
        deleteButton.addActionListener(this);
        operatePanel.add(deleteButton);


        //增加航班
        JPanel addPanel = new JPanel();
        tabbedPane.addTab("增加航班", null, addPanel, null);

        JLabel lblNewLabel = new JLabel("航空公司：");

        airlineCompanyTextField = new JTextField();
        airlineCompanyTextField.setColumns(10);

        JLabel label_1 = new JLabel("航班号：");

        codeTextField = new JTextField();
        codeTextField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("起飞城市：");

        startCityTextField = new JTextField();
        startCityTextField.setColumns(10);

        JLabel label_2 = new JLabel("降落城市：");

        arriveCityTextField = new JTextField();
        arriveCityTextField.setColumns(10);

        JLabel label_3 = new JLabel("起飞机场：");

        startDromeTextField = new JTextField();
        startDromeTextField.setColumns(10);

        JLabel label_4 = new JLabel("降落机场：");

        arriveDromeTextField = new JTextField();
        arriveDromeTextField.setColumns(10);

        JLabel label_5 = new JLabel("起飞时间：");

        startTimeTextField = new JTextField();
        startTimeTextField.setColumns(10);

        JLabel label_6 = new JLabel("降落时间：");

        arriveTimeTextField = new JTextField();
        arriveTimeTextField.setColumns(10);

        JLabel label_7 = new JLabel("飞机型号：");

        airlineModeTextField = new JTextField();
        airlineModeTextField.setColumns(10);

        JLabel label_8 = new JLabel("机票价格：");

        ticketPriceTextField = new JTextField();
        ticketPriceTextField.setColumns(10);

        JLabel label_9 = new JLabel("机舱座位：");

        seatsNumberTextField = new JTextField();
        seatsNumberTextField.setColumns(10);

        submitButton = new JButton("提交");
        submitButton.addActionListener(this);
        cancelButton = new JButton("取消");
        cancelButton.addActionListener(this);

        JLabel label_10 = new JLabel("请填写完整以下信息");
        label_10.setFont(new Font("Lucida Grande", Font.PLAIN, 25));

        GroupLayout gl_addPanel = new GroupLayout(addPanel);
        gl_addPanel.setHorizontalGroup(
                gl_addPanel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, gl_addPanel.createSequentialGroup()
                                .addGap(126)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                .addComponent(label_9)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(seatsNumberTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                .addGroup(gl_addPanel.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addGroup(gl_addPanel.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(lblNewLabel)
                                                                        .addComponent(lblNewLabel_1))
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addGroup(gl_addPanel.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(airlineCompanyTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(startCityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(label_3)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(startDromeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(label_5)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(startTimeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(label_7)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(airlineModeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(135)
                                                .addGroup(gl_addPanel.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(label_8)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(ticketPriceTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(label_6)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(arriveTimeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_addPanel.createParallelGroup(Alignment.LEADING)
                                                                .addGroup(gl_addPanel.createSequentialGroup()
                                                                        .addComponent(label_2)
                                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                                        .addComponent(arriveCityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(gl_addPanel.createSequentialGroup()
                                                                        .addComponent(label_1)
                                                                        .addGap(18)
                                                                        .addComponent(codeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(label_4)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(arriveDromeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(156, Short.MAX_VALUE))
                        .addGroup(Alignment.LEADING, gl_addPanel.createSequentialGroup()
                                .addGap(293)
                                .addComponent(submitButton)
                                .addGap(61)
                                .addComponent(cancelButton))
                        .addGroup(Alignment.LEADING, gl_addPanel.createSequentialGroup()
                                .addGap(269)
                                .addComponent(label_10))
        );
        gl_addPanel.setVerticalGroup(
                gl_addPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_addPanel.createSequentialGroup()
                                .addGap(14)
                                .addComponent(label_10)
                                .addGap(18)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel)
                                        .addComponent(airlineCompanyTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_1)
                                        .addComponent(codeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(39)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(startCityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_2)
                                        .addComponent(arriveCityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(46)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_3)
                                        .addComponent(startDromeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_4)
                                        .addComponent(arriveDromeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(45)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_5)
                                        .addComponent(startTimeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_6)
                                        .addComponent(arriveTimeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(40)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_7)
                                        .addComponent(airlineModeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_8)
                                        .addComponent(ticketPriceTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(41)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_9)
                                        .addComponent(seatsNumberTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(cancelButton)
                                        .addComponent(submitButton))
                                .addGap(30))
        );
        addPanel.setLayout(gl_addPanel);
        setResizable(false);
        setVisible(true);
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

    //返回一天后的日期
    public String nextday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//date 换成已经已知的Date对象
        cal.add(Calendar.HOUR_OF_DAY, +24); // 一天后
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(cal.getTime());
    }

    //根据航班编号查找航班
    public void showsearchresult(){

        for (int index = tableModel.getRowCount() - 1; index >= 0; index--) {
            tableModel.removeRow(index);
        }
        //清除表中原有的项。
        table.repaint();

        String airlinecode = airlineCodeTextField.getText();

        try {
            //查找该航班
            String sqlforplane = "SELECT * from AirlineDate WHERE AirlineCode = ?";
            PreparedStatement preparedStatementforplane = conn.prepareStatement(sqlforplane);
            preparedStatementforplane.setString(1, airlinecode);
            ResultSet res = preparedStatementforplane.executeQuery();

            while(res.next()){
                String company = res.getString("Company");
                String mode = res.getString("Mode");
                String startdrome = res.getString("StartDrome");
                String arrivedrome = res.getString("ArriveDrome");
                String starttime = res.getString("StartTime");
                String arrivetime = res.getString("ArriveTime");
                int price = res.getInt("AirlinePrice");
                int seats = res.getInt("Seats");
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

    //删除航班
    public void deleteairline(String airlinecode){

        try {
            String sqlfordelete = "DELETE FROM AirlineDate WHERE AirlineCode = ?";
            PreparedStatement preparedStatementfordelete = conn.prepareStatement(sqlfordelete);
            preparedStatementfordelete.setString(1, airlinecode);
            preparedStatementfordelete.execute();
            JOptionPane.showMessageDialog(null,"删除成功！");
            preparedStatementfordelete.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //添加航班
    public void addairline(){

        String airlinecompany = airlineCompanyTextField.getText();
        String airlinecode = codeTextField.getText();
        String startcity = startCityTextField.getText();
        String arrivecity = arriveCityTextField.getText();
        String startdrome = startDromeTextField.getText();
        String arrivedrome = arriveDromeTextField.getText();
        String starttime = startTimeTextField.getText();
        String arrivetime = arriveTimeTextField.getText();
        String airlinemode = airlineModeTextField.getText();
        String airlineprice = ticketPriceTextField.getText();
        String seatsnum = seatsNumberTextField.getText();

        if(airlinecompany.equals("")||airlinecode.equals("")||startcity.equals("")||arrivecity.equals("")||
                starttime.equals("")||arrivetime.equals("")||startdrome.equals("")||arrivedrome.equals("")||
                airlinemode.equals("")||airlineprice.equals("")||seatsnum.equals("")){
            JOptionPane.showMessageDialog(null,"请将信息填写完整再重试！");
        }
        else {
            int price = Integer.valueOf(airlineprice);
            int seats = Integer.valueOf(seatsnum);
            try {
                //判断该航班号是否已存在
                String sqlforplane = "SELECT * FROM AirlineDate WHERE AirlineCode = ?";
                PreparedStatement preparedStatementforplane = conn.prepareStatement(sqlforplane);
                preparedStatementforplane.setString(1, airlinecode);
                ResultSet res = preparedStatementforplane.executeQuery();
                if (res.next()) {
                    System.out.println(res.getString("AirlineCode"));
                    JOptionPane.showMessageDialog(null, "该航班号已存在，请重新输入！");

                }

                else{
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

                    //将该航班写入数据库
                    String sqlforadd = "INSERT INTO AirlineDate " +
                            "(Company,AirlineCode,startCity,lastcity,StartDrome,ArriveDrome,StartTime,ArriveTime,Mode,AirlinePrice,Seats )" +
                            " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement preparedStatementforadd = conn.prepareStatement(sqlforadd);
                    preparedStatementforadd.setString(1, airlinecompany);
                    preparedStatementforadd.setString(2, airlinecode);
                    preparedStatementforadd.setString(3, start);
                    preparedStatementforadd.setString(4, arrive);
                    preparedStatementforadd.setString(5, startdrome);
                    preparedStatementforadd.setString(6, arrivedrome);
                    preparedStatementforadd.setString(7, starttime);
                    preparedStatementforadd.setString(8, arrivetime);
                    preparedStatementforadd.setString(9, airlinemode);
                    preparedStatementforadd.setInt(10, price);
                    preparedStatementforadd.setInt(11, seats);
                    preparedStatementforadd.execute();
                    preparedStatementforadd.close();

                    //将航班七天的票务信息写入数据库
                    for(int i = 0; i < 7; i++) {
                        System.out.println(date);

                        String sqlforaddtickets = "INSERT INTO SeatsRemain (AirlineCode,Date,Seats_Remain ) VALUES (?,?,?)";
                        PreparedStatement preparedStatementforaddtickets = conn.prepareStatement(sqlforaddtickets);
                        preparedStatementforaddtickets.setString(1, airlinecode);
                        preparedStatementforaddtickets.setString(2, date);
                        preparedStatementforaddtickets.setInt(3, seats);
                        preparedStatementforaddtickets.execute();
                        preparedStatementforaddtickets.close();

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = format.parse(this.date);
                        this.date = nextday(date);
                    }

                    JOptionPane.showMessageDialog(null,"增加成功！");
                }
                preparedStatementforplane.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void actionPerformed(ActionEvent e) {
        //查找
        if(e.getSource() == button){
            showsearchresult();
        }
        //修改航班信息
        if(e.getSource() == reviseButton){
            if(table.getSelectedRow() != -1){
                String airlinecode = tableModel.getValueAt(table.getSelectedRow(),1).toString();
                new ReviseAirlineWindow(airlinecode).addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        showsearchresult();
                    }
                });
            }

        }
        //删除航班
        if(e.getSource() == deleteButton) {
            if (table.getSelectedRow() != -1) {
                String airlinecode = tableModel.getValueAt(table.getSelectedRow(), 1).toString();

                if (JOptionPane.showConfirmDialog(null, "是否删除该航班信息？") == 0) {
                    deleteairline(airlinecode);
                    showsearchresult();
                }
            }
            else
                JOptionPane.showMessageDialog(null,"请先选择航班再进行操作！");
        }
        //增加航班
        if(e.getSource() == submitButton){
            //将航班写入数据库
            addairline();
            //将航班票务信息写入数据库

        }
        //取消增加
        if(e.getSource() == cancelButton){
            if(JOptionPane.showConfirmDialog(null, "是否取消此次编辑？") == 0) {
                airlineCompanyTextField.setText("");
                codeTextField.setText("");
                startCityTextField.setText("");
                arriveCityTextField.setText("");
                startDromeTextField.setText("");
                arriveDromeTextField.setText("");
                startTimeTextField.setText("");
                arriveTimeTextField.setText("");
                airlineModeTextField.setText("");
                ticketPriceTextField.setText("");
                seatsNumberTextField.setText("");
            }
        }
    }
}
