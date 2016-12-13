package org.codeforcoffee.exoplanetarchive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codeforcoffee on stellar_cat_7/8/16.
 */
public class PlanetsDatabaseHelper extends SQLiteOpenHelper {

    // db version & name
    public static final String DATABASE_NAME = "Planets.db";
    public static final int DATABASE_VERSION = 12;

    // table specific private classes statements
    public static final String PLANET_CATEGORY_TABLE_NAME = "planet_categories";
    public static final String PLANET_CATEGORY_COL_ID = "_id";
    public static final String PLANET_CATEGORY_COL_MIN_MASS = "min_mass";
    public static final String PLANET_CATEGORY_COL_MAX_MASS = "max_mass";
    public static final String PLANET_CATEGORY_COL_NAME = "name";
    public static final String PLANET_CATEGORY_COL_DESC = "description";

    public static final String STELLAR_CATEGORY_TABLE_NAME = "stellar_categories";
    public static final String STELLAR_CATEGORY_COL_ID = "_id";
    public static final String STELLAR_CATEGORY_COL_MIN_TEMPERATURE = "min_temperature";
    public static final String STELLAR_CATEGORY_COL_MAX_TEMPERATURE = "max_temperature";
    public static final String STELLAR_CATEGORY_COL_NAME = "name";
    public static final String STELLAR_CATEGORY_COL_CLASS = "classification";
    public static final String STELLAR_CATEGORY_COL_DESC = "description";

    public static final String PLANET_TABLE_NAME = "planets";
    public static final String PLANET_COL_ID = "_id";
    public static final String PLANET_COL_NAME = "name";
    public static final String PLANET_COL_RADIUS = "radius";
    public static final String PLANET_COL_JUPITER_MASS = "jupiter_mass";
    public static final String PLANET_COL_CATEGORY_ID = "category_id";

    public static final String STAR_TABLE_NAME = "stars";
    public static final String STAR_COL_ID = "_id";
    public static final String STAR_COL_NAME = "name";
    public static final String STAR_COL_TEMPERATURE = "temperature";
    public static final String STAR_COL_STELLAR_CATEGORY_ID = "stellar_category_id";

    // default constructor
    public PlanetsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings here.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // singleton for db instance
    private static PlanetsDatabaseHelper mInstance;

    public static synchronized PlanetsDatabaseHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new PlanetsDatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    // CREATE tables
    private String createPlanetCategoryTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(PLANET_CATEGORY_TABLE_NAME);
        sb.append(" (");
        sb.append(PLANET_CATEGORY_COL_ID);
        sb.append(" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sb.append(PLANET_CATEGORY_COL_NAME);
        sb.append(" TEXT, ");
        sb.append(PLANET_CATEGORY_COL_DESC);
        sb.append(" TEXT, ");
        sb.append(PLANET_CATEGORY_COL_MIN_MASS);
        sb.append(" REAL, ");
        sb.append(PLANET_CATEGORY_COL_MAX_MASS);
        sb.append(" REAL);");
        return sb.toString();
    }

    private String createStellarCategoryTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(STELLAR_CATEGORY_TABLE_NAME);
        sb.append(" (");
        sb.append(STELLAR_CATEGORY_COL_ID);
        sb.append(" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sb.append(STELLAR_CATEGORY_COL_NAME);
        sb.append(" TEXT, ");
        sb.append(STELLAR_CATEGORY_COL_CLASS);
        sb.append(" TEXT, ");
        sb.append(STELLAR_CATEGORY_COL_DESC);
        sb.append(" TEXT, ");
        sb.append(STELLAR_CATEGORY_COL_MIN_TEMPERATURE);
        sb.append(" REAL, ");
        sb.append(STELLAR_CATEGORY_COL_MAX_TEMPERATURE);
        sb.append(" REAL);");
        return sb.toString();
    }

    private String createPlanetTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(PLANET_TABLE_NAME);
        sb.append(" (");
        sb.append(PLANET_COL_ID);
        sb.append(" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sb.append(PLANET_COL_NAME);
        sb.append(" TEXT, ");
        sb.append(PLANET_COL_RADIUS);
        sb.append(" REAL, ");
        sb.append(PLANET_COL_JUPITER_MASS);
        sb.append(" REAL, ");
        sb.append(PLANET_COL_CATEGORY_ID);
        sb.append(" REAL, FOREIGN KEY (");
        sb.append(PLANET_COL_CATEGORY_ID);
        sb.append(") REFERENCES ");
        sb.append(PLANET_CATEGORY_TABLE_NAME);
        sb.append("(");
        sb.append(PLANET_CATEGORY_COL_ID);
        sb.append(")");
        sb.append(");");
        return sb.toString();
    }

    private String createStarTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(STAR_TABLE_NAME);
        sb.append(" (");
        sb.append(STAR_COL_ID);
        sb.append(" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sb.append(STAR_COL_NAME);
        sb.append(" TEXT, ");
        sb.append(STAR_COL_TEMPERATURE);
        sb.append(" REAL, ");
        sb.append(STAR_COL_STELLAR_CATEGORY_ID);
        sb.append(" REAL, FOREIGN KEY (");
        sb.append(STAR_COL_STELLAR_CATEGORY_ID);
        sb.append(") REFERENCES ");
        sb.append(STELLAR_CATEGORY_TABLE_NAME);
        sb.append("(");
        sb.append(STELLAR_CATEGORY_COL_ID);
        sb.append(")");
        sb.append(");");
        return sb.toString();
    }

    // DROP tables
    private String dropPlanetCategoryTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append(PLANET_CATEGORY_TABLE_NAME);
        sb.append(";");
        return sb.toString();
    }

    private String dropStellarCategoryTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append(STELLAR_CATEGORY_TABLE_NAME);
        sb.append(";");
        return sb.toString();
    }

    private String dropStarTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append(STAR_TABLE_NAME);
        sb.append(";");
        return sb.toString();
    }

    private String dropPlanetTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append(PLANET_TABLE_NAME);
        sb.append(";");
        return sb.toString();
    }

    // stellar category CRUD
    public void addStellarCategory(StellarCategory category) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(STELLAR_CATEGORY_COL_NAME, category.getName());
        values.put(STELLAR_CATEGORY_COL_CLASS, category.getSpectralClass());
        values.put(STELLAR_CATEGORY_COL_MIN_TEMPERATURE, category.getMinTemp());
        values.put(STELLAR_CATEGORY_COL_MAX_TEMPERATURE, category.getMaxTemp());
        values.put(STELLAR_CATEGORY_COL_DESC, category.getDescription());
        db.insert(STELLAR_CATEGORY_TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void seedStellarCategory(StellarCategory category, SQLiteDatabase db) {
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(STELLAR_CATEGORY_COL_NAME, category.getName());
        values.put(STELLAR_CATEGORY_COL_CLASS, category.getSpectralClass());
        values.put(STELLAR_CATEGORY_COL_MIN_TEMPERATURE, category.getMinTemp());
        values.put(STELLAR_CATEGORY_COL_MAX_TEMPERATURE, category.getMaxTemp());
        values.put(STELLAR_CATEGORY_COL_DESC, category.getDescription());
        db.insert(STELLAR_CATEGORY_TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public StellarCategory getStellarCategory(int id) {
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");
        query.append(STELLAR_CATEGORY_TABLE_NAME);
        query.append(" WHERE ");
        query.append(STELLAR_CATEGORY_COL_ID);
        query.append(" = ");
        query.append(String.valueOf(id));
        Cursor cursor = db.rawQuery(query.toString(), null);
        if (cursor.moveToFirst()) {
            return new StellarCategory(
                    cursor.getInt(cursor.getColumnIndex(STELLAR_CATEGORY_COL_ID)),
                    cursor.getString(cursor.getColumnIndex(STELLAR_CATEGORY_COL_CLASS)),
                    cursor.getString(cursor.getColumnIndex(STELLAR_CATEGORY_COL_NAME)),
                    cursor.getDouble(cursor.getColumnIndex(STELLAR_CATEGORY_COL_MIN_TEMPERATURE)),
                    cursor.getDouble(cursor.getColumnIndex(STELLAR_CATEGORY_COL_MAX_TEMPERATURE)),
                    cursor.getString(cursor.getColumnIndex(STELLAR_CATEGORY_COL_DESC))
            );
        }
        return null;
    }

    public List<StellarCategory> getAllStellarCategories() {
        List<StellarCategory> categories = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");
        query.append(STELLAR_CATEGORY_TABLE_NAME);
        Cursor cursor = db.rawQuery(query.toString(), null);
        if (cursor.moveToFirst()) {
            do {
                categories.add(new StellarCategory(
                        cursor.getInt(cursor.getColumnIndex(STELLAR_CATEGORY_COL_ID)),
                        cursor.getString(cursor.getColumnIndex(STELLAR_CATEGORY_COL_CLASS)),
                        cursor.getString(cursor.getColumnIndex(STELLAR_CATEGORY_COL_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(STELLAR_CATEGORY_COL_MIN_TEMPERATURE)),
                        cursor.getDouble(cursor.getColumnIndex(STELLAR_CATEGORY_COL_MAX_TEMPERATURE)),
                        cursor.getString(cursor.getColumnIndex(STELLAR_CATEGORY_COL_DESC))
                ));
            } while(cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }

    public List<StellarCategory> searchAllStellarCategories(String val) {
        List<StellarCategory> categories = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");
        query.append(STELLAR_CATEGORY_TABLE_NAME);
        query.append(" WHERE ");
        query.append(STELLAR_CATEGORY_COL_NAME);
        query.append(" LIKE '%");
        query.append(val);
        query.append("%'");
        Cursor cursor = db.rawQuery(query.toString(), null);
        if (cursor.moveToFirst()) {
            do {
                categories.add(new StellarCategory(
                        cursor.getInt(cursor.getColumnIndex(STELLAR_CATEGORY_COL_ID)),
                        cursor.getString(cursor.getColumnIndex(STELLAR_CATEGORY_COL_CLASS)),
                        cursor.getString(cursor.getColumnIndex(STELLAR_CATEGORY_COL_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(STELLAR_CATEGORY_COL_MIN_TEMPERATURE)),
                        cursor.getDouble(cursor.getColumnIndex(STELLAR_CATEGORY_COL_MAX_TEMPERATURE)),
                        cursor.getString(cursor.getColumnIndex(STELLAR_CATEGORY_COL_DESC))
                ));
            } while(cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }




    // planet category CRUD

    // star crud

    // planet crud


    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME,
    // this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createPlanetCategoryTable());
        db.execSQL(createStellarCategoryTable());
        db.execSQL(createPlanetTable());
        db.execSQL(createStarTable());
        seedDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(dropPlanetCategoryTable());
        db.execSQL(dropStellarCategoryTable());
        db.execSQL(dropPlanetTable());
        db.execSQL(dropStarTable());
        onCreate(db);
    }

    /***
     * Seed SQLiteDatabase db with data
     * @param db
     */
    public void seedDatabase(SQLiteDatabase db) {
        // first star
        StellarCategory firstStellarCategory = new StellarCategory(0, "O", "Blue", 30000, 60000, "These are the rarest of all main-sequence stars. About 1 in 3,000,000 (0.00003%) of the main-sequence stars in the solar neighborhood are O-type stars. Some of the most massive stars lie within this spectral class. Because of their early development, the O-Type stars are already luminous in the huge hydrogen and helium clouds in which lower mass stars are forming. They light the stellar nurseries with ultraviolet light and cause the clouds to glow in some of the dramatic nebulae associated with the H II regions.");
        StellarCategory secondStellarCategory = new StellarCategory(1, "B", "Blue-White", 10000, 30000, "B-type stars are very luminous and blue. As O- and B-type stars are so energetic, they only live for a relatively short time. Thus, due to the low probability of kinematic interaction during their lifetime, they are unable to stray far from the area in which they formed, apart from runaway stars. which are associated with giant molecular clouds. About 1 in 800 (0.125%) of the main-sequence stars in the solar neighborhood are B-type main-sequence objects.");
        StellarCategory thirdStellarCategory = new StellarCategory(2, "A", "White", 7500, 10000, "A-type stars are among the more common naked eye stars, and are white or bluish-white. About 1 in 160 (0.625%) of the main-sequence stars in the solar neighborhood are A-type stars.");
        StellarCategory fourthCategory = new StellarCategory(3, "F", "Yellow-White", 6000, 7500, "F-type stars have strengthening H and K lines of Ca II. N Their spectra are characterized by the weaker hydrogen lines and ionized metals. Their color is white. About 1 in 33 (3.03%) of the main-sequence stars in the solar neighborhood are F-type stars.");
        StellarCategory fifthCategory = new StellarCategory(4, "G", "Yellow", 5000, 6000, "G-type stars  include the Sun. Class G main-sequence stars make up about 7.5%, nearly one in thirteen, of the main-sequence stars in the solar neighborhood. G is host to the \"Yellow Evolutionary Void\". Supergiant stars often swing between O or B (blue) and K or M (red). While they do this, they do not stay for long in the yellow supergiant G classification as this is an extremely unstable place for a supergiant to be.");
        StellarCategory sixthCategory = new StellarCategory(5, "K", "Yellow-Orange", 3500, 5000, "K-type stars are orangish stars that are slightly cooler than the Sun. They make up about 12%, nearly one in eight, of the main-sequence stars in the solar neighborhood. There are also giant K-type stars, which range from hypergiants like RW Cephei, to giants and supergiants, such as Arcturus, whereas orange dwarfs, like Alpha Centauri B, are main-sequence stars. There is a suggestion that K Spectrum stars may potentially increase the chances of life developing on orbiting planets that are within the habitable zone.");
        StellarCategory seventhCategory = new StellarCategory(6, "M", "Red", 0, 3500, "Class M stars are by far the most common. About 76% of the main-sequence stars in the solar neighborhood are class M stars. However, class M main-sequence stars (red dwarfs) have such low luminosities that none are bright enough to be seen with the unaided eye, unless under exceptional conditions. Although most class M stars are red dwarfs, most giants and some supergiants such as VY Canis Majoris, Antares and Betelgeuse are also class M. Furthermore, the hotter brown dwarfs are late class M.");

        seedStellarCategory(firstStellarCategory, db);
        seedStellarCategory(secondStellarCategory, db);
        seedStellarCategory(thirdStellarCategory, db);
        seedStellarCategory(fourthCategory, db);
        seedStellarCategory(fifthCategory, db);
        seedStellarCategory(sixthCategory, db);
        seedStellarCategory(seventhCategory, db);
    }
}
