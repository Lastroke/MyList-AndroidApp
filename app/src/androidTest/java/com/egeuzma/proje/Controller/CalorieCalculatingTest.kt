package com.egeuzma.proje.Controller

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.egeuzma.proje.R
import org.hamcrest.core.StringContains.containsString

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CalorieCalculatingTest {

    // Uygulama açılınca Ana Ekran yükleniyor mu?
    @Test
    fun isMainActivityInView(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.MainActivityLayout)).check(matches(isDisplayed()))
    }

    // Calori Calculator navigasyonu çalışıyor mu?
    @Test
    fun isNavToCalculatorWorking(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button4)).perform(click())
        onView(withId(R.id.KaloriHesaplıyıcıLayout)).check(matches(isDisplayed()))
    }

    // Test edilecek ürün detayı
    var ürünismi= "Ayran"
    var miktar = "50"
    var totalkalori = "17"

    // Kalori hesaplıyıcı eklenen ürünün kalorisini doğru gösteriyor mu
    @Test
    fun isCalculatorWorking(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button4)).perform(click())

        onView(withId(R.id.searchResultRecyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
            (
            hasDescendant(withText(ürünismi))
            ,click()))
        onView(withId(R.id.kaloriPopupLayout)).check(matches(isDisplayed()))

        onView(withId(R.id.text_miktar)).perform(typeText(miktar))
        onView(withText("Ekle"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())

        //total kalori yukardaki miktar bilgisiyle aynı mı?
        onView(withId(R.id.toplamKaloriTextView)).check(matches(withText(containsString(totalkalori))))
    }
}