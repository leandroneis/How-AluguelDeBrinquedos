package br.com.pulapulaalugueldebrinquedos.brinquedo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.pulapulaalugueldebrinquedos.R;
import br.com.pulapulaalugueldebrinquedos.database.DatabaseHelper;

/*Classe responsavel para editar um registro do cadastro de brinquedo*/
public class EditarFragment extends Fragment {

    private EditText etNome;
    private EditText etEstoque;
    private EditText etValor;
    private DatabaseHelper databaseHelper;
    private Brinquedo brinquedo;

    public EditarFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.brinquedo_fragment_editar, container, false);
        Bundle b = getArguments();
        int id_brinquedo = b.getInt("id");

        etNome = v.findViewById(R.id.editText_nome_brinquedo);
        etEstoque = v.findViewById(R.id.editText_estoque_brinquedo);
        etValor = v.findViewById(R.id.editText_valor_brinquedo);

        databaseHelper = new DatabaseHelper(getActivity());
        brinquedo = databaseHelper.getByIdBrinquedo(id_brinquedo);

        etNome.setText(brinquedo.getNome());
        etEstoque.setText(String.valueOf(brinquedo.getEstoque()));
        etValor.setText(String.valueOf(brinquedo.getValor()));


        Button btnEditar = v.findViewById(R.id.button_editar_brinquedo);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar(id_brinquedo);
            }
        });
        Button btnExcluir = v.findViewById(R.id.button_excluir_brinquedo);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.excluir);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        excluir(id_brinquedo);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return v;
    }
    /*Método responsável para validar campos obrigatórios e inlcuir o registro do brinquedo que foi editado*/
    private void editar(int id) {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome do brinquedo", Toast.LENGTH_LONG).show();
        } else if (etEstoque.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a quantidade em estoque do brinquedo", Toast.LENGTH_LONG).show();
        } else if (etValor.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o valor do brinquedo", Toast.LENGTH_LONG).show();
        } else {
            brinquedo = new Brinquedo();
            brinquedo.setId(id);
            brinquedo.setNome(etNome.getText() != null ?  etNome.getText().toString() : "" );
            brinquedo.setEstoque(etEstoque.getText() != null ? Integer.parseInt(etEstoque.getText().toString()) : 0);
            brinquedo.setValor(etValor.getText() != null ? Double.parseDouble(etValor.getText().toString()) : 0);


            databaseHelper.updateBrinquedo(brinquedo);
            Toast.makeText(getActivity(), "Brinquedo atualizado", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_brinquedo, new ListarFragment()).commit();
        }
    }
    /*Método responsável para excluir um registro do brinquedo */
    private void excluir(int id) {
        brinquedo = new Brinquedo();
        brinquedo.setId(id);
        databaseHelper.deleteBrinquedo(brinquedo);
        Toast.makeText(getActivity(), "Brinquedo excluído", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_brinquedo, new ListarFragment()).commit();
    }
}
