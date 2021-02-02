package hilos.progredes;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import clases.progredes.Cliente;
import socketTCP.progredes.et37.SocketClienteTCP;

public class HiloCliente implements Runnable{
	
	
	
	//Variables	
	String nombre;
	int puerto;
	String ip;
	Cliente cliente;
	Cliente cRecibido;
	SocketClienteTCP socketCliente;
	String mensajeRecibido;
	String mensaje;
	
	//Interfaz
	JButton btConectar;
	JButton btDesconectar;
	JButton btConfirmar;
	JButton btEnviar;
	JTextField txtMensaje;
	JLabel bienvenido;
	JTextArea txtArea;
	
	
	
	//Constructor
	public HiloCliente(JTextArea txtArea, JLabel bienvenido, Cliente cliente,String nombre, int puerto, String ip,
			JButton btConectar, JButton btDesconectar, JButton btConfirmar, JButton btEnviar, JTextField txtMensaje, SocketClienteTCP socketCliente) {
		this.txtArea=txtArea;
		this.cliente= cliente;
		this.nombre = nombre;
		this.puerto = puerto;
		this.ip = ip;
		
		this.bienvenido=bienvenido;
		this.btConectar=btConectar;
		this.btDesconectar=btDesconectar;
		this.btConfirmar=btConfirmar;
		this.btEnviar=btEnviar;
		this.txtMensaje=txtMensaje;
		
		this.socketCliente=socketCliente;
		
	}
	
	@Override
	public void run() {
		
		try {
		
		//while que mantiene en escucha al cliente
		while(true) {
			
			mensaje = socketCliente.recibir();
			
			
			if (mensaje.equals("si")) {
				do {
					String respuesta = JOptionPane.showInputDialog(null, "Escriba nuevamente su nombre", "Error!", JOptionPane.ERROR_MESSAGE);
					
					mensaje = socketCliente.recibir();
				}while(mensaje.equals("si"));
			}
			
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					if (mensaje != null && mensaje != "" && mensaje != " ") {
						System.out.println(" Mensaje recibido: "+mensaje+"\n");
						txtArea.append(mensaje+"\n");
						
					}	
				}
			});
		}
		
		
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "No se pudo establecer una conexión. \n Servidor inhabilitado.");
		} finally {
			
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						JOptionPane.showMessageDialog(null, "Ha finalizado la conexión con el servidor.", "Información", JOptionPane.WARNING_MESSAGE);
						btEnviar.setEnabled(false);
						txtMensaje.setEnabled(false);
					}
				});
			
		}
	}

}
