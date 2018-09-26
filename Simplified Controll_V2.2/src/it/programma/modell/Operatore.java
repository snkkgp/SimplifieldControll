package it.programma.modell;

public class Operatore extends Utente {
	private int id;
	private String userName;
	private String  password;
	private String permessi;




	public Operatore() {

	}



	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName.trim();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPermessi() {
		return permessi;
	}
	public void setPermessi(String permessi) {
		this.permessi = permessi;
	}




	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}







}
