package pantallas.progredes;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import hilos.progredes.HiloServidorEscucha;
import socketTCP.progredes.et37.SocketServidorTCP;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class PantallaServer extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtPuerto;
	int puerto;
	SocketServidorTCP serverSocket = new SocketServidorTCP();
	
	HiloServidorEscucha hiloServer;
	
	public PantallaServer(JFrame marco) {
		
		setLayout(null);
		
		JLabel lblServidor = new JLabel("SERVIDOR");
		lblServidor.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblServidor.setHorizontalAlignment(SwingConstants.CENTER);
		lblServidor.setBounds(10, 23, 280, 31);
		add(lblServidor);
		
		txtPuerto = new JTextField();
		txtPuerto.setBounds(150, 68, 86, 20);
		add(txtPuerto);
		txtPuerto.setColumns(10);
		
		JLabel lblPuerto = new JLabel("Puerto:");
		lblPuerto.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPuerto.setBounds(72, 72, 68, 14);
		add(lblPuerto);
		
		JButton btnDesconectar = new JButton("DESCONECTAR");
		btnDesconectar.setEnabled(false);
		btnDesconectar.setForeground(Color.BLACK);
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int confirmacion;
				confirmacion = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea cortar la conexión?", "Alerta", JOptionPane.YES_NO_OPTION);
				if(confirmacion == JOptionPane.YES_OPTION) {
					
					try {
						
						hiloServer.setConexion(false);
						serverSocket.cerrar();
						marco.setVisible(false);
						marco.dispose();
						
						System.exit(0);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		});
		btnDesconectar.setBounds(151, 113, 120, 23);
		add(btnDesconectar);
		
		//---------------------------------------------------------------------------------------------------------------
		JButton btnEscuchar = new JButton("ESCUCHAR");
		
		btnEscuchar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					puerto = Integer.parseInt(txtPuerto.getText());
				
				
			    hiloServer = new HiloServidorEscucha(puerto, txtPuerto, btnEscuchar, btnDesconectar, serverSocket);
				new Thread (hiloServer).start();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Ingrese un puerto", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnEscuchar.setForeground(Color.BLUE);
		btnEscuchar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEscuchar.setBounds(30, 113, 110, 23);
		add(btnEscuchar);
		//---------------------------------------------------------------------------------------------------------------
		
		
	}
}
