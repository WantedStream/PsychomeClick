package com.example.psychomeclick;

import static com.example.psychomeclick.model.FirebaseManager.adminList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.psychomeclick.fragments.BookFragment;
import com.example.psychomeclick.fragments.SimulationsFragment;
import com.example.psychomeclick.fragments.StopperFragment;
import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.fragments.GeneralFragment;
import com.google.gson.Gson;
import java.util.LinkedHashMap;

/**
 * The type User activity.
 */
public class UserActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        createStuff();

        findViewById(R.id.signoutbutton).setOnClickListener((t)->{
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        FirebaseManager.userData=null;
                        FirebaseManager.firebaseAuth.signOut();
                        deleteUserFromShared(this.getSharedPreferences(FirebaseManager.PrefLocaltion,MODE_PRIVATE));
                        Intent intent = new Intent(this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to sign out?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        });

        findViewById(R.id.aibtn).setOnClickListener((t)->{
            Intent intent = new Intent(this, ChatBotActivity.class);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.general).setOnClickListener((t)->{
            changeColorOfButton((ImageButton) t);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, new GeneralFragment());
            transaction.commit();
        });
        findViewById(R.id.tests).setOnClickListener((t)->{
            changeColorOfButton((ImageButton) t);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, new SimulationsFragment());
            transaction.commit();
        });
        findViewById(R.id.bookbtn).setOnClickListener((t)->{
            changeColorOfButton((ImageButton) t);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, new BookFragment());
            transaction.commit();
        });
        findViewById(R.id.clockBtn).setOnClickListener((t)->{
            changeColorOfButton((ImageButton) t);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, new StopperFragment());
            transaction.commit();
        });
    }

    @Override
   public void onStart() {

        super.onStart();
    }

    /**
     * Create stuff.
     */
    public void createStuff(){
        FirebaseManager.db.collection("Users").document(FirebaseManager.firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(userTask -> {
            String name=userTask.getResult().getString("username").toString();
            ((TextView) findViewById(R.id.WelcomeText)).setText("welcome " +name);
        });

        if(!adminList.contains(FirebaseManager.userData.getName())){
            findViewById(R.id.goToAdminPage).setVisibility(View.GONE);
        }
        findViewById(R.id.goToAdminPage).setOnClickListener((v)->{  Intent intent = new Intent(this,AdminActivity.class);
            startActivity(intent);
            finish();});

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, new GeneralFragment());
        transaction.commit();

        changeColorOfButton(findViewById(R.id.general));
    }


    /**
     * Delete user from shared.
     *
     * @param sp the sp
     */
    public static void deleteUserFromShared(SharedPreferences sp){
        SharedPreferences.Editor prefsEditor = sp.edit();
        Gson gson = new Gson();
        LinkedHashMap<String,String> map= new LinkedHashMap<>();
        map.put("email","");
        map.put("password","");
        String json = gson.toJson(map);
        prefsEditor.putString("currentUser", json);
        prefsEditor.commit();
    }


    /**
     * Change color of button.
     *
     * @param b the b
     */
    public void changeColorOfButton(ImageButton b){
       this.findViewById(R.id.circle_layout).setBackground(null);
        this.findViewById(R.id.vi_layout).setBackground(null);
        this.findViewById(R.id.book_layout).setBackground(null);
        this.findViewById(R.id.clock_layout).setBackground(null);

        LinearLayout parent = (LinearLayout) b.getParent();
        parent.setBackgroundColor(Color.MAGENTA);
    }
}