package semantic.web.rdf;

import semantic.web.model.Sea;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.XSD;

public class SeaFactory {
	public final String baseURI = "http://htwk-leipzig.de/anja-rommel/sea/";
	
	private final OntClass seaClass;
	
	private final OntProperty idProperty;
	private final OntProperty nameProperty;
	private final OntProperty countryProperty;
	private final OntProperty cityProperty;
	private final OntProperty areaProperty;
	
	public SeaFactory(final OntModel model) {
		super();
		
		seaClass = model.createClass(baseURI + "Sea" );
		 
		idProperty = model.createOntProperty(baseURI + "Id");
		idProperty.addRange(XSD.xint);
		idProperty.addDomain(seaClass);
		
		nameProperty = model.createOntProperty(baseURI + "Name");
		nameProperty.addRange(XSD.xstring);
		nameProperty.addDomain(seaClass);
		
		countryProperty = model.createOntProperty(baseURI + "Country");
		countryProperty.addRange(XSD.xstring);
		countryProperty.addDomain(seaClass);
		 
		cityProperty = model.createOntProperty(baseURI + "City");
		cityProperty.addRange(XSD.xstring);
		cityProperty.addDomain(seaClass);
		
		areaProperty = model.createOntProperty(baseURI + "Area");
		areaProperty.addRange(XSD.xdouble);
		areaProperty.addDomain(seaClass);
	}
	
	public void addSeaToModel(Sea sea, OntModel model){
		
		final Individual seaIndividual = model.createIndividual( baseURI + sea.getId(), seaClass);
		
		seaIndividual.setPropertyValue(idProperty, ResourceFactory.createTypedLiteral(Integer.toString(sea.getId()),XSDDatatype.XSDint));
		seaIndividual.setPropertyValue(nameProperty, ResourceFactory.createTypedLiteral(sea.getName()));
		seaIndividual.setPropertyValue(countryProperty, ResourceFactory.createTypedLiteral(sea.getCountry()));
		seaIndividual.setPropertyValue(cityProperty, ResourceFactory.createTypedLiteral(sea.getCity()));
		seaIndividual.setPropertyValue(areaProperty, ResourceFactory.createTypedLiteral(sea.getArea()));
		
	}

}
