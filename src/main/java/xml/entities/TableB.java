package xml.entities;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Julia
 */
@XmlRootElement(name = "Entry")
public class TableB implements Table {

    String text;
    Integer age;
    private final Logger LOGGER = Logger.getLogger(TableB.class);


    public String getText() {
        return text;
    }

    @XmlElement
    public void setText(String text) {
        this.text = text;
    }

    public Integer getAge() {
        return age;
    }

    @XmlElement
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TableB{" +
                ", text='" + text + '\'' +
                ", age=" + age +
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

            domain.TableB tableB = new domain.TableB();
            tableB.setText(text);
            tableB.setAge(age);
            int id = (Integer) session.save(tableB);
            LOGGER.info("TableB, id = " + id);

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
