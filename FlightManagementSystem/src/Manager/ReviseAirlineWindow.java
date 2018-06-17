package Manager;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import Customer.DbConnect;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ReviseAirlineWindow extends JFrame implements ActionListener{

    private JPanel contentPane;
    private JButton confirmButton,cancelButton;
    private JTextField airlineCompanyTextField,codeTextField;
    private JTextField startCityTextField,arriveCityTextField;
    private JTextField startDromeTextField,arriveDromeTextField;
    private JTextField startTimeTextField,arriveTimeTextField;
    private JTextField airlineModeTextField,ticketPriceTextField;
    private JTextField seatsNumberTextField;
    private String formerairlinecode;

    //建立数据库的链接
    DbConnect dbConnect = new DbConnect();
    Connection conn = dbConnect.connect();

    /**
     * Create the frame.
     */
    public ReviseAirlineWindow(String airlinecode) {

        setBounds(400, 200, 850, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

        JPanel addPanel = new JPanel();
        tabbedPane.addTab("修改信息", null, addPanel, null);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

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

        confirmButton = new JButton("提交");
        cancelButton = new JButton("取消");
        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);

        this.formerairlinecode = airlinecode;
        showairlineinformation(airlinecode);

        GroupLayout gl_addPanel = new GroupLayout(addPanel);
        gl_addPanel.setHorizontalGroup(
                gl_addPanel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_addPanel.createSequentialGroup()
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                .addGap(126)
                                                .addGroup(gl_addPanel.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(lblNewLabel)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(airlineCompanyTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(lblNewLabel_1)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(startCityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(label_3)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(startDromeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(label_5)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(startTimeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(label_7)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(airlineModeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(label_9)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(seatsNumberTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(135)
                                                .addGroup(gl_addPanel.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(label_8)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(ticketPriceTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(label_6)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(arriveTimeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(label_4)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(arriveDromeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(label_2)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(arriveCityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                                .addComponent(label_1)
                                                                .addGap(18)
                                                                .addComponent(codeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(gl_addPanel.createSequentialGroup()
                                                .addGap(292)
                                                .addComponent(confirmButton)
                                                .addGap(62)
                                                .addComponent(cancelButton)))
                                .addContainerGap(156, Short.MAX_VALUE))
        );
        gl_addPanel.setVerticalGroup(
                gl_addPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_addPanel.createSequentialGroup()
                                .addGap(41)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel)
                                        .addComponent(airlineCompanyTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_1)
                                        .addComponent(codeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(43)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(startCityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_2)
                                        .addComponent(arriveCityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(50)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(startDromeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_4)
                                        .addComponent(arriveDromeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_3))
                                .addGap(50)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_5)
                                        .addComponent(startTimeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_6)
                                        .addComponent(arriveTimeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(50)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_7)
                                        .addComponent(airlineModeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_8)
                                        .addComponent(ticketPriceTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(44)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_9)
                                        .addComponent(seatsNumberTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(34)
                                .addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(confirmButton)
                                        .addComponent(cancelButton))
                                .addGap(20))
        );
        addPanel.setLayout(gl_addPanel);
        setDefaultCloseOperation(1);
        setVisible(true);
        setResizable(false);

    }

    public void showairlineinformation(String airlinecode){
        try{
            String sqlforplane = "SELECT * from AirlineDate WHERE AirlineCode = ?";
            PreparedStatement preparedStatementforplane = conn.prepareStatement(sqlforplane);
            preparedStatementforplane.setString(1, airlinecode);
            ResultSet res = preparedStatementforplane.executeQuery();
            if(res.next()){

                String company = res.getString("Company");
                String mode = res.getString("Mode");
                String startdrome = res.getString("StartDrome");
                String arrivedrome = res.getString("ArriveDrome");
                String startcity = res.getString("startCity");
                String arrivecity = res.getString("lastCity");
                String starttime = res.getString("StartTime");
                String arrivetime = res.getString("ArriveTime");
                int price = res.getInt("AirlinePrice");
                int seats = res.getInt("Seats");

                String start = "",arrive = "";
                //查找出发城市名字
                String sqlforstart = "SELECT * from Domestic WHERE Abbreviation = ?";
                PreparedStatement preparedStatementforstart = conn.prepareStatement(sqlforstart);
                preparedStatementforstart.setString(1, startcity);
                ResultSet res_1 = preparedStatementforstart.executeQuery();
                if(res_1.next()) {
                    start = res_1.getString("Address_cn");
                }
                preparedStatementforstart.close();
                //查找到达城市名字
                String sqlforarrive = "SELECT * from Domestic WHERE Abbreviation = ?";
                PreparedStatement preparedStatementforarrive = conn.prepareStatement(sqlforarrive);
                preparedStatementforarrive.setString(1, arrivecity);
                ResultSet res_2 = preparedStatementforarrive.executeQuery();
                if(res_2.next()) {
                    arrive = res_2.getString("Address_cn");
                }
                preparedStatementforarrive.close();

                airlineCompanyTextField.setText(company);
                codeTextField.setText(airlinecode);
                airlineModeTextField.setText(mode);
                startDromeTextField.setText(startdrome);
                arriveDromeTextField.setText(arrivedrome);
                startCityTextField.setText(start);
                arriveCityTextField.setText(arrive);
                startTimeTextField.setText(starttime);
                arriveTimeTextField.setText(arrivetime);
                ticketPriceTextField.setText(Integer.toString(price));
                seatsNumberTextField.setText(Integer.toString(seats));

            }
            preparedStatementforplane.close();

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void reviseairlineinformation(){

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
                if (res.next() && !airlinecode.equals(formerairlinecode)) {
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
                    String sqlforrevise = "UPDATE AirlineDate SET Company = ?,AirlineCode = ?,startCity = ?,lastcity = ?," +
                            "StartDrome = ?,ArriveDrome = ?,StartTime = ?,ArriveTime = ?,Mode = ?,AirlinePrice = ?,Seats = ? " +
                            "WHERE AirlineCode = ?";
                    PreparedStatement preparedStatementforrevise = conn.prepareStatement(sqlforrevise);
                    preparedStatementforrevise.setString(1, airlinecompany);
                    preparedStatementforrevise.setString(2, airlinecode);
                    preparedStatementforrevise.setString(3, start);
                    preparedStatementforrevise.setString(4, arrive);
                    preparedStatementforrevise.setString(5, startdrome);
                    preparedStatementforrevise.setString(6, arrivedrome);
                    preparedStatementforrevise.setString(7, starttime);
                    preparedStatementforrevise.setString(8, arrivetime);
                    preparedStatementforrevise.setString(9, airlinemode);
                    preparedStatementforrevise.setInt(10, price);
                    preparedStatementforrevise.setInt(11, seats);
                    preparedStatementforrevise.setString(12,formerairlinecode);
                    preparedStatementforrevise.executeUpdate();

                    JOptionPane.showMessageDialog(null,"修改成功！");
                    this.dispose();

                    preparedStatementforrevise.close();
                }
                preparedStatementforplane.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == confirmButton){
            reviseairlineinformation();
        }
        if(e.getSource() == cancelButton){

        }


    }
}
