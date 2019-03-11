package edcc.friendfinder;

import android.support.v4.app.Fragment;

/**
 * Utility class to hold Intent keywords.
 *
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
class Extras {

    static final String FRIEND_ID = "friendId";
    static final String CURRENT_FRAGMENT = "currentFragment";
    static final String MAJOR_ID = "majorId";
    static final String LANGUAGE_ID = "languageId";
    static final String CLASS1_ID = "class1Id";
    static final String CLASS2_ID = "class2Id";
    static final String CLASS3_ID = "class3Id";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_FRIEND = 2;
    static final int REQUEST_POTENTIAL_FRIEND = 3;
    static final int REQUEST_AUTH = 4;
    static final int REQUEST_MAJOR = 5;
    static final int REQUEST_LANGUAGE = 6;
    static final int REQUEST_CLASS1 = 7;
    static final int REQUEST_CLASS2 = 8;
    static final int REQUEST_CLASS3 = 9;
    static final String ITEM_ID = "itemId";
    static final String USER_ID = "userId";
    static final String PROFILE_ID = "userId";
    private Fragment fragment;
    private UserManager um;

}
