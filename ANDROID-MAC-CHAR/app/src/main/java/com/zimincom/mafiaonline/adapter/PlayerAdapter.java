package com.zimincom.mafiaonline.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zimincom.mafiaonline.R;
import com.zimincom.mafiaonline.item.User;

import java.util.ArrayList;

/**
 * Created by Zimincom on 2017. 5. 16..
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    public static final int READY_MESSAGE = 202;
    Context context;
    ArrayList<User> users;
    User lastVotedUser;
    int playerLayout;
    String userName;
    String gameState;
    Handler handler;

    public PlayerAdapter(Context context, ArrayList<User> users, int messageLayout, String userName,
                         String gameState, Handler handler) {
        this.context = context;
        this.users = users;
        this.playerLayout = messageLayout;
        this.userName = userName;
        this.gameState = gameState;
        this.handler = handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(playerLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int readyColor = context.getResources().getColor(R.color.colorPrimary);
        User selectedUser = users.get(position);
        holder.nickname.setText(users.get(position).getNickName());
        if (selectedUser.isReady())
            holder.readyState.setTextColor(readyColor);

        if (selectedUser.isKilled()) {
            Drawable overlay = context.getDrawable(R.drawable.bullet_hole);
            holder.container.getOverlay().add(overlay);
        }
        if (selectedUser.getVoted()) {
            holder.container.setSelected(true);
            Logger.d("user seleted");
        } else {
            holder.container.setSelected(false);
        }

        // if day -> 타이머가 종료될때 서버에 메시지를 보낸다 .
         if (gameState != null && gameState.equals("day")) {
            holder.readyState.setVisibility(View.GONE);
            holder.container.setOnClickListener(view -> {
                if (lastVotedUser == null) {
                    lastVotedUser = users.get(position);
                }
                Logger.d("last votedUser: %s", lastVotedUser.getNickName());
                lastVotedUser.setVoted(false);
                //set new voted User;
                lastVotedUser = users.get(position);
                users.get(position).setVoted(true);
                notifyDataSetChanged();
            });

        } else {

            holder.itemView.setOnClickListener(view -> {
                if (selectedUser.getNickName().equals(userName)) {
                    if (!selectedUser.isReady()) {
                        holder.readyState.setTextColor(readyColor);
                        selectedUser.setStatus(User.Status.READY);
                        handler.sendEmptyMessage(READY_MESSAGE);
                        Logger.d("im ready");
                    } else {
                        holder.readyState.setTextColor(Color.WHITE);
                        selectedUser.setStatus(User.Status.NOT_READY);
                        handler.sendEmptyMessage(READY_MESSAGE);
                        Logger.d("ready canceled");
                    }
                }
            });
        }
    }

    public void addItems(ArrayList<User> users) {
        Logger.d(users);
        this.users = users;
        notifyDataSetChanged();
    }

    public void addItemByNickName(String nickName) {
        User user = new User(nickName, User.Status.NOT_READY);
        users.add(user);
        notifyDataSetChanged();
    }

    public void ready(String nickName) {
        int targetPosition = 0;
        for (User user : users) {
            if (user.getNickName().equals(nickName)) {
                if (!user.isReady()) {
                    user.setStatus(User.Status.READY);
                    notifyItemChanged(targetPosition);
                    break;
                } else {
                    user.setStatus(User.Status.NOT_READY);
                    notifyItemChanged(targetPosition);
                    break;
                }
            }
            targetPosition++;
        }
    }

    public void removeItemByNickName(String nickName) {
        int targetPosition = 0;
        for (User user : users) {
            if (user.getNickName().equals(nickName)) {
                Logger.d(nickName);
                Logger.d(targetPosition);
                users.remove(targetPosition);
                notifyItemRemoved(targetPosition);
                break;
            }
            targetPosition++;
        }
    }

    public void setState(String gameState) {
        this.gameState = gameState;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public String getVotedUserName() {
         if (lastVotedUser == null) {
             return "";
         }
        return lastVotedUser.getNickName();
    }

    public void killByNickName(String nickName) {
        int targetPosition = 0;
        for (User user : users) {
            if (user.getNickName().equals(nickName)) {
                users.get(targetPosition).setKilled(true);
                notifyItemChanged(targetPosition);
                break;
            }
            targetPosition++;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout container;
        TextView nickname;
        TextView readyState;
        TextView role;

        public ViewHolder(View itemView) {
            super(itemView);
            container = (LinearLayout) itemView.findViewById(R.id.container);
            nickname = (TextView) itemView.findViewById(R.id.nickname);
            readyState = (TextView) itemView.findViewById(R.id.ready_state);
            role = (TextView) itemView.findViewById(R.id.role);

        }
    }
}
