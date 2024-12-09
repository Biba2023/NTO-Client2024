package ru.myitschool.work.ui.main_screen;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import ru.myitschool.work.R;
import ru.myitschool.work.databinding.FragmentMainScreenBinding;

public class MainScreenFragment extends Fragment {

    private MainScreenViewModel mViewModel;

    public static MainScreenFragment newInstance() {
        return new MainScreenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainScreenViewModel.class);
        // TODO: Use the ViewModel
        TextView fullname = getView().findViewById(R.id.fullname);
        TextView position = getView().findViewById(R.id.position);
        TextView lastEntry = getView().findViewById(R.id.lastEntry);
        TextView error = getView().findViewById(R.id.error);
        ImageButton refresh = getView().findViewById(R.id.refresh);
        ImageButton logout = getView().findViewById(R.id.logout);
        ImageButton scan = getView().findViewById(R.id.scan);
        error.setVisibility(View.GONE);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.go_to_qr);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = requireActivity().getSharedPreferences("hasLogIn", Context.MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.putBoolean("hasLogIn", false);
                e.apply();
                NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.go_to_login);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}


