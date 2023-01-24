package com.example.myapplication.ui.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.example.myapplication.MainActivity

open class BaseFragment : Fragment() {

    fun navigate(
        @IdRes resId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    ) {
        if (activity is MainActivity) {
            (activity as MainActivity).navigate(
                resId,
                args,
                navOptions,
                navigatorExtras
            )
        }
    }
}