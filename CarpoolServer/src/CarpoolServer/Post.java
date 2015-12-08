package CarpoolServer;

import java.util.ArrayList;
import java.util.Date;

//게시글 정보
public class Post {
	private int post_id;
	private String id;
	private String title;
	private String content;
	private Date created_datetime;
	private ArrayList<Comment> comments;

	public Post(int post_id, String id, String title, String content, Date created_datetime) {
		this.post_id = post_id;
		this.id = id;
		this.title = title;
		this.content = content;
		this.created_datetime = created_datetime;
		comments = new ArrayList<Comment>();
	}

	public int getPostId() {
		return post_id;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Date getCreatedDatetime() {
		return created_datetime;
	}

	public void setPostId(int post_id) {
		this.post_id = post_id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreatedDatetime(Date created_datetime) {
		this.created_datetime = created_datetime;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void addComment(Comment comment) {
		comments.add(comment);
	}
}
