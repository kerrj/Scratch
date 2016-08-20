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

import justin.scratch.Scripting.MainActivity;
import justin.scratch.R;

/**
 * Created by Justin on 8/9/2016.
 */
public class RepeatDialog extends DialogFragment{
    public interface RepeatDialogListener{
        public void onPositiveClick(DialogFragment dialog,Bundle s);
        public void onNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle s){
        final RepeatDialogListener listener=(RepeatDialogListener)getArguments().get("ScriptBlock");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) MainActivity.getActivity().getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.dialog_repeat,null);
        final EditText editText=(EditText)v.findViewById(R.id.dialog_repeat_editText);
        builder.setTitle("Repeat for?");
        builder.setView(v);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle s=new Bundle();
                s.putString("repeatFor",editText.getText().toString());
                listener.onPositiveClick(RepeatDialog.this,s);
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onNegativeClick(RepeatDialog.this);
            }
        });
        return builder.create();
    }
}
