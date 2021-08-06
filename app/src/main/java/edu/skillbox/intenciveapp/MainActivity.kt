package edu.skillbox.intenciveapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.skillbox.intenciveapp.ui.personlist.PersonListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PersonListFragment.newInstance())
                .commitNow()
        }
    }
}