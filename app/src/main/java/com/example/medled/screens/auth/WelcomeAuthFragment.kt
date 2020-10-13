package com.example.medled.screens.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.medled.R
import com.example.medled.adapters.view_pager.WelcomeViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_welcome_auth.*

class WelcomeAuthFragment : Fragment() {

    //-----pager auto scroll-----
    private var viewPagerItemPosition :Int = 0
    private var handler : Handler = Handler(Looper.getMainLooper())
    //===========================


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_welcome_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        handler.postDelayed(changeViewPagerItem,2000)
    }



    //-------------------------------| setup view pager set view and margin between pager dots |------------------------------
    private fun setupViewPager(){
        welcomeAuthViewPager.adapter = WelcomeViewPagerAdapter(requireContext())
        welcomePagerDots.setupWithViewPager(welcomeAuthViewPager)
        for (i in 0 until welcomePagerDots.tabCount) {
            val tab = (welcomePagerDots.getChildAt(0) as ViewGroup).getChildAt(i)
            (tab.layoutParams as ViewGroup.MarginLayoutParams).setMargins(0, 0, 31, 0);
            tab.requestLayout()
        }
        welcomePagerDots.invalidate()
    }
    //==========================================================================================================================

    //-------------------------| Pager auto scroll |--------------------------------
    private var changeViewPagerItem : Runnable = object : Runnable {
        override fun run() {
            viewPagerItemPosition = if(viewPagerItemPosition>=welcomeAuthViewPager.childCount) 0
            else welcomeAuthViewPager.currentItem + 1
            welcomeAuthViewPager.setCurrentItem(viewPagerItemPosition,true)
            handler.postDelayed(this,2000)
        }
    }
    //===============================================================================


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }


}