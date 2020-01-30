package com.example.pokemon.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;

import com.example.pokemon.api.ServiceGenerator;
import com.example.pokemon.model.Pokemon;
import com.example.pokemon.persistence.PokemonDao;
import com.example.pokemon.persistence.PokemonDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Flowable;
import io.reactivex.Maybe;


public class PokemonRepository {
    private static PokemonRepository instance;
    private PokemonDao pokemonDao;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public static PokemonRepository getInstance(Context context){
        if(instance == null){
            instance = new PokemonRepository(context);
        }
        return instance;
    }


    private PokemonRepository(Context context) {
        pokemonDao = PokemonDatabase.getInstance(context).getRecipeDao();
    }

    public Flowable<Pokemon> searchPokemon(final String name){
        return ServiceGenerator.getPokemonApi().getPokemon(name);
    }

    public Maybe<Pokemon> getPokemonFromFavourite(int id) {
        return pokemonDao.getPokemon(id);
    }

    public void insertPokemonInFavourite(final Pokemon pokemon) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                pokemonDao.insertPokemon(pokemon);
            }
        });
    }

    public void deletePokemonInFavourite(final Pokemon pokemon) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                pokemonDao.deletePokemon(pokemon);
            }
        });
    }

    public LiveData<List<Pokemon>> getFavouritePokemons() {
        return pokemonDao.getPokemons();
    }
}

