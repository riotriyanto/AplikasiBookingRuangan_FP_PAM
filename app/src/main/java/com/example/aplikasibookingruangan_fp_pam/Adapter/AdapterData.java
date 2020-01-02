package com.example.aplikasibookingruangan_fp_pam.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasibookingruangan_fp_pam.Delete;
import com.example.aplikasibookingruangan_fp_pam.Edit;
import android.content.Intent;
import com.example.aplikasibookingruangan_fp_pam.Login;
import com.example.aplikasibookingruangan_fp_pam.MainActivity;
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
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aksi", "arep ngedit");
//                Intent intent = new Intent(this, Edit.class);
                Intent intent = new Intent(context, Edit.class);
                intent.putExtra("ruangan", modelData.getId());
                context.startActivity(intent);

            }
        });

        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aksi", "arep hapus");
                Intent intent = new Intent(context, Delete.class);
                intent.putExtra("ruangan", modelData.getId());
                context.startActivity(intent);

//                context.startActivity(new Intent(context, Edit.class));




            }
        });

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView urai_ruangan;
        public Button btn_edit, btn_del;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urai_ruangan = itemView.findViewById(R.id.ruangan);
            btn_del = itemView.findViewById(R.id.btn_del);
            btn_edit = itemView.findViewById(R.id.btn_edit);
        }
    }
}
