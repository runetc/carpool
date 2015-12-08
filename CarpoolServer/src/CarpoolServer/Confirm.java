package CarpoolServer;

import java.util.Date;

//인증 정보
public class Confirm {
	private String id;
	private String confirm_mail;
	private Date issue_datetime;

	public Confirm(String id, String confirm_mail, Date issue_datetime) {
		this.id = id;
		this.confirm_mail = confirm_mail;
		this.issue_datetime = issue_datetime;
	}

	public String getId() {
		return id;
	}

	public String getConfirmMail() {
		return confirm_mail;
	}

	public Date getIssueDatetime() {
		return issue_datetime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setConfirmMail(String confirm_mail) {
		this.confirm_mail = confirm_mail;
	}

	public void setIssueDatetime(Date issue_datetime) {
		this.issue_datetime = issue_datetime;
	}
}
