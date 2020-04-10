/*viewmodel*/
package com.example.controlandmonitorlight.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IntroductionViewModel extends ViewModel {
    private MutableLiveData<List<Room>> intro = new MutableLiveData<>() ;
    public MutableLiveData<User> title = new MutableLiveData<>();
    public MutableLiveData<List<Room>> getIntro() {
        return intro;
    }
    public List<Room> list = new ArrayList<>();
    public void SetData()
    {
       this.intro.setValue(list);
    }
    public void LoadTitle(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child("00001");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                title.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void LoadDataFireBase(final Context context)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("rooms");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    //Log.d("key",snapshot.getKey()+"");
                  //  Toast.makeText(context,""+snapshot.getValue(),Toast.LENGTH_SHORT).show();
                    Room r = snapshot.getValue(Room.class);
                    list.add(r);
                }
                intro.postValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

