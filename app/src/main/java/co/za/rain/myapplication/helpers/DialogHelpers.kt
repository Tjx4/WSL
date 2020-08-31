package co.za.rain.myapplication.helpers

import co.za.rain.myapplication.R
import co.za.rain.myapplication.constants.LAYOUT
import co.za.rain.myapplication.constants.TITLE
import co.za.rain.myapplication.features.base.activity.BaseActivity
import co.za.rain.myapplication.features.base.fragments.BaseDialogFragment
import co.za.rain.myapplication.features.base.fragments.LoadingSpinnerFragment

fun showLoadingDialog(loadingMessage: String, activity: BaseActivity) {
    var loadingSpinnerFragment = LoadingSpinnerFragment.newInstance("")
    loadingSpinnerFragment.isCancelable = false
    showDialogFragment(loadingMessage, R.layout.fragment_loading_spinner, loadingSpinnerFragment, activity)

}

fun hideCurrentLoadingDialog(activity: BaseActivity) {
    activity.activeDialogFragment?.dismiss()
}

fun showDialogFragment(title: String, Layout: Int, newFragmentBaseBase: BaseDialogFragment, activity: BaseActivity) {
    activity?.activeDialogFragment?.dismiss()
    val ft = activity.supportFragmentManager.beginTransaction()
    var newFragment = getFragmentDialog(title, Layout, newFragmentBaseBase)
    newFragment.show(ft, "dialog")
    activity.activeDialogFragment = newFragment
}

private fun getFragmentDialog(title: String, layout: Int, newFragmentBaseBase: BaseDialogFragment) : BaseDialogFragment {
    val payload = newFragmentBaseBase.arguments
    payload?.putString(TITLE, title)
    payload?.putInt(LAYOUT, layout)

    newFragmentBaseBase.arguments = payload
    return newFragmentBaseBase
}

