package edcc.friendfinder;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class EditUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Event handler for the photo button click event.
     *
     * @param view the photo button
     */
//    public void ibtnUserPhotoOnClick(View view) {
//        dispatchTakePictureIntent();
//    }
//
//    /**
//     * Sends control to a camera app to get a thumbnail.
//     */
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, Extras.REQUEST_IMAGE_CAPTURE);
//        }
//    }

}
