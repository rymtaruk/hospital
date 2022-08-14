package com.rymtaruk.hospital.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.rymtaruk.core.di.util.ViewModelFactory
import com.rymtaruk.hospital.databinding.ActivityHomeBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onObserverData()
    }

    private fun onObserverData(){
        viewModel.responseCovidData.observeForever {
            Toast.makeText(this, it.data.id.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}