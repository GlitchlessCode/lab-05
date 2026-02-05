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

public class EditCityFragment extends DialogFragment {
    /**
     * Listener interface for deleting cities
     */
    interface ModifyCityDialogListener {
        /**
         * Handles deleting a city
         * @param city The city to update
         */
        void updateCity(City city, String name, String province);

        /**
         * Handles deleting a city
         * @param position The index of the city to delete
         */
        void deleteCity(int position);
    }

    private ModifyCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ModifyCityDialogListener) {
            listener = (ModifyCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    /**
     * Constant argument name for position argument
     */
    private final static String ARG_POSITION_NAME = "position";
    /**
     * Constant argument name for city argument
     */
    private final static String ARG_CITY_NAME = "city";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        FragmentEditCityBinding binding = FragmentEditCityBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        Bundle args = getArguments();
        if (args == null)
            return builder.setTitle("Encountered an error...").setPositiveButton("Ok", null).create();

        int position = args.getInt(ARG_POSITION_NAME);
        City city = (City)(args.getSerializable(ARG_CITY_NAME));

        if(city != null) {
            binding.editTextCity.setText(city.getName());
            binding.editTextProvince.setText(city.getProvince());
        }

        return builder.setView(binding.getRoot()).setTitle("Edit a city").setNegativeButton("Cancel", null).setPositiveButton("Confirm", (dialog, which) -> {
            String name = Optional.ofNullable(binding.editTextCity.getText()).map(Object::toString).orElse("NULL");
            String province = Optional.ofNullable(binding.editTextProvince.getText()).map(Object::toString).orElse("NULL");
            listener.updateCity(city, name, province);
        }).setNeutralButton("Delete", (dialog, which) -> {
            listener.deleteCity(position);
        }).create();
    }

    public static EditCityFragment newInstance(City city, int position) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY_NAME, city);
        args.putInt(ARG_POSITION_NAME, position);
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
