package kd.classes.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import kd.classes.FileManager;
import kd.classes.output.LogOutputStream;

public class OsuExtracterGui extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6755670518411925947L;
	private JPanel paths;
	private JPanel buttons_down;
	private JPanel copyright;
	private JPanel log;
	
	private String osuDir;
	
	private boolean re;
	
	private JTextArea output;
	
	private PrintStream out;
	
	public OsuExtracterGui(String osuDir) {
		super("Osu Music Extracter");
		
		this.osuDir = osuDir;
		this.re = false;
		
		if(osuDir == null || osuDir.isEmpty()) JOptionPane.showMessageDialog(this,
			    "Your Osu! directory was not found!",
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
		
		
		//Set the size and center the widnow
		this.setMinimumSize(new Dimension(400, 600));
		this.setLocationRelativeTo(null);  
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		this.setResizable(false);
		
		this.setJMenuBar();
		
		BoxLayout gl = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
		this.setLayout(gl);
		gl.layoutContainer(getContentPane());
		
		this.paths = new JPanel();
		this.paths.setLayout(new BoxLayout(paths, BoxLayout.Y_AXIS));
		
		this.buttons_down = new JPanel();
		this.buttons_down.setLayout(new FlowLayout());
		
		this.copyright = new JPanel();
		this.copyright.setLayout(new FlowLayout());
		
		this.log = new JPanel();
		log.setLayout(new FlowLayout());
		
		this.components();
		
		this.paths.setVisible(true);	
		this.buttons_down.setVisible(true);
	
		this.add(paths);
		this.add(log);
		this.add(buttons_down);
		this.add(copyright);
		
		this.out = System.out;
		
		PrintStream printStream = new PrintStream(new LogOutputStream(this.output));
		
		System.setOut(printStream);
        System.setErr(printStream);
        
        System.out.println(new Date());
		System.out.println("For futher information, click the 'Info' item above");
		
		this.setVisible(true);
	}
	
	private void components() {
		JLabel udirtext = new JLabel("Your Osu directory's path:", SwingConstants.CENTER);
		udirtext.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.paths.add(udirtext);
		
		JTextField text = new JTextField();
		if(osuDir != null) text.setText(osuDir);
		text.setMaximumSize(new Dimension(170,20));
		this.paths.add(text);
		
		this.paths.add(Box.createRigidArea(new Dimension(5,5)));
		
		JButton b_c = new JButton("Change Osu! directory");
		b_c.setAlignmentX(Component.CENTER_ALIGNMENT);
		b_c.setPreferredSize(new Dimension(170,20));
		this.paths.add(b_c);
		b_c.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser dir = new JFileChooser();
				dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int ret = dir.showDialog(null, "Open Directory");                
				if (ret == JFileChooser.APPROVE_OPTION) {
				    File file = dir.getSelectedFile();
				    text.setText(file.getAbsolutePath());
				}
				
			}
			
		});
		
		this.paths.add(Box.createRigidArea(new Dimension(5,5)));
		
		JLabel udirmustext = new JLabel("Music directory:", SwingConstants.CENTER);
		udirmustext.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.paths.add(udirmustext);
		
		JTextField text2 = new JTextField();
		text2.setToolTipText("e.g C:/Users/Music");
		text2.setMaximumSize(new Dimension(170,20));
		this.paths.add(text2);
		
		this.paths.add(Box.createRigidArea(new Dimension(5,5)));
		
		JButton b2_c = new JButton("Change music directory");
		b2_c.setAlignmentX(Component.CENTER_ALIGNMENT);
		b2_c.setPreferredSize(new Dimension(170,20));
		this.paths.add(b2_c);
		
		b2_c.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser dir = new JFileChooser();
				dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int ret = dir.showDialog(null, "Open Directory");                
				if (ret == JFileChooser.APPROVE_OPTION) {
				    File file = dir.getSelectedFile();
				    text2.setText(file.getAbsolutePath());
				}
				
			}
			
		});
		
		
		this.paths.add(Box.createRigidArea(new Dimension(5,5)));
		
		//Log
		
		this.output = new JTextArea(20,16);
		JScrollPane sp = new JScrollPane(output);
		sp.setPreferredSize(new Dimension(300,330));
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.log.add(sp);
		
		//CheckBoxes
		JCheckBox rewrite = new JCheckBox("Rewrite files");
		rewrite.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.paths.add(rewrite);
		rewrite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(re) {
					re = false;
				}else {
					re = true;
				}
				
			}
			
		});
		
		//Last buttons
		JButton b2 = new JButton("Open Osu! folder");
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Path p = Paths.get(text.getText());
				if(text.getText().isEmpty()) {
					JOptionPane.showMessageDialog(b2.getParent(),
						    "Your Osu! directory is empty!",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				 if(!Files.exists(p)){
					 JOptionPane.showMessageDialog(b2.getParent(),
							    "Your Osu! directory doesn't exist!",
							    "Error",
							    JOptionPane.ERROR_MESSAGE);
				     return;
				 }
				 
				 try {
					Desktop.getDesktop().open(p.toFile());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
			}
			
		});
		b2.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.buttons_down.add(b2);
		
		JButton b = new JButton("Extract");
		b.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.buttons_down.add(b);
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Path p = Paths.get(text.getText());
				if(text.getText().isEmpty()) {
					JOptionPane.showMessageDialog(b2.getParent(),
						    "Your Osu! directory is empty!",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				 if(!Files.exists(p)){
					 JOptionPane.showMessageDialog(b2.getParent(),
							    "Your Osu! directory doesn't exist!",
							    "Error",
							    JOptionPane.ERROR_MESSAGE);
				     return;
				 }	
				
				p = Paths.get(text2.getText());
				if(text2.getText().isEmpty()) {
					JOptionPane.showMessageDialog(b.getParent(),
						    "Your music directory is empty!",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(!Files.exists(p)){
					 JOptionPane.showMessageDialog(b.getParent(),
							    "Your music directory doesn't exist!",
							    "Error",
							    JOptionPane.ERROR_MESSAGE);
				     return;
				 }
				
				
				try {
					FileManager.getInstance().ExtractSongs(text.getText() + "\\Songs", text2.getText(), re);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
				output.setCaretPosition(output.getText().length());
				
			}
		});
		
		JButton b3 = new JButton("Open music folder");
		b3.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.buttons_down.add(b3);
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Path p = Paths.get(text2.getText());
				if(text2.getText().isEmpty()) {
					JOptionPane.showMessageDialog(b3.getParent(),
						    "Your music directory is empty!",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(!Files.exists(p)){
					 JOptionPane.showMessageDialog(b3.getParent(),
							    "Your music directory doesn't exist!",
							    "Error",
							    JOptionPane.ERROR_MESSAGE);
				     return;
				 }
				 
				 try {
					Desktop.getDesktop().open(p.toFile());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		
		//Copyright
		JLabel copyright = new JLabel("Copyright © K_D.", SwingConstants.CENTER);
		copyright.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.copyright.add(copyright);
		
	}
	
	private void setJMenuBar(){
		JMenuBar menu = new JMenuBar();
		menu.setBackground(Color.GRAY);
		
		JMenuItem inf = new JMenuItem("Info");
		inf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				info();
			}
			
		});
		
		menu.add(inf);
		
		this.setJMenuBar(menu);	
		
		
	}
	
	private void info() {
		 System.out.println("Created by K_D");
		 System.out.println("Github page: https://github.com/KDjnkns");	 
		 System.out.println("Report bugs on the github page!");	 
	}
	

}
