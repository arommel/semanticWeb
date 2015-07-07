package semantic.web.rdf;

import semantic.web.model.Regatta;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.XSD;

public class RegattaFactory {
	public final String baseURI = "http://htwk-leipzig.de/anja-rommel/regatta/";
	
	private final OntClass regattaClass;
	
	private final OntProperty idProperty;
	private final OntProperty nameProperty;
	private final OntProperty startDateProperty;
	private final OntProperty endDateProperty;
	private final OntProperty clubAbbreviationProperty;
		
	public RegattaFactory(final OntModel model) {
		super();
		
		regattaClass = model.createClass(baseURI + "Regatta" );
		
		idProperty = model.createOntProperty(baseURI + "Id");
		idProperty.addRange(XSD.xint);
		idProperty.addDomain(regattaClass);
		
		nameProperty = model.createOntProperty(baseURI + "Name");
		nameProperty.addRange(XSD.xstring);
		nameProperty.addDomain(regattaClass);
		
		startDateProperty = model.createOntProperty(baseURI + "StartDate");
		startDateProperty.addRange(XSD.xstring);
		startDateProperty.addDomain(regattaClass);
		
		endDateProperty = model.createOntProperty(baseURI + "EndDate");
		endDateProperty.addRange(XSD.xstring);
		endDateProperty.addDomain(regattaClass);
		
		clubAbbreviationProperty = model.createOntProperty(baseURI + "ClubAbbreviation");
		clubAbbreviationProperty.addRange(XSD.xstring);
		clubAbbreviationProperty.addDomain(regattaClass);
	}
	
	public void addRegattaToModel(Regatta regatta, OntModel model){
		
		final Individual seaIndividual = model.createIndividual( baseURI + regatta.getId(), regattaClass);
		
		seaIndividual.setPropertyValue(idProperty, ResourceFactory.createTypedLiteral(Integer.toString(regatta.getId()),XSDDatatype.XSDint));
		seaIndividual.setPropertyValue(nameProperty, ResourceFactory.createTypedLiteral(regatta.getName()));
		seaIndividual.setPropertyValue(startDateProperty, ResourceFactory.createTypedLiteral(regatta.getStartDate()));
		seaIndividual.setPropertyValue(endDateProperty, ResourceFactory.createTypedLiteral(regatta.getEndDate()));
		seaIndividual.setPropertyValue(clubAbbreviationProperty, ResourceFactory.createTypedLiteral(regatta.getSailingClubAbbreviation()));
	}
}
