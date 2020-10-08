package br.com.andretortolano.numbers_trivia.di

import android.content.Context
import br.com.andretortolano.numbers_trivia.ui.NumbersTriviaActivity
import br.com.andretortolano.shared.di.SharedComponent
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [SharedComponent::class], modules = [FeatureModule::class])
interface FeatureComponent {

    fun inject(activity: NumbersTriviaActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun featureDependencies(dependencies: SharedComponent): Builder
        fun build(): FeatureComponent
    }
}