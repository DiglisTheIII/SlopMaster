package slopMaster;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.security.auth.login.LoginException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class SlopControlPanel {

	private JFrame frame;
	private JTextField nameField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SlopControlPanel window = new SlopControlPanel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SlopControlPanel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1069, 578);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBounds(100, 100, 1069, 578);
		frame.getContentPane().add(panel);
		
		JPasswordField value = new JPasswordField();   
	    JLabel l1=new JLabel("Password:");    
	    l1.setBounds(450,150, 80,30);    
	    value.setBounds(515,150,100,30);    
	    panel.add(value);  
	    panel.add(l1);  
	    panel.setSize(300,300);    
	    panel.setLayout(null);
	    
		    
	    panel.setSize(300,300);    
	    panel.setLayout(null);    
	    
	    JButton startup = new JButton("Startup");
	    startup.setBounds(465, 82, 126, 23);
	    panel.add(startup);
	    panel.setVisible(true);
	    
	    JButton shutdown = new JButton("Shutdown");
		shutdown.setBounds(465, 116, 126, 23);
		panel.add(shutdown);
			
		startup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					String token = Files.readAllLines(Paths.get("C:/Users/mmmmm/Desktop/botgifs/password.txt")).get(0);
					boolean passCorrect = false;
					JLabel validator = new JLabel("");
		    		validator.setBounds(100, 167, 126, 23);
		    		panel.add(validator);
		    		char[] password = value.getPassword();
		    	    char[] correctPass = new String(token).toCharArray();
		    		if(Arrays.equals(password, correctPass)) {
		    			passCorrect = true;
		    			value.setText(null);
		    		} else {
		    			passCorrect = false;
		    		}
					if(passCorrect) {
						try {
							BotDriverClass drver = new BotDriverClass();
							drver.startup();
						} catch (LoginException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		shutdown.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e){
				try {
					String token = Files.readAllLines(Paths.get("C:/Users/mmmmm/Desktop/botgifs/password.txt")).get(0);
					boolean passCorrect = false;
					JLabel validator = new JLabel("");
		    		validator.setBounds(100, 167, 126, 23);
		    		panel.add(validator);
		    		char[] password = value.getPassword();
		    	    char[] correctPass = new String(token).toCharArray();
		    		if(Arrays.equals(password, correctPass)) {
		    			passCorrect = true;
		    			value.setText(null);
		    		} else {
		    			passCorrect = false;
		    		}
		    		if(passCorrect) {
		    			System.exit(0);
		    		}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
			
		panel.add(startup, BorderLayout.CENTER);
			
		JLabel ctrl = new JLabel("Slop Master Control Panel");
		ctrl.setHorizontalAlignment(SwingConstants.CENTER);
		ctrl.setFont(new Font("Comic Sans MS", Font.PLAIN, 36));
		ctrl.setBounds(266, 11, 524, 60);
		panel.add(ctrl);
		
		nameField = new JTextField();
		nameField.setBounds(515, 191, 100, 30);
		panel.add(nameField);
		nameField.setColumns(10);
		
		JLabel nameLbl = new JLabel("New Name");
		nameLbl.setBounds(450, 199, 55, 14);
		panel.add(nameLbl);
		
		JButton nameBtn = new JButton("Change Name");
		nameBtn.setBounds(450, 232, 108, 30);
		nameBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String token = Files.readAllLines(Paths.get("C:/Users/mmmmm/Desktop/botgifs/password.txt")).get(0);
					boolean passCorrect = false;
					JLabel validator = new JLabel("");
		    		validator.setBounds(100, 167, 126, 23);
		    		panel.add(validator);
		    		char[] password = value.getPassword();
		    	    char[] correctPass = new String(token).toCharArray();
		    		if(Arrays.equals(password, correctPass)) {
		    			passCorrect = true;
		    			value.setText(null);
		    		} else {
		    			passCorrect = false;
		    		}
		    		if(passCorrect) {
		    			Commands cmd = new Commands();
						cmd.setName(nameField.getText());
		    		}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		panel.add(nameBtn);
			
			
		}
	}

