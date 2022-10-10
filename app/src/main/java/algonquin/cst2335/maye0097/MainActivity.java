package algonquin.cst2335.maye0097;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG= "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w(TAG, "In onCreate() - Loading Widgets");
        Button loginButton = findViewById(R.id.loginButton);
        EditText emailT = findViewById(R.id.editTextEmail);
        loginButton.setOnClickListener(clk->{
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            String email = emailT.getText().toString();
            int age = 24;
            String name = "Laura Mayer";
            String postCode = "K2G 3L6";
            nextPage.putExtra("EmailAddress", email);
            nextPage.putExtra("Age", age);
            nextPage.putExtra("Name", name);
            nextPage.putExtra("PostalCode", postCode);
            startActivity(nextPage);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy() - Any memory used by application is freed");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "onStop() - The application is no longer visible");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause() - The application is no longer responding to user input");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume() - The application is now responding to user input");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "onStart() - The application is now visible on screen");
    }
}