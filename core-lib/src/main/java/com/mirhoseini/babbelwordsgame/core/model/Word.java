
package com.mirhoseini.babbelwordsgame.core.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohsen on 07/06/16.
 */
@AutoValue
public abstract class Word {

    static Word create(String textEng, String textSpa) {
        return new AutoValue_Word(textEng, textSpa);
    }

    @SerializedName("text_eng")
    public abstract String getTextEng();

    @SerializedName("text_spa")
    public abstract String getTextSpa();

    public static TypeAdapter<Word> typeAdapter(Gson gson) {
        return new AutoValue_Word.GsonTypeAdapter(gson);
    }

}
