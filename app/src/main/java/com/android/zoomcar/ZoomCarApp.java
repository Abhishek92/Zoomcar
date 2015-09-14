package com.android.zoomcar;

import android.app.Application;


import io.realm.Realm;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by hp pc on 12-09-2015.
 */
public class ZoomCarApp extends Application {
    private static Realm realmDb;
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            realmDb = Realm.getInstance(this);
        } catch (RealmMigrationNeededException ex) {

        }
    }

    public static Realm getRealmDb()
    {
        return realmDb;
    }
}
