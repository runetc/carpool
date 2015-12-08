package CarpoolServer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//유저 정보
public class User {
	private String id;
	private String name;
	private String password;
	private String mail;
	private String phone;
	private Driver driver_info;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public User(String id, String name, String password, String mail, String phone, Driver driver_info) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.mail = mail;
		this.phone = phone;
		this.driver_info = driver_info;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getMail() {
		return mail;
	}

	public String getPhone() {
		return phone;
	}

	public Driver getDriverInfo() {
		return driver_info;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setDriverInfo(Driver driver_info) {
		this.driver_info = driver_info;
	}

	public String createConfirmNum() {
		String confrimNum = Integer.toString((int) (Math.random() * 10000 - 1));

		return confrimNum;
	}

	public boolean confirmNumCheck(Confirm confirm, String confirm_mail) {

		if (confirm_mail.equals(confirm.getConfirmMail()) == false)
			return false;

		return true;
	}

	public boolean confirmTimeCheck(Confirm confirm) {

		Date sand_time = confirm.getIssueDatetime();
		Date join_currentTime = new Date();
		try {
			join_currentTime = sdf.parse(sdf.format(join_currentTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar sand_timeCal = Calendar.getInstance();
		Calendar join_currentTimeCal = Calendar.getInstance();

		sand_timeCal.setTime(sand_time);
		join_currentTimeCal.setTime(join_currentTime);

		long diffMillis = join_currentTimeCal.getTimeInMillis() - sand_timeCal.getTimeInMillis();
		int diff = (int) (diffMillis / (24 * 60 * 60 * 1000));

		if (diff > 300)
			return false;

		return true;
	}

	public ArrayList<Demand> CarMatching(Schedule boarderSchedule, ArrayList<Demand> demands) {
		ArrayList<Demand> result = new ArrayList<Demand>();

		for (int i = 0; i < demands.size(); i++) {
			long boarderSTime = boarderSchedule.getDatetime().getTime();
			long demandSTime = demands.get(i).getSchedule().getDatetime().getTime();
			long diff = (boarderSTime - demandSTime) / (60 * 1000);

			if (diff >= -30 && diff <= 30) {
				if (Matching(demands.get(i).getSchedule().getStartX(), demands.get(i).getSchedule().getStartY(),
						demands.get(i).getSchedule().getEndX(), demands.get(i).getSchedule().getEndY(),
						boarderSchedule.getStartX(), boarderSchedule.getStartY(), boarderSchedule.getEndX(),
						boarderSchedule.getEndY())) {
					result.add(demands.get(i));
				}
			}
		}

		return result;
	}

	public Demand selectDemand(ArrayList<Demand> demands, int demandId) {
		for (int i = 0; i < demands.size(); i++)
			if (demands.get(i).getDemandId() == demandId)
				return demands.get(i);
		return null;
	}

	public Post selectPost(ArrayList<Post> postList, int postId) {

		Post postNull = new Post(0, "Nothing", "Nothing", "Nothing", null);

		for (int i = 0; i < postList.size(); i++) {
			if (postList.get(i).getPostId() == postId)
				return postList.get(i);
		}

		return postNull;
	}

	public static boolean Matching(double start_x1, double start_y1, double end_x1, double end_y1, double start_x2,
			double start_y2, double end_x2, double end_y2) {
		double dsx = start_x1 - start_x2;
		double dsy = start_y1 - start_y2;
		double dex = end_x1 - end_x2;
		double dey = end_y1 - end_y2;

		if (dsx <= 0.005 && dsx >= -0.005 && dsy <= 0.005 && dsy >= -0.005 && dex <= 0.005 && dex >= -0.005
				&& dey <= 0.005 && dey >= -0.005) {
			return true;
		} else {
			return false;
		}
	}

	public boolean TaxiMatching(Schedule taxiSchedule, Schedule requests) {
		if (Matching(taxiSchedule.getStartX(), taxiSchedule.getStartY(), taxiSchedule.getEndX(), taxiSchedule.getEndY(),
				requests.getStartX(), requests.getStartY(), requests.getEndX(), requests.getEndY())) {
			return true;
		}

		return false;
	}
}
