package com.example.lemonade

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    private val INITIAL_START = "initial"
    private val SELECT = "select"
    private val SQUEEZE = "squeeze"
    private val DRINK = "drink"
    private val RESTART = "restart"
    private var lemonadeState = "initial"
    private var lemonSize = -1
    private var squeezeCount = -1
    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }
        lemonImage = findViewById(R.id.imageView3)
        setViewElements()
        lemonImage!!.setOnClickListener {

            clickLemonImage()
        }
        lemonImage!!.setOnLongClickListener {

            showSnackbar()
        }
//        val view1: TextView = findViewById(R.id.textView)
//        val view2: TextView = findViewById(R.id.textView2)
//        val storeImage: ImageView = findViewById(R.id.imageView3)
//        storeImage.setOnClickListener() {
//            view1.text=""
//            view2.text="Click here to select\n       a Lemon"
//            storeImage.setImageResource(R.drawable.lemonplant)
//        }

    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    private fun clickLemonImage() {
        when(lemonadeState){
            INITIAL_START->{
                lemonadeState=SELECT
            }
            SELECT->{
                lemonadeState=SQUEEZE
                lemonSize=lemonTree.pick()
                squeezeCount=0
            }
            SQUEEZE->{
                squeezeCount+=1
                lemonSize-=1
                lemonadeState=if(lemonSize==0){
                    DRINK
                }else SQUEEZE
            }
            DRINK-> {
                lemonadeState=RESTART
                lemonSize=-1
            }
            RESTART-> lemonadeState=INITIAL_START
        }
        setViewElements()

    }

    private fun setViewElements() {
        val chText: TextView = findViewById(R.id.textView)
        val chImg: ImageView= findViewById(R.id.imageView3)
        when(lemonadeState){
            INITIAL_START->{
                chText.text=getString(R.string.initial)
                chImg.setImageResource(R.drawable.lemontree)
            }
            SELECT->{
                chText.text=getString(R.string.lemon_select)
                chImg.setImageResource(R.drawable.lemonp)
            }
            SQUEEZE->{
                chText.text=getString(R.string.lemon_squeeze)
                chImg.setImageResource(R.drawable.lemons)
                showSnackbar()
            }
            DRINK->{
                chText.text=getString(R.string.lemon_drink)
                chImg.setImageResource(R.drawable.wallpaperflare_com_wallpaper1)
            }
            RESTART->{
                chText.text=getString(R.string.lemon_empty_glass)
                chImg.setImageResource(R.drawable.emptyg)
            }
        }

    }

    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}

class LemonTree(){
    fun pick():Int{
        return (2..4).random()
    }
}