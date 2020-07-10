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
import com.example.linusgram.Models.Post;
import com.example.linusgram.R;
import com.parse.ParseFile;

import org.w3c.dom.Text;

import java.util.List;

public class PostAdapater extends RecyclerView.Adapter<PostAdapater.ViewHolder> {

    private Context context;
    private List<Post> posts;
    onClickListener clickListener;
    public static final int DETAILS_CODE = 200;

    public interface onClickListener{
        void onItemClicked(int position, int replyCode);
    }

    public PostAdapater(Context context, List<Post> posts, onClickListener clickListener) {
        this.context = context;
        this.posts = posts;
        this.clickListener = clickListener;
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



    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private TextView tvUserName;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvUserNameDescription;
        private TextView tvDate;
        private TextView tvNumberofLikes;
        private TextView profilePic;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivPostImage);
            tvUserNameDescription = itemView.findViewById(R.id.tvUserNameDescription);
            tvDate = itemView.findViewById(R.id.tvCreatedAt);
            tvNumberofLikes = itemView.findViewById(R.id.NumberofActualLikes);


        }

        public void bind(Post post) {

            tvDescription.setText(post.getDescription());
            tvUserName.setText(post.getUser().getUsername());
            tvUserNameDescription.setText(post.getUser().getUsername());
            tvDate.setText(post.getTime());
            tvNumberofLikes.setText(post.getLikes().toString());

            ParseFile image = post.getImage();

            if (image != null){
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivImage);
            }else{
                Log.i("pOSN CB ", "IMAGE NOT EXISTING" );
            }

            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClicked(getAdapterPosition(), DETAILS_CODE);
                }
            });

        }
    }
}
