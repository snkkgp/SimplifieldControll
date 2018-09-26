package it.programma.modell.riscontro;

import java.time.LocalDate;

import it.programma.modell.Utente;

public class Riscontro {
	private int Id;
	private String tipoPagamento;
	private String nominativo;
	private String targa_Polizza;
	private String descrizione;
	private double importo;
	private LocalDate dataInserimento;
	private LocalDate dataRiscontro;
	boolean esitoRiscontro;


	public Riscontro() {

	}




	public String getTipoPagamento() {
		return tipoPagamento;
	}


	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}



	public double getImporto() {
		return importo;
	}


	public void setImporto(double importo) {
		this.importo = importo;
	}


	public LocalDate getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(LocalDate dataInserimento) {
		this.dataInserimento = dataInserimento;
	}


	public LocalDate getDataRiscontro() {
		return dataRiscontro;
	}
	public void setDataRiscontro(LocalDate dataRiscontro) {
		this.dataRiscontro = dataRiscontro;
	}


	public boolean isEsitoRiscontro() {
		return esitoRiscontro;
	}



	public void setEsitoRiscontro(boolean esitoRiscontro) {
		this.esitoRiscontro = esitoRiscontro;
	}




	public String getNominativo() {
		return nominativo;
	}




	public void setNominativo(String nominativo) {
		String nome[] = nominativo.split("\\s");
		String n1 = null; 
		StringBuilder finale = new StringBuilder();

		for(int index = 0; index < nome.length; index++ ) {
			n1 = nome[index].substring(0, 1).toUpperCase();
			finale.append(n1.concat(nome[index].substring(1) + " "));
		}
		this.nominativo = finale.toString();
	}




	public String getTarga_Polizza() {
		return targa_Polizza;
	}




	public void setTarga_Polizza(String targa_Polizza) {
		this.targa_Polizza = targa_Polizza.toUpperCase();
	}


	public void setId(int id) {
		this.Id = id;
	}

	public int getId() {
		return Id;
	}




	public String getDescrizione() {
		return descrizione;
	}




	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

    


}
