package edcc.friendfinder;

import android.support.v4.app.Fragment;

/**
 * Utility class to hold Intent keywords.
 *
 * @author Jonathan Young
 * @author Anthony Luong
 * @author Estefano Felipe
 * @version 1.0
 */
class Extras {

    static final String FRIEND_ID = "friendId";
    static final String CURRENT_FRAGMENT = "currentFragment";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_FRIEND = 2;
    static final int REQUEST_POTENTIAL_FRIEND = 3;
    static final int REQUEST_AUTH = 4;

    private Fragment fragment;
    private UserManager um;
    static final String ITEM_ID = "itemId";
    static final String USER_ID = "userId";

}
