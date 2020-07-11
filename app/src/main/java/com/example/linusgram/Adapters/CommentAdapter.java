package com.example.linusgram.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.linusgram.Models.Comment;
import com.example.linusgram.Models.Post;
import com.example.linusgram.R;
import com.parse.ParseFile;

import org.w3c.dom.Text;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private List<Comment> comments;
    onClickListener clickListener;

    public static final int PROFILE_FRAGMENT_CODE = 22;

    public interface onClickListener{
        void onItemClicked(int position, int replyCode);
    }

    public CommentAdapter(Context context, List<Comment> comments, onClickListener clickListener) {
        this.context = context;
        this.comments = comments;
        this.clickListener = clickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment);

    }

    public void clear(){
        comments.clear();
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        ImageView ivImage;
        TextView body;
        TextView userName;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivProfileImageComment);
            body = itemView.findViewById(R.id.tv)

        }

        public void bind(final Post post) {

            String postPic = post.getImage().getUrl();

            Glide.with(context)
                    .load(postPic)
                    .into(ivImage);



        }


    }
}

