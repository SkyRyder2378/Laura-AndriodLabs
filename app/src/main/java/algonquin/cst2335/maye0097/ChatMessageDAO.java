package algonquin.cst2335.maye0097;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.maye0097.ChatMessage;

@Dao
public interface ChatMessageDAO {

    @Insert
    public void insertMessage(ChatMessage message);

    @Query("Select * from ChatMessage")
    public List<ChatMessage> getAllMessages();

    @Delete
    void deleteMessage(ChatMessage message);

}
