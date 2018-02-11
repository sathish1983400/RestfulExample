package com.mkyong.rest;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mkyong.Track;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;




@Path("/json/metallica")
public class JSONService {

	private static final String AAPI_105 = "AAPI_105";
	private static final String AAPI_104 = "AAPI_104";

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List getTrackInJSON() throws UnknownHostException {
		List<Track> trackList = new ArrayList();
		MongoDBUtility mongoDBUtility = new MongoDBUtility();
		DBCollection collection = mongoDBUtility.findCollection();
		DBCursor cursorDoc = collection.find();
		Track track =null ;
		// iterate list from db to show in the screen
		while (cursorDoc.hasNext()) {
			track = new Track();
			DBObject dbObject = cursorDoc.next();
            List list = new ArrayList(((LinkedHashMap<String, Object>) dbObject).values());
            track.setTitle((String) list.get(1));
            track.setSinger((String) list.get(2));
            trackList.add(track);
		}
 	return trackList;
	}
	
	@GET
	@Path("/{title}")
	@Produces(MediaType.APPLICATION_JSON)
	public List getTrackDetailInJSON(@PathParam("title") String id) throws UnknownHostException {
		List<Track> trackList = new ArrayList();
		MongoDBUtility mongoDBUtility = new MongoDBUtility();
		DBCollection collection = mongoDBUtility.findCollection();
		DBCursor cursorDoc = collection.find();
		Track track =null ;
		while (cursorDoc.hasNext()) {
			track = new Track();
			DBObject dbObject = cursorDoc.next();
			List list = new ArrayList(((LinkedHashMap<String, Object>) dbObject).values());
           
			// iterate list from db , match the title to show corresponding details in the screen
			// iterate list from db to show in the screen
				track = new Track();
			
				if(list.get(1).toString().equalsIgnoreCase(id)) {
	            track.setTitle((String) list.get(1));
	            track.setSinger((String) list.get(2));
	            
	            trackList.add(track);
			}			
		}
            return trackList;
	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Track track) throws UnknownHostException, CustomReasonPhraseException {
		String result = "Track saved : " + track;
		MongoDBUtility mongoDBUtility = new MongoDBUtility();
		
		DBCollection collection = mongoDBUtility.findCollection();
		DBCursor cursorDoc = collection.find();
		while (cursorDoc.hasNext()) {
			DBObject dbObject = cursorDoc.next();
			List list = new ArrayList(((LinkedHashMap<String, Object>) dbObject).values());
			
			//same combination existing check
			if (list!= null && list.get(1).toString().equalsIgnoreCase(track.getTitle()) && (list.get(2).toString().equalsIgnoreCase(track.getSinger()))) {
				throw new CustomReasonPhraseException(AAPI_105, "This data already exists");

			}
           
			// Only one entry allowed per title
			if (list!= null && list.get(1).toString().equalsIgnoreCase(track.getTitle())) {
				//return Response.status(428).entity(result).build();
				throw new CustomReasonPhraseException(AAPI_104, "Data exists already for the chosen option");
			}
		}
		mongoDBUtility.insertRow(track);
		return Response.status(201).entity(result).build();

	}
	
	

	@PUT
	@Path("/{singer}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Track updateTrackInJSON(@PathParam("singer") String id, Track track) throws UnknownHostException {
		MongoDBUtility mongoDBUtility = new MongoDBUtility();
		mongoDBUtility.updateRow(id,  track);
		return track;

		
	}
	
	@DELETE
	@Path("/{singer}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteTrackInJSON(@PathParam("singer") String id) throws UnknownHostException {
		MongoDBUtility mongoDBUtility = new MongoDBUtility();
		mongoDBUtility.deleteRow(id);
	}
		
		
	
	
}