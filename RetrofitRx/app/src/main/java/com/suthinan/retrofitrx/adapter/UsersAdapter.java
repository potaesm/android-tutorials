package com.suthinan.retrofitrx.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suthinan.retrofitrx.R;
import com.suthinan.retrofitrx.model.User;
import com.suthinan.retrofitrx.view.MainActivity;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    public UsersAdapter(ArrayList<User> userArrayList, MainActivity mainActivity) {
        this.userArrayList = userArrayList;
        this.mainActivity = mainActivity;
    }

    private final ArrayList<User> userArrayList;
    private final MainActivity mainActivity;

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_list, viewGroup, false);
        return new UserViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        User user = userArrayList.get(i);
        userViewHolder.username.setText(user.getUsername());
        userViewHolder.permissionLevel.setText(user.getPermissionLevel().toString());
        userViewHolder.itemView.setOnClickListener(v -> mainActivity.addAndEditUser(true, user));
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView username, permissionLevel;

        public UserViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            permissionLevel = itemView.findViewById(R.id.permission_level);
        }
    }
}
