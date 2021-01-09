package com.egeuzma.proje.Controller

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.egeuzma.proje.R

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CheckingAProductTest {

    // Uygulama açılınca Ana Ekran yükleniyor mu?
    @Test
    fun isMainActivityInView(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.MainActivityLayout)).check(matches(isDisplayed()))
    }

    var ListeAdı = "Baklava Listesi"  // Test için kullanılacak liste adı

    // Test amaçlı üstü çizilecek ürün adı
    var TıklancakÜrün = "Ayran"

    @Test
    fun isCheckWorkingWhenClicked(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.ListerecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(ListeAdı))
            ,click()))
        onView(withId(R.id.listeIcerikrecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(TıklancakÜrün))
            ,click()))

        /*
        TODO: Ürün işaretlenmiş mi kontrolü yapılcak
        onView(with(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(TıklancakÜrün)),

        ))*/
    }


}