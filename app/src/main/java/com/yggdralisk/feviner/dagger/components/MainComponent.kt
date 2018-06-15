package com.yggdralisk.feviner.dagger.components

import com.yggdralisk.feviner.MainActivity
import com.yggdralisk.feviner.dagger.modules.UserScoreModule
import dagger.Component

@Component(modules = [UserScoreModule::class])
interface MainComponent {
    fun inject(activity: MainActivity)
}