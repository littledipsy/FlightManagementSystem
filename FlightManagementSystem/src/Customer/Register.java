package Customer;

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
    private JComboBox comboBox;
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
        super("用户注册");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(620, 300, 450, 490);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel label;
        label = new JLabel("姓名：");
        textField = new JTextField();
        textField.setColumns(10);

        JLabel label_1;
        label_1 = new JLabel("性别：");
        comboBox = new JComboBox();
        comboBox.addItem("男");
        comboBox.addItem("女");

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
                                .addContainerGap(92, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                                        .addComponent(label)
                                                        .addComponent(label_1)
                                                        .addComponent(label_2)
                                                        .addComponent(label_3)
                                                        .addComponent(label_4)
                                                        .addComponent(label_5))
                                                .addGap(37))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(registerbutton, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                                                .addGap(13)))
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(textField_2, 149, 149, 149)
                                                        .addComponent(textField_3, 149, 149, 149)
                                                        .addComponent(textField_4, 149, 149, 149)
                                                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(textField_1, 149, 149, 149)
                                                        .addComponent(textField, 149, 149, 149)))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(28)
                                                .addComponent(cancel)))
                                .addGap(97))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(80)
                                .addComponent(lblNewLabel)
                                .addContainerGap(80, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(31)
                                .addComponent(lblNewLabel)
                                .addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_1))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_2))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_3))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_4))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_5))
                                .addGap(36)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(registerbutton)
                                        .addComponent(cancel))
                                .addGap(45))
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
        String tel = textField_1.getText();
        String username = textField_2.getText();
        String password = new String(textField_3.getPassword());
        String password_ = new String(textField_4.getPassword());
        if (name.equals("") || sex.equals("") || tel.equals("") ||
                username.equals("") || password.equals("") || password_.equals("")) {
            JOptionPane.showMessageDialog(null, "请完善信息再重试");
        } else {
            if (!password.equals(password_)) {
                JOptionPane.showMessageDialog(null, "请重新确认密码");
            } else {
                try {
                    DbConnect dbConnect = new DbConnect();
                    Connection conn = dbConnect.connect();

                    String sqlforid = "SELECT * from UserInformation WHERE User_ID=?";
                    PreparedStatement preparedStatementforid = conn.prepareStatement(sqlforid);
                    preparedStatementforid.setString(1,username);
                    ResultSet res = preparedStatementforid.executeQuery();
                    if(res.next()){
                        JOptionPane.showMessageDialog(null, "改用户名已被注册，请重新输入");
                    }
                    else {

                        String sqlforregister = "INSERT into UserInformation( User_Name,User_ID,User_Password,User_Sex,User_Tel) values(? , ? , ? , ? , ?)";

                        PreparedStatement preparedStatement = conn.prepareStatement(sqlforregister);
                        preparedStatement.setString(1, name);
                        preparedStatement.setString(2, username);
                        preparedStatement.setString(3, password);
                        preparedStatement.setInt(4, sexid);
                        preparedStatement.setString(5, tel);

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
