package algonquin.cst2335.maye0097;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    public String filename = "Picture.png";
    public Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    public ActivityResultLauncher <Intent> cameraResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Bitmap thumbnail = data.getParcelableExtra("data");
                        FileOutputStream fOut = null;
                        ImageView image = findViewById(R.id.imageView);
                        image.setImageBitmap(thumbnail);
                        try {
                            fOut = openFileOutput(filename, Context.MODE_PRIVATE);
                            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                            fOut.flush();
                            fOut.close();
                        }
                        catch (FileNotFoundException e) { Log.w(TAG, "catch FileNotFoundException"); e.printStackTrace(); }
                        catch (IOException e) { Log.w(TAG, "catch IOException");e.printStackTrace(); }

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent fromPrevious = getIntent();
        String email = fromPrevious.getStringExtra("EmailName");
        int numb = 2;
        int age = fromPrevious.getIntExtra("Age", numb);
        String name = fromPrevious.getStringExtra("Name");
        String pCode = fromPrevious.getStringExtra("PostalCode");

        TextView phone = findViewById(R.id.editTextPhone);

        SharedPreferences data = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String phoneIn = data.getString("Phone Number", phone.getText().toString());
        phone.setText(phoneIn);

        TextView topText = findViewById(R.id.textView3);
        topText.setText("Welcome Back " + email);
        Button callB = findViewById(R.id.button2);
        callB.setOnClickListener(clk->{
            Intent call = new Intent(Intent.ACTION_DIAL);
            String phoneNumber = phone.getText().toString();
            call.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(call);
        });

        Button picture = findViewById(R.id.button3);
        ImageView profileImage = findViewById(R.id.imageView);

        picture.setOnClickListener(click->{
            cameraResult.launch(cameraIntent);
        });

        File file = new File(getFilesDir(), filename);
        if(file.exists()){
            Bitmap theImage = BitmapFactory.decodeFile(file.getPath());
            profileImage.setImageBitmap(theImage);
        }

    }

    protected void onPause(){
        TextView phone = findViewById(R.id.editTextPhone);
        SharedPreferences data = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putString("Phone Number", phone.getText().toString());
        editor.apply();
        super.onPause();
    }
}