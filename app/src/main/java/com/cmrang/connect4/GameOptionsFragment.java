package com.cmrang.connect4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GameOptionsFragment extends Fragment {
    private RadioGroup difficultyRadioGroup;

    // Override onCreateView instead of onCreate
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_game_options, container, false);

        // Instantiate SharedPreferences to store the selected level
        SharedPreferences prefs = requireActivity().getSharedPreferences("GamePrefs", getContext().MODE_PRIVATE);

        // Get reference to the RadioGroup
        difficultyRadioGroup = view.findViewById(R.id.radioGroup_Difficulty);

        // Load the selected level from SharedPreferences
        int selectedLevel = prefs.getInt("selectedLevel", R.id.radioButtonEasy);  // Default to 'easyRadioButton'

        // Set the corresponding radio button as checked
        ((RadioButton) view.findViewById(selectedLevel)).setChecked(true);

        // Add a listener to the radio group to handle level selection
        difficultyRadioGroup.setOnCheckedChangeListener((group, checkedId) -> onLevelSelected(checkedId, prefs));

        // Return the inflated view
        return view;
    }

    // Method to handle radio button selection and save the level in SharedPreferences
    private void onLevelSelected(int checkedId, SharedPreferences prefs) {
        // Save the selected level ID into SharedPreferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("selectedLevel", checkedId);  // Store the ID of the selected radio button
        editor.apply();  // Apply the changes
    }
}
