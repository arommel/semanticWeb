package semantic.web.model;

import java.util.ArrayList;
import java.util.Date;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

public class Regatta {
	//general properties
	private int id;
	private String name;
	private ArrayList<String> sailingTypes;
	private String startDate;
	private String endDate;
	private String sailingClubAbbreviation;
	
	//RDF properties
	private String baseURI = "http://htwk-leipzig.de/anja-rommel/";
	
	public Regatta(int id, String name, ArrayList<String> sailingTypes, String startDate,
			String endDate, String sailingClubAbbreviation) {
		super();
		this.id = id;
		this.name = name;
		this.sailingTypes = sailingTypes;
		this.startDate = startDate;
		this.endDate = endDate;
		this.sailingClubAbbreviation = sailingClubAbbreviation;
	}

	@Override
	public String toString() {
		return "Regatta [id=" + id + ", name=" + name + ", sailingTypes="
				+ sailingTypes + ", startDate=" + startDate + ", endDate="
				+ endDate + ", sailingClubAbbreviation="
				+ sailingClubAbbreviation + ", baseURI=" + baseURI + "]";
	}

	public void addToModell(Model model){
		String uri = this.baseURI + this.name.replace(" ", "_")
				.replace(';', '_')
				.replace(':', '_')
				.replaceAll("ß", "ss")
				.replaceAll("ä", "ae")
				.replaceAll("ö", "oe")
				.replaceAll("ü", "ue")
				.replaceAll("„", "")
				.replaceAll("“", "")
				.replaceAll("\\[", "")
				.replaceAll("\\]", "")
				.replace("Kopie_", "");
		
		Resource self = model.createResource(uri);
		
		self.addProperty(model.createProperty(this.baseURI, "name"), this.name);
		self.addProperty(model.createProperty(this.baseURI, "startDate"), this.startDate);
		self.addProperty(model.createProperty(this.baseURI, "endDate"), this.endDate);
		self.addProperty(model.createProperty(this.baseURI, "clubAbbreviation"), this.sailingClubAbbreviation);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getSailingTypes() {
		return sailingTypes;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getSailingClubAbbreviation() {
		return sailingClubAbbreviation;
	}

	public String getBaseURI() {
		return baseURI;
	}
}

