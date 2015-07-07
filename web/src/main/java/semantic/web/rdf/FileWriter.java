package semantic.web.rdf;

import java.io.File;
import java.io.FileOutputStream;

import com.hp.hpl.jena.ontology.OntModel;

public class FileWriter {

	private String directory;

	public FileWriter() {
		super();
		this.directory = "Macintosh HD//Benutzer//anjarommel//Schreibtisch//rdf//";
	}

	public void writeModelToFile(OntModel model, String filename) {
		FileOutputStream fos = null;
		File file;

		try {
			file = new File(filename);
			fos = new FileOutputStream(file);
			model.write(fos, "RDF/XML-ABBREV");
			System.out.println("Write File " + filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
