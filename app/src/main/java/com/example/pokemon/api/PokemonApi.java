package com.example.pokemon.api;

import com.example.pokemon.model.Pokemon;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApi {
    @GET("pokemon/{pokemonIdOrName}")
    Flowable<Pokemon> getPokemon(@Path("pokemonIdOrName") String pokemonIdOrName);
}
