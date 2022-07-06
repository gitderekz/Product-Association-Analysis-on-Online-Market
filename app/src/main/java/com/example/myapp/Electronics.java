package com.example.myapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Electronics extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TabItem MobilePhonesTab,ComputersTab,EarPhonesTab,SpeakersTab,CamerasTab,TvTab,GamingTab,MoreTab;

    BottomNavigationView bottomNavigationView;

    Settings settingsFragment = new Settings();
    Favourites favouritesFragment = new Favourites();
    MyHome myHomeFragment = new MyHome();
    Chats chatsFragment = new Chats();
    OnCart onCartFragment = new OnCart();
    Juice mobilePhones = new Juice();
    Soda computers = new Soda();
    Champagne earphones = new Champagne();
    HomeDrinks speakers = new HomeDrinks();
    Ingredients cameras = new Ingredients();
    Grain tv = new Grain();
    Baked gaming = new Baked();
    Vegetable more = new Vegetable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics);

        MobilePhonesTab = findViewById(R.id.mobilePhonesTab);
        ComputersTab = findViewById(R.id.computersTab);
        EarPhonesTab = findViewById(R.id.earphonesTab);
        SpeakersTab = findViewById(R.id.speakersTab);
        CamerasTab = findViewById(R.id.camerasTab);
        TvTab = findViewById(R.id.tvTab);
        GamingTab = findViewById(R.id.gamingTab);
        MoreTab = findViewById(R.id.moreTab);

        bottomNavigationView = findViewById(R.id.electronics_bottom_navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.startHome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.ElectronicsViewpager);

        tabLayout = findViewById(R.id.ElectronicsTabs);
//        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,tabLayout.getTabCount()/*,tabLayout.getSelectedTabPosition()*/);
        viewPager.setAdapter(adapter);
//        setupViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,tabLayout.getTabCount());



////        getSupportFragmentManager().beginTransaction().replace(R.id.electronicsContainer,mobilePhones,"MobilePhones").commit();
        adapter.addFrag(new Juice(), "MobilePhones");
        adapter.addFrag(new Soda(), "Computers");
        adapter.addFrag(new Champagne(), "Earphones");
        adapter.addFrag(new HomeDrinks(), "Speakers");
        adapter.addFrag(new Ingredients(), "Cameras");
        adapter.addFrag(new Grain(), "Tv");
        adapter.addFrag(new Baked(), "Gaming");
        adapter.addFrag(new Vegetable(), "More");
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.electronicsContainer,settingsFragment).addToBackStack(null).commit();
                return true;
            case R.id.favourites:
                getSupportFragmentManager().beginTransaction().replace(R.id.electronicsContainer,favouritesFragment).addToBackStack(null).commit();
                return true;
            case R.id.startHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.electronicsContainer,myHomeFragment).addToBackStack(null).commit();
                return true;
//            case R.id.chats:
//                getSupportFragmentManager().beginTransaction().replace(R.id.electronicsContainer,chatsFragment).addToBackStack(null).commit();
//                return true;
            case R.id.cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.electronicsContainer,onCartFragment).addToBackStack(null).commit();
                return true;
        }
        return false;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private  int tabsNumber;

        public ViewPagerAdapter(FragmentManager manager,int behaviour,int tabsNumber) {
            super(manager,behaviour);
            this.tabsNumber = tabsNumber;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position)
            {
                case 0:
                    fragment = mobilePhones;
//                    computers = null;
//                    earphones = null;
//                    speakers = null;
//                    cameras = null;
//                    tv = null;
//                    gaming = null;
//                    more = null;
                    break;
                case 1:
                    fragment = computers;
//                    mobilePhones = null;
//                    earphones = null;
//                    speakers = null;
//                    cameras = null;
//                    tv = null;
//                    gaming = null;
//                    more = null;
                    break;
                case 2:
                    fragment = earphones;
//                    mobilePhones = null;
//                    computers = null;
//                    speakers = null;
//                    cameras = null;
//                    tv = null;
//                    gaming = null;
//                    more = null;
                    break;
                case 3:
                    fragment = speakers;
//                    mobilePhones = null;
//                    earphones = null;
//                    computers = null;
//                    cameras = null;
//                    tv = null;
//                    gaming = null;
//                    more = null;
                    break;
                case 4:
                    fragment = cameras;
//                    mobilePhones = null;
//                    earphones = null;
//                    speakers = null;
//                    computers = null;
//                    tv = null;
//                    gaming = null;
//                    more = null;
                    break;
                case 5:
                    fragment = tv;
//                    mobilePhones = null;
//                    earphones = null;
//                    speakers = null;
//                    cameras = null;
//                    computers = null;
//                    gaming = null;
//                    more = null;
                    break;
                case 6:
                    fragment = gaming;
//                    mobilePhones = null;
//                    earphones = null;
//                    speakers = null;
//                    cameras = null;
//                    tv = null;
//                    computers = null;
//                    more = null;
                    break;
                case 7:
                    fragment = more;
//                    mobilePhones = null;
//                    earphones = null;
//                    speakers = null;
//                    cameras = null;
//                    tv = null;
//                    gaming = null;
//                    computers = null;
                    break;
            }
//            return mFragmentList.get(position);

            return fragment;
        }

        @Override
        public int getCount() {
//            return mFragmentList.size();
            return tabsNumber;
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            ViewPager viewPager = (ViewPager) container;
//            View view = (View) object;
//            viewPager.removeView(object);
        }
    }
}