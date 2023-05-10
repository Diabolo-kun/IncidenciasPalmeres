package com.mapega.incidenciaspalmeres.ObjectClass;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.os.AsyncTask;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.ImageView;
        import android.widget.TextView;
        import androidx.recyclerview.widget.RecyclerView;

        import com.mapega.incidenciaspalmeres.Conexion;
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
                    showAlertDialog(item);
                }
            });
        }
        private void showAlertDialog(IncidenciaMantenimiento item) {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle(item.getTitulo());

            // Inflar el layout personalizado
            View dialogView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.dialog_almacen, null);
            builder.setView(dialogView);

            // Obtener referencias a los TextViews del layout personalizado
            TextView idTextView = dialogView.findViewById(R.id.id);
            TextView idUserTextView = dialogView.findViewById(R.id.iduseralmacen);
            TextView fechacreaTextView = dialogView.findViewById(R.id.fechacreacionalmacen);
            TextView fechafinTextView = dialogView.findViewById(R.id.fechafinalmacen);
            TextView importanciaTextView = dialogView.findViewById(R.id.importancia);
            TextView descripcionContenidoTextView = dialogView.findViewById(R.id.descripcion_contenido);
            CheckBox pedido= dialogView.findViewById(R.id.check);

            // Establecer los valores de los TextViews con la información del aviso
            idTextView.setText("ID: " + item.getId());
            idUserTextView.setText("Usuario: " + item.getId_usuario_creador());
            fechacreaTextView.setText("Fecha Creacion: " + item.getFecha_creacion());
            fechafinTextView.setText("Fecha Finalizacion: " + item.getFecha_finalizacion());
            importanciaTextView.setText("Prioridad: " + item.getNivel_prioridad());
            descripcionContenidoTextView.setText(item.getDescripcion());
            pedido.setChecked(item.isDone());
            builder.setPositiveButton("Validar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Acción al pulsar el botón Validar
                    boolean isChecked = pedido.isChecked();
                    new MantenimientoListAdapter.ResueltoAsyncTask(dialog).execute(item.getId(), isChecked);
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    private static class ResueltoAsyncTask extends AsyncTask<Object, Void, Void> {
        private final DialogInterface dialog;
        public ResueltoAsyncTask(DialogInterface dialog) {
            this.dialog = dialog;
        }
        @Override
        protected Void doInBackground(Object... params) {
            int objetoId = (int) params[0];
            boolean estadoCheckBox = (boolean) params[1];
            Conexion.done(objetoId, estadoCheckBox);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            // Acciones posteriores a la ejecución de la tarea
            dialog.dismiss();
        }
    }
}
