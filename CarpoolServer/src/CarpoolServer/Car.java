package CarpoolServer;

//Â÷ Á¤º¸
public class Car {
	private String car_model;
	private String car_num;
	private String car_color;

	public Car(String car_model, String car_num, String car_color) {
		this.car_model = car_model;
		this.car_num = car_num;
		this.car_color = car_color;
	}

	public String getCarModel() {
		return car_model;
	}

	public String getCarNum() {
		return car_num;
	}

	public String getCarColor() {
		return car_color;
	}

	public void setCarModel(String car_model) {
		this.car_model = car_model;
	}

	public void setCarNum(String car_num) {
		this.car_num = car_num;
	}

	public void setCarColor(String car_color) {
		this.car_color = car_color;
	}
}
