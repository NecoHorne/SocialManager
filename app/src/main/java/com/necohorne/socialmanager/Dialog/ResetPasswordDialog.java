package com.necohorne.socialmanager.Dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.necohorne.socialmanager.R;

import static android.text.TextUtils.isEmpty;

public class ResetPasswordDialog extends DialogFragment {

    private static final String TAG = "ResetPasswordDialog";

    private EditText email;
    private Button confirm;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_reset_password, container, false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT));

        email = (EditText) view.findViewById( R.id.resend_password_email );
        mContext = getActivity();
        confirm = (Button) view.findViewById( R.id.resend_password_confirmbutton );
        confirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty(email.getText().toString())){
                    Log.d( TAG, "OnClickListener: attempting to send reset Email" );
                    resendPassword(email.getText().toString());
                    getDialog().dismiss();
                }
            }
        });
        return view;
    }

    private void resendPassword(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d( TAG, "resendPassword: reset Email sent successfully" );
                    Toast.makeText( mContext, "Password Reset Link Sent to Email", Toast.LENGTH_SHORT ).show();
                }else{
                    Log.d( TAG, "No User is Associated with that Email");
                    Toast.makeText( mContext, "No User is Associated with that Email", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }
}
