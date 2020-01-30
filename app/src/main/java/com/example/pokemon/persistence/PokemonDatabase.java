package com.example.pokemon.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pokemon.model.Pokemon;

@Database(entities = {Pokemon.class}, version = 1)
public abstract class PokemonDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "pokemon_db";

    private static PokemonDatabase instance;

    public static PokemonDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    PokemonDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract PokemonDao getRecipeDao();

}

