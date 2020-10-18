package com.zohotask.app.countrylist.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.zohotask.app.R;
import com.zohotask.app.countrylist.model.CountryListModel;

import java.util.List;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.MyViewHolder> {
    private List<CountryListModel> countryListArr;
    private Activity context;
    private OnItemClickListener itemClickListener;
    public CountryListAdapter(Activity context, List<CountryListModel> countryListArr, CountryListAdapter.OnItemClickListener itemClickListener){
        this.context = context;
        this.countryListArr = countryListArr;
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_item, parent,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CountryListModel countryListModel = countryListArr.get(position);
        SvgLoader.pluck()
                .with(context)
                .setPlaceHolder(R.drawable.ic_image, R.drawable.ic_image)
                .load(countryListModel.getFlag(), holder.flagImage);
        holder.countryText.setText(countryListModel.getName());
        holder.capitalText.setText("Capital: "+countryListModel.getCapital());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(countryListModel);
            }
        });
    }

    public void updateList(List<CountryListModel> list){
        countryListArr = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return countryListArr.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView flagImage;
        TextView countryText;
        TextView capitalText;
        RelativeLayout parentLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            flagImage = itemView.findViewById(R.id.flagImage);
            countryText = itemView.findViewById(R.id.countryTv);
            capitalText = itemView.findViewById(R.id.capitalTv);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(CountryListModel countryListModel);
    }
}
