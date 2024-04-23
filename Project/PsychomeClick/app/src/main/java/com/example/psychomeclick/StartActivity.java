package com.example.psychomeclick;

import static com.example.psychomeclick.model.FirebaseManager.PrefLocaltion;
import static com.example.psychomeclick.model.FirebaseManager.firebaseAuth;
import static com.example.psychomeclick.model.FirebaseManager.userData;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.psychomeclick.helpers.NotificationService;
import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.model.UserData;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;


public class StartActivity<T> extends Activity {
    boolean pullQuestionFinished = false;
    boolean loginFinished = false;
    Intent nextIntent;
    Thread pullQuestionToMapThread = new Thread(() -> {
        FirebaseManager.db.collection("Questions").get().addOnCompleteListener((t)->{
            for (DocumentSnapshot doc: t.getResult().getDocuments()) {
                FirebaseManager.QuestionMap.put(doc.getId(),(Integer.parseInt(doc.get("correctAnswer").toString())));
                pullQuestionFinished=true;
                checkBothThreadsFinished();
            }
        });
    });

        Thread loginThread = new Thread(() -> {
            Map<String,String> emailAndPass = getUserFromShared(getSharedPreferences(PrefLocaltion,MODE_PRIVATE));
            if(emailAndPass==null){
                //never logged in or created an account
                LogIn.saveShareRef("","",getSharedPreferences(PrefLocaltion,MODE_PRIVATE));
            }
            emailAndPass = getUserFromShared(getSharedPreferences(PrefLocaltion,MODE_PRIVATE));
            String email=emailAndPass.get("email"),password=emailAndPass.get("password");
            if(email!=null&&password!=null&&!email.isEmpty()&&!password.isEmpty()){
                FirebaseManager.firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener((authData)->{
                    FirebaseManager.db.collection("Users").document(authData.getUser().getUid()).get().addOnSuccessListener(userDataSnapshot->{

                        userData=new UserData(userDataSnapshot.getString("username"),userDataSnapshot.getString("email"),userDataSnapshot.getString("phone"),userDataSnapshot.getString("userprogress"));
                        loginFinished=true;
                        nextIntent = new Intent(this, UserActivity.class);
                        checkBothThreadsFinished();


                    }).addOnFailureListener((failgetusedataTask)->{
                        firebaseAuth.signOut();
                        Toast.makeText(this,"user data doesnt exist!",
                                Toast.LENGTH_SHORT).show();
                         nextIntent = new Intent(this, MainActivity.class);
                        loginFinished=true;
                        checkBothThreadsFinished();


                    });
                }).addOnFailureListener((failsingintask)->{
                    loginFinished=true;
                    nextIntent = new Intent(this, MainActivity.class);
                    Toast.makeText(this,"user password/email were updated",Toast.LENGTH_SHORT).show();
                    checkBothThreadsFinished();
                });

            }
            else{
                loginFinished=true;
                nextIntent = new Intent(this, MainActivity.class);
                checkBothThreadsFinished();

            }
        });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // SplashScreen splashScreen=setSp
       // setup
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);


        pullQuestionToMapThread.start();
        loginThread.start();
    }
    private void checkBothThreadsFinished() {
        if (pullQuestionFinished && loginFinished) {
            this.startService(new Intent(this, NotificationService.class));
            startActivity(nextIntent);
            finish();
        }
    }
    public static Map<String,String> getUserFromShared(SharedPreferences sp){
        Gson gson = new Gson();
        String json = sp.getString("currentUser", null);
        Map<String,String> obj = gson.fromJson(json, LinkedHashMap.class);
        return obj;
    }
}