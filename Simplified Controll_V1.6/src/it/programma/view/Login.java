package it.programma.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import it.programma.bussiness.GestionaleBussiness;
import it.programma.modell.Operatore;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {
    
	
	private JTextField textUser;
	private JPasswordField passwordField;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	public Login() throws HeadlessException {
		super();
	}


	/**
	 * Create the frame.
	 */
	public Login(JFrame frames,Operatore opt) {
       
		getContentPane().setFont(new Font("Tahoma", Font.BOLD, 14));
		setAutoRequestFocus(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 402);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		JLabel logo = new JLabel("New label");
		logo.setForeground(new Color(0, 0, 0));
		logo.setIcon(new ImageIcon(Login.class.getResource("/image/logo_definitivo1.png")));
		logo.setBounds(119, 11, 263, 165);
		getContentPane().add(logo);

		JLabel logoMess = new JLabel("Benvenuto in Simpifield Controll");
		logoMess.setForeground(new Color(0, 100, 0));
		logoMess.setFont(new Font("Tahoma", Font.BOLD, 14));
		logoMess.setBounds(147, 11, 235, 24);
		getContentPane().add(logoMess);

		JLabel lblAreaDaccesso = new JLabel("Area D'accesso");
		lblAreaDaccesso.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAreaDaccesso.setForeground(new Color(0, 128, 0));
		lblAreaDaccesso.setBounds(205, 187, 114, 14);
		getContentPane().add(lblAreaDaccesso);

		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsername.setForeground(new Color(0, 0, 0));
		lblUsername.setBounds(101, 231, 74, 14);
		getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(101, 282, 74, 14);
		getContentPane().add(lblPassword);

		textUser = new JTextField();
		textUser.setBounds(194, 229, 136, 20);
		getContentPane().add(textUser);
		textUser.setColumns(10);

		passwordField = new JPasswordField();



		passwordField.setToolTipText("La Password deve contenere 8 carettari, di cui un carattere maiuscolo, \r\nun carattere minuscolo e un numero.");
		passwordField.setBounds(195, 280, 135, 20);
		getContentPane().add(passwordField);

		//////////////tasto accedi//////////////////////////////////
		JButton btnAccedi = new JButton("Accedi");
		btnAccedi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((!textUser.getText().equals("")) && (!passwordField.getText().equals(""))) {
					Operatore operatore = new Operatore();
					
					operatore.setUserName(textUser.getText());
					char[] passMom = passwordField.getPassword();
					String controll = "";
					for(int i = 0; i < passMom.length; i++) {
						controll += passMom[i];
					}
                 
						operatore.setPassword(controll); 
					

					try {
						int flagAcc = GestionaleBussiness.getInstance().access(operatore);  //query al database
						if(flagAcc > 0) {
							textUser.setText("");
							passwordField.setText("");
							setVisible(false);
							
							//creo il JText utente attivi da far vedere nella Home, non trovo altre soluzioni
							
							JTextField textField = new JTextField();
							textField.setBackground(new Color(176, 224, 230));
							textField.setEditable(false);
							textField.setBounds(1038, 10, 86, 20);
							frames.getContentPane().add(textField);
							textField.setColumns(10);
							textField.setText(operatore.getUserName());
						  
						    opt.setPermessi(operatore.getPermessi());
							frames.setVisible(true);
							
					   }
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null,"Nome Utente, o Password errati","Attenzione",JOptionPane.WARNING_MESSAGE);
					}


				}
				else {
					JOptionPane.showMessageDialog(null, "Campo UserName o Password non inserito",
							"attenzione", JOptionPane.WARNING_MESSAGE);
				}
			}	
		});

		btnAccedi.setBounds(375, 329, 89, 23);
		getContentPane().add(btnAccedi);
		//////////////////////////////////////////////////////////////

	}
}
