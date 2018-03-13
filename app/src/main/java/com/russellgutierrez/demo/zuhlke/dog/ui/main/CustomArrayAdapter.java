package com.russellgutierrez.demo.zuhlke.dog.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.stream.Collectors;

public class CustomArrayAdapter<T> extends ArrayAdapter<String> {

    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource);
        initializeData(objects);
    }

    private void initializeData(@NonNull List<T> objects) {
        addAll(objects.stream()
                .map(this::buildStringValue)
                .collect(Collectors.toList()));
        notifyDataSetChanged();
    }

    protected String buildStringValue(T object) {
        return object.toString();
    }
}
