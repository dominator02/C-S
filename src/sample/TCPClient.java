package sample;

import java.io.*;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        //1.创建一个客户端对线Socket,构造方法中绑定服务器的IP地址和端口号
        Socket socket= new Socket("127.0.0.1",8888);
        //2.使用Socket对象中的方法getOutputStream(0获取网络字节输出流OutputSteam对象
        //这个流是个网络流,指向了服务器
        OutputStream os=socket.getOutputStream();
        File f=new File("E:\\图片\\焰.jpg");
        FileInputStream fis=new FileInputStream(f);
        byte[] bs=new byte[1024];
        int len;
        while((len=fis.read(bs))!=-1)
        {
            //3.使用字节输出流OutputSteam对象中的方法write,给服务器发送数据
            os.write(bs,0,len);
        }
        //告诉服务器关闭输出
        socket.shutdownOutput();
        fis.close();

        //4.使用Socket对象中的方法getInputSteam()获取网络字节输入流InputSteam()对象
        InputStream is=socket.getInputStream();
        //5.使用网络字节输入流InputSteam对象中的方法read,读取服务器回写的数据
        byte[] list =new byte[1024];
        int len1=is.read(list);
        String str1=new String(list,0,len1);
        System.out.println(str1);
        socket.close();
    }
}
