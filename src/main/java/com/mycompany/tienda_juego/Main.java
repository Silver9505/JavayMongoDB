/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tienda_juego;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Main {

	public static void main(String args[]) {

		ArrayList<Juego> juegos = new ArrayList<Juego>();

		juegos.add(new Juego("DOOM Eternal", "id Software", "Bethesda Softworks", "DOOM", (float)250000));
		juegos.add(new Juego("Sekiro", "FromSoftware", "Activision", "", (float)220000));
                juegos.add(new Juego("MONSTER HUNTER: WORLD", "CAPCOM Co., Ltd", "CAPCOM Co., Ltd", "Monster Hunter", (float)96300));
                juegos.add(new Juego("Tomb Raider", "Crystal Dynamics", "Square Enix", "Tomb Raider", (float)61500));
                juegos.add(new Juego("DRIFT21", "ECC GAMES S.A", "505 Games", "", (float)92300));
                juegos.add(new Juego("Gears Tactics", "Splash Damage", "Xbox Game Studios", "Gears of War", (float)220000));
                
		try {
		// PASO 1: Conexión al Server de MongoDB Pasandole el host y el puerto
			MongoClient mongoClient = new MongoClient("localhost", 27017);

		// PASO 2: Conexión a la base de datos
			DB db = mongoClient.getDB("Tienda_Juegos");

		// PASO 3: Obtenemos una coleccion para trabajar con ella
			DBCollection collection = db.getCollection("Juego");

		// PASO 4: CRUD (Create-Read-Update-Delete)

			// PASO 4.1: "CREATE" -> Metemos los objetos juegos (o documentos en Mongo) en la coleccion Juego
			for (Juego jue : juegos) {
				collection.insert(jue.toDBObjectJuego());
			}

			// PASO 4.2.1: "READ" -> Leemos todos los documentos de la base de datos
			int numDocumentos = (int) collection.getCount();
			System.out.println("Número de documentos en la colección Juego: " + numDocumentos + "\n");

			// Busco todos los documentos de la colección y los imprimo
			DBCursor cursor = collection.find();
			try {
				while (cursor.hasNext()) {
					System.out.println(cursor.next().toString());
				}
			} finally {
				cursor.close();
			}

			// PASO 4.2.2: "READ" -> Hacemos una Query con condiciones (Buscar Juegos que tengan precio de 220000) y lo pasamos a un objeto Java
			System.out.println("\nJuegos que tengan precio de 220000");
			DBObject query = new BasicDBObject("precio", new BasicDBObject("$gt", 220000));
			cursor = collection.find(query);
			try {
				while (cursor.hasNext()) {
					Juego juego = new Juego((BasicDBObject) cursor.next());
					System.out.println(juego.toString());
				}
			} finally {
				cursor.close();
			}

			// PASO 4.3: "UPDATE" -> Actualizamos el precio de los juegos. Sumamos 100  a los juegos que tengan mas de 250000 de precio
			DBObject find = new BasicDBObject("precio", new BasicDBObject("$gt", 250000));
			DBObject updated = new BasicDBObject().append("$inc", new BasicDBObject().append("edad", 100));
			collection.update(find, updated, false, true);

			// PASO 4.4: "DELETE" -> Borramos todos los juegos que no tengan franquicia
			DBObject findDoc = new BasicDBObject("franquicia", "");
			collection.remove(findDoc);

		// PASO FINAL: Cerrar la conexion
			mongoClient.close();

		} catch (UnknownHostException ex) {
			System.out.println("Exception al conectar al server de Mongo: " + ex.getMessage());
		}

	}

}
