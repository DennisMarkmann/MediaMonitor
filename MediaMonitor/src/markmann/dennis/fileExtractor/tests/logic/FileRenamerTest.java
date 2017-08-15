package markmann.dennis.fileExtractor.tests.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import markmann.dennis.fileExtractor.logic.FileRenamer;
import markmann.dennis.fileExtractor.mediaObjects.Anime;
import markmann.dennis.fileExtractor.mediaObjects.Series;

public class FileRenamerTest {

    @Test
    public void testAnimeRenaming() {
        String originalFineName = "[HorribleSubs] Kono Subarashii Sekai ni Shukufuku wo! 2 - 07 [1080p].mkv";
        String resultTitle = "Kono Subarashii Sekai ni Shukufuku wo! 2";
        String resultEpisode = "07";
        String resultExtension = "mkv";
        String resultTitleComplete = "Kono Subarashii Sekai ni Shukufuku wo! 2 - 07.mkv";
        String resultTitleCompleteNoExt = "Kono Subarashii Sekai ni Shukufuku wo! 2 - 07";

        Anime anime = new FileRenamer().handleAnimeRenaming(originalFineName, new Anime());
        assertEquals(resultTitle, anime.getTitle());
        assertEquals(resultEpisode, anime.getEpisode());
        assertEquals(resultExtension, anime.getExtension());
        assertEquals(resultTitleComplete, anime.getCompleteTitle());
        assertEquals(resultTitleCompleteNoExt, anime.getCompleteTitleNoExt());
    }

    @Test
    public void testAnimeRenamingExtended() {
        String originalFineName = "[RH] Kono Subarashii Sekai ni Shukufuku wo! - 01 [Test] [Dual Audio] [9C73C70E].mkv";
        String resultTitle = "Kono Subarashii Sekai ni Shukufuku wo!";
        String resultEpisode = "01";
        String resultExtension = "mkv";
        String resultTitleComplete = "Kono Subarashii Sekai ni Shukufuku wo! - 01.mkv";
        String resultTitleCompleteNoExt = "Kono Subarashii Sekai ni Shukufuku wo! - 01";

        Anime anime = new FileRenamer().handleAnimeRenaming(originalFineName, new Anime());
        assertEquals(resultTitle, anime.getTitle());
        assertEquals(resultEpisode, anime.getEpisode());
        assertEquals(resultExtension, anime.getExtension());
        assertEquals(resultTitleComplete, anime.getCompleteTitle());
        assertEquals(resultTitleCompleteNoExt, anime.getCompleteTitleNoExt());
    }

    @Test
    public void testAnimeRenamingWithVersionNumber() {
        String originalFineName = "[HorribleSubs] Kono Subarashii Sekai ni Shukufuku wo! 2 - 07v2 [1080p].mkv";
        String resultTitle = "Kono Subarashii Sekai ni Shukufuku wo! 2";
        String resultEpisode = "07";
        String resultExtension = "mkv";
        String resultTitleComplete = "Kono Subarashii Sekai ni Shukufuku wo! 2 - 07.mkv";
        String resultTitleCompleteNoExt = "Kono Subarashii Sekai ni Shukufuku wo! 2 - 07";

        Anime anime = new FileRenamer().handleAnimeRenaming(originalFineName, new Anime());
        assertEquals(resultTitle, anime.getTitle());
        assertEquals(resultEpisode, anime.getEpisode());
        assertEquals(resultExtension, anime.getExtension());
        assertEquals(resultTitleComplete, anime.getCompleteTitle());
        assertEquals(resultTitleCompleteNoExt, anime.getCompleteTitleNoExt());
    }

    @Test
    public void testSeriesRenaming() {
        String originalFineName = "Marvels.Agents.of.S.H.I.E.L.D.S04E22.1080p.WEB-DL.DD5.1.H264-RARBG.mkv";
        String resultTitle = "Marvels Agents of S H I E L D";
        String resultSeason = "04";
        String resultEpisode = "22";
        String resultExtension = "mkv";
        String resultTitleComplete = "Marvels Agents of S H I E L D - S04E22.mkv";
        String resultTitleCompleteNoExt = "Marvels Agents of S H I E L D - S04E22";

        Series series = new FileRenamer().handleSeriesRenaming(originalFineName, new Series());
        assertEquals(resultTitle, series.getTitle());
        assertEquals(resultSeason, series.getSeason());
        assertEquals(resultEpisode, series.getEpisode());
        assertEquals(resultExtension, series.getExtension());
        assertEquals(resultTitleComplete, series.getCompleteTitle());
        assertEquals(resultTitleCompleteNoExt, series.getCompleteTitleNoExt());
    }

}
