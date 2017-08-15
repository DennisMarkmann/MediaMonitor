package markmann.dennis.fileExtractor.settings;

/**
 * Settings object containing all universal settings affecting every kind of media.
 *
 * @author Dennis.Markmann
 */
public class GeneralSettings implements Settings {

    // dont make those fields private: needed default to read them from XML
    boolean useGui = false;
    boolean useSystemTray = true;
    boolean useTimer = true;
    int timerInterval = 60;
    boolean usePopupNotification = true;
    boolean useRenaming = true;
    boolean useCleanup = true;
    boolean useFileMoving = true;
    boolean useHistory = true;
    boolean useExtendedLogging = false;
    boolean removeCorruptFiles = true;
    boolean removeVersionNumbers = true;
    boolean startPaused = false;

    public int getTimerInterval() {
        return this.timerInterval;
    }

    public boolean removeCorruptFiles() {
        return this.removeCorruptFiles;
    }

    public boolean removeVersionNumbers() {
        return this.removeVersionNumbers;
    }

    public void setRemoveCorruptFiles(boolean removeCorruptFiles) {
        this.removeCorruptFiles = removeCorruptFiles;
    }

    public void setRemoveVersionNumbers(boolean removeVersionNumbers) {
        this.removeVersionNumbers = removeVersionNumbers;
    }

    public void setStartPaused(boolean startPaused) {
        this.startPaused = startPaused;
    }

    public void setTimerInterval(int timerInterval) {
        this.timerInterval = timerInterval;
    }

    public void setUseCleanup(boolean useCleanup) {
        this.useCleanup = useCleanup;
    }

    public void setUseExtendedLogging(boolean useExtendedLogging) {
        this.useExtendedLogging = useExtendedLogging;
    }

    public void setUseFileMoving(boolean useFileMoving) {
        this.useFileMoving = useFileMoving;
    }

    public void setUseGui(boolean useGui) {
        this.useGui = useGui;
    }

    public void setUseHistory(boolean useHistory) {
        this.useHistory = useHistory;
    }

    public void setUsePopupNotification(boolean usePopupNotification) {
        this.usePopupNotification = usePopupNotification;
    }

    public void setUseRenaming(boolean useRenaming) {
        this.useRenaming = useRenaming;
    }

    public void setUseSystemTray(boolean useSystemTray) {
        this.useSystemTray = useSystemTray;
    }

    public void setUseTimer(boolean useTimer) {
        this.useTimer = useTimer;
    }

    public boolean startPaused() {
        return this.startPaused;
    }

    public boolean useCleanup() {
        return this.useCleanup;
    }

    public boolean useExtendedLogging() {
        return this.useExtendedLogging;
    }

    public boolean useFileMoving() {
        return this.useFileMoving;
    }

    boolean useGui() {
        return this.useGui;
    }

    public boolean useHistory() {
        return this.useHistory;
    }

    public boolean usePopupNotification() {
        return this.usePopupNotification;
    }

    public boolean useRenaming() {
        return this.useRenaming;
    }

    public boolean useSystemTray() {
        return this.useSystemTray;
    }

    public boolean useTimer() {
        return this.useTimer;
    }

}
