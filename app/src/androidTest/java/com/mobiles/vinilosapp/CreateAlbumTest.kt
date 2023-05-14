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
class CreateAlbumTest{
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewItemClick() {
        // Hacer clic en el elemento de la barra de navegación que muestra el fragmento
        Espresso.onView(ViewMatchers.withId(R.id.add_album)).perform(ViewActions.click())

        // Esperar un tiempo para que se muestre el fragmento
        Thread.sleep(3000)

        // Verificar que el fragmento se muestra correctamento - verificar elemenetos de la pantalla
        Espresso.onView(ViewMatchers.withId(R.id.albumCreateImage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.txt_album_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.txt_album_desc))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.etDate))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.txt_album_genre))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.txt_album_disc))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Ingresar Datos
        Espresso.onView(ViewMatchers.withId(R.id.txt_album_name))
            .perform(ViewActions.replaceText("Álbum Test 1"))

        Espresso.onView(ViewMatchers.withId(R.id.txt_album_desc))
            .perform(ViewActions.replaceText("Description test"))

        Espresso.onView(ViewMatchers.withId(R.id.etDate))
            .perform(ViewActions.replaceText("14/05/2023"))

        Espresso.onView(ViewMatchers.withId(R.id.txt_album_genre))
            .perform(ViewActions.replaceText("Rock"))

        Espresso.onView(ViewMatchers.withId(R.id.txt_album_disc))
            .perform( ViewActions.replaceText("Sony Music"))

        Thread.sleep(3000)

        Espresso.onView(ViewMatchers.withId(R.id.album_create_button)).perform(ViewActions.scrollTo())

        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.album_create_button)).perform(ViewActions.click())

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(R.id.navigation_albums)).perform(ViewActions.click())

        Thread.sleep(2000)

        Espresso.pressBack()

        Thread.sleep(3000)
        //Espresso.onView(ViewMatchers.withId(R.id.txt_album_name))
        //    .perform(ViewActions.replaceText("Description"))


        // Verificar que se muestra
        //Espresso.onView(ViewMatchers.withId(R.id.artistsDetailRv))
        //    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //Thread.sleep(1000)
        // Verificar la descripción del seleccionado
        //Espresso.onView(ViewMatchers.withText("Es un cantautor, compositor, actor, escritor, poeta y músico español."))
        //   .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }



}