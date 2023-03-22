package algonquin.cst2335.maye0097;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.maye0097.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.maye0097.databinding.ReceiveMessageBinding;
import algonquin.cst2335.maye0097.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    RecyclerView.Adapter myAdapter;
    ArrayList<ChatMessage> messages;
    ChatRoomViewModel chatModel;
    ChatMessageDAO mDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null){
            chatModel.messages.setValue(messages = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                messages.addAll(mDAO.getAllMessages());
                binding.recycleView.setAdapter( myAdapter );
            });
        }

        binding.sendButton.setOnClickListener(click -> {
            String message = binding.sendMessage.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String time = sdf.format(new Date());
            int is = 0;

            ChatMessage tempMessage = new ChatMessage(message, time, is);
            messages.add(tempMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.sendMessage.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {
            String message = binding.sendMessage.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String time = sdf.format(new Date());
            int is = 1;

            ChatMessage tempMessage = new ChatMessage(message, time, is);
            messages.add(tempMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.sendMessage.setText("");
        });

        chatModel.selectMessage.observe(this, (newMessageValue) -> {
            MessageDetailFragment chatFragment = new MessageDetailFragment (newMessageValue);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frameLayout, chatFragment);
            transaction.commit();
            transaction.addToBackStack("");
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view;
                MyRowHolder rowHolder;
                if(viewType == 0){
                    SentMessageBinding newBinding = SentMessageBinding.inflate(getLayoutInflater());
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_message, parent, false);
                    rowHolder = new MyRowHolder(newBinding.getRoot());
                }
                else{
                    ReceiveMessageBinding newBinding = ReceiveMessageBinding.inflate(getLayoutInflater());
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_message, parent, false);
                    rowHolder = new MyRowHolder(newBinding.getRoot());
                }
                return rowHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                String m = messages.get(position).message;
                String t = messages.get(position).timeSent;
                holder.messageText.setText(m);
                holder.timeText.setText(t);
            }

            public int getItemViewType(int position) {
                ChatMessage message = messages.get(position);
                if(message.isSendOrReceive() == 0){
                    return 0;
                }
                return 1;
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }
        });

    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder (@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                ChatMessage selected = messages.get(position);

                chatModel.selectMessage.postValue(selected);
            });
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }

}