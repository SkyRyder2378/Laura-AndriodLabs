package algonquin.cst2335.maye0097;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2335.maye0097.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        TextView mytext = variableBinding.myText;
        Button btn = variableBinding.myButton;
        EditText myedit = variableBinding.myEdittext;

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String editString = myedit.getText().toString();
                mytext.setText("Your edit text has: " + editString);
            }
        });
    }
}