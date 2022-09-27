package algonquin.cst2335.maye0097.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2335.maye0097.data.MainViewModel;
import algonquin.cst2335.maye0097.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MainViewModel model;
    private ActivityMainBinding variableBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        variableBinding.myText.setText(model.editString.toString());
        variableBinding.myButton.setOnClickListener(click ->
                {
                    model.editString.postValue(variableBinding.myEdittext.getText().toString());
                    variableBinding.myText.setText("Your edit text has: " + model.editString);
                });

        model.editString.observe(this, s -> {
            variableBinding.myText.setText("Your edit text has " + s);
        });





        //TextView mytext = variableBinding.myText;
        //Button btn = variableBinding.myButton;
        //EditText myedit = variableBinding.myEdittext;
        //String editString = myedit.getText().toString();

    }
}