package clases.progredes;

import java.io.Serializable;
import java.net.Socket;

public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 4450504144627399045L;
	
	String nombre;
	String mensaje;
	Socket socket;
	/*SocketClienteTCP socketCliente = new SocketClienteTCP();
	
	public SocketClienteTCP getSocketCliente() {
		return socketCliente;
	}
	*/


	public Cliente() {
		
	}
	
	public Cliente(String nombre, String mensaje){
		this.nombre = nombre;
		
		this.mensaje=mensaje;
		
	}

	public Cliente(String nombre, String mensaje, Socket socket){
		this.nombre = nombre;
		
		this.mensaje=mensaje;
		
		this.socket = socket;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje=mensaje;
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	

}
