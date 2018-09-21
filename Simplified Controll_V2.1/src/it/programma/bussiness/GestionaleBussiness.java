package it.programma.bussiness;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import it.programma.modell.Operatore;
import it.programma.modell.riscontro.Riscontro;


public class GestionaleBussiness<Utente> {

	private Operatore operatore;
	private Connection con;
	private Riscontro rsc;
	private static GestionaleBussiness gb;
	private  String ServerIp;
	private int numbertPort;
	private String userDatabase;
	private String passwordDatabase;
	private String cartellaConf = "C:\\fileConfig\\";
	private String cartellaRiscontri;

	public enum Querytype {MISTA,SPECIAL,NON_RIENTRATI};


	/**
	 * @return the cartellaRiscontri
	 */
	public String getCartellaRiscontri() {
		return cartellaRiscontri;
	}



	/**
	 * @param cartellaRiscontri the cartellaRiscontri to set
	 */
	public void setCartellaRiscontri(String cartellaRiscontri) {
		this.cartellaRiscontri = cartellaRiscontri;
	}



	/**
	 * @return the serverIp
	 */
	public  String getServerIp() {
		return ServerIp;
	}



	/**
	 * @param serverIp the serverIp to set
	 */
	public void setServerIp(String serverIp) {
		ServerIp = serverIp;
	}


	/**
	 * @return the numbertPort
	 */
	public int getNumbertPort() {
		return numbertPort;
	}



	/**
	 * @param numbertPort the numbertPort to set
	 */
	public void setNumbertPort(int numbertPort) {
		this.numbertPort = numbertPort;
	}



	/**
	 * @return the userDatabase
	 */
	public String getUserDatabase() {
		return userDatabase;
	}



	/**
	 * @param userDatabase the userDatabase to set
	 */
	public void setUserDatabase(String userDatabase) {
		this.userDatabase = userDatabase;
	}



	/**
	 * @return the passwordDatabase
	 */
	public String getPasswordDatabase() {
		return passwordDatabase;
	}



	/**
	 * @param passwordDatabase the passwordDatabase to set
	 */
	public void setPasswordDatabase(String passwordDatabase) {
		this.passwordDatabase = passwordDatabase;
	}



	/////////SingletonPattern/////////////////////////////


	public static GestionaleBussiness getInstance() {
		if(gb == null) {
			gb = new GestionaleBussiness();
		}
		return gb;
	}



	////////////////Connessione al DataBase/////////////////////

	private Connection getConnection() throws SQLException {
		if(con == null) {	

			///creo cartella//////
			File  folder = new File(cartellaConf);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			///////////////creoFile se non gia esistente///////////

			File file = null;
			file = new File(cartellaConf + "file.properties");
			if(!file.exists()) {
				try {
					file.createNewFile();
					Properties properties = new Properties();

					FileWriter writer = null; 




					properties.setProperty("ipserver","192.168.0.68");
					properties.setProperty("numberport","3306");
					properties.setProperty("usernameDB","giusy");
					properties.setProperty("passwordDB","Giusy1995");
					properties.setProperty("CartellaRiscontri","C:\\Users\\Donato\\Desktop\\Riscontri\\");
					properties.setProperty("project-name", "SimplifieldControll");
					properties.setProperty("Version", "V1.4 del 12/05/2018");

					writer = new FileWriter(file);


					properties.store(writer,"autore:Tuzzolino Donato");
					writer.close();


				} catch (IOException e) {
					e.printStackTrace();
				}
			}





			/////////////////read file//////////

			try {
				FileReader reader = new FileReader(cartellaConf + "file.properties");

				Properties pro = new Properties();
				pro.load(reader);
				setServerIp(pro.getProperty("ipserver"));
				setNumbertPort(Integer.parseInt(pro.getProperty("numberport")));
				setUserDatabase(pro.getProperty("usernameDB"));
				setPasswordDatabase(pro.getProperty("passwordDB"));
				setCartellaRiscontri(pro.getProperty("CartellaRiscontri"));

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

            try {  
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setServerName(getServerIp() );
			dataSource.setPort(getNumbertPort());
			dataSource.setUser(getUserDatabase());
			dataSource.setPassword(getPasswordDatabase());
			dataSource.setDatabaseName("gestionale");
			
			con = dataSource.getConnection();
            }catch(Exception e) {
            	JOptionPane.showMessageDialog(null,"Si è verificato un problema di connessione con il Database!!\n"
            			+ "                                  Chiamare il tecnico!",
						"Attenzione",JOptionPane.WARNING_MESSAGE);
            }
            
		}
		return con;
	}

	//////////////Accesso gestionale///////////////////

	public int access(Operatore user) throws SQLException {

		String sql = "SELECT id,userName,ruolo FROM operatore WHERE userName = ? AND password = ?";

		PreparedStatement ps = getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, user.getUserName());
		ps.setString(2, user.getPassword());


		ResultSet rs = ps.executeQuery();


		rs.next();

		user.setUserName(rs.getString(2));
		user.setPermessi(rs.getString(3));




		return rs.getInt(1); 

	}

