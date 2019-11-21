package id.web.jagungbakar.chordzilla.controllers;

import android.content.ContentValues;

import java.util.List;

import id.web.jagungbakar.chordzilla.ui.search.SerializableChord;
import id.web.jagungbakar.chordzilla.utils.Database;
import id.web.jagungbakar.chordzilla.utils.DatabaseContents;
import id.web.jagungbakar.chordzilla.utils.DateTimeStrategy;

public class SongController {
    private static Database database;
    private static SongController instance;

    private SongController() {}

    public static SongController getInstance() {
        if (instance == null)
            instance = new SongController();
        return instance;
    }

    /**
     * Sets database for use in this class.
     * @param db database.
     */
    public static void setDatabase(Database db) {
        database = db;
    }

    public List<Object> getItems() {
        List<Object> contents = database.select("SELECT * FROM " + DatabaseContents.TABLE_SONGS);

        return contents;
    }

    public int addSong(SerializableChord song) {
        ContentValues content = new ContentValues();
        content.put("_id", song.getId());
        content.put("title", song.getTitle());
        content.put("slug", song.getSlug());
        content.put("chord_permalink", song.getChordPermalink());
        content.put("artist_id", song.getArtistId());
        content.put("genre_id", song.getGenreId());
        content.put("story", song.getStory());
        content.put("content", song.getContent());
        content.put("published_at", song.getPublishedAt());
        content.put("date_added", DateTimeStrategy.getCurrentTime());

        int id = database.insert(DatabaseContents.TABLE_SONGS.toString(), content);
        if (id > 0) {
            // add artist
            Boolean has_artist_data = false;
            try {
                List<Object> artists = database.select("SELECT _id FROM " + DatabaseContents.TABLE_ARTISTS);
                if (artists.size() > 0) {
                    ContentValues c_artist = (ContentValues) artists.get(0);
                    if (c_artist.containsKey("_id")) {
                        has_artist_data = true;
                    }
                }
            } catch (Exception e){e.printStackTrace();}
            if (!has_artist_data) {
                ContentValues content2 = new ContentValues();
                content2.put("_id", song.getArtistId());
                content2.put("name", song.getArtistName());
                content2.put("slug", song.getArtistSlug());
                content2.put("date_added", DateTimeStrategy.getCurrentTime());
                int id2 = database.insert(DatabaseContents.TABLE_ARTISTS.toString(), content2);
            }
            // add genre
            Boolean has_genre_data = false;
            try {
                List<Object> genres = database.select("SELECT _id FROM " + DatabaseContents.TABLE_GENRES);
                if (genres.size() > 0) {
                    ContentValues c_genre = (ContentValues) genres.get(0);
                    if (c_genre.containsKey("_id")) {
                        has_genre_data = true;
                    }
                }
            } catch (Exception e){e.printStackTrace();}
            if (!has_genre_data) {
                ContentValues content3 = new ContentValues();
                content3.put("_id", song.getGenreId());
                content3.put("name", song.getGenreName());
                content3.put("date_added", DateTimeStrategy.getCurrentTime());
                int id3 = database.insert(DatabaseContents.TABLE_GENRES.toString(), content3);
            }
        }

        return id;
    }

    public boolean removeSong(int id) {
        Boolean del = database.delete(DatabaseContents.TABLE_SONGS.toString(), id);
        if (del) {
            Boolean del2 = database.deleteAllByAttributes(DatabaseContents.TABLE_HISTORY.toString(), "song_id", id+"");
        }

        return del;
    }
}
