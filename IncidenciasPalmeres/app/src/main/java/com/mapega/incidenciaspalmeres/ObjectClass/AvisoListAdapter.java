package com.mapega.incidenciaspalmeres.ObjectClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mapega.incidenciaspalmeres.R;

import java.util.List;

public class AvisoListAdapter extends RecyclerView.Adapter<AvisoListAdapter.ViewHolder> {
    private List<Aviso> mdata;
    private LayoutInflater mInflater;
    private Context context;

    final AvisoListAdapter.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(Aviso item);
    }

    public AvisoListAdapter(List<Aviso> itemList, Context context, AvisoListAdapter.OnItemClickListener listener){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mdata=itemList;
        this.listener= listener;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (Aviso aviso : mdata) {
            if (aviso.isVisible()) { // suponiendo que el nombre de la variable es "visible" y es de tipo boolean
                count++;
            }
        }
        return count;
    }

    @Override
    public AvisoListAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.avisoelement,null);
        return new AvisoListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AvisoListAdapter.ViewHolder holder, int position) {
        holder.bindData(mdata.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView important;
        TextView titulo, fecha;

        ViewHolder(View itemView){
            super(itemView);
            important =itemView.findViewById(R.id.important);
            titulo=itemView.findViewById(R.id.titulo);
            fecha=itemView.findViewById(R.id.fecha);
        }

        void bindData(final Aviso item){
            titulo.setText(item.getTitulo());
            fecha.setText(item.getFechaCreacion().toString());
            if (item.isImportante()){
                important.setImageResource(R.drawable.ic_important);
            }else{
                important.setImageResource(R.drawable.ic_no_important);
            }
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    

}
