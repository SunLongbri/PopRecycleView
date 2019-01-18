package com.heaven.sl.recycleviewtest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.text.SimpleDateFormat;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMsgRecyclerView;
    private MsgAdapter mMsgAdapter;
    private List<Message> mMsgList;

    private boolean mFlag = false;
    private int mNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initDatas();
    }

    private void initViews() {
        setContentView(R.layout.activity_check_recyclerview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setHomeAsUpEnable(false);
        mMsgRecyclerView = findViewById(R.id.recycler_view);
        mMsgRecyclerView.setAdapter(mMsgAdapter);

    }

    private void setHomeAsUpEnable(boolean enable) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enable);
        }
    }

    private void initDatas() {
        mMsgList = new LinkedList<>();
        mMsgAdapter = new MsgAdapter();
        mMsgRecyclerView.setAdapter(mMsgAdapter);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                //获取当前时间
                String str = formatter.format(curDate);


                if (mNum == 1000) {
                    return;
                }
                if (mFlag) {
                    updateMessage(str, mFlag);
                    mFlag = false;
                } else {
                    updateMessage(str, mFlag);
                    mFlag = true;
                }
                mNum++;

                handler.postDelayed(this, 1 * 1000);
            }
        }, 50);

    }

    private void updateMessage(String message, boolean isNotificaiton) {

        runOnUiThread(() -> {
            Message msg = new Message();
            msg.text = message;
            msg.isNotification = isNotificaiton;
            mMsgList.add(msg);
            mMsgAdapter.notifyItemInserted(mMsgList.size() - 1);
            mMsgRecyclerView.scrollToPosition(mMsgList.size() - 1);
        });
    }

    private class MsgHolder extends RecyclerView.ViewHolder {
        TextView text1;

        MsgHolder(View itemView) {
            super(itemView);

            text1 = itemView.findViewById(android.R.id.text1);
        }
    }

    private class MsgAdapter extends RecyclerView.Adapter<MsgHolder> {

        @NonNull
        @Override
        public MsgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.blufi_message_item, parent, false);
            return new MsgHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MsgHolder holder, int position) {
            Message msg = mMsgList.get(position);
            holder.text1.setText(msg.text);
            holder.text1.setTextColor(msg.isNotification ? Color.RED : Color.BLACK);
        }

        @Override
        public int getItemCount() {
            return mMsgList.size();
        }
    }

    private class Message {
        String text;
        boolean isNotification;
    }


}

