package com.mycompany.tienda_juego;

import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class Juego {

	private String titulo;
	private String desarrollador;
	private String editor;
	private String franquicia;
	private Float precio;

	public Juego() {
	}

	public Juego(String titulo, String desarrollador, String editor, String franquicia, Float precio) {
		this.titulo = titulo;
		this.desarrollador = desarrollador;
		this.editor = editor;
		this.franquicia = franquicia;
		this.precio = precio;
	}

	// Transformo un objecto que me da MongoDB a un Objecto Java
	public Juego(BasicDBObject dBObjectJuego) {
		this.titulo = dBObjectJuego.getString("titulo");
		this.desarrollador = dBObjectJuego.getString("desarrollador");
		this.editor = dBObjectJuego.getString("editor");
		this.franquicia=dBObjectJuego.getString("franquicia");
		this.precio=dBObjectJuego.getFloat("precio");
		
	}

	public BasicDBObject toDBObjectJuego() {

		// Creamos una instancia BasicDBObject
		BasicDBObject dBObjectJuego = new BasicDBObject();

		dBObjectJuego.append("titulo", this.getTitulo());
		dBObjectJuego.append("desarrollador", this.getDesarrollador());
		dBObjectJuego.append("editor", this.getEditor());
		dBObjectJuego.append("franquicia", this.getFranquicia());
		dBObjectJuego.append("precio", this.getPrecio());

		return dBObjectJuego;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDesarrollador() {
		return desarrollador;
	}

	public void setDesarrollador(String desarrollador) {
		this.desarrollador = desarrollador;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getFranquicia() {
		return franquicia;
	}

	public void setFranquicia(String franquicia) {
		this.franquicia = franquicia;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Titulo: " + this.getTitulo() + " " + " Desarrollador: "+ this.getDesarrollador() + " Editor: " + this.getEditor() + " Franquicia: " + this.getFranquicia() + " Precio: " + this.getPrecio();
	}
}
