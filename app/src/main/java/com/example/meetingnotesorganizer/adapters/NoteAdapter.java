package com.example.meetingnotesorganizer.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetingnotesorganizer.R;
import com.example.meetingnotesorganizer.ViewNoteActivity;
import com.example.meetingnotesorganizer.data.Note;
import com.example.meetingnotesorganizer.helpers.DatabaseHelper;
import com.example.meetingnotesorganizer.helpers.Utils;
import com.example.meetingnotesorganizer.services.NoteService;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> localDataSet;
    private Context context;
    private FragmentManager supportFragmentManager;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText, dateText, timeText;
        private final Button viewBtn, deleteBtn;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            titleText = view.findViewById(R.id.titleText);
            dateText = view.findViewById(R.id.dateText);
            timeText = view.findViewById(R.id.timeText);

            viewBtn = view.findViewById(R.id.viewBtn);
            deleteBtn = view.findViewById(R.id.deleteBtn);
        }

        public TextView getTitleText() {
            return titleText;
        }

        public TextView getDateText() {
            return dateText;
        }

        public TextView getTimeText() {
            return timeText;
        }

        public Button getViewBtn() {
            return viewBtn;
        }

        public Button getDeleteBtn() {
            return deleteBtn;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public NoteAdapter(List<Note> dataSet, Context context, FragmentManager supportFragmentManager) {
        localDataSet = dataSet;
        this.context = context;
        this.supportFragmentManager = supportFragmentManager;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_notes, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        // viewHolder.getTextView().setText(localDataSet[position]);
        Note note = localDataSet.get(position);
        viewHolder.getTitleText().setText("Title: " + note.getTitle());
        viewHolder.getDateText().setText("Date: " + note.getDate());
        viewHolder.getTimeText().setText("Time: " + note.getTime());
        viewHolder.getViewBtn().setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewNoteActivity.class);
            intent.putExtra("noteId", note.getId());
            context.startActivity(intent);
        });
        viewHolder.getDeleteBtn().setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    NoteService.delete(note.getId());
                    Utils.longToast(note.getTitle() + " note has been deleted!", context);
                    updateDataSet();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancels the dialog.
                }
            });
            builder.setMessage("Are you sure you want to delete this note?");

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    public void updateDataSet() {
        List<Note> updatedNotes = DatabaseHelper.getNoteBank().getAll();
        updateDataSet(updatedNotes);
    }

    public void updateDataSet(List<Note> updatedDataSet) {
        localDataSet.clear();
        localDataSet.addAll(updatedDataSet);
        notifyDataSetChanged();
    }

    public void reverseDataSet() {
        List<Note> reversed = new ArrayList<>();
        for (int i = localDataSet.size() - 1; i >= 0; i--) {
            reversed.add(localDataSet.get(i));
        }
        updateDataSet(reversed);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
