package com.barebones.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barebones.R;
import com.barebones.models.Comments;

import java.util.List;

public class CommentsRecyclerAdapter extends RecyclerView.Adapter {

    List<Comments> mCommentsList;

    public CommentsRecyclerAdapter(List<Comments> commentsList) {
        mCommentsList = commentsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View childView =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comment_item_layout, viewGroup, false);
        CommentsViewHolder viewHolder = new CommentsViewHolder(childView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof CommentsViewHolder) {
            CommentsViewHolder holder = (CommentsViewHolder) viewHolder;
            holder.mUserTextView.setText(mCommentsList.get(position).getUser().getUserName());
            holder.mCommentTextView.setText(mCommentsList.get(position).getComment());
        }
    }

    @Override
    public int getItemCount() {
        return mCommentsList.size();
    }

    private class CommentsViewHolder extends RecyclerView.ViewHolder{

        public TextView mCommentTextView;
        public TextView mUserTextView;

        public CommentsViewHolder(View childView) {
            super(childView);

            mUserTextView = childView.findViewById(R.id.comment_item_users);
            mCommentTextView = childView.findViewById(R.id.comment_item_msg);
        }
    }
}
