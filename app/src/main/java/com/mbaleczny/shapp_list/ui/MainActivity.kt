package com.mbaleczny.shapp_list.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.mbaleczny.shapp_list.R
import com.mbaleczny.shapp_list.ui.base.TabLayoutMediator
import com.mbaleczny.shapp_list.ui.list.ShoppingListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    companion object {
        val TABS = arrayListOf("Current", "Archived")
        const val TAB_COUNT = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tabs)

        viewPager.adapter = createViewPagerAdapter()

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = TABS[position]
        }.attach()
    }

    private fun createViewPagerAdapter(): FragmentStateAdapter =
        object : FragmentStateAdapter(this) {

            override fun getItem(position: Int): Fragment =
                ShoppingListFragment.newInstance(position == 1)

            override fun getItemCount(): Int = TAB_COUNT
        }
}