	//////////////visualizza tutti Utenti attivi//////////	

	public List<Operatore> getUserList() throws SQLException{

		String sql = "SELECT id, userName, password, ruolo FROM operatore ";
		PreparedStatement ps = getConnection().prepareStatement(sql);

		List<Operatore> user = new ArrayList<Operatore>();

		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			operatore = new Operatore();
			operatore.setId(rs.getInt(1));
			operatore.setUserName(rs.getString(2));
			operatore.setPassword(rs.getString(3));
			operatore.setPermessi(rs.getString(4));
			user.add(operatore);

		}

		return user;
	}

	//////////////////Inseriemwnto Nuovo Utente///////////////////////

	public int newUser(Operatore opt) throws SQLException {

		String sql_1 = "SELECT id FROM operatore WHERE username = ?";
		PreparedStatement ps = getConnection().prepareStatement(sql_1);
		ps.setString(1, opt.getUserName());

		ResultSet rs  = ps.executeQuery();

		while(rs.next()) {
			opt.setId(rs.getInt(1));
		}


		if(opt.getId() == 0) {

			String sql_2 = "INSERT INTO operatore (userName,password,ruolo) VALUES (?, ?, ?)";

			PreparedStatement ps_1 = getConnection().prepareStatement(sql_2,Statement.RETURN_GENERATED_KEYS);

			ps_1.setString(1, opt.getUserName());
			ps_1.setString(2, opt.getPassword());
			ps_1.setString(3, opt.getPermessi());

			ps_1.executeUpdate();

			ResultSet rs_1 = ps_1.getGeneratedKeys();

			rs_1.next();

			return rs_1.getInt(1);

		}
		return 0;
	}

	/////////////////////////////Aggiornamento dati utente////////////////////////////////

	public int refreshDati(Operatore opt,int userID) throws SQLException {



		String sql_1 = "UPDATE operatore SET password = ? , ruolo = ? WHERE id = ?" ;

		PreparedStatement ps_1 = getConnection().prepareStatement(sql_1);

		ps_1.setString(1, opt.getPassword());
		ps_1.setString(2, opt.getPermessi());
		ps_1.setInt(3,userID);
		ps_1.executeUpdate();

		return userID;



	}


	///////////////////////elimina utente/////////////////

	public void deleteUser(List<Operatore> user,int index) throws SQLException {

		String sql = "DELETE FROM operatore WHERE id = ?";

		PreparedStatement ps = getConnection().prepareStatement(sql);
		ps.setInt(1, user.get(index).getId());
		ps.executeUpdate();

	}

	////////////////////////Inserimento Riscontro////////////////////////

	public int InsertRiscontro(Riscontro rsc) throws SQLException {

		String sql = "INSERT INTO riscontro (data_inserimento, tipo, cliente, targa_polizza, importo,data_Rientro,casuale) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)"; 

		PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, rsc.getDataInserimento().toString());
		ps.setString(2, rsc.getTipoPagamento());
		ps.setString(3, rsc.getNominativo());
		ps.setString(4, rsc.getTarga_Polizza());
		ps.setDouble(5, rsc.getImporto());
		ps.setString(6, "1990-01-01");
		ps.setString(7, rsc.getDescrizione());

		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();

		return rs.getInt(1);
	}


	//////////////////////Update dati Riscontro Inserito//////////////////////////////////////

	public void UpdateRiscontri(Riscontro riscontro,int id) throws SQLException {
		String sql = "UPDATE riscontro SET data_inserimento = ?, tipo = ?, cliente = ?, targa_polizza = ?, importo = ?, data_Rientro = ?, esito = ? WHERE id_riscontro = ?";


		LocalDate dr = LocalDate.now();
		riscontro.setDataRiscontro(dr);
		PreparedStatement ps = getConnection().prepareStatement(sql);
		ps.setString(1, riscontro.getDataInserimento().toString());
		ps.setString(2, riscontro.getTipoPagamento());
		ps.setString(3, riscontro.getNominativo());
		ps.setString(4, riscontro.getTarga_Polizza());
		ps.setDouble(5, riscontro.getImporto());
		ps.setString(6, riscontro.getDataRiscontro().toString());
		ps.setBoolean(7,riscontro.isEsitoRiscontro());
		ps.setInt(8, id);




		ps.executeUpdate();


	}
	
	
	///////////////////// contrassegn tutti i riscontri come accreditati/////////////////
	
	public void UpdateRiscontri(List<Riscontro> riscontro) throws SQLException {
		String sql = "UPDATE riscontro SET esito = ? ,data_Rientro = ? WHERE data_inserimento = ? AND tipo = ?";
	
		
		
		PreparedStatement ps = getConnection().prepareStatement(sql);
		for(Riscontro rsc : riscontro) {
			ps.setBoolean(1,rsc.isEsitoRiscontro());
			ps.setString(2, LocalDate.now().toString());
			ps.setNString(3, rsc.getDataInserimento().toString());
			ps.setString(4, rsc.getTipoPagamento());
			ps.executeUpdate();
		}
	
	}
	
	
	
	////////////	QUERY UNICA //////////////////////

	public List<Riscontro> ricerca(Riscontro riscontro,JCheckBox chechNotReented) throws SQLException {
		PreparedStatement ps = null;
    
		Querytype query = Querytype.MISTA ; // ricerca di default 



		if(riscontro.getDataInserimento() != null && !riscontro.getTipoPagamento().equals("") && 
				!riscontro.getTarga_Polizza().equals("")) {

			query = Querytype.SPECIAL;  // ricerca specifica data, tipo e targa
		}


		if(chechNotReented.isSelected()) {    //ricerca per riscontro non ancora accreditato
			query = Querytype.NON_RIENTRATI;
		}


		if(query == Querytype.NON_RIENTRATI) {

			String sql ="SELECT *FROM riscontro WHERE esito = ?";

			ps = getConnection().prepareStatement(sql);

			ps.setBoolean(1, false);

			ResultSet rs = ps.executeQuery();
			List<Riscontro> listaRiscontri = new ArrayList<Riscontro>();


			while(rs.next()) {
				rsc = new Riscontro();
				rsc.setId(rs.getInt(1));
				String data = rs.getString(2);
				String array[] = data.split("-");
				LocalDate lc = LocalDate.of(Integer.parseInt(array[0]), 
						Integer.parseInt(array[1]), Integer.parseInt(array[2]));
				rsc.setDataInserimento(lc);
                rsc.setTipoPagamento(rs.getString(3));
				rsc.setNominativo(rs.getString(4));
				rsc.setTarga_Polizza(rs.getString(5));
				rsc.setImporto(rs.getDouble(6));

				String data2 = rs.getString(7);
				String array2[] = data2.split("-");
				LocalDate lc2 = LocalDate.of(Integer.parseInt(array2[0]), 
						Integer.parseInt(array2[1]), Integer.parseInt(array2[2]));
				rsc.setDataRiscontro(lc2);

				rsc.setEsitoRiscontro(rs.getBoolean(8));
				rsc.setDescrizione(rs.getString(9));
				listaRiscontri.add(rsc);

			}


		}


		if(query == Querytype.MISTA ) {

			String sql = "SELECT *FROM riscontro WHERE (data_inserimento = ? AND tipo = ? ) OR targa_Polizza = ? OR importo = ?";

			ps = getConnection().prepareStatement(sql);

			if(riscontro.getDataInserimento() != null) {
				ps.setString(1, riscontro.getDataInserimento().toString());

			}
			else {
				ps.setString(1, "");
			}


			if(riscontro.getTipoPagamento() != null) {
				ps.setString(2, riscontro.getTipoPagamento());
			}
			else {
				ps.setString(2, "");
			}



			if(riscontro.getTarga_Polizza() != null) {
				ps.setString(3, riscontro.getTarga_Polizza());
			}
			else {
				ps.setString(3, "");
			}


			if(riscontro.getImporto() > 0) {
				ps.setDouble(4, riscontro.getImporto());
			}
			else {
				ps.setDouble(4, 0);
			}

		}



		if(query == Querytype.SPECIAL)  {

			String sql ="SELECT *FROM riscontro WHERE data_inserimento = ? AND tipo = ?  AND targa_Polizza = ?";

			ps = getConnection().prepareStatement(sql);

			if(riscontro.getDataInserimento() != null) {
				ps.setString(1, riscontro.getDataInserimento().toString());

			}
			else {
				ps.setString(1, "");
			}


			if(riscontro.getTipoPagamento() != null) {
				ps.setString(2, riscontro.getTipoPagamento());
			}
			else {
				ps.setString(2, "");
			}



			if(riscontro.getTarga_Polizza() != null) {
				ps.setString(3, riscontro.getTarga_Polizza());
			}
			else {
				ps.setString(3, "");
			}

		}


		ResultSet rs = ps.executeQuery();
		List<Riscontro> listaRiscontri = new ArrayList<Riscontro>();


		while(rs.next()) {
			rsc = new Riscontro();
			rsc.setId(rs.getInt(1));
			String data = rs.getString(2);
			String array[] = data.split("-");
			LocalDate lc = LocalDate.of(Integer.parseInt(array[0]), 
					Integer.parseInt(array[1]), Integer.parseInt(array[2]));
			rsc.setDataInserimento(lc);
            rsc.setTipoPagamento(rs.getString(3));
			rsc.setNominativo(rs.getString(4));
			rsc.setTarga_Polizza(rs.getString(5));
			rsc.setImporto(rs.getDouble(6));

			String data2 = rs.getString(7);
			String array2[] = data2.split("-");
			LocalDate lc2 = LocalDate.of(Integer.parseInt(array2[0]), 
					Integer.parseInt(array2[1]), Integer.parseInt(array2[2]));
			rsc.setDataRiscontro(lc2);

			rsc.setEsitoRiscontro(rs.getBoolean(8));
			rsc.setDescrizione(rs.getString(9));
			listaRiscontri.add(rsc);

			chechNotReented.setSelected(false);

		}


		return listaRiscontri;


	}

	
