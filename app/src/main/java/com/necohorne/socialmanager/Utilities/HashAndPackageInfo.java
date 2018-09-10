package com.necohorne.socialmanager.Utilities;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.necohorne.socialmanager.Activities.SocialSelectionActivity.PACKAGE;

public class HashAndPackageInfo {

    private static final String TAG = HashAndPackageInfo.class.getSimpleName();

        private void show(Context context){
//    This method is used in debug to get package info and hashcode info to setup the developer dashboards in order to use API's.
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    PACKAGE,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                System.out.println("Package name: " + info.packageName + "hashcode: " + Base64.encodeToString(md.digest(), Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }

}
