package com.example.pokemon.ui.main;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokemon.R;
import com.example.pokemon.ui.main.adapter.FavouritePokemonRecyclerViewAdapter;
import com.example.pokemon.model.Pokemon;
import com.example.pokemon.viewmodel.PokemonViewModel;

import java.util.List;


public class FavouritePokemonFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavouritePokemonRecyclerViewAdapter adapter;
    private PokemonViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite_pokemon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);
        subscribeObservers();

        recyclerView = view.findViewById(R.id.recycler_view);
        initRecyclerView();
    }

    private void subscribeObservers() {
        viewModel.getFavouritePokemons()
                .observe(getViewLifecycleOwner(), new Observer<List<Pokemon>>() {
                    @Override
                    public void onChanged(List<Pokemon> pokemons) {
                        adapter.setPokemons(pokemons);
                    }
                });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FavouritePokemonRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


}
