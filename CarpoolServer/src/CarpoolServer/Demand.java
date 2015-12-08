package CarpoolServer;

//운전자 요구사항 정보
public class Demand {
	private int demand_id;
	private String license_num;
	private int charge;
	private int max_person;
	private int curr_person;
	private Schedule schedule;

	public Demand(int demand_id, String license_num, int charge, int max_person, int curr_person, Schedule schedule) {
		this.demand_id = demand_id;
		this.license_num = license_num;
		this.charge = charge;
		this.max_person = max_person;
		this.curr_person = curr_person;
		this.schedule = schedule;
	}

	public int getDemandId() {
		return demand_id;
	}

	public String getLicenseId() {
		return license_num;
	}

	public int getCharge() {
		return charge;
	}

	public int getMaxPerson() {
		return max_person;
	}

	public int getCurrPerson() {
		return curr_person;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setDemandId(int demand_id) {
		this.demand_id = demand_id;
	}

	public void setLicenseId(String license_num) {
		this.license_num = license_num;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public void setMaxPerson(int max_person) {
		this.max_person = max_person;
	}

	public void setCurrPerson(int curr_person) {
		this.curr_person = curr_person;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
}
