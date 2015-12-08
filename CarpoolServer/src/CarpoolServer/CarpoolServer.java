package CarpoolServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//서버 시작 지점
public class CarpoolServer implements Runnable {
	private ServerSocket serverSocket;
	private Thread[] threadArr;
	private static DatabaseConnector dc;
	private static int matchingNum = 0;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public CarpoolServer(int num) {
		try {
			serverSocket = new ServerSocket(6666);
			threadArr = new Thread[num];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		for (int i = 0; i < threadArr.length; i++) {
			threadArr[i] = new Thread(this);
			threadArr[i].start();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println(getTime() + " 가 연결 요청을 기다립니다.");

				Socket socket = serverSocket.accept();
				System.out.println(getTime() + " " + socket.getInetAddress() + "로부터 연결요청이 들어왔습니다.");

				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				DataInputStream dis = new DataInputStream(in);
				DataOutputStream dos = new DataOutputStream(out);

				String who = dis.readUTF(); // 접속자 확인
				User user = dc.getUser(who);

				boolean isEnd = false;
				while (true) {
					int what = dis.readInt(); // 원하는 서비스 확인
					switch (what) {
					case 0:
						isEnd = true;
						break;
					case 1: // 인증번호 발송
						user.setMail(dis.readUTF());
						MailSender mailsender = new MailSender();
						String confrim_mail = user.createConfirmNum();

						Date currentTime = new Date();

						Confirm sand_Confirm = new Confirm(user.getId(), confrim_mail, currentTime);

						dc.insertConfirm(sand_Confirm);
						if (mailsender.send(user.getMail(), "인증메일", confrim_mail))
							dos.writeBoolean(true);
						else
							dos.writeBoolean(false);

						break;
					case 2: // 회원가입
						user.setMail(dis.readUTF());
						user.setName(dis.readUTF());
						user.setPassword(dis.readUTF());
						user.setPhone(dis.readUTF());
						String inconfirm_mail = dis.readUTF();

						// 시간 인증번호 체크
						if (user.confirmTimeCheck(dc.getConfirm(user.getId()))
								&& user.confirmNumCheck(dc.getConfirm(user.getId()), inconfirm_mail)) {
							dc.insertUser(user);
							dos.writeBoolean(true);
						} else
							dos.writeBoolean(false);

						break;
					case 3:// 로그인
						String login_inpassword = dis.readUTF();

						if (user.getPassword() == null) {
							dos.writeBoolean(false);
						} else if (user.getPassword().equals(login_inpassword)) {
							dos.writeBoolean(true);
						} else {
							dos.writeBoolean(false);
						}

						break;
					case 4:// 비밀번호 찾기 인증번호
						MailSender mailsenderF = new MailSender();
						String confrim_mailF = user.createConfirmNum();

						Date currentTimeF = new Date();

						Confirm sand_ConfirmF = new Confirm(user.getId(), confrim_mailF, currentTimeF);

						if (mailsenderF.send(dis.readUTF(), "인증메일", confrim_mailF)) {
							dc.insertConfirm(sand_ConfirmF);
							dos.writeBoolean(true);
						} else
							dos.writeBoolean(false);

						break;
					case 5:// 비밀번호 인증
						String confrim_mailFC = dis.readUTF();

						if (user.confirmTimeCheck(dc.getConfirm(user.getId()))
								&& user.confirmNumCheck(dc.getConfirm(user.getId()), confrim_mailFC)) {
							dos.writeBoolean(true);
						} else {
							dos.writeBoolean(false);
						}

						break;
					case 6:// 비밀번호 재설정
						if (dc.update("user", "id", user.getId(), "password", dis.readUTF())) {
							dos.writeBoolean(true);
						} else {
							dos.writeBoolean(false);
						}
						break;
					case 7:// 택시 매칭
						Schedule taxiSchedule = new Schedule(0, new Date(), Double.parseDouble(dis.readUTF()),
								Double.parseDouble(dis.readUTF()), Double.parseDouble(dis.readUTF()),
								Double.parseDouble(dis.readUTF()));

						ArrayList<Request> requests = dc.getRequests();

						boolean check = false;
						for (int count = 0; count < requests.size(); count++) {
							if (user.TaxiMatching(taxiSchedule, requests.get(count).getSchedule())) {
								if (requests.get(count).getMatchingGroupNum() == 0) {
									int num = ++matchingNum;

									dc.update("request", "request_id",
											Integer.toString(requests.get(count).getRequestId()), "matching_group_num",
											Integer.toString(num));
									dc.insertSchedule(taxiSchedule);
									Request request = new Request(0, user.getId(), taxiSchedule, num, true);
									dc.insertRequest(request);
									check = true;
									dos.writeBoolean(true);
									break;
								} else {
									Request request = new Request(0, user.getId(), taxiSchedule,
											requests.get(count).getMatchingGroupNum(), true);
									dc.insertSchedule(taxiSchedule);
									dc.insertRequest(request);
									check = true;
									dos.writeBoolean(true);
									break;
								}
							}
						}
						if (!check) {
							Request request = new Request(0, user.getId(), taxiSchedule, 0, false);
							dc.insertSchedule(taxiSchedule);
							dc.insertRequest(request);
							dos.writeBoolean(true);
						}
						break;

					case 8:// 운전하기
						Driver driver = dc.getDriver(user.getId());

						Schedule driverSchedule = null;
						try {
							driverSchedule = new Schedule(0, sdf.parse(dis.readUTF()),
									Double.parseDouble(dis.readUTF()), Double.parseDouble(dis.readUTF()),
									Double.parseDouble(dis.readUTF()), Double.parseDouble(dis.readUTF()));
							dc.insertSchedule(driverSchedule);
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (ParseException e) {
							e.printStackTrace();
						}

						Demand demand = new Demand(0, driver.getLicenseNum(), Integer.parseInt(dis.readUTF()),
								Integer.parseInt(dis.readUTF()), 0, driverSchedule);
						if (dc.insertDemand(demand)) {
							dos.writeBoolean(true);
						} else {
							dos.writeBoolean(false);
						}
						break;

					case 9:// 탑승하기 - 조회
						Schedule boarderSchedule = null;
						try {
							boarderSchedule = new Schedule(0, sdf.parse(dis.readUTF()),
									Double.parseDouble(dis.readUTF()), Double.parseDouble(dis.readUTF()),
									Double.parseDouble(dis.readUTF()), Double.parseDouble(dis.readUTF()));
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (ParseException e) {
							e.printStackTrace();
						}

						ArrayList<Demand> bdemands = user.CarMatching(boarderSchedule, dc.getDemands());

						int i;
						int limit = bdemands.size();

						dos.writeInt(limit);

						for (i = 0; i < limit; i++) {
							int demandId = bdemands.get(i).getDemandId();
							dos.writeUTF(Integer.toString(demandId));
							dos.writeUTF(Double.toString(bdemands.get(i).getSchedule().getStartX()));
							dos.writeUTF(Double.toString(bdemands.get(i).getSchedule().getStartY()));
							dos.writeUTF(Double.toString(bdemands.get(i).getSchedule().getEndX()));
							dos.writeUTF(Double.toString(bdemands.get(i).getSchedule().getEndY()));
							dos.writeUTF(sdf.format(bdemands.get(i).getSchedule().getDatetime()));
							Demand demandS = user.selectDemand(dc.getDemands(), bdemands.get(i).getDemandId());
							dos.writeUTF(Integer.toString(demandS.getCharge()));
						}

						dos.writeUTF("EOT");

						if (limit == i)
							dos.writeBoolean(true);
						else
							dos.writeBoolean(false);

						break;

					case 10:// 탑승하기 - 리스트 선택
						Demand demandLS = user.selectDemand(dc.getDemands(), Integer.parseInt(dis.readUTF()));
						ArrayList<Evaluation> Eval = dc.getEvaluations(user.getId());
						int sumEval = 0, countEval = 0;

						dos.writeInt(Eval.size());
						dos.writeUTF(Integer.toString(demandLS.getDemandId()));

						for (countEval = 0; countEval < Eval.size(); countEval++) {
							sumEval = sumEval + Eval.get(countEval).getGrade();
						}

						dos.writeUTF(Double.toString(demandLS.getSchedule().getStartX()));
						dos.writeUTF(Double.toString(demandLS.getSchedule().getStartY()));
						dos.writeUTF(Double.toString(demandLS.getSchedule().getEndX()));
						dos.writeUTF(Double.toString(demandLS.getSchedule().getEndY()));
						dos.writeUTF(sdf.format(demandLS.getSchedule().getDatetime()));
						dos.writeUTF(Integer.toString(demandLS.getCharge()));
						dos.writeUTF(dc.getCar(demandLS.getLicenseId()).getCarModel());
						dos.writeUTF(Integer.toString(demandLS.getCurrPerson()));
						dos.writeUTF(Double.toString((double) sumEval / (double) countEval));

						for (countEval = 0; countEval < Eval.size(); countEval++) {
							dos.writeUTF(Integer.toString(Eval.get(countEval).getGrade()));
							dos.writeUTF(Eval.get(countEval).getReview());
						}
						dos.writeUTF("EOT");

						if (countEval == Eval.size())
							dos.writeBoolean(true);
						else
							dos.writeBoolean(false);

						break;

					case 11:// 탑승하기 - 신청
						if (dc.insertState(user.getId(), Integer.parseInt(dis.readUTF()), false))
							dos.writeBoolean(true);
						else
							dos.writeBoolean(false);

						break;

					case 12:// 게시판-조회
						ArrayList<Post> posts = dc.getPosts();

						int posti;
						int postsize = posts.size();
						dos.writeInt(postsize);

						for (posti = 0; posti < postsize; posti++) {
							dos.writeUTF(Integer.toString(posts.get(posti).getPostId()));
							dos.writeUTF(posts.get(posti).getTitle());
							dos.writeUTF(posts.get(posti).getId());
							dos.writeUTF(sdf.format(posts.get(posti).getCreatedDatetime()));
						}

						dos.writeUTF("EOT");

						if (postsize == posti)
							dos.writeBoolean(true);
						else
							dos.writeBoolean(false);

						break;

					case 13:// 게시판-글쓰기
						Post post = new Post(0, user.getId(), dis.readUTF(), dis.readUTF(), new Date());

						if (dc.insertPost(post))
							dos.writeBoolean(true);
						else
							dos.writeBoolean(false);

						break;

					// 댓글작성자 댓글작성일 댓글내용 3개 반복

					case 14:// 게시판 list select

						int postId = Integer.parseInt(dis.readUTF());

						Post readPost = user.selectPost(dc.getPosts(), postId);
						ArrayList<Comment> comments = dc.getComments(postId);

						if (readPost.getPostId() == postId) {
							dos.writeInt(comments.size());
							dos.writeUTF(Integer.toString(postId));
							dos.writeUTF(readPost.getId());
							dos.writeUTF(sdf.format(readPost.getCreatedDatetime()));
							dos.writeUTF(readPost.getTitle());
							dos.writeUTF(readPost.getContent());

							for (int j = 0; j < comments.size(); j++) {
								dos.writeUTF(comments.get(j).getId());
								dos.writeUTF(sdf.format(comments.get(j).getCreatedDatetime()));
								dos.writeUTF(comments.get(j).getContent());
							}
							dos.writeUTF("EOT");

							dos.writeBoolean(true);
						} else
							dos.writeBoolean(false);

						break;

					case 15:// 게시판 - 글 댓글달기

						String postid = dis.readUTF();

						Comment c = new Comment(0, user.getId(), dis.readUTF(), new Date());

						if (dc.insertComment(c, Integer.parseInt(postid)))
							dos.writeBoolean(true);
						else
							dos.writeBoolean(false);

						break;

					case 16:// 회원정보 조회
						dos.writeUTF(user.getId());
						dos.writeUTF(user.getName());
						dos.writeUTF(user.getMail());
						dos.writeUTF(user.getPhone());
						user.setDriverInfo(dc.getDriver(user.getId()));
						user.getDriverInfo().setCar(dc.getCar(user.getDriverInfo().getLicenseNum()));

						Driver user_driverInfo = dc.getDriver(user.getId());
						dos.writeUTF(user_driverInfo.getLicenseNum());
						dos.writeUTF(user_driverInfo.getCar().getCarModel());
						dos.writeUTF(user_driverInfo.getCar().getCarNum());
						dos.writeUTF(user_driverInfo.getCar().getCarColor());

						dos.writeUTF("EOT");

						dos.writeBoolean(true);

						break;

					case 17: // 택시 신청 현황
						ArrayList<Request> requestConList = dc.getRequests();

						for (int j = 0; j < requestConList.size(); j++) {
							if (requestConList.get(j).getId().equals(user.getId())) {
								int mgn = requestConList.get(j).getMatchingGroupNum();
								if (mgn != 0) {
									for (int k = 0; k < requestConList.size(); k++) {
										if (requestConList.get(k).getMatchingGroupNum() == mgn) {
											String id = dc.getUser(requestConList.get(k).getId()).getId();
											if (id != user.getId()) {
												dos.writeUTF(dc.getUser(id).getPhone());
											}
										}
									}
								}
								break;
							}
						}

						dos.writeUTF("EOT");
						dos.writeBoolean(true);

						break;

					case 18:// 내상태 - 신청현황 - 운전자
						// listview크기 현재인원 탑승자id 탑승자평점 <<탑승자 id , 탑승자 평점 2개 반복한뒤
						// 끝나고 EOT

						ArrayList<State> state = dc.getState();
						ArrayList<State> stateS = new ArrayList<State>();
						ArrayList<Demand> ds = dc.getDemands();

						Demand d = null;
						for (int j = 0; j < ds.size(); j++) {
							if (dc.getDriver(user.getId()).getLicenseNum().equals(ds.get(j).getLicenseId())) {
								d = ds.get(j);
								break;
							}
						}

						if (d != null) {
							dos.writeUTF("NEXT");

							for (int j = 0; j < state.size(); j++) {
								if (state.get(j).getDemandId() == d.getDemandId()) {
									stateS.add(state.get(j));
								}
							}

							dos.writeInt(stateS.size());

							dos.writeUTF(Integer.toString(d.getCurrPerson()));

							for (int j = 0; j < stateS.size(); j++) {
								dos.writeUTF(stateS.get(j).getId());

								ArrayList<Evaluation> myEval = dc.getEvaluations(stateS.get(j).getId());
								int sum = 0, count = 0;

								for (count = 0; count < myEval.size(); count++) {
									sum = sum + myEval.get(count).getGrade();
								}
								dos.writeUTF(Integer.toString(sum / count));
							}
						}

						dos.writeUTF("EOT");
						dos.writeBoolean(true);

						break;

					case 19: // 신청형황 - 운전자 - 탑승자 정보조회

						ArrayList<State> stateI = dc.getState();
						ArrayList<Demand> demandI = dc.getDemands();
						Demand demandSel = null;
						ArrayList<State> result = new ArrayList<State>();

						for (int j = 0; j < demandI.size(); j++) {
							if (demandI.get(j).getLicenseId() == dc.getDriver(user.getId()).getLicenseNum()) {
								demandSel = demandI.get(j);
								break;
							}
						}

						if (demandSel != null) {
							long startTime = demandSel.getSchedule().getDatetime().getTime();
							long currTime = new Date().getTime();

							if (startTime - currTime < 0)
								dc.delete("demand", "demand_id", Integer.toString(demandSel.getDemandId()));

							else {

								for (int k = 0; k < stateI.size(); k++) {
									if (stateI.get(k).getDemandId() == demandSel.getDemandId()) {
										if (stateI.get(k).isConfirmed()) {
											result.add(stateI.get(k));
										}
									}

								}

								dos.writeInt(result.size());

								for (int j = 0; j < result.size(); j++) {
									dos.writeUTF(result.get(j).getId());
									dos.writeUTF(dc.getUser(result.get(j).getId()).getPhone());
								}
							}
						} else {
							dos.writeInt(0);
						}

						dos.writeUTF("EOT");
						dos.writeBoolean(true);

						break;

					case 20:// 신청현황 - 운전자 - 수락
						String boarderCon_Id = dis.readUTF();
						ArrayList<State> stateCon = dc.getState();
						ArrayList<Demand> demandCon = dc.getDemands();
						State stateSel = null;

						for (int j = 0; j < stateCon.size(); j++) {
							if (stateCon.get(j).getId().equals(boarderCon_Id)) {
								if (dc.update("state", "state_id", boarderCon_Id, "confirm", "1")) {
									System.out.println("ok1");
									stateSel = stateCon.get(j);
									break;
								}
							}
						}
						for (int j = 0; j < demandCon.size(); j++) {
							if (stateSel.getDemandId() == demandCon.get(j).getDemandId()) {
								int temp = demandCon.get(j).getCurrPerson();
								demandCon.get(j).setCurrPerson(temp);
							}
						}
						dos.writeBoolean(true);

						break;
					case 21:// 신청현황 - 운전자 - 거절
						String boarderNCon_Id = dis.readUTF();
						ArrayList<State> stateNCon = dc.getState();

						for (int j = 0; j < stateNCon.size(); j++) {
							if (stateNCon.get(j).getId().equals(boarderNCon_Id)) {
								if (dc.update("state", "state_id", boarderNCon_Id, "confirm", "0")) {
									System.out.println("ok2");
									dos.writeBoolean(true);
									break;
								} else {
									dos.writeBoolean(false);
									break;
								}
							}
						}

						break;

					case 22:// 내상태 - 신청현황 - 탑승자
						ArrayList<State> state_Waitperson = dc.getState();
						ArrayList<Demand> demand_boarder = dc.getDemands();

						State state_user = null;
						for (int j = 0; j < state_Waitperson.size(); j++) {
							if (state_Waitperson.get(j).getId() == user.getId()) {
								state_user = state_Waitperson.get(j);
								break;
							}
							if (j == state_Waitperson.size() - 1) {
								dos.writeBoolean(false);
								break;
							}
						}

						if (state_user == null) {
							dos.writeUTF("EOT");
							dos.writeBoolean(true);
							break;
						} else {
							dos.writeUTF("OK");
						}

						dos.writeBoolean(state_user.isConfirmed());

						for (int j = 0; j < demand_boarder.size(); j++) {
							if (state_user.getDemandId() == demand_boarder.get(j).getDemandId()) {
								dos.writeUTF(Double.toString(demand_boarder.get(j).getSchedule().getStartX()));
								dos.writeUTF(Double.toString(demand_boarder.get(j).getSchedule().getStartY()));
								dos.writeUTF(Double.toString(demand_boarder.get(j).getSchedule().getEndX()));
								dos.writeUTF(Double.toString(demand_boarder.get(j).getSchedule().getEndY()));
								dos.writeUTF(sdf.format(demand_boarder.get(j).getSchedule().getDatetime()));
								dos.writeUTF(Integer.toString(demand_boarder.get(j).getCharge()));
								dos.writeUTF(dc.getCar(demand_boarder.get(j).getLicenseId()).getCarModel());
								dos.writeUTF(Integer.toString(demand_boarder.get(j).getCurrPerson()));
								ResultSet rs = dc.select("driver", "license_id", demand_boarder.get(j).getLicenseId());
								int s = 0;
								int cnt = 0;
								ArrayList<Evaluation> e = null;
								try {
									rs.first();
									String drv_id = rs.getString("id");
									e = dc.getEvaluations(drv_id);

									for (int k = 0; k < e.size(); k++) {
										s += e.get(k).getGrade();
										cnt++;
									}
								} catch (SQLException e1) {
									e = new ArrayList<Evaluation>();
									e1.printStackTrace();
								}
								dos.writeUTF(Double.toString((double) s / (double) cnt));

								dos.writeInt(e.size());

								if (e != null) {
									for (int k = 0; k < e.size(); k++) {
										dos.writeUTF(Integer.toString(e.get(k).getGrade()));
										dos.writeUTF(e.get(k).getReview());
									}
								}

								dos.writeUTF("EOT");
								dos.writeBoolean(true);
								break;
							}
						}

						break;
					}
					if (isEnd) {
						break;
					}
				}

				System.out.println("연결 종료");

				dis.close();
				dos.close();
				socket.close();

			} catch (IOException e) {
			}
		}

	}

	public static String getTime() {
		String name = Thread.currentThread().getName();
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date()) + name;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static void main(String[] args) {
		if (args.length != 1 && isInteger(args[0])) {
			System.out.println("Server thread_count");
			return;
		}

		dc = new DatabaseConnector();
		dc.connect();

		int thread_count = Integer.parseInt(args[0]);
		CarpoolServer server = new CarpoolServer(thread_count);
		server.start();
	}
}
