package co.gov.metropol.area247.centrocontrol.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Archivo {
    private BufferedWriter archivoEscritura;
    private BufferedReader archivoLectura;
    private ArrayList<String> contenido;
    
    private String ruta;
    private String path = "";
    

    public Archivo(String ruta) {
        this.ruta = ruta;
        this.contenido = new ArrayList<>();
    }
    
    public ArrayList<String> getContenido() {
        return contenido;
    }

    public void setContenido(ArrayList<String> contenido) {
        this.contenido = contenido;
    }

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

    public void borrarArchivo(){
        File pageFile = new File(ruta);
        pageFile.delete();
    }
    
    public void crearArchivo(){
        try {
            archivoEscritura = new BufferedWriter(new FileWriter(ruta, true));
        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void abrirArchivo(boolean escritura) throws IOException {
        if (escritura == true) {
            this.archivoEscritura = new BufferedWriter(new FileWriter(this.ruta, true));
        } else {
            this.archivoLectura = new BufferedReader(new FileReader(this.ruta));
        }
    }
    
    public void escribirArchivo(String datos) throws IOException {
        archivoEscritura.write(datos);
        archivoEscritura.newLine();
    }

    public void cerrarArchivo() throws IOException {
        if (archivoEscritura != null) {
            archivoEscritura.close();
        }
        if (archivoLectura != null) {
            archivoLectura.close();
        }
    }

    public void leerArchivo() throws IOException {
        String cadena;
        contenido.clear();
        FileReader f = new FileReader(ruta);
        BufferedReader b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            contenido.add(cadena);
        }
        b.close();
    }

    public void leerLineas(String texto) throws IOException {
        String[] cadena = new String[texto.length()];
        
        for (int i = 0; i < cadena.length; i++) {
            cadena = texto.split("");
        }
    }
    
    
    
    
}
