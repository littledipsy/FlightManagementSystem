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

public class RevisePasswordWindow extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JPasswordField formerPasswordField;
    private JPasswordField newPasswordField_1;
    private JPasswordField newPasswordField_2;
    private JButton confirmButton, cancelButton;
    private String userid;

    DbConnect dbConnect = new DbConnect();
    Connection conn = dbConnect.connect();

    /**
     * Create the frame.
     */
    public RevisePasswordWindow(String userid) {
        super("修改密码");
        this.userid = userid;
        setBounds(620, 330, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel label = new JLabel("请输入原密码：");

        formerPasswordField = new JPasswordField();

        JLabel label_1 = new JLabel("请输入新密码：");

        newPasswordField_1 = new JPasswordField();

        JLabel label_2 = new JLabel("请确认密码：");

        newPasswordField_2 = new JPasswordField();

        confirmButton = new JButton("提交");
        cancelButton = new JButton("取消");
        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap(62, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(label_1)
                                        .addComponent(label)
                                        .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                .addComponent(confirmButton)
                                                .addComponent(label_2)))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                                        .addComponent(newPasswordField_2, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                                                        .addComponent(newPasswordField_1)
                                                        .addComponent(formerPasswordField)))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(39)
                                                .addComponent(cancelButton)))
                                .addContainerGap(55, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(41)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label)
                                        .addComponent(formerPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(28)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_1)
                                        .addComponent(newPasswordField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(28)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_2)
                                        .addComponent(newPasswordField_2, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(cancelButton)
                                        .addComponent(confirmButton))
                                .addGap(29))
        );
        contentPane.setLayout(gl_contentPane);
        setDefaultCloseOperation(1);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == confirmButton){
            String formerpassword = new String(formerPasswordField.getPassword());
            String newpassword_1 = new String(newPasswordField_1.getPassword());
            String newpassword_2 = new String(newPasswordField_2.getPassword());

            try{
                String sqlforid = "SELECT * from UserInformation WHERE User_ID=?";
                PreparedStatement preparedStatementforid = conn.prepareStatement(sqlforid);
                preparedStatementforid.setString(1,userid);
                ResultSet res = preparedStatementforid.executeQuery();

                if(res.next()){
                    String password = res.getString("User_Password");
                    System.out.println("原密码："+password);
                    if(formerpassword.equals(password)){
                        if(newpassword_1.equals(newpassword_2)){
                            String sqlforrevise = "UPDATE UserInformation SET User_Password = ?  WHERE User_ID = ?";

                            PreparedStatement preparedStatement = conn.prepareStatement(sqlforrevise);
                            preparedStatement.setString(1, newpassword_1);
                            preparedStatement.setString(2, userid);

                            preparedStatement.execute();
                            preparedStatementforid.close();
                            preparedStatement.close();
                            conn.close();

                            JOptionPane.showMessageDialog(null,"修改成功，请重新登录！");
                            this.dispose();
                        }
                        else
                            JOptionPane.showMessageDialog(null,"密码不匹配，请重试！");
                    }
                    else
                        JOptionPane.showMessageDialog(null,"密码输入错误，请重试！");
                }

            }catch(Exception ee){
                ee.printStackTrace();
            }
        }
        if(e.getSource() == cancelButton){
            int i= JOptionPane.showConfirmDialog(null,"是否放弃本次修改？");
            if(i == 0) {
                this.dispose();
            }
        }
    }

}
