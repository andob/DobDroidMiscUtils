package ro.dobrescuandrei.dobdroidmiscutils

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ro.dobrescuandrei.utils.*
import java.io.File

class MainActivity : AppCompatActivity()
{
    val permissionsHandler = PermissionsHandler()

    fun findFilesIn(directory : File) : List<File> = yieldListOf<File> {
        directory.listFiles()?.forEach { file ->
            if (file.isDirectory)
                yieldAll(findFilesIn(directory = file))
            else yield(file)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.title="Title"
        toolbar.subtitle="Subtitle"
//        toolbar.setupBackIcon()

        toolbar.setMenu(R.menu.menu_main)
        toolbar[R.id.add] = {
            Log.e("a", "add logic")
        }

        toolbar.setupHamburgerMenu()
        toolbar.setOnHamburgerMenuClickedListener {
            Log.e("a", "toggle drawer")
        }

        ScreenSize.init(withContext = this)

        permissionsHandler.askFor(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .onGranted { Log.e("a", "YAY") }
            .onDenied { Log.e("a", "NAY") }
            .withContext(context = this)

        Handler(Looper.getMainLooper()).postDelayed({
            Keyboard.open(on = this)
            Handler(Looper.getMainLooper()).postDelayed({
                Keyboard.close(on = this)
            }, 1000)
        }, 1000)

        editText.setOnTextChangedListener { newText ->
            Log.e("a", newText)
        }

        editText.setOnEditorActionListener { actionId ->
            if (actionId==EditorInfo.IME_ACTION_DONE)
                Log.e("a", "enter pressed")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsHandler.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        toolbar.onCreateOptionsMenu(menuInflater, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean
    {
        toolbar.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }
}
