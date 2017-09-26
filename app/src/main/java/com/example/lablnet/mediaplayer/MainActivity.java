package com.example.lablnet.mediaplayer;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    List<Data> myList = new ArrayList<>();
    NavigationView mNavigationView;
    DrawerLayout drawerLayout;
    ListView lst;
    boolean Audio = true;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lst = (ListView) findViewById(R.id.listView);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //set Toggle on actionBar
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.drawerOpen, R.string.drawerClose
        );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getAudioFile();
        setAdapter();
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Data data = myList.get(position);
                String path = data.getPath();
                Intent intent;
                /*Check whether user Click audio or video*/
                if (Audio) {
                    intent = new Intent(MainActivity.this, Play_Audio.class);
                } else {
                    intent = new Intent(MainActivity.this, Play_Video_Activity.class);
                }
                intent.putExtra("Path", path);
                intent.putExtra("Title", data.getFileTitle());
                startActivity(intent);
            }
        });
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.audio);

    }

    /*This method return list of all Audio file store on storage*/
    public List getAudioFile() {
        myList.clear();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        while (c.moveToNext()) {
            int titleId = c.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int sizeindex = c.getColumnIndex(MediaStore.Audio.Media.SIZE);
            int durationColumn = c.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int pathId = c.getColumnIndex(MediaStore.Audio.Media.DATA);
            int albumId = c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            if (c.moveToFirst()) {
                do {
                    String fileTitle = c.getString(titleId);
                    String albumID = c.getString(albumId);
                    String duration = c.getString(durationColumn);
                    String path = c.getString(pathId);
                    String size = c.getString(sizeindex);
                    Data data = new Data(fileTitle, duration, albumID, path, size);
                    myList.add(data);
                } while (c.moveToNext());
            }
        }
        c.close();
        return myList;
    }

    /*This method return list of all Video file store on storage*/
    public List getVideoFiles() {
        myList.clear();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        while (c.moveToNext()) {
            int titleId = c.getColumnIndex(MediaStore.Video.Media.TITLE);
            int sizeindex = c.getColumnIndex(MediaStore.Video.Media.SIZE);
            int durationColumn = c.getColumnIndex(MediaStore.Video.Media.DURATION);
            int pathId = c.getColumnIndex(MediaStore.Video.Media.DATA);
            int albumId = c.getColumnIndex(MediaStore.Video.Media.ALBUM);
            if (c.moveToFirst()) {
                do {
                    String fileTitle = c.getString(titleId);
                    String albumID = c.getString(albumId);
                    String duration = c.getString(durationColumn);
                    String path = c.getString(pathId);
                    String size = c.getString(sizeindex);
                    Data data = new Data(fileTitle, duration, albumID, path, size);
                    myList.add(data);
                } while (c.moveToNext());
            }
        }
        c.close();
        return myList;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.audio:
                getAudioFile();
                setAdapter();
                Audio = true;
                return true;
            case R.id.video:
                getVideoFiles();
                setAdapter();
                Audio = false;
                return true;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    public void setAdapter() {
        Adapter adapter = new Adapter(this, myList);
        lst.setAdapter(adapter);
    }
}