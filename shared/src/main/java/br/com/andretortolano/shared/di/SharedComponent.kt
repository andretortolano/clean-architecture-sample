package br.com.andretortolano.shared.di

import br.com.andretortolano.data.DataModule
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface SharedComponent {

    fun provideDataModule(): DataModule
}