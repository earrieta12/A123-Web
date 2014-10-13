package co.a123.mediacion.gcm;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;

import co.a123.mediacion.events.EventManager;
import co.a123.mediacion.util.Constant;

public class GCMConnection {

	ConnectionConfiguration config;
	XMPPConnection connection;
	private static GCMConnection connectionInstance = null;
	
	private GCMConnection(){
		
	}
	
	public static GCMConnection getInstance(){
		if(connectionInstance != null){
			connectionInstance = new GCMConnection();
		}
		return connectionInstance;
	}
	
	public boolean isConected(){
		return connection.isConnected();
	}
	
	/**
	 * Connects to GCM Cloud Connection Server using the supplied credentials.
	 * 
	 * @param username GCM_SENDER_ID@gcm.googleapis.com
	 * @param password API Key
	 * @throws XMPPException
	 */
	public void connect(String username, String password) throws XMPPException {
		config = new ConnectionConfiguration(Constant.GCM_SERVER,Constant.GCM_PORT);
		config.setSecurityMode(SecurityMode.enabled);
		config.setReconnectionAllowed(true);
		config.setRosterLoadedAtLogin(false);
		config.setSendPresence(false);
		config.setSocketFactory(SSLSocketFactory.getDefault());

		config.setDebuggerEnabled(false);
		// -Dsmack.debugEnabled=true
		XMPPConnection.DEBUG_ENABLED = false;

		connection = new XMPPConnection(config);
		connection.connect();
		
		EventManager eventManager = new EventManager(connection);
		
		connection.addConnectionListener(eventManager.CONNECTION_LISTENER);
		
		connection.addPacketListener(eventManager.PACKET_LISTENER, new PacketTypeFilter(Message.class));

		connection.addPacketInterceptor(eventManager.PACKET_INTERCEPTOR, new PacketTypeFilter(Message.class));

		connection.login(username, password);

	}
	
	public boolean disconnect(){
		
		boolean disconnect = false;
		if(connection != null){
			connection.disconnect();
			disconnect = true;
		}
		return disconnect;
	}

	
}
