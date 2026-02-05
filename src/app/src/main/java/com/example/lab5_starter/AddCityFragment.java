package com.example.lab5_starter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.lab5_starter.databinding.FragmentEditCityBinding;

import java.util.Optional;

public class AddCityFragment extends DialogFragment {
    /**
     * Listener interface for adding cities
     */
    interface AddCityDialogListener {
        /**
         * Handles adding a new city
         * @param city The city to add
         */
        void addCity(City city);
    }

    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener)context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        FragmentEditCityBinding binding = FragmentEditCityBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder.setView(binding.getRoot()).setTitle("Add a city").setNegativeButton("Cancel", null).setPositiveButton("Add", (dialog, which) -> {
            String name = Optional.ofNullable(binding.editTextCity.getText()).map(Object::toString).orElse("NULL");
            String province = Optional.ofNullable(binding.editTextProvince.getText()).map(Object::toString).orElse("NULL");
            listener.addCity(new City(name, province));
        }).create();
    }
}
