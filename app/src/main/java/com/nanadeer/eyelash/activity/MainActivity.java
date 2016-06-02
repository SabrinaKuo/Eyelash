package com.nanadeer.eyelash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.fragment.CustomListFragment;
import com.nanadeer.eyelash.fragment.NewCustomFragment;
import com.nanadeer.eyelash.parameter.EyelashParameter;

public class MainActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomListFragment customListFragment = new CustomListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(EyelashParameter.FRAGMENT_CUSTOM);
        fragmentTransaction.replace(R.id.mainActivityContent, customListFragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                NewCustomFragment newCustomFragment = new NewCustomFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(EyelashParameter.FRAGMENT_ADD);
                fragmentTransaction.replace(R.id.mainActivityContent, newCustomFragment);
                fragmentTransaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
