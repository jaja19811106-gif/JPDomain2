package dto;

public class DomainDto {
	private int id;
	private String domainName;
	private String registrant;
	private String ns1;
	private String ns2;
	private int period;

	// getter / setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getRegistrant() {
		return registrant;
	}

	public void setRegistrant(String registrant) {
		this.registrant = registrant;
	}

	public String getNs1() {
		return ns1;
	}

	public void setNs1(String ns1) {
		this.ns1 = ns1;
	}

	public String getNs2() {
		return ns2;
	}

	public void setNs2(String ns2) {
		this.ns2 = ns2;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
}