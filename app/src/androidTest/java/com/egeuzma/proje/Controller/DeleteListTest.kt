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
class DeleteListTest {

    // Uygulama açılınca Ana Ekran yükleniyor mu?
    @Test
    fun isMainActivityInView(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.MainActivityLayout)).check(matches(isDisplayed()))
    }

    var silincekListeAdı = "yeni liste" // silinmek istenen listenin adı

    // Listeye tıklayınca liste içeriği geliyor mu
    @Test
    fun isClickingAListWorking(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.ListerecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.ListerecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(silincekListeAdı))
            ,click()))
    }

    // Liste içeriğindeki silme tuşu çalışıyor mu sonrasında ana sayfaya dönülüyor mu?
    @Test
    fun isDeletingAListWorking(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.ListerecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.ListerecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(silincekListeAdı))
            ,click()))

        onView(withId(R.id.button6)).check(matches(isDisplayed()))
        onView(withId(R.id.button6)).perform(click())

        onView(withId(R.id.MainActivityLayout)).check(matches(isDisplayed()))
    }

}