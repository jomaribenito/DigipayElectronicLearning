package ph.digipay.digipayelectroniclearning.common.utils;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public enum FragmentUtils {
    INSTANCE;

    public static String createTag(AppCompatActivity activity, Fragment fragment) {
        return activity.getClass().getName() + ":" + fragment.getClass().getName();
    }

    public static void addFragment(AppCompatActivity activity, int resId, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
                .add(resId, fragment, createTag(activity, fragment))
                .commit();
    }

    public static void replaceFragment(AppCompatActivity activity, int resId, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(resId, fragment, createTag(activity, fragment))
                .commit();
    }

    public static  void replaceFragmentAddToBackStack(AppCompatActivity activity, int resId, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(resId, fragment, createTag(activity, fragment))
                .addToBackStack(createTag(activity, fragment))
                .commit();
    }

    public static void removeFragment(AppCompatActivity activity, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commit();
    }
}
