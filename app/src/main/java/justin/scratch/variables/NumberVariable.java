package justin.scratch.variables;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import justin.scratch.MainActivity;
import justin.scratch.ScriptBlock;
import justin.scratch.ScriptBlockManager;

/**
 * Created by Justin on 8/7/2016.
 */
@SuppressLint("ParcelCreator")
public class NumberVariable extends ScriptBlock implements NumberVariableDialog.NumberVariableDialogListener,Parcelable{
    private String name;

    public NumberVariable(){
        makeDialog();
    }

    @Override
    public void makeDialog(){
        Bundle b=new Bundle();
        b.putParcelable("NumberVariable",this);
        NumberVariableDialog dialog=new NumberVariableDialog();
        dialog.setArguments(b);
        dialog.show(MainActivity.getActivity().getFragmentManager(), "numbervariable");
    }

    public String getName(){
        return name;
    }
    @Override
    public String getType(){
        return "NumberVariable";
    }

    @Override
    public void onPositiveClick(DialogFragment dialog,Bundle b) {
        name=b.getString("Name");
    }

    @Override
    public void onNegativeClick(DialogFragment dialog,Bundle b) {
        ScriptBlockManager.removeVariable(this);
        name=null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this);
    }
}
