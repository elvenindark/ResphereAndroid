package com.resphere.android.vista;

/*import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;*/
import org.json.JSONArray;
import org.json.JSONObject;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.group.GroupService;
import com.resphere.android.util.Preferencias;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"test@liferay.com", "test" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	
	private Preferencias preferencias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		
		preferencias = new Preferencias();
		preferencias.init(getBaseContext());
		if(Preferencias.getFirstUse()){
			Toast.makeText(getApplicationContext(), "Preparando para usar por primera vez", Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent(LoginActivity.this, ConfiguracionActivity.class);	 		
			startActivity(intent);
		}

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin(getBaseContext());
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin(getBaseContext());
					}
				});
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(Preferencias.getFirstUse()){
			Toast.makeText(getApplicationContext(), "Preparando para usar por primera vez", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(LoginActivity.this, ConfiguracionActivity.class);	 		
			startActivity(intent);
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin(Context context) {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() <= 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask(context);
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {			
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		
		private Context context;
		
		UserLoginTask(Context context){
			this.context = context;
		}
		
		@Override		
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			
			if(getLogin(context)){
			// TODO: register the new account here.
				Intent intent = new Intent(LoginActivity.this, InicioActivity.class);       		
				startActivity(intent);
				return true;
			}else{
				dummyCredentials();
				return false;
			}
		}
		
		public void dummyCredentials(){
			mEmailView.setText(DUMMY_CREDENTIALS[0]);
			mPasswordView.setText(DUMMY_CREDENTIALS[1]);
		}
		
		public Boolean getLogin(Context context){
						
			if(preferencias.getHost()!=null){
				String server = "http://" + preferencias.getServer() + ":" + "8000";
				preferencias.setServer(server);
				preferencias.setLogin(mEmailView.getText().toString());
				preferencias.setPassword(mPasswordView.getText().toString());
				Session session = Preferencias.getSession();
				if(session!=null&&!session.getUsername().equals("")){
					try {
						Long groupid = getGuestGroupId(session);
						Log.d("group id", String.valueOf(groupid));
						if(groupid == -1){
							preferencias.setInternetAllow(false);
							return false;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}
					Log.d("username", session.getUsername());
					preferencias.setInternetAllow(true);
					return true;
				}	
			}else{
				//Toast.makeText(context, "Servidor no encontrado, configure preferencias", Toast.LENGTH_SHORT).show();
				Log.d("server", "not found");
				Intent intent = new Intent(LoginActivity.this, ConfiguracionActivity.class);        		
         		startActivity(intent);
				return false;
			}				
			return false;
		}
		
		protected long getGuestGroupId(Session session) throws Exception {
			long groupId = -1;

			GroupService groupService = new GroupService(session);
			
			JSONArray groups = groupService.getUserSites();
			
			for (int i = 0; i < groups.length(); i++) {
				JSONObject group = groups.getJSONObject(i);

				String name = group.getString("name");
				
				if (!name.equals("Guest")) {
					continue;
				}

				groupId = group.getLong("groupId");
			}
			
			if (groupId == -1) {
				throw new Exception("No se puede encontrar el grupo id");				
			}
			
			return groupId;
		}


		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				finish();
			} else {
				mPasswordView.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
