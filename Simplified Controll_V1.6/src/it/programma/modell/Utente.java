package it.programma.modell;

import java.time.LocalDate;

public abstract class Utente {
    private String  tipologia;
    private LocalDate dataCrezione;
    
    
    
    
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public LocalDate getDataCrezione() {
		return dataCrezione;
	}
	public void setDatacrezione(LocalDate dataCrezione) {
		this.dataCrezione = dataCrezione;
	}
    
    
    
	
}
