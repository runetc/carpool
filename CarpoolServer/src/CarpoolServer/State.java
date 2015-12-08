package CarpoolServer;

public class State {
	private int state_id;
	private String id;
	private int demand_id;
	private boolean confirm;

	public State(int state_id, String id, int demand_id, boolean confirm) {
		this.state_id = state_id;
		this.id = id;
		this.demand_id = demand_id;
		this.confirm = confirm;
	}

	public int getState_id() {
		return state_id;
	}

	public String getId() {
		return id;
	}

	public int getDemandId() {
		return demand_id;
	}

	public boolean isConfirmed() {
		return confirm;
	}

	public void setStateId(int state_id) {
		this.state_id = state_id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDemandId(int demand_id) {
		this.demand_id = demand_id;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}
}
