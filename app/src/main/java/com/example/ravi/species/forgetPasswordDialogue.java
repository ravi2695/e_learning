package com.example.ravi.species;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPasswordDialogue extends AppCompatDialogFragment {

    private EditText forgetEmail;
    private DialogueListener dialogueListener;
    FirebaseAuth sendingPassword;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        sendingPassword=FirebaseAuth.getInstance();

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        final View view=inflater.inflate(R.layout.dialogue_layout,null);

        builder.setView(view)
                .setTitle("forget password")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        forgetEmail=view.findViewById(R.id.forgetpasswordarea);
                        if(forgetEmail.getText().toString().trim().isEmpty())
                        {
                            forgetEmail.setError("this field is required");
                            forgetEmail.requestFocus();
                            return;
                        }
                        dialogueListener.applyChanges(forgetEmail.getText().toString().trim());


                    }

                });


        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogueListener= (DialogueListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" don't forget the implementation");
        }
    }

    public interface DialogueListener
    {
        void applyChanges(String getForgotEmail);
    }
}
