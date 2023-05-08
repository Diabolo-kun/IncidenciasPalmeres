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

        import com.mapega.incidenciaspalmeres.LayoutController.Avisoexpose;
        import com.mapega.incidenciaspalmeres.LayoutController.Mantenimientoexpose;
        import com.mapega.incidenciaspalmeres.R;

        import java.util.List;

public class MantenimientoListAdapter extends RecyclerView.Adapter<MantenimientoListAdapter.ViewHolder> {
    private List<IncidenciaMantenimiento> mdata;
    private LayoutInflater mInflater;
    private Context context;

    public MantenimientoListAdapter(List<IncidenciaMantenimiento> itemList, Context context){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mdata=itemList;
    }

    @Override
    public int getItemCount() {

        return mdata.size();
    }

    @Override
    public MantenimientoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.mantenelement,null);
        return new MantenimientoListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MantenimientoListAdapter.ViewHolder holder, int position) {
        holder.bindData(mdata.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView titulo;

        Button btn;

        ViewHolder(View itemView){
            super(itemView);
            img =itemView.findViewById(R.id.done);
            titulo=itemView.findViewById(R.id.titulomantenimiento);
            btn=itemView.findViewById(R.id.btn_ver_detalles);
        }

        void bindData(final IncidenciaMantenimiento item){
            titulo.setText(item.getTitulo());
            if (item.isDone()){
                img.setImageResource(R.drawable.ic_check);
            }else{
                img.setImageResource(R.drawable.ic_no_check);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Mantenimientoexpose.class);
                    intent.putExtra("IncidenciaMantenimiento", item);
                    context.startActivity(intent);
                }
            });
        }
    }



}