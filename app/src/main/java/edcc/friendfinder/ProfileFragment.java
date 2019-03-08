package edcc.friendfinder;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View rootView;
    private User thisUser;
    private ProfileListener listener;
    private UserManager um;
    private TextView lblName;
    private TextView lblMajor;
    private TextView lblClasses;
    private TextView lblBio;
    private TextView lblLanguage;
    private ImageView imgUser;
    private PreferencesManager pm;

    public ProfileFragment() {
        // Required empty public constructor
    }

    interface ProfileListener {
        void editUser(User current);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        um = UserManager.getUserManager(getContext(), FirebaseAuth.getInstance().getUid());
        pm = PreferencesManager.getInstance(getActivity().getApplicationContext());
        //find UI components
        lblName = rootView.findViewById(R.id.lblName);
        lblMajor = rootView.findViewById(R.id.lblMajor);
        lblLanguage = rootView.findViewById(R.id.lblLanguage);
        lblClasses = rootView.findViewById(R.id.lblClasses);
        lblBio = rootView.findViewById(R.id.txtBio);
        imgUser = rootView.findViewById(R.id.imgUserPhoto);
        updateData();
        return rootView;
    }


    /**
     * Handles the top menu item selection.
     *
     * @param item the item selected
     * @return true or false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                listener.editUser(um.getThisUser());
                return true;
            case R.id.action_settings:
                //Intent intent = new Intent(this, PreferencesActivity.class);
                //startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfileFragment.ProfileListener) {
            this.listener = (ProfileFragment.ProfileListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
    }

    public void updateData() {
        //set UI components
        thisUser = um.getThisUser();
        lblName.setText(thisUser.toString());
        if(lblName.getText().toString().equals(" ")) {
            listener.editUser(um.getThisUser());
        } else {
            lblMajor.setText(thisUser.getMajor());
            lblLanguage.setText(thisUser.getLanguage());
            lblClasses.setText(thisUser.printClasses());
            lblBio.setText(thisUser.getBio());

            String photoStr = thisUser.getPhoto();
            if (photoStr != null) {
                byte[] photo = Base64.decode(photoStr, Base64.DEFAULT);
                imgUser.setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
                imgUser.setScaleType(ImageView.ScaleType.FIT_CENTER);
            } else {
                imgUser.setImageBitmap(null);
            }
        }
    }

}
