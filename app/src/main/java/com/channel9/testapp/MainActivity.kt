package com.channel9.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Main activity that holds the fragments in a container
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}