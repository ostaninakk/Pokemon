package com.example.pokemon.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemon.R;
import com.example.pokemon.model.Pokemon;

import java.util.List;

public class FavouritePokemonRecyclerViewAdapter extends RecyclerView.Adapter<FavouritePokemonRecyclerViewAdapter.PokemonViewHolder> {
    private List<Pokemon> pokemonList;


    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        final Pokemon pokemon = pokemonList.get(position);
        holder.name.setText(pokemon.getName());
        holder.base_experience.setText(String.valueOf(pokemon.getBaseExperience()));
        holder.height.setText(String.valueOf(pokemon.getHeight()));
        holder.weight.setText(String.valueOf(pokemon.getWeight()));
    }

    @Override
    public int getItemCount() {
        if (pokemonList!= null) {
            return pokemonList.size();
        }
        return 0;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemonList = pokemons;
        notifyDataSetChanged();
    }

    static class PokemonViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView base_experience;
        TextView height;
        TextView weight;

        PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            base_experience = itemView.findViewById(R.id.base_experience);
            height = itemView.findViewById(R.id.height);
            weight = itemView.findViewById(R.id.weight);

        }
    }
}