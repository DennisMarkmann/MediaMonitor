package markmann.dennis.fileExtractor.mediaObjects;

/**
 * Anime media object. Extends medium and adds the episode attribute.
 *
 * @author Dennis.Markmann
 */

public class Anime extends Medium {

    private String episode = "";

    @Override
    public String getCompleteTitleNoExt() {
        if (this.isKeepOriginalName()) {
            return this.title;
        }
        else {
            return this.title + " - " + this.episode;
        }
    }

    public String getEpisode() {
        return this.episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }
}
