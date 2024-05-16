package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Atributos
    EditText txtNome,txtCurso,txtId;
    Button btnSalvar, btnAtualizar, btnSelecionar;

    //Classe SQLiteDataBase -> responsavel por todas as
    //operacoes do DB
    SQLiteDatabase bcoDadosShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Elementos da tela
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnAtualizar = (Button) findViewById(R.id.btnAtualizar);
        btnSelecionar = (Button) findViewById(R.id.btnSelecionar);

        //Chamando o metodo criarDB
        criarDB();

        //Evento do botao gravar
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gravar();
            }
        });

        //Evento do botao atualizar
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizar();
            }
        });

        //Evento do botao Selecionar
        btnSelecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarUnico();
            }
        });


    }//onCreate()




    //Metodo para Salvar valores
    public void gravar()
    {
        //Iniciando os elementos da tela
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtCurso = (EditText) findViewById(R.id.txtCurso);

        //Variavel String para armazenar a query de INSERT
        String sql = "";

        try
        {
            //Criando a query
            sql = "INSERT INTO Alunos(Nome,Curso)VALUES('"
                    +txtNome.getText().toString()+"','"
                    +txtCurso.getText().toString()+"')";

            //Executando a !@#$%  da query
            bcoDadosShow.execSQL(sql);

            //Mens. de sucesso
            Toast.makeText(MainActivity.this,
                    "Aluno inserido com sucesso!",
                    Toast.LENGTH_LONG).show();

            //Limpando os campos
            txtCurso.setText("");
            txtNome.setText("");

        }
        catch(Exception erro)
        {
            Toast.makeText(MainActivity.this,
                       "Erro ao inserir dados \n"
                               +erro.getMessage()
                               +"\n"+sql,
                    Toast.LENGTH_LONG).show();
        }








    }//gravar()

    //Metodo para criar/abrir o DB
    public void criarDB()
    {
        // 1) Variavel para armazenar a query
        String sql;

        // 2) Tentando criar/abir o DB
        try
        {
            // 3) Criando a tabela, caso a mesma nao exista
            sql = "CREATE TABLE IF NOT EXISTS Alunos(" +
                    "Matricula INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Nome TEXT," +
                    "Curso TEXT)";

            // 4) Criando/abrindo o DB
            bcoDadosShow = openOrCreateDatabase("ETEC",
                                                MODE_PRIVATE,
                                                null);

            // 5) Executando a query
            bcoDadosShow.execSQL(sql);

            // 6) Executando mens de sucesso!!!! :D
            Toast.makeText(MainActivity.this,
                    "Sistema Carregado com Sucesso!!!",
                       Toast.LENGTH_LONG).show();
        }//try
        catch(Exception erro)
        {
            //Se ocorrer um erro... Toast na tela!!!!
            Toast.makeText(MainActivity.this,
                            erro.getMessage(),
                            Toast.LENGTH_LONG).show();
        }//catch
    }//criarDB()

    //Metodo para Atualizar valores
    public void atualizar()
    {
        // 1) Iniciando os elementos
        txtCurso = (EditText) findViewById(R.id.txtCurso);
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtId = (EditText) findViewById(R.id.txtId);

        // 2) Variavel para montar a query
        String sql = "";

        try
        {
            // 3) Montando a query de update
            sql = "UPDATE Alunos SET " +
                    "Nome = '"+txtNome.getText().toString() +
                    "',Curso = '"+txtCurso.getText().toString()+"' " +
                    "WHERE Matricula = "+txtId.getText().toString();


            // 4) Executando a Query
            bcoDadosShow.execSQL(sql);

            // 5) Mens de sucesso
            Toast.makeText(MainActivity.this,
                    "Atualizado com Sucesso!",
                    Toast.LENGTH_LONG).show();

            // 6) Limpando os campos
            txtNome.setText("");
            txtCurso.setText("");
            txtId.setText("");
        }
        catch (Exception erro)
        {
            Toast.makeText(MainActivity.this,
                            "Erro ao Atualizar registro!"+
                            erro.getMessage(),
                        Toast.LENGTH_LONG).show();
        }


    }

    //Metodo para selecionar um unico registro
    public void selecionarUnico()
    {
        try
        {
            // 1) Iniciando o elemento da tela
            txtId = (EditText) findViewById(R.id.txtId);

            // 2) Montando a query de select
            String sql = "SELECT * FROM Alunos WHERE Nome ='" +
                         txtId.getText().toString() + "'";

            // 3) Criando um cursor para armazenar os dados
            //durante a execucao
            Cursor objCursor;

            // 4) Executando a query e salvando os dados no cursor
            objCursor = bcoDadosShow.rawQuery(sql,null);

            /*   "exemplificando" o cursor
                  -    Matricula(0)   Nome(1)    Curso(2)
                  0    1              Pedro        ADM
                  1    2              Claudio      ADM
                  2    3              Ines         ADM
                  3    4              Maria        ADM


             */


            String mens = "";

            // 6) Percorrendo Cursor linha a linha
            while(objCursor.moveToNext())
            {
                //Monto a classe aluno
                mens = objCursor.getInt(0) +
                        objCursor.getString(1)+
                      objCursor.getString(2);
            }


            Toast.makeText(MainActivity.this,
                            mens,
                           Toast.LENGTH_LONG).show();



        }
        catch(Exception erro)
        {
            Toast.makeText(MainActivity.this,
                            erro.getMessage(),
                      Toast.LENGTH_LONG).show();

        }
    }

}//MainActivity