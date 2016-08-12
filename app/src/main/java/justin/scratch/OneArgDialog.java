package justin.scratch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import justin.scratch.Math.Divide;
import justin.scratch.variables.NumberVariable;

/**
 * Created by Justin on 8/10/2016.
 */
public class OneArgDialog  extends DialogFragment{
    private int selection1=0;
    public interface OneArgListener {
        public void onPositiveClick(DialogFragment dialog, Bundle s);
        public void onNegativeClick(DialogFragment dialog);
    }

    private AdapterView.OnItemSelectedListener spinnerListener1=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selection1=i;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    @Override
    public Dialog onCreateDialog(Bundle s){
        final OneArgListener listener=(OneArgListener) getArguments().get("ScriptBlock");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Set Variable");
        LayoutInflater inflater = (LayoutInflater) MainActivity.getActivity().getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.dialog_one_arg,null);
        final Spinner spinner1=(Spinner)v.findViewById(R.id.dialog_one_arg_spinner);
        ArrayList<String> variables=new ArrayList<>();
        variables.add("Choose");
        for(NumberVariable n:ScriptBlockManager.getVariables()){
            variables.add(n.getName());
        }
        spinner1.setAdapter(new ArrayAdapter(MainActivity.getActivity().getApplicationContext(),
                R.layout.spinner_item, variables) {
        });
        spinner1.setOnItemSelectedListener(spinnerListener1);
        builder.setView(v);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle s = new Bundle();
                if (selection1 > 0) {
                    s.putString("variable", ScriptBlockManager.getVariables().get(selection1 - 1).getName());
                }
                listener.onPositiveClick(OneArgDialog.this, s);
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onNegativeClick(OneArgDialog.this);
            }
        });
        return builder.create();
    }
}
