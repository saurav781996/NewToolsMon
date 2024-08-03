package com.example.newtoolsmon.dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;
import com.example.newtoolsmon.camera.Mirror;
import com.example.newtoolsmon.other.PrivacyPolicy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

import androidmads.library.qrgenearator.BuildConfig;


public class MainActivity extends CommonActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    NavigationView drawer_navigation_view;

    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 11;
    InstallStateUpdatedListener installStateUpdatedListener;


    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.person);


        // AUTO UPDATE

        installStateUpdatedListener = new
                InstallStateUpdatedListener() {
                    @Override
                    public void onStateUpdate(InstallState state) {
                        if (state.installStatus() == InstallStatus.DOWNLOADED) {
//CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                            popupSnackbarForCompleteUpdate();
                        } else if (state.installStatus() == InstallStatus.INSTALLED) {
                            if (mAppUpdateManager != null) {
                                mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                            }

                        } else {
                            Log.i("TAG", "InstallStateUpdatedListener: state: " + state.installStatus());
                        }
                    }
                };


        // AUTO UPDATE end


        // Drawer Layout


        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
       actionBarDrawerToggle.syncState();

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        // to make the Navigation drawer icon always appear on the action bar
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Drawer Layout end


        moreOptions();


    }

    MainDashBoardFragment mainDashBoard = new MainDashBoardFragment();


    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.person) {
            //  Toast.makeText(MainActivity.this, "click " , Toast.LENGTH_SHORT).show();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, mainDashBoard)
                    .commit();
        }

        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();

        mAppUpdateManager = AppUpdateManagerFactory.create(this);

        mAppUpdateManager.registerListener(installStateUpdatedListener);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)) {

                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/, MainActivity.this, RC_APP_UPDATE);

                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate();
            } else {
                Log.e("TAG", "checkForAppUpdateAvailability: something else");
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.e("TAG", "onActivityResult: app download failed");
            }
        }
    }


    private void popupSnackbarForCompleteUpdate() {

        Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout_main), "New app is ready!", Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (mAppUpdateManager != null) {
                mAppUpdateManager.completeUpdate();
            }
        });


        snackbar.setActionTextColor(getResources().getColor(R.color.app_blue_main));
        snackbar.show();
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (mAppUpdateManager != null) {
            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void text(View view) {


        drawerLayout.open();
    }


    private void moreOptions() {


        ImageView moreOptions;

        moreOptions = (ImageView) findViewById(R.id.more_option);

        moreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, moreOptions);

// Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.navigation_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getTitle().toString().equals("About Us")) {
                            aboutUSAlertDialog();
                        }
                        if (menuItem.getTitle().toString().equals("Rate App")) {
                            MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                        }
                        if (menuItem.getTitle().toString().equals("Privacy Policy")) {
                            startActivity(new Intent(MainActivity.this, PrivacyPolicy.class));
                        }
                        if (menuItem.getTitle().toString().equals("Share App")) {
                            sendOther();
                        }
                        if (menuItem.getTitle().toString().equals("Contact")) {
                            contactUS();
                        }


                        return true;
                    }
                });
// Showing the popup menu
                popupMenu.show();
            }
        });


    }


    private void contactUS() {

        String model = Build.MODEL;
        String manufacturer = Build.MANUFACTURER;
        String version = Build.VERSION.RELEASE;
        int versionCode = BuildConfig.VERSION_CODE;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"saurav781996@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "ToolsMon Support");
        intent.putExtra(Intent.EXTRA_TEXT, "--Support Info--\n\nUser ID: " + "\nApp Version: " + versionCode + "\nPlan: " + "\nManufacturer: " + manufacturer + "\nModel: " + model + "\nOS: " + version);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }


    public void sendOther() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", "Hey there! its nice app i am Using https://play.google.com/store/apps/details?id=com.saurav.toolsmon");
        startActivity(Intent.createChooser(intent, "Share with"));
    }


    public void aboutUSAlertDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.about_us, null);
        dialogBuilder.setView(dialogView);
        Button buy_coins = dialogView.findViewById(R.id.buy_coins);

        final AlertDialog alertDialog = dialogBuilder.create();

        buy_coins.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                alertDialog.cancel();
            }
        });


        alertDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askChangeBrightness() {

        Context context = getApplicationContext();
        boolean settingsCanWrite = Settings.System.canWrite(context);

        if (!settingsCanWrite) {
            new AlertDialog.Builder(MainActivity.this).setTitle("Change brightness permission")
                    .setMessage("Change brightness permission required for better app experience")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                            startActivity(intent);
                        }
                    }).show();
        } else {
            setSharedPreferences("brightness_permission", "true", MainActivity.this);
        }
    }






}



