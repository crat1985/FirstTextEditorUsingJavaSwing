package fr.texteditor.com;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.apache.commons.lang3.StringUtils;

public class Main extends JFrame{

	private static final long serialVersionUID = -3821142735327780006L;
	JScrollPane scPane;
	JTextArea textArea;
	public Main() {
		super("Editeur de texte");
		this.setLocationRelativeTo(null);
		this.setSize(720,480);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setJMenuBar(createJMenuBar());
		
		JPanel contentPane = (JPanel) this.getContentPane();
		textArea = new JTextArea();
		scPane = new JScrollPane(textArea);
		contentPane.add(scPane,BorderLayout.CENTER);
	}
	
	private AbstractAction newEmptyFileAction = new AbstractAction() {
		{
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
			putValue(MNEMONIC_KEY, KeyEvent.VK_N);
			putValue(NAME, "Empty File");
			putValue(SHORT_DESCRIPTION, "Créer un nouveau fichier vide");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			textArea.setText("");
		}
	};
	
	private AbstractAction openFileAction = new AbstractAction() {
		{
			putValue(NAME, "Open File");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_DOWN_MASK));
			putValue(MNEMONIC_KEY, KeyEvent.VK_O);
			putValue(SHORT_DESCRIPTION, "Open a file from your computer");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(Main.this);
			if(fileChooser.getSelectedFile()==null) {
				return;
			}
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
				String tempContent = "";
				String content = "";
				try {
					while((tempContent = bufferedReader.readLine())!=null) {
						content+=tempContent+"\n";
					}
					content = StringUtils.removeEnd(content, "\n");
					textArea.setText(content);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Fichier");
		menuBar.add(fileMenu);
		JMenu newMenu = new JMenu("New...");
		fileMenu.add(newMenu);
		newMenu.add(newEmptyFileAction);
		fileMenu.add(openFileAction);
		return menuBar;
	}

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		Main window = new Main();
		window.setVisible(true);
	}

}
