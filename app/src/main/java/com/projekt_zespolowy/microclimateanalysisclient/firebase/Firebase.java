package com.projekt_zespolowy.microclimateanalysisclient.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

public class Firebase {
    private static final String TAG = Firebase.class.getName();
    private final static String TOPIC = "default";

    public static void subscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
                .addOnCompleteListener(task -> Log.i(TAG, "Subscription to topic succeeded"))
                .addOnFailureListener(e -> Log.w(TAG, "Subscription to topic failed"));
    }

    public static void unsubscribe() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC)
                .addOnCompleteListener(task -> Log.i(TAG, "Unsubscribed from topic"))
                .addOnFailureListener(e -> Log.w(TAG, "Failed to unsubscribe from topic"));
    }
}
