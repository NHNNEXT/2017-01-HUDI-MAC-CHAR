package com.zimincom.mafiaonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zimincom.mafiaonline.GameRoomActivity;
import com.zimincom.mafiaonline.R;
import com.zimincom.mafiaonline.item.Room;
import com.zimincom.mafiaonline.item.User;

import java.util.ArrayList;

/**
 * Created by Zimincom on 2017. 4. 10..
 */

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    Context context;
    ArrayList<Room> rooms;
    User user;

    int itemLayout;

    public RoomAdapter(Context context, ArrayList<Room> rooms, User user, int itemLayout) {
        this.context = context;
        this.rooms = rooms;
        this.itemLayout = itemLayout;
        this.user = user;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.roomId.setText(rooms.get(position).getId());
        holder.roomTitle.setText(rooms.get(position).getTitle());
        String playerCount = rooms.get(position).getUserCount() + "/8";
        holder.playerCount.setText(playerCount);

        holder.itemView.setOnClickListener(view -> {
            String roomId = rooms.get(position).getId();

            Intent intent = new Intent(context, GameRoomActivity.class);
            intent.putExtra("roomId", roomId);
            intent.putExtra("user", user);
            intent.putExtra("userName", user.getNickName());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView roomId;
        public TextView roomTitle;
        public TextView playerCount;

        public ViewHolder(View itemView) {
            super(itemView);
            roomId = (TextView) itemView.findViewById(R.id.room_id);
            roomTitle = (TextView) itemView.findViewById(R.id.room_title);
            playerCount = (TextView) itemView.findViewById(R.id.current_player);

        }
    }
}
