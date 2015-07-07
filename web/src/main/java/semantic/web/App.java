package semantic.web;

import semantic.web.parser.ClubParser;
import semantic.web.parser.RegattaParser;
import semantic.web.parser.SeaParser;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class App 
{
    public static void main( String[] args )
    {        
    	Model model = ModelFactory.createDefaultModel();
    	
    	System.out.println("Parse seas ...");
    	SeaParser seaParser = new SeaParser();
    	seaParser.loadSeas();
        
    	System.out.println("Parse sailing clubs ...");
    	ClubParser clubParser = new ClubParser();
    	clubParser.loadClubs();
        
    	System.out.println("Parse regatten ...");
    	RegattaParser regattaParser = new RegattaParser();
    	regattaParser.loadRegatten();
    	
    	//model.write(System.out, "RDF/XML-ABBREV");
    }
    
    
}
