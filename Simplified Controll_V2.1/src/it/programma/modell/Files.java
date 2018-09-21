package it.programma.modell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.mysql.fabric.xmlrpc.base.Data;

import it.programma.modell.riscontro.Riscontro;

public class Files {
	private String cartella;
	private String path;
	private List<Riscontro> lista; 
    private DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	
    public Files(String cartella,String path, List<Riscontro> lista) {
    	setCartella(cartella);
    	setPath(path);
    	creaFile(cartella, path,lista);
    }

	public String getCartella() {
		return cartella;
	}
	public void setCartella(String cartella) {
		this.cartella = cartella;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public void creaFile(String cartella,String path,List<Riscontro> lista) {
		File file = new File(cartella + path);

		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			PrintWriter pw = null;
			LocalDate data = lista.get(0).getDataInserimento();
			try {
				pw = new PrintWriter(new FileWriter(file));
				
				pw.println("\t\t\t\tPAGAMENTI " + lista.get(0).getTipoPagamento().toUpperCase() + " DEL "  + data.format(dt) );
                for(Riscontro rsc : lista) {
                	
                	pw.println();
                	pw.println();
                	pw.print(rsc.getNominativo().toUpperCase() + "     TARGA/POLIZZA N:   " + rsc.getTarga_Polizza() + "   " + rsc.getImporto() + " EURO");
                	pw.println();
                	
                }
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				pw.close();
			}
		}

	}


}
