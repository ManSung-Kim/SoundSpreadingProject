package com.gtk.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.gtk.util.MSLog;
import com.gtk.wifisoundserver.StaticData;

public class TcpServer implements Runnable{
	static private String spmIp;
	static private TcpController spmController;
	static private ServerSocket spmServerSocket = null;
	
	static public ServerSocket getInstance() {
		if(spmServerSocket == null) {
			try {
				spmServerSocket = new ServerSocket(StaticData.PORT);
			} catch (IOException e) {
				MSLog.log(e.getMessage());
				e.printStackTrace();
			}
		}
		return spmServerSocket;
	}
	
	@Override
	public void run() {
		spmController = TcpController.getInstance();
		if(spmController == null) {
			MSLog.log("Get Instance Failed");
			return;
		}
		
		spmIp = spmController.getIp();
		if(!spmController.isValidIp(spmIp)) {
			MSLog.log("invalid IP => ip:"+spmIp);
			return;
		}
		
		ServerSocket lmServerSocket = null;
		try {
			MSLog.log("@server. connect "+spmIp+":"+StaticData.PORT);
			//lmServerSocket = new ServerSocket(StaticData.PORT);
			lmServerSocket = TcpServer.getInstance();
			if(lmServerSocket == null) {
				MSLog.log("@server. serverSocket null");
				return;
			}
			
			while(true) {
				MSLog.log("@server. wait until a connect is made");
				Socket lmClientSocket = lmServerSocket.accept();// blocks until a connect is made 
				MSLog.log("@server. recv..");
				try {
					BufferedReader lmBuffReader;
					lmBuffReader = new BufferedReader(
										new InputStreamReader(lmClientSocket.getInputStream())
									);
					String lmStrRead = lmBuffReader.readLine();
					MSLog.log("@server. read : "+lmStrRead);
					
					PrintWriter lmPrintWriter;
					lmPrintWriter = new PrintWriter(
										new BufferedWriter(
											new OutputStreamWriter(lmClientSocket.getOutputStream())
										)
										, true // auto flush true
									);
					
					/*try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						MSLog.log(e.getMessage());
						e.printStackTrace();
					} finally {*/
						MSLog.log("@server. send to client : "+lmStrRead);
						lmPrintWriter.println("@server echo. "+lmStrRead);
					//}
				} catch (IOException e) {
					MSLog.log(e.getMessage());
					e.printStackTrace();
				} finally {
					lmClientSocket.close();
					MSLog.log("@server. server close complete");
				}
			}
		} catch (IOException e) {
			MSLog.log(e.getMessage());
			e.printStackTrace();			
		}
		MSLog.log("fin");
	}

		
}
