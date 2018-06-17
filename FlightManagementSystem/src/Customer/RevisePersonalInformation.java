package Customer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class RevisePersonalInformation extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField nameTextField,telTextField,usernameTextField;
    private JPasswordField passwordField;
    JComboBox comboBox;
    private JButton confirmbutton,cancelbutton;
    private String password,userid;

    DbConnect dbConnect = new DbConnect();
    Connection conn = dbConnect.connect();
    /**
     * Create the frame.
     */
    public RevisePersonalInformation(String userid) {
        super("修改个人信息");
        this.userid = userid;
        setBounds(620, 270, 450, 470);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel namelabel = new JLabel("姓名：");

        JLabel sexlabel = new JLabel("性别：");

        JLabel tellabel = new JLabel("电话：");

        JLabel usernamelabel = new JLabel("同户名：");

        JLabel label = new JLabel("请输入密码确认身份：");
        passwordField = new JPasswordField();
        passwordField.setColumns(10);

        confirmbutton = new JButton("提交");
        cancelbutton = new JButton("取消");
        confirmbutton.addActionListener(this);
        cancelbutton.addActionListener(this);

        try {
            String sqlforid = "SELECT * from UserInformation WHERE User_ID=?";
            PreparedStatement preparedStatementforid = conn.prepareStatement(sqlforid);
            preparedStatementforid.setString(1, userid);
            ResultSet res = preparedStatementforid.executeQuery();
            if(res.next()) {
                nameTextField = new JTextField(res.getString("User_Name"));
                nameTextField.setColumns(10);

                comboBox = new JComboBox();
                comboBox.addItem("男");
                comboBox.addItem("女");
                int i = res.getInt("User_Sex");
                comboBox.setSelectedIndex(i-1);

                telTextField = new JTextField(res.getString("User_Tel"));
                telTextField.setColumns(10);

                usernameTextField = new JTextField(res.getString("User_ID"));
                usernameTextField.setColumns(10);

                this.password = res.getString("User_Password");
                preparedStatementforid.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(49)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(confirmbutton)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(usernamelabel, Alignment.TRAILING)
                                                        .addComponent(tellabel, Alignment.TRAILING)
                                                        .addComponent(sexlabel, Alignment.TRAILING)
                                                        .addComponent(namelabel, Alignment.TRAILING)
                                                        .addComponent(label, Alignment.TRAILING))
                                                .addGap(13)))
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(33)
                                                .addComponent(cancelbutton))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(20)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                                        .addComponent(nameTextField)
                                                        .addComponent(telTextField)
                                                        .addComponent(usernameTextField)
                                                        .addComponent(passwordField, Alignment.TRAILING)
                                                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(95, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap(44, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(namelabel)
                                        .addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(sexlabel)
                                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(31)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(tellabel)
                                        .addComponent(telTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(38)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(usernamelabel)
                                        .addComponent(usernameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(49)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label))
                                .addGap(43)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(confirmbutton)
                                        .addComponent(cancelbutton))
                                .addGap(50))
        );
        contentPane.setLayout(gl_contentPane);
        setDefaultCloseOperation(1);
        setVisible(true);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == confirmbutton){
            String name = nameTextField.getText();
            String sex = comboBox.getSelectedItem().toString();
            int sexid = 0;
            switch (sex) {
                case "男":
                    sexid = 1;
                    break;
                case "女":
                    sexid = 2;
                    break;
                default:
                    ;
            }
            String tel = telTextField.getText();
            String username = usernameTextField.getText();
            String inputpassword = new String(passwordField.getPassword());

            if (name.equals("") || sex.equals("") || tel.equals("") || username.equals("") || inputpassword.equals("")) {
                JOptionPane.showMessageDialog(null, "请完善信息再重试");
            } else {
                if (!inputpassword.equals(password)) {
                    JOptionPane.showMessageDialog(null, "请重新确认密码");
                } else {
                    try {
                        String sqlforid = "SELECT * from UserInformation WHERE User_ID=?";
                        PreparedStatement preparedStatementforid = conn.prepareStatement(sqlforid);
                        preparedStatementforid.setString(1,username);
                        ResultSet res = preparedStatementforid.executeQuery();
                        if(res.next() && (!res.getString("User_ID").equals(userid))){
                                JOptionPane.showMessageDialog(null, "改用户名已被注册，请重新输入");
                        }
                        else {

                            String sqlforrevise = "UPDATE UserInformation SET User_Name = ? , User_ID = ? , User_Sex = ? , User_Tel = ? WHERE User_ID = ?";

                            PreparedStatement preparedStatement = conn.prepareStatement(sqlforrevise);
                            preparedStatement.setString(1, name);
                            preparedStatement.setString(2, username);
                            preparedStatement.setInt(3, sexid);
                            preparedStatement.setString(4, tel);
                            preparedStatement.setString(5,userid);

                            preparedStatement.execute();
                            preparedStatementforid.close();
                            preparedStatement.close();
                            conn.close();

                            this.dispose();
                        }

                    } catch (Exception ee) {
                        ee.printStackTrace();

                    }
                }

            }
        }

        if(e.getSource() == cancelbutton){
            int i= JOptionPane.showConfirmDialog(null,"是否放弃本次修改？");
            if(i == 0) {
                this.dispose();
            }
        }

    }
}
