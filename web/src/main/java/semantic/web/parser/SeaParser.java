package semantic.web.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import semantic.web.model.Sea;
import semantic.web.rdf.FileWriter;
import semantic.web.rdf.SeaFactory;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class SeaParser {	
	private ArrayList<Sea> seaList;
	private String baseURL;
	private OntModel model;
	private SeaFactory seaFactory;
	
	private int id;

	public SeaParser() {
		super();
		this.id = 1;
		this.seaList = new ArrayList<Sea>();
		this.baseURL = "https://de.wikipedia.org/wiki/Liste_von_Seen_in_Deutschland/";
		
		this.model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM );
		this.seaFactory = new SeaFactory(this.model);
	}
	
	private void parsePage (final String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements seaElements = doc.select("tr");
			
			for (int i = 1; i < seaElements.size(); i++) {
				Element seaRow = seaElements.get(i);
				Elements seaAttributes = seaRow.children();	
				
				double size;
				
				if (!seaAttributes.get(0).text().equals("Name")) {
					try {
						size = Double.parseDouble(seaAttributes.get(3).text().replace(',', '.'));
					} catch (NumberFormatException e) {
						size = 0.0;
					}
					
					Sea seaObject = new Sea(this.id, seaAttributes.get(0).text(), seaAttributes.get(2).text(), seaAttributes.get(1).text(), size);
					this.id++;
					this.seaFactory.addSeaToModel(seaObject, this.model);
					this.seaList.add(seaObject);
				} 
			}
			//System.out.println(doc.title());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadSeas() {
		
		char character = 'A';
		int i = 1;
		
		do {
			
			String url = this.baseURL;
			
			if(character == 'P'){
				url = url + "P–Q";
				character += 2;
				i += 2;
			} else if (character == 'X'){
				url = url + "X–Z";
				character += 3;
				i += 3;
			}
			else {
				url = url + character;
				character++;
				i++;
			}
			
			this.parsePage(url); 
			
		} while (i <= 26);
		
		this.model.setNsPrefix("Sea", this.seaFactory.baseURI);
		FileWriter fw = new FileWriter();
		fw.writeModelToFile(model, "sea.rdf");
	}
}
