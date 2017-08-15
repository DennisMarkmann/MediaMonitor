package markmann.dennis.fileExtractor.settings;

/**
 * Used as a workaround to store media objects with different destinations from the default.
 *
 * @author Dennis.Markmann
 */
public class ExceptionPath {

    private String name;
    private String path;

    ExceptionPath(String name, String path) {
        this.setName(name);
        this.setPath(path);
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
