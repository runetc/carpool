package CarpoolServer;

//탑승자 요구사항 정보
public class Request {
	private int request_id;
	private String id;
	private Schedule schedule;
	private int matching_group_num;
	private boolean confirm;

	public Request(int request_id, String id, Schedule schedule, int matching_group_num, boolean confirm) {
		this.request_id = request_id;
		this.id = id;
		this.schedule = schedule;
		this.matching_group_num = matching_group_num;
		this.confirm = confirm;
	}

	public int getRequestId() {
		return request_id;
	}

	public String getId() {
		return id;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public int getMatchingGroupNum() {
		return matching_group_num;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setRequestId(int request_id) {
		this.request_id = request_id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public void setMatchingGroupNum(int matching_group_num) {
		this.matching_group_num = matching_group_num;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}
}
