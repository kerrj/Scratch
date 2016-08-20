package justin.scratch.Scripting.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import justin.scratch.R;
import justin.scratch.Scripting.MainActivity;

/**
 * Created by Justin on 8/13/2016.
 */
public class NewProjectDialog extends DialogFragment {
    public interface NewProjectListener {
        public void onPositiveClick(DialogFragment dialog, Bundle s);
        public void onNegativeClick(DialogFragment dialog);
    }


    @Override
    public Dialog onCreateDialog(Bundle s){
        final NewProjectListener listener=(NewProjectListener) getArguments().get("Activity");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("New Project");
        LayoutInflater inflater = (LayoutInflater) MainActivity.getActivity().getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.dialog_project,null);
        final EditText editText=(EditText)v.findViewById(R.id.dialog_project_editText);
        builder.setView(v);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle s = new Bundle();
                s.putString("projectName",editText.getText().toString());
                listener.onPositiveClick(NewProjectDialog.this, s);
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onNegativeClick(NewProjectDialog.this);
            }
        });
        return builder.create();
    }
}
