package justin.scratch.Scripting.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import justin.scratch.R;
import justin.scratch.Scripting.MainActivity;

/**
 * Created by Justin on 8/15/2016.
 */
public class NewSpriteDialog extends DialogFragment {
    public interface NewSpriteListener {
        public void onPositiveSpriteClick(DialogFragment dialog, Bundle s);
        public void onNegativeSpriteClick(DialogFragment dialog);
    }


    @Override
    public Dialog onCreateDialog(Bundle s){
        final NewSpriteListener listener=(NewSpriteListener) getArguments().get("Activity");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("New Sprite");
        LayoutInflater inflater = (LayoutInflater) MainActivity.getActivity().getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.dialog_new_sprite,null);
        final EditText editText=(EditText)v.findViewById(R.id.dialog_new_sprite_editText);
        builder.setView(v);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle s = new Bundle();
                s.putString("spriteName",editText.getText().toString());
                listener.onPositiveSpriteClick(NewSpriteDialog.this, s);
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onNegativeSpriteClick(NewSpriteDialog.this);
            }
        });
        return builder.create();
    }
}
