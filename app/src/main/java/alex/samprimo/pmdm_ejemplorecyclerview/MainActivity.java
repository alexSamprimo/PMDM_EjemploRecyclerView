package alex.samprimo.pmdm_ejemplorecyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import alex.samprimo.pmdm_ejemplorecyclerview.adapters.ToDoAdapter;
import alex.samprimo.pmdm_ejemplorecyclerview.databinding.ActivityMainBinding;
import alex.samprimo.pmdm_ejemplorecyclerview.modelos.ToDo;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<ToDo> todoList;
    private ToDoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        todoList = new ArrayList<>();
        //crearTareas();

        //creamos el adapter
        adapter = new ToDoAdapter(todoList,R.layout.todo_view_mode,MainActivity.this);

        //enlazamos el adapter con el recycler view
        binding.contentMain.contenedor.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createToDo().show();
            }
        });
    }

    private AlertDialog createToDo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //queremos que aparezca en esta vista
        builder.setTitle("CREAR TAREA");
        builder.setCancelable(false); //para que si apretamos fuera no se cierre

        //para acceder a la vista
        View todoAlert = LayoutInflater.from(this).inflate(R.layout.todo_model_alert,null);

        EditText txtTitulo = todoAlert.findViewById(R.id.txtTituloToDoModelAlert);
        EditText txtContenido = todoAlert.findViewById(R.id.txtContenidoToDoModelAlert);

        builder.setView(todoAlert);//aqui estamos poniendo la vista que hemos creado dentro de la alerta que estamos creando

        builder.setNegativeButton("CANCELAR",null);
        builder.setPositiveButton("INSERTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!txtTitulo.getText().toString().isEmpty() && !txtContenido.getText().toString().isEmpty()){
                    todoList.add(new ToDo(txtTitulo.getText().toString(),txtContenido.getText().toString()));
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return builder.create();

    }

    private void crearTareas() {
        for (int i = 0; i < 1000000; i++) {
            todoList.add(new ToDo("Titulo"+i,"Contenido"+i));
        }
    }
}