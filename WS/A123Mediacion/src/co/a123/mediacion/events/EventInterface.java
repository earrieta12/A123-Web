package co.a123.mediacion.events;

import java.util.Map;

public interface EventInterface {

	public enum EVENTOS{
		ACK,
		NACK,
		REGISTRAR_DISPOSITIVO,
		ELIMINAR_DISPOSITIVO
	}
	
	void send(String jsonRequest);
	void manipularAckEvent(Map<String, Object> jsonObject);
	void manipularNackEvent(Map<String, Object> jsonObject);
	void manipularRegistroDispositivo(Map<String, Object> jsonObject);
	void manipularEliminacionDispositivo(Map<String, Object> jsonObject);
	
	
	String createJsonAck(String to, String messageId);
	
}
