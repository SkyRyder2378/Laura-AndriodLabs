package algonquin.cst2335.maye0097;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent fromPrevious = getIntent();
        String email = fromPrevious.getStringExtra("EmailAddress");
        int numb = 2;
        int age = fromPrevious.getIntExtra("Age", numb);
        String name = fromPrevious.getStringExtra("Name");
        String pCode = fromPrevious.getStringExtra("PostalCode");

        TextView topText = findViewById(R.id.textView3);
        topText.setText("Welcome Back " + email);
        Button callB = findViewById(R.id.button2);
        callB.setOnClickListener(clk->{
            Intent call = new Intent(Intent.ACTION_DIAL);
            TextView phone = findViewById(R.id.editTextPhone);
            String phoneNumber = phone.getText().toString();
            call.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(call);
        });

        Button picture = findViewById(R.id.button3);
        ImageView profileImage = findViewById(R.id.imageView);

        picture.setOnClickListener(click->{
            Log.w(TAG, "In setOnClickListener for Capture Picture Button");
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Log.w(TAG, "Create first Intent");

            ActivityResultLauncher <Intent> cameraResult = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            Log.w(TAG, "before if in onActivityResult");
                            if (result.getResultCode() == Activity.RESULT_OK){
                                Log.w(TAG, "In onActivityResult in ActivityResultLauncher");
                                Intent data = result.getData();
                                Log.w(TAG, "after second Intent creation");
                                Bitmap thumbnail = data.getParcelableExtra("data");
                                Log.w(TAG, "after create Bitmap");
                                profileImage.setImageBitmap(thumbnail);
                                Log.w(TAG, "after setImageBitmap");
                            }
                        }
                    });

            Log.w(TAG, "after ActivityResultLauncher");
            cameraResult.launch(cameraIntent);
        });




    }
}