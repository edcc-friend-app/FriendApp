package edcc.friendfinder;


import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    private UserManager um;
    private ListView lstFriends;
    private View rootView;
    private ArrayAdapter<User> listAdapter;
    private FriendListener listener;

    public FriendsFragment() {
        // Required empty public constructor
    }


    interface FriendListener {
        void viewFriendRequested(User friend);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        um = UserManager.getUserManager();
        rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        lstFriends = rootView.findViewById(R.id.lstFriends);
        listAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_selectable_list_item,
                um.getUsers());
        lstFriends.setAdapter(listAdapter);
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        User thisFriend = (User) lstFriends.getItemAtPosition(position);
                        listener.viewFriendRequested(thisFriend);
                        //clearFilter();
                        //isFiltered = false;
                    }
                };
        lstFriends.setOnItemClickListener(itemClickListener);
        //return inflater.inflate(R.layout.fragment_friends, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FriendListener) {
            this.listener = (FriendListener) context;
        }
    }

}