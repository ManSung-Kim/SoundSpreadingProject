package com.gtk.tcp;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.gtk.util.MSLog;
import com.gtk.wifisoundserver.StaticData;

public class TcpController {
	static private TcpController spmInstance = null;
		
	/*
	 * get Instance of TcpController
	 */
	static public TcpController getInstance() {
		if(spmInstance == null) 
			spmInstance = new TcpController();
		return spmInstance;
	}
	
	/**
	 * get Local Server Ip
	 * @return Local Server Ip
	 */
	public String getIp() {
		try {
			Enumeration<NetworkInterface> lmNetInterfaceList = NetworkInterface.getNetworkInterfaces();
			NetworkInterface lmCurrNetInterface;
			for(;lmNetInterfaceList.hasMoreElements();) {
				lmCurrNetInterface = lmNetInterfaceList.nextElement();
				Enumeration<InetAddress> lmInetAddrList = lmCurrNetInterface.getInetAddresses();
				InetAddress lmInetAddr;
				for(;lmInetAddrList.hasMoreElements();) {
					lmInetAddr = lmInetAddrList.nextElement();
					if(	!lmInetAddr.isLoopbackAddress() // not loopback device
							&& !lmInetAddr.isLinkLocalAddress() // not link local addr
							&& lmInetAddr.isSiteLocalAddress() ) {
						return lmInetAddr.getHostAddress().toString();
					}
				}
			}
		} catch ( SocketException e ) {
			MSLog.log(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param ip Ip get '#getIP', and may it valid ip is STRING_TYPE. if not, null 
	 * @return t/f
	 */
	public boolean isValidIp(String ip) {
		boolean lmValid = (ip==null)?false:true;
		return lmValid;
	}

}
