package com.necohorne.socialmanager.Dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.necohorne.socialmanager.R;
import com.necohorne.socialmanager.Interfaces.MyDialogCloseListener;

import static android.text.TextUtils.isEmpty;

public class ResendVerificationDialog extends DialogFragment {

    private static final String TAG = "ResendVerification";

    //UI Widgets
    private EditText mEmail;
    private EditText mPassword;
    private TextView mCancel;
    private TextView mSend;

    private Context mContext;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_resend_verification_dialog, container, false);
        mContext = getActivity();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT));

        setupUI();

        return mView;
    }

    private void setupUI(){
        mEmail = (EditText) mView.findViewById( R.id.resend_verification_email );
        mPassword = (EditText) mView.findViewById( R.id.resend_verification_password);
        mCancel = (TextView) mView.findViewById(R.id.dialog_cancel);
        mCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        } );

        mSend = (TextView) mView.findViewById( R.id.dialog_send );
        mSend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationEmail();
            }
        } );
    }

    private void resendVerificationEmail() {
        Log.d(TAG, "resend attempt");

        if (!isEmpty(mEmail.getText().toString())
                && !isEmpty( mPassword.getText().toString())){
            authenticateAndResend( mEmail.getText().toString(), mPassword.getText().toString());
        }else {
            Toast.makeText(mContext, "all fields must be filled out", Toast.LENGTH_SHORT).show();
        }

    }

    private void authenticateAndResend(String email, String password){
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        FirebaseAuth.getInstance()
                .signInWithCredential( credential)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "onComplete: re-authenticate success.");
                    sendVerificationEmail();
                    FirebaseAuth.getInstance().signOut();
                    getDialog().dismiss();
                }
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "Invalid Credentials \nReset your Password and try again", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        } );

    }

    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(mContext, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(mContext, "couldn't send email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity();
        if(activity instanceof MyDialogCloseListener)
            ((MyDialogCloseListener)activity).handleDialogClose(dialog);
    }
}
