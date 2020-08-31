package co.za.rain.myapplication.features.locationTracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import co.za.rain.myapplication.R
import co.za.rain.myapplication.features.base.fragments.BaseDialogFragment

class MoreInfoFragment : BaseDialogFragment() {
    private var closeBtn: ImageButton? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var parentView = super.onCreateView(inflater, container, savedInstanceState)
        initViews(parentView)
        return parentView
    }

    private fun initViews(parentView: View) {
        closeBtn = parentView.findViewById(R.id.imgBtnCloseInfo)
    }

    companion object {
        fun newInstance(): BaseDialogFragment {
            val moreInfoFragment = MoreInfoFragment()
            moreInfoFragment.arguments = Bundle()
            return moreInfoFragment
        }
    }

}