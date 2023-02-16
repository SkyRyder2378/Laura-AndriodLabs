package algonquin.cst2335.maye0097;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * @author Laura Mayer
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** This holds the text at the centre of the screen */
    private TextView textView = null;
    /** This hold the text input in the middle of the screen just below the TextView */
    private EditText passwordET = null;
    /** This hold the login button at the bottom of the screen */
    private Button loginBtn = null;
    /** This is a Sting TAG for Log messages */
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.passwordTextView);
        passwordET = findViewById(R.id.passwordField);
        loginBtn = findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(click -> {
            Log.w(TAG, "inside loginButton click listener");
            String password = passwordET.getText().toString();
            boolean checked = checkPasswordComplexity(password);

            Log.w(TAG, "before if");
            if(checked){
                Log.w(TAG, "inside if success");
                textView.setText("Your password meets the requirements");
            }
            else{
                Log.w(TAG, "inside if fail or else");
                textView.setText("You shall not pass!");
            }
        });

    }

    /** This function is used to check the complexity of a password before allowing it,
     * to be complex enough it needs an uppercase letter, a lowercase letter, a number,
     * and a special character.
     *
     * @param password The String object we are checking
     * @return Returns true if the password is complex enough.
     */
    boolean checkPasswordComplexity(String password){

        Log.w(TAG, "inside checkPasswordComplexity");
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for(int i = 0; i < password.length(); i++){
            char temp = password.charAt(i);
            if (Character.isUpperCase(temp)){
                foundUpperCase = true;
            }
            else if(Character.isLowerCase(temp)){
                foundLowerCase = true;
            }
            else if(Character.isDigit(temp)){
                foundNumber = true;
            }
            else if(isSpecialCharacter(temp)){
                foundSpecial = true;
            }
        }

        if(!foundUpperCase)
        {
            Toast.makeText(getApplicationContext(), "Missing an uppercase letter", Toast.LENGTH_SHORT);
            return false;

        }

        else if( ! foundLowerCase)
        {
            Toast.makeText(getApplicationContext(), "Missing a lowercase letter", Toast.LENGTH_SHORT);
            return false;

        }

        else if( ! foundNumber) {

            Toast.makeText(getApplicationContext(), "Missing a number", Toast.LENGTH_SHORT);
            return false;

        }

        else if(! foundSpecial) {
            Toast.makeText(getApplicationContext(), "Missing a special character", Toast.LENGTH_SHORT);
            return false;
        }





        return true;
    }

    /** This function check a character to see if it is a special characters.
     *
     * @param c The character variable we are checking
     * @return Returns true if c is a special character
     */
    boolean isSpecialCharacter(char c){
        Log.w(TAG, "In isSpecialCharacter");
        switch (c){
            case '!':
            case '@':
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '?':
                return true;
            default:
                return false;

        }
    }

}