package markmann.dennis.fileExtractor.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.mediaObjects.Anime;
import markmann.dennis.fileExtractor.mediaObjects.MediaType;
import markmann.dennis.fileExtractor.mediaObjects.Medium;
import markmann.dennis.fileExtractor.mediaObjects.Series;
import markmann.dennis.fileExtractor.settings.SettingHandler;

/**
 * Used to handle the media renaming.
 *
 * @author Dennis.Markmann
 */

public class FileRenamer {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    /**
     * Capitalize the first char of the given String.
     */
    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    /**
     * Regex for anime renaming.
     */
    public Anime handleAnimeRenaming(String fileName, Anime anime) {
        final Pattern pattern = Pattern.compile("(\\[.{1,}])?([^<]*)\\ - (.{2,6})(\\[.{4,5}])?(\\[.{1,}])?\\.(.{3})");

        Matcher m = pattern.matcher(fileName);
        if (m.matches()) {
            String title = this.capitalize(m.group(2).trim());
            String episode = m.group(3).trim();
            String extension = m.group(6).trim();

            anime.setTitle(title);
            this.setEpisode(anime, episode);
            anime.setExtension(extension);
            return anime;
        }
        return null;
    }

    /**
     * Regex for series renaming.
     */
    public Series handleSeriesRenaming(String fileName, Series series) {
        final Pattern pattern = Pattern.compile("([^<]*)(\\ - |\\.{1})(?i)S(.{2,3})(?i)E(.{2,3})(\\.[^<]*)?\\.(.{3})");
        Matcher m = pattern.matcher(fileName);
        if (m.matches()) {
            String title = this.capitalize(this.replaceFiller(m.group(1).trim()));
            String season = m.group(3).trim();
            String episode = m.group(4).trim();
            String extension = m.group(6).trim();

            series.setTitle(title);
            series.setSeason(season);
            series.setEpisode(episode);
            series.setExtension(extension);
            return series;
        }
        return null;
    }

    /**
     * Replaces all dots in the filename with space.
     */
    private String replaceFiller(String fileName) {
        fileName = fileName.replaceAll("\\.", " ");
        fileName = fileName.replaceAll("_", " ");
        return fileName;
    }

    /**
     * Starts the renaming process for the give fileList.
     *
     * @param fileList to rename.
     * @param mediaType of the files.
     * @return the renamed list of files.
     */
    ArrayList<Medium> scanFiles(ArrayList<File> fileList, MediaType mediaType) {

        ArrayList<Medium> mediaList = new ArrayList<>();
        for (final File file : fileList) {
            String originalFileName = file.getName();
            Medium medium = null;
            boolean useRenaming = SettingHandler.getGeneralSettings().useRenaming();
            if (mediaType == MediaType.Anime) {
                if (useRenaming) {
                    medium = this.handleAnimeRenaming(originalFileName, new Anime());
                }
                else {
                    medium = new Anime();
                }
            }

            if (mediaType == MediaType.Series) {
                if (useRenaming) {
                    medium = this.handleSeriesRenaming(originalFileName, new Series());
                }
                else {
                    medium = new Series();
                }
            }
            if (!useRenaming) {
                medium.setKeepOriginalName(true);
                medium.setTitle(originalFileName);
            }

            if ((medium == null) && useRenaming) {
                if (SettingHandler.getGeneralSettings().removeCorruptFiles()) {
                    NotificationHelper.showErrorNotification(
                            "Renaming of file:'" + originalFileName + "' not successful. File deleted.",
                            true,
                            null);
                    file.delete();
                }
                else {
                    NotificationHelper.showErrorNotification(
                            "Renaming of file:'" + originalFileName + "' not successful. Please try solving the problem.",
                            true,
                            null);
                }
                continue;
            }
            else if (medium == null) {
                NotificationHelper.showErrorNotification(
                        "Processing of file:'" + originalFileName + "' not successful. Please try solving the problem.",
                        true,
                        null);
            }

            String originPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));

            medium.setOriginPath(originPath);

            if (!(originalFileName.equals(medium.getCompleteTitle())) && useRenaming) {
                File newFile = new File(medium.getOriginPath() + "\\" + medium.getCompleteTitle());
                if (file.renameTo(newFile)) {
                    LOGGER.info("Renaming '" + originalFileName + "' to '" + medium.getCompleteTitle() + "'.");
                }
            }
            mediaList.add(medium);
        }
        return mediaList;
    }

    /**
     * Set the episode number attribute for the anime episode.
     *
     * @param anime to store the episode number for.
     * @param episode number to store.
     */
    private void setEpisode(Anime anime, String episode) {
        if (SettingHandler.getGeneralSettings().removeVersionNumbers() && episode.contains("v")) {
            episode = episode.substring(0, episode.indexOf("v"));
        }
        anime.setEpisode(episode);
    }
}
