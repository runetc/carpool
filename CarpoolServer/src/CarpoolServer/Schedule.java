package CarpoolServer;

import java.util.Date;

//일정 정보
public class Schedule {
	private int schedule_id;
	private Date datetime;
	private double start_x;
	private double start_y;
	private double end_x;
	private double end_y;

	public Schedule(int schedule_id, Date datetime, double start_x, double start_y, double end_x, double end_y) {
		this.schedule_id = schedule_id;
		this.datetime = datetime;
		this.start_x = start_x;
		this.start_y = start_y;
		this.end_x = end_x;
		this.end_y = end_y;
	}

	public int getScheduleId() {
		return schedule_id;
	}

	public Date getDatetime() {
		return datetime;
	}

	public double getStartX() {
		return start_x;
	}

	public double getStartY() {
		return start_y;
	}

	public double getEndX() {
		return end_x;
	}

	public double getEndY() {
		return end_y;
	}

	public void setScheduleId(int schedule_id) {
		this.schedule_id = schedule_id;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public void setStartX(double start_x) {
		this.start_x = start_x;
	}

	public void setStartY(double start_y) {
		this.start_y = start_y;
	}

	public void setEndX(double end_x) {
		this.end_x = end_x;
	}

	public void setEndY(double end_y) {
		this.end_y = end_y;
	}
}
