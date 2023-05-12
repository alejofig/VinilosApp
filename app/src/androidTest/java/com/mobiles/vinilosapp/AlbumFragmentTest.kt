package com.mobiles.vinilosapp


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
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
class AlbumFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewItemClick() {
        // Hacer clic en el elemento de la barra de navegación que muestra el fragmento
        onView(ViewMatchers.withId(R.id.navigation_albums)).perform(ViewActions.click())

        // Esperar un tiempo para que se muestre el fragmento
        Thread.sleep(1000)

        // Verificar que el fragmento se muestra correctamente
        onView(ViewMatchers.withId(R.id.albumsRv)).check(matches(ViewMatchers.isDisplayed()))


        //Desplazarse hasta el primer elemento del RecyclerView y hacer clic en él
        onView(ViewMatchers.withId(R.id.albumsRv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(ViewMatchers.withId(R.id.albumsRv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        // Esperar un tiempo para que se realice la acción de clic en el elemento
        Thread.sleep(1000)


        // Verificar que se muestra la vista de detalle del álbum
        onView(ViewMatchers.withId(R.id.albumsDetailRv)).check(matches(ViewMatchers.isDisplayed()))
    }


    
}