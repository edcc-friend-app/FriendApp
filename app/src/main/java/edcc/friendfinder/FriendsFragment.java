package edcc.friendfinder;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


/**
 * Fragment class for friends list screen.
 *
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
public class FriendsFragment extends Fragment {

    //fields
    private ListView lstFriends;
    private EditText txtFilter;
    private ArrayAdapter<User> lstAdapter;
    private List<User> friendList = new ArrayList<>();
    private boolean isFiltered;
    private FriendListener listener;
    private UserManager um;
    private View rootView;


    /**
     * Required empty public constructor
     */
    public FriendsFragment() {

    }

    /**
     * Creates the fragment.
     *
     * @param inflater           the layout inflater
     * @param container          the container holding the fragment
     * @param savedInstanceState the saved state
     * @return the root view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        um = UserManager.getUserManager(getContext(), FirebaseAuth.getInstance().getUid());
        //Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        lstFriends = rootView.findViewById(R.id.lstFriends);
        txtFilter = rootView.findViewById(R.id.txtFilter);
        //get saved state
        if (savedInstanceState != null) {
            isFiltered = savedInstanceState.getBoolean("isFiltered");
        }
        //set up filter button
        ImageButton btnFilter = rootView.findViewById(R.id.ibtnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleFilterClick();
            }
        });
        //updateData();
        return rootView;
    }

    /**
     * Registers the controlling activity as a listener when the fragment is attached to it.
     *
     * @param context the controlling activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FriendListener) {
            this.listener = (FriendListener) context;
        }
    }

    /**
     * Saves the state of the activity.
     *
     * @param outState the class state
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFiltered", isFiltered);
    }

    /**
     * On resume, set up the list adapter and filter if was filtered.
     */
    @Override
    public void onResume() {
        super.onResume();
        if (lstAdapter != null) {
            lstAdapter.notifyDataSetChanged();
        }
        if (isFiltered) {
            filterList();
        }
    }

    /**
     * Updates the friends list. This method must be called by the controlling activity
     * whenever this fragment is visible and user data is altered outside of this fragment.
     */
    public void updateData() {
        friendList = um.getFriendList();
        if (isFiltered) {
            filterList();
        } else {
            //set up list view adapter
            lstAdapter = new ArrayAdapter<>(rootView.getContext(),
                    android.R.layout.simple_list_item_1, friendList);
            lstFriends.setAdapter(lstAdapter);
            //create list view click event
            AdapterView.OnItemClickListener itemClickListener =
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            User thisFriend = (User) lstFriends.getItemAtPosition(position);
                            listener.viewFriendRequested(thisFriend);
                            clearFilter();
                            isFiltered = false;
                        }
                    };
            lstFriends.setOnItemClickListener(itemClickListener);
        }
        //hide keyboard
        if (getActivity() != null) {
            InputMethodManager inputManager = (InputMethodManager)
                    rootView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(
                        (null == getActivity().getCurrentFocus()) ? null :
                                getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * Handles the filter click
     */
    private void handleFilterClick() {
        //hide keyboard
        if (getActivity() != null) {
            InputMethodManager inputManager = (InputMethodManager)
                    rootView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(
                        (null == getActivity().getCurrentFocus()) ? null :
                                getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        //handle filter request
        if (txtFilter.getText().toString().isEmpty()) {
            clearFilter();
            isFiltered = false;
        } else {
            isFiltered = true;
            filterList();
        }
    }

    /**
     * Filters the list based on user selection.
     */
    private void filterList() {
        if (um == null) {
            return;
        }
        String filter = txtFilter.getText().toString().toLowerCase();
        //filter list
        List<User> filteredList = new ArrayList<>();
        for (User f : friendList) {
            if ((f.getFirstName().toLowerCase().contains(filter))
                    || (f.getMajor().toLowerCase().contains(filter))
                    || (f.getLanguage().toLowerCase().contains(filter))) {
                filteredList.add(f);
            }
        }
        lstAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_list_item_1, filteredList);
        lstFriends.setAdapter(lstAdapter);
    }

    /**
     * Clears the list filter.
     */
    private void clearFilter() {
        lstAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_list_item_1, um.getFriendList());
        lstFriends.setAdapter(lstAdapter);
        txtFilter.setText("");
    }

    /**
     * Interface for an activity to register as a FriendsFragment listener.
     */
    interface FriendListener {
        void viewFriendRequested(User friend);

    }

}
