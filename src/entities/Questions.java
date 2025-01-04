package entities;

public class Questions {
  private String question;
  private Integer id;

  public Questions(String question, Integer id) {
    this.question = question;
    this.id = id;
  }
  
  public String getQuestion() {
    return question;
  }
  public void setQuestions(String question) {
    this.question = question;
  }
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  
}
