package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPSever {
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
                    long time=System.currentTimeMillis();
                    //3.使用Socket对象中的方法getInputSteam() 获得输入流
                    InputStream is =socket.getInputStream();
                    FileOutputStream fos=new FileOutputStream(new File("C:\\Users\\马\\IdeaProjects\\C-S",time+".jpg"));
                    byte[] list=new byte[1024];
                    int len;
                    //4.使用网络字节输入流InputStream对象中的read()方法
                    while((len=is.read(list))!=-1){
                        fos.write(list,0,len);
                    }
                    fos.close();
                    //5. 使用Socket对象中的getoutputStream()方法获得输出流
                    OutputStream  os=socket.getOutputStream();
                    //6.使用网络字节输出流OutputSteam对象中的write()方法
                    os.write("你好，图片传输完毕".getBytes());

                    socket.close();

                }catch (IOException e){
                    e.printStackTrace();
                }
            }).start();

        }
    }
}
