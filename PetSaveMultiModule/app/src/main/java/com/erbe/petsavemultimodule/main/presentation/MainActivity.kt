package com.erbe.petsavemultimodule.main.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.erbe.petsavemultimodule.R
import com.erbe.petsavemultimodule.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * Main Screen
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainActivityViewModel>()

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as DynamicNavHostFragment)
            .navController
    }

    private val appBarConfiguration by lazy {
        AppBarConfiguration(
          topLevelDestinationIds = setOf(
            R.id.onboardingFragment,
            R.id.animalsNearYouFragment,
            R.id.searchFragment
          )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch to AppTheme for displaying the activity
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupBottomNav()
        triggerStartDestinationEvent()
        observeViewEffects()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupBottomNav() {
        binding.bottomNavigation.setupWithNavController(navController)
        hideBottomNavWhenNeeded()
    }

    private fun hideBottomNavWhenNeeded() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
              R.id.onboardingFragment -> binding.bottomNavigation.visibility = View.GONE
                else -> binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    private fun triggerStartDestinationEvent() {
        viewModel.onEvent(MainActivityEvent.DefineStartDestination)
    }

    private fun observeViewEffects() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewEffect.collect { reactTo(it) }
        }
    }

    private fun reactTo(effect: MainActivityViewEffect) {
        when (effect) {
          is MainActivityViewEffect.SetStartDestination -> setNavGraphStartDestination(effect.destination)
        }
    }

    private fun setNavGraphStartDestination(startDestination: Int) {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        navGraph.startDestination = startDestination
        navController.graph = navGraph
    }
}