package co.za.rain.myapplication.features.locationTracker.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import co.za.rain.myapplication.R
import co.za.rain.myapplication.databinding.FragmentMoreInfoBindingImpl
import co.za.rain.myapplication.features.base.fragments.BaseDialogFragment
import co.za.rain.myapplication.features.locationTracker.LocationTrackerActivity

class MoreInfoFragment : BaseDialogFragment() {
    lateinit var binding: FragmentMoreInfoBindingImpl
    private var locationTrackerActivity: LocationTrackerActivity? = null
    lateinit var parentView: View
    private var closeBtn: ImageButton? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more_info, container, false)
        binding.lifecycleOwner = this
        binding.locationTrackerViewModel = locationTrackerActivity?.locationTrackerViewModel
        parentView = binding.root
        initViews(parentView)
        return parentView
    }

    private fun initViews(parentView: View) {
        closeBtn = parentView.findViewById(R.id.imgBtnCloseInfo)
        closeBtn?.setOnClickListener {
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        locationTrackerActivity = context as LocationTrackerActivity
    }

    companion object {
        fun newInstance(): BaseDialogFragment {
            val moreInfoFragment = MoreInfoFragment()
            moreInfoFragment.arguments = Bundle()
            return moreInfoFragment
        }
    }

}