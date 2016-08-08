package justin.scratch.variables;

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
public class NumberVariable extends ScriptBlock implements NumberVariableDialog.NumberVariableDialogListener,Parcelable{
    private String name;
    private long value=0;

    public NumberVariable(){
        makeDialog();
    }

    @Override
    public void makeDialog(){
        Bundle b=new Bundle();
        b.putParcelable("NumberVariable",this);
        NumberVariableDialog dialog=new NumberVariableDialog();
        dialog.setArguments(b);
        dialog.show(MainActivity.getActivity().getFragmentManager(),"numbervariable");
    }

    public long getValue(){
        return value;
    }
    public String getName(){
        return name;
    }

    @Override
    public void onPositiveClick(DialogFragment dialog,Bundle b) {
        Log.d(b.getString("Name"), b.getString("Value"));
        name=b.getString("Name");
        try {
            value = Long.decode(b.getString("Value"));
        }catch(NumberFormatException e){
            value=0;
            e.printStackTrace();
        }
    }

    @Override
    public void onNegativeClick(DialogFragment dialog,Bundle b) {
        ScriptBlockManager.removeVariable(this);
        name=null;
        value=0;
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
