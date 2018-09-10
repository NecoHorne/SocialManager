package com.necohorne.socialmanager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.necohorne.socialmanager.Dialog.ResendVerificationDialog;
import com.necohorne.socialmanager.Dialog.ResetPasswordDialog;
import com.necohorne.socialmanager.R;
import com.necohorne.socialmanager.Interfaces.MyDialogCloseListener;

import io.fabric.sdk.android.Fabric;

import static android.text.TextUtils.isEmpty;

public class LoginActivity extends AppCompatActivity implements MyDialogCloseListener {

    private EditText email;
    private EditText password;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Intent mLoggedInIntent;
    public static boolean isActivityRunning;
    private ProgressBar mProgressBar;
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        emailLoginSetup();
        mProgressBar = findViewById(R.id.email_progressbar);
        mLoggedInIntent = new Intent( LoginActivity.this, MainActivity.class );
        setupFirebaseAuth();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
        isActivityRunning = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
        isActivityRunning = false;
    }

    //------------LOGIN SETUP------------//

    private void setupFirebaseAuth(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in
                    if (user.isEmailVerified()) {
                        Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_LONG).show();
                        startActivity( mLoggedInIntent );
                        finish();
                    }else {
                        Toast.makeText( LoginActivity.this, "Please Verify your email address", Toast.LENGTH_SHORT ).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }else {
                    Log.d( TAG, "onAuthStateChanged: not signed in");
                }
            }
        };
    }

    private void emailLoginSetup(){

        email = (EditText) findViewById(R.id.login_activity_email_edit_text);
        password = (EditText) findViewById( R.id.login_activity_password_edit_text);
        Button loginButton = (Button) findViewById( R.id.login_activity_login_button );
        loginButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "login button clicked");
                mProgressBar.setVisibility(View.VISIBLE);
                emailLogin();
            }
        } );

        TextView registerNewAccount = (TextView) findViewById( R.id.login_activity_register_new );
        registerNewAccount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "create button clicked");
                createNewAccount();
            }
        } );

        TextView forgotPassword = (TextView) findViewById( R.id.login_forgot_password );
        forgotPassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "forgot button clicked");
                forgotPass();
            }
        } );

        TextView resendVerification = (TextView) findViewById( R.id.login_resend_verification );
        resendVerification.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "resend button clicked");
                resendVerEmail();
            }
        } );
    }

    private void forgotPass() {
        ResetPasswordDialog dialog = new ResetPasswordDialog();
        dialog.show( getFragmentManager(),"activity_reset_password");
    }

    private void resendVerEmail() {
        ResendVerificationDialog dialog = new ResendVerificationDialog();
        // Auth state listener started each time the resend verification tried to sign in with credential and effectively signing out the user.
        // this issue was resolved by first removing the auth listener, doing the resend then using the interface of MyDialogCloseListener to add the listener back
        FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        dialog.show( getFragmentManager(), "activity_resend_verification_dialog" );
    }

    private void createNewAccount() {
        Intent newAccountIntent = new Intent(LoginActivity.this, RegisterNewAccount.class);
        startActivity(newAccountIntent);
    }

    private void emailLogin() {
        //showDialog

        if (!isEmpty(email.getText().toString())
                && !isEmpty( password.getText().toString())){
            Log.d( TAG, "Attempting to Authenticate" );

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            } );

        } else {
            Toast.makeText( LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
