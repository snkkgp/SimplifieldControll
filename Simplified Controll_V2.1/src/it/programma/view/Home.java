package it.programma.view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.omg.CORBA.portable.Delegate;

import it.programma.bussiness.GestionaleBussiness;
import it.programma.modell.Operatore;
import it.programma.modell.riscontro.Riscontro;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;

public class Home {
	private AccountPage frame1;
	private Login login; 
	private JFrame frame;
	Operatore opt = new Operatore() ;

	private VisualRiscontro paginaRiscontro;
	private List<Riscontro> riscontri;// = new ArrayList<Riscontro>();
	private List<Riscontro> notReceived;
	private JTextField textField;
	private JTextField textDay;
	private JTextField textMonth;
	private JTextField textFieldYear;
	private JTextField textFieldCliente;
	private JTextField textFieldTarga;
	private JTextField textFieldImporto;
	private JTextField textFieldRicercaTarga;
	private JTextField textRIcercaImporto;
	private JTextField textFieldDD;
	private JTextField textFieldMM;
	private JTextField textFieldAAAA;
	private ButtonGroup btn;
	private ButtonGroup btncampoRicerca;
	private JTable tableRiscontri;
	private DefaultTableModel dtm;
	private DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	private JRadioButton rdbtnPostale;
	private JRadioButton rdbtnPos;
	private JRadioButton rdbtnBonifico;
	private JRadioButton radioButtonBonif;
	private JTextField texttotale;
	private int colonnaDel;
	private int riga;
	private int colonna;
	private String casuale = null;
	private boolean riscontroBool; 
	private String pathFolder = "C:\\Users\\Donato\\Desktop\\Riscontri\\";
	private String nomeFile;
	private double totalChiusura = 0.0;
	private JButton buttonStampa1;
	private JTextField textFieldServerIp;
	private JTextField textFieldPortaNum;
	private JTextField textField_2Usermysql;
	private JTextField textField_PassMysql;
	private JTextField textField_1Path;
	private JButton btnSalva_1;
	private int progrNumber = 0;
	private int row = -1;
	private JTable warningTable;
	private JScrollPane scrollPane2;
	private JCheckBox chechNotReented;
	private JCheckBox chckbxNewCheckBox;
	private JButton selectAll;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Home window = new Home();

