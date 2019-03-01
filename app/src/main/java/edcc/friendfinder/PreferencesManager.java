package edcc.friendfinder;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages the user preferences for the app.
 */
public class PreferencesManager {
    static final String CURRENT_FRAGMENT = "currentFragment";
    //fields
    private static PreferencesManager pm;
    //private boolean listBreed;
    private boolean sortAZ;
    private boolean warnBeforeDeletingFriend;
    //    private boolean warnBeforeDeletingVet;
//    private boolean warnBeforeDeletingClient;
    private String currentFragment;
    private final SharedPreferences PREFS;

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
     * private constructor
     */
    private PreferencesManager(Context ctx) {
        PREFS = ctx.getSharedPreferences("edcc.friendfinder", Context.MODE_PRIVATE);
//        listBreed = PREFS.getBoolean("listType", true);
//        User.listType = listBreed;
        sortAZ = PREFS.getBoolean("sortAZ", true);
        warnBeforeDeletingFriend = PREFS.getBoolean("warnBeforeDeletingFriend", true);
//        warnBeforeDeletingVet = PREFS.getBoolean("warnBeforeDeletingVet", true);
//        warnBeforeDeletingClient = PREFS.getBoolean("warnBeforeDeletingClient", true);
        currentFragment = PREFS.getString(CURRENT_FRAGMENT, "profile");
    }

//    /**
//     * Provides access to the preference to list the breed with the pet name.
//     *
//     * @return true if breed should be listed, false if not
//     */
//    boolean isListBreed() {
//        return listBreed;
//    }

//    /**
//     * Allows the preference to list the breed with the pet name to be changed.
//     *
//     * @param listBreed true if the breed should be listed, false if not
//     */
//    void setListBreed(boolean listBreed) {
//        this.listBreed = listBreed;
//        PREFS.edit().putBoolean("listType", listBreed).apply();
//        User.listType = listBreed;
//    }

    /**
     * Provides access to the preference to sort friends A to Z or Z to A.
     *
     * @return true if friends should be sorted A to Z, false if Z to A
     */
    boolean isSortAZ() {
        return sortAZ;
    }

    /**
     * Allows the preference to sort friends A to Z or Z to A to be changed.
     *
     * @param sortAZ true if friends should be sorted A to Z, false if Z to A
     */
    void setSortAZ(boolean sortAZ) {
        this.sortAZ = sortAZ;
        PREFS.edit().putBoolean("sortAZ", sortAZ).apply();
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
     * Allows the preference to warn before deleting a friend to be changed.
     *
     * @param warnBeforeDeletingFriend true if a a warning should be given, false if not
     */
    void setWarnBeforeDeletingFriend(boolean warnBeforeDeletingFriend) {
        this.warnBeforeDeletingFriend = warnBeforeDeletingFriend;
        PREFS.edit().putBoolean("warnBeforeDeletingPet", warnBeforeDeletingFriend).apply();
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