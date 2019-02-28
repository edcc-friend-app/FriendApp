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


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFriendsFragment extends Fragment {

    private UserManager um;
    private ListView lstFriends;
    private View rootView;
    private ArrayAdapter<User> listAdapter;
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
                        listener.viewPotFriendRequested(thisFriend);
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
        if (context instanceof FindFriendsFragment.FriendListener) {
            this.listener = (FindFriendsFragment.FriendListener) context;
        }
    }

}