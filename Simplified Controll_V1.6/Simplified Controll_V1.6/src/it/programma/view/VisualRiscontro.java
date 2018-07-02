package it.programma.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.EscapeTokenizer;

import it.programma.bussiness.GestionaleBussiness;
import it.programma.modell.riscontro.Riscontro;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class VisualRiscontro extends JFrame {

	private JPanel contentPane;
	private JTextField dataRiscDD;
	private JTextField dataRiscMM;
	private JTextField dataRiscAAAA;
	private JTextField textFieldVisualRiscontro;
	private JTextField textFieldVisualTarga;
	private JTextField textFieldVisualImporto;
	private JRadioButton radioRiscontroPostale;
	private JRadioButton radioRiscontroPos;
	private JRadioButton radioRiscontroBonifico;
	private JCheckBox chckbxAccreditato;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualRiscontro frame = new VisualRiscontro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public VisualRiscontro() throws HeadlessException {
		super();
	}


	/**
	 * Create the frame.
	 */
	public VisualRiscontro(List<Riscontro> riscontri,int rowtable) {
		setTitle("Riscontro");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(350, 100, 523, 362);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(143, 188, 143));
		panel.setBounds(10, 11, 497, 311);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblRiscontroDel = new JLabel("Data Inserimento");
		lblRiscontroDel.setForeground(new Color(0, 0, 0));
		lblRiscontroDel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRiscontroDel.setBounds(10, 56, 110, 14);
		panel.add(lblRiscontroDel);

		dataRiscDD = new JTextField();
		dataRiscDD.setBackground(new Color(176, 224, 230));
		dataRiscDD.setBounds(130, 50, 21, 20);
		panel.add(dataRiscDD);
		dataRiscDD.setColumns(10);
		dataRiscDD.setText(String.valueOf(riscontri.get(rowtable).getDataInserimento().getDayOfMonth()));

		dataRiscMM = new JTextField();
		dataRiscMM.setBackground(new Color(176, 224, 230));
		dataRiscMM.setBounds(159, 50, 21, 20);
		panel.add(dataRiscMM);
		dataRiscMM.setColumns(10);
		dataRiscMM.setText(String.valueOf(riscontri.get(rowtable).getDataInserimento().getMonthValue()));

		dataRiscAAAA = new JTextField();
		dataRiscAAAA.setBackground(new Color(176, 224, 230));
		dataRiscAAAA.setBounds(192, 50, 45, 20);
		panel.add(dataRiscAAAA);
		dataRiscAAAA.setColumns(10);
		dataRiscAAAA.setText(String.valueOf(riscontri.get(rowtable).getDataInserimento().getYear()));

		JLabel lblTipoRiscontro = new JLabel("Tipo Riscontro");
		lblTipoRiscontro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTipoRiscontro.setBounds(334, 28, 95, 14);
		panel.add(lblTipoRiscontro);

		radioRiscontroPostale = new JRadioButton("Postale");
		radioRiscontroPostale.setBackground(new Color(143, 188, 143));
		radioRiscontroPostale.setFont(new Font("Tahoma", Font.BOLD, 11));
		radioRiscontroPostale.setForeground(new Color(255, 255, 0));
		radioRiscontroPostale.setBounds(345, 49, 74, 23);
		panel.add(radioRiscontroPostale);

		radioRiscontroPos = new JRadioButton("Pos");
		radioRiscontroPos.setBackground(new Color(143, 188, 143));
		radioRiscontroPos.setFont(new Font("Tahoma", Font.BOLD, 11));
		radioRiscontroPos.setForeground(new Color(0, 0, 205));
		radioRiscontroPos.setBounds(416, 49, 45, 23);
		panel.add(radioRiscontroPos);
		
		radioRiscontroBonifico = new JRadioButton("Bonifico");
		radioRiscontroBonifico.setBackground(new Color(143, 188, 143));
		radioRiscontroBonifico.setForeground(new Color(0, 128, 0));
		radioRiscontroBonifico.setFont(new Font("Tahoma", Font.BOLD, 11));
		radioRiscontroBonifico.setBounds(270, 49, 80, 23);
		panel.add(radioRiscontroBonifico);
		

		//////creazione buttonGroup e settaggio in base al tipo di riscontro//////////////
		ButtonGroup btn = new ButtonGroup();
		btn.add(radioRiscontroPos);
		btn.add(radioRiscontroPostale);
		btn.add(radioRiscontroBonifico);

		if(riscontri.get(rowtable).getTipoPagamento().equals("pos")) {
			radioRiscontroPos.setSelected(true);
		}
		else if(riscontri.get(rowtable).getTipoPagamento().equals("postale")) {
			radioRiscontroPostale.setSelected(true);
		}
		else if(riscontri.get(rowtable).getTipoPagamento().equals("bonifico")) {
			radioRiscontroBonifico.setSelected(true);
		}



		JLabel lblNominativo = new JLabel("Nominativo");
		lblNominativo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNominativo.setBounds(22, 123, 80, 14);
		panel.add(lblNominativo);

		textFieldVisualRiscontro = new JTextField();
		textFieldVisualRiscontro.setBackground(new Color(176, 224, 230));
		textFieldVisualRiscontro.setBounds(130, 120, 107, 20);
		panel.add(textFieldVisualRiscontro);
		textFieldVisualRiscontro.setColumns(10);
		textFieldVisualRiscontro.setText(riscontri.get(rowtable).getNominativo());

		JLabel lblTarga = new JLabel("Targa/Polizza N\u00B0");
		lblTarga.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTarga.setBounds(270, 123, 95, 14);
		panel.add(lblTarga);

		textFieldVisualTarga = new JTextField();
		textFieldVisualTarga.setBackground(new Color(176, 224, 230));
		textFieldVisualTarga.setBounds(375, 120, 86, 20);
		panel.add(textFieldVisualTarga);
		textFieldVisualTarga.setColumns(10);
		textFieldVisualTarga.setText(riscontri.get(rowtable).getTarga_Polizza());

		JLabel lblImporto = new JLabel("Importo Euro");
		lblImporto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblImporto.setForeground(new Color(0, 0, 0));
		lblImporto.setBounds(22, 195, 80, 14);
		panel.add(lblImporto);

		textFieldVisualImporto = new JTextField();
		textFieldVisualImporto.setBackground(new Color(175, 238, 238));
		textFieldVisualImporto.setBounds(130, 192, 86, 20);
		panel.add(textFieldVisualImporto);
		textFieldVisualImporto.setColumns(10);
		textFieldVisualImporto.setText(String.valueOf(riscontri.get(rowtable).getImporto()));

		JLabel lblEsitoRisontro = new JLabel("Esito Risontro:");
		lblEsitoRisontro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEsitoRisontro.setBounds(270, 195, 95, 14);
		panel.add(lblEsitoRisontro);

		chckbxAccreditato = new JCheckBox("Accreditato");
		chckbxAccreditato.setBackground(new Color(143, 188, 143));
		chckbxAccreditato.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckbxAccreditato.setForeground(new Color(255, 0, 0));
		chckbxAccreditato.setBounds(375, 191, 97, 23);
		panel.add(chckbxAccreditato);

		////settaggio flag risocntro in base all effettivo  rientro del riscontro

		if(riscontri.get(rowtable).isEsitoRiscontro()) {
			chckbxAccreditato.setSelected(true);
		}
		else {
			chckbxAccreditato.setSelected(false);
		}



		JLabel lblDettaglioRiscontro = new JLabel("Dettaglio Riscontro:");
		lblDettaglioRiscontro.setForeground(new Color(210, 105, 30));
		lblDettaglioRiscontro.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDettaglioRiscontro.setBounds(96, 11, 141, 20);
		panel.add(lblDettaglioRiscontro);


		///////////////////////////////Update dati riscontro//////////////////////////Sei qui--------------------------------------->

		JButton btnSalvaRiscontro = new JButton("Salva");
		btnSalvaRiscontro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if((!dataRiscDD.getText().equals("")) && (!dataRiscMM.getText().equals("")) && 
						(!dataRiscAAAA.getText().equals("")) && (radioRiscontroPos.isSelected()) || 
						(radioRiscontroPostale.isSelected() || radioRiscontroBonifico.isSelected()) && (!textFieldVisualRiscontro.getText().equals("")) &&
						(!textFieldVisualTarga.getText().equals("")) && (!textFieldVisualImporto.getText().equals(""))) {

					Riscontro riscontro = new Riscontro();
					boolean errData = false;
					boolean errImporto = false;
					boolean flagInvalidMessage = false;

					String gg = dataRiscDD.getText().trim();
					String mm = dataRiscMM.getText().trim();
					String yyyy = dataRiscAAAA.getText().trim().substring(0, 4);

					try {

						LocalDate datIns = LocalDate.of(Integer.parseInt(yyyy),Integer.parseInt(mm),Integer.parseInt(gg));
						riscontro.setDataInserimento(datIns);

					}
					catch(java.time.DateTimeException ext) {
						JOptionPane.showMessageDialog(null,"Formato Data Inserimento non valido",
								"Errore",JOptionPane.INFORMATION_MESSAGE);
						errData = true;
					}

					if(radioRiscontroPos.isSelected()) {
						riscontro.setTipoPagamento("pos");
					}
					else if(radioRiscontroPostale.isSelected()) {
						riscontro.setTipoPagamento("postale");
					}
					else if(radioRiscontroBonifico.isSelected()) {
						riscontro.setTipoPagamento("bonifico");
					}

					riscontro.setNominativo(textFieldVisualRiscontro.getText());
					riscontro.setTarga_Polizza(textFieldVisualTarga.getText());
					if(chckbxAccreditato.isSelected()) {
						riscontro.setEsitoRiscontro(true);
						
					}
					double importo = 0;

					try {
						importo = Double.parseDouble(textFieldVisualImporto.getText());
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
							int id = riscontri.get(rowtable).getId();
							GestionaleBussiness.getInstance().UpdateRiscontri(riscontro,id);    //passo alla pagina l'oggetto riscontro e l'id dove effettuare le modifiche
							JOptionPane.showMessageDialog(panel, "Riscontro Aggiornato con Successo!");
							setVisible(false);
							

						} catch (SQLException e) {

							e.printStackTrace();
						}
					}
							
				}
			
			}
			  
			
			
		});
		btnSalvaRiscontro.setForeground(new Color(0, 128, 0));
		btnSalvaRiscontro.setBounds(398, 266, 89, 23);
		panel.add(btnSalvaRiscontro);
		////////////////////////////////////////////////////////////		

		//////esci pagina riscontro//////////////////////////////////	
		JButton btnEsciRiscontro = new JButton("Esci");
		btnEsciRiscontro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		btnEsciRiscontro.setForeground(new Color(255, 0, 0));
		btnEsciRiscontro.setBounds(299, 266, 89, 23);
		panel.add(btnEsciRiscontro);
			
	}

	
}
