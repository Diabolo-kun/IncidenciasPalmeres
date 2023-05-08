package com.mapega.incidenciaspalmeres.ObjectClass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mapega.incidenciaspalmeres.LayoutController.Almacenexpose;
import com.mapega.incidenciaspalmeres.R;

import java.util.List;

public class AlmacenListAdapter extends RecyclerView.Adapter<AlmacenListAdapter.ViewHolder> {
    private List<IncidenciaAlmacen> mdata;
    private LayoutInflater mInflater;
    private Context context;

    public AlmacenListAdapter(List<IncidenciaAlmacen> itemList, Context context){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mdata=itemList;
    }

    @Override
    public int getItemCount() {

        return mdata.size();
    }

    @Override
    public AlmacenListAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.almacenelement,null);
        return new AlmacenListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AlmacenListAdapter.ViewHolder holder, int position) {
        holder.bindData(mdata.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView titulo, cantidad;
        Button btn;


        ViewHolder(View itemView){
            super(itemView);
            img =itemView.findViewById(R.id.pedido);
            titulo=itemView.findViewById(R.id.tituloalmacen);
            cantidad=itemView.findViewById(R.id.cantidad);
            btn=itemView.findViewById(R.id.btn_ver_detalles);
        }

        void bindData(final IncidenciaAlmacen item){
            titulo.setText(String.valueOf(item.getProducto()));
            cantidad.setText(String.valueOf(item.getCantidad()));
            if (item.isPedido()){
                img.setImageResource(R.drawable.ic_check);
            }else{
                img.setImageResource(R.drawable.ic_no_check);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Almacenexpose.class);
                    intent.putExtra("IncidenciaAlmacen", item);
                    context.startActivity(intent);
                }
            });
        }
    }



}

