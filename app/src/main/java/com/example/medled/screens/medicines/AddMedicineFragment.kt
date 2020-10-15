package com.example.medled.screens.medicines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.MedicineFormsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_add_medicine.*


class AddMedicineFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
        setupRecyclerView()
        onSeekbarChanged()
        setUpMedicineType()
    }

    private fun setupNavigation() =  addMedicineBackButton.setOnClickListener {
        requireActivity().onBackPressed()
    }

    private fun setupRecyclerView(){
        medicineFormRecyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        medicineFormRecyclerView.adapter = MedicineFormsRecyclerViewAdapter()
    }


    //-------------------------------| set count of weeks on seekbar changed |-----------------------------
    private fun onSeekbarChanged(){
        durationSeekbar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                durationText.text = "$p1 weeks"
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }
    //======================================================================================================

    //----------------------------| setup medicine type auto complete text view as spinner |------------------------------
    private fun setUpMedicineType(){
        medicineTypeChooser.setAdapter(ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,arrayListOf("pills", "mg", "ml")))
        medicineTypeChooser.inputType = 0
        medicineTypeChooser.keyListener = null;
        medicineTypeChooser.setOnClickListener {
            medicineTypeChooser.showDropDown()
        }

    }
    //=====================================================================================================================

}