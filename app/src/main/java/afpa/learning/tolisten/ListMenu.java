package afpa.learning.tolisten;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Afpa on 08/02/2017.
 */

public abstract class ListMenu extends AppCompatActivity {

    abstract void onMenuCreated(Menu menu);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        this.onMenuCreated(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itmLstMedia:
                finish();
                Intent list = new Intent(this, ListActivity.class);
                startActivity(list);
                return true;
            case R.id.itmAddMedia:
                Intent add = new Intent(this, FormActivity.class);
                add.putExtra("edition", Boolean.FALSE);
                startActivityForResult(add, 0);
                return true;
            case R.id.itmAbout:
                Toast.makeText(getBaseContext(), this.getString(R.string.about_txt), Toast.LENGTH_LONG).show();
                return true;
            case R.id.itmLang:
                Intent lang = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(lang);
                return true;
            case R.id.itmExit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
