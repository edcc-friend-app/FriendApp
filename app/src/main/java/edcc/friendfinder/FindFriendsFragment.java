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
 * Fragment class for find friends list screen.
 *
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
public class FindFriendsFragment extends Fragment {

    //fields
    private boolean isFiltered;
    private EditText txtFilter;
    private UserManager um;
    private ListView lstFriends;
    private View rootView;
    private ArrayAdapter<User> listAdapter;
    private List<User> potFriendList = new ArrayList<>();
    private FindFriendsFragment.FriendListener listener;

    /**
     * Required default constructor
     */
    public FindFriendsFragment() {
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        um = UserManager.getUserManager(getContext(), FirebaseAuth.getInstance().getUid());
        rootView = inflater.inflate(R.layout.fragment_find_friends, container, false);
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
        updateData();
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
        if (context instanceof FindFriendsFragment.FriendListener) {
            this.listener = (FindFriendsFragment.FriendListener) context;
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
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
        if (isFiltered) {
            filterList();
        }
    }

    /**
     * Updates the potential friend list. This method must be called by the controlling activity
     * whenever this fragment is visible and pet data is altered outside of this fragment.
     */
    private void updateData() {
        potFriendList = um.getPotentialFriends();
        if (isFiltered) {
            filterList();
        } else {
            //set up list view adapter
            listAdapter = new ArrayAdapter<>(rootView.getContext(),
                    android.R.layout.simple_list_item_1,
                    potFriendList);
            lstFriends.setAdapter(listAdapter);
            //create list view click event
            AdapterView.OnItemClickListener itemClickListener =
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            User thisFriend = (User) lstFriends.getItemAtPosition(position);
                            listener.viewPotFriendRequested(thisFriend);
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
        for (User u : potFriendList) {
            if ((u.getFirstName().toLowerCase().contains(filter))
                    || (u.getMajor().toLowerCase().contains(filter))
                    || (u.getLanguage().toLowerCase().contains(filter))) {
                filteredList.add(u);
            }
        }
        listAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_list_item_1, filteredList);
        lstFriends.setAdapter(listAdapter);
    }

    /**
     * Clears the list filter.
     */
    private void clearFilter() {
        listAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_list_item_1, potFriendList);
        lstFriends.setAdapter(listAdapter);
        txtFilter.setText("");
    }

    /**
     * Interface for an activity to register as a FindFriendsFragment listener.
     */
    interface FriendListener {
        void viewPotFriendRequested(User friend);
    }

}