package com.catalog.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.catalog.activities.R;
import com.catalog.core.AppPreferences;
import com.catalog.core.AsyncTaskFactory;
import com.catalog.core.CatalogApplication;
import com.catalog.helper.Constants;
import com.google.analytics.tracking.android.MapBuilder;

public class ChangePasswordDialog extends Dialog {

	private static final String CLASS_NAME = Constants.ChangePasswordDialog;
	private EditText newPass1;
	private EditText newPass2;
	private Button btnSave;
	private AsyncTaskFactory asyncTaskFactory;

	public ChangePasswordDialog(final Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		setContentView(R.layout.dialog_change_password);

		asyncTaskFactory = AsyncTaskFactory.getInstance(AppPreferences
				.getInstance(context).isIpExternal());
		newPass1 = (EditText) findViewById(R.id.et_pass1);
		newPass2 = (EditText) findViewById(R.id.et_pass2);
		btnSave = (Button) findViewById(R.id.btn_save);

		btnSave.setEnabled(false);

		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				asyncTaskFactory.getTask(context, CLASS_NAME, "").execute(
						newPass1.getText().toString());

				CatalogApplication.getGaTracker().send(
						MapBuilder
								.createEvent(Constants.UI_ACTION,
										Constants.UI_ACTION_CHANGE_PASSWORD,
										null, null).build());

				dismiss();
			}
		});

		newPass1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(final CharSequence s, final int start,
					final int before, final int count) {

			}

			@Override
			public void beforeTextChanged(final CharSequence s,
					final int start, final int count, final int after) {

			}

			@Override
			public void afterTextChanged(final Editable s) {
				if (s.length() == 0) {
					btnSave.setEnabled(false);
					newPass1.setError(context.getResources().getString(
							R.string.missmatch_password));
					newPass2.setError(context.getResources().getString(
							R.string.missmatch_password));

				} else {
					if (newPass1.getText().toString()
							.equals(newPass2.getText().toString())
							&& s.length() > 5) {
						btnSave.setEnabled(true);
						newPass1.setError(null);
						newPass2.setError(null);
					}
				}
			}
		});

		newPass2.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(final CharSequence s, final int start,
					final int before, final int count) {

			}

			@Override
			public void beforeTextChanged(final CharSequence s,
					final int start, final int count, final int after) {

			}

			@Override
			public void afterTextChanged(final Editable s) {
				if (s.length() == 0
						|| !s.toString().equals(newPass1.getText().toString())) {
					btnSave.setEnabled(false);
					newPass1.setError(context.getResources().getString(
							R.string.missmatch_password));
					newPass2.setError(context.getResources().getString(
							R.string.missmatch_password));
				} else {
					if (s.toString().equals(newPass1.getText().toString())
							&& s.length() > 5) {
						btnSave.setEnabled(true);
						newPass1.setError(null);
						newPass2.setError(null);
					}
				}

			}
		});
	}

}
