package hilos.progredes;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import clases.progredes.Cliente;
import socketTCP.progredes.et37.SocketServidorTCP;

public class HiloServidorEscucha implements Runnable{
	
	//Variables
	Cliente cliente;
	SocketServidorTCP serverSocket;
	String reenvioMen;
	ArrayList <Cliente> listaClientes = new ArrayList<>();
	//private String nombre;
	
	//JVariables
	JTextField txtPuerto;
	JButton btnEscuchar;
	JButton btnDesconectar;
	
	int puerto;
	boolean conexion;
	
	int cont=0;
	
	HiloServidorMensajes hiloEscucha;
	
	//Constructor
	public HiloServidorEscucha(int puerto, JTextField txtPuerto, JButton btnEscuchar, JButton btnDesconectar, SocketServidorTCP serverSocket) {
		this.puerto = puerto;
		this.txtPuerto = txtPuerto;
		this.btnEscuchar = btnEscuchar;
		this.btnDesconectar = btnDesconectar;
		this.serverSocket=serverSocket;
	}
	
	//Metodo Run
	@Override
	public void run(){
		
		try {
			
		conexion = true;
				
		serverSocket.escuchar(puerto);
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				
				txtPuerto.setEditable(false);
				btnEscuchar.setEnabled(false);
				btnDesconectar.setEnabled(true);
			}
		});
		
		
		while(getConexion() == true){
			
			
				
			System.out.println("Se espera un nuevo cliente");
		
			serverSocket.aceptar();
			
			System.out.println("Se aceptó y se crea un socket");
			
			do {
				
				if (cont == 3) {
					System.out.println("segunda vueltaaa\n");
				}
			
			cliente = (Cliente) serverSocket.recibirObjeto();
			
			System.out.println("Se recibió un objeto cliente");
			
			cont++;
			
			if (nombreRepetido() == true) {
				System.out.println("CLIENTE CON EL MISMO NOMBRE");
				serverSocket.enviar("si");
			}else {
				serverSocket.enviar("no");
			}
			
			System.out.println("contador = "+ cont);
			
			}while(nombreRepetido()==true);
				
				cliente.setSocket(serverSocket.getSocket());
				
				listaClientes.add(cliente);
				
				System.out.println("Se agrega al cliente a la lista\n");
				
				hiloEscucha = new HiloServidorMensajes(listaClientes, serverSocket.getSocket());
				//no pasar el serversocket asi nomas sino pasar el serversocket.getsocket para luego 
				//asignarlo a una variable socket standard en hilo servidor mensajes
				new Thread (hiloEscucha).start();
			
		}
		
		hiloEscucha.setConexion(false);
		
		
		}catch(ClassNotFoundException e) {
			System.out.println("CLASS NOT FOUND Hilo Servidor");
		}catch(IOException e) {
			System.out.println("IOException Hilo Servidor");
			conexion = false;//TODO Si tira la excepción, debería matar al server
			JOptionPane.showMessageDialog(null, "No se pudo iniciar, intente con otro puerto", "Alerta", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public void setConexion(Boolean conexion) {
		this.conexion=conexion;
	}
	
	public Boolean getConexion() {
		return conexion;
	}
	
	public Boolean nombreRepetido() {
		for (Cliente c : listaClientes) {
			if (c.getNombre().equals(cliente.getNombre())) {
				return true;
			}
		}
		return false;
	}

}
