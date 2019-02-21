package edcc.friendfinder;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditUserActivity extends AppCompatActivity {

    private Bitmap photo;
    private EditText firstName;
    private EditText lastName;
    private EditText major;
    private EditText age;
    private EditText firstclasses;
    private EditText bio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void ibtnUserPhotoOnClick(View view) {


    }
}
