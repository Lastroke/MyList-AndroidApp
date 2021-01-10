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
class DeletingProductFromAListTest {

    // Uygulama açılınca Ana Ekran yükleniyor mu?
    @Test
    fun isMainActivityInView(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.MainActivityLayout)).check(matches(isDisplayed()))
    }

    var ListeAdı = "yeni liste"  // Test için kullanılacak liste adı

    // Test amaçlı silincek ürün bilgileri
    var silincekUrun = "Ayran"

    //ListeAdı isimli listeye tıklanabiliyor mu?
    @Test
    fun isListExist(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.ListerecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(ListeAdı))
            ,click()))
    }

    // Silinecek ürüne uzunca tıklayınca ürün sayfası açılıyor mu?
    @Test
    fun isNavProductInfoWorking(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.ListerecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(ListeAdı))
            ,click()))
        onView(withId(R.id.listeIcerikrecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(silincekUrun))
            ,longClick()))

        onView(withId(R.id.ProductInfoLayout)).check(matches(isDisplayed()))
    }

    // Ürün Silme Butonu çalışıyor mu?
    @Test
    fun isDeletingWorking(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.ListerecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(ListeAdı))
            ,click()))
        onView(withId(R.id.listeIcerikrecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(silincekUrun))
            ,longClick()))

        onView(withId(R.id.adetKaydetbutton)).perform(click())
    }

}