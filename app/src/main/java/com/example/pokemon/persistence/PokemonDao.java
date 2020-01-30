package com.example.pokemon.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokemon.model.Pokemon;

import java.util.List;

import io.reactivex.Maybe;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PokemonDao {

    @Insert(onConflict = REPLACE)
    void insertPokemon(Pokemon pokemon);

    @Query("SELECT * FROM pokemon WHERE id = :pokemon_id")
    Maybe<Pokemon> getPokemon(int pokemon_id);

    @Query("SELECT * FROM pokemon")
    LiveData<List<Pokemon>> getPokemons();

    @Delete
    void deletePokemon(Pokemon pokemon);
}