package edcc.friendfinder;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages the user preferences for the app.
 *
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
public class PreferencesManager {

    private static final String CURRENT_FRAGMENT = "currentFragment";
    //fields
    private static PreferencesManager pm;
    private final boolean sortAZ;
    private final boolean warnBeforeDeletingFriend;
    //    private boolean warnBeforeDeleting;
    private String currentFragment;

    /**
     * private constructor
     */
    private PreferencesManager(Context ctx) {
        SharedPreferences PREFS = ctx.getSharedPreferences("edcc.friendfinder", Context.MODE_PRIVATE);
        sortAZ = PREFS.getBoolean("sortAZ", true);
        warnBeforeDeletingFriend = PREFS.getBoolean("warnBeforeDeletingFriend", true);
        currentFragment = PREFS.getString(CURRENT_FRAGMENT, "profile");
    }

    /**
     * Singleton pattern preferences manager instance.
     *
     * @return the single instance
     */
    public static PreferencesManager getInstance(Context ctx) {
        if (pm == null) {
            pm = new PreferencesManager(ctx);
        }
        return pm;
    }

    /**
     * Provides access to the preference to sort friends A to Z or Z to A.
     *
     * @return true if friends should be sorted A to Z, false if Z to A
     */
    boolean isSortAZ() {
        return sortAZ;
    }

    /**
     * Provides access to the preference to warn before deleting a friend.
     *
     * @return true if a warning should be given, false if not
     */
    boolean isWarnBeforeDeletingFriend() {
        return warnBeforeDeletingFriend;
    }

    /**
     * Provides access to the current fragment showing in the NavActivity. This allows the app
     * to open with the last fragment displayed. It also allows return to the same fragment when
     * navigating away from NavActivity and then back.
     *
     * @return the current fragment setting
     */
    String getCurrentFragment() {
        return currentFragment;
    }

    /**
     * Allows the current fragment for NavActivity to be set. Not handled by PreferencesActivity.
     * Instead, it is handled by the NavActivity.
     *
     * @param currentFragment the current fragment to display
     */
    void setCurrentFragment(String currentFragment) {
        this.currentFragment = currentFragment;
    }
}