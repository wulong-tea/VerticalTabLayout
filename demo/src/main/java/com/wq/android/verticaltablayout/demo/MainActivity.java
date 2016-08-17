package com.wq.android.verticaltablayout.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;
import com.wq.android.lib.verticaltablayout.VerticalTabLayout;

public class MainActivity extends AppCompatActivity implements VerticalTabLayout.OnTabSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LeakCanary.install(getApplication());
        VerticalTabLayout vTabLayout = (VerticalTabLayout) findViewById(R.id.tab_layout_1);
        for (int i = 0; i < 20; i++) {
            vTabLayout.addTab(vTabLayout.newTab().setText("TEST " + i).setDrawable(R.drawable.ic_selector));
        }
        vTabLayout.setOnTabSelectedListener(this);
        vTabLayout = (VerticalTabLayout) findViewById(R.id.tab_layout_2);
        for (int i = 0; i < 20; i++) {
            vTabLayout.addTab(vTabLayout.newTab().setText("TEST " + i).setDrawable(R.drawable.ic_selector));
        }
        vTabLayout.setOnTabSelectedListener(this);
        vTabLayout = (VerticalTabLayout) findViewById(R.id.tab_layout_3);
        for (int i = 0; i < 20; i++) {
            vTabLayout.addTab(vTabLayout.newTab().setText("TEST " + i).setDrawable(R.drawable.ic_selector).setDrawableGravity(Gravity.RIGHT));
        }
        vTabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(VerticalTabLayout.Tab tab, int position) {
        Toast.makeText(getApplicationContext(), "onTabSelected: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReleased(VerticalTabLayout.Tab tab, int position) {

    }
}
