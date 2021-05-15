package com.demo.datastore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.demo.datastore.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // using view binding instead of findViewById
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //1 : create a instance of User Preference
        val userPrefrence = UserPrefrence(this)

        //2: get Counter value from data store when activity create & display to textview
        lifecycleScope.launch {
            binding.textView.text = userPrefrence.readCounter.first().toString()
        }

        //3: On button clik increment value in data store
        binding.incrementBtn.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                //icrement value to data store
                userPrefrence.incrementCounter()
            }

            //display increment value on textview
            val value = (binding.textView.text.toString()).toInt() + 1
            binding.textView.text = value.toString()

        }
    }
}