package com.example.aplikasibookingruangan_fp_pam.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasibookingruangan_fp_pam.Model.ModelData;
import com.example.aplikasibookingruangan_fp_pam.R;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.ViewHolder> {
    private List<ModelData> mItem;
    private Context context;

    public AdapterData(Context context, List<ModelData> items){
        this.mItem=items;
        this.context=context;
    }


    @NonNull
    @Override
    public AdapterData.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterData.ViewHolder holder, int position) {
        final ModelData modelData = mItem.get(position);
        holder.urai_ruangan.setText(modelData.getRuangan());

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView urai_ruangan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urai_ruangan = itemView.findViewById(R.id.ruangan);
        }
    }
}
