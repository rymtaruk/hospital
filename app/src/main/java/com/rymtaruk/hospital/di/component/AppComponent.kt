package com.rymtaruk.hospital.di.component

import com.rymtaruk.core.di.component.CoreComponent
import com.rymtaruk.core.di.module.ContextModule
import com.rymtaruk.core.di.util.AppScope
import com.rymtaruk.hospital.BaseApplication
import com.rymtaruk.hospital.di.module.ActivityModule
import com.rymtaruk.hospital.di.module.ApiModule
import com.rymtaruk.hospital.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@AppScope
@Component(
    modules = [ApiModule::class, ContextModule::class, ActivityModule::class, ViewModelModule::class, AndroidInjectionModule::class],
    dependencies = [CoreComponent::class]
)
interface AppComponent : AndroidInjector<BaseApplication>{
    @Component.Builder
    interface Build {
        @BindsInstance
        fun application(application: BaseApplication) : Build
        fun coreComponent(component: CoreComponent) : Build
        fun build() : AppComponent
    }
}