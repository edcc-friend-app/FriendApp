package edcc.friendfinder;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View rootView;
    private ImageButton ibtnUserPhoto;
    private TextView lblInfo;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ibtnUserPhoto = rootView.findViewById(R.id.ibtnUserPhoto);
        //lblInfo = rootView.findViewById(R.id.lblInfo);
        return rootView;
    }



//    public void ibtnUserPhotoOnClick(View view) {
//        dispatchTakePictureIntent();
//    }

//    public void dispatchTakePictureIntent(){
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitMap = (Bitmap) extras.get("data");
//            ibtnUserPhoto.setImageBitmap(imageBitMap);
//            String info = (imageBitMap.getWidth() + "x" + imageBitMap.getHeight());
//            lblInfo.setText(info);
//        }
//    }

}
