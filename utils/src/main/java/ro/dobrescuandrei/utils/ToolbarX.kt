package ro.dobrescuandrei.utils

import android.graphics.Color
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import com.balysv.materialmenu.MaterialMenu
import com.balysv.materialmenu.MaterialMenuDrawable

class ToolbarXData
{
    val menuItemClickListeners : MutableMap<Int, () -> (Unit)> = mutableMapOf()

    @MenuRes
    var menuResourceId : Int = 0
}

fun Toolbar.getData() : ToolbarXData
{
    if (tag==null)
        tag=ToolbarXData()
    return tag as ToolbarXData
}

fun Toolbar.setupBackIcon()
{
    setNavigationIcon(R.drawable.ic_arrow_left_white_24dp)
    setNavigationOnClickListener { context.toActivity()!!.onBackPressed() }
}

fun Toolbar.setMenu(menuResourceId : Int)
{
    getData().menuResourceId=menuResourceId
}

operator fun Toolbar.set(menuItemId : Int, value: () -> (Unit)) =
    getData().menuItemClickListeners.put(menuItemId, value)

fun Toolbar.onOptionsItemSelected(item : MenuItem?)
{
    if (item?.itemId==android.R.id.home)
    {
        context.toActivity()!!.onBackPressed()
    }
    else
    {
        val listeners=getData().menuItemClickListeners
        for ((itemId, action) in listeners)
        {
            if (item?.itemId==itemId)
            {
                action()
                return
            }
        }
    }
}

fun Toolbar.onCreateOptionsMenu(menuInflater : MenuInflater, menu : Menu?)
{
    val menuResourceId=getData().menuResourceId
    if (menuResourceId!=0&&menu!=null)
        menuInflater.inflate(menuResourceId, menu)
}

fun Toolbar.onCreateOptionsMenuFromFragment()
{
    val menu=getData().menuResourceId
    if (menu!=0)
        inflateMenu(menu)
}

fun Toolbar.setupHamburgerMenu()
{
    val materialMenu=MaterialMenuDrawable(context, Color.WHITE, MaterialMenuDrawable.Stroke.THIN)
    materialMenu.iconState=MaterialMenuDrawable.IconState.BURGER
    navigationIcon = materialMenu
}

fun Toolbar.setOnHamburgerMenuClickedListener(listener : () -> (Unit))
{
    setNavigationOnClickListener {
        (navigationIcon as? MaterialMenu)?.let { materialMenu ->
            if (materialMenu.iconState==MaterialMenuDrawable.IconState.BURGER)
                materialMenu.animateIconState(MaterialMenuDrawable.IconState.ARROW)
            else materialMenu.animateIconState(MaterialMenuDrawable.IconState.BURGER)
        }

        listener()
    }
}
