package com.example.faculdade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DisciplinaActivity extends AppCompatActivity {

    protected EditText editTextDisciplina;
    protected DisciplinaValue disciplinaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);

        Button button = (Button) findViewById(R.id.buttonSalvar);

        this.editTextDisciplina = (EditText) findViewById(R.id.editTextDisciplina);

        Intent intent = getIntent();
        disciplinaSelecionada = (DisciplinaValue) intent.getSerializableExtra("disciplinaSelecionada");
        if(disciplinaSelecionada!=null) {
            button.setText("Alterar");
            editTextDisciplina.setText(disciplinaSelecionada.getDisciplina());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DisciplinaDAO dao = new DisciplinaDAO(DisciplinaActivity.this);
                if (disciplinaSelecionada==null) {
                    DisciplinaValue disciplinaValue = new DisciplinaValue();
                    disciplinaValue.setDisciplina(editTextDisciplina.getText().toString());
                    dao.salvar(disciplinaValue);
                }else{
                    disciplinaSelecionada.setDisciplina(editTextDisciplina.getText().toString());
                    dao.alterar(disciplinaSelecionada);

                }

                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_disciplinas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}