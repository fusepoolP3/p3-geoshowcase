package eu.fusepool.geo;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;


public class FusekiClient {
	
	private final String datasetUrl = "http://localhost:3030/fusepoolp3";
	private final String graph = "<urn:x-localinstance:/pat-pistesci>";
	
	
	/**
	 * Search for ski runs within a radius (in km)
	 */
	public List<String> searchSkiRuns(String lat, String lon, String radius) {
		
		List<String> skiruns = new ArrayList<String>();
		
		String serviceSparql = datasetUrl + "/sparql";
		
		String queryString = "PREFIX spatial: <http://jena.apache.org/spatial#> \n" +
		                     "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
				             "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
		                     "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n" +
				             "PREFIX patres: <http://www.territorio.provincia.tn.it/geodati/resource/> \n" + 
				             "SELECT ?ski ?label " +	
		                     "FROM " + graph + "\n" +
				             "WHERE { " + "\n" +		
						         "?place spatial:nearby (" + lat + " " + lon + " " + radius + " \'km\') . \n" +	
				                 "?ski geo:geometry ?place . \n" +
						         "?ski rdf:type patres:piste_da_sci . \n" +
				                 "?ski rdfs:label ?label . \n" +
						     "} ";
	
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qe = QueryExecutionFactory.sparqlService(serviceSparql, query);
		
		try {
		  if(qe != null){	
		    ResultSet results = qe.execSelect() ;
            for ( ; results.hasNext() ; ) {
              QuerySolution solution = results.nextSolution() ;  
              String label = solution.getLiteral("?label").getString();
              skiruns.add(label);              
            }
          }
		  
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally { 
			qe.close(); 
		}
		
		return skiruns;
		
	}
		
}
