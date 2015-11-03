package com.gtk.util;

import com.gtk.wifisoundserver.StaticData;

public class MSLog {
	private static String psmTitle = "GTK_WIFISOUND_LOG >> ";
	
	static public <T extends Object> void log(T msg) {
		if(StaticData.DBG) System.out.println(psmTitle.concat(msg+""));
	}
}
