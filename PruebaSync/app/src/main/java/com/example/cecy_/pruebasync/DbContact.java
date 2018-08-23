package com.example.cecy_.pruebasync;

/**
 * Created by cecy_ on 30/07/2018.
 */
public class DbContact {
    public static final int SYNC_STATUS_OK = 0;
    public static final int SYNC_STATUS_FAILED = 1;
    public static String SERVER_URL = "http://192.168.1.71/arbolito/appSync.php";
    public static final String UI_UPDATE_BROADCAST = "com.example.cecy_.pruebasync.uiupdatebroadcast";

    public static final String DATABASE_NAME = "contactdb";
    public static final String TABLE_NAME = "contactinfo";
    public static final String NAME = "name";
    public static final String SYNC_STATUS = "syncstatus";



}
