package br.com.andretortolano.shared.di

import android.app.Activity
import androidx.fragment.app.Fragment

interface FeatureEntryPoints {
    fun inject(activity: Activity)
    fun inject(fragment: Fragment)
}