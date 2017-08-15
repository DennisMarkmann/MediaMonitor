package markmann.dennis.fileExtractor.logging;

import java.io.Serializable;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 * LogHandler for creating a new Logger with one Appender.
 *
 * @author dennis.markmann
 */

public class LogHandler implements Serializable {

    private static final long serialVersionUID = 8262395849529211245L;
    private static RollingFileAppender appender = null;
    private static Logger log = null;

    private static Logger createLogAppender(final String logFilePath) {
        final String logFileName = logFilePath.substring((logFilePath.lastIndexOf("/") + 1));

        final Logger log = Logger.getLogger(logFileName);
        final PatternLayout layout = new PatternLayout();
        layout.setConversionPattern("%d{dd.MM.yyyy HH:mm:ss} %5p (%F:%L) - %m%n");

        appender = new RollingFileAppender();
        appender.setFile(logFilePath);
        appender.setMaxFileSize("5MB");
        appender.setMaxBackupIndex(5);
        appender.setLayout(layout);
        appender.activateOptions();
        log.setLevel(Level.ALL);
        log.addAppender(LogHandler.appender);

        return log;
    }

    public static Logger getLogger(final String logfileName) {
        if (log != null) {
            return log;
        }
        else {
            log = createLogAppender(logfileName);
            return log;
        }
    }
}
