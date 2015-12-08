package CarpoolServer;

import java.util.Date;

//´ñ±Û Á¤º¸
public class Comment {
	private int comment_id;
	private String id;
	private String content;
	private Date created_datetime;

	public Comment(int comment_id, String id, String content, Date created_datetime) {
		this.comment_id = comment_id;
		this.id = id;
		this.content = content;
		this.created_datetime = created_datetime;
	}

	public int getComment_id() {
		return comment_id;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public Date getCreatedDatetime() {
		return created_datetime;
	}

	public void setCommentId(int comment_id) {
		this.comment_id = comment_id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreatedDatetime(Date created_datetime) {
		this.created_datetime = created_datetime;
	}
}
