package Customer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class OrderTicketsWindow extends JFrame implements ActionListener {

    private JPanel contentPane;
    private String traveldate,formertraveldate,airlinecode,userid;
    private JComboBox comboBox;
    private JButton confirmButton,cancelButton;
    private int ticketsremian,ticketsnum,mark;

    DbConnect dbConnect = new DbConnect();
    Connection conn = dbConnect.connect();
    /**
     * Create the frame.
     */
    public OrderTicketsWindow(String traveldate,String formertraveldate,String airlinecode,String startcity,String arrivecity,String userid,int ticketsremain,int ticketsnum,int mark) {

        super("确认订票");

        this.airlinecode = airlinecode;
        this.traveldate = traveldate;
        this.formertraveldate = formertraveldate;
        this.userid = userid;
        this.ticketsremian = ticketsremain;
        this.ticketsnum = ticketsnum;

        setBounds(620, 400, 450, 160);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel messageLabel,label;

        //mark用来标记该窗口是用来改签还是订票，mark=0用来改签，mark=1用来订票
        this.mark = mark;
        if(mark ==1) {
            messageLabel = new JLabel(" ", JLabel.CENTER);
            String message = "您正在订购" + traveldate + "从" + startcity + "飞往" + arrivecity + "的机票";
            messageLabel.setText(message);
            label = new JLabel("请选择订购数量：");

            comboBox = new JComboBox();
            comboBox.addItem("1");
            comboBox.addItem("2");
            comboBox.addItem("3");
        }
        else {
            messageLabel = new JLabel(" ", JLabel.CENTER);
            String message = "您正在将" + formertraveldate + "从" + startcity + "飞往" + arrivecity + "的机票改签为"+traveldate;
            messageLabel.setText(message);
            label = new JLabel("请选择改签数量：");

            comboBox = new JComboBox();
            for(int i = 1;i<=ticketsnum;i++){
                comboBox.addItem(i);
            }
        }

        confirmButton = new JButton("确定");
        confirmButton.addActionListener(this);
        cancelButton = new JButton("取消");
        cancelButton.addActionListener(this);

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 425, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(61)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                .addGap(62)
                                                                .addComponent(confirmButton)
                                                                .addGap(18)
                                                                .addComponent(cancelButton))
                                                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                                                .addComponent(label)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(19)
                                .addComponent(messageLabel)
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(cancelButton)
                                        .addComponent(confirmButton))
                                .addContainerGap(11, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
        setDefaultCloseOperation(1);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == confirmButton) {
            int num = Integer.valueOf(comboBox.getSelectedItem().toString());

            if(num > ticketsremian)
                JOptionPane.showMessageDialog(null,"余票不足！");
            else {

                try {
                    String sqlforuser = "Select * From BookTicketsInformation Where User_ID = ? AND Travel_Date = ? AND AirlineCode = ?";
                    PreparedStatement preparedStatementforuser = conn.prepareStatement(sqlforuser);
                    preparedStatementforuser.setString(1,userid);
                    preparedStatementforuser.setString(2,traveldate);
                    preparedStatementforuser.setString(3,airlinecode);
                    ResultSet resultSet = preparedStatementforuser.executeQuery();

                    if(resultSet.next()){ //如果之前已经买过此趟航班的票，则只要更新购买数量
                        int total = num +resultSet.getInt("Tickets_Num");
                        String sqlfororder = "UPDATE BookTicketsInformation SET Tickets_Num = ? Where User_ID = ? AND Travel_Date = ? AND AirlineCode = ?";
                        PreparedStatement preparedStatementfororder = conn.prepareStatement(sqlfororder);
                        preparedStatementfororder.setInt(1, total);
                        preparedStatementfororder.setString(2, userid);
                        preparedStatementfororder.setString(3, traveldate);
                        preparedStatementfororder.setString(4, airlinecode);

                        preparedStatementfororder.execute();
                        preparedStatementfororder.close();

                    }else {  //未买过创建一条新记录
                        String sqlfororder = "INSERT INTO BookTicketsInformation(User_ID,Travel_Date,Tickets_Num,AirlineCode) VALUES(? , ? , ? , ?)";
                        PreparedStatement preparedStatementfororder = conn.prepareStatement(sqlfororder);
                        preparedStatementfororder.setString(1, userid);
                        preparedStatementfororder.setString(2, traveldate);
                        preparedStatementfororder.setInt(3, num);
                        preparedStatementfororder.setString(4, airlinecode);

                        preparedStatementfororder.execute();
                        preparedStatementfororder.close();
                    }
                    preparedStatementforuser.close();

                    int seatsremian = ticketsremian - num;
                    //System.out.println("剩余："+seatsremian);

                    String sqlforseatsremian = "UPDATE SeatsRemain SET Seats_Remain = ? WHERE AirlineCode = ? And Date = ?";
                    PreparedStatement preparedStatementforseatsremain = conn.prepareStatement(sqlforseatsremian);
                    preparedStatementforseatsremain.setInt(1,seatsremian);
                    preparedStatementforseatsremain.setString(2,airlinecode);
                    preparedStatementforseatsremain.setString(3,traveldate);

                    preparedStatementforseatsremain.executeUpdate();
                    preparedStatementforseatsremain.close();

                    if(mark == 1) {
                        JOptionPane.showMessageDialog(null, "订票成功！");
                        this.dispose();
                    }

                } catch (Exception ee) {
                    ee.printStackTrace();
                }

            }

            if(mark != 1){
                try {
                    int left = ticketsnum - num;
                    if (left > 0) {  //如果未全部改签则更新购买票数
                        String sqlforreturn = "UPDATE BookTicketsInformation SET Tickets_Num = ? Where User_ID = ? AND Travel_Date = ? AND AirlineCode = ?";
                        PreparedStatement preparedStatementforreturn = conn.prepareStatement(sqlforreturn);
                        preparedStatementforreturn.setInt(1, left);
                        preparedStatementforreturn.setString(2, userid);
                        preparedStatementforreturn.setString(3, formertraveldate);
                        preparedStatementforreturn.setString(4, airlinecode);

                        preparedStatementforreturn.execute();
                        preparedStatementforreturn.close();
                    } else {  //如果全部改签则删除此条记录
                        String sqlforreturn = "DELETE FROM BookTicketsInformation Where User_ID = ? AND Travel_Date = ? AND AirlineCode = ?";
                        PreparedStatement preparedStatementforreturn = conn.prepareStatement(sqlforreturn);
                        preparedStatementforreturn.setString(1, userid);
                        preparedStatementforreturn.setString(2, formertraveldate);
                        preparedStatementforreturn.setString(3, airlinecode);

                        preparedStatementforreturn.execute();
                        preparedStatementforreturn.close();
                    }

                    String sqlforseatsremian = "UPDATE SeatsRemain SET Seats_Remain = Seats_Remain + ? WHERE AirlineCode = ? And Date = ?";
                    PreparedStatement preparedStatementforseatsremain = conn.prepareStatement(sqlforseatsremian);
                    preparedStatementforseatsremain.setInt(1, num);
                    preparedStatementforseatsremain.setString(2, airlinecode);
                    preparedStatementforseatsremain.setString(3, formertraveldate);

                    preparedStatementforseatsremain.executeUpdate();
                    preparedStatementforseatsremain.close();

                    JOptionPane.showMessageDialog(null, "改签成功！");
                    this.dispose();
                }catch (Exception ee){
                    ee.printStackTrace();
                }

            }

        }
        if(e.getSource() == cancelButton){
            int i = JOptionPane.showConfirmDialog(null,"是否要取消操作？");
            if(i == 0) {
                this.dispose();
            }
        }

    }

}
