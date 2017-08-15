package markmann.dennis.fileExtractor.mediaObjects;

/**
 * Enum used to differentiate between different media objects.
 *
 * @author Dennis.Markmann
 */

public enum MediaType {

    Anime {

        @Override
        public String toString() {
            return "Anime";
        }
    },

    Series {

        @Override
        public String toString() {
            return "Series";
        }
    },

    Movie {

        @Override
        public String toString() {
            return "Movie";
        }
    }
}
