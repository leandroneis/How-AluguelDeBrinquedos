package br.com.pulapulaalugueldebrinquedos.database;

import br.com.pulapulaalugueldebrinquedos.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import br.com.pulapulaalugueldebrinquedos.aluguel.Aluguel;
import br.com.pulapulaalugueldebrinquedos.brinquedo.Brinquedo;
import br.com.pulapulaalugueldebrinquedos.cliente.Cliente;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "aluguel_de_brinquedos";
    private static final String TABLE_CLIENTE = "cliente";
    private static final String TABLE_BRINQUEDO = "brinquedo";
    private static final String TABLE_ALUGUEL = "aluguel";

    private static final String CREATE_TABLE_CLIENTE = "CREATE TABLE " + TABLE_CLIENTE + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome_completo VARCHAR(255), " +
            "telefone VARCHAR(15), " +
            "data_de_nascimento DATE," +
            "cpf VARCHAR(14)," +
            "rua VARCHAR(255), " +
            "numero VARCHAR(10), " +
            "complemento VARCHAR(255), " +
            "bairro VARCHAR(255), " +
            "cidade VARCHAR(255), " +
            "cep VARCHAR(20), " +
            "observacao text)";

    private static final String CREATE_TABLE_BRINQUEDO = "CREATE TABLE " + TABLE_BRINQUEDO + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " nome VARCHAR(255)," +
            " estoque int, " +
            " valor real)";


    private static final String CREATE_TABLE_ALUGUEL = "CREATE TABLE " + TABLE_ALUGUEL + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_cliente INTEGER, " +
            "id_brinquedo INTEGER, " +
            "data_inicio DATE, " +
            "data_fim DATE, " +
            "CONSTRAINT fk_alguel_cliente FOREIGN KEY (id_cliente) REFERENCES cliente (id), " +
            "CONSTRAINT fk_aluguel_brinquedo FOREIGN KEY (id_brinquedo) REFERENCES brinquedo (id))";

    private static final String DROP_TABLE_CLIENTE = "DROP TABLE IF EXISTS " + TABLE_CLIENTE;
    private static final String DROP_TABLE_BRINQUEDO = "DROP TABLE IF EXISTS " + TABLE_BRINQUEDO;
    private static final String DROP_TABLE_ALUGUEL = "DROP TABLE IF EXISTS " + TABLE_ALUGUEL;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BRINQUEDO);
        db.execSQL(CREATE_TABLE_CLIENTE);
        db.execSQL(CREATE_TABLE_ALUGUEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_CLIENTE);
        db.execSQL(DROP_TABLE_BRINQUEDO);
        db.execSQL(DROP_TABLE_ALUGUEL);
        onCreate(db);
    }

    /* Início CRUD Cliente */
    public long createCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome_completo", cliente.getNomeCompleto());
        cv.put("telefone", cliente.getTelefone());
        cv.put("data_de_nascimento", cliente.getDataDeNascimento());
        cv.put("cpf", cliente.getCpf());
        cv.put("rua", cliente.getRua());
        cv.put("numero", cliente.getNumero());
        cv.put("complemento", cliente.getComplemento());
        cv.put("bairro", cliente.getBairro());
        cv.put("cidade", cliente.getCidade());
        cv.put("cep", cliente.getCep());
        cv.put("observacao", cliente.getObservacao());
        long id = db.insert(TABLE_CLIENTE, null, cv);
        db.close();
        return id;
    }

    public long updateCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome_completo", cliente.getNomeCompleto());
        cv.put("telefone", cliente.getTelefone());
        cv.put("data_de_nascimento", cliente.getDataDeNascimento());
        cv.put("cpf", cliente.getCpf());
        cv.put("rua", cliente.getRua());
        cv.put("numero", cliente.getNumero());
        cv.put("complemento", cliente.getComplemento());
        cv.put("bairro", cliente.getBairro());
        cv.put("cidade", cliente.getCidade());
        cv.put("cep", cliente.getCep());
        cv.put("observacao", cliente.getObservacao());
        long id = db.update(TABLE_CLIENTE, cv,
                "_id = ?", new String[]{String.valueOf(cliente.getId())});
        db.close();
        return id;
    }

    public long deleteCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_CLIENTE, "_id = ?",
                new String[]{String.valueOf(cliente.getId())});
        db.close();
        return id;
    }

    public void getAllCliente(Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome_completo", "cpf"};
        Cursor data = db.query(TABLE_CLIENTE, columns, null, null,
                null, null, "nome_completo");
        int[] to = {R.id.textViewIdListarCliente, R.id.textViewNomeCompletoListarCliente, R.id.textViewCpfListarCliente};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.cliente_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    public void getAllNameCliente (ArrayList<Integer> listClienteId, ArrayList<String> listClienteName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome_completo"};
        Cursor data = db.query(TABLE_CLIENTE, columns, null, null, null,
                null, "nome_completo");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listClienteId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome_completo");
            listClienteName.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    public Cliente getByIdCliente(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome_completo", "telefone", "data_de_nascimento", "cpf", "rua", "numero", "complemento", "bairro", "cidade", "cep", "observacao"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_CLIENTE, columns, "_id = ?", args,
                null, null, null);
        data.moveToFirst();
        Cliente cliente = new Cliente();

        cliente.setId(data.getInt(0));
        cliente.setNomeCompleto(data.getString(1));
        cliente.setTelefone(data.getString(2));
        cliente.setDataDeNascimento(data.getString(3));
        cliente.setCpf(data.getString(4));
        cliente.setRua(data.getString(5));
        cliente.setNumero(data.getString(6));
        cliente.setComplemento(data.getString(7));
        cliente.setBairro(data.getString(8));
        cliente.setCidade(data.getString(9));
        cliente.setCep(data.getString(10));
        cliente.setObservacao(data.getString(11));

        data.close();
        db.close();
        return cliente;
    }
    /* Fim CRUD Cliente */

    /* Inicio do CRUD do Brinquedo */

    public long createBrinquedo(Brinquedo brinquedo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", brinquedo.getNome());
        cv.put("estoque", brinquedo.getEstoque());
        cv.put("valor", brinquedo.getValor());

        long id = db.insert(TABLE_BRINQUEDO, null, cv);
        db.close();
        return id;
    }

    public long updateBrinquedo(Brinquedo brinquedo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", brinquedo.getNome());
        cv.put("estoque", brinquedo.getEstoque());
        cv.put("valor", brinquedo.getValor());
        long id = db.update(TABLE_BRINQUEDO, cv,
                "_id = ?", new String[]{String.valueOf(brinquedo.getId())});
        db.close();
        return id;
    }

    public long deleteBrinquedo(Brinquedo brinquedo) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_BRINQUEDO, "_id = ?",
                new String[]{String.valueOf(brinquedo.getId())});
        db.close();
        return id;
    }

    public void getAllBrinquedo(Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_BRINQUEDO, columns, null, null,
                null, null, "nome");
        int[] to = {R.id.textViewIdListarBrinquedo, R.id.textViewNomeListarBrinquedo};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.brinquedo_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    public void getAllNameBrinquedo (ArrayList<Integer> listBrinquedoId, ArrayList<String> listBrinquedoName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_BRINQUEDO, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listBrinquedoId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listBrinquedoName.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    public Brinquedo getByIdBrinquedo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome", "estoque", "valor"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_BRINQUEDO, columns, "_id = ?", args,
                null, null, null);
        data.moveToFirst();
        Brinquedo brinquedo = new Brinquedo();

        brinquedo.setId(data.getInt(0));
        brinquedo.setNome(data.getString(1));
        brinquedo.setEstoque(data.getInt(2));
        brinquedo.setValor(data.getDouble(3));

        data.close();
        db.close();
        return brinquedo;
    }

    /* Fim CRUD Brinquedo */

    /* Inicio CRUD Agenda */

    public long createAluguel(Aluguel aluguel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_cliente", aluguel.getId_cliente());
        cv.put("id_brinquedo", aluguel.getId_brinquedo());
        cv.put("data_inicio", aluguel.getDataInicio());
        cv.put("data_fim", aluguel.getDataFim());

        long id = db.insert(TABLE_ALUGUEL, null, cv);
        db.close();
        return id;
    }

    public long updateAluguel(Aluguel aluguel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_cliente", aluguel.getId_cliente());
        cv.put("id_brinquedo", aluguel.getId_brinquedo());
        cv.put("data_inicio", aluguel.getDataInicio());
        cv.put("data_fim", aluguel.getDataFim());
        long rows = db.update(TABLE_ALUGUEL, cv, "_id = ?", new String[]{String.valueOf(aluguel.getId())});
        db.close();
        return rows;
    }

    public long deleteAluguel(Aluguel aluguel) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rows = db.delete(TABLE_ALUGUEL, "_id = ?", new String[]{String.valueOf(aluguel.getId())});
        db.close();
        return rows;
    }

    public void getAllAluguel(Context context, ListView lv) {
        String sql = "       SELECT a._id,a.id_cliente,a.id_brinquedo,a.data_inicio,a.data_fim,cli.nome_completo, bri.nome \n" +
                "            FROM aluguel a INNER JOIN cliente cli \n" +
                "                ON a.id_cliente = cli._id \n" +
                "            INNER JOIN brinquedo bri \n" +
                "                ON a.id_brinquedo = bri._id";

        String[] columns = {"_id","id_cliente","id_brinquedo","data_inicio","data_fim","nome_completo","nome"};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(sql,null);

        int[] to = {R.id.textViewIdListarAluguel,R.id.textViewClienteAluguel,R.id.textViewBrinquedoAluguel, R.id.textViewDataInicioAluguel,R.id.textViewDataFimAluguel , R.id.textViewNomeClienteAluguel, R.id.textViewNomeBrinquedoAluguel};


        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context, R.layout.aluguel_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }



    public Aluguel getByIdAluguel(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "id_cliente", "id_brinquedo", "data_inicio", "data_fim"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_ALUGUEL, columns, "_id = ?", args, null,
                null, null);
        data.moveToFirst();
        Aluguel aluguel = new Aluguel();
        aluguel.setId(data.getInt(0));
        aluguel.setId_cliente(data.getInt(1));
        aluguel.setId_cliente(data.getInt(2));
        aluguel.setDataInicio(data.getString(3));
        aluguel.setDataFim(data.getString(4));
        data.close();
        db.close();
        return aluguel;
    }

    /* Fim CRUD Agenda */
}