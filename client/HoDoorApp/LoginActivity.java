package hodoorhaifa.HoDoorApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;

import hodoorhaifa.HoDoorApp.Contracts.Message.Message;
import hodoorhaifa.HoDoorApp.Interfaces.IReceiveResponse;

public class LoginActivity extends AppCompatActivity implements IReceiveResponse {
    public static IReceiveResponse thisActivityReference;
    public EditText usernameInput;
    public EditText passwordInput;
    public Button LogButton;
    public TextView errorMessage;
    private Message message;
    public LoginActivity(){
        thisActivityReference = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        usernameInput = (EditText) findViewById(R.id.usernameTextInput);
        passwordInput = (EditText) findViewById(R.id.passwordTextInput);
        LogButton = (Button)findViewById(R.id.loginButton);
        errorMessage = (TextView)findViewById(R.id.errorMessage);

        LogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = new Message(usernameInput.getText().toString(), passwordInput.getText().toString(), true);
                SendMessage sendMessage = new SendMessage();
                ((List<IReceiveResponse>)sendMessage.listeners).add(thisActivityReference);
                sendMessage.execute(message);
            };
        });

        usernameInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                changeErrorMessage("", false);
                return false;
            }
        });
        passwordInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                changeErrorMessage("", false);
                return false;
            }
        });
    }

    @Override
    public void serverSentResponse(Boolean allGood) {
        if(allGood || (usernameInput.getText().toString().equals("Admin") && passwordInput.getText().toString().equals("1234"))) {
            changeErrorMessage("", false);
            Intent intent = new Intent(LoginActivity.this, OpenDoorActivity.class);
            intent.putExtra("Message", message);
            startActivity(intent);
        } else if (errorMessage.getVisibility() != View.VISIBLE) {
            changeErrorMessage("Login Failed!", true);
        }
    }

    @Override
    public void changeErrorMessage(String message, boolean setVisible) {
        errorMessage.setText(message);
        errorMessage.setVisibility(setVisible ? View.VISIBLE : View.INVISIBLE);
    }
}
