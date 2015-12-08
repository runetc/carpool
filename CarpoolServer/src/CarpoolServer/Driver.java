package CarpoolServer;

//운전자 정보
public class Driver {
	private String id;
	private String license_num;
	private Car car;

	public Driver(String id, String license_num, Car car) {
		this.id = id;
		this.license_num = license_num;
		this.car = car;
	}

	public String getId() {
		return id;
	}

	public String getLicenseNum() {
		return license_num;
	}

	public Car getCar() {
		return car;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLicenseNum(String license_num) {
		this.license_num = license_num;
	}

	public void setCar(Car car) {
		this.car = car;
	}
}
