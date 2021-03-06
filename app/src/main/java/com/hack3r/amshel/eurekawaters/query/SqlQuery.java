package com.hack3r.amshel.eurekawaters.query;

public class SqlQuery {
    public static final String TABLE_CLIENT = "client";
    public static final String CLIENT_COLUMN_ID = "client";
    public static final String CLIENT_COLUMN_CODE = "code";
    public static final String CLIENT_COLUMN_NAME = "full_name";
    public static final String CLIENT_COLUMN_METER = "meter_no";
    public static final String CLIENT_COLUMN_TELEPHONE = "mobile";
    private static final String INDEX2 = "identifictaion2";

    public static final String TABLE_READING = "reading";
    public static final String READING_COLUMN_ID = "reading";
    public static final String READING_COLUMN_CLIENT = "client";
    public static final String READING_COLUMN_DATE = "date";
    public static final String READING_COLUMN_VALUE = "value";
    private static final String INDEX1 = "identification1";

    public static final String subq = "subq";
    public static final String subq1 = "subq1";
//query to create the table reading

     public static final String CREATE_READING_TABLE = "CREATE TABLE "
            +TABLE_READING +"("
            +READING_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +READING_COLUMN_CLIENT +" TEXT NOT NULL, "
            +READING_COLUMN_DATE +" TEXT NOT NULL, "
            +READING_COLUMN_VALUE+" INTEGER NOT NULL, " +
             "FOREIGN KEY("+READING_COLUMN_CLIENT+") REFERENCES "+ TABLE_CLIENT +"("+CLIENT_COLUMN_ID +"))";
//query to create the table client
    public static final String CREATE_CLIENT_TABLE = "CREATE TABLE "
            +TABLE_CLIENT +"("
            +CLIENT_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +CLIENT_COLUMN_CODE +" TEXT NOT NULL, "
            +CLIENT_COLUMN_NAME+" TEXT NOT NULL, "
            +CLIENT_COLUMN_METER+" TEXT, "
            +CLIENT_COLUMN_TELEPHONE+ " TEXT) ";

//query to drop both the client and reading table
    public static final String DROP_TABLE_CLIENT ="DROP TABLE IF EXISTS "+TABLE_CLIENT;
    public static final String DROP_TABLE_READING ="DROP TABLE IF EXISTS "+TABLE_READING;

    //insert a single reading
    public static final String INSERT_READING = "INSERT into "
                                                    +TABLE_READING
                                                    +"(" +READING_COLUMN_DATE+ ", "
                                                    + READING_COLUMN_VALUE+ ", "
                                                    +READING_COLUMN_CLIENT+ ") "
                                                    +"VALUES(?, ?, (SELECT "+CLIENT_COLUMN_ID
                                                    + " FROM " +TABLE_CLIENT
                                                    + " WHERE "+CLIENT_COLUMN_CODE+"=?))";
    //query to insert a new client
    public static final String INSERT_CLIENTS = "INSERT into "
                                                    +TABLE_CLIENT
                                                    +"("+CLIENT_COLUMN_CODE+", "
                                                    +CLIENT_COLUMN_NAME+", "
                                                    +CLIENT_COLUMN_METER+", "
                                                    +CLIENT_COLUMN_TELEPHONE+ ") "
                                                +" VALUES ( ?, ?, ?, ?)";

    //select a specific number of clients for auto complete
    public static final String SELECT_GROUP_CLIENTS = "SELECT * FROM "+ TABLE_CLIENT;

    //These three queries are for calculating the reading on the maximum date ad also retrieving
    //the client details
    private  static final String MAX_DATE ="SELECT "+
                                                READING_COLUMN_CLIENT+
                                                ", max("+READING_COLUMN_DATE+") as "+
                                                READING_COLUMN_DATE +
                                            " FROM "+
                                                TABLE_READING+
                                            " GROUP BY "+READING_COLUMN_CLIENT;


    private static final String MAX_READING = "SELECT "+
                                                TABLE_READING +".*" +
                                            " FROM "+
                                                TABLE_READING + " INNER JOIN ("+MAX_DATE+") AS "+subq+" ON " +
                                                TABLE_READING+"."+READING_COLUMN_DATE+ "=" +subq+"."+READING_COLUMN_DATE+
                                            " AND " +
                                                TABLE_READING+"."+READING_COLUMN_CLIENT +"="+subq+"."+READING_COLUMN_CLIENT;

    public static final String SELECT_PREVIOUS_READING = "SELECT "+
                                                            TABLE_CLIENT+".*, "+
                                                            subq1+".* "+
                                                        " FROM "+
                                                            TABLE_CLIENT+" LEFT JOIN ("+MAX_READING+") AS "+subq1+
                                                        " ON "+TABLE_CLIENT+"."+CLIENT_COLUMN_ID+"="+subq1+"."+CLIENT_COLUMN_ID+
                                                        " WHERE "+CLIENT_COLUMN_CODE +"=?";
    /**
     * Query for selecting the edge cases.
     * What are edge cases???
     * These are records in which dates dont have the yyyy-mm-dd format
     * These dates have dd-mm-yyyy format and since elias had collected quite afew i didnt change the
     * format of the date until the period was over
     */
    public static final String DAILY_READING_POSTING = "SELECT "
                                                            +TABLE_CLIENT+".*, "
                                                            +TABLE_READING+".* "
                                                        +" FROM "
                                                            +TABLE_READING+ " INNER JOIN "+ TABLE_CLIENT +" ON "
                                                            +TABLE_READING+"."+CLIENT_COLUMN_ID+"=" +TABLE_CLIENT+"."+CLIENT_COLUMN_ID
                                                        +" WHERE "+TABLE_READING+"."+READING_COLUMN_DATE+ " NOT LIKE '2018-%'";

    //query for getting all readings in the database
    public static final String ALL_READINGS = "SELECT "
                                                    +TABLE_CLIENT+".*, "
                                                    +TABLE_READING+".* "
                                                +" FROM "
                                                    +TABLE_READING+ " INNER JOIN "+ TABLE_CLIENT +" ON "
                                                    +TABLE_READING+"."+CLIENT_COLUMN_ID+"=" +TABLE_CLIENT+"."+CLIENT_COLUMN_ID
                                                + " ORDER BY "+READING_COLUMN_DATE+ " DESC";


    //function that accepts a query string and formulates an sql based on it
    public static String formulateSql(String query){

        //split the query
        String[] params = query.split("[= > <]+");

        String column = params[0];
        String val = "'"+params[1]+"'";

        //query for fetiching readings of a particular criteria
        String SPECIFIC_READINGS = "SELECT "
                +TABLE_CLIENT+".*, "
                +TABLE_READING+".* "
                +" FROM "
                +TABLE_READING+ " INNER JOIN "+ TABLE_CLIENT +" ON "
                +TABLE_READING+"."+CLIENT_COLUMN_ID+"=" +TABLE_CLIENT+"."+CLIENT_COLUMN_ID
                +" WHERE "+column +"=" +val
                + " ORDER BY "+READING_COLUMN_DATE+ " DESC";

        return SPECIFIC_READINGS;
    }

    //set unique indexes
    public static final String READING_INDEXES = "CREATE UNIQUE INDEX "+INDEX1 +" ON "+TABLE_READING+"("+READING_COLUMN_DATE+", "+READING_COLUMN_CLIENT+ ")";
    public static final String CLIENT_INDEXES = "CREATE UNIQUE INDEX "+INDEX2 +" ON "+TABLE_CLIENT+"("+CLIENT_COLUMN_CODE+")";

}
