package br.com.pulapulaalugueldebrinquedos.aluguel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import br.com.pulapulaalugueldebrinquedos.R;
import br.com.pulapulaalugueldebrinquedos.database.DatabaseHelper;

/*Classe responsavel para editar um registro do cadastro de aluguel*/
public class EditarFragment extends Fragment {

    private EditText etDataInicio;
    private EditText etDataFim;
    private Spinner spCliente;
    private Spinner spBrinquedo;
    private ArrayList<Integer> listClienteId;
    private ArrayList<String> listClienteName;
    private ArrayList<Integer> listBrinquedoId;
    private ArrayList<String> listBrinquedoName;
    private DatabaseHelper databaseHelper;
    private Aluguel aluguel;

    public EditarFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.aluguel_fragment_editar, container, false);
        Bundle b = getArguments();
        int id_aluguel = b.getInt("id");

        etDataInicio= v.findViewById(R.id.editTextDataInicioAluguel);
        etDataFim= v.findViewById(R.id.editTextDataFimAluguel);
        spCliente= v.findViewById(R.id.spinnerClienteAluguel);
        spBrinquedo= v.findViewById(R.id.spinnerBrinquedoAluguel);

        databaseHelper = new DatabaseHelper(getActivity());

        listClienteId = new ArrayList<>();
        listClienteName = new ArrayList<>();
        databaseHelper.getAllNameCliente(listClienteId, listClienteName);

        listBrinquedoId = new ArrayList<>();
        listBrinquedoName = new ArrayList<>();
        databaseHelper.getAllNameBrinquedo(listBrinquedoId, listBrinquedoName);

        ArrayAdapter<String> spClienteArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listClienteName);
        spCliente.setAdapter(spClienteArrayAdapter);

        ArrayAdapter<String> spBrinquedoArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listBrinquedoName);
        spBrinquedo.setAdapter(spBrinquedoArrayAdapter);


        databaseHelper = new DatabaseHelper(getActivity());
        aluguel = databaseHelper.getByIdAluguel(id_aluguel);

        spCliente.setSelection(listClienteId.indexOf(aluguel.getId_cliente()));
        spBrinquedo.setSelection(listBrinquedoId.indexOf(aluguel.getId_brinquedo()));
        etDataInicio.setText(aluguel.getDataInicio());
        etDataFim.setText(aluguel.getDataFim());

        Button btnEditar = v.findViewById(R.id.button_editar_aluguel);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar(id_aluguel);
            }
        });

        Button btnExcluir = v.findViewById(R.id.button_excluir_aluguel);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.excluir);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        excluir(id_aluguel);
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

    /*Método responsável para validar campos obrigatórios e inlcuir o registro de aluguel que foi editado*/

    private void editar(int id) {
        if (spCliente.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione o cliente!", Toast.LENGTH_LONG).show();
        } else if (spBrinquedo.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione o brinquedo!", Toast.LENGTH_LONG).show();
        } else if (etDataInicio.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a data de início!", Toast.LENGTH_LONG).show();
        } else if (etDataFim.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a data de fim!", Toast.LENGTH_LONG).show();
        } else {
            aluguel = new Aluguel();
            aluguel.setId(id);
            String nomeCliente = spCliente.getSelectedItem().toString();
            aluguel.setId_cliente(listClienteId.get(listClienteName.indexOf(nomeCliente)));
            String nomeBrinquedo= spBrinquedo.getSelectedItem().toString();
            aluguel.setId_brinquedo(listBrinquedoId.get(listBrinquedoName.indexOf(nomeBrinquedo)));
            aluguel.setDataInicio(etDataInicio.getText().toString());
            aluguel.setDataFim(etDataFim.getText().toString());


            databaseHelper.updateAluguel(aluguel);
            Toast.makeText(getActivity(), "Aluguel atualizado", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_aluguel, new ListarFragment()).commit();
        }
    }

    /*Método responsável para excluir um registro de aluguel*/
    private void excluir(int id) {
        aluguel = new Aluguel();
        aluguel.setId(id);
        databaseHelper.deleteAluguel(aluguel);
        Toast.makeText(getActivity(), "Aluguel excluído", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_aluguel, new ListarFragment()).commit();
    }
}
