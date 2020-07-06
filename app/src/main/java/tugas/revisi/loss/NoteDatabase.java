package tugas.revisi.loss;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 3, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {


    //instance created to turn database class to a singleton
    private static NoteDatabase instance;

    //database callback created to populate database when its initially created
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    //database instance
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    //method used to access Dao methods
    public abstract NotesDao notesDao();

    //background operation to populate database when initially created
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotesDao notesDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            notesDao = db.notesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notesDao.insertNote(new Note("Judul 1", "Deskripsi 1", "6rd Juli 2020"));
            notesDao.insertNote(new Note("Judul 2", "Deskripsi 2", "6rd Juli 2020"));
            notesDao.insertNote(new Note("Judul 3", "Deskripsi 3", "6rd Juli 2020"));

            return null;
        }
    }
}
