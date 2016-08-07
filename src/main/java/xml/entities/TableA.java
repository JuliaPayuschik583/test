package xml.entities;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import xml.DateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * @author Julia
 */
@XmlRootElement(name = "Entry")
public class TableA implements Table {

    String content;
    Date creationDate;
    private final Logger LOGGER = Logger.getLogger(TableA.class);

    public String getContent() {
        return content;
    }

    @XmlElement
    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "TableA{" +
                "content='" + content + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public void createFieldInTable() {
        Configuration configuration = new Configuration();
        SessionFactory factory = configuration.configure().buildSessionFactory();
        LOGGER.info("Reference to SessionFactory " + factory);

        Session session = null;
        try {
            session = factory.openSession();
            Transaction transaction = session.beginTransaction();

            domain.TableA tableA = new domain.TableA();
            tableA.setContent(content);
            tableA.setCreationDate(creationDate);
            int id = (Integer) session.save(tableA);
            LOGGER.info("TableA, id = " + id);

            transaction.commit();
            factory.close();

        } catch (HibernateException ex) {
            LOGGER.error("ERROR: Open session failed", ex);
        } finally {
            if(session != null) {
                session.close();
            }
        }

    }
}
