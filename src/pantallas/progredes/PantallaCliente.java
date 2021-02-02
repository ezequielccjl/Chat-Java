package pantallas.progredes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import clases.progredes.Cliente;
import hilos.progredes.HiloCliente;
import socketTCP.progredes.et37.SocketClienteTCP;



public class PantallaCliente extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Variables interfaz
	private JTextField txtIp;
	private JTextField txtPuerto;
	private JTextField txfMensaje;
	private JTextField txtNombre;
	private JButton btnConfirmar;
	private JButton btDesconectar;
	private String nombrePrueba;
	private Boolean vali;
	
	//Variables
	Cliente cli;
	String mensaje = "";
	SocketClienteTCP socketCliente = new SocketClienteTCP();
	
	
	
	public PantallaCliente(JFrame marco) {
		setLayout(null);
		
		JLabel lblAhoraSosCliente = new JLabel("CLIENTE");
		lblAhoraSosCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblAhoraSosCliente.setFont(new Font("SansSerif", Font.BOLD, 28));
		lblAhoraSosCliente.setBounds(10,30,610,40);
		add(lblAhoraSosCliente);
		
		JLabel lblIpServer = new JLabel("IP Server:");
		lblIpServer.setFont(new Font("Arial", Font.PLAIN, 15));
		lblIpServer.setBounds(30, 85, 77, 28);
		add(lblIpServer);
		
		txtIp = new JTextField();
		txtIp.setBounds(117, 90, 145, 20);
		add(txtIp);
		txtIp.setColumns(10);
		
		JLabel lblPuerto = new JLabel("Puerto:");
		lblPuerto.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPuerto.setBounds(30, 123, 62, 14);
		add(lblPuerto);
		
		txtPuerto = new JTextField();
		txtPuerto.setBounds(117, 121, 145, 20);
		add(txtPuerto);
		txtPuerto.setColumns(10);		
		
		JLabel lblBienvenido = new JLabel("");
		lblBienvenido.setForeground(Color.BLUE);
		lblBienvenido.setBounds(30, 337, 187, 14);
		add(lblBienvenido);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(320, 115, 300, 310);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(280, 90, 300, 275);
		add(scrollPane);
		scrollPane.setViewportView(textArea);
		
		txfMensaje = new JTextField();
		txfMensaje.setEditable(false);
		txfMensaje.setBounds(20, 384, 488, 39);
		add(txfMensaje);
		txfMensaje.setColumns(10);
		
		JLabel lblMensajeAEnviar = new JLabel("Mensaje a enviar:");
		lblMensajeAEnviar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMensajeAEnviar.setBounds(30, 359, 115, 14);
		add(lblMensajeAEnviar);
		
		//-------------------------------------------------------------------------------------------------------------------------------
		
		JButton btEnviar = new JButton(">");
		btEnviar.setEnabled(false);
		btEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				mensaje= txfMensaje.getText();
				cli.setMensaje(mensaje);
				
				if (mensaje != null && mensaje!= "" && mensaje != " ") {
					socketCliente.enviarObjeto(cli);//TODO acá lo envía bien
					
					System.out.println(" Mensaje enviado: ["+cli.getNombre()+"]: "+cli.getMensaje());
					
					textArea.append(" ["+cli.getNombre()+"]: "+mensaje+"\n");
				}
				
				txfMensaje.setText("");
				
				}catch(IOException f) {
					System.out.println("IOE enviar cliente");
				}
				
			}
		});
		btEnviar.setFont(new Font("Arial Black", Font.BOLD, 12));
		btEnviar.setBounds(518, 382, 62, 40);
		add(btEnviar);
		
		//---------------------------------------------------------------------------------------------------------------------------------
		
		JLabel lblDatosPersonales = new JLabel("Datos Personales:");
		lblDatosPersonales.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosPersonales.setBounds(24, 163, 238, 14);
		add(lblDatosPersonales);
		
		JLabel lblNombre = new JLabel("Nombre :");
		lblNombre.setBounds(45, 188, 62, 14);
		add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(102, 185, 130, 20);
		add(txtNombre);
		
		//-------------------------------------------------------------------------------------------------------------------------------
		
		JButton btConectar = new JButton("CONECTAR");
		btConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nuevoNom;
				String validacion;
				String nom = txtNombre.getText();
				String ipp = txtIp.getText();
				int port = Integer.parseInt(txtPuerto.getText());
				nombrePrueba = txtNombre.getText();
				cli= new Cliente(nom, mensaje);
				
				try {
					socketCliente.conectar(ipp, port);
					socketCliente.enviarObjeto(cli);
					do {
					validacion = socketCliente.recibir();
					
					//----------------------------------------------------
					
					if (validacion.equals("si")){
						vali = true;
						System.out.println("Se repite: "+ validacion);
					}else {
						vali = false;
						System.out.println("Se repite: "+ validacion);
					}
					
					//----------------------------------------------------
					
					if(vali == true) {
						JOptionPane.showMessageDialog(null, "Ya hay un usuario con ese nombre", "Aviso", JOptionPane.WARNING_MESSAGE );
						nuevoNom = JOptionPane.showInputDialog(null, "Escriba nuevamente su nombre", "Error!", JOptionPane.ERROR_MESSAGE);
						System.out.println("nuevo nombre: "+ nuevoNom);
						cli.setNombre(nuevoNom);
						socketCliente.enviarObjeto(cli);
						System.out.println("Se envió el objeto cliente con nuevo nombre\n");
					}
					
					} while(vali == true);

					
					JOptionPane.showMessageDialog(null, "Conexión establecida con éxito. ");
					btDesconectar.setEnabled(true);
					btConectar.setEnabled(false);
					txfMensaje.setEditable(true);
					btEnviar.setEnabled(true);
					
					textArea.append("\n");
					
						HiloCliente hiloCliente = new HiloCliente(textArea, lblBienvenido, cli, nom, port, ipp, btConectar, btDesconectar, 
								btnConfirmar, btEnviar, txfMensaje, socketCliente);
						new Thread (hiloCliente).start();
						lblBienvenido.setText("Bienvenido al chat " + cli.getNombre()+"!");
						
				} catch(ConnectException e1) {
					JOptionPane.showMessageDialog(null, "No se encuentra un servidor", "Aviso", JOptionPane.WARNING_MESSAGE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
				btConectar.setEnabled(false);
				btConectar.setForeground(Color.BLUE);
				btConectar.setFont(new Font("Tahoma", Font.PLAIN, 13));
				btConectar.setBounds(72, 259, 125, 28);
				add(btConectar);
		
		//---------------------------------------------------------------------------------------------------------------------------------
		
		btnConfirmar = new JButton("Confirmar Datos");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int confirmacion;
				confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro?", "Alerta", JOptionPane.YES_NO_OPTION);
				if (confirmacion == JOptionPane.YES_OPTION) {
					
					try	{
						String nom = txtNombre.getText();
						String ipp = txtIp.getText();
						String port = txtPuerto.getText();
						if (nom == null || nom.length()== 0 || ipp == null || ipp.length()==0 || port==null || port.length()==0) {
							JOptionPane.showMessageDialog(null, "Por favor rellene todos los campos.", "ERROR", JOptionPane.ERROR_MESSAGE);
						}else {
							
							txtNombre.setEditable(false);
							
							txtIp.setEditable(false);
							txtPuerto.setEditable(false);
							
							btConectar.setEnabled(true);
							btnConfirmar.setEnabled(false);
							
						}
						
					}catch (NumberFormatException ex)	{
					JOptionPane.showMessageDialog(null, "Por favor revise los valores ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		btnConfirmar.setBounds(62, 220, 150, 28);
		add(btnConfirmar);
		
		
		//------------------------------------------------------------------------------------------------------------------------------------
		
		btDesconectar = new JButton("DESCONECTAR");
		btDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmacion;
				confirmacion = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea cortar la conexión?", "Alerta", JOptionPane.YES_NO_OPTION);
				if(confirmacion == JOptionPane.YES_OPTION) {
					
					try {
						System.exit(0);
						socketCliente.cerrar();
						marco.setVisible(false);
						marco.dispose();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "DESCONECTAR PANTALLA CLIENTE", "Error", JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
				
			}
		});
		btDesconectar.setForeground(Color.RED);
		btDesconectar.setBounds(72, 298, 125, 28);
		add(btDesconectar);
		btDesconectar.setEnabled(false);
		
		//-----------------------------------------------------------------------------------------------------------------------------------
		
		
	}
}

