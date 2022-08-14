package com.rymtaruk.hospital.di.module

import com.rymtaruk.hospital.ui.HomeActivity
import com.rymtaruk.hospital.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}