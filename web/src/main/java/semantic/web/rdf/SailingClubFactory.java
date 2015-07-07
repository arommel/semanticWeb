package semantic.web.rdf;

import semantic.web.model.SailingClub;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.XSD;

public class SailingClubFactory {

	public final String baseURI = "http://htwk-leipzig.de/anja-rommel/club/";
	
	private final OntClass clubClass;
	
	private final OntProperty idProperty;
	private final OntProperty nameProperty;
	private final OntProperty abbreviationProperty;
	private final OntProperty seaProperty;
	private final OntProperty countryProperty;

	public SailingClubFactory(final OntModel model) {
		super();
		
		clubClass = model.createClass(baseURI + "Club");
		
		idProperty = model.createOntProperty(baseURI + "Id");
		idProperty.addRange(XSD.xint);
		idProperty.addDomain(clubClass);
		
		nameProperty = model.createOntProperty(baseURI + "Name");
		nameProperty.addRange(XSD.xstring);
		nameProperty.addDomain(clubClass);
		
		abbreviationProperty = model.createOntProperty(baseURI + "Abbreviation");
		abbreviationProperty.addRange(XSD.xstring);
		abbreviationProperty.addDomain(clubClass);
		
		seaProperty = model.createOntProperty(baseURI + "Sea");
		seaProperty.addRange(XSD.xstring);
		seaProperty.addDomain(clubClass);
		
		countryProperty = model.createOntProperty(baseURI + "Country");
		countryProperty.addRange(XSD.xstring);
		countryProperty.addDomain(clubClass);
	}
	
	public void addClubToModel(SailingClub club, OntModel model){
		
		final Individual seaIndividual = model.createIndividual( baseURI + club.getId(), clubClass);
		
		seaIndividual.setPropertyValue(idProperty, ResourceFactory.createTypedLiteral(Integer.toString(club.getId()),XSDDatatype.XSDint));
		seaIndividual.setPropertyValue(nameProperty, ResourceFactory.createTypedLiteral(club.getName()));
		seaIndividual.setPropertyValue(abbreviationProperty, ResourceFactory.createTypedLiteral(club.getAbbreviation()));
		seaIndividual.setPropertyValue(seaProperty, ResourceFactory.createTypedLiteral(club.getSea()));
		seaIndividual.setPropertyValue(countryProperty, ResourceFactory.createTypedLiteral(club.getCountry()));
	}
	
}
