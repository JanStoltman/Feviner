package com.yggdralisk.feviner.dagger.modules

import com.yggdralisk.feviner.models.UserScore
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    fun provideUserScore(): UserScore = UserScore()
}