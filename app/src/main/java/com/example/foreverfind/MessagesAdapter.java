package com.example.foreverfind;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foreverfind.model.Promotion;
import com.example.foreverfind.sessions.SessionManagement;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MessagesAdapter extends FirebaseRecyclerAdapter<
        Promotion, MessagesAdapter.messagesViewholder> {


    public MessagesAdapter(@NonNull FirebaseRecyclerOptions<Promotion> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull messagesViewholder holder, int position, @NonNull Promotion model) {
        SessionManagement sm = new SessionManagement(holder.itemView.getContext());
        String sw = sm.getSwitch();

        if(sw.equals("switch_on")) {

            String key = this.getRef(position).getKey();
            holder.desc.setText(model.getDesc());
            holder.code.setText("Enter code: " + key);
            holder.edate.setText("Valid till: " + model.getExpdate());

        }
    }

    @NonNull
    @Override
    public messagesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messages_view, parent, false);
        return new MessagesAdapter.messagesViewholder(view);
    }

    class messagesViewholder
            extends RecyclerView.ViewHolder {
        public TextView desc,code,edate;
        public messagesViewholder(@NonNull View itemView)
        {
            super(itemView);


            desc = itemView.findViewById(R.id.desc);
            code = itemView.findViewById(R.id.code);
            edate = itemView.findViewById(R.id.exp);

        }
    }
}
