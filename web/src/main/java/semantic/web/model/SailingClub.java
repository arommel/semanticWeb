package semantic.web.model;

public class SailingClub {
	//general properties
	private int id;
	private String name;
	private String abbreviation;
	private String sea;
	private String country;
	
	public SailingClub(int id, String name, String abbreviation, String sea, String country) {
		super();
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;
		this.sea = sea;
		this.country = country;
	}
	
	@Override
	public String toString() {
		return "SailingClub [id=" + id + ", name=" + name + ", abbreviation="
				+ abbreviation + ", sea=" + sea + ", country=" + country + "]";
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getSea() {
		return sea;
	}

	public String getCountry() {
		return country;
	}
}
