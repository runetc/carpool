package CarpoolServer;

import java.util.ArrayList;

//자가용 매칭 그룹 정보
public class CarMatchingGroup {
	private int demand_id;
	private ArrayList<String> list;

	public CarMatchingGroup(int demand_id) {
		this.demand_id = demand_id;
		list = new ArrayList<String>();
	}

	public int getDemandId() {
		return demand_id;
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setDemandId(int demand_id) {
		this.demand_id = demand_id;
	}

	public void addUser(String id) {
		list.add(id);
	}
}
