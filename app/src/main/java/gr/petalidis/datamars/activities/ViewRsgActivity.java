/*        Copyright 2017 Nikolaos Petalidis
*
*        Licensed under the Apache License, Version 2.0 (the "License");
*        you may not use this file except in compliance with the License.
*        You may obtain a copy of the License at
*
*        http://www.apache.org/licenses/LICENSE-2.0
*
*        Unless required by applicable law or agreed to in writing, software
*        distributed under the License is distributed on an "AS IS" BASIS,
*        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*        See the License for the specific language governing permissions and
*        limitations under the License.
*/


package gr.petalidis.datamars.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

import gr.petalidis.datamars.R;
import gr.petalidis.datamars.rsglibrary.Rsg;
import gr.petalidis.datamars.rsglibrary.RsgReader;


public class ViewRsgActivity extends AppCompatActivity {

    private final static String TAG = ViewRsgActivity.class.getName();
    private TextView mTextMessage;
    private String filename = "";
    private String date = "";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rsg);

        if (savedInstanceState != null) {
            filename = savedInstanceState.getString("filename");
            if (filename == null) {
                filename = "";
            }
            if (date == null) {
                date = "";
            }
        } else {
            Intent intent = getIntent();
            if (intent != null) {
                filename = intent.getStringExtra("rsg");
                date = intent.getStringExtra("date");
            }
        }
        try {
            TextView dateField = (TextView)findViewById(R.id.rsgDate);
            dateField.setText(date);
            ArrayList<Rsg> rsgs = RsgReader.readRsgFromTablet(filename);
            RsgAdapter adapter = new RsgAdapter(this, rsgs);
            ListView gridView = (ListView) findViewById(R.id.rsglistId);
            gridView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, "Unable to readRsgFromTablet: " + e.getLocalizedMessage());
        }


    }

    public void sortGridEntriesByCountry(View view) {
        ListView gridView = (ListView) findViewById(R.id.rsglistId);
        RsgAdapter rsgAdapter = (RsgAdapter) gridView.getAdapter();
        rsgAdapter.sort(new Comparator<Rsg>() {
            @Override
            public int compare(Rsg first, Rsg second) {
                return first.getCountryCode().compareTo(second.getCountryCode());
            }
        });

        Button button = (Button)findViewById(R.id.countryButton);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                sortGridEntriesByCountryReverse(v);
            }
        });

    }

    public void sortGridEntriesByCountryReverse(View view) {
        ListView gridView = (ListView) findViewById(R.id.rsglistId);
        RsgAdapter rsgAdapter = (RsgAdapter) gridView.getAdapter();
        rsgAdapter.sort(new Comparator<Rsg>() {
            @Override
            public int compare(Rsg first, Rsg second) {
                return second.getCountryCode().compareTo(first.getCountryCode());
            }
        });

        Button button = (Button)findViewById(R.id.countryButton);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                sortGridEntriesByCountry(v);
            }
        });
    }
    public void sortGridEntriesById(View view) {
        ListView gridView = (ListView) findViewById(R.id.rsglistId);
        RsgAdapter rsgAdapter = (RsgAdapter) gridView.getAdapter();
        rsgAdapter.sort(new Comparator<Rsg>() {
            @Override
            public int compare(Rsg first, Rsg second) {
                return first.getIdentificationCode().compareTo(second.getIdentificationCode());
            }
        });
        Button button = (Button)findViewById(R.id.idButton);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                sortGridEntriesByIdReverse(v);
            }
        });


    }

    public void sortGridEntriesByIdReverse(View view) {
        ListView gridView = (ListView) findViewById(R.id.rsglistId);
        RsgAdapter rsgAdapter = (RsgAdapter) gridView.getAdapter();
        rsgAdapter.sort(new Comparator<Rsg>() {
            @Override
            public int compare(Rsg first, Rsg second) {
                return second.getIdentificationCode().compareTo(first.getIdentificationCode());
            }
        });
        Button button = (Button)findViewById(R.id.idButton);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                sortGridEntriesById(v);
            }
        });
    }

    public void sortGridEntriesByTime(View view) {
        ListView gridView = (ListView) findViewById(R.id.rsglistId);
        RsgAdapter rsgAdapter = (RsgAdapter) gridView.getAdapter();
        rsgAdapter.sort(new Comparator<Rsg>() {
            @Override
            public int compare(Rsg first, Rsg second) {
                return first.getDate().compareTo(second.getDate());
            }
        });
        Button button = (Button)findViewById(R.id.timeButton);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                sortGridEntriesByTimeReverse(v);
            }
        });

    }

    public void sortGridEntriesByTimeReverse(View view) {
        ListView gridView = (ListView) findViewById(R.id.rsglistId);
        RsgAdapter rsgAdapter = (RsgAdapter) gridView.getAdapter();
        rsgAdapter.sort(new Comparator<Rsg>() {
            @Override
            public int compare(Rsg first, Rsg second) {
                return second.getDate().compareTo(first.getDate());
            }
        });
        Button button = (Button)findViewById(R.id.timeButton);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                sortGridEntriesByTime(v);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("filename", filename);
        savedInstanceState.putString("date",date);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            filename = savedInstanceState.getString("filename");
            if (filename == null) {
                filename = "";
            }
            date = savedInstanceState.getString("date");
        }
    }

}
