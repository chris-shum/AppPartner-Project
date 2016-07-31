package com.apppartner.androidprogrammertest;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.adapters.ChatsArrayAdapter;
import com.apppartner.androidprogrammertest.models.ChatData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ChatActivity extends ActionBarActivity
{
    private static final String LOG_TAG = "ActionBarActivity";
    private ArrayList<ChatData> mChatDataArrayList;
    private ChatsArrayAdapter mChatsArrayAdapter;
    private ListView mListView;
    private Toolbar mToolbar;
    private TextView mToolbarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//        inserted toolbar
//        inserted text fonts
//        removed onBackPress as it creates an infinite backpress loop

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(android.R.color.black));
        mToolbarTextView = (TextView) findViewById(R.id.toolBarTextView);
        mToolbarTextView.setText(R.string.title_activity_chat);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato ExtraLight.ttf");
        mToolbarTextView.setTypeface(myTypeface);
        Drawable backArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        backArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        mToolbar.setNavigationIcon(backArrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mListView = (ListView) findViewById(R.id.chatListView);
        mChatDataArrayList = new ArrayList<ChatData>();

        try
        {
            String chatFileData = loadChatFile();
            JSONObject jsonData = new JSONObject(chatFileData);
            JSONArray jsonArray = jsonData.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ChatData chatData = new ChatData(jsonObject);
                mChatDataArrayList.add(chatData);
            }
        }
        catch (Exception e)
        {
            Log.w(LOG_TAG, e);
        }
        mChatsArrayAdapter = new ChatsArrayAdapter(this, mChatDataArrayList);
        mListView.setAdapter(mChatsArrayAdapter);
    }

    private String loadChatFile() throws IOException
    {
        InputStream inputStream = getResources().openRawResource(R.raw.chat_data);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String receiveString;
        StringBuilder stringBuilder = new StringBuilder();
        while ((receiveString = bufferedReader.readLine()) != null )
        {
            stringBuilder.append(receiveString);
            stringBuilder.append("\n");
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        return stringBuilder.toString();
    }

}
