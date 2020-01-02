package com.example.android.laura.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.laura.Interface.ItemClickListener;
import com.example.android.laura.R;

public class productviewholder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView nameview;
    public TextView desview;
    public ImageView iview;
    public Button delete;
    public ItemClickListener listener;

    public productviewholder(View itemview)
    {
        super(itemview);
        iview=(ImageView) itemview.findViewById(R.id.productimage);
        nameview=(TextView)itemview.findViewById(R.id.productitem);
       // desview=(TextView)itemview.findViewById(R.id.productdes);
        delete=(Button)itemview.findViewById(R.id.del);
    }

    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener =listener;
    }
    @Override
    public void onClick(View view)
    {
      listener.onClick(view,getAdapterPosition(),false);
    }
}
