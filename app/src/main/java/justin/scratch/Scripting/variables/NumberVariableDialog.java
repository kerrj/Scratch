package justin.scratch.Scripting.variables;

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
 * Created by Justin on 8/7/2016.
 */
public class NumberVariableDialog extends DialogFragment {

    public interface NumberVariableDialogListener{
        public void onPositiveClick(DialogFragment dialog,Bundle s);
        public void onNegativeClick(DialogFragment dialog,Bundle s);
    }

    @Override
    public Dialog onCreateDialog(Bundle s){
        final NumberVariableDialogListener listener=(NumberVariableDialogListener)getArguments().get("NumberVariable");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Variable");
        LayoutInflater inflater = (LayoutInflater) MainActivity.getActivity().getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.dialog_number,null);
        final EditText name=(EditText)v.findViewById(R.id.name);
        final Bundle b=new Bundle();
        builder.setView(v);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                b.putString("Name",name.getText().toString());
                listener.onPositiveClick(NumberVariableDialog.this,b);
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onNegativeClick(NumberVariableDialog.this,b);
            }
        });
        return builder.create();
    }
}
