package smart.visitor.ir.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import smart.visitor.ir.R;

public class DescriptionActivity extends BaseActivity {
    EditText edt_Description1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        InitView();
        LoadData();
    }

    private void LoadData() {
        String Description =  getIntent().getStringExtra("Description");
        this.edt_Description1.setText(Description);
    }

    private void InitView() {
        edt_Description1 = findViewById(R.id.edt_Description1);
    }

    public void img_DescriptionDelete_Click(View view) {
        edt_Description1.setText(null);

    }

    public void fab_Description_Click(View view) {
        String str = edt_Description1.getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Description",str);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
        //super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
