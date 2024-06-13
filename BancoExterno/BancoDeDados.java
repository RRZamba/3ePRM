package com.example.sqliteexterno_19_06;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BancoDeDados extends SQLiteOpenHelper {

    //Pegar o nome da classe
    public String TAG =BancoDeDados.class.getSimpleName();
    //Numero da classe
    public int flag;
    //Nome do DB
    public static String DB_Name = "dbAluno.db";
    //context, para pegar a 'programacao' da activity
    Context meuContext;
    //Arquivo de saida
    String saidaFile = "";
    //Caminho on ficara salvo o DB
    String DB_Path;
    //Classe para fazer as operacoes do DB
    SQLiteDatabase db;

    //Metodo Construtor!!!!!
    public BancoDeDados(Context context) {
        super(context,DB_Name,null,1);

        //Inserindo o Contexto do parametro no atributo local
        this.meuContext = context;

        //Recuperando caminho do DB no cel....
        ContextWrapper objContextWrapper = new ContextWrapper(context);
        DB_Path = objContextWrapper.getFilesDir().getAbsolutePath()+"/databases/";
        //Mensagem de log
        Log.e(TAG,"BancoDeDados:DB_Path:"+DB_Path);
        //Montando o caminho completo(com o nome do DB)
        saidaFile = DB_Path + DB_Name;
        //Verificando diretorio foi criado/existe
        File objFile = new File(DB_Path);
        //Log para controle
        Log.e(TAG,"BancoDeDados"+objFile.exists());
        //Se não existir ele gera o caminho
        if(!objFile.exists())
        {
            objFile.mkdir();
        }
    }


    //Copiando o Banco de Dados
    public void copiandoDB() throws IOException
    {
        //Criando o Buffer para passar o DB dentro do APK
        byte[] buffer = new byte[1024];
        OutputStream minhaSaida = null;
        int tamanho;
        //Classe para inserir oque veido do assets
        InputStream minhaEntrada = null;
        try{
            //Pegando o banco da pasta assets
            minhaEntrada = meuContext.getAssets().open(DB_Name);
            //Transferindo do Assets para dentro do cel
            minhaSaida = new FileOutputStream(DB_Path+DB_Name);
            //percorrendo  byte por byte
            while((tamanho = minhaEntrada.read(buffer)) > 0)
            {
                minhaSaida.write(buffer,0,tamanho);
            }

            minhaSaida.close();
            minhaSaida.flush();
            minhaEntrada.close();
            //Chegou aqui, log que deu certo
            Log.e(TAG,"Banco de dados foi copiado!!!");

        }catch(IOException erro)
        {
            erro.printStackTrace();
        }

    }


    //Verificando se ja existe o DB
    public boolean checandoDB()
    {
        SQLiteDatabase verificarDB = null;
        try {
            SQLiteDatabase.openDatabase(saidaFile,null,
                              SQLiteDatabase.OPEN_READWRITE);
        }catch(SQLiteException erro)
        {
            try {
                copiandoDB();
            }catch (IOException erro1)
            {
                erro1.printStackTrace();
            }
        }
        if (verificarDB != null) {
            verificarDB.close();
        }

        //retornando se DB existe ou nao(True/False)
        return verificarDB != null? true : false;
    }


    //Abrindo o DB
    public void criandoDB()
    {
        //Verificar se BD existe
        boolean existeDB = checandoDB();
        if(existeDB){
            //Se existir nao faça nada
        }
        else
        {
            this.getReadableDatabase();
            try {
                copiandoDB();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //Met. para abrir o DB
    public void abrirDB (){
        //Caminho do banco
        String caminho = DB_Path + DB_Name;
        db = SQLiteDatabase.openDatabase(caminho,null,
                      SQLiteDatabase.OPEN_READWRITE);

        //Mensagem de Log
        Log.e(TAG,"DB aberto!: " + db.isOpen());
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
