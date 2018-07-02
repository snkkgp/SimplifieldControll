package it.programma.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import it.programma.bussiness.GestionaleBussiness;
import it.programma.modell.Operatore;

import javax.sql.RowSetWriter;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class VisualUser extends JFrame {

	private JButton btnSalva;
	private JRadioButton rdbAdmni;
	private JRadioButton rdbOperatore;
	private JPanel contentPane;
	private JTextField textUserName;
	private JTextField textPassword;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					VisualUser frame = new VisualUser();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public VisualUser() throws HeadlessException {
		super();
	}


	/**
	 * Create the frame.
	 */
	public VisualUser(List<Operatore> user,int rowTable,Operatore op) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(350, 100, 523, 362);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setLocationRelativeTo(null); //mi fa comaprire la finestra sempre a centro schermo
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setForeground(new Color(0, 0, 0));
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(10, 11, 497, 311);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btnAnnulla = new JButton("Annulla");
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);

			}
		});
		btnAnnulla.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAnnulla.setForeground(Color.BLUE);
		btnAnnulla.setBounds(288, 277, 89, 23);
		panel.add(btnAnnulla);


		//////////////aggionrna Dati utente///////////////////
		btnSalva = new JButton("Aggiorna");
		btnSalva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if((!textUserName.getText().equals("")) && (!textPassword.getText().equals("")) 
						&& (rdbAdmni.isSelected() || rdbOperatore.isSelected()) ) {	

					Operatore opt = new Operatore();

					String controllPass = textPassword.getText();
					opt.setUserName(textUserName.getText());

					if(rdbAdmni.isSelected()) {
						opt.setPermessi("amministratore");
					}
					else if(rdbOperatore.isSelected()) {
						opt.setPermessi("operatore");
					}

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
					if(passNotNull == true) { ////////////insert

						try {
							int id = user.get(rowTable).getId();
							int flagUpdate = 0;
							flagUpdate = GestionaleBussiness.getInstance().refreshDati(opt,id);

							if(flagUpdate > 0) {

								JOptionPane.showMessageDialog(panel, "Utente Aggiornato con Successo");
								textUserName.setText("");
								textPassword.setText("");
								setVisible(false);

							}
							else {
								JOptionPane.showMessageDialog(null, "UserName gia in uso", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}

					}

				}
				else {
					JOptionPane.showMessageDialog(null, "Specificare tutti i campi richiesti!",
							"attenzione", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnSalva.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalva.setForeground(Color.RED);
		btnSalva.setBounds(398, 277, 89, 23);
		panel.add(btnSalva);

		JLabel lblDatiOperatore = new JLabel("Dati Operatore");
		lblDatiOperatore.setForeground(new Color(100, 149, 237));
		lblDatiOperatore.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDatiOperatore.setBounds(193, 11, 129, 23);
		panel.add(lblDatiOperatore);

		JLabel lblUsarname = new JLabel("UsarName");
		lblUsarname.setForeground(new Color(0, 128, 0));
		lblUsarname.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsarname.setBounds(55, 65, 72, 14);
		panel.add(lblUsarname);

		textUserName = new JTextField();
		textUserName.setToolTipText("UserName non modificabile");
		textUserName.setEditable(false);
		textUserName.setBackground(new Color(255, 218, 185));
		textUserName.setBounds(193, 63, 114, 20);
		panel.add(textUserName);
		textUserName.setColumns(10);
		textUserName.setText(user.get(rowTable).getUserName());

		textPassword = new JTextField();
		textPassword.setBackground(new Color(255, 218, 185));
		textPassword.setBounds(193, 126, 114, 20);
		panel.add(textPassword);
		textPassword.setColumns(10);

		textPassword.setText(user.get(rowTable).getPassword()); 

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(0, 128, 0));
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPassword.setBounds(55, 129, 72, 14);
		panel.add(lblPassword);

		JLabel lblManzione = new JLabel("Manzione ");
		lblManzione.setForeground(new Color(255, 0, 0));
		lblManzione.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblManzione.setBounds(55, 193, 72, 14);
		panel.add(lblManzione);

		rdbAdmni = new JRadioButton("Admin");
		rdbAdmni.setBackground(new Color(192, 192, 192));
		rdbAdmni.setFont(new Font("Tahoma", Font.BOLD, 13));
		rdbAdmni.setForeground(new Color(0, 0, 0));
		rdbAdmni.setBounds(166, 190, 109, 23);
		panel.add(rdbAdmni);

		rdbOperatore = new JRadioButton("operatore");
		rdbOperatore.setBackground(new Color(192, 192, 192));
		rdbOperatore.setFont(new Font("Tahoma", Font.BOLD, 13));
		rdbOperatore.setBounds(288, 190, 109, 23);
		panel.add(rdbOperatore);


		////creazione buttonGroup////
		ButtonGroup btn = new ButtonGroup();
		btn.add(rdbAdmni);
		btn.add(rdbOperatore);

		/////////////gestione azioni tasti in base ai permessi////////////////////

		if(user.get(rowTable).getPermessi().equals("amministratore")) {
			rdbAdmni.setSelected(true);
		}

		if(user.get(rowTable).getPermessi().equals("operatore")) {
			rdbOperatore.setSelected(true);
		}

		if(op.getPermessi().equals("operatore")){

			textUserName.setEditable(false);
			textPassword.setEditable(false);
			textPassword.setText("*********");
			btnSalva.setEnabled(false);
			rdbAdmni.setEnabled(false);
			rdbOperatore.setEnabled(false);
		}	

	}
}
