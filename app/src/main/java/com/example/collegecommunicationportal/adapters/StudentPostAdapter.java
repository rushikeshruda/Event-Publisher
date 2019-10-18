package com.example.collegecommunicationportal.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegecommunicationportal.Model.Post;
import com.example.collegecommunicationportal.Model.PostRegister;
import com.example.collegecommunicationportal.R;
import com.example.collegecommunicationportal.RegisterPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.List;

public class StudentPostAdapter extends RecyclerView.Adapter<StudentPostAdapter.ProductViewHolder> {

    private Context mCtx;
    int count=0;
   // Post product;

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


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popup = new PopupMenu(mCtx,holder.imageView);
                popup.getMenuInflater().inflate(R.menu.popup_interested, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.interest_popup:


                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Post Register");

                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.exists()) {

                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                PostRegister postRegister = postSnapshot.getValue(PostRegister.class);

                                                if ((postRegister.getStudentId().equals(FirebaseAuth.getInstance().getUid())) && product.getId().equals(postRegister.getPostId()) || postRegister == null) {
                                                    Toast.makeText(mCtx, "Already Registered", Toast.LENGTH_SHORT).show();
                                                    count=1;
                                                    break;

                                                }
                                            }
                                        }else
                                        {
                                            if (count==1){count=0;}
                                            else {
                                                Intent intent = new Intent(mCtx, RegisterPost.class);

                                                intent.putExtra("Post", product);

                                                view.getContext().startActivity(intent);
                                            }
                                        }

                                        if (count==1){count=0;}
                                        else {
                                            Intent intent = new Intent(mCtx, RegisterPost.class);

                                            intent.putExtra("Post", product);

                                            view.getContext().startActivity(intent);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                return true;

                            case R.id.not_interest_popup:

                                final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Post Register");

                                databaseReference1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                                        {
                                            PostRegister postRegister = postSnapshot.getValue(PostRegister.class);

                                            if ((postRegister.getStudentId().equals(FirebaseAuth.getInstance().getUid()))&&product.getId().equals(postRegister.getPostId()))
                                            {
                                                databaseReference1.removeValue();


                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                return true;

                            default:
                                return onMenuItemClick(item);
                        }
                    }
                });
                popup.show();

            }
        });

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
            imageView = itemView.findViewById(R.id.popupbtn);

        }
    }
}

