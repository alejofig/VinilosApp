package com.mobiles.vinilosapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ArtistFragmentTest{
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewItemClick() {
        // Hacer clic en el elemento de la barra de navegación que muestra el fragmento
        Espresso.onView(ViewMatchers.withId(R.id.navigation_artists)).perform(ViewActions.click())

        // Esperar un tiempo para que se muestre el fragmento
        Thread.sleep(1000)

        // Verificar que el fragmento se muestra correctamente
        Espresso.onView(ViewMatchers.withId(R.id.artistsRv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(1000)
        // Verificar que haya un artista con el nombre "Rubén Blades"
        Espresso.onView(ViewMatchers.withText("Rubén Blades Bellido de Luna"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


}