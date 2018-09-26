package it.programma.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import it.programma.bussiness.GestionaleBussiness;
import it.programma.modell.Operatore;

public class AccountPage extends JFrame {

	private List<Operatore> user;              //lista che contine gli oparatori nel db
	private JPanel contentPane;
	private JTable tabellaUtenti;
	private JButton btnCreaUtente;
	private int column;      ///riga e sotto colonna che servono per capire se la casella della tabella utenti è selezionata
	private int riga;
	private Operatore opt;
	private boolean casella;
	private NewUtente page;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountPage frame = new AccountPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	public AccountPage() throws HeadlessException {
		super();
	}



	/**
	 * Create the frame.
	 */
	public AccountPage(JFrame home,Operatore op){
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setBounds(100, 100, 634, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 239, 213));
		panel.setBounds(10, 11, 598, 430);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel utentiAttivi = new JLabel("Utenti Attivi");
		utentiAttivi.setForeground(new Color(0, 128, 0));
		utentiAttivi.setFont(new Font("Tahoma", Font.BOLD, 16));
		utentiAttivi.setBounds(237, 29, 123, 26);
		panel.add(utentiAttivi);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 67, 578, 152);
		panel.add(scrollPane);
		////////////////////////tabella/////////////////////
		tabellaUtenti = new JTable();
		tabellaUtenti.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tabellaUtenti.setBackground(new Color(192, 192, 192));
		tabellaUtenti.setFillsViewportHeight(true);
		tabellaUtenti.setModel(new DefaultTableModel(

				new Object[][] {

				},
				new String[] {
						"Utente", "Permessi", "Seleziona"
				}
				) {
			Class[] columnTypes = new Class[] {
					String.class, Object.class, Boolean.class
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}


			boolean[] columnEditables = new boolean[] {
					false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}


		});
		tabellaUtenti.getColumnModel().getColumn(0).setPreferredWidth(155);
		tabellaUtenti.getColumnModel().getColumn(1).setPreferredWidth(168);
		tabellaUtenti.getColumnModel().getColumn(2).setPreferredWidth(194);
		tabellaUtenti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tabellaUtenti);

		/////////////tatsto ritorna///////////////////
		JButton btnRitorna = new JButton("Indietro");
		btnRitorna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

				home.setVisible(true);

			}
		});
		btnRitorna.setBounds(10, 385, 89, 23);
		panel.add(btnRitorna);



		btnCreaUtente = new JButton("Crea Utente");
		btnCreaUtente.setForeground(new Color(0, 128, 0));
		btnCreaUtente.addActionListener(new ActionListener() {
			////Creazione Nuovo Utente////////////////////////////////////		
			public void actionPerformed(ActionEvent arg0) {

				page = new NewUtente();
				page.setVisible(true);
			}
		});

		btnCreaUtente.setBounds(483, 385, 105, 23);
		panel.add(btnCreaUtente);
		////////////////////rilevo selezione riga e colonna tabella////////////////
		tabellaUtenti.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {

				riga = e.getFirstRow();
				column= e.getColumn();

			}
		});

		visualizzaUtente(btnCreaUtente); //visualizzo gli user nella tabella:


		//////////////elimina utente///////////////////////////////////////////////
		JButton btnNewButton = new JButton("Elimina");
		btnNewButton.setForeground(Color.RED);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int scelataJoption = 0;
				DefaultTableModel dtm  = (DefaultTableModel) tabellaUtenti.getModel();
				if(riga > 0 && column > 0) {                                                        //controlla che almeno una riga abbia il flag
					casella = Boolean.valueOf(tabellaUtenti.getValueAt(riga, column).toString());  //mi permette di eliminare la riga tabella solo se ho il flag in casella
					if(casella == true ) {                                                         //JoptionPane che mi chiede se sono sicuro dellam scelta che sto facedo
						scelataJoption = JOptionPane.showConfirmDialog(null, "Eliminare Utente?");
						if(scelataJoption == 0) {
							try {
								GestionaleBussiness.getInstance().deleteUser(user, riga);
								casella = false;
								dtm.setRowCount(0);                                                //cancello tabella
								setVisible(false);                                                 //nascondo la finestra

							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if(scelataJoption == 2) {
							casella = false;
							dtm.setRowCount(0); 

						}
					}

					try {
						dtm.setRowCount(0); 
						user = GestionaleBussiness.getInstance().getUserList();            //rifaccio la query aggiornando la tabella utenti
						for(Operatore opt : user) {
							Vector rowData = new Vector();

							rowData.add(opt.getUserName());
							rowData.add(opt.getPermessi());
							dtm.addRow(rowData);
							setVisible(true);                                             //rendo la tabella nuovamente visibile
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Selezionare l'Utente da Eliminare", 
							"Attenzione", JOptionPane.WARNING_MESSAGE);
				}

			}	
		});

		btnNewButton.setBounds(384, 385, 89, 23);
		panel.add(btnNewButton);

		JButton btnAggiorna = new JButton("Aggiorna");
		btnAggiorna.addActionListener(new ActionListener() {

			//////aggiorna tabella user dopo modifiche//////////////		
			public void actionPerformed(ActionEvent arg0) {
				try {
					user = GestionaleBussiness.getInstance().getUserList();
				} catch (SQLException e) {

					e.printStackTrace();
				}

				DefaultTableModel dtm = (DefaultTableModel) tabellaUtenti.getModel();
				dtm.setRowCount(0);

				for(Operatore o : user) {
					Vector rowData = new Vector();

					rowData.add(o.getUserName());
					rowData.add(o.getPermessi());
					dtm.addRow(rowData);
					tabellaUtenti.setVisible(true);                                       //rendo la tabella nuovamente visibile
				}
			}
		});
		btnAggiorna.setBounds(249, 252, 89, 23);
		panel.add(btnAggiorna);
		////////////////////////////visualizza gli utenti nel dettaglio////////////////////////////

		tabellaUtenti.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowTable = 0;
				if(e.getClickCount() == 2) {

					DefaultTableModel dm = (DefaultTableModel) tabellaUtenti.getModel();

					JTable target = (JTable) e.getSource();
					rowTable =  target.getSelectedRow();                        ///evidenzia riga selezionata
					VisualUser paginaUser = new VisualUser(user,rowTable,op);
					paginaUser.setVisible(true);



				}
			}
		});


		if(op.getPermessi().equals("operatore")) {
			btnCreaUtente.setEnabled(false);
		}

	}


	/////////////////////visualizza Utenti attivi in tabella////////////////

	public void visualizzaUtente(JButton btnCreaUtente) {

		DefaultTableModel dtm = (DefaultTableModel) tabellaUtenti.getModel();

		try {
			user = GestionaleBussiness.getInstance().getUserList();
			for(Operatore opt : user) {

				Vector rowData = new Vector();

				rowData.add(opt.getUserName());
				rowData.add(opt.getPermessi());

				dtm.addRow(rowData);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
}
