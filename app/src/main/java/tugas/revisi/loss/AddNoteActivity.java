package tugas.revisi.loss;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "tugas.revisi.loss.EXTRA_ID";
    public static final String EXTRA_TITLE = "tugas.revisi.loss.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "tugas.revisi.loss.EXTRA_DESCRIPTION";

    private EditText titleEditText, writtenNoteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal_note);

        titleEditText = findViewById(R.id.title_edit_text);
        writtenNoteEditText = findViewById(R.id.written_notes_edit_text);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Catatan");
            titleEditText.setText(intent.getStringExtra(EXTRA_TITLE));
            writtenNoteEditText.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        } else {
            setTitle("Tambah Catatan");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_button) {
            saveNote();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    //Saves note object
    private void saveNote() {
        String title = titleEditText.getText().toString();
        String description = writtenNoteEditText.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Mohon masukkan judul dan deskripsi", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }


}
