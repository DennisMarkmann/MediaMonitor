package markmann.dennis.fileExtractor.logging;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;

/**
 * Self defined outputStream used for logging.
 *
 * @author Dennis.Markmann
 */
public class LoggingOutputStream extends OutputStream {

    private static final String LINE_SEPERATOR = System.getProperty("line.separator");
    private static final int DEFAULT_BUFFER_LENGTH = 2048;
    private boolean hasBeenClosed = false;
    private byte[] buf;
    private int count;
    private int bufLength;
    private final Category category;
    private final Priority priority;

    public LoggingOutputStream(final Category cat, final Priority priority) {
        if (cat == null) {
            throw new IllegalArgumentException("cat == null");
        }
        if (priority == null) {
            throw new IllegalArgumentException("priority == null");
        }

        this.priority = priority;
        this.category = cat;
        this.bufLength = DEFAULT_BUFFER_LENGTH;
        this.buf = new byte[DEFAULT_BUFFER_LENGTH];
        this.count = 0;
    }

    @Override
    public void close() {
        this.flush();
        this.hasBeenClosed = true;
    }

    @Override
    public void flush() {
        if (this.count == 0) {
            return;
        }

        if (this.count == LINE_SEPERATOR.length()) {
            if ((((char) this.buf[0]) == LINE_SEPERATOR.charAt(0)) && ((this.count == 1) || // <-
                    ((this.count == 2) && (((char) this.buf[1]) == LINE_SEPERATOR.charAt(1))))) {
                this.reset();
                return;
            }
        }

        final byte[] theBytes = new byte[this.count];

        System.arraycopy(this.buf, 0, theBytes, 0, this.count);

        this.category.log(this.priority, new String(theBytes));

        this.reset();
    }

    private void reset() {
        this.count = 0;
    }

    @Override
    public void write(final int b) throws IOException {
        if (this.hasBeenClosed) {
            throw new IOException("The stream has been closed.");
        }

        if (b == 0) {
            return;
        }

        if (this.count == this.bufLength) {
            final int newBufLength = this.bufLength + DEFAULT_BUFFER_LENGTH;
            final byte[] newBuf = new byte[newBufLength];

            System.arraycopy(this.buf, 0, newBuf, 0, this.bufLength);

            this.buf = newBuf;
            this.bufLength = newBufLength;
        }

        this.buf[this.count] = (byte) b;
        this.count++;
    }

}
