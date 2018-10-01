package com.yggdralisk.feviner.dagger.components

import com.yggdralisk.feviner.MainActivity
import com.yggdralisk.feviner.dagger.modules.MainActivityModule
import dagger.Component

@Component(modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}