package hilos.progredes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import clases.progredes.Cliente;
import socketTCP.progredes.et37.SocketClienteTCP;
import socketTCP.progredes.et37.SocketServidorTCP;

public class HiloServidorMensajes implements Runnable{

	ArrayList<Cliente> clientes = new ArrayList<>();
	Cliente cliente;
	SocketServidorTCP serverSocket;
	Socket socketPrevio;
	SocketClienteTCP socketActual = new SocketClienteTCP();//TODO esto estaba al principio del run, lo moví acá para testear pero no debería hacer diferencia
	String reenvioMen;
	boolean conexion=true;
	
	ObjectInputStream ois;
	
	public HiloServidorMensajes() {
		
	}
	
	public HiloServidorMensajes(ArrayList<Cliente> clientes, Socket socketPrevio) {
		this.clientes=clientes;
		this.socketPrevio=socketPrevio;
	}
	
	@Override
	public void run() {
		//posibilidad: crear nuevo SocketServidorTCP y asignarlo al Socket 
			try{
				
				while(getConexion()==true) {
					System.out.println("Esperando recibir objeto cliente con un mensaje");
					
					//cliente = (Cliente) serverSocket.recibirObjeto();//TODO Acá tiraba la excepción
					
					ois = new ObjectInputStream(socketPrevio.getInputStream());
					
					cliente = (Cliente) ois.readObject();
					
					System.out.println("Se recibió un mensaje");
					
					reenvioMen = " ["+cliente.getNombre()+"]: "+cliente.getMensaje();
					
					System.out.println("----Clientes que están en el array:");
					for (Cliente c : clientes) {
						System.out.println("-----"+c.getNombre());
					}
					
					for (Cliente c : clientes) {
						if (!c.getNombre().equals(cliente.getNombre())) {
							socketActual.setSocket(c.getSocket());
							socketActual.enviar(reenvioMen);
							System.out.println("Recorriendo al cliente "+ c.getNombre());
						}
					}
					System.out.println("conexion: "+conexion);
					System.out.println("nombre: "+cliente.getNombre()+"\nreenvioMen: "+reenvioMen+"\n\n");
				}
			} catch(SocketException e) {
				actualizarLista();
				System.out.println("Socket Exception, se saca al cliente de la lista");
			} catch(ClassNotFoundException e) {
				System.out.println("Class Not Found Hilo Escucha");
				e.printStackTrace();
			}catch(IOException e) {
				System.out.println("IOE Hilo Escucha");
				e.printStackTrace();
		}
	}
	
	public void setConexion(Boolean conexion) {
		this.conexion=conexion;
	}
	
	public Boolean getConexion() {
		return conexion;
	}
	
	public void actualizarLista() {
		for (Cliente c : clientes) {
			if (c.getNombre().equals(cliente.getNombre())) {
				clientes.remove(c);
			}
		}
	}
	
}
