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

    public AdicionarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cliente_fragment_adicionar, container, false);

        etNomeCompleto = v.findViewById(R.id.editText_nome_completo);
        etTelefone = v.findViewById(R.id.editText_telefone);
        etDataDeNascimento = v.findViewById(R.id.editText_data_de_nascimento);
        etCpf = v.findViewById(R.id.editText_cpf);
        etRua = v.findViewById(R.id.editText_rua);
        etNumero = v.findViewById(R.id.editText_numero);
        etComplemento = v.findViewById(R.id.editText_complemento);
        etBairro = v.findViewById(R.id.editText_bairro);
        etCidade = v.findViewById(R.id.editText_cidade);
        etCep = v.findViewById(R.id.editText_cpf);
        etObservacao = v.findViewById(R.id.editText_observacao);



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
            cliente.setNomeCompleto(etNomeCompleto.getText().toString());
            cliente.setTelefone(etTelefone.getText().toString());
            cliente.setDataDeNascimento(etDataDeNascimento.getText().toString());
            cliente.setCpf(etCpf.getText().toString());
            cliente.setRua(etRua.getText().toString());
            cliente.setNumero(etNumero.getText().toString().equals("") ? "" : etNumero.getText().toString());
            cliente.setComplemento(etComplemento.getText().toString().equals("") ? "" : etComplemento.getText().toString());
            cliente.setBairro(etBairro.getText().toString());
            cliente.setCidade(etCidade.getText().toString());
            cliente.setCep(etCep.getText().toString());
            cliente.setObservacao(etObservacao.getText().toString().equals("") ? "" : etObservacao.getText().toString());

            databaseHelper.createCliente(cliente);
            Toast.makeText(getActivity(), "Cliente salvo com sucesso!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_cliente, new ListarFragment()).commit();
        }
    }
}