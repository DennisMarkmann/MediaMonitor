package markmann.dennis.fileExtractor.settings;

import java.util.ArrayList;

/**
 * Settings object containing specific settings affecting only one kind of media.
 *
 * @author Dennis.Markmann
 */

public class ShowsToWatch implements Settings {

    // dont make those fields private: needed default to read them from XML
    ArrayList<String> showsToWatch = new ArrayList<>();

    void addShow(String newSeries) {
        for (String name : this.showsToWatch) {
            if (name.equals(newSeries)) {
                return;
            }
        }
        this.showsToWatch.add(newSeries);
    }

    void clearExceptions() {
        this.showsToWatch = new ArrayList<>();
    }

    public ArrayList<String> getShows() {
        return this.showsToWatch;
    }

}