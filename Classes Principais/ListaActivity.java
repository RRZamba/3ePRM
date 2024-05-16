package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity
{

    //Atributo
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lista = (ListView) findViewById(R.id.lista);

        SelecionarTodos();

    } //onCreate

    public void SelecionarTodos()
    {
        //Iniciando classe SQLiteDB
        SQLiteDatabase bcoDadosShow =
                      openOrCreateDatabase("ETEC",
                                          MODE_PRIVATE,
                                            null);

        try
        {
            // 2) Montando a query de select
            String sql = "SELECT * FROM Alunos";

            // 3) Criando um cursor para armazenar os dados
            Cursor objCursor;

            // 4) Executando a query e salvando os dados no cursor
            objCursor = bcoDadosShow.rawQuery(sql,null);


            //5) Instanciando a classe Aluno e
            // criando uma lista
            ArrayList<Aluno> listaAluno =
                    new ArrayList<Aluno>();


            // 6) Percorrendo Cursor linha a linha
            while(objCursor.moveToNext())
            {
                Aluno objAluno = new Aluno();
                objAluno.setMatricula(objCursor.getInt(0));
                objAluno.setNome(objCursor.getString(1));
                objAluno.setCurso(objCursor.getString(2));

                //Insiro o Aluno no Arraylist
                listaAluno.add(objAluno);
            }

            //Criando um adaptador
            ArrayAdapter<Aluno> objAdapter =
                    new ArrayAdapter<Aluno>(
                            ListaActivity.this,
                            android.R.layout.simple_list_item_1,
                            listaAluno
                            );

            //Adiciono a lista adaptada no listview
            lista.setAdapter(objAdapter);
        }
        catch(Exception erro)
        {
            Toast.makeText(ListaActivity.this,
                    erro.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}//ListaActivity