					//window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();

				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Home() {
		initialize();
		try {
			FileReader reader = new FileReader("C:\\fileConfig\\file.properties");

			Properties pro = new Properties();
			pro.load(reader);
			textFieldServerIp.setText((pro.getProperty("ipserver")));
			textFieldPortaNum.setText((pro.getProperty("numberport")));
			textField_2Usermysql.setText((pro.getProperty("usernameDB")));
			textField_PassMysql.setText((pro.getProperty("passwordDB")));
			textField_1Path.setText((pro.getProperty("CartellaRiscontri")));

			JPopupMenu popupMenu = new JPopupMenu();
			addPopup(textField_1Path, popupMenu);

			JMenuItem mntmIncolla_2 = new JMenuItem("Incolla");
			mntmIncolla_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					textField_1Path.paste();
				}
			});
			popupMenu.add(mntmIncolla_2);

			JMenuItem mntmCopia_2 = new JMenuItem("Copia");
			mntmCopia_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textField_1Path.copy();
				}
			});
			popupMenu.add(mntmCopia_2);

			JLabel lblWarningTable = new JLabel("WARNING TABLE");
			lblWarningTable.setToolTipText("");
			lblWarningTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblWarningTable.setForeground(Color.RED);
			lblWarningTable.setBounds(965, 67, 122, 14);
			frame.getContentPane().add(lblWarningTable);



			try {
				notReceived = GestionaleBussiness.getInstance().notReturned();
				printNotReceived();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}	

		} catch (FileNotFoundException e) {


		} catch (IOException e) {
			e.printStackTrace();
		}



	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().setBackground(new Color(192, 192, 192));
		frame.setBounds(50, 100, 1224, 775);

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JLabel lblBenvenuto = new JLabel("Benvenuto");
		lblBenvenuto.setBounds(955, 11, 75, 14);
		lblBenvenuto.setForeground(new Color(0, 128, 0));
		lblBenvenuto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.getContentPane().add(lblBenvenuto);
		//////////////////////////////////////////////L0gout
		JButton btnEsci = new JButton("Esci");
		btnEsci.setBounds(1145, 9, 63, 23);
		btnEsci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frame.setVisible(false);
				login.setVisible(true);
				dtm = (DefaultTableModel) tableRiscontri.getModel();

				clearInsertRiscontro();
				clearRicercaRiscontro();

			}
		});



		frame.getContentPane().add(btnEsci);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 250, 240));
		panel.setBounds(78, 11, 705, 272);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(224, 255, 255));
		tabbedPane.setBounds(10, 11, 685, 255);
		panel.add(tabbedPane);

		JPanel panel_1 = new JPanel();

		panel_1.setBackground(new Color(169, 169, 169));
		tabbedPane.addTab("Ricerca", null, panel_1, null);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("Ricerca Riscontro per:");
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 168, 19);
		panel_1.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Targa ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(185, 12, 57, 19);
		panel_1.add(lblNewLabel_1);

		textFieldRicercaTarga = new JTextField();
		textFieldRicercaTarga.setBackground(new Color(176, 224, 230));
		textFieldRicercaTarga.setBounds(291, 12, 97, 20);
		panel_1.add(textFieldRicercaTarga);
		textFieldRicercaTarga.setColumns(10);

		JPopupMenu popupMenu_1 = new JPopupMenu();
		addPopup(textFieldRicercaTarga, popupMenu_1);

		JMenuItem mntmIncolla_1 = new JMenuItem("Incolla");
		mntmIncolla_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldRicercaTarga.paste();

			}
		});
		popupMenu_1.add(mntmIncolla_1);

		JMenuItem mntmCopia_1 = new JMenuItem("Copia");
		mntmCopia_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldRicercaTarga.copy();
			}
		});
		popupMenu_1.add(mntmCopia_1);

		JLabel lblImporto = new JLabel("Importo");
		lblImporto.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblImporto.setBounds(185, 61, 71, 14);
		panel_1.add(lblImporto);

		textRIcercaImporto = new JTextField();
		textRIcercaImporto.setToolTipText("Per la parte Decimale usare il Punto");
		textRIcercaImporto.setBackground(new Color(176, 224, 230));
		textRIcercaImporto.setBounds(291, 61, 97, 20);
		panel_1.add(textRIcercaImporto);
		textRIcercaImporto.setColumns(10);

		JPopupMenu popupMenu_2 = new JPopupMenu();
		addPopup(textRIcercaImporto, popupMenu_2);

		JMenuItem mntmIncolla_3 = new JMenuItem("Incolla");
		mntmIncolla_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textRIcercaImporto.paste();
			}
		});
		popupMenu_2.add(mntmIncolla_3);

		JMenuItem mntmCopia_3 = new JMenuItem("Copia");
		mntmCopia_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textRIcercaImporto.copy();
			}
		});
		popupMenu_2.add(mntmCopia_3);

		JLabel lblNewLabel_3 = new JLabel("Data Inserimento");
		lblNewLabel_3.setToolTipText("Clicca per settare campo con data Odierna");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LocalDate data = LocalDate.now();
				textFieldDD.setText(String.valueOf(data.getDayOfMonth()));
				textFieldMM.setText(String.valueOf(data.getMonthValue()));
				textFieldAAAA.setText(String.valueOf(data.getYear()));
			}
		});
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3.setBounds(151, 103, 119, 14);
		panel_1.add(lblNewLabel_3);

		textFieldDD = new JTextField();
		textFieldDD.setBackground(new Color(176, 224, 230));
		textFieldDD.setBounds(291, 101, 23, 20);
		panel_1.add(textFieldDD);
		textFieldDD.setColumns(10);

		textFieldMM = new JTextField();
		textFieldMM.setBackground(new Color(176, 224, 230));
		textFieldMM.setBounds(324, 101, 23, 20);
		panel_1.add(textFieldMM);
		textFieldMM.setColumns(10);

		textFieldAAAA = new JTextField();
		textFieldAAAA.setBackground(new Color(176, 224, 230));
		textFieldAAAA.setForeground(new Color(0, 0, 0));
		textFieldAAAA.setBounds(357, 101, 46, 20);
		panel_1.add(textFieldAAAA);
		textFieldAAAA.setColumns(10);


		//////////////////////////azione tasto cerca riscontri/////////////////////////
		JButton btnRicerca = new JButton("Ricerca");
		btnRicerca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				buttonStampa1.setEnabled(false);
				selectAll.setEnabled(false);
				boolean textEmpy = false;
				boolean errDate = false;

				if((textFieldRicercaTarga.getText().equals("")) && (textRIcercaImporto.getText().equals(""))
						&& (textFieldDD.getText().equals("")) && (textFieldMM.getText().equals("")) 
						&& (textFieldAAAA.getText().equals("") && (!chechNotReented.isSelected())) ) {	
					JOptionPane.showMessageDialog(null,"Compilare campo Targa, o campo Importo, o Data e tipo Riscontro, o solo"
							+ " il campo riscontro ",
							"Attenzione",JOptionPane.WARNING_MESSAGE);
					textEmpy = true;
				}

				Riscontro riscontro = new Riscontro();


				riscontro.setTarga_Polizza(textFieldRicercaTarga.getText());



				try {
					if(!textRIcercaImporto.getText().equals("")) {
						riscontro.setImporto(Double.parseDouble(textRIcercaImporto.getText()));
					}
				}
				catch(java.lang.NumberFormatException ext) {
					JOptionPane.showMessageDialog(null,"Valore Importo non Valido, Virogola o caratteri numerici non ammessi, ",
							"Attenzione",JOptionPane.INFORMATION_MESSAGE);

				}


				if(rdbtnPos.isSelected()) {
					riscontro.setTipoPagamento("pos");
				}
				else if(rdbtnPostale.isSelected()) {
					riscontro.setTipoPagamento("postale");
				}
				else if(rdbtnBonifico.isSelected()) {
					riscontro.setTipoPagamento("bonifico");
				}

				String gg = "";
				String mm = "";
				String yyyy = "";
				gg = textFieldDD.getText().trim();
				mm = textFieldMM.getText().trim();
				yyyy = textFieldAAAA.getText().trim();


				if((!gg.equals("")) && (!mm.equals("")) && (!yyyy.equals(""))) {	 
					try {

						LocalDate datIns1 =  LocalDate.of(Integer.parseInt(yyyy),Integer.parseInt(mm),Integer.parseInt(gg));
						riscontro.setDataInserimento(datIns1);

					}
					catch(java.time.DateTimeException ext) {

						JOptionPane.showMessageDialog(null,"Formato Data Inserita non valida",

								"Errore",JOptionPane.INFORMATION_MESSAGE);
						errDate = true;
					}

				}

				if(textEmpy != true && errDate != true) {
					try {

						riscontri = GestionaleBussiness.getInstance().ricerca(riscontro,chechNotReented);
						if(!riscontri.isEmpty()) {
							printRicerca();
							buttonStampa1.setEnabled(true);
							selectAll.setEnabled(true);
						}
						else {
							JOptionPane.showMessageDialog(null,"Nessuna Riscontro trovato!",
									"Attenzione",JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}


			}
		});
		btnRicerca.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRicerca.setForeground(new Color(0, 100, 0));
		btnRicerca.setBounds(569, 193, 89, 23);
		panel_1.add(btnRicerca);


		/////////////////////////////////////////cancella campi ricerca////////////////
		JButton btnAnnullaRiscCrea = new JButton("Annulla");
		btnAnnullaRiscCrea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonStampa1.setEnabled(false);
				selectAll.setEnabled(false);
				clearRicercaRiscontro();

				try {
					notReceived = GestionaleBussiness.getInstance().notReturned();
					printNotReceived();
				} catch (SQLException e1) {

					e1.printStackTrace();
				}	
			}
		});
		btnAnnullaRiscCrea.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAnnullaRiscCrea.setForeground(new Color(255, 0, 0));
		btnAnnullaRiscCrea.setBounds(446, 193, 89, 23);
		panel_1.add(btnAnnullaRiscCrea);

		JLabel lblTipoRiscontro = new JLabel("Tipo Riscontro");
		lblTipoRiscontro.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTipoRiscontro.setBounds(514, 14, 103, 14);
		panel_1.add(lblTipoRiscontro);

		rdbtnPostale = new JRadioButton("Postale");
		rdbtnPostale.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnPostale.setForeground(Color.YELLOW);
		rdbtnPostale.setBackground(new Color(169, 169, 169));
		rdbtnPostale.setBounds(463, 35, 71, 23);
		panel_1.add(rdbtnPostale);

		rdbtnPos = new JRadioButton("Pos");
		rdbtnPos.setForeground(new Color(0, 0, 205));
		rdbtnPos.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnPos.setBackground(new Color(169, 169, 169));
		rdbtnPos.setBounds(536, 35, 57, 23);
		panel_1.add(rdbtnPos);
		tabbedPane.setEnabledAt(0, true);



		rdbtnBonifico = new JRadioButton("Bonifico");
		rdbtnBonifico.setForeground(new Color(0, 128, 0));
		rdbtnBonifico.setBackground(new Color(169, 169, 169));
		rdbtnBonifico.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnBonifico.setBounds(595, 35, 79, 23);
		panel_1.add(rdbtnBonifico);

		///Creazione ButtonGroup campo Ricerca
		btncampoRicerca = new ButtonGroup();
		btncampoRicerca.add(rdbtnBonifico);
		btncampoRicerca.add(rdbtnPostale);
		btncampoRicerca.add(rdbtnPos);

		chechNotReented = new JCheckBox("Non Accreditati");
		chechNotReented.setFont(new Font("Tahoma", Font.BOLD, 11));
		chechNotReented.setBounds(291, 144, 112, 23);
		panel_1.add(chechNotReented);
		chechNotReented.setBackground(new Color(169, 169, 169));

		JPanel panel_2 = new JPanel();

		panel_2.setBackground(new Color(169, 169, 169));
		tabbedPane.addTab("Crea", null, panel_2, null);
		tabbedPane.setEnabledAt(1, true);
		panel_2.setLayout(null);

		JRadioButton rdbPostale = new JRadioButton("Postale");
		rdbPostale.setBackground(new Color(169, 169, 169));
		rdbPostale.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbPostale.setForeground(Color.YELLOW);
		rdbPostale.setBounds(27, 52, 79, 23);
		panel_2.add(rdbPostale);

		JLabel lblTipoDiRiscontro = new JLabel("Tipo di Riscontro");
		lblTipoDiRiscontro.setForeground(new Color(0, 0, 0));
		lblTipoDiRiscontro.setBackground(new Color(0, 0, 0));
		lblTipoDiRiscontro.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTipoDiRiscontro.setBounds(72, 26, 109, 19);
		panel_2.add(lblTipoDiRiscontro);

		JRadioButton rdbPos = new JRadioButton("Pos");
		rdbPos.setBackground(new Color(169, 169, 169));
		rdbPos.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbPos.setForeground(new Color(0, 0, 255));
		rdbPos.setBounds(108, 52, 45, 23);
		panel_2.add(rdbPos);


		radioButtonBonif = new JRadioButton("Bonifico");
		radioButtonBonif.setForeground(new Color(0, 128, 0));
		radioButtonBonif.setFont(new Font("Tahoma", Font.BOLD, 11));
		radioButtonBonif.setBackground(new Color(169, 169, 169));
		radioButtonBonif.setBounds(167, 52, 69, 23);
		panel_2.add(radioButtonBonif);

		/////////creazione ButtonGroup//////////////////////////

		btn = new ButtonGroup();
		btn.add(rdbPos);
		btn.add(rdbPostale);
		btn.add(radioButtonBonif);

		//////////////////////////////////////////////////////

		JLabel lblDataInserimento = new JLabel("Data Inserimento");
		lblDataInserimento.setToolTipText("Clicca per settare campo con data Odierna");
		lblDataInserimento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				LocalDate data = LocalDate.now();
				textDay.setText(String.valueOf(data.getDayOfMonth()));
				textMonth.setText(String.valueOf(data.getMonthValue()));
				textFieldYear.setText(String.valueOf(data.getYear()));
			}
		});
		lblDataInserimento.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDataInserimento.setBounds(379, 28, 118, 14);
		panel_2.add(lblDataInserimento);

		textDay = new JTextField();
		textDay.setBackground(new Color(176, 224, 230));
		textDay.setBounds(528, 26, 29, 20);
		panel_2.add(textDay);
		textDay.setColumns(2);



		textMonth = new JTextField();
		textMonth.setBackground(new Color(176, 224, 230));
		textMonth.setBounds(567, 26, 29, 20);
		panel_2.add(textMonth);
		textMonth.setColumns(2);


		textFieldYear = new JTextField();
		textFieldYear.setBackground(new Color(176, 224, 230));
		textFieldYear.setBounds(606, 26, 55, 20);
		panel_2.add(textFieldYear);
		textFieldYear.setColumns(4);


		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCliente.setBounds(378, 64, 55, 14);
		panel_2.add(lblCliente);

		textFieldCliente = new JTextField();
		textFieldCliente.setBackground(new Color(176, 224, 230));
		textFieldCliente.setBounds(528, 62, 144, 20);
		panel_2.add(textFieldCliente);
		textFieldCliente.setColumns(10);

		JLabel lblTarganPolizza = new JLabel("Targa/N\u00B0 Polizza");
		lblTarganPolizza.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTarganPolizza.setBounds(379, 93, 118, 20);
		panel_2.add(lblTarganPolizza);

		textFieldTarga = new JTextField();
		textFieldTarga.setBackground(new Color(176, 224, 230));
		textFieldTarga.setBounds(528, 93, 118, 20);
		panel_2.add(textFieldTarga);
		textFieldTarga.setColumns(10);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(textFieldTarga, popupMenu);

		JMenuItem mntmCopia = new JMenuItem("Copia");
		mntmCopia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldTarga.copy();

			}
		});
		popupMenu.add(mntmCopia);

		JMenuItem mntmIncolla = new JMenuItem("Incolla");
		mntmIncolla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldTarga.paste();
			}
		});
		popupMenu.add(mntmIncolla);
		///////////////////////////////////////////inserimento riscontro/////////////////////////////////////////////////////////////		
		JButton btnSalva = new JButton("Salva");
		btnSalva.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if(casuale != null && (rdbPos.isSelected() || rdbPostale.isSelected() || radioButtonBonif.isSelected()) && 
						(!textDay.getText().equals("")) && (!textMonth.getText().equals(""))
						&& (!textFieldYear.getText().equals("")) && (!textFieldCliente.getText().equals("")) 
						&& (!textFieldTarga.getText().equals("")) && (!textFieldImporto.getText().equals(""))) {
					Riscontro riscontro = new Riscontro();
					boolean errData = false,errImporto = false;
					if(rdbPos.isSelected()) {
						riscontro.setTipoPagamento("pos");
					}
					else if(rdbPostale.isSelected()) {
						riscontro.setTipoPagamento("postale");
					}
					else if(radioButtonBonif.isSelected()) {
						riscontro.setTipoPagamento("bonifico");
					}

					String gg = textDay.getText().trim();
					String mm = textMonth.getText().trim();
					String yyyy = textFieldYear.getText().trim().substring(0, 4);
					int data = Integer.parseInt(yyyy);

					if(data >= 2050 || data <=1900) {
						errData = true;
						JOptionPane.showMessageDialog(null,"Formato Data Inserimento non valido",
								"Errore",JOptionPane.INFORMATION_MESSAGE);
					}
					try {

						LocalDate datIns = LocalDate.of(Integer.parseInt(yyyy),Integer.parseInt(mm),Integer.parseInt(gg));
						riscontro.setDataInserimento(datIns);

					}
					catch(java.time.DateTimeException ext) {
						JOptionPane.showMessageDialog(null,"Formato Data Inserimento non valido",
								"Errore",JOptionPane.INFORMATION_MESSAGE);
						errData = true;
					}

					riscontro.setNominativo(textFieldCliente.getText().trim());
					riscontro.setTarga_Polizza(textFieldTarga.getText().trim());
					riscontro.setDescrizione(casuale);
					double importo = 0;
					boolean flagInvalidMessage = false;
					try {

						importo = Double.parseDouble(textFieldImporto.getText());
					}
					catch(java.lang.NumberFormatException ext) {
						JOptionPane.showMessageDialog(null,"Valore Importo non Valido, Virogola o caratteri AlfaNumerici non ammessi, ",
								"Attenzione",JOptionPane.INFORMATION_MESSAGE);
						errImporto = true;
						flagInvalidMessage = true;
					}

					if(importo > 0) {
						riscontro.setImporto(importo);
					}
					else if(flagInvalidMessage != true) {
						JOptionPane.showMessageDialog(null,"Il Campo Importo deve essere maggiore di Zero",
								"Attenzione",JOptionPane.INFORMATION_MESSAGE);
					}

					if(errImporto != true && errData != true) {
						try {
							int flagInsert = GestionaleBussiness.getInstance().InsertRiscontro(riscontro);
							if(flagInsert > 0 ) {
								JOptionPane.showMessageDialog(null,"Riscontro Inserito Correttamente, ",
										"Informazione",JOptionPane.INFORMATION_MESSAGE);
								clearInsertRiscontro();
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}	
				}
				else {
					JOptionPane.showMessageDialog(null,"Compilare tutti i Campi Richiesti",
							"Attenzione",JOptionPane.WARNING_MESSAGE);
				}

				try {
					notReceived = GestionaleBussiness.getInstance().notReturned();
					printNotReceived();
				} catch (SQLException e1) {

					e1.printStackTrace();
				}	
			}
		});
		btnSalva.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalva.setForeground(new Color(0, 100, 0));
		btnSalva.setBounds(572, 193, 89, 23);
		panel_2.add(btnSalva);
		///////////////////////////////Annulla inserimento riscontro/////////////////////////////////////////////////	

		JButton btnAnnulla = new JButton("Annulla");
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearInsertRiscontro();

			}
		});
		btnAnnulla.setForeground(new Color(255, 0, 0));
		btnAnnulla.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAnnulla.setBounds(443, 193, 89, 23);
		panel_2.add(btnAnnulla);


		JLabel lblNewLabel_2 = new JLabel("Importo");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(431, 141, 61, 14);
		panel_2.add(lblNewLabel_2);

		textFieldImporto = new JTextField();
		textFieldImporto.setBackground(new Color(176, 224, 230));
		textFieldImporto.setBounds(528, 139, 55, 20);
		panel_2.add(textFieldImporto);
		textFieldImporto.setColumns(10);

		///Jlist///////////////////////////////////////////

		DefaultListModel listModel = null;
		listModel = new DefaultListModel();
		listModel.addElement("Pagamento Polizza");
		listModel.addElement("Acconto Polizza");
		listModel.addElement("Saldo Polizza");
		listModel.addElement("Sostituzione Polizza");
		listModel.addElement("Sospensione Polizza");
		listModel.addElement("Polizza Professionale");
		listModel.addElement("Polizza Casa");
		listModel.addElement("Polizza Fideiussoria");
		listModel.addElement("Demolizione");
		listModel.addElement("Pratiche Aurora");
		listModel.addElement("Varie RCAuto ");


		JList list = new JList(listModel);
		list.setBounds(80, 121, 108, 62);
		list.setBackground(new Color(143,188,143));
		panel_2.add(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);

		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(80, 121, 144, 62);
		panel_2.add(scrollPane);

		//////logia di scelta campo casuale/////////////////////
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) {
					casuale =(String) list.getSelectedValue();

				}
			}
		});


		JLabel labelCasuale = new JLabel("Casuale Pagamento:");
		labelCasuale.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelCasuale.setBounds(82, 96, 154, 23);
		panel_2.add(labelCasuale);



		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(192, 192, 192));
		tabbedPane.addTab("Opzioni Server", null, panel_4, null);
		tabbedPane.setBackgroundAt(2, new Color(255, 255, 255));
		panel_4.setLayout(null);



		////gestione opzioni database in base ai Permessi//////////

		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(opt.getPermessi().equals("operatore")) {
					textFieldServerIp.setEditable(false);
					textFieldPortaNum.setEditable(false);
					textField_2Usermysql.setEditable(false);
					textField_PassMysql.setEditable(false);
					textField_1Path.setEditable(false);
					btnSalva_1.setEnabled(false);

				}

			}
		});



		JLabel lblIndirizzoIpServer = new JLabel("Indirizzo IP Server");
		lblIndirizzoIpServer.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblIndirizzoIpServer.setBounds(175, 23, 131, 26);
		panel_4.add(lblIndirizzoIpServer);

		textFieldServerIp = new JTextField();
		textFieldServerIp.setBackground(new Color(176, 224, 230));
		textFieldServerIp.setForeground(Color.BLACK);
		textFieldServerIp.setBounds(328, 27, 120, 20);
		panel_4.add(textFieldServerIp);
		textFieldServerIp.setColumns(10);


		JLabel lblPortaN = new JLabel("Porta");
		lblPortaN.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPortaN.setBounds(175, 60, 131, 26);
		panel_4.add(lblPortaN);

		textFieldPortaNum = new JTextField();
		textFieldPortaNum.setForeground(Color.BLACK);
		textFieldPortaNum.setColumns(10);
		textFieldPortaNum.setBackground(new Color(176, 224, 230));
		textFieldPortaNum.setBounds(328, 64, 51, 20);
		panel_4.add(textFieldPortaNum);

		JLabel lblUsernameMysql = new JLabel("UserName Mysql");
		lblUsernameMysql.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsernameMysql.setBounds(174, 97, 131, 26);
		panel_4.add(lblUsernameMysql);

		textField_2Usermysql = new JTextField();
		textField_2Usermysql.setForeground(Color.BLACK);
		textField_2Usermysql.setColumns(10);
		textField_2Usermysql.setBackground(new Color(176, 224, 230));
		textField_2Usermysql.setBounds(328, 101, 120, 20);
		panel_4.add(textField_2Usermysql);

		JLabel lblPasswordMysql = new JLabel("Password Mysql");
		lblPasswordMysql.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPasswordMysql.setBounds(175, 134, 131, 26);
		panel_4.add(lblPasswordMysql);

		textField_PassMysql = new JTextField();
		textField_PassMysql.setForeground(Color.BLACK);
		textField_PassMysql.setColumns(10);
		textField_PassMysql.setBackground(new Color(176, 224, 230));
		textField_PassMysql.setBounds(328, 138, 120, 20);
		panel_4.add(textField_PassMysql);

		JLabel lblPercorsoCartellaRiscontri = new JLabel("Path cartella Riscontri");
		lblPercorsoCartellaRiscontri.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPercorsoCartellaRiscontri.setBounds(175, 173, 155, 26);
		panel_4.add(lblPercorsoCartellaRiscontri);

		textField_1Path = new JTextField();
		textField_1Path.setForeground(Color.BLACK);
		textField_1Path.setColumns(10);
		textField_1Path.setBackground(new Color(176, 224, 230));
		textField_1Path.setBounds(328, 177, 213, 20);
		panel_4.add(textField_1Path);



		///Logica salvataggio dati e opzioni//////////////////////////////////////////
		btnSalva_1 = new JButton("Salva");
		btnSalva_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((!textFieldServerIp.getText().equals("")) && (!textFieldPortaNum.getText().equals("")) &&
						(!textField_2Usermysql.getText().equals("")) && (!textField_PassMysql.getText().equals("")) &&
						(!textField_1Path.getText().equals(""))) {

					GestionaleBussiness.getInstance().setServerIp(textFieldServerIp.getText());
					GestionaleBussiness.getInstance().setNumbertPort(Integer.parseInt(textFieldPortaNum.getText()));
					GestionaleBussiness.getInstance().setUserDatabase(textField_2Usermysql.getText());
					GestionaleBussiness.getInstance().setPasswordDatabase(textField_PassMysql.getText());
					GestionaleBussiness.getInstance().setCartellaRiscontri(textField_1Path.getText());


					try {
						Properties properties = new Properties();
						properties.setProperty("ipserver",GestionaleBussiness.getInstance().getServerIp());
						properties.setProperty("numberport",String.valueOf(GestionaleBussiness.getInstance().getNumbertPort()));
						properties.setProperty("usernameDB",GestionaleBussiness.getInstance().getUserDatabase());
						properties.setProperty("passwordDB",GestionaleBussiness.getInstance().getPasswordDatabase());
						properties.setProperty("CartellaRiscontri",GestionaleBussiness.getInstance().getCartellaRiscontri());
						properties.setProperty("project-name", "SimplifieldControll");
						properties.setProperty("Version", "V2.1 del 13/09/2018");




						FileWriter writer = new FileWriter("C:\\fileConfig\\file.properties");

						properties.store(writer,"autore:Tuzzolino Donato");
						writer.close();

					} catch (IOException e) {

						e.printStackTrace();
					}

					JOptionPane.showMessageDialog(null,"Configurazione Aggiornate Correttamente!, ",
							"Informazione",JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null,"Inserire tutti i campi!",
							"Attenzione",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnSalva_1.setBounds(581, 193, 89, 23);
		panel_4.add(btnSalva_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 250, 240));
		panel_3.setBounds(50, 299, 773, 279);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);

		//////////////tabella ricerca riscontri/////////////////////////////////////////////////		

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(10, 11, 753, 224);
		panel_3.add(scrollPane_1);

		tableRiscontri = new JTable();
		tableRiscontri.setToolTipText("");
		tableRiscontri.setBackground(new Color(143, 188, 143));
		tableRiscontri.setFillsViewportHeight(true);
		tableRiscontri.setModel(new DefaultTableModel(
				new Object[][] {

				},
				new String[] {
						"Data Inserimento", "Tipo Riscontro", "Nominativo", "Targa/Polizza N\u00B0","Casuale", "Importo", "Data Rientro", "Esito"
				}
				) {
			Class[] columnTypes = new Class[] {
					Object.class, String.class, String.class, String.class,String.class, Double.class, Object.class, Boolean.class
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			/////imposto colonne editabili
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false,false
			};

			///metodo che ritorna un array boelano e mi dice se la casella e selezionata o no
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableRiscontri.getColumnModel().getColumn(0).setPreferredWidth(101);
		tableRiscontri.getColumnModel().getColumn(1).setPreferredWidth(85);
		tableRiscontri.getColumnModel().getColumn(2).setPreferredWidth(130);
		tableRiscontri.getColumnModel().getColumn(3).setPreferredWidth(92);
		tableRiscontri.getColumnModel().getColumn(4).setPreferredWidth(130);
		tableRiscontri.getColumnModel().getColumn(7).setPreferredWidth(50);
		tableRiscontri.setForeground(new Color(0, 0, 0));
		scrollPane_1.setViewportView(tableRiscontri);

		JPopupMenu popupMenu_3 = new JPopupMenu();
		addPopup(tableRiscontri, popupMenu_3);
		JMenuItem mntmElimina =  new JMenuItem("Elimina");


		///////// elimina riscontro con tasto destro Mouse/////////////



		mntmElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int scelataJoption = 0;
				Integer deleteID = null;
				if(row >= 0) {
					deleteID = riscontri.get(row).getId();
					scelataJoption = JOptionPane.showConfirmDialog(null, "Eliminare Utente?");
					if(scelataJoption == 0) {

						try {

							GestionaleBussiness.getInstance().removeRiscontro(deleteID);
							dtm.setRowCount(0);
							JOptionPane.showMessageDialog(null,"Riscontro Eliminato con Sucesso!, ",
									"Informazione",JOptionPane.INFORMATION_MESSAGE);

						} 
						catch (SQLException e1) {
							e1.printStackTrace();
						}

					}
					if(scelataJoption == 2) {
						row = -1;
					}
					if(scelataJoption == 1) {
						row = -1;
					}



				}
				else if (row < 0) {
					JOptionPane.showMessageDialog(null,"Nessun Riscontro selezionato!",
							"Attenzione",JOptionPane.WARNING_MESSAGE);
				}
				row = -1;

			}
		});
		popupMenu_3.add(mntmElimina);

		////////////rilevo selezione della riga e colonna del tabella ricerca/////////////////

		tableRiscontri.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {

				riga = e.getFirstRow();
				colonna = e.getColumn();

			}
		});
		////////////////////Visualizza Dettaglio risocntro con doppio clic  /////////////////////////////////////////////	

		tableRiscontri.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable) e.getSource();
				row =  target.getSelectedRow();
				System.out.println(row);
				if(e.getClickCount() == 2) {
					int rowTable = 0;

					rowTable = target.getSelectedRow();
					colonnaDel = target.getSelectedColumn();
					VisualRiscontro paginaRiscontro = new VisualRiscontro(riscontri,rowTable);
					paginaRiscontro.setVisible(true);
					clearRicercaRiscontro();
					printNotReceived();
					
				}
						
			}
			
		});



		JLabel lblTotale = new JLabel("Totale Euro");
		lblTotale.setForeground(new Color(0, 128, 0));
		lblTotale.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotale.setBounds(432, 247, 86, 14);
		panel_3.add(lblTotale);

		texttotale = new JTextField();
		texttotale.setToolTipText("Totale  somma riscontri");
		texttotale.setFont(new Font("Tahoma", Font.PLAIN, 13));
		texttotale.setEditable(false);
		texttotale.setForeground(new Color(255, 0, 0));
		texttotale.setBackground(new Color(169, 169, 169));
		texttotale.setBounds(542, 244, 86, 20);
		panel_3.add(texttotale);
		texttotale.setColumns(10);
		
		
		selectAll = new JButton("Select All");
		selectAll.setEnabled(false);
		//// Segna tutti i riscontri come Accredidati/////////////
		
		selectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selection();
			}
		});
		
		selectAll.setToolTipText("Clicca per trasformare tutti i riscontri selezionati come Rientrati");
		selectAll.setBounds(670, 244, 93, 23);
		panel_3.add(selectAll);
		
		
		
		buttonStampa1 = new JButton("Stampa");
		buttonStampa1.setEnabled(false);
		
		/////logica tasto stampa////////////
		
		buttonStampa1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				File folder = new File(pathFolder);
				if(!folder.exists()) {
					folder.mkdir();
				}
				LocalDate data1 = riscontri.get(0).getDataInserimento();
				nomeFile ="Riscontro_" + riscontri.get(0).getTipoPagamento() + "_" + data1.format(dt);
				nomeFile = nomeFile.replaceAll("[\\W]",".");
				File file = null;
				if(riscontri.get(0).getTipoPagamento().equals("bonifico")) {
					progrNumber = riscontri.get(0).getId();
					file = new File( GestionaleBussiness.getInstance().getCartellaRiscontri() + nomeFile + "_" + progrNumber + ".doc");
				}
				else {
					file = new File( GestionaleBussiness.getInstance().getCartellaRiscontri() + nomeFile + ".doc");
				}


				if(!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException f) {
						f.printStackTrace();
					}

					PrintWriter pw = null;
					LocalDate data = riscontri.get(0).getDataInserimento();
					LocalDate dataRientro = riscontri.get(0).getDataRiscontro();


					try {
						pw = new PrintWriter(new FileWriter(file));
						pw.println("DATA RIENTRO: " + dataRientro.format(dt) + "\n\n\n");
						pw.println("\t\t\t\tPAGAMENTI " + riscontri.get(0).getTipoPagamento().toUpperCase() + " DEL "  + data.format(dt) );
						for(Riscontro rsc : riscontri) {


							pw.println();
							pw.println();
							pw.print(rsc.getNominativo().toUpperCase() + "     TARGA/POLIZZA N:   " + rsc.getTarga_Polizza() + "   " 
									+ String.format("%.2f",rsc.getImporto()) + " EURO");
							pw.println();


						}
						pw.print("\n\n\t\t\t\t\t\t\tTOTALE " + String.format("%.2f",totalChiusura)  + " EURO");
					} catch (IOException b) {
						b.printStackTrace();
					}
					finally {
						pw.close();
					}

					Desktop d = java.awt.Desktop.getDesktop();     
					try {
						d.open(file);                             //apro il file con Word
					} catch (IOException a) {

						a.printStackTrace();
					}
				}
				
			}
		});
		buttonStampa1.setBounds(308, 244, 89, 23);
		panel_3.add(buttonStampa1);



		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(0, 128, 0));
		frame.setJMenuBar(menuBar);

		JMenu mnOpzioni = new JMenu("Opzioni");
		menuBar.add(mnOpzioni);

		JMenu mnGestioneUtente = new JMenu("Gestione Utente");
		mnOpzioni.add(mnGestioneUtente);

		JMenuItem mntmAggiungi = new JMenuItem("Visualizza");
		mntmAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				frame1 = new AccountPage(frame,opt);    //////////////
				frame1.setVisible(true);
				frame1.setLocationRelativeTo(null);

			}
		});

		mnGestioneUtente.add(mntmAggiungi);

		login = new Login(frame,opt);
		login.setVisible(true);

		////////warningTble////////////////////////////////////////////


		JPanel panel_7 = new JPanel();
		panel_7.setBounds(814, 92, 394, 193);
		panel_7.setBackground(new Color(255, 250, 240));
		frame.getContentPane().add(panel_7);
		panel_7.setLayout(null);

		JScrollPane scrollPane2;
		scrollPane2 = new JScrollPane();
		scrollPane2.setToolTipText("Cerca tutti i pagamenti non ancora accreditati");
		scrollPane2.setBounds(10, 11, 374, 171);
		panel_7.add(scrollPane2);

		warningTable = new JTable();
		warningTable.setColumnSelectionAllowed(true);
		warningTable.setCellSelectionEnabled(true);
		warningTable.setForeground(Color.RED);
		warningTable.setToolTipText("Visualizza i riscontri non pervenuti dopo 5 giorni");
		warningTable.setBackground(new Color(143, 188, 143));
		warningTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"DATA INS", "TIPO PAGAMENTO ", "NOMINATIVO", "TARGA", "IMPORTO"
				}
				) {
			Class[] columnTypes = new Class[] {
					Object.class, String.class, String.class, String.class, Double.class
			};

			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false
			};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		warningTable.getColumnModel().getColumn(0).setPreferredWidth(103);
		scrollPane2.setViewportView(warningTable);

	}

	///metodo cancella campi in pagina  Crea /////////////////////////////////
	public void clearInsertRiscontro() {

		btn.clearSelection();
		textDay.setText("");
		textMonth.setText("");
		textFieldYear.setText("");
		textFieldCliente.setText("");
		textFieldTarga.setText("");
		textFieldImporto.setText("");

	}


	//cancella campi pagina ricerca

	public void clearRicercaRiscontro() {
		textFieldRicercaTarga.setText("");
		textRIcercaImporto.setText("");
		textFieldDD.setText("");
		textFieldMM.setText("");
		textFieldAAAA.setText("");
		dtm = (DefaultTableModel) tableRiscontri.getModel();
		dtm.setRowCount(0);
		btncampoRicerca.clearSelection();
		texttotale.setText("");
		chechNotReented.setSelected(false);

	}

	/////visuliazza tutti i riscontri presenti nella lista "riscontri"/////////////////
	

	public void printRicerca() {

		dtm = (DefaultTableModel) tableRiscontri.getModel();
		dtm.setRowCount(0);

		totalChiusura = 0.0;


		for(Riscontro rsc : riscontri) {
			Vector rowRisc = new Vector<>();

			totalChiusura += rsc.getImporto();         // somma riscontri della giornata per tipo
			LocalDate date = rsc.getDataInserimento(); //conversione data si inserimento
			LocalDate date2 = rsc.getDataRiscontro();  //se la data di riscontro è null(1990-01-01) stampo // // ////
			String dataMom = date2.format(dt);

			if(dataMom.equals("01/01/1990")) {
				dataMom = "Non Presente";
			}

			boolean riscontroBool = false ;
			if(rsc.isEsitoRiscontro()) {
				riscontroBool = Boolean.TRUE;
			}
			else if(!rsc.isEsitoRiscontro()) {
				riscontroBool = Boolean.FALSE;
			}

			rowRisc.add(date.format(dt));
			rowRisc.add(rsc.getTipoPagamento());
			rowRisc.add(rsc.getNominativo());
			rowRisc.add(rsc.getTarga_Polizza());
			rowRisc.add(rsc.getDescrizione());
			rowRisc.add(rsc.getImporto());
			rowRisc.add(dataMom);
			rowRisc.add(riscontroBool);



			dtm.addRow(rowRisc);
			

		}

		NumberFormat numFormat = NumberFormat.getInstance(); // classe per l'arrotondamento  dei numeri con parte decimale (stringa)
		texttotale.setText(numFormat.format(totalChiusura));  //visualizzo totale riscontri pere tipo 

	}


	//////////////////////SELEZIONA TUTTE LE RIGHE per rientro//////////////////

	private void selection() {

		for(Riscontro rsc : riscontri) {
			rsc.setEsitoRiscontro(true);
		}
		try {
			GestionaleBussiness.getInstance().UpdateRiscontri(riscontri);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		dtm.setRowCount(0);
		printRicerca();
		selectAll.setEnabled(false);
       

	}



	/////////////////////////////////////////////////////////////////	
	public void printNotReceived() {

		dtm = (DefaultTableModel) warningTable.getModel();
		dtm.setRowCount(0);

		for(Riscontro rsc : notReceived) {
			Vector rowRisc = new Vector<>();
			LocalDate date = rsc.getDataInserimento();
			rowRisc.add(date.format(dt));
			rowRisc.add(rsc.getTipoPagamento());
			rowRisc.add(rsc.getNominativo());
			rowRisc.add(rsc.getTarga_Polizza());
			rowRisc.add(rsc.getImporto());

			dtm.addRow(rowRisc);
		}

	}

	///////////////////////////////////////////////	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
