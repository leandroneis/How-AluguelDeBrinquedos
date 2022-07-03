package br.com.pulapulaalugueldebrinquedos.brinquedos;

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


public class AdicionarFragment extends Fragment {

    private EditText etNome;
    private EditText etEstoque;
    private EditText etValor;
    
    public AdicionarFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.brinquedo_fragment_adicionar, container, false);

        etNome = v.findViewById(R.id.editText_nome_brinquedo);
        etEstoque = v.findViewById(R.id.editText_estoque_brinquedo);
        etValor = v.findViewById(R.id.editText_valor_brinquedo);

        Button btnSalvar = v.findViewById(R.id.button_salvar_brinquedo);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar() {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome do brinquedo", Toast.LENGTH_LONG).show();
        } else if (etEstoque.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a quantidade em estoque do brinquedo", Toast.LENGTH_LONG).show();
        } else if (etValor.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o valor do brinquedo", Toast.LENGTH_LONG).show();
        } else {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
            Brinquedo brinquedo = new Brinquedo();
            brinquedo.setNome(etNome.getText() != null ?  etNome.getText().toString() : "" );
            brinquedo.setEstoque(etEstoque.getText() != null ? Integer.parseInt(etEstoque.getText().toString()) : 0);
            brinquedo.setValor(etValor.getText() != null ? Double.parseDouble(etValor.getText().toString()) : 0);


            databaseHelper.createBrinquedo(brinquedo);
            Toast.makeText(getActivity(), "Brinquedo salvo com sucesso!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_brinquedo, new ListarFragment()).commit();
        }
    }
}