package alex.samprimo.pmdm_ejemplorecyclerview.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import alex.samprimo.pmdm_ejemplorecyclerview.R;
import alex.samprimo.pmdm_ejemplorecyclerview.modelos.ToDo;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoVH> {
    //la lista de cosas que quiero mostrar

    private List<ToDo> objects;

    //la fila del elemento que quiero mostrar
    private int resource;

    //contexto, la actividad donde lo voy a mostrar
    private Context context;

    public ToDoAdapter(List<ToDo> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    //instanciar tantos elementos como quepan por pantalla
    @NonNull
    @Override
    public ToDoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(context).inflate(resource,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        todoView.setLayoutParams(lp); //le estamos poniendo parametros a la vista
        return new ToDoVH(todoView);
    }

    //para pintar cada uno de los elementos (les mete el contenido)
    @Override
    public void onBindViewHolder(@NonNull ToDoVH holder, int position) {
        ToDo toDo = objects.get(position);
        holder.lbTitulo.setText(toDo.getTitulo());
        holder.lbContenido.setText(toDo.getContenido());
        holder.lbFecha.setText(toDo.getFecha().toString());

        if (toDo.isCompletado()){
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        }else{
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);
        }
        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmaUpdate("¿Seguro que quieres cambiar?",toDo).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete("SEGURO QUE QUIERES ELIMINAR?",holder.getAdapterPosition()).show();
            }
        });
    }
    //cuantos elementos tengo para mostrar

    @Override
    public int getItemCount() {
        return objects.size();
    }
    private AlertDialog confirmaUpdate(String titulo, ToDo toDo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo);
        builder.setCancelable(false);

        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toDo.setCompletado(!toDo.isCompletado()); //ponemos el checkbox al contrario de lo que estaba marcado
                notifyDataSetChanged();
            }
        });

        return builder.create();
    }

    private AlertDialog confirmDelete(String titulo, int posicion){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo);
        builder.setCancelable(false);
        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                objects.remove(posicion);
                notifyItemRemoved(posicion);
            }
        });
        return builder.create();
    }

    public class ToDoVH extends RecyclerView.ViewHolder{

        TextView lbTitulo, lbContenido, lbFecha;
        ImageButton btnCompletado,btnDelete;

        public ToDoVH(@NonNull View itemView) {
            super(itemView);

            lbTitulo = itemView.findViewById(R.id.lbTituloToDoViewModel);
            lbContenido = itemView.findViewById(R.id.lbContenidoToDoModelView);
            lbFecha = itemView.findViewById(R.id.lbFechaToDoViewModel);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoToDoViewModel);
            btnDelete = itemView.findViewById(R.id.btnDeleteToDoViewModel);
        }
    }

}
