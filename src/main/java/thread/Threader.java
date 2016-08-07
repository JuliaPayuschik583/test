package thread;

import exception.ParserException;
import org.apache.log4j.Logger;
import xml.entities.Table;
import xml.entities.TableA;
import xml.entities.TableB;
import xml.entities.TableC;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * @author julia
 */
public class Threader implements Runnable {

    private final Logger LOGGER = Logger.getLogger(Threader.class);

    private final File fileForRead;
    private final File fileForParsed;
    private final File fileForFailedParse;
    private boolean isFailedFileForRead = false;

    public Threader(File fileForRead) {
        this.fileForRead = fileForRead;
        fileForParsed = new File(new File("").getAbsolutePath() + File.separator + "for_parsed" + File.separator + fileForRead.getName());
        fileForFailedParse = new File(new File("").getAbsolutePath() + File.separator + "for_failed" + File.separator + fileForRead.getName());
    }

    @Override
    public void run() {
        try {
            process();
        } catch (ParserException ex) {
            LOGGER.error("ERROR: " + ex.getMessage());
            if (isFailedFileForRead) {
                moveFile(fileForFailedParse);
            }
        }
    }

    private synchronized void process() throws ParserException {
        Table table = parseTable();
        LOGGER.info("fileForRead = " + fileForRead.getName() + " parsed in table = " + table);
        table.createFieldInTable();
    }

    private Table parseTable() throws ParserException {
        TableA tableA = null;
        TableB tableB = null;
        TableC tableC = null;

        try {
            tableA = (TableA) createTableFromXml(TableA.class);
            tableB = (TableB) createTableFromXml(TableB.class);
            tableC = (TableC) createTableFromXml(TableC.class);

        } catch (JAXBException ex) {
            isFailedFileForRead = true;
            moveFile(fileForFailedParse);
            throw new ParserException("Parse XML failed");
        }

        if (!isFailedFileForRead) {
            moveFile(fileForParsed);
        }

        //check tables:
        if (tableA != null && (tableA.getContent() != null || tableA.getCreationDate() != null)) {
            return tableA;
        }
        if (tableB != null && (tableB.getText() != null || tableB.getAge() != null)) {
            return tableB;
        }
        if (tableC != null && tableC.getName() != null) {
            return tableC;
        }
        isFailedFileForRead = true;
        throw new ParserException("Parse TABLE failed");
    }

    private Table createTableFromXml(Class table) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(table);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return  (Table) jaxbUnmarshaller.unmarshal(fileForRead);
    }

    private void moveFile(File renameFile) {
        if (!fileForRead.renameTo(renameFile)) {
            LOGGER.info("Rename file is failed");
        }
    }



}
