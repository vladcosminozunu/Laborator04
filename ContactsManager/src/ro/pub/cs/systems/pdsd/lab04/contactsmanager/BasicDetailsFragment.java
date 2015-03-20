package ro.pub.cs.systems.pdsd.lab04.contactsmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class BasicDetailsFragment extends Fragment {

	private ShowListener showListener = new ShowListener();
	
	private SaveListener saveListener = new SaveListener();
	
	private CancelListener cancelListener = new CancelListener();
	
	private String name, phone, email;
	
	private class CancelListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			getActivity().setResult(Activity.RESULT_CANCELED, new Intent());
			getActivity().finish();
		}
		
	}

	private class SaveListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
			intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
			
			EditText nameET = (EditText)getActivity().findViewById(R.id.name);
			name = nameET.getText().toString();
			
			EditText phoneET = (EditText)getActivity().findViewById(R.id.number);
			phone = phoneET.getText().toString();
			
			EditText emailET = (EditText)getActivity().findViewById(R.id.email);
			email = emailET.getText().toString();
			
			if (name != null) {
			  intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
			}
			if (phone != null) {
			  intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
			}
			if (email != null) {
			  intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
			}
		
			getActivity().startActivityForResult(intent, 3);
		}
		
	}
	
	private class ShowListener implements View.OnClickListener {
		
		// adaugare frame2 in ecran la apasare buton Show
		@Override
		public void onClick(View v) {
			FragmentManager fm = getActivity().getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			AdditionalDetailsFragment f2 = (AdditionalDetailsFragment)fm.findFragmentById(R.id.frame2);
			if (f2!=null) {
				ft.remove(f2);
				((Button)v).setText(getActivity().getResources().getString(R.string.Additional));
			} else {
				ft.add(R.id.frame2, new AdditionalDetailsFragment(), "F2");
				((Button)v).setText("Hide");
			}
			ft.commit();
		}
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		Button showButton = (Button)getActivity().findViewById(R.id.show);
		showButton.setOnClickListener(showListener);
		
		EditText phoneNumber = (EditText)getActivity().findViewById(R.id.number);
		phoneNumber.setText(getActivity().getIntent().getStringExtra("telefon"));
	
		Button saveButton = (Button)getActivity().findViewById(R.id.save);
		saveButton.setOnClickListener(saveListener);
		
		Button cancelButton = (Button)getActivity().findViewById(R.id.cancel);
		cancelButton.setOnClickListener(cancelListener);
	}
	
	@Override
	public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle b){
		return li.inflate(R.layout.fragment_basic_details, vg, false);
	}
}
