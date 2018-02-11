package com.mkyong.rest;

import java.net.UnknownHostException;

import com.mkyong.Track;
import com.mongodb.BasicDBObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;


/**
 * Java MongoDB : Insert a Document
 *
 */
public class MongoDBUtility {
  public  static void main(String[] args) {

    try {

    	Track track = new Track();
    	track.setTitle("Hollywood");
    	track.setSinger("Rihanna");
    	  insertRow(track);
    	//updateRow (track);
    	//deleteRow(track);
	} catch (UnknownHostException e) {
	e.printStackTrace();
    } catch (MongoException e) {
	e.printStackTrace();
    }

  }

public static void insertRow(Track track) throws UnknownHostException {
	DBCollection collection = findCollection();
	BasicDBObject document = new BasicDBObject();

	document.put("title", track.getTitle());
	document.put("singer", track.getSinger());

	collection.insert(document);
}


public  static void updateRow(String id, Track track) throws UnknownHostException {
	BasicDBObject newDocument = new BasicDBObject();
	newDocument.put("title", track.getTitle());
	newDocument.put("singer", track.getSinger());
	
	//search by title and update
	BasicDBObject searchQuery = new BasicDBObject().append("title", track.getTitle());
	DBCollection collection = findCollection();

	collection.update(searchQuery, newDocument);
	
}

public  static void deleteRow(String id) throws UnknownHostException {
	
	DBCollection collection = findCollection();
	collection.remove(new BasicDBObject().append("title", id));
}



public  static DBCollection findCollection() throws UnknownHostException {
	
	//Establish connection and fetch rows from table
	Mongo mongo = new Mongo("localhost", 27017);
	DB db = mongo.getDB("MyNewDatabase");
	
	DBCollection collection = db.getCollection("Songs");
	/*DBCursor cursor = collection.find();
	while(cursor.hasNext()) {
	    System.out.println("i am" + cursor.next());
  	}*/
	return collection;
}
}