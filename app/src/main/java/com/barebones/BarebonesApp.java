package com.barebones;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class BarebonesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Fresco.initialize(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
