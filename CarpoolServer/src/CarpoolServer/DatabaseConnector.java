package CarpoolServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//DB ¿¬°áÀÚ
public class DatabaseConnector {
	private Connection con = null;
	private String ip = "127.0.0.1";
	private String id = "root";
	private String password = "1234";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public boolean connect() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + ip, id, password);
			Statement st = con.createStatement();
			st.executeQuery("use carpool");
			st.close();

			return true;
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());

			return false;
		}
	}

	public boolean close() {
		try {
			con.close();

			return true;
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());

			return false;
		}
	}

	public ResultSet select(String table_name) {
		String query = "select * from " + table_name;

		ResultSet rs = null;
		try {
			Statement st = con.createStatement();

			rs = st.executeQuery(query);

			if (st.execute(query)) {
				rs = st.getResultSet();
			}
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}

		return rs;
	}

	public ResultSet select(String table_name, String target_col_name, String arg) {
		String query = "select * from " + table_name + " where " + target_col_name + "='" + arg + "'";

		ResultSet rs = null;
		try {
			Statement st = con.createStatement();

			rs = st.executeQuery(query);

			if (st.execute(query)) {
				rs = st.getResultSet();
			}
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}

		return rs;
	}

	public boolean insert(String table_name, String[] col_names, String[] args, boolean needSchedule) {
		String query = "insert into " + table_name + " (";
		for (int i = 0; i < col_names.length - 1; i++) {
			query += col_names[i] + ", ";
		}
		query += col_names[col_names.length - 1] + ") values (";
		for (int i = 0; i < args.length - 1; i++) {
			query += "'" + args[i] + "', ";
		}
		if (!needSchedule) {
			query += "'" + args[args.length - 1] + "')";
		} else {
			query += args[args.length - 1] + ")";
		}

		try {
			Statement st = con.createStatement();

			st.execute(query);

			return true;
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());

			return false;
		}
	}

	public boolean update(String table_name, String col_name, String arg, String value_col_name, String value) {
		String query = "update " + table_name + " set " + value_col_name + "='" + value + "' where " + col_name + "='"
				+ arg + "'";

		try {
			Statement st = con.createStatement();

			st.execute(query);

			return true;
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());

			return false;
		}
	}

	public boolean delete(String table_name, String col_name, String arg) {
		String query = "delete from " + table_name + " where " + col_name + "='" + arg + "'";

		try {
			Statement st = con.createStatement();

			st.execute(query);

			return true;
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());

			return false;
		}
	}

	public ResultSet excute(String query) {
		ResultSet rs = null;
		try {
			Statement st = con.createStatement();

			rs = st.executeQuery(query);

			if (st.execute(query)) {
				rs = st.getResultSet();
			}
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}

		return rs;
	}

	public User getUser(String id) {
		User result = null;

		ResultSet rs = select("user", "id", id);
		try {
			rs.first();
			result = new User(id, rs.getString("name"), rs.getString("password"), rs.getString("mail"),
					rs.getString("phone"), null);
		} catch (SQLException e) {
			result = new User(id, "", "", "", "", null);
		}

		return result;
	}

	public Car getCar(String license_num) {
		Car result = null;

		ResultSet rs = select("car", "license_num", license_num);
		try {
			rs.first();
			result = new Car(rs.getString("car_model"), rs.getString("car_num"), rs.getString("car_color"));
		} catch (SQLException e) {
		}

		return result;
	}

	public Driver getDriver(String id) {
		Driver result = null;

		ResultSet rs = select("driver", "id", id);
		try {
			rs.first();
			result = new Driver(id, rs.getString("license_num"), getCar(rs.getString("license_num")));
		} catch (SQLException e) {
		}

		return result;
	}

	public ArrayList<Post> getPosts() {
		ArrayList<Post> result = new ArrayList<Post>();

		ResultSet rs = select("post");
		try {
			rs.first();
			Date d = sdf.parse(rs.getString("created_datetime"));
			result.add(new Post(rs.getInt("post_id"), rs.getString("id"), rs.getString("title"),
					rs.getString("content"), d));
			while (rs.next()) {
				d = sdf.parse(rs.getString("created_datetime"));
				result.add(new Post(rs.getInt("post_id"), rs.getString("id"), rs.getString("title"),
						rs.getString("content"), d));
			}
		} catch (SQLException | ParseException e) {
		}

		return result;
	}

	public ArrayList<Comment> getComments(int post_id) {
		ArrayList<Comment> result = new ArrayList<Comment>();

		ResultSet rs = select("comment", "post_id", Integer.toString(post_id));
		try {
			rs.first();
			Date d = sdf.parse(rs.getString("created_datetime"));
			result.add(new Comment(rs.getInt("comment_id"), rs.getString("id"), rs.getString("content"), d));
			while (rs.next()) {
				d = sdf.parse(rs.getString("created_datetime"));
				result.add(new Comment(rs.getInt("comment_id"), rs.getString("id"), rs.getString("content"), d));
			}
		} catch (SQLException | ParseException e) {
		}

		return result;
	}

	public Confirm getConfirm(String id) {
		Confirm result = null;

		ResultSet rs = select("confirm", "id", id);
		try {
			rs.first();
			Date d = sdf.parse(rs.getString("issue_datetime"));
			result = new Confirm(id, rs.getString("confirm_mail"), d);
		} catch (SQLException | ParseException e) {
		}

		return result;
	}

	public ArrayList<Evaluation> getEvaluations(String id) {
		ArrayList<Evaluation> result = new ArrayList<Evaluation>();

		ResultSet rs = select("evaluation", "id", id);
		try {
			rs.first();
			result.add(new Evaluation(rs.getInt("evaluation_id"), id, rs.getInt("grade"), rs.getString("review")));
			while (rs.next()) {
				result.add(new Evaluation(rs.getInt("evaluation_id"), id, rs.getInt("grade"), rs.getString("review")));
			}
		} catch (SQLException e) {
		}

		return result;
	}

	public Schedule getSchedule(int schedule_id) {
		Schedule result = null;

		ResultSet rs = select("schedule", "schedule_id", Integer.toString(schedule_id));
		try {
			rs.first();
			Date d = sdf.parse(rs.getString("datetime"));
			result = new Schedule(schedule_id, d, rs.getDouble("start_x"), rs.getDouble("start_y"),
					rs.getDouble("end_x"), rs.getDouble("end_y"));
		} catch (SQLException | ParseException e) {
		}

		return result;
	}

	public ArrayList<Demand> getDemands() {
		ArrayList<Demand> result = new ArrayList<Demand>();

		ResultSet rs = select("demand");
		try {
			rs.first();
			result.add(new Demand(rs.getInt("demand_id"), rs.getString("license_num"), rs.getInt("charge"),
					rs.getInt("max_person"), rs.getInt("curr_person"), getSchedule(rs.getInt("schedule_id"))));
			while (rs.next()) {
				result.add(new Demand(rs.getInt("demand_id"), rs.getString("license_num"), rs.getInt("charge"),
						rs.getInt("max_person"), rs.getInt("curr_person"), getSchedule(rs.getInt("schedule_id"))));
			}
		} catch (SQLException e) {
		}

		return result;
	}

	public ArrayList<Request> getRequests() {
		ArrayList<Request> result = new ArrayList<Request>();

		ResultSet rs = select("request");
		try {
			rs.first();
			result.add(new Request(rs.getInt("request_id"), rs.getString("id"), getSchedule(rs.getInt("schedule_id")),
					rs.getInt("matching_group_num"), rs.getBoolean("confirm")));
			while (rs.next()) {
				result.add(
						new Request(rs.getInt("request_id"), rs.getString("id"), getSchedule(rs.getInt("schedule_id")),
								rs.getInt("matching_group_num"), rs.getBoolean("confirm")));
			}
		} catch (SQLException e) {
		}

		return result;
	}

	public CarMatchingGroup getCarMatchingGroup(int demand_id) {
		CarMatchingGroup cmg = null;

		ResultSet rs = select("state");
		try {
			rs.first();
			cmg = new CarMatchingGroup(demand_id);
			int temp = rs.getInt("demand_id");
			if (temp == demand_id) {
				cmg.addUser(rs.getString("id"));
			}
			while (rs.next()) {
				temp = rs.getInt("demand_id");
				if (temp == demand_id) {
					cmg.addUser(rs.getString("id"));
				}
			}
		} catch (SQLException e) {
		}

		return cmg;
	}

	public ArrayList<State> getState() {
		ArrayList<State> result = new ArrayList<State>();

		ResultSet rs = select("state");
		try {
			rs.first();
			result.add(new State(rs.getInt("state_id"), rs.getString("id"), rs.getInt("demand_id"),
					rs.getBoolean("confirm")));
			while (rs.next()) {
				result.add(new State(rs.getInt("state_id"), rs.getString("id"), rs.getInt("demand_id"),
						rs.getBoolean("confirm")));
			}
		} catch (SQLException e) {
		}

		return result;
	}

	public boolean insertUser(User user) {
		String[] col_names = { "id", "password", "mail", "name", "phone" };
		String[] args = { user.getId(), user.getPassword(), user.getMail(), user.getName(), user.getPhone() };
		return insert("user", col_names, args, false);
	}

	public boolean insertCar(Car car, String license_num) {
		String[] col_names = { "license_num", "car_num", "car_model", "car_color" };
		String[] args = { license_num, car.getCarNum(), car.getCarModel(), car.getCarColor() };
		return insert("car", col_names, args, false);
	}

	public boolean insertDriver(Driver driver) {
		String[] col_names = { "license_num", "id" };
		String[] args = { driver.getLicenseNum(), driver.getId() };
		return insert("driver", col_names, args, false);
	}

	public boolean insertPost(Post post) {
		String[] col_names = { "title", "content", "created_datetime", "id" };
		String[] args = { post.getTitle(), post.getContent(), sdf.format(post.getCreatedDatetime()), post.getId() };
		return insert("post", col_names, args, false);
	}

	public boolean insertComment(Comment comment, int post_id) {
		String[] col_names = { "content", "created_datetime", "id", "post_id" };
		String[] args = { comment.getContent(), sdf.format(comment.getCreatedDatetime()), comment.getId(),
				Integer.toString(post_id) };
		return insert("comment", col_names, args, false);
	}

	public boolean insertConfirm(Confirm confirm) {
		String[] col_names = { "id", "confirm_mail", "issue_datetime" };
		String[] args = { confirm.getId(), confirm.getConfirmMail(), sdf.format(confirm.getIssueDatetime()) };
		return insert("confirm", col_names, args, false);
	}

	public boolean insertEvaluation(Evaluation evaluation) {
		String[] col_names = { "grade", "review", "id" };
		String[] args = { Integer.toString(evaluation.getGrade()), evaluation.getReview(), evaluation.getId() };
		return insert("evaluation", col_names, args, false);
	}

	public boolean insertSchedule(Schedule schedule) {
		String[] col_names = { "datetime", "start_x", "start_y", "end_x", "end_y" };
		String[] args = { sdf.format(schedule.getDatetime()), Double.toString(schedule.getStartX()),
				Double.toString(schedule.getStartY()), Double.toString(schedule.getEndX()),
				Double.toString(schedule.getEndY()) };
		return insert("schedule", col_names, args, false);
	}

	public boolean insertDemand(Demand demand) {
		String[] col_names = { "charge", "max_person", "curr_person", "license_num", "schedule_id" };
		String[] args = { Integer.toString(demand.getCharge()), Integer.toString(demand.getMaxPerson()),
				Integer.toString(demand.getCurrPerson()), demand.getLicenseId(), "LAST_INSERT_ID()" };
		return insert("demand", col_names, args, true);
	}

	public boolean insertRequest(Request request) {
		String[] col_names = { "confirm", "matching_group_num", "id", "schedule_id" };
		int c;
		if (request.isConfirm()) {
			c = 1;
		} else {
			c = 0;
		}
		String[] args = { Integer.toString(c), Integer.toString(request.getMatchingGroupNum()), request.getId(),
				"LAST_INSERT_ID()" };
		return insert("request", col_names, args, true);
	}

	public boolean insertState(String id, int demand_id, boolean confirm) {
		String[] col_names = { "id", "demand_id", "confirm" };
		int i;
		if (confirm) {
			i = 1;
		} else {
			i = 0;
		}
		String[] args = { id, Integer.toString(demand_id), Integer.toString(i) };
		return insert("state", col_names, args, false);
	}
}
