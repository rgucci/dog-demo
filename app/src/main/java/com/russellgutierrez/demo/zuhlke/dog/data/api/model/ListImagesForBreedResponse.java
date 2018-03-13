package com.russellgutierrez.demo.zuhlke.dog.data.api.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class ListImagesForBreedResponse implements Response{
    public abstract List<String> message();

    public static TypeAdapter<ListImagesForBreedResponse> typeAdapter(Gson gson) {
        return new AutoValue_ListImagesForBreedResponse.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_ListImagesForBreedResponse.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder status(String status);

        public abstract Builder message(List<String> message);

        public abstract ListImagesForBreedResponse build();
    }
}
