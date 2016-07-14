package com.nanadeer.eyelash.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.database.EyelashDatabaseManager;
import com.nanadeer.eyelash.database.EyelashSQLiteHelper;
import com.nanadeer.eyelash.fragment.CustomListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EyelashSQLiteHelper helper = new EyelashSQLiteHelper(this);
        EyelashDatabaseManager.initializeInstance(helper);

        CustomListFragment customListFragment = new CustomListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainActivityContent, customListFragment);
        fragmentTransaction.commit();

    }

}
