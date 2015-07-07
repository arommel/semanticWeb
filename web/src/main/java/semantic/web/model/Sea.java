package semantic.web.model;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Sea {
	//general properties
	private int id;
	private String name;
	private String country;
	private String city;
	private Double area;
	
	//RDF properties
	private String baseURI = "http://htwk-leipzig.de/anja-rommel/";
	
	public Sea(int id, String name, String country, String city, Double area) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
		this.city = city;
		this.area = area;
	}

	@Override
	public String toString() {
		return "Sea [id=" + id + ", name=" + name + ", country=" + country
				+ ", city=" + city + ", area=" + area + ", baseURI=" + baseURI
				+ "]";
	}

	public void addToModell(Model model){
		String uri = this.baseURI + this.name.replace(" ", "_")
				.replaceAll("ß", "ss")
				.replaceAll("ä", "ae")
				.replaceAll("ö", "oe")
				.replaceAll("ü", "ue");
		
		Resource self = model.createResource(uri);
		
		self.addProperty(model.createProperty(this.baseURI, "name"), this.name);
		self.addProperty(model.createProperty(this.baseURI, "country"), this.country);
		self.addProperty(model.createProperty(this.baseURI, "city"), this.city);
		self.addProperty(model.createProperty(this.baseURI, "area"), this.area.toString());
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public Double getArea() {
		return area;
	}

	public String getBaseURI() {
		return baseURI;
	}
}
