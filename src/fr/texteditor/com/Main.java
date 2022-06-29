package fr.texteditor.com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.apache.commons.lang3.StringUtils;

public class Main extends JFrame{

	private static final long serialVersionUID = -3821142735327780006L;
	JScrollPane scPane;
	JTextArea textArea;
	File file;
	public Main() {
		super("Editeur de texte");
		this.setLocationRelativeTo(null);
		this.setSize(720,480);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setJMenuBar(createJMenuBar());
		
		JPanel contentPane = (JPanel) this.getContentPane();
		textArea = new JTextArea();
		textArea.setTabSize(2);
		scPane = new JScrollPane(textArea);
		contentPane.add(scPane,BorderLayout.CENTER);
		
		contentPane.add(createJToolBar(),BorderLayout.NORTH);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int response = JOptionPane.showConfirmDialog(Main.this, "Voulez-vous vraiment quitter l'application ?","Confirmer ?",JOptionPane.YES_NO_OPTION);
				if(response==JOptionPane.YES_OPTION) {
					dispose();
				}
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println("Au revoir");
			}
		});
		
		
		UIDefaults uiDefaults = UIManager.getDefaults();
		uiDefaults.put("activeCaption", new ColorUIResource(Color.gray));
		uiDefaults.put("activeCaptionText", new ColorUIResource(Color.white));
		JFrame.setDefaultLookAndFeelDecorated(true);
	}
	
	private JToolBar createJToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.add(newEmptyFileAction);
		toolBar.add(newJavaFileAction);
		toolBar.add(openFileAction);
		toolBar.add(saveAction);
		toolBar.add(saveAsAction);
		toolBar.add(quitAction);
		return toolBar;
	}

	private AbstractAction newEmptyFileAction = new AbstractAction() {
		{
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
			putValue(MNEMONIC_KEY, KeyEvent.VK_N);
			putValue(NAME, "Empty File");
			putValue(SHORT_DESCRIPTION, "Créer un nouveau fichier vide");
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource("icons/new.png")));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!textArea.getText().trim().equals("")) {
				int response = JOptionPane.showConfirmDialog(Main.this, "Voulez-vous enregistrer votre travail avant de créer un nouveau fichier vide ?","Confirmer ?",JOptionPane.YES_NO_OPTION);
				if((response!=JOptionPane.YES_OPTION)&&(response!=JOptionPane.NO_OPTION)) {
					JOptionPane.showMessageDialog(Main.this, "Opération annulée...");
					return;
				}
				if(response==JOptionPane.YES_OPTION) {
					if(file==null) {
						saveFunction();
						return;
					}
					try {
						BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
						bufferedWriter.write(textArea.getText());
						bufferedWriter.close();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Main.this, "Erreur lors de l'enregistrement de "+file.getAbsolutePath());
						return;
					}
					JOptionPane.showMessageDialog(Main.this, "Enregistrement réussi de "+file.getAbsolutePath()+" !");
				}
			}
			textArea.setText("");
			file = null;
			JOptionPane.showMessageDialog(Main.this, "Nouveau document vide créé avec succès !");
		}
	};
	
	private AbstractAction newJavaFileAction = new AbstractAction() {
		{
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK));
			putValue(MNEMONIC_KEY, KeyEvent.VK_J);
			putValue(NAME, "Java File");
			putValue(SHORT_DESCRIPTION, "Créer un nouveau fichier Java basique");
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource("icons/java-project-icon.png")));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!textArea.getText().trim().equals("")) {
				int response = JOptionPane.showConfirmDialog(Main.this, "Voulez-vous enregistrer votre travail avant de créer un nouveau fichier vide ?","Confirmer ?",JOptionPane.YES_NO_OPTION);
				if((response!=JOptionPane.YES_OPTION)&&(response!=JOptionPane.NO_OPTION)) {
					JOptionPane.showMessageDialog(Main.this, "Opération annulée...");
					return;
				}
				if(response==JOptionPane.YES_OPTION) {
					if(file==null) {
						saveFunction();
						return;
					}
					try {
						BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
						bufferedWriter.write(textArea.getText());
						bufferedWriter.close();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Main.this, "Erreur lors de l'enregistrement de "+file.getAbsolutePath());
						return;
					}
					JOptionPane.showMessageDialog(Main.this, "Enregistrement réussi de "+file.getAbsolutePath()+" !");
				}
			}
			textArea.setText("public class Main{\n\tpublic static void main(String[] args){\n\t\tSystem.out.println(\"Hello World\")\n\t}\n}");
			file = null;
			JOptionPane.showMessageDialog(Main.this, "Nouveau document Java basique créé avec succès !");
		}
	};
	
	private AbstractAction quitAction = new AbstractAction() {
		{
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
			putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
			putValue(NAME, "Quit");
			putValue(SHORT_DESCRIPTION, "Quitter l'application");
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource("icons/exit.png")));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			int response = JOptionPane.showConfirmDialog(Main.this, "Voulez-vous vraiment quitter l'application ?","Confirmer ?",JOptionPane.YES_NO_OPTION);
			if(response==JOptionPane.YES_OPTION) {
				dispose();
			}
		}
	};
	private AbstractAction saveAction = new AbstractAction() {
		{
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
			putValue(MNEMONIC_KEY, KeyEvent.VK_S);
			putValue(NAME, "Save");
			putValue(SHORT_DESCRIPTION, "Save file you're editing");
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource("icons/save.png")));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(file==null) {
				saveFunction();
				return;
			}
			try {
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
				bufferedWriter.write(textArea.getText());
				bufferedWriter.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(Main.this, "Erreur lors de l'enregistrement de "+file.getAbsolutePath());
				return;
			}
			JOptionPane.showMessageDialog(Main.this, "Enregistrement réussi de "+file.getAbsolutePath()+" !");
		}
	};
	private void saveFunction() {
		JFileChooser fileChooser;
		File fileChoosed = file;
		fileChooser = new JFileChooser();
		fileChooser.showSaveDialog(Main.this);
		fileChoosed = fileChooser.getSelectedFile();
		if(fileChoosed==null) {
			JOptionPane.showMessageDialog(Main.this, "Sauvegarde annulée...");
			return;
		}
		file = fileChoosed;
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileChoosed));
			bufferedWriter.write(textArea.getText());
			bufferedWriter.close();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(Main.this, "Erreur lors de l'enregistrement de "+fileChoosed.getAbsolutePath());
			return;
		}
		JOptionPane.showMessageDialog(Main.this, "Enregistrement réussi de "+fileChoosed.getAbsolutePath()+" !");
	}
	
	private AbstractAction saveAsAction = new AbstractAction() {
		{
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK));
			putValue(MNEMONIC_KEY, KeyEvent.VK_S);
			putValue(NAME, "Save as");
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource("icons/save_as.png")));
			putValue(SHORT_DESCRIPTION, "Save the file you're editing to a specific location");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			saveFunction();
		}
	};
	
	private AbstractAction openFileAction = new AbstractAction() {
		{
			putValue(NAME, "Open File");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_DOWN_MASK));
			putValue(MNEMONIC_KEY, KeyEvent.VK_O);
			putValue(SHORT_DESCRIPTION, "Open a file from your computer");
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource("icons/open.png")));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(Main.this);
			if(fileChooser.getSelectedFile()==null) {
				return;
			}
			file = fileChooser.getSelectedFile();
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				String tempContent = "";
				String content = "";
				try {
					while((tempContent = bufferedReader.readLine())!=null) {
						content+=tempContent+"\n";
					}
					bufferedReader.close();
					content = StringUtils.removeEnd(content, "\n");
					textArea.setText(content);
				} catch (IOException e2) {
					e2.printStackTrace();
				} finally {
					try {
						if(bufferedReader!=null){
							bufferedReader.close();
						}
					} catch (IOException e3) {
						e3.printStackTrace();
					}
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
		newMenu.add(newJavaFileAction);
		fileMenu.add(openFileAction);
		fileMenu.add(saveAction);
		fileMenu.add(saveAsAction);
		fileMenu.add(quitAction);
		return menuBar;
	}

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		Main window = new Main();
		window.setVisible(true);
	}

}
