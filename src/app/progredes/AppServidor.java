package app.progredes;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import pantallas.progredes.PantallaServer;

public class AppServidor {
	
	public static void main(String[] args) {
		JFrame marco = new JFrame();
		marco.setTitle("TP N° 6: SERVIDOR");
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco.setBounds(1000, 100, 320, 200);
		marco.setVisible(true);
		marco.setContentPane(new PantallaServer(marco));
		JDesktopPane desktopPane = new JDesktopPane();
		JInternalFrame internalFrame = new JInternalFrame();        
		JPanel mainPanel = new JPanel();
		mainPanel.add(desktopPane);
		mainPanel.add(internalFrame);
		marco.add(mainPanel);
		marco.validate();
	}

}
