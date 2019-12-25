package com.example.aws.uit_blog.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aws.uit_blog.Adapters.UserAdapter;
import com.example.aws.uit_blog.Models.User;
import com.example.aws.uit_blog.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserFragment  extends Fragment {
    private  RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_users,container,false);

        recyclerView = view.findViewById(R.id.search_user);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsers = new ArrayList<>();
        readUsers();
        return view;
    }

    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    assert firebaseUser != null;
                    if (user.getImageUrl().equals(firebaseUser.getUid()))
                    {
                        mUsers.add(user);

                    }
                }
                userAdapter = new UserAdapter(getContext(),mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
