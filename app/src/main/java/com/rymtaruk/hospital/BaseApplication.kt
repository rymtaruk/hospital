package com.rymtaruk.hospital

import com.rymtaruk.core.di.component.CoreComponent
import com.rymtaruk.core.di.component.CoreComponentProvider
import com.rymtaruk.core.di.component.DaggerCoreComponent
import com.rymtaruk.core.di.module.ProviderModule
import com.rymtaruk.hospital.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication : DaggerApplication(), CoreComponentProvider {
    private var coreComponent: CoreComponent? = null

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        ProviderModule.context = applicationContext
        return DaggerAppComponent.builder().application(this).coreComponent(provideCoreComponent())
            .build()
    }

    override fun provideCoreComponent(): CoreComponent {
        if (coreComponent == null) {
            coreComponent = DaggerCoreComponent.builder().build()
        }
        return coreComponent!!
    }
}