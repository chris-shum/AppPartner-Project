package com.apppartner.androidprogrammertest.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.R;
import com.apppartner.androidprogrammertest.models.ChatData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created on 12/23/14.
 *
 * @author Thomas Colligan
 */
public class ChatsArrayAdapter extends ArrayAdapter<ChatData> {
    public ChatsArrayAdapter(Context context, List<ChatData> objects) {
        super(context, 0, objects);
    }

//    added the imageview to the adapter
//    imported Picasso library to handle image inserts
//    added check for convertView as a view does not need to be inflated if you pass a view in already


    public View getView(int position, View convertView, ViewGroup parent) {
        ChatCell chatCell = new ChatCell();

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.cell_chat, parent, false);

            chatCell.chatImage = (ImageView) convertView.findViewById(R.id.chatImage);
            chatCell.usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);
            chatCell.messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);

            ChatData chatData = getItem(position);

            Picasso.with(convertView.getContext()).load(chatData.avatarURL).into(chatCell.chatImage);
            chatCell.usernameTextView.setText(chatData.username);
            chatCell.messageTextView.setText(chatData.message);

            Typeface userNameTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Jelloween - Machinato.ttf");
            chatCell.usernameTextView.setTypeface(userNameTypeface);
            Typeface messageTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Jelloween - Machinato Light.ttf");
            chatCell.messageTextView.setTypeface(messageTypeface);
        }
        return convertView;
    }


    private static class ChatCell {
        ImageView chatImage;
        TextView usernameTextView;
        TextView messageTextView;
    }

}
