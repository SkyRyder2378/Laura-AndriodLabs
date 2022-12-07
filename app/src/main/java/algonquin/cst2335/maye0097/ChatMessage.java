package algonquin.cst2335.maye0097;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="message")
    protected String message;

    @ColumnInfo(name="TimeSent")
    protected String timeSent;

    @ColumnInfo(name="SendOrReceive")
    protected int sendOrReceive;

    public ChatMessage(String m, String t, int sent){
        message = m;
        timeSent = t;
        sendOrReceive = sent;
    }

    public ChatMessage(){}

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public int isSendOrReceive() {
        return sendOrReceive;
    }

}
