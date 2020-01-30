package com.example.pokemon.ui.main;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pokemon.R;


public class MenuFragment extends Fragment implements View.OnClickListener {
    private NavController navController;
    private Button searchButton;
    private Button randomButton;
    private Button favouriteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        searchButton = view.findViewById(R.id.search_pokemon_button);
        randomButton = view.findViewById(R.id.search_random_pokemon_button);
        favouriteButton = view.findViewById(R.id.favourite_pokemons_button);

        searchButton.setOnClickListener(this);
        randomButton.setOnClickListener(this);
        favouriteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case (R.id.search_pokemon_button):
                    navController.navigate(R.id.action_menuFragment_to_searchPokemonFragment);
                    break;
                case (R.id.search_random_pokemon_button):
                    navController.navigate(R.id.action_menuFragment_to_randomPokemonFragment);
                    break;
                case (R.id.favourite_pokemons_button):
                    navController.navigate(R.id.action_menuFragment_to_favouritePokemonFragment);
                    break;
            }
        }

    }
}
