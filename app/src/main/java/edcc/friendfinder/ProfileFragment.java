package edcc.friendfinder;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
    private ProfileListener listener;
    private UserManager um;
    //private
    private int userId;
    static final int REQUEST_IMAGE_CAPTURE = 1;


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
        um = UserManager.getUserManager();
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //lblInfo = rootView.findViewById(R.id.lblInfo);
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
            case R.id.action_sign_out:
                System.out.println("hey");
                //signOut();
                return true;
            case R.id.action_edit:
                listener.editUser(um.getUsers().get(0));
                return true;
            case R.id.action_settings:
                //Intent intent = new Intent(this, PreferencesActivity.class);
                //startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
        @Override
        public void onAttach (Context context){
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
}
//    public class PetListFragment extends Fragment {
//
//        //fields
//        private ListView lstPets;
//        private EditText txtFilter;
//        private ArrayAdapter<Pet> lstAdapter;
//        private List<Pet> petList = new ArrayList<>();
//        private boolean isFiltered;
//        private PetListListener listener;
//        private DataManager dm;
//        private View rootView;
//
//        /**
//         * Interface for an activity to register as a PetListFragment listener.
//         */
//        interface PetListListener {
//            void viewPetRequested(Pet pet);
//        }
//
//        /**
//         * Required default constructor.
//         */
//        public PetListFragment() {
//        }
//
//        /**
//         * Creates the fragment.
//         *
//         * @param inflater           the layout inflater
//         * @param container          the container holding the fragment
//         * @param savedInstanceState the saved state
//         * @return the root view of the fragment
//         */
//        @Override
//        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            dm = DataManager.getDataManager(getContext(), FirebaseAuth.getInstance().getUid());
//            // Inflate the layout for this fragment
//            rootView = inflater.inflate(R.layout.fragment_pet_list, container, false);
//            lstPets = rootView.findViewById(R.id.lstPets);
//            txtFilter = rootView.findViewById(R.id.txtFilter);
//            //get saved state
//            if (savedInstanceState != null) {
//                isFiltered = savedInstanceState.getBoolean("isFiltered");
//            }
//            //set up filter button
//            ImageButton btnFilter = rootView.findViewById(R.id.ibtnFilter);
//            btnFilter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    handleFilterClick();
//                }
//            });
//            return rootView;
//        }
//
//        /**
//         * Registers the controlling activity as a listener when the fragment is attached to it.
//         *
//         * @param context the controlling activity
//         */
//        @Override
//        public void onAttach(Context context) {
//            super.onAttach(context);
//            if (context instanceof PetListListener) {
//                this.listener = (PetListListener) context;
//            }
//        }
//
//        /**
//         * Saves the state of the activity.
//         *
//         * @param outState the class state
//         */
//        @Override
//        public void onSaveInstanceState(@NonNull Bundle outState) {
//            super.onSaveInstanceState(outState);
//            outState.putBoolean("isFiltered", isFiltered);
//        }
//
//        /**
//         * On resume, set up the list adapter and filter if was filtered.
//         */
//        @Override
//        public void onResume() {
//            super.onResume();
//            if (lstAdapter != null) {
//                lstAdapter.notifyDataSetChanged();
//            }
//            if (isFiltered) {
//                filterList();
//            }
//        }
//
//        /**
//         * Updates the pet list. This method must be called by the controlling activity
//         * whenever this fragment is visible and pet data is altered outside of this fragment.
//         */
//        public void updateData() {
//            petList = dm.getPetList();
//            if (isFiltered) {
//                filterList();
//            } else {
//                //set up list view adapter
//                lstAdapter = new ArrayAdapter<>(rootView.getContext(),
//                        android.R.layout.simple_list_item_1, petList);
//                lstPets.setAdapter(lstAdapter);
//                //create list view click event
//                AdapterView.OnItemClickListener itemClickListener =
//                        new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Pet thisPet = (Pet) lstPets.getItemAtPosition(position);
//                                listener.viewPetRequested(thisPet);
//                                clearFilter();
//                                isFiltered = false;
//                            }
//                        };
//                lstPets.setOnItemClickListener(itemClickListener);
//            }
//            //hide keyboard
//            if (getActivity() != null) {
//                InputMethodManager inputManager = (InputMethodManager)
//                        rootView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (inputManager != null) {
//                    inputManager.hideSoftInputFromWindow(
//                            (null == getActivity().getCurrentFocus()) ? null :
//                                    getActivity().getCurrentFocus().getWindowToken(),
//                            InputMethodManager.HIDE_NOT_ALWAYS);
//                }
//            }
//        }
//
//        /**
//         * Handles the filter click
//         */
//        private void handleFilterClick() {
//            //hide keyboard
//            if (getActivity() != null) {
//                InputMethodManager inputManager = (InputMethodManager)
//                        rootView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (inputManager != null) {
//                    inputManager.hideSoftInputFromWindow(
//                            (null == getActivity().getCurrentFocus()) ? null :
//                                    getActivity().getCurrentFocus().getWindowToken(),
//                            InputMethodManager.HIDE_NOT_ALWAYS);
//                }
//            }
//            //handle filter request
//            if (txtFilter.getText().toString().isEmpty()) {
//                clearFilter();
//                isFiltered = false;
//            } else {
//                isFiltered = true;
//                filterList();
//            }
//        }
//
//        /**
//         * Filters the list based on user selection.
//         */
//        private void filterList() {
//            if (dm == null) {
//                return;
//            }
//            String filter = txtFilter.getText().toString().toLowerCase();
//            //filter list
//            List<Pet> filteredList = new ArrayList<>();
//            for (Pet p : petList) {
//                if ((p.getName().toLowerCase().contains(filter))
//                        || (p.getClientId() > -1 && dm.getClient(p.getClientId()).toString().toLowerCase().contains(filter))
//                        || (p.getType().toLowerCase().contains(filter))
//                        || (p.getVetId() > -1 && dm.getVet(p.getVetId()).toString().toLowerCase().contains(filter))) {
//                    filteredList.add(p);
//                }
//            }
//            lstAdapter = new ArrayAdapter<>(rootView.getContext(),
//                    android.R.layout.simple_list_item_1, filteredList);
//            lstPets.setAdapter(lstAdapter);
//        }
//
//        /**
//         * Clears the list filter.
//         */
//        private void clearFilter() {
//            lstAdapter = new ArrayAdapter<>(rootView.getContext(),
//                    android.R.layout.simple_list_item_1, dm.getPetList());
//            lstPets.setAdapter(lstAdapter);
//            txtFilter.setText("");
//        }
//
//    }

