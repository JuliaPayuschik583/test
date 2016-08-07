package xml.entities;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Julia
 */
@XmlRootElement(name = "Entry")
public class TableC implements Table {

    String name;
    private final Logger LOGGER = Logger.getLogger(TableC.class);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TableC{" +
                "name='" + name + '\'' +
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

            domain.TableC tableC = new domain.TableC();
            tableC.setName(name);
            int id = (Integer) session.save(tableC);
            LOGGER.info("TableC, id = " + id);

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
