package edcc.friendfinder;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

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
    private FriendsFragment.FriendListener listener;
    private List<User> userList = new ArrayList<>();
    private boolean isFiltered;

    public FindFriendsFragment() {
        // Required empty public constructor
    }


    interface FriendListener {
        void viewFriendRequested(User friend);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        um = UserManager.getUserManager(getContext(), FirebaseAuth.getInstance().getUid());
        rootView = inflater.inflate(R.layout.fragment_find_friends, container, false);
        lstFriends = rootView.findViewById(R.id.lstFriends);
        listAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_selectable_list_item,
                um.getUsers());
        lstFriends.setAdapter(listAdapter);
//        lstTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(TypeListActivity.this, ItemListActivity.class);
//                intent.putExtra(TYPE_ID, position);
//                startActivity(intent);
//            }
//        });
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
        if (context instanceof FriendsFragment.FriendListener) {
            this.listener = (FriendsFragment.FriendListener) context;
        }
    }

//    /**
//     * Updates the client list. This method must be called by the controlling activity
//     * whenever this fragment is visible and client data is altered outside of this fragment.
//     */
//    public void updateData() {
//        userList = um.getUsers();
//        if (isFiltered) {
//            filterList();
//        } else {
//            //set up client list
//            clientList = dm.getClientList();
//            clientList = dm.getClientList();
//            //set up list view adapter
//            lstAdapter = new ArrayAdapter<>(rootView.getContext(),
//                    android.R.layout.simple_list_item_1, clientList);
//            lstClients.setAdapter(lstAdapter);//set up list view adapter
//            //create list view click event
//            AdapterView.OnItemClickListener itemClickListener =
//                    new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            Client thisClient = (Client) lstClients.getItemAtPosition(position);
//                            listener.viewClientRequested(thisClient);
//                            clearFilter();
//                            isFiltered = false;
//                        }
//                    };
//            lstClients.setOnItemClickListener(itemClickListener);
//        }
//    }

//    /**
//     * Filters the list based on user selection.
//     */
//    private void filterList() {
//        String filter = txtFilter.getText().toString().toLowerCase();
//        //filter list
//        List<Client> filteredList = new ArrayList<>();
//        for (Client c : clientList) {
//            if ((c.getLastName().toLowerCase().contains(filter))
//                    || (c.getFirstName().toLowerCase().contains(filter))) {
//                filteredList.add(c);
//            }
//        }
//        lstAdapter = new ArrayAdapter<>(rootView.getContext(),
//                android.R.layout.simple_list_item_1, filteredList);
//        lstClients.setAdapter(lstAdapter);
//    }
//
//    /**
//     * Clears the list filter.
//     */
//    private void clearFilter() {
//        lstAdapter = new ArrayAdapter<>(rootView.getContext(),
//                android.R.layout.simple_list_item_1, clientList);
//        lstClients.setAdapter(lstAdapter);
//        txtFilter.setText("");
//    }

}