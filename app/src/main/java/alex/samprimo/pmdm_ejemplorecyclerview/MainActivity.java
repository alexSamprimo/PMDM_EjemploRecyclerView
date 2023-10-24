package alex.samprimo.pmdm_ejemplorecyclerview;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

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
        crearTareas();

        //creamos el adapter
        adapter = new ToDoAdapter(todoList,R.layout.todo_view_mode,MainActivity.this);

        //enlazamos el adapter con el recycler view
        binding.contentMain.contenedor.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void crearTareas() {
        for (int i = 0; i < 1000000; i++) {
            todoList.add(new ToDo("Titulo"+i,"Contenido"+i));
        }
    }
}