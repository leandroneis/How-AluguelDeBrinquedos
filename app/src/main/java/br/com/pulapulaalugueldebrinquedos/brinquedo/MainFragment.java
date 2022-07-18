package br.com.pulapulaalugueldebrinquedos.brinquedo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.pulapulaalugueldebrinquedos.R;


public class MainFragment extends Fragment {

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.brinquedo_fragment_main, container, false);

        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_brinquedo, new ListarFragment()).commit();
        }
        Button btnAdicionar = v.findViewById(R.id.button_adicionar_brinquedo);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_brinquedo, new AdicionarFragment()).commit();
            }
        });

        Button btnListar = v.findViewById(R.id.button_listar_brinquedo);
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_brinquedo, new ListarFragment()).commit();
            }
        });

        return v;
    }
}