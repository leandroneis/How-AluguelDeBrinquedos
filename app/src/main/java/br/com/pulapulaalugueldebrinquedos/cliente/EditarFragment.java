package br.com.pulapulaalugueldebrinquedos.cliente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import br.com.pulapulaalugueldebrinquedos.R;
import br.com.pulapulaalugueldebrinquedos.database.DatabaseHelper;
import br.com.pulapulaalugueldebrinquedos.webservice.DadosEndereco;
import br.com.pulapulaalugueldebrinquedos.webservice.RetornarEnderecoPeloCep;
/*Classe responsavel para editar um registro do cadastro de cliente*/
public class EditarFragment extends Fragment {

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
    private DatabaseHelper databaseHelper;
    private Cliente cliente;

    public EditarFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View v = inflater.inflate(R.layout.cliente_fragment_editar, container, false);
        Bundle b = getArguments();
        int id_cliente = b.getInt("id");

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


        databaseHelper = new DatabaseHelper(getActivity());
        cliente = databaseHelper.getByIdCliente(id_cliente);

        etNomeCompleto.setText(cliente.getNomeCompleto());
        etTelefone.setText(cliente.getTelefone());
        etDataDeNascimento.setText(cliente.getDataDeNascimento() != null ? cliente.getDataDeNascimento().toString() : "");
        etCpf.setText(cliente.getCpf());
        etRua.setText(cliente.getRua());
        etNumero.setText(cliente.getNumero());
        etComplemento.setText(cliente.getComplemento());
        etBairro.setText(cliente.getBairro());
        etCidade.setText(cliente.getCidade());
        etCep.setText(cliente.getCep());
        etObservacao.setText(cliente.getObservacao());

        etCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!cliente.getCep().equals(etCep.getText().toString()))
                    try {
                        DadosEndereco dadosEndereco = new RetornarEnderecoPeloCep(etCep.getText().toString()).execute().get();
                        etRua.setText(dadosEndereco.getLogradouro());
                        etBairro.setText(dadosEndereco.getBairro());
                        etCidade.setText(dadosEndereco.getLocalidade());

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Button btnEditar = v.findViewById(R.id.button_editar_cliente);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar(id_cliente);
            }
        });
        Button btnExcluir = v.findViewById(R.id.button_excluir_cliente);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.excluir);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        excluir(id_cliente);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}});
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return v;
    }

    /*Método responsável para validar campos obrigatórios e inlcuir o registro do cliente que foi editado*/
    private void editar(int id) {
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
            cliente = new Cliente();
            cliente.setId(id);
            cliente.setNomeCompleto(etNomeCompleto.getText() != null ?  etNomeCompleto.getText().toString() : "" );
            cliente.setTelefone(etTelefone.getText() != null ? etTelefone.getText().toString() : "");
            cliente.setDataDeNascimento(etDataDeNascimento.getText().toString());
            cliente.setCpf(etCpf.getText() != null ? etCpf.getText().toString() : "");
            cliente.setRua( etRua.getText() != null ?  etRua.getText().toString() : "");
            cliente.setNumero(etNumero.getText() != null ? etNumero.getText().toString()  : "");
            cliente.setComplemento(etComplemento.getText() != null ? etComplemento.getText().toString() : "");
            cliente.setBairro(etBairro.getText() != null ? etBairro.getText().toString() : "");
            cliente.setCidade(etCidade.getText() != null ? etCidade.getText().toString() : "");
            cliente.setCep(etCep.getText() != null ? etCep.getText().toString() : "");
            cliente.setObservacao(etObservacao.getText() != null ?etObservacao.getText().toString() : "");

            databaseHelper.updateCliente(cliente);
            Toast.makeText(getActivity(), "Cliente atualizado", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_cliente, new ListarFragment()).commit();
        }
    }
    /*Método responsável para excluir um registro do cliente */
    private void excluir(int id) {
        cliente = new Cliente();
        cliente.setId(id);
        databaseHelper.deleteCliente(cliente);
        Toast.makeText(getActivity(), "Cliente excluído", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_cliente, new ListarFragment()).commit();
    }
}