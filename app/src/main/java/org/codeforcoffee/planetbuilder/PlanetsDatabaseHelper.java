package org.codeforcoffee.planetbuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by codeforcoffee on 7/8/16.
 */
public class PlanetsDatabaseHelper extends SQLiteOpenHelper {

    // db version & name
    public static final String DATABASE_NAME = "Planets.db";
    public static final int DATABASE_VERSION = 7;

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
        values.put(STELLAR_CATEGORY_COL_ID, "null");
        values.put(STELLAR_CATEGORY_COL_NAME, category.getName());
        values.put(STELLAR_CATEGORY_COL_MIN_TEMPERATURE, category.getMinTemp());
        values.put(STELLAR_CATEGORY_COL_MAX_TEMPERATURE, category.getMaxTemp());
        values.put(STELLAR_CATEGORY_COL_DESC, category.getDescription());
        db.insert(STELLAR_CATEGORY_TABLE_NAME, null,values);
        db.setTransactionSuccessful();
        db.endTransaction();
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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(dropPlanetCategoryTable());
        db.execSQL(dropStellarCategoryTable());
        db.execSQL(dropPlanetTable());
        db.execSQL(dropStarTable());
        onCreate(db);
    }
}
