package sample;


import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.sql.*;

    public class Main {

        public static void main(String[] args) throws Exception{
            // write your code here
            ServerSocket serverSocket=new ServerSocket(8888);
            Socket socket=serverSocket.accept();

            InputStream is=socket.getInputStream();
            OutputStream os=socket.getOutputStream();


            byte[] bytes=new byte[1024];
            int len=is.read(bytes);
            String str2=new String(bytes,0,len);
            System.out.println(str2);//get

            String [] splitSTR=str2.split("#");
            switch (splitSTR[0]){
                case "studentlogin":

                    break;
                case "adminlogin":
                    if(AdminLogin(str2))
                        SendYes(os);
                    else
                        SendNo(os);
                    break;
            }


            //AdminLogin(str2);
            //os.write("Yes".getBytes());
            //os.flush();
            os.close();

        }

        private static void SendYes(OutputStream os) throws Exception
        {
            os.write(1);
            os.flush();
        }
        private static void SendNo(OutputStream os) throws Exception
        {
            os.write(0);
            os.flush();
        }

        private static boolean AdminLogin(String source) throws Exception {
            final String URL = "jdbc:mysql://116.62.5.153:3306";
            final String USER = "root";
            final String PASSWORD = "WYKwyk123";

            String[] temp =source.split("#");

            //1.加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2. 获得数据库连接
            //Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Connection conn = DriverManager.getConnection("jdbc:mysql://116.62.5.153:3306/results?useSSL=false&serverTimezone=UTC",USER,PASSWORD);
            //3.操作数据库，实现增删改查
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username, password FROM results.adminpassword");
            //如果有数据，rs.next()返回true
            while(rs.next()){
                //System.out.println(rs.getString("username")+" password："+rs.getString("password"));
                if(rs.getString("username").equals(temp[1]) && rs.getString("password").equals(temp[2]))
                {
                    return true;
                }

            }
            return false;


        }
    }

