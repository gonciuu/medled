package com.myapp.medled.screens.profile.user_config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.medled.R
import com.myapp.medled.adapters.recycler_view.LinksRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_sources.*


class SourcesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sources, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sourcesBackButton.setOnClickListener { requireActivity().onBackPressed() }
        setupLinksRecyclerViews()
    }

    //---------------------------| Setup links adapters |---------------------------------
    private fun setupLinksRecyclerViews(){
        layoutInspirationsSourcesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        imagesSourcesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val inspirationsAdapter = LinksRecyclerViewAdapter(getLayoutInspirationsLinks())
        val imagesSources = LinksRecyclerViewAdapter(getImagesLinks())

        layoutInspirationsSourcesRecyclerView.adapter = inspirationsAdapter
        imagesSourcesRecyclerView.adapter = imagesSources
    }
    //=====================================================================================


    //--------------------------| Get layout inspirations links |----------------------------
    private fun getLayoutInspirationsLinks():ArrayList<String>{
        return arrayListOf(
            "https://dribbble.com/shots/13959395-Medicines-reminder",
            "https://dribbble.com/shots/6081979-TFP-Add-Pills",
            "https://dribbble.com/shots/11869114-Medical-Mobile-App"
        )
    }
    //=======================================================================================
    //--------------------------| Get layout inspirations links |----------------------------
    private fun getImagesLinks():ArrayList<String>{
        return arrayListOf(
            "https://pixabay.com/pl/vectors/chemik-komiks-znak%C3%B3w-katastrofa-2026442/",
            " https://pixabay.com/vectors/medical-nurse-doctor-hospital-5459654/",
            "https://pixabay.com/illustrations/kit-bless-you-medicine-drugs-1704526/",
            "https://pixabay.com/illustrations/doctor-health-icon-button-medical-2411135/",
            " https://pixabay.com/vectors/vitamins-tablets-pills-medicine-26622/",
            "https://pixabay.com/vectors/capsule-drug-gelatine-medicine-158568/",
            "https://pixabay.com/illustrations/pot-maple-syrup-medicine-4325111/",
            "https://pixabay.com/pl/vectors/pojemnika-pasta-do-z%C4%99b%C3%B3w-rura-1294964/",
            "https://pixabay.com/illustrations/lotion-icon-blank-tube-flu-drug-5153989/",
            "https://pixabay.com/vectors/syringe-cure-medicine-drug-1712511/",
            "https://pixabay.com/vectors/medical-female-care-woman-flat-5047582/",
            "https://pixabay.com/illustrations/nurse-woman-person-nursing-medical-359321/",
            "https://pixabay.com/vectors/medical-female-care-woman-flat-5047624/",
            "https://pixabay.com/vectors/medical-female-care-woman-flat-5047619/",
            "https://pixabay.com/vectors/boy-cartoon-chart-checkup-clinic-2027615/",
            "https://pixabay.com/vectors/lungs-organ-anatomy-bronchia-154282/",
            "https://pixabay.com/vectors/man-sad-depression-alone-lonely-5095968/",
            "https://pixabay.com/vectors/brain-neurology-mind-anatomy-2228215/",
            "https://pixabay.com/vectors/baby-boy-girl-neutral-child-cute-507132/",
            "https://pixabay.com/vectors/people-human-group-person-symbol-3245739/",
            "https://pixabay.com/vectors/beauty-brunette-face-girl-woman-157149/",
            "https://pixabay.com/vectors/ekg-electrocardiogram-heart-art-2069872/",
            "https://pixabay.com/vectors/man-person-avatar-face-head-156584/",
            "https://pixabay.com/vectors/businessman-male-business-avatar-310819/",
            "https://pixabay.com/vectors/user-avatar-female-blond-girl-310807/",
            "https://pixabay.com/vectors/avatar-cartoon-eyes-female-funny-2026510/",
            "https://pixabay.com/vectors/avatar-woman-female-icon-people-2109804/",
            "https://pixabay.com/vectors/avatar-people-person-business-user-3680134/"

        )
    }
    //=======================================================================================

}