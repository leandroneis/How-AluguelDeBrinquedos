package br.com.pulapulaalugueldebrinquedos.aluguel;

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

public class AdicionarFragment extends Fragment {

    private EditText etDataInicio;
    private EditText etDataFim;
    private Spinner spCliente;
    private Spinner spBrinquedo;
    private ArrayList<Integer> listClienteId;
    private ArrayList<String> listClienteName;
    private ArrayList<Integer> listBrinquedoId;
    private ArrayList<String> listBrinquedoName;
    private DatabaseHelper databaseHelper;

    public AdicionarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.aluguel_fragment_adicionar, container, false);

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

        Button btnSalvar = v.findViewById(R.id.button_salvar_brinquedo);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar () {
        if (spCliente.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione o cliente!", Toast.LENGTH_LONG).show();
        } else if (spBrinquedo.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione o brinquedo!", Toast.LENGTH_LONG).show();
        } else if (etDataInicio.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a data de início!", Toast.LENGTH_LONG).show();
        } else if (etDataFim.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a data de fim!", Toast.LENGTH_LONG).show();
        } else {
            Aluguel aluguel = new Aluguel();
            String nomeCliente = spCliente.getSelectedItem().toString();
            aluguel.setId_cliente(listClienteId.get(listClienteName.indexOf(nomeCliente)));
            String nomeBrinquedo = spBrinquedo.getSelectedItem().toString();
            aluguel.setId_brinquedo(listBrinquedoId.get(listBrinquedoName.indexOf(nomeBrinquedo)));
            aluguel.setDataInicio(etDataInicio.getText().toString());
            aluguel.setDataFim(etDataFim.getText().toString());

            databaseHelper.createAluguel(aluguel);
            Toast.makeText(getActivity(), "Aluguel salvo!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_aluguel, new ListarFragment()).commit();
        }
    }

}