package markmann.dennis.fileExtractor.settings;

import java.util.ArrayList;

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
    boolean useCleanup = true;
    boolean useHistory = true;
    boolean useExtendedLogging = false;
    boolean startPaused = false;
    String CompletionPath = "Test";
    boolean useFolderCreation = false;
    ArrayList<String> pathsToMonitor = new ArrayList<>();

    void addShow(String newPath) {
        for (String name : this.pathsToMonitor) {
            if (name.equals(newPath)) {
                return;
            }
        }
        this.pathsToMonitor.add(newPath);
    }

    void clearMonitoredPaths() {
        this.pathsToMonitor = new ArrayList<>();
    }

    public String getCompletionPath() {
        return this.CompletionPath;
    }

    public ArrayList<String> getMonitoredPaths() {
        return this.pathsToMonitor;
    }

    public int getTimerInterval() {
        return this.timerInterval;
    }

    public void setCompletionPath(String completionPath) {
        this.CompletionPath = completionPath;
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

    public void setUseFolderCreation(boolean useFolderCreation) {
        this.useFolderCreation = useFolderCreation;
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

    public boolean useFolderCreation() {
        return this.useFolderCreation;
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

    public boolean useSystemTray() {
        return this.useSystemTray;
    }

    public boolean useTimer() {
        return this.useTimer;
    }

}
