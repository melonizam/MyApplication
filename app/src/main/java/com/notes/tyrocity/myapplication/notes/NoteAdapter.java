package com.notes.tyrocity.myapplication.notes;
 
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog; 
import android.support.v7.widget.RecyclerView; 
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
 
import com.google.firebase.database.DatabaseReference; 
import com.google.firebase.database.FirebaseDatabase;
import com.notes.tyrocity.myapplication.R;

import org.ocpsoft.prettytime.PrettyTime; 
 
import java.util.Calendar;
import java.util.List;
 
/* 
Assignment InClass10 
NoteAdapter.java 
Sai Yesaswy Mylavarapu, Harish Pendyala, Febin Zachariah, Danjie Gu 
 */ 
 
 
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder>{ 
 
    public List<Note> NoteList;
    public Context mContext;
    Intent playActivityIntent;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref = dref.child("Notes");
 
 
//    mp3Interface activity; 
 
    public NoteAdapter(Context context, List<Note> NoteList) {
        this.NoteList = NoteList;
        this.mContext = context;
    } 
 
    @Override 
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_layout, parent, false);
        return new MyViewHolder(itemView);
 
    } 
 
    @Override 
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Note note = NoteList.get(position);
 
        holder.textViewNote.setText(note.getTaskNote());
        holder.textViewPriority.setText(note.getPriority());
 
        PrettyTime p  = new PrettyTime();
        holder.textViewTime.setText(p.format(note.getCreatedTime()));
 
        holder.cbStatus.setChecked(note.getStatus().equals("completed"));
 
        holder.cbStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override 
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    AlertDialog dialog = new AlertDialog.Builder(mContext)
                            .setTitle("Are you really want mark is as pending??")
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
 
                                    note.setStatus("pending");
                                    note.setCreatedTime(Calendar.getInstance().getTime());
 
                                    String id = note.getId();
                                    String idnote = id.substring(id.indexOf("Notes/-")+6).trim();
 
                                    childref.child(idnote).setValue(note);
 
                                    Log.d("Demo8",idnote);
                                } 
                            }) 
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    holder.cbStatus.setChecked(true);
                                } 
                            }).create();
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override 
                        public void onShow(DialogInterface dialog) {
                        } 
                    }); 
                    dialog.show();
 
                } 
                else { 
                    note.setStatus("completed");
                    note.setCreatedTime(Calendar.getInstance().getTime());
                    String id = note.getId();
                    String idnote = id.substring(id.indexOf("Notes/-")+6).trim();
 
                    childref.child(idnote).setValue(note);
                } 
            } 
        }); 
 
    } 
 
    @Override 
    public int getItemCount() { 
        return NoteList.size();
    } 
 
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView textViewNote, textViewPriority,textViewTime;
        public CheckBox cbStatus;
 
        public MyViewHolder(View itemView) {
            super(itemView);
            textViewNote = (TextView)itemView.findViewById(R.id.tvNoteText);
            textViewPriority = (TextView)itemView.findViewById(R.id.tvPriority);
            cbStatus = (CheckBox)itemView.findViewById(R.id.cbStatus);
            textViewTime = (TextView)itemView.findViewById(R.id.tvUpdatedOn);
 
            itemView.setOnLongClickListener(this);
        } 
 
        public boolean onLongClick(View view) {
            final int position = getAdapterPosition();
            AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("Are you really want to delete the task??")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Note note = NoteList.get(position);
 
                                String id = note.getId();
                                String idnote = id.substring(id.indexOf("Notes/-")+6).trim();
 
                                childref.child(idnote).removeValue();
                            } 
                        }) 
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing 
                            } 
                        }).create();
                dialog.show();
 
            return false; 
        } 
    } 
 
//    static public interface mp3Interface{ 
//        public void setupmp3(String mp3url,double duration); 
//        public void closemp3(); 
//    } 
} 
 