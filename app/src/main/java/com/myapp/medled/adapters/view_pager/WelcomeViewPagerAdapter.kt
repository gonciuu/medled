package com.myapp.medled.adapters.view_pager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.myapp.medled.R
import com.myapp.medled.models.PagerCard
import kotlinx.android.synthetic.main.view_pager_card.view.*

class WelcomeViewPagerAdapter(private val context: Context,private val listOfCards:ArrayList<PagerCard>):PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun getCount(): Int {
        return listOfCards.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(this.context).inflate(R.layout.view_pager_card, container, false)
        view.pagerTitle.text = listOfCards[position].title
        view.pagerMessage.text = listOfCards[position].message
        container.addView(view)
        return view
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }


}