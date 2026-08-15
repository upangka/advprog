///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package server.serial;

import java.io.IOException;
import java.net.ServerSocket;

import constants.Constants;
import server.Server;

/**
 * SerialServer
 */
public class SerialServer implements Server{
    private final ServerSocket serverSocket;

    public SerialServer(int port) throws IOException{
         this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        System.out.println("Serial Server 启动成功，端口: %d".formatted(serverSocket.getLocalPort()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void stop(){
        try{
            if(!this.serverSocket.isClosed()){
                this.serverSocket.close();  
                System.out.println("关闭服务器Success");
            }
        }catch(IOException e){
            System.err.println("关闭服务器出现错误: " + e.getMessage());
        }

    }
}