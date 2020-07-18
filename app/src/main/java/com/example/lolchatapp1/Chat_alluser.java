package com.example.lolchatapp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class Chat_alluser extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_chat_alluser);
        ViewPager viewPager  = findViewById(R.id.viewpager);
        viewPager = addFrag(viewPager);

        TabLayout tabLayout = findViewById(R.id.tablayout);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Chats");
        tabLayout.getTabAt(1).setText("All Users");


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Lol Chat App");
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.logout:
                        Toast.makeText(Chat_alluser.this, "Logging out!!!", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        Intent i = new Intent(Chat_alluser.this,MainActivity.class);
                        startActivity(i);
                        finish();

                        return true;
                    case R.id.Acc_Set:
                        Intent i1 = new Intent(Chat_alluser.this,Acc_setting.class);
                        startActivity(i1);
                        return true;
                }
                return false;
            }
        });


    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                Toast.makeText(Chat_alluser.this, "Logging out!!!", Toast.LENGTH_SHORT);
                return true;
            case R.id.Acc_Set:
                Intent i = new Intent(Chat_alluser.this,Acc_setting.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    public ViewPager addFrag(ViewPager v ){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new Chats_f(), "Chats");
        viewPagerAdapter.addFrag(new Alluser_f(Chat_alluser.this), "All Users");

        v.setAdapter(viewPagerAdapter);

        return v;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            Intent i = new Intent(Chat_alluser.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
