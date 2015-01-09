package name.duzenko.farfaraway;

import name.duzenko.farfaraway.simulation.Universe;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class SettingActivity extends Activity {
	
	RadioGroup preset;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		preset = (RadioGroup) findViewById(R.id.radioGroup1);
	}
	
	public void onStartClick(View view) {
		int radioButtonID = preset.getCheckedRadioButtonId();
		View radioButton = preset.findViewById(radioButtonID);
		int idx = preset.indexOfChild(radioButton);
		Intent intent = new Intent(this, MainActivity.class);
		switch (idx) {
		case 1:
			Universe.starsCount = 500;
			intent.putExtra("preset", 'P');
			break;
		case 2:
			Universe.starsCount = 800;
			intent.putExtra("preset", 'H');
			break;
		case 3:
			Universe.starsCount = 1300;
			intent.putExtra("preset", 'X');
			break;
		default:
			Universe.starsCount = 300;
			intent.putExtra("preset", 'E');
		}
		startActivity(intent);
	}

}
