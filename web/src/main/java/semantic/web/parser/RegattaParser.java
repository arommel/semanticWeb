package semantic.web.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import semantic.web.model.DatePeriod;
import semantic.web.model.Regatta;
import semantic.web.rdf.FileWriter;
import semantic.web.rdf.RegattaFactory;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class RegattaParser {
	private ArrayList<Regatta> regattaList;
	private String baseURL;
	private DatePeriod[] periods;
	
	private OntModel model;
	private RegattaFactory regattaFactory;
	
	private int id;
	
	public RegattaParser() {
		super();
		this.id = 1;
		this.regattaList = new ArrayList<Regatta>();
		this.baseURL = "http://www.raceoffice.org/index.php?";
		
		this.periods = new DatePeriod[15];
		this.periods[0] = new DatePeriod("01-01-2015", "31-03-2015");
		this.periods[1] = new DatePeriod("01-04-2015", "30-04-2015");
	    this.periods[2] = new DatePeriod("01-05-2015", "08-05-2015");
	    this.periods[3] = new DatePeriod("09-05-2015", "15-05-2015");
	    this.periods[4] = new DatePeriod("16-05-2015", "29-05-2015");
	    this.periods[5] = new DatePeriod("30-05-2015", "12-06-2015");
	    this.periods[6] = new DatePeriod("13-06-2015", "19-06-2015");
	    this.periods[7] = new DatePeriod("20-06-2015", "26-06-2015");
	    this.periods[8] = new DatePeriod("27-06-2015", "05-07-2015");
	    this.periods[9] = new DatePeriod("06-07-2015", "26-07-2015");
	    this.periods[10] = new DatePeriod("27-07-2015", "21-08-2015"); 
	    this.periods[11] = new DatePeriod("22-08-2015", "03-09-2015");
	    this.periods[12] = new DatePeriod("04-09-2015", "17-09-2015");
	    this.periods[13] = new DatePeriod("18-09-2015", "05-10-2015");
	    this.periods[14] = new DatePeriod("06-10-2015", "31-12-2015");
		
	    this.model  = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM );
	    this.regattaFactory = new RegattaFactory(this.model);
	}
	
	public void loadRegatten(){
		
		for (DatePeriod datePeriod : periods) {
			this.parsePage(this.getUrlForDatePeriod(datePeriod));
		}
		
		this.model.setNsPrefix("Regatta", this.regattaFactory.baseURI);
		FileWriter fw = new FileWriter();
		fw.writeModelToFile(model, "regatten.rdf");
		//model.write(System.out, "N-TRIPLES");
	}
	
	private String getUrlForDatePeriod(DatePeriod period){
		return this.baseURL + "s_von=" + period.getStartDate() + "&s_bis=" + period.getEndDate() + "&s_search=";
	}
	
	private void parsePage(String url){
		try {
			Document doc = Jsoup.connect(url).get();
			Elements regElements = doc.select("tr");
			
			for (int i = 2; i < regElements.size(); i++) {
				Element regRow = regElements.get(i);
				Elements regAttributes = regRow.children();	
				
				if (!regRow.text().contains("Heute ist der ")) {
					String name = regAttributes.get(1).select("a").get(0).child(0).text();
					
					ArrayList<String> sailingTypes = new ArrayList<String>();
					String[] types = regAttributes.get(3).html().split("<br>");
					for (String type : types) {
						sailingTypes.add(type);
					}
					
					String startDate = "";
					String endDate = "";
					
					String[] dates = regAttributes.get(0).html().split("<br>");
					startDate = dates[0];
					if (dates.length > 1) {
						endDate = dates[1];
					}

					String[] html = regAttributes.get(1).text().split("\\(");
					String part = html[html.length-1];
					String wholeAbbrevations = part.split("\\)")[0];
					String sailingClubAbbreviation = wholeAbbrevations.split(",")[0].trim();
					

					Regatta reg = new Regatta(this.id, name, sailingTypes, startDate, endDate, sailingClubAbbreviation);
					this.id++;
					this.regattaFactory.addRegattaToModel(reg, model);
					this.regattaList.add(reg);
					
				/*	if (name.equals("ECK_Days 2015")) {
						System.out.println(reg);
					}*/
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
