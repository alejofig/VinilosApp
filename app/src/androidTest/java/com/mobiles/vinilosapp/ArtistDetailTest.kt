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
class ArtistDetailTest{
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


        //Desplazarse hasta el primer elemento del RecyclerView y hacer clic en él
        Espresso.onView(ViewMatchers.withId(R.id.artistsRv))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        Espresso.onView(ViewMatchers.withId(R.id.artistsRv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        // Esperar un tiempo para que se realice la acción de clic en el elemento
        Thread.sleep(1000)


        // Verificar que se muestra la vista de detalle del álbum
        Espresso.onView(ViewMatchers.withId(R.id.artistsDetailRv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(1000)
        // Verificar la descripción del seleccionado
        Espresso.onView(ViewMatchers.withText("Es un cantautor, compositor, actor, escritor, poeta y músico español."))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }



}