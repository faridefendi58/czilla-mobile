package id.web.jagungbakar.chordzilla.utils;

public enum DatabaseContents {

    DATABASE("chordzilla.db"),
    TABLE_SONGS("tbl_songs"),
    TABLE_ARTISTS("tbl_artists"),
    TABLE_GENRES("tbl_genres"),
    TABLE_HISTORY("tbl_history"),
    TABLE_PARAMS("tbl_params");

    private String name;

    /**
     * Constructs DatabaseContents.
     * @param name name of this content for using in database.
     */
    private DatabaseContents(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
