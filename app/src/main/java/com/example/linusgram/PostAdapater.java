package com.example.linusgram;

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
import com.parse.ParseFile;

import java.util.List;

public class PostAdapater extends RecyclerView.Adapter<PostAdapater.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostAdapater(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }

    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }

    public void addaLL(List<Post> post){

        posts.addAll(post);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private TextView tvUserName;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvUserNameDescription;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivPostImage);
            tvUserNameDescription = itemView.findViewById(R.id.tvUserNameDescription);

        }

        public void bind(Post post) {

            tvDescription.setText(post.getDescription());
            tvUserName.setText(post.getUser().getUsername());
            tvUserNameDescription.setText(post.getUser().getUsername());

            ParseFile image = post.getImage();

            if (image != null){
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivImage);
            }else{
                Log.i("pOSN CB ", "IMAGE NOT EXISTING" );
            }




        }
    }
}
