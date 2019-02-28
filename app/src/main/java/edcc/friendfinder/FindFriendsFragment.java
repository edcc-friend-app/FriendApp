package edcc.friendfinder;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFriendsFragment extends Fragment {

    private UserManager um;
    private ListView lstFriends;
    private View rootView;
    private ArrayAdapter<User> listAdapter;
    private List<User> potFriendList = new ArrayList<>();
    private FindFriendsFragment.FriendListener listener;

    public FindFriendsFragment() {
        // Required empty public constructor
    }


    interface FriendListener {
        void viewPotFriendRequested(User friend);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        um = UserManager.getUserManager();
        rootView = inflater.inflate(R.layout.fragment_find_friends, container, false);
        lstFriends = rootView.findViewById(R.id.lstFriends);
        //get saved state
        if(savedInstanceState != null) {

        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FindFriendsFragment.FriendListener) {
            this.listener = (FindFriendsFragment.FriendListener) context;
        }
    }

    /**
     * Updates the pet list. This method must be called by the controlling activity
     * whenever this fragment is visible and pet data is altered outside of this fragment.
     */
    public void updateData() {
        potFriendList = um.getPotentialFriends();
        listAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_selectable_list_item,
                um.getPotentialFriends());
        lstFriends.setAdapter(listAdapter);
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        User thisFriend = (User) lstFriends.getItemAtPosition(position);
                        listener.viewPotFriendRequested(thisFriend);
                        //clearFilter();
                        //isFiltered = false;
                    }
                };
        lstFriends.setOnItemClickListener(itemClickListener);
//        if (isFiltered) {
//            filterList();
//        } else {
//            //set up list view adapter
//            lstAdapter = new ArrayAdapter<>(rootView.getContext(),
//                    android.R.layout.simple_list_item_1, petList);
//            lstPets.setAdapter(lstAdapter);
//            //create list view click event
//            AdapterView.OnItemClickListener itemClickListener =
//                    new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            Pet thisPet = (Pet) lstPets.getItemAtPosition(position);
//                            listener.viewPetRequested(thisPet);
//                            clearFilter();
//                            isFiltered = false;
//                        }
//                    };
//            lstPets.setOnItemClickListener(itemClickListener);
//        }
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
}