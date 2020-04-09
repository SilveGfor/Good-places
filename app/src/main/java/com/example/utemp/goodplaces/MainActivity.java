package com.example.utemp.goodplaces;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {
    private  static final String Tag = "MainActivity";

    private  static final  int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(isServicesOK())
        {
            init();
        }
    }
    private void init()
    {
        Authorization fragment = new Authorization();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity,fragment).commit();

    }

    public  boolean isServicesOK()
    {
        Log.d(Tag, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS)
        {
            //всё хорошо
            Log.d(Tag, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            //ошибка, но не всё потеряно
            Log.d(Tag, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else
        {
            Toast.makeText(this, "Мы не можем подключиться к карте", Toast.LENGTH_SHORT).show();

        }
        return false;
    }
}
