package co.za.rain.myapplication.features.locationTracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.za.rain.myapplication.features.base.fragments.BaseDialogFragment


class MoreInfoFragment : BaseDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val parentView = super.onCreateView(inflater, container, savedInstanceState)
        return parentView
    }

    companion object {
        fun newInstance(): BaseDialogFragment {
            val moreInfoFragment = MoreInfoFragment()
            moreInfoFragment.arguments = Bundle()
            return MoreInfoFragment()
        }
    }

}