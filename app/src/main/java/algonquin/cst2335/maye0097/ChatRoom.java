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

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.maye0097.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.maye0097.databinding.ReceiveMessageBinding;
import algonquin.cst2335.maye0097.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    RecyclerView.Adapter myAdapter;
    ArrayList<ChatMessage> messages;
    int itemClicked;
    TextView messageClicked;
    ChatRoomViewModel chatModel;
    ChatMessageDAO mDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.showOverflowMenu();

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").fallbackToDestructiveMigration().build();

        mDAO = db.cmDAO();

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

            ChatMessage tempSMessage = new ChatMessage(message, time, is);
            messages.add(tempSMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.sendMessage.setText("");

            Executor threadS = Executors.newSingleThreadExecutor();
            threadS.execute(()-> {
                mDAO.insertMessage(tempSMessage);
            });
        });

        binding.receiveButton.setOnClickListener(click -> {
            String message = binding.sendMessage.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String time = sdf.format(new Date());
            int is = 1;

            ChatMessage tempRMessage = new ChatMessage(message, time, is);
            messages.add(tempRMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.sendMessage.setText("");

            Executor threadR = Executors.newSingleThreadExecutor();
            threadR.execute(()-> {
                mDAO.insertMessage(tempRMessage);
            });
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

    public boolean onCreateOptionMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    public boolean onOptionItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.item_1:
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                builder.setMessage("Do you want to delete the message: " + messageClicked.getText());
                builder.setTitle("Question: ");
                builder.setPositiveButton("Yes", (dialog, cl) -> {
                    ChatMessage removedMessage = messages.get(itemClicked);
                    messages.remove(itemClicked);
                    myAdapter.notifyItemRemoved(itemClicked);
                    Snackbar.make(messageClicked, "You deleted message #" + itemClicked, Snackbar.LENGTH_LONG)
                            .setAction("Undo", click -> {
                                messages.add(itemClicked, removedMessage);
                                myAdapter.notifyItemInserted(itemClicked);
                            })
                            .show();
                });
                builder.setNegativeButton("No", (dialog, cl) -> {  });
                builder.create().show();
                break;
            case R.id.item_about:
                Context context = getApplicationContext();
                CharSequence text = "Version 1.0, created by Laura Mayer";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
        }
        return true;
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder (@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);

            messageClicked = messageText;

            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                itemClicked = position;
                ChatMessage selected = messages.get(position);

                chatModel.selectMessage.postValue(selected);
            });

        }
    }

}