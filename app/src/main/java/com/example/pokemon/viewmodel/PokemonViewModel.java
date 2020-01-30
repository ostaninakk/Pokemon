package com.example.pokemon.viewmodel;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.pokemon.model.Pokemon;
import com.example.pokemon.repository.PokemonRepository;
import com.example.pokemon.util.Resource;


import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PokemonViewModel extends AndroidViewModel {

    private MediatorLiveData<Resource<Pokemon>> pokemonLiveData = new MediatorLiveData<>();
    private PokemonRepository repository = PokemonRepository.getInstance(getApplication());

    public PokemonViewModel(@NonNull Application application) {
        super(application);
    }

    public void searchPokemon(String name){
            pokemonLiveData.setValue(Resource.loading((Pokemon) null));

            final LiveData<Resource<Pokemon>> source = LiveDataReactiveStreams.fromPublisher(
                    repository.searchPokemon(name)
                            .onErrorReturn(new Function<Throwable, Pokemon>() {
                                @Override
                                public Pokemon apply(Throwable throwable) throws Exception {
                                    Pokemon pokemon = new Pokemon();
                                    pokemon.setId(-1);
                                    return pokemon;
                                }
                            })

                            .map(new Function<Pokemon, Resource<Pokemon>>() {
                                @Override
                                public Resource<Pokemon> apply(Pokemon pokemon) throws Exception {
                                    if(pokemon.getId() == -1){
                                        return Resource.error(null, "Something went wrong");
                                    }
                                    return Resource.success(pokemon, "");
                                }
                            })

                            .subscribeOn(Schedulers.io())
            );

            pokemonLiveData.addSource(source, new Observer<Resource<Pokemon>>() {
                @Override
                public void onChanged(Resource<Pokemon> resource) {
                    pokemonLiveData.setValue(resource);
                    pokemonLiveData.removeSource(source);
                }
            });
    }

    public LiveData<Resource<Pokemon>> getPokemon(){
        return pokemonLiveData;
    }

    public Maybe<Pokemon> getPokemonFromFavourite(int id) {
        return repository.getPokemonFromFavourite(id);
    }

    public void insertPokemonInFavourite(Pokemon pokemon) {
        repository.insertPokemonInFavourite(pokemon);
    }

    public void deletePokemonInFavourite(Pokemon pokemon) {
        repository.deletePokemonInFavourite(pokemon);
    }

    public LiveData<List<Pokemon>> getFavouritePokemons() {
        return repository.getFavouritePokemons();
    }

}

