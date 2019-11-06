import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {
    public static void main(String[] args) throws IOException {
        method();

    }
    public static void method() throws IOException{
        //  1.创建一个服务器对ServerSocket对象和指定的端口号要一致.
        ServerSocket serverSocket=new ServerSocket(8888);
        while(true){
            //2.使用ServerSocket对象中的的方法accept,获取到请求的客户端对象Scoket
            // 创建多线程,提高可以同时与多个客户端进行数据的传输,提高效率
            Socket socket =serverSocket.accept();//阻塞(如果没有客户端连接,程序会停止在这个地方)
            new Thread(()->{
                try{
                    //获得客户端Socket对象
                    //给保存的文件设置随机名,避免覆盖

                    //3.使用Socket对象中的方法getInputSteam() 获得输入流
                    InputStream is =socket.getInputStream();
                    OutputStream os=socket.getOutputStream();
                    String message="";
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                    String lineMessage;
                    while ((lineMessage=bufferedReader.readLine())!=null)
                    {
                        message+=lineMessage;
                    }

                    JSONObject root=new JSONObject(message);
                    String operator=root.getString("operator");
                    String id=root.getString("id");
                    String account=root.getString("account");
                    String password=root.getString("password");
                    String tableName="";
                    if(operator.equals("login")) {
                        if(id.equals("stu")){
                            tableName="studentpassword";
                        }
                        if(id.equals("tea")){
                            tableName="teacherpassword";
                        }
                        if(id.equals("admin")){
                            tableName="adminpassword";
                        }
                    }

                    String sql="select username,password from "+tableName;
                    Connection connection=CONN();
                    Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ResultSet resultSet=statement.executeQuery(sql);
                    while (resultSet.next()){
                        if(resultSet.getString("username").equals(account)){
                            if(resultSet.getString("password").equals(password)){
                                os.write("OK".getBytes());
                                break;
                            }else {
                                os.write("Failed".getBytes());
                                break;
                            }
                        }else {
                            os.write("Failed".getBytes());
                            break;
                        }
                    }


//                    byte[] buf = new byte[1024];
//                    int len = is.read(buf);
//                    String str2 = new String(buf,0,len);
//                    //5. 使用Socket对象中的getoutputStream()方法获得输出流
//                    String[] strings=str2.split("#");
//                    if(operator.equals("login"))
//                    {
//                        if(id.equals("stu")&&account.equals("11")&&password.equals("hhh"))
//                            os.write("StuOK".getBytes());
//                        else {
//                            if(id.equals("tea")&&account.equals("22")&&password.equals("hhh"))
//                                os.write("teacherOK".getBytes());
//                            else{
//                                if(id.equals("admin")&&account.equals("00")&&password.equals("hhh"))
//                                    os.write("adminOK".getBytes());
//                                else
//                                    os.write("Failed".getBytes());
//                            }
//                        }
//                    }
//
                   is.close();
                   os.close();

                    socket.close();

                }catch (IOException | JSONException e){
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }
    public static Connection CONN() throws ClassNotFoundException, SQLException {
        final String URL = "jdbc:mysql://116.62.5.153:3306";
        final String USER = "root";
        final String PASSWORD = "WYKwyk123";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://116.62.5.153:3306/results?useSSL=false&serverTimezone=UTC",USER,PASSWORD);
        return conn;

    }

}
