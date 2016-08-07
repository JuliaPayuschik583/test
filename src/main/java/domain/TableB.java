package domain;

import javax.persistence.*;

/**
 * @author Julia
 */
@Entity
@Table(name = "TableB")
public class TableB {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "text")
    private String text;

    @Column(name = "age")
    private Integer age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
