package com.gtk.wifisoundserver;

import java.io.IOException;

import com.gtk.tcp.TcpServer;
import com.gtk.util.MSLog;

public class MainController {
	
	public static void main(String args[]) {
		Thread spmServerThread = new Thread(new TcpServer());
		spmServerThread.start();
		MSLog.log("@MainController. fin");
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				MSLog.log("@shutdown hook tread");
				try {
					TcpServer.getInstance().close();
				} catch (IOException e) {
					MSLog.log("@shutdown hook tread. TcpServer Close Fail");
					e.printStackTrace();					
				} finally {
					MSLog.log("@shutdown hook tread. TcpServer Close Complete");
				}
			}
		}));
	}
}