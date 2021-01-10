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
class CreateListTest {

    // Uygulama açılınca Ana Ekran yükleniyor mu?
    @Test
    fun isMainActivityInView(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.MainActivityLayout)).check(matches(isDisplayed()))
    }

    // Ana Sayfadaki yeni liste ekleme butonu gözüküyor mu?
    //Ist die Schaltfläche Neue Liste hinzufügen auf der Startseite sichtbar?
    @Test
    fun isCreateListButtonInView(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button5)).check(matches(isDisplayed()))
    }

    // Yeni liste ekle butonuna basınca yeni liste ekleme penceresi açılıyor mu?
    @Test
    fun isCreateListButtonWorking() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button5)).perform(click())
        onView(withId(R.id.createListLayout)).check(matches(isDisplayed()))
    }

    // Açılan pencereye Text girip ekle tuşuna basınca liste ekleniyor mu?
    val newListName = "yeni liste"
    @Test
    fun CreatingAListWorking(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.button5)).perform(click())
        onView(withId(R.id.yeniListe)).perform(typeText(newListName))
        onView(withId(R.id.kaydetbutton)).perform(click())
    }
    //"yeni liste" adında liste eklendi mi?
    @Test
    fun CreatedListExists(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.ListerecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.ListerecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(newListName))
            ,click()))
    }

}