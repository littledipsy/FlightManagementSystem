package Manager;

import Customer.DbConnect;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;

public class Login extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField useridField;
    private JPasswordField passwardField;
    private JButton loginbutton,registerbutton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Login();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Create the frame.
     */
    public Login() {
        super("管理员登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(620, 320, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel label = new JLabel("账号：");

        useridField = new JTextField();
        useridField.setColumns(10);

        JLabel label_1 = new JLabel("密码：");

        passwardField = new JPasswordField();
        passwardField.setColumns(10);

        loginbutton = new JButton("登陆");
        registerbutton = new JButton("注册");
        loginbutton.addActionListener(this);
        registerbutton.addActionListener(this);

        JLabel lblNewLabel = new JLabel("欢迎登陆航班管理系统");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(141)
                                .addComponent(loginbutton, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(registerbutton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(143))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(136)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(label_1)
                                        .addComponent(label))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(passwardField, 132, 132, 132)
                                        .addComponent(useridField, 132, 132, 132))
                                .addContainerGap(115, Short.MAX_VALUE))
                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addContainerGap(104, Short.MAX_VALUE)
                                .addComponent(lblNewLabel)
                                .addGap(86))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(27)
                                .addComponent(lblNewLabel)
                                .addGap(30)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label)
                                        .addComponent(useridField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(17)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_1)
                                        .addComponent(passwardField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(28)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(registerbutton)
                                        .addComponent(loginbutton))
                                .addGap(30))
        );
        contentPane.setLayout(gl_contentPane);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == loginbutton) {
            login();
        }

        if(e.getSource() == registerbutton) {

            this.setVisible(false);

            new Register().addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Manager.Login.super.setVisible(true);
                }
            });
        }

    }


    public void login()
    {

        try
        {
            DbConnect dbConnect = new DbConnect();
            Connection conn = dbConnect.connect();

            boolean nameexist=false;  //判断用户名是否存在
            boolean passwordtrue=false;  //判断密码是否正确

            String inputuserid = useridField.getText();
            String password = new String(passwardField.getPassword());

            String sqlforid = "SELECT * from ManagerInformation WHERE Manager_ID=?";
            String sqlforpassword = "SELECT * from ManagerInformation WHERE Manager_ID=? AND Manager_Password=?";

            //查询用户名
            PreparedStatement preparedStatementforid = conn.prepareStatement(sqlforid);
            preparedStatementforid.setString(1,inputuserid);
            ResultSet id = preparedStatementforid.executeQuery();

            if (id.next())
            {
                nameexist = true;
            }

            //查询用户名及密码
            PreparedStatement preparedStatementforpassword = conn.prepareStatement(sqlforpassword);
            preparedStatementforpassword.setString(1,inputuserid);
            preparedStatementforpassword.setString(2,password);
            ResultSet res = preparedStatementforpassword.executeQuery();

            if (res.next())
            {
                passwordtrue = true;
                String userid = res.getString("Manager_ID");
                System.out.println(userid);
            }

            if (nameexist && !passwordtrue)  //此时用户名存在但密码错误
            {
                JOptionPane.showMessageDialog(null,"密码错误");
            }
            else if (!passwordtrue)//此时用户名不存在
            {
                JOptionPane.showMessageDialog(null,"用户名不存在");
            }
            if (passwordtrue)//此时登录成功
            {
                JOptionPane.showMessageDialog(null,"登录成功");
                new ManagementWindow();
                this.dispose();
            }

            preparedStatementforid.close();
            preparedStatementforpassword.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }



}
