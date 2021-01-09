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
import org.junit.FixMethodOrder

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class AddingProductsFromRecipesTest {

    // Uygulama açılınca Ana Ekran yükleniyor mu?
    @Test
    fun isMainActivityInView(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.MainActivityLayout)).check(matches(isDisplayed()))
    }

    // Recipes butonu çalışıyor mu?
    @Test
    fun isNavToRecipesWorking(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button3)).perform(click())

        onView(withId(R.id.RecipesLayout)).check(matches(isDisplayed()))
    }

    var RecipeName = "Baklava" // Test için kullanılacak Tarifin adı

    // Ürünleri ekleme butonu çalışıyor mu
    @Test
    fun isAddIngredientsButtonWorking(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button3)).perform(click())

        onView(withId(R.id.tariflerRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.tariflerRecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(RecipeName))
            ,click()))

        onView(withId(R.id.button25)).check(matches(isDisplayed()))
        onView(withId(R.id.button25)).perform(click())
    }

    // Ürünler listesi eklenmiş mi?
    @Test
    fun isProductsAdded(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.ListerecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(RecipeName+" Listesi"))
            ,click()))
    }

}