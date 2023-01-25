package algonquin.cst2335.maye0097.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
import android.widget.Toast;

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

        model.editString.observe(this, s -> {
            String text = "Your edit text has " + s;
            variableBinding.myText.setText(text);
        });

        variableBinding.myButton.setOnClickListener(click ->
                {
                    model.editString.postValue(variableBinding.myEdittext.getText().toString());
                });


        variableBinding.checkBox.setOnCheckedChangeListener((btn, isChecked) -> {model.isSelected.postValue(isChecked);});
        variableBinding.radioButton.setOnCheckedChangeListener((btn, isChecked) -> {model.isSelected.postValue(isChecked);});
        variableBinding.switch1.setOnCheckedChangeListener((btn, isChecked) -> {model.isSelected.postValue(isChecked);});


        model.isSelected.observe(this, selected -> {
            variableBinding.checkBox.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);
            variableBinding.switch1.setChecked(selected);

            Context context = getApplicationContext();
            CharSequence text = "The value is now " + selected;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

        variableBinding.imageButton2.setOnClickListener(iClick -> {
            Context context = getApplicationContext();
            CharSequence text = "The width = " + variableBinding.imageButton2.getWidth() + " and height = " + variableBinding.imageButton2.getHeight();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });
    }
}