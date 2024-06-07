package com.example.psychomeclick.helpers;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.*;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

/**
 * The type My app glide module.
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        // Register FirebaseImageLoader to handle StorageReference
        registry.append(StorageReference.class, InputStream.class, new FirebaseImageLoader.Factory());
    }

    //Glide is an image loading library for Android that simplifies the process of loading, caching, and displaying images in your app.
    /*
     Context: This class provides information about the application environment.
     Glide: This is the main class from Glide library.
     Registry: This class allows you to register custom components with Glide.
     FirebaseImageLoader: This class from Firebase UI library helps load images from Firebase Storage.
     StorageReference: This class represents a reference to a file or directory in Firebase Storage.
        InputStream: This class represents a stream of bytes coming from a source.
        @GlideModule Annotation: This annotation indicates that this class is a Glide module. Glide modules allow you to configure Glide for your application.

    registerComponents Method: This method is called by Glide when the app is first launched. It allows you to register custom components with Glide.

        In this case, the code uses registry.append method to register FirebaseImageLoader.Factory with Glide. This allows Glide to handle loading images from Firebase Storage references (StorageReference). The FirebaseImageLoader.Factory creates instances of FirebaseImageLoader which can then be used to load images from Firebase Storage.
     */
}