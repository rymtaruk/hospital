package com.rymtaruk.hospital.di.module

import com.rymtaruk.hospital.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity
}