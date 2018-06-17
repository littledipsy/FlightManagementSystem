package Manager;

import Customer.DbConnect;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Register extends JFrame implements ActionListener {

    private JPanel contentPane;

    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JPasswordField textField_3;
    private JPasswordField textField_4;
    private JButton registerbutton, cancel;
    private JLabel lblNewLabel;

    /**
     * Create the frame.
     */
    public Register() {
        super("管理员注册");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(620, 300, 450, 480);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel label;
        label = new JLabel("姓名：");
        textField = new JTextField();
        textField.setColumns(10);

        JLabel label_2 = new JLabel("电话：");
        textField_1 = new JTextField();
        textField_1.setColumns(10);

        JLabel label_3 = new JLabel("用户名：");
        textField_2 = new JTextField();
        textField_2.setColumns(10);

        JLabel label_4 = new JLabel("密码：");
        textField_3 = new JPasswordField();
        textField_3.setColumns(10);

        JLabel label_5 = new JLabel("确认密码：");
        textField_4 = new JPasswordField();
        textField_4.setColumns(10);

        registerbutton = new JButton("注册");
        cancel = new JButton("取消");
        registerbutton.addActionListener(this);
        cancel.addActionListener(this);

        lblNewLabel = new JLabel("欢迎注册，请完整填写以下信息");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap(83, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                                .addComponent(lblNewLabel)
                                                .addGap(77))
                                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                                        .addComponent(label_5)
                                                        .addComponent(label)
                                                        .addComponent(label_2)
                                                        .addComponent(label_3)
                                                        .addComponent(label_4))
                                                .addGap(37)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(textField, 149, 149, 149)
                                                        .addComponent(textField_1, 149, 149, 149)
                                                        .addComponent(textField_2, 149, 149, 149)
                                                        .addComponent(textField_3, 149, 149, 149)
                                                        .addComponent(textField_4, 149, 149, 149))
                                                .addGap(97))
                                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                                .addComponent(registerbutton, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                                                .addGap(47)
                                                .addComponent(cancel)
                                                .addGap(139))))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(27)
                                .addComponent(lblNewLabel)
                                .addGap(30)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label)
                                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(26)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_2)
                                        .addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(27)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_3)
                                        .addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(28)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_4)
                                        .addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(34)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_5)
                                        .addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(cancel)
                                        .addComponent(registerbutton))
                                .addGap(41))
        );
        contentPane.setLayout(gl_contentPane);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerbutton) {
            if(register()) {
                JOptionPane.showMessageDialog(null, "注册成功,请登录");
                this.dispose();
                new Login();
            }
        }
        if (e.getSource() == cancel) {
            this.dispose();
        }
    }

    public boolean register() {
        String name = textField.getText();

        String tel = textField_1.getText();
        String managername = textField_2.getText();
        String password = new String(textField_3.getPassword());
        String password_ = new String(textField_4.getPassword());
        if (name.equals("") || tel.equals("") || managername.equals("") || password.equals("") || password_.equals("")) {
            JOptionPane.showMessageDialog(null, "请完善信息再重试");
        } else {
            if (!password.equals(password_)) {
                JOptionPane.showMessageDialog(null, "请重新确认密码");
            } else {
                try {
                    DbConnect dbConnect = new DbConnect();
                    Connection conn = dbConnect.connect();

                    String sqlforid = "SELECT * from ManagerInformation WHERE Manager_ID = ?";
                    PreparedStatement preparedStatementforid = conn.prepareStatement(sqlforid);
                    preparedStatementforid.setString(1,managername);
                    ResultSet res = preparedStatementforid.executeQuery();
                    if(res.next()){
                        JOptionPane.showMessageDialog(null, "该管理员账号已被注册，请重新输入");
                    }
                    else {

                        String sqlforregister = "insert into ManagerInformation( Manager_Name,Manager_ID,Manager_Password,Manager_Tel) values( ? , ? , ? , ?)";

                        PreparedStatement preparedStatement = conn.prepareStatement(sqlforregister);
                        preparedStatement.setString(1, name);
                        preparedStatement.setString(2, managername);
                        preparedStatement.setString(3, password);
                        preparedStatement.setString(4, tel);

                        preparedStatement.execute();
                        preparedStatementforid.close();
                        preparedStatement.close();
                        conn.close();
                        return true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

        }
        return false;
    }
}