///////////////cacella riscontro//////////////////////////////	
	
	public void removeRiscontro( int row) throws SQLException {

		String slq = "DELETE FROM riscontro  WHERE id_riscontro = ?";
		PreparedStatement ps = getConnection().prepareStatement(slq);

		ps.setInt(1,row);
		ps.executeUpdate();
	}

////////////riscontri non pervenutio entro   i 5 giorni/////////////////////
	
	public List<Riscontro> notReturned() throws SQLException{

		String sql = "SELECT  data_inserimento, tipo, cliente, targa_Polizza, importo FROM gestionale.riscontro WHERE esito = ?";
		PreparedStatement ps =  getConnection().prepareStatement(sql);
		ps.setBoolean(1, false);

		List<Riscontro> notReceived =new ArrayList<Riscontro>();
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			rsc = new Riscontro();
			String data = rs.getString(1);
			String array[] = data.split("-");

			LocalDateTime ldt = LocalDateTime.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]), 
					Integer.parseInt(array[2]), 0, 0);
			ZonedDateTime zdt = ldt.atZone(ZoneId.of("Europe/Rome"));
			long millisRisc = zdt.toInstant().toEpochMilli();
			long att = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			long cinqueGiorni = 432000000;
			if((att - millisRisc) > cinqueGiorni) {

				LocalDate lc = LocalDate.of(Integer.parseInt(array[0]), 
						Integer.parseInt(array[1]), Integer.parseInt(array[2]));

				rsc.setDataInserimento(lc);
				rsc.setTipoPagamento(rs.getString(2));
				rsc.setNominativo(rs.getString(3));
				rsc.setTarga_Polizza(rs.getString(4));
				rsc.setImporto(rs.getDouble(5));
				notReceived.add(rsc);

			}
		} 

		return notReceived;
	}

}
