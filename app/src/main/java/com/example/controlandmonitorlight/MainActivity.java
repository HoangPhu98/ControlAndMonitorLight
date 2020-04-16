package com.example.controlandmonitorlight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.model.User;
import com.example.controlandmonitorlight.view.view.Activity.LoginActivity;
import com.example.controlandmonitorlight.view.view.Activity.RoomActivity;
import com.example.controlandmonitorlight.view.view.Activity.SettingActivity;
import com.example.controlandmonitorlight.view.view.Activity.StaticActivity;
import com.example.controlandmonitorlight.view.view.Fragment.HomeFragment;
import com.example.controlandmonitorlight.view.view.Fragment.SettingFragment;
import com.example.controlandmonitorlight.view.view.Fragment.StaticFragment;
import com.example.controlandmonitorlight.viewmodel.Comunication;
import com.example.controlandmonitorlight.viewmodel.IntroductionViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_ROOM_ID = "com.example.controlandmonitorlight.KEY_ROOM_ID";
    public static final String KEY_ROOM_NAME = "com.example.controlandmonitorlight.KEY_ROOM_NAME";

    private FirebaseUser mUser;
    private BottomNavigationView bottomNav;
    private FragmentManager fragmentManager ;
    private RecyclerView recyclerView ;
    private TextView mName , mTitle ;
    private CircleImageView imageUser ;
    private List<Room> mListRoom = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        Mapping();
        if(savedInstanceState == null){
            bottomNav.setSelectedItemId(R.id.home);
        }

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_place,new HomeFragment()).commit();
        mName.setText("Hi, "+mUser.getDisplayName()+" !");
        Glide.with(this)
                .load(mUser.getPhotoUrl().toString())
                .apply(RequestOptions.circleCropTransform())
                .into(imageUser);
        /*
            IntroductionViewModel viewModel = ViewModelProviders.of(this).get(IntroductionViewModel.class);
            viewModel.SetData();
            viewModel.LoadDataFireBase(this);
            viewModel.getIntro().observe(this, new Observer<List<Room>>() {
                @Override
                public void onChanged(List<Room> rooms) {

                }
            });

        */


    }
    void Mapping(){
        imageUser = findViewById(R.id.profile_image);
        mTitle = findViewById(R.id.txt_title);
        mName = findViewById(R.id.txt_name);
        bottomNav = findViewById(R.id.navigation);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null ;
            switch (menuItem.getItemId()){
                case R.id.home:
                    fragment = new HomeFragment();
                    break;
                case R.id.static1:
                    fragment = new StaticFragment();
                    break;
                case R.id.profile:
                    fragment = new SettingFragment();
                    break;
            }
            if(fragment != null ){
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_place,fragment).commit();
                return true ;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }



}
