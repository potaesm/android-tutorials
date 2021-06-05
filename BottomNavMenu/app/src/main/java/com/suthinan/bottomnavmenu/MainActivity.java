package com.suthinan.bottomnavmenu;

import android.annotation.SuppressLint;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_menu);
        bottomNavView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    System.out.println("Clicked Home");
                    Toast.makeText(getBaseContext(), "Clicked Home", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_about_us:
                    System.out.println("Clicked About Us");
                    Toast.makeText(getBaseContext(), "Clicked About Us", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_contact_us:
                    System.out.println("Clicked Contact Us");
                    Toast.makeText(getBaseContext(), "Clicked Contact Us", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        });
    }
}