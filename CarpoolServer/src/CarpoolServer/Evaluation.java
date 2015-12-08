package CarpoolServer;

//평가 정보
public class Evaluation {
	private int evaluation_id;
	private String id;
	private int grade;
	private String review;

	public Evaluation(int evaluation_id, String id, int grade, String review) {
		this.evaluation_id = evaluation_id;
		this.id = id;
		this.grade = grade;
		this.review = review;
	}

	public int getEvaluationId() {
		return evaluation_id;
	}

	public String getId() {
		return id;
	}

	public int getGrade() {
		return grade;
	}

	public String getReview() {
		return review;
	}

	public void setEvaluation(int evaluation_id) {
		this.evaluation_id = evaluation_id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setReview(String review) {
		this.review = review;
	}
}
