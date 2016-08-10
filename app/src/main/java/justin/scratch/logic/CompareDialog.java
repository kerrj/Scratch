package justin.scratch.logic;

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
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

import justin.scratch.MainActivity;
import justin.scratch.R;
import justin.scratch.ScriptBlockManager;
import justin.scratch.variables.NumberVariable;

/**
 * Created by Justin on 8/9/2016.
 */
public class CompareDialog extends DialogFragment {
    private int selection1=0;
    private int selection2=0;
    public interface CompareDialogListener{
        public void onPositiveClick(DialogFragment dialog,Bundle s);
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
    private AdapterView.OnItemSelectedListener spinnerListener2=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selection2=i;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    @Override
    public Dialog onCreateDialog(Bundle s){
        final CompareDialogListener listener=(CompareDialogListener)getArguments().get("ScriptBlock");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString("Set Arguments"));
        LayoutInflater inflater = (LayoutInflater) MainActivity.getActivity().getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.dialog_compare,null);
        final EditText value1=(EditText)v.findViewById(R.id.valueIn1);
        final EditText value2=(EditText)v.findViewById(R.id.valueIn2);
        final Spinner spinner1=(Spinner)v.findViewById(R.id.spinner1);
        ArrayList<String> variables=new ArrayList<>();
        variables.add("Choose");
        for(NumberVariable n:ScriptBlockManager.getVariables()){
            variables.add(n.getName());
        }
        spinner1.setAdapter(new ArrayAdapter(MainActivity.getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, variables) {
        });
        spinner1.setOnItemSelectedListener(spinnerListener1);
        final Spinner spinner2=(Spinner)v.findViewById(R.id.spinner2);
        spinner2.setAdapter(new ArrayAdapter(MainActivity.getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,variables) {
        });
        spinner2.setOnItemSelectedListener(spinnerListener2);
        builder.setView(v);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle s = new Bundle();
                if (selection1 > 0) {
                    s.putString("variableArg1", ScriptBlockManager.getVariables().get(selection1 - 1).getName());
                }
                if (selection2 > 0) {
                    s.putString("variableArg2", ScriptBlockManager.getVariables().get(selection2 - 1).getName());
                }
                try {
                    s.putLong("valueArg1", Long.decode(value1.getText().toString()));
                } catch (NumberFormatException e) {

                }
                try {
                    s.putLong("valueArg2", Long.decode(value2.getText().toString()));
                } catch (NumberFormatException e) {

                }
                listener.onPositiveClick(CompareDialog.this, s);
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onNegativeClick(CompareDialog.this);
            }
        });
        return builder.create();
    }
}
