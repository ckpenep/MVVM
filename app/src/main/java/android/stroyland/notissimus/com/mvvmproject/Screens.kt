package android.stroyland.notissimus.com.mvvmproject

import android.stroyland.notissimus.com.mvvmproject.mvvm.BaseViewModel
import android.stroyland.notissimus.com.mvvmproject.presentation.fragments.BaseFragment
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    class FragmentScreen<P: BaseViewModel, T: BaseFragment<P>>(var fragment : T): SupportAppScreen() {
        override fun getFragment(): Fragment {
            return fragment
        }
    }
}