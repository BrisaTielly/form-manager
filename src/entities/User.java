package entities;

import java.util.HashMap;
import java.util.Map;

public class User {
  private String name;
  private String email;
  private String age;
  private String height;

  private Map<String, String> additionalQuestions;

  public User(){
  }
  
  public User(String name, String email, String age, String height) {
    this.name = name;
    this.email = email;
    this.age = age;
    this.height = height;
    this.additionalQuestions = new HashMap<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public void addQuestion(String question, String answer) {
    additionalQuestions.put(question, answer); // Adiciona a pergunta e a resposta ao Map
}

  public String getAnswer(String question) {
    return additionalQuestions.get(question);
  }


  @Override
  public String toString() {
    return 
        "\nName: " + name + "\n" + "Email: " + email + "\nIdade: "  + age + "\nAltura: " + height + "\n".replace("[", " ");
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((age == null) ? 0 : age.hashCode());
    result = prime * result + ((height == null) ? 0 : height.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (age == null) {
      if (other.age != null)
        return false;
    } else if (!age.equals(other.age))
      return false;
    if (height == null) {
      if (other.height != null)
        return false;
    } else if (!height.equals(other.height))
      return false;
    return true;
  }

  
  
}
