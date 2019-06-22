package com.barebones.database;


import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.barebones.models.Video;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }


    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Video.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(Video.class);
        realm.commitTransaction();
    }

    //find all objects in the Video.class
    public RealmResults<Video> getVideo() {

        return realm.where(Video.class).findAll();
    }

    //query a single item with the given id
    public Video getVideo(String id) {
        return realm.where(Video.class).equalTo("id", id).findFirst();
    }

    //query a single item with the given id
    public void saveVideo(Video video) {
        //return realm.where(Video.class).equalTo("id", id).findFirst();
    }

    //check if Book.class is empty
    public boolean hasVideo() {

        return !realm.allObjects(Video.class).isEmpty();
    }


}
