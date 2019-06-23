package android.stroyland.notissimus.com.mvvmproject.di.modules

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class NavigationModule {
    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideCiceroneRouter(cicerone: Cicerone<Router>): Router = cicerone.router

    @Provides
    @Singleton
    fun provideCiceroneNavigator(cicerone: Cicerone<Router>): NavigatorHolder = cicerone.navigatorHolder
}