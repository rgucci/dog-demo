package com.russellgutierrez.demo.zuhlke.dog.data.api.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;
import java.util.Map;

@AutoValue
public abstract class ListAllBreedsResponse implements Response {
    public abstract Map<String, List<String>> message();

    public static TypeAdapter<ListAllBreedsResponse> typeAdapter(Gson gson) {
        return new AutoValue_ListAllBreedsResponse.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_ListAllBreedsResponse.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder status(String status);

        public abstract Builder message(Map<String, List<String>> message);

        public abstract ListAllBreedsResponse build();
    }
}
