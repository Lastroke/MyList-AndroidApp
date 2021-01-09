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
class AddingProductToAListTest {

    // Uygulama açılınca Ana Ekran yükleniyor mu?
    @Test
    fun isMainActivityInView(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.MainActivityLayout)).check(matches(isDisplayed()))
    }

    var ListeAdı = "Baklava Listesi"  // Test için kullanılacak liste adı

    // Test amaçlı eklenicek ürün bilgileri
    var EklencekUrun = "Ayran"
    var Gram = "50"
    var Note = "Not"

    //ListeAdı isimli listeye tıklanabiliyor mu?
    @Test
    fun isListExist(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.ListerecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(ListeAdı))
            ,click()))
    }

    //Ürün ekle butonu ürün ekleme sayfasına yönlendiriyor mu?
    @Test
    fun navAddProductWorking(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.ListerecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(ListeAdı))
            ,click()))
        onView(withId(R.id.button)).perform(click())

        onView(withId(R.id.addProductLayout)).check(matches(isDisplayed()))
    }

    // Ürün Ekleme Çalışıyor mu?
    @Test
    fun addingAProductWorking(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.ListerecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(ListeAdı))
            ,click()))
        onView(withId(R.id.button)).perform(click())

        onView(withId(R.id.searchView)).perform(typeText(EklencekUrun))
        onView(withId(R.id.recyclerViewProduct)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(EklencekUrun))
            ,click()))

        onView(withId(R.id.ürünEklemeAdetGirmeText)).perform(typeText(Gram))
        onView(withId(R.id.ürünEklemeNotGirmeText)).perform(typeText(Note))

        onView(withId(R.id.ürünEklemeKaydetbutton)).perform(click())
    }

    // Eklenen Ürün listede mevcut mu?
    @Test
    fun addedProductExists(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.ListerecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(ListeAdı))
            ,click()))
        onView(withId(R.id.listeIcerikrecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(EklencekUrun))
            ,click()))
    }




}