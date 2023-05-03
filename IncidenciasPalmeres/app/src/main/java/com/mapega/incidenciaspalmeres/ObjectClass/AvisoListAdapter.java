package com.mapega.incidenciaspalmeres.ObjectClass;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mapega.incidenciaspalmeres.Conexion;
import com.mapega.incidenciaspalmeres.LayoutController.Avisoexpose;
import com.mapega.incidenciaspalmeres.R;

import java.sql.Date;
import java.util.List;

public class AvisoListAdapter extends RecyclerView.Adapter<AvisoListAdapter.ViewHolder> {
    private List<Aviso> mdata;
    private LayoutInflater mInflater;
    private Context context;

    public AvisoListAdapter(List<Aviso> itemList, Context context){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mdata=itemList;
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

        Button btn;

        ViewHolder(View itemView){
            super(itemView);
            important =itemView.findViewById(R.id.important);
            titulo=itemView.findViewById(R.id.titulo);
            fecha=itemView.findViewById(R.id.fecha);
            btn=itemView.findViewById(R.id.btn_ver_detalles);
        }

        void bindData(final Aviso item){
            titulo.setText(item.getTitulo());
            fecha.setText(item.getFechaCreacion().toString());
            if (item.getNivel_prioridad()==1){
                important.setImageResource(R.drawable.ic_importance_1);
            } else if (item.getNivel_prioridad()==2) {
                important.setImageResource(R.drawable.ic_importance_2);
            } else if (item.getNivel_prioridad()==3) {
                important.setImageResource(R.drawable.ic_importance_3);
            }else{
                important.setImageResource(R.drawable.ic_importance_ukn);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Avisoexpose.class);
                    intent.putExtra("aviso", item);
                    context.startActivity(intent);
                }
            });
        }

    }
    

}
