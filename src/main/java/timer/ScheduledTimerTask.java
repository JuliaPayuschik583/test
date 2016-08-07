package timer;

import org.apache.log4j.Logger;
import thread.Threader;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;

/**
 * @author julia
 */
public class ScheduledTimerTask extends TimerTask {

    private final Logger LOGGER = Logger.getLogger(ScheduledTimerTask.class);

    public void run() {
        File myFolder = new File(new File("").getAbsolutePath() + File.separator + "for_read");
        File[] files = myFolder.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (isXML(file.getName())) {
                    Thread thread = new Thread(new Threader(file));
                    thread.start();
                } else {
                    LOGGER.error("ERROR: file = " + file.getName() + " is not xml");
                }
            }
        }

    }

    private boolean isXML(String file) {
        return file != null && file.endsWith(".xml");
    }
}
