package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmObject;

public class MainActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getInstance(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final User standAloneUser = new User("zaki@example.com", "Makoto Yamazaki");
        standAloneUser.setPhone(new Phone("Android", "HT-03A"));

        tryMoshi(standAloneUser);
        tryGson(standAloneUser);
        tryJackson(standAloneUser);
        tryJPP(standAloneUser);
    }

    private void tryMoshi(User standAloneUser) {
        try {
            final Moshi moshi = new Moshi.Builder().build();
            final JsonAdapter<User> adapter = moshi.adapter(User.class);
            Log.d("json", "Moshi(stand alone): " + adapter.toJson(standAloneUser));


            final User managedUser;

            realm.beginTransaction();
            {
                managedUser = realm.copyToRealm(standAloneUser);
            }
            realm.commitTransaction();

            Log.d("json", "Moshi(managed): " + adapter.toJson(managedUser));
        } catch (Exception e) {
            Log.d("json", "Moshi: error", e);
        }
    }

    private void tryGson(User standAloneUser) {
        final Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        Log.d("json", "Gson(stand alone): " + gson.toJson(standAloneUser));


        final User managedUser;

        realm.beginTransaction();
        {
            managedUser = realm.copyToRealm(standAloneUser);
        }
        realm.commitTransaction();

        Log.d("json", "Gson(managed): " + gson.toJson(managedUser));
    }

    private void tryJackson(User standAloneUser) {
        try {
            final ObjectMapper mapper = new ObjectMapper();

            Log.d("json", "Jackson(stand alone): " + mapper.writeValueAsString(standAloneUser));


            final User managedUser;

            realm.beginTransaction();
            {
                managedUser = realm.copyToRealm(standAloneUser);
            }
            realm.commitTransaction();

            Log.d("json", "Jackson(managed): " + mapper.writeValueAsString(managedUser));
        } catch (Exception e) {
            Log.d("json", "Jackson: error", e);
        }
    }

    private void tryJPP(User standAloneUser) {
        try {
            StringWriter writer = new StringWriter();
            UserGen.encode(writer, standAloneUser);
            writer.close();

            Log.d("json", "JPP(stand alone): " + writer.toString());


            final User managedUser;

            realm.beginTransaction();
            {
                managedUser = realm.copyToRealm(standAloneUser);
            }
            realm.commitTransaction();

            writer = new StringWriter();
            UserGen.encode(writer, managedUser);
            writer.close();

            Log.d("json", "JPP(managed): " + writer.toString());
        } catch (Exception e) {
            Log.d("json", "JPP: error", e);
        }
    }
}
