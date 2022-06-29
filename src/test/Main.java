package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Main extends JFrame{

	public Main() {
		super("Editeur de texte");
		this.setLocationRelativeTo(null);
		this.setSize(720,480);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel contentPane = (JPanel) this.getContentPane();
		
		JDesktopPane desktopPane = new JDesktopPane();
		contentPane.add(desktopPane);
		
//		JOptionPane.showInternalMessageDialog(desktopPane, "slt");
		
		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(new JLabel("slt"));
		desktopPane.add(northPanel,BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		Main window = new Main();
		window.setVisible(true);
	}

}
