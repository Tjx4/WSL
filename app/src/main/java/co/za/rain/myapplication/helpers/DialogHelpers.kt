package co.za.rain.myapplication.helpers

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.Window
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

fun showSuccessAlert(context: Context, title: String, message: String, buttonText: String = "Ok", callbackFun:  () -> Unit = {}){
    val ab = setupBasicMessage(title, message, buttonText, "", "", callbackFun, {}, {}, context)
    ab.setIcon(R.drawable.ic_success_dark)
    ab.setCancelable(false)
    showAlertMessage(ab, context)
}

fun showErrorAlert(context: Context, title: String, message: String, buttonText: String = "Ok", callbackFun: () -> Unit = {}){
    val ab = setupBasicMessage(title, message, buttonText, "", "", callbackFun, {}, {}, context)
    ab.setIcon(R.drawable.ic_error_dark)
    ab.setCancelable(false)
    showAlertMessage(ab, context)
}

fun showConfirmAlert(context: Context, title: String, message: String, yesButtonText: String?, neutralButtonText: String?, noButtonText: String?, yesCallbackFun: () -> Unit, neutralCallback: () -> Unit = {}, noCallbackFun: () -> Unit){
    val ab = setupBasicMessage(title, message, yesButtonText, neutralButtonText, noButtonText, yesCallbackFun, neutralCallback, noCallbackFun, context)
    ab.setIcon(R.drawable.ic_confirm_dark)
    ab.setCancelable(false)
    showAlertMessage(ab, context)
}

private fun setupBasicMessage(title: String,message: String,
                              positiveButtonText: String?, neutralButtonText: String?, negetiveButtonText: String?,
                              positiveCallback: () -> Unit, neutralCallback: () -> Unit, negetiveCallback: () -> Unit,
                              context: Context
): AlertDialog.Builder {

    val ab = AlertDialog.Builder(context, R.style.AlertDialogCustom)
    ab.setMessage(message)
        .setTitle(title)
        .setPositiveButton(positiveButtonText) { dialogInterface, i ->
            positiveCallback()
        }

    if (neutralButtonText != null) {
        ab.setNeutralButton(neutralButtonText) { dialogInterface, i ->
            neutralCallback()
        }
    }

    if (negetiveButtonText != null) {
        ab.setNegativeButton(negetiveButtonText) { dialogInterface, i ->
            negetiveCallback()
        }
    }

    return ab
}

private fun showAlertMessage(ab: AlertDialog.Builder, context: Context) {
    val a = ab.create()
    a.requestWindowFeature(Window.FEATURE_NO_TITLE)
    a.show()

    a.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(context.resources.getColor(R.color.darkText))
    a.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(context.resources.getColor(R.color.darkText))
    a.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(context.resources.getColor(R.color.darkText))
}
