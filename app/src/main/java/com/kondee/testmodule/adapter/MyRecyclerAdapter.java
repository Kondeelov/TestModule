package com.kondee.testmodule.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kondee.testmodule.R;
import com.kondee.testmodule.model.Modeller;
import com.kondee.testmodule.view.ImageItemList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "Kondee";

    //    ListImageItemBinding listBinding;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(MyViewHolder holder, final int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imvShowList;
        public TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            imvShowList = (ImageView) itemView.findViewById(R.id.imvShowList);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        ListImageItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_image_item,parent,false);
        return new MyViewHolder(new ImageItemList(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final MyViewHolder item = (MyViewHolder) holder;

        DisplayMetrics imv = item.imvShowList.getResources().getDisplayMetrics();

        Glide.with(item.itemView.getContext())
                .load(Modeller.newInstance().imgUrl.get(position))
                .override(imv.widthPixels,imv.heightPixels)
                .into(item.imvShowList);

        ViewCompat.setTransitionName(item.imvShowList,String.valueOf(position)+"_detail");
        item.tvName.setText(position+"");

        item.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
