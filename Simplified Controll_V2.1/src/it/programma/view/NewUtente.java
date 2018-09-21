package it.programma.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import it.programma.bussiness.GestionaleBussiness;
import it.programma.modell.Operatore;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class NewUtente extends JFrame {

	private JPanel contentPane;
	private JTextField textUser;
	private JTextField textPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewUtente frame = new NewUtente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewUtente() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 497, 399);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setVisible(false);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(10, 11, 461, 338);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblCreaNuovoUtente = new JLabel("Crea Nuovo Utente:");
		lblCreaNuovoUtente.setForeground(new Color(0, 100, 0));
		lblCreaNuovoUtente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCreaNuovoUtente.setBounds(164, 11, 145, 14);
		panel.add(lblCreaNuovoUtente);

		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsername.setBounds(75, 80, 80, 21);
		panel.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(75, 150, 80, 21);
		panel.add(lblPassword);

		textUser = new JTextField();
		textUser.setBounds(209, 82, 120, 20);
		panel.add(textUser);
		textUser.setColumns(10);


		textPassword = new JTextField();
		textPassword.setToolTipText("Password ammessa; MIN 8 caratteri, di cui uno Maiuscolo, uno Minuscolo ed un Numero");
		textPassword.setBounds(209, 152, 120, 20);
		panel.add(textPassword);
		textPassword.setColumns(10);

		JLabel lblCreaCome = new JLabel("Crea come:");
		lblCreaCome.setForeground(new Color(0, 0, 0));
		lblCreaCome.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCreaCome.setBounds(197, 204, 101, 14);
		panel.add(lblCreaCome);

		JRadioButton rdbtnAmministartore = new JRadioButton("Amministartore");
		rdbtnAmministartore.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnAmministartore.setForeground(new Color(255, 0, 0));
		rdbtnAmministartore.setBackground(new Color(192, 192, 192));
		rdbtnAmministartore.setBounds(108, 238, 120, 23);
		panel.add(rdbtnAmministartore);

		JRadioButton rdbtnOperatore = new JRadioButton("Operatore");
		rdbtnOperatore.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnOperatore.setForeground(new Color(0, 100, 0));
		rdbtnOperatore.setBackground(new Color(192, 192, 192));
		rdbtnOperatore.setBounds(272, 238, 109, 23);
		panel.add(rdbtnOperatore);

		///creazione button Group//////////////

		ButtonGroup btn = new ButtonGroup();
		btn.add(rdbtnAmministartore);
		btn.add(rdbtnOperatore);

		///////////////tatsto annulla////////
		JButton btnAnnulla = new JButton("Annulla");
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textUser.setText("");
				textPassword.setText("");
				setVisible(false);
			}
		});
		btnAnnulla.setBounds(240, 304, 89, 23);
		panel.add(btnAnnulla);

		/////Logica tasto salvataggio////////////////////
		JButton btnSalva = new JButton("Salva");
		btnSalva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if((!textUser.getText().equals("")) && (!textPassword.getText().equals("")) 
						&& (rdbtnAmministartore.isSelected() || rdbtnOperatore.isSelected()) )  {

					Operatore opt = new Operatore();
					opt.setUserName(textUser.getText());

					if(rdbtnAmministartore.isSelected()) {
						opt.setPermessi("amministratore");
					}
					else if (rdbtnOperatore.isSelected()) {
						opt.setPermessi("operatore");
					}

					String controllPass = textPassword.getText();
					String passRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";  //minimo 8 caratteri almeno un carattere Maius,uno minuscolo,un number
					Pattern pattern = Pattern.compile(passRegex);
					Matcher matcher = pattern.matcher(controllPass);
					boolean passNotNull = false;        ////mi fa partire la query solo se la password non è null
					if(matcher.matches()) {
						opt.setPassword(controllPass);
						passNotNull = true;
					}
					else {
						JOptionPane.showMessageDialog(null,"Formato Password non Ammessa", "Attenzione",JOptionPane.INFORMATION_MESSAGE);
					}

					if(passNotNull == true) {
						try {
							int flag  = GestionaleBussiness.getInstance().newUser(opt);
							if(flag > 0) {
								JOptionPane.showMessageDialog(panel, "Utente Creato con Successo");
								textUser.setText("");
								textPassword.setText("");
							
							   
								setVisible(false);
								
								
								
							}
							else {
								JOptionPane.showMessageDialog(null, "UserName gia in uso", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (java.sql.SQLException e1 ) {
							e1.printStackTrace();

						}
					}	

				}
				else {
					JOptionPane.showMessageDialog(null, "Specificare tutti i campi richiesti!",
							"attenzione", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnSalva.setBounds(362, 304, 89, 23);
		panel.add(btnSalva);
	}
}
