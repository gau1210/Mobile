package com.example.faculdade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected ListView lista;
    protected DisciplinaValue disciplinaValue;
    protected ArrayAdapter<DisciplinaValue> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisciplinaDAO dao = new DisciplinaDAO(this);

        //String[] disciplinas = {"Redes", "Algoritmos", "Programacao"};


        int layout = android.R.layout.simple_list_item_1;

        adapter = new ArrayAdapter<DisciplinaValue>(this, layout, dao.getLista());

        dao.close();

        lista = (ListView) findViewById(R.id.listView);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View view,
                                    int posicao, long id) {
                Toast.makeText(MainActivity.this, adapter.getItemAtPosition(posicao).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void onResume() {

        super.onResume();
        DisciplinaDAO dao = new DisciplinaDAO(this);
        ArrayList<DisciplinaValue> disciplinas= new ArrayList(dao.getLista());
        dao.close();
        int layout = android.R.layout.simple_list_item_1;

        adapter =
                new ArrayAdapter<DisciplinaValue>(this,layout, disciplinas);


        lista.setAdapter(adapter);

        lista.setOnCreateContextMenuListener(this);

        registerForContextMenu(lista);

    }
    @Override
    protected void onActivityResult(int codigo,int reultado,Intent it) {

        super.onActivityResult(codigo, reultado, it);
        this.adapter.notifyDataSetChanged();

    }

    public boolean onContextItemSelected(final MenuItem item) {
        disciplinaValue= (DisciplinaValue) this.adapter.getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
        int id = item.getItemId();
        if (id == R.id.action_new) {
            Intent intent = new Intent(this, DisciplinaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_update) {
            Intent intent = new Intent(this, DisciplinaActivity.class);
            intent.putExtra("disciplinaSelecionada", disciplinaValue);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_delete) {
            DisciplinaDAO dao = new DisciplinaDAO(MainActivity.this);
            dao.deletar(disciplinaValue);
            dao.close();
            this.onResume();
            return true;
        }
        return true;
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_lista_disciplinas, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu. menu_lista_disciplinas, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_new) {
            Intent intent = new Intent(this, DisciplinaActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}