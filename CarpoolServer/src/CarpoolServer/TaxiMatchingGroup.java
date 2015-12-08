package CarpoolServer;

import java.util.ArrayList;

//택시 매칭 그룹 정보
public class TaxiMatchingGroup {
	private int group_num;
	private ArrayList<String> list;

	public TaxiMatchingGroup(int group_num) {
		this.group_num = group_num;
		list = new ArrayList<String>();
	}

	public int getGroupNum() {
		return group_num;
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setGroupNum(int group_num) {
		this.group_num = group_num;
	}

	public void addUser(String id) {
		list.add(id);
	}
}
