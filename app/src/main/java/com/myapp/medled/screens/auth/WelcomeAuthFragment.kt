package com.myapp.medled.screens.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.myapp.medled.R
import com.myapp.medled.adapters.view_pager.WelcomeViewPagerAdapter
import com.myapp.medled.models.PagerCard
import kotlinx.android.synthetic.main.fragment_welcome_auth.*

class WelcomeAuthFragment : Fragment() {

    //-----pager auto scroll-----
    private var viewPagerItemPosition :Int = 0
    private var handler : Handler = Handler(Looper.getMainLooper())
    //===========================

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_welcome_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupNavigation()
        handler.postDelayed(changeViewPagerItem,2000)
    }



    //-------------------------------| setup view pager set view and margin between pager dots |------------------------------
    private fun setupViewPager(){
        welcomeAuthViewPager.adapter = WelcomeViewPagerAdapter(requireContext(),getPagerMessagesList())
        welcomePagerDots.setupWithViewPager(welcomeAuthViewPager)
        for (i in 0 until welcomePagerDots.tabCount) {
            val tab = (welcomePagerDots.getChildAt(0) as ViewGroup).getChildAt(i)
            (tab.layoutParams as ViewGroup.MarginLayoutParams).setMargins(0, 0, 31, 0);
            tab.requestLayout()
        }
        welcomePagerDots.invalidate()
    }
    //==========================================================================================================================


    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(changeViewPagerItem)
    }


    //--------------------| Get titles and messages to viewpager |------------------------
    private fun getPagerMessagesList():ArrayList<PagerCard>{
        val listOfCards = ArrayList<PagerCard>()
        listOfCards.add(PagerCard("Let's get started","The app which helps you to remember all your daily medicines. Keep it all in save place! "))
        listOfCards.add(PagerCard("Keep medicines save","Save your medicines - app will remind you when you have to take pills! You don't have to remember anything"))
        listOfCards.add(PagerCard("Contact with doctor","Easily keep in touch with your favorite doctor. Messages with him and take disease solution!"))
        return listOfCards
    }
    //====================================================================================


    //--------------------------| Go to login/register destination |--------------------------
    private fun setupNavigation(){
        welcomeAuthRegisterButton.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeAuthFragment_to_registerFragment)
        }
        welcomeAuthLoginButton.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeAuthFragment_to_loginFragment)
        }
    }
    //=========================================================================================


}