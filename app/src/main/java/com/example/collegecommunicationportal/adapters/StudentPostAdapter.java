package com.example.collegecommunicationportal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.collegecommunicationportal.Model.Post;
import com.example.collegecommunicationportal.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class StudentPostAdapter extends RecyclerView.Adapter<StudentPostAdapter.ProductViewHolder> {

    private Context mCtx;

    //we are storing all the products in a list
    private List<Post> productList;

    //getting the context and product list with constructor
    public StudentPostAdapter(Context mCtx, List<Post> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.post_recyclerview, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        //getting the product of the specified position
        final Post product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(product.getTitle());
        holder.textViewCollege.setText(product.getCollege());
        holder.textViewEntryFees.setText(String.valueOf(product.getEntryFee()));
        holder.textViewDate.setText(String.valueOf(product.getDate()));
        holder.textViewTime.setText(String.valueOf(product.getTime()));



    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewCollege, textViewEntryFees, textViewDate,textViewTime;
        ImageView imageView;


        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCollege = itemView.findViewById(R.id.textViewcollege);
            textViewEntryFees = itemView.findViewById(R.id.textViewEntryFees);
            textViewDate = itemView.findViewById(R.id.dateexams);
            textViewTime= itemView.findViewById(R.id.timeexams);

        }
    }
}

