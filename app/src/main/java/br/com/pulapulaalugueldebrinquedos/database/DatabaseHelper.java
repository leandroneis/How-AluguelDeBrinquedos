package br.com.pulapulaalugueldebrinquedos.database;

import br.com.pulapulaalugueldebrinquedos.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import br.com.pulapulaalugueldebrinquedos.clientes.Cliente;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "aluguel_de_brinquedos";
    private static final String TABLE_CLIENTE = "cliente";

    private static final String CREATE_TABLE_CLIENTE = "CREATE TABLE " + TABLE_CLIENTE + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome_completo VARCHAR(255), " +
            "telefone VARCHAR(15), " +
            "data_de_nascimento DATE," +
            "cpf VARCHAR(14),"+
            "rua VARCHAR(255), " +
            "numero VARCHAR(10), " +
            "complemento VARCHAR(255), " +
            "bairro VARCHAR(255), " +
            "cidade VARCHAR(255), " +
            "cep VARCHAR(20), " +
            "observacao text);";




    private static final String DROP_TABLE_CLIENTE = "DROP TABLE IF EXISTS " + TABLE_CLIENTE;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CLIENTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_CLIENTE);
        onCreate(db);
    }

    /* Início CRUD Cliente */
    public long createCliente (Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome_completo",cliente.getNomeCompleto());
        cv.put("telefone",cliente.getTelefone());
        cv.put("data_de_nascimento",cliente.getDataDeNascimento());
        cv.put("cpf",cliente.getCep());
        cv.put("rua",cliente.getRua() );
        cv.put("numero",cliente.getNumero() );
        cv.put("complemento",cliente.getComplemento() );
        cv.put("bairro",cliente.getBairro() );
        cv.put("cidade",cliente.getCidade());
        cv.put("cep",cliente.getCep() );
        cv.put("observacao",cliente.getObservacao());
        long id = db.insert(TABLE_CLIENTE, null, cv);
        db.close();
        return id;
    }
    public long updateCliente (Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome_completo",cliente.getNomeCompleto());
        cv.put("telefone",cliente.getTelefone());
        cv.put("data_de_nascimento",cliente.getDataDeNascimento());
        cv.put("cpf",cliente.getCep());
        cv.put("rua",cliente.getRua() );
        cv.put("numero",cliente.getNumero() );
        cv.put("complemento",cliente.getComplemento() );
        cv.put("bairro",cliente.getBairro() );
        cv.put("cidade",cliente.getCidade());
        cv.put("cep",cliente.getCep() );
        cv.put("observacao",cliente.getObservacao());
        long id = db.update(TABLE_CLIENTE, cv,
                "_id = ?", new String[]{String.valueOf(cliente.getId())});
        db.close();
        return id;
    }
    public long deleteCliente (Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_CLIENTE, "_id = ?",
                new String[]{String.valueOf(cliente.getId())});
        db.close();
        return id;
    }
    public void getAllCliente (Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome_completo","cpf"};
        Cursor data = db.query(TABLE_CLIENTE, columns, null, null,
                null, null, "nome_completo");
        int[] to = {R.id.textViewIdListarCliente, R.id.textViewNomeCompletoListarCliente,
                R.id.textViewCpfListarMedico};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.cliente_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }
    public Cliente getByIdCliente (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome_completo","telefone","data_de_nascimento","cpf","rua","numero","complemento","bairro","cidade","cep","observacao"};
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
}