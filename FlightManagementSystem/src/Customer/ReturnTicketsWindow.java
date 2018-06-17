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

public class ReturnTicketsWindow extends JFrame implements ActionListener {

    private JPanel contentPane;
    private String traveldate,airlinecode,userid;
    private JComboBox comboBox;
    private JButton confirmButton,cancelButton;
    private int ticketsnum;

    /**
     * Create the frame.
     */
    public ReturnTicketsWindow(String traveldate,String airlinecode,String startdrome,String arrivedrome,String userid,int ticketsnum) {

        super("退票");

        this.airlinecode = airlinecode;
        this.traveldate = traveldate;
        this.userid = userid;
        this.ticketsnum = ticketsnum;

        setBounds(620, 400, 450, 160);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel messageLabel = new JLabel(" ",JLabel.CENTER);
        String message = "您正在退购"+traveldate+"从"+startdrome+"飞往"+arrivedrome+"的机票";
        messageLabel.setText(message);

        JLabel label = new JLabel("请选择退订数量：");

        comboBox = new JComboBox();
        for(int i = 1;i<=ticketsnum;i++)
            comboBox.addItem(i);

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

            DbConnect dbConnect = new DbConnect();
            Connection conn = dbConnect.connect();
            try {

                int left = ticketsnum - num;
                if(left > 0) {  //如果未全部退票则更新购买票数
                    String sqlforreturn = "UPDATE BookTicketsInformation SET Tickets_Num = ? Where User_ID = ? AND Travel_Date = ? AND AirlineCode = ?";
                    PreparedStatement preparedStatementforreturn = conn.prepareStatement(sqlforreturn);
                    preparedStatementforreturn.setInt(1, left);
                    preparedStatementforreturn.setString(2, userid);
                    preparedStatementforreturn.setString(3, traveldate);
                    preparedStatementforreturn.setString(4, airlinecode);

                    preparedStatementforreturn.execute();
                    preparedStatementforreturn.close();
                }
                else {  //如果全部退票则删除此条记录
                    String sqlforreturn = "DELETE FROM BookTicketsInformation Where User_ID = ? AND Travel_Date = ? AND AirlineCode = ?";
                    PreparedStatement preparedStatementforreturn = conn.prepareStatement(sqlforreturn);
                    preparedStatementforreturn.setString(1, userid);
                    preparedStatementforreturn.setString(2, traveldate);
                    preparedStatementforreturn.setString(3, airlinecode);

                    preparedStatementforreturn.execute();
                    preparedStatementforreturn.close();
                }

                String sqlforseatsremian = "UPDATE SeatsRemain SET Seats_Remain = Seats_Remain + ? WHERE AirlineCode = ? And Date = ?";
                PreparedStatement preparedStatementforseatsremain = conn.prepareStatement(sqlforseatsremian);
                preparedStatementforseatsremain.setInt(1,num);
                preparedStatementforseatsremain.setString(2,airlinecode);
                preparedStatementforseatsremain.setString(3,traveldate);

                preparedStatementforseatsremain.executeUpdate();
                preparedStatementforseatsremain.close();

                JOptionPane.showMessageDialog(null,"退票成功");
                conn.close();
                this.dispose();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        if(e.getSource() == cancelButton){
            int i = JOptionPane.showConfirmDialog(null,"是否要取消退票？");
            if(i == 0) {
                this.dispose();
            }
        }

    }

}
