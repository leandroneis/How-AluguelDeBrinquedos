package br.com.pulapulaalugueldebrinquedos.brinquedo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import br.com.pulapulaalugueldebrinquedos.R;
import br.com.pulapulaalugueldebrinquedos.database.DatabaseHelper;

/*Classe responsavel por listar os registros do cadastro do brinquedo*/
public class ListarFragment extends Fragment {

    public ListarFragment() { }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.brinquedo_fragment_listar, container, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        ListView lv = v.findViewById(R.id.list_view_listar_brinquedos);
        databaseHelper.getAllBrinquedo(getActivity(), lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tvId = view.findViewById(R.id.textViewIdListarBrinquedo);
                Bundle b = new Bundle();
                b.putInt("id", Integer.parseInt(tvId.getText().toString()));

                EditarFragment editar = new EditarFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                editar.setArguments(b);
                ft.replace(R.id.frame_brinquedo, editar).commit();
            }
        });

        return v;
    }
}