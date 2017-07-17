package com.klindziuk.offlinelibrary.server.util;

import com.klindziuk.offlinelibrary.serverobserver.Server;

/**
 * @author Pavel_Klindziuk
 * Initialize Singleton server for every single testing thread.
 */
public class MultiServer {
	private static ThreadLocal<Server> threadServer = new ThreadLocal<Server>() {
        @Override
        public Server initialValue() {
           return Server.getInstance();
        }
    };
    public static Server getInstance() {
        return threadServer.get();
    }
}
