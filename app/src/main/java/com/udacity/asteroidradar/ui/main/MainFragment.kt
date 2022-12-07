package com.udacity.asteroidradar.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private var mainAdapter = MainRecycler()

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.asteroidRecycler.adapter = mainAdapter
        viewModel.astroidData.observe(viewLifecycleOwner, Observer {
            mainAdapter.setData(it)
        })
        viewModel.getImageOfTheDay().observe(viewLifecycleOwner, Observer {
            Picasso.with(context).load(it.url).into(binding.activityMainImageOfTheDay)
            binding.activityMainImageOfTheDay.contentDescription = it.title
        })
//        viewModel.ImageLiveData.observe(viewLifecycleOwner, Observer {
//            Picasso.with(context).load(it.url).into(binding.activityMainImageOfTheDay)
//        })
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week -> week()
            R.id.show_today -> today()
            R.id.show_offline -> offline()
        }
        return true
    }

    private fun offline() {
        viewModel.astroidData.observe(viewLifecycleOwner, Observer {
            mainAdapter.setData(it)
        })
    }

    private fun today() {
        viewModel.getToday("2022-12-07").observe(viewLifecycleOwner, Observer {
            mainAdapter.setData(it)
        })
    }

    private fun week() {
        viewModel.astroidData.observe(viewLifecycleOwner, Observer {
            mainAdapter.setData(it)
        })
    }
}
