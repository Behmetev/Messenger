package com.grib.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private UsersAdapter usersAdapter;

    private UsersViewModel usersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        initViews();
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        observeViewModel();

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            User user = new User("in" + i, "user" + i, "ln" + i, 10 + i, new Random().nextBoolean());
            users.add(user);
        }
        usersAdapter.setUsers(users);
    }

    private void observeViewModel() {
        usersViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Intent intent = LoginActivity.newIntent(UsersActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initViews() {
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        usersAdapter = new UsersAdapter();
        recyclerViewUsers.setAdapter(usersAdapter);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, UsersActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_logout) {
            usersViewModel.logout();
        }
        return super.onOptionsItemSelected(item);
    }
}