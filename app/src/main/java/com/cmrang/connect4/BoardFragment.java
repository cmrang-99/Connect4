package com.cmrang.connect4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.graphics.drawable.Drawable;


public class BoardFragment extends Fragment {

    private ConnectFourGame game;
    private GridLayout mGrid;

    public BoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_board, container, false);

        mGrid = rootView.findViewById(R.id.gridLayout); // Assuming your GridLayout's ID is gridLayout

        // Initialize the game
        game = new ConnectFourGame();

        if (savedInstanceState == null) {
            game.startGame();  // Start a new game if there's no saved instance state
        } else {
            String gameState = savedInstanceState.getString("gameState");
            game.loadGameState(gameState);  // Load saved game state
        }

        // Set up button click listeners for the grid
        for (int i = 0; i < mGrid.getChildCount(); i++) {
            Button button = (Button) mGrid.getChildAt(i);
            button.setOnClickListener(this::onButtonClick);
        }

        // Update the UI with the current game state
        setDisc();

        return rootView;
    }

    private void onButtonClick(View view) {
        int buttonIndex = mGrid.indexOfChild(view);  // Assuming mGrid is your GridLayout
        int row = buttonIndex / ConnectFourGame.COL;
        int col = buttonIndex % ConnectFourGame.COL;

        game.dropDisc(col);  // Drop the disc at the calculated column
        setDisc();  // Update the UI with the new game state

        if (game.isGameOver()) {
            // Show a message and reset the game if necessary
            Toast.makeText(getContext(), "Player " + (game.getCurrentPlayer() == ConnectFourGame.BLUE ? "Blue" : "Red") + " wins!", Toast.LENGTH_SHORT).show();
            game.resetGame();
        }
    }

    public void setDisc() {
        for (int row = 0; row < ConnectFourGame.ROW; row++) {
            for (int col = 0; col < ConnectFourGame.COL; col++) {
                Button gridButton = (Button) mGrid.getChildAt(row * ConnectFourGame.COL + col);  // Get the button for the grid
                int disc = game.getDisc(row, col);

                // Set the button's background based on the disc
                Drawable drawable;
                switch (disc) {
                    case ConnectFourGame.BLUE:
                        drawable = ContextCompat.getDrawable(getActivity(), R.drawable.circle_blue);
                        break;
                    case ConnectFourGame.RED:
                        drawable = ContextCompat.getDrawable(getActivity(), R.drawable.circle_red);
                        break;
                    default:
                        drawable = ContextCompat.getDrawable(getActivity(), R.drawable.circle_white);
                        break;
                }
                gridButton.setBackground(DrawableCompat.wrap(drawable));  // Set the background for the button
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("gameState", game.getGameState());
    }
}
