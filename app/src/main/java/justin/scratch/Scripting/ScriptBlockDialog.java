package justin.scratch.Scripting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Justin on 8/8/2016.
 */
public class ScriptBlockDialog extends DialogFragment {
    public interface ScriptBlockDialogListener{
        public void onPositiveClick(DialogFragment dialog);
        public void onNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle s){
        final ScriptBlockDialogListener listener=(ScriptBlockDialogListener)getArguments().get("ScriptBlock");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString("Type"));
        builder.setMessage("Delete this block?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onPositiveClick(ScriptBlockDialog.this);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onNegativeClick(ScriptBlockDialog.this);
            }
        });
        return builder.create();
    }
}
