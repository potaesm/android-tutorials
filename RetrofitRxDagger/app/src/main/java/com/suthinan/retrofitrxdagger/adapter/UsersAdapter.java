package com.suthinan.retrofitrxdagger.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.suthinan.retrofitrxdagger.R;
import com.suthinan.retrofitrxdagger.model.User;
import com.suthinan.retrofitrxdagger.view.MainActivity;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

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
