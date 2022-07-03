package br.com.pulapulaalugueldebrinquedos.clientes;

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

    private EditText etNomeCompleto;
    private EditText etTelefone;
    private EditText etDataDeNascimento;
    private EditText etCpf;
    private EditText etRua;
    private EditText etNumero;
    private EditText etComplemento;
    private EditText etBairro;
    private EditText etCidade;
    private EditText etCep;
    private EditText etObservacao;

    public AdicionarFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cliente_fragment_adicionar, container, false);

        etNomeCompleto = v.findViewById(R.id.editText_nome_completo_cliente);
        etTelefone = v.findViewById(R.id.editText_telefone_cliente);
        etDataDeNascimento = v.findViewById(R.id.editText_data_de_nascimento_cliente);
        etCpf = v.findViewById(R.id.editText_cpf_cliente);
        etRua = v.findViewById(R.id.editText_rua_cliente);
        etNumero = v.findViewById(R.id.editText_numero_cliente);
        etComplemento = v.findViewById(R.id.editText_complemento_cliente);
        etBairro = v.findViewById(R.id.editText_bairro_cliente);
        etCidade = v.findViewById(R.id.editText_cidade_cliente);
        etCep = v.findViewById(R.id.editText_cep_cliente);
        etObservacao = v.findViewById(R.id.editText_observacao_cliente);

        Button btnSalvar = v.findViewById(R.id.button_salvar_cliente);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar() {
        if (etNomeCompleto.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome completo do cliente", Toast.LENGTH_LONG).show();
        } else if (etTelefone.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o número do telefone para contato do cliente", Toast.LENGTH_LONG).show();
        } else if (etDataDeNascimento.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a data de nascimento do cliente", Toast.LENGTH_LONG).show();
        } else if (etCpf.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o cpf do cliente", Toast.LENGTH_LONG).show();
        } else if (etRua.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a rua do cliente", Toast.LENGTH_LONG).show();
        } else if (etBairro.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o bairro do cliente", Toast.LENGTH_LONG).show();
        } else if (etCidade.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a cidade do cliente", Toast.LENGTH_LONG).show();
        } else if (etCep.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o cep do cliente", Toast.LENGTH_LONG).show();

        } else {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
            Cliente cliente = new Cliente();
            cliente.setNomeCompleto(etNomeCompleto.getText() != null ?  etNomeCompleto.getText().toString() : "" );
            cliente.setTelefone(etTelefone.getText() != null ? etTelefone.getText().toString() : "");
            cliente.setDataDeNascimento(etDataDeNascimento.getText() != null ? etDataDeNascimento.getText().toString() : "");
            cliente.setCpf(etCpf.getText() != null ? etCpf.getText().toString() : "");
            cliente.setRua( etRua.getText() != null ?  etRua.getText().toString() : "");
            cliente.setNumero(etNumero.getText() != null ? etNumero.getText().toString()  : "");
            cliente.setComplemento(etComplemento.getText() != null ? etComplemento.getText().toString() : "");
            cliente.setBairro(etBairro.getText() != null ? etBairro.getText().toString() : "");
            cliente.setCidade(etCidade.getText() != null ? etCidade.getText().toString() : "");
            cliente.setCep(etCep.getText() != null ? etCep.getText().toString() : "");
            cliente.setObservacao(etObservacao.getText() != null ?etObservacao.getText().toString() : "");

            databaseHelper.createCliente(cliente);
            Toast.makeText(getActivity(), "Cliente salvo com sucesso!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_cliente, new ListarFragment()).commit();
        }
    }
}