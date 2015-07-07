package semantic.web.model;

public class DatePeriod {
	private String startDate;
	private String endDate;
	
	public DatePeriod(String startDate, String endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}
		
}
