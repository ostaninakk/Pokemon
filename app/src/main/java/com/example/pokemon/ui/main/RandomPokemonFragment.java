package com.example.pokemon.ui.main;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokemon.R;
import com.example.pokemon.model.Pokemon;
import com.example.pokemon.util.Resource;
import com.example.pokemon.viewmodel.PokemonViewModel;


import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;


public class RandomPokemonFragment extends Fragment {
    private static final int POKEMON_MAX_COUNT = 807;
    private static final String TAG = "POKEMON_LOG";

    private PokemonViewModel viewModel;
    private Button randomButton;
    private ProgressBar progressBar;
    private LinearLayout pokemonDataSection;
    private TextView errorSection;
    private TextView pokemonName;
    private TextView pokemonBaseExperience;
    private TextView pokemonHeight;
    private TextView pokemonWeight;
    private CheckBox favourite;
    private Pokemon pokemon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_random_pokemon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        initView(view);

        viewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);
        subscribeObservers();

        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRandomSearch();
            }
        });

        favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if (pokemon != null) {
                    setFavourite(isChecked);
                }
            }
        });
    }

    private void initView(View view) {
        randomButton = view.findViewById(R.id.random_button);
        progressBar = view.findViewById(R.id.progress_bar);
        pokemonDataSection = view.findViewById(R.id.pokemon_data_section);
        errorSection = view.findViewById(R.id.error_not_found);
        pokemonName = view.findViewById(R.id.pokemon_name);
        pokemonBaseExperience = view.findViewById(R.id.pokemon_base_experience);
        pokemonHeight = view.findViewById(R.id.pokemon_height);
        pokemonWeight = view.findViewById(R.id.pokemon_weight);
        favourite = view.findViewById(R.id.checkbox_favourite);
    }

    private void subscribeObservers() {
        viewModel.getPokemon().removeObservers(getViewLifecycleOwner());
        viewModel.getPokemon().observe(getViewLifecycleOwner(), new Observer<Resource<Pokemon>>() {
            @Override
            public void onChanged(Resource<Pokemon> resource) {
                if(resource != null){
                    switch (resource.status){

                        case LOADING:{
                            showError(false);
                            showPokemonDataSection(false);
                            showProgressBar(true);
                            break;
                        }

                        case SUCCESS:{
                            showProgressBar(false);
                            showError(false);
                            showPokemonDataSection(true);

                            pokemon = resource.data;
                            if (pokemon != null) {
                                setPokemonData(pokemon);
                            }
                            break;
                        }

                        case ERROR:{
                            showProgressBar(false);
                            showPokemonDataSection(false);
                            showError(true);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void showProgressBar(boolean visibility){
        progressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    private void showPokemonDataSection(boolean visibility){
        pokemonDataSection.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    private void showError(boolean visibility){
        errorSection.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    private void setPokemonData(Pokemon pokemon) {
        setPokemonFavourite(pokemon);
        pokemonName.setText(pokemon.getName());
        pokemonBaseExperience.setText(String.valueOf(pokemon.getBaseExperience()));
        pokemonHeight.setText(String.valueOf(pokemon.getHeight()));
        pokemonWeight.setText(String.valueOf(pokemon.getWeight()));
    }

    private void setPokemonFavourite(Pokemon pokemon) {
        viewModel.getPokemonFromFavourite(pokemon.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableMaybeObserver<Pokemon>() {
                    @Override
                    public void onSuccess(Pokemon pokemon) {
                        favourite.setChecked(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Search pokemon in favourite in database:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        favourite.setChecked(false);
                    }
                });
    }

    private void startRandomSearch() {
        String name = String.valueOf(new Random().nextInt(POKEMON_MAX_COUNT));
        if (name.isEmpty()) {
            Toast.makeText(getContext(), getString(R.string.name_empty), Toast.LENGTH_LONG).show();
            return;
        }
        viewModel.searchPokemon(name);
    }

    private void setFavourite(Boolean isFavourite) {
        if (isFavourite) {
            viewModel.insertPokemonInFavourite(pokemon);
        } else {
            viewModel.deletePokemonInFavourite(pokemon);
        }
    }
}
