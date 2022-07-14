package br.com.pulapulaalugueldebrinquedos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import br.com.pulapulaalugueldebrinquedos.cliente.MainFragment;

public class MenuFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_menu, container, false);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clientes:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new MainFragment()).commit();
                break;
            case R.id.menu_brinquedos:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new br.com.pulapulaalugueldebrinquedos.brinquedo.MainFragment()).commit();
                break;
            case R.id.menu_aluguel:
                System.out.println("MENU ALUGUEL-> " + R.id.menu_aluguel);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new br.com.pulapulaalugueldebrinquedos.aluguel.MainFragment()).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}