package hodoorhaifa.HoDoorApp;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import hodoorhaifa.HoDoorApp.Contracts.Message.Message;
import hodoorhaifa.HoDoorApp.Interfaces.IReceiveResponse;

public class OpenDoorActivity extends AppCompatActivity implements IReceiveResponse {
    public static IReceiveResponse thisActivityReference;
    private Button openDoorButton;
    private TextView errorMessage;
    private SendMessage sendMessage;
    private static boolean lockIsAvailable = true;

    public OpenDoorActivity(){
        thisActivityReference = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_door_activity);

        openDoorButton = (Button)findViewById(R.id.openDoorButton);
        errorMessage = (TextView)findViewById(R.id.openDoorErrorMessage);
        final Message message = (Message)getIntent().getSerializableExtra("Message");
        message.setIsLogin(false);

        openDoorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lockIsAvailable) {
                    lockIsAvailable = false;
                    sendMessage = new SendMessage();
                    ((List<IReceiveResponse>) sendMessage.listeners).add(thisActivityReference);
                    sendMessage.execute(message);
                    cooldown();
                }
            }
        });
    }
    private void cooldown(){
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        openDoorButton.setEnabled(false);
        while (elapsedTime < (1.5)*1000) {
            elapsedTime = (new Date()).getTime() - startTime;
        }
        openDoorButton.setEnabled(true);
        lockIsAvailable = true;

    }

    @Override
    public void serverSentResponse(Boolean allGood) {
        if(allGood) {
            changeErrorMessage("", false);
        }
    }

    @Override
    public void changeErrorMessage(String message, boolean setVisible) {
        errorMessage.setText(message);
        errorMessage.setVisibility(setVisible ? View.VISIBLE : View.INVISIBLE);
    }
}
