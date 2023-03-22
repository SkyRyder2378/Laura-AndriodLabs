package algonquin.cst2335.maye0097;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.maye0097.databinding.DetailsLayoutBinding;

public class MessageDetailFragment extends Fragment {

    ChatMessage selected;

    public MessageDetailFragment(){
    }

    public MessageDetailFragment(ChatMessage message){
        selected = message;
    }

    public void displayMessage(ChatMessage message){
        selected = message;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.messageTextView.setText(selected.message);
        binding.timeTextView.setText(selected.timeSent);
        binding.databaseIDTextView.setText("Id = " + selected.id);

        return binding.getRoot();
    }

}
