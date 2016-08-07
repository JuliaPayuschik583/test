package domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Julia
 */
@Entity
@Table(name = "TableA")
public class TableA {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "creationDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
