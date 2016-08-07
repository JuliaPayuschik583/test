import org.apache.log4j.Logger;
import timer.ScheduledTimerTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author julia
 */
public class Main {

    private static final int DEFAULT_SECONDS = 60;//1 minute
    private static final String CONFIG_NAME_SECONDS = "monitoring_time_in_seconds";
    private static final String NAME_DEFAULT_CONFIG = "config.txt";
    private static final File CONFIG_FILE = new File(new File("").getAbsolutePath() + File.separator
            + "config" + File.separator + NAME_DEFAULT_CONFIG);
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String args[]) throws InterruptedException {
        Timer time = new Timer();
        ScheduledTimerTask scheduledTimerTask = new ScheduledTimerTask();

        int seconds = readConfigTime();
        if (seconds == 0) {
            seconds = DEFAULT_SECONDS;
        }
        time.schedule(scheduledTimerTask, 0, 1000 * seconds);
    }

    private static int readConfigTime() {
        try (Stream<String> stream = Files.lines(Paths.get(CONFIG_FILE.getAbsolutePath()))) {

            List<String> configList = stream.collect(Collectors.toList());
            if (!configList.isEmpty()) {
                return parseTimeConfig(configList.get(0));
            }

        } catch (IOException ex) {
            LOGGER.error("ERROR: " + ex.getMessage());
        }

        return 0;
    }

    private static int parseTimeConfig(String config) {
        config = config.replaceAll(" ", "");
        String[] configTime = config.split("=");
        if (configTime.length == 2 && CONFIG_NAME_SECONDS.equals(configTime[0])) {
            if (configTime[1].matches("\\d+")) {
                return Integer.valueOf(configTime[1]);
            }
        }
        return 0;
    }
}
