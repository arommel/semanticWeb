package semantic.web.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import semantic.web.model.SailingClub;
import semantic.web.rdf.FileWriter;
import semantic.web.rdf.SailingClubFactory;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class ClubParser {

	private ArrayList<SailingClub> clubList;
	private OntModel model;
	private SailingClubFactory clubFactory;

	private int id;

	public ClubParser() {
		super();
		this.id = 1;
		this.clubList = new ArrayList<SailingClub>();

		this.model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		this.clubFactory = new SailingClubFactory(this.model);
	}

	public void loadClubs() {
		this.parseBayernClubs();
		this.parseBrandenburgClubs();
		this.parseRheinlandPfalzClubs();
		this.parseSachsenClubs();

		this.model.setNsPrefix("SailingClub", this.clubFactory.baseURI);
		FileWriter fw = new FileWriter();
		fw.writeModelToFile(model, "sailingClubs.rdf");
	}

	private void parseBayernClubs() {
		String url = "http://www.bayernsail.de/index.php?id=127";
		String country = "Bayern";

		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements clubElements = doc.select("tr");

			for (int i = 1; i < clubElements.size(); i++) {
				Element clubRow = clubElements.get(i);
				Elements clubColoums = clubRow.children();

				String name = clubColoums.get(1).select("b").text();
				String abbreviation = clubColoums.get(0).select("b").text();
				String sea = clubColoums.get(2).select("a").text();

				if (sea.isEmpty()) {
					sea = "keine Angaben";
				}

				SailingClub club = new SailingClub(this.id, name, abbreviation, sea, country);
				this.id++;
				this.clubFactory.addClubToModel(club, model);
				this.clubList.add(club);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseBrandenburgClubs() {
		String[] urls = { "http://www.segel-vbs.de/83/reviere-vereine/brandenburg",
				"http://www.segel-vbs.de/84/reviere-vereine/cottbus",
				"http://www.segel-vbs.de/85/reviere-vereine/eberswalde",
				"http://www.segel-vbs.de/86/reviere-vereine/fuerstenwalde",
				"http://www.segel-vbs.de/395/reviere-vereine/zeuthener-see",
				"http://www.segel-vbs.de/87/reviere-vereine/neuruppin",
				"http://www.segel-vbs.de/88/reviere-vereine/potsdam" };

		String country = "Brandenburg";

		for (String url : urls) {
			Document doc;
			try {
				doc = Jsoup.connect(url).get();
				Elements clubElements = doc.select("table");
				Element clubTable = clubElements.get(16);
				clubElements = clubTable.select("tr");

				for (int i = 2; i < clubElements.size(); i++) {
					Element clubRow = clubElements.get(i);
					Elements clubColoums = clubRow.children();

					if (!clubColoums.get(0).html().equals("&nbsp;")) {

						// parse club name
						String name = "";

						Elements elName = clubColoums.get(2).select("a");
						if (elName.size() > 0) {
							name = elName.first().text();
						} else {
							Elements elFont = clubColoums.get(2).select("font");

							if (elFont.size() > 0) {
								name = clubColoums.get(2).text();
							} else {
								name = clubColoums.get(2).html().split("<strong>")[0].replaceAll("<br>", " ");
							}
						}

						Elements elAbbrevation = clubColoums.get(2).select("strong");
						String abbreviation = "";
						if (elAbbrevation.size() >= 1) {
							abbreviation = elAbbrevation.first().text().replace(" ", "");
						}
						// parse sea
						String sea = "";

						String[] s = clubColoums.get(2).html().split("</strong>");
						String st = "";
						if (s.length > 1) {
							st = s[1];

							String[] seas = st.split("<br>");
							int x = 1;
							String currentString = seas[x].replaceAll("Rev. ", "");

							if (currentString.contains("<div>")) {
								String[] strings = currentString.split("<div>");
								currentString = strings[0];
							}

							sea = currentString;
							/*
							 * while(currentString.charAt(currentString.length()-
							 * 1) == ','){ x++; currentString = seas[x]; sea =
							 * sea + currentString; }
							 */
						} else {
							sea = "keine Angaben";
						}

						SailingClub club = new SailingClub(this.id, name, abbreviation, sea.trim(), country);
						this.id++;
						this.clubFactory.addClubToModel(club, model);
						this.clubList.add(club);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void parseSachsenClubs() {
		String url = "http://segeln-sachsen.de/vereine/";
		String country = "Sachsen";

		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements clubElements = doc.select("tr");

			for (int i = 3; i < clubElements.size(); i++) {

				Element clubRow = clubElements.get(i);
				Elements clubColoums = clubRow.children();

				String name = clubColoums.get(1).select("strong").text();

				String abbreviation = clubColoums.get(2).text().split(" ")[0];
				String sea = clubColoums.get(1).children().last().text().replace('{', ' ').replace('}', ' ').trim();

				SailingClub club = new SailingClub(this.id, name, abbreviation, sea, country);
				this.id++;
				this.clubFactory.addClubToModel(club, model);
				this.clubList.add(club);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseRheinlandPfalzClubs() {
		String url = "http://www.lsv-rp.de/verband/vereine/";
		String country = "Rheinland-Pfalz";

		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements clubElements = doc.select("tr");

			for (int i = 2; i < clubElements.size(); i++) {
				Element clubRow = clubElements.get(i);
				Elements clubColoums = clubRow.children();

				String name = clubColoums.get(1).text();
				String abbreviation = clubColoums.get(2).text();
				String sea = clubColoums.get(3).text();

				SailingClub club = new SailingClub(this.id, name, abbreviation, sea, country);
				this.id++;
				this.clubFactory.addClubToModel(club, model);
				this.clubList.add(club);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
