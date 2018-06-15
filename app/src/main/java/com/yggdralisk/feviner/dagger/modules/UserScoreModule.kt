package com.yggdralisk.feviner.dagger.modules

import com.yggdralisk.feviner.models.UserScore
import dagger.Module
import javax.inject.Singleton
import dagger.Provides



@Module
class UserScoreModule{
    @Provides
    fun provideUserScore(): UserScore = UserScore()
}