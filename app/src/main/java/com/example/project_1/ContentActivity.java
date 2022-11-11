package com.example.project_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_1.adapter.UserRVAdapter;
import com.example.project_1.entity.User;
import com.example.project_1.view.ViewModal;

import java.util.List;

public class ContentActivity extends AppCompatActivity {

    // creating a variables for our button and edittext.
    private EditText userNameEdt, userPhoneEdt;
    private Button userBtn;

    // creating a variables for our recycler view.
    private RecyclerView userViewRV;

    private ViewModal viewModal;

    // creating a constant string variable for our
    // course name, description and duration.
    public static final String EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID";
    public static final String EXTRA_USER_NAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_USER_NAME";
    public static final String EXTRA_PHONE = "com.gtappdevelopers.gfgroomdatabase.EXTRA_PHONE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        // initializing our variables for each view.
        userViewRV = findViewById(R.id.idRVUsers);
        userNameEdt = findViewById(R.id.idEdtUserName);
        userPhoneEdt = findViewById(R.id.idEdtUserPhone);
        userBtn = findViewById(R.id.idBtnInsertUser);

        // below line is to get intent as we
        // are getting data via an intent.
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            // if we get id for our data then we are
            // setting values to our edit text fields.
            userNameEdt.setText(intent.getStringExtra(EXTRA_USER_NAME));
            userPhoneEdt.setText(intent.getStringExtra(EXTRA_PHONE));
        }
        // adding on click listener for our save button.
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text value from edittext and validating if
                // the text fields are empty or not.
                String userName = userNameEdt.getText().toString();
                String userPhone = userPhoneEdt.getText().toString();
                if (userName.isEmpty() || userPhone.isEmpty()) {
                    Toast.makeText(ContentActivity.this, "Please enter the valid course details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to save our course.
                saveUser(userName, userPhone);
            }
        });

        // setting layout manager to our adapter class.
        userViewRV.setLayoutManager(new LinearLayoutManager(this));
        userViewRV.setHasFixedSize(true);

        // initializing adapter for recycler view.
        final UserRVAdapter adapter = new UserRVAdapter();

        // setting adapter class for recycler view.
        userViewRV.setAdapter(adapter);

        // passing a data from view modal.
        viewModal = new ViewModelProvider(this).get(ViewModal.class);

        // below line is use to get all the courses from view modal.
        // when the data is changed in our models we are
        // adding that list to our adapter class.
        viewModal.getAllUsers().observe(this, adapter::submitList);
        // below method is use to add swipe to delete method for item of recycler view.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // on recycler view item swiped then we are deleting the item of our recycler view.
                viewModal.delete(adapter.getUserAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ContentActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
            }
        }).
                // below line is use to attach this to recycler view.
                        attachToRecyclerView(userViewRV);


    }

    private void saveUser(String name, String phone) {
        User model = new User(userNameEdt.getText().toString(), userPhoneEdt.getText().toString());
        viewModal.insert(model);
        Toast.makeText(this, "User saved", Toast.LENGTH_SHORT).show();
    }
}
