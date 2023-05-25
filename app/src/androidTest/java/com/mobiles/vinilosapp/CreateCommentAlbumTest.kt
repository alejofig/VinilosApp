package com.mobiles.vinilosapp

import android.view.View
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
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
class CreateCommentAlbumTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewItemClick() {

        // Hacer clic en el elemento de la barra de navegación que muestra el fragmento
        Espresso.onView(ViewMatchers.withId(R.id.navigation_albums)).perform(ViewActions.click())

        // Esperar un tiempo para que se muestre el fragmento
        Thread.sleep(2000)

        //Desplazarse hasta el primer elemento del RecyclerView y hacer clic en él
        Espresso.onView(ViewMatchers.withId(R.id.albumsRv))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        Espresso.onView(ViewMatchers.withId(R.id.albumsRv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        // Esperar un tiempo para que se muestre el fragmento
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.add_comment)).perform(ViewActions.scrollTo())


        // Hacer clic en el elemento de la barra de navegación que muestra el fragmento
        Espresso.onView(ViewMatchers.withId(R.id.add_comment)).perform(ViewActions.click())

        // Esperar un tiempo para que se muestre el fragmento
        Thread.sleep(3000)

        // Verificar que el fragmento se muestra correctamento - verificar elemenetos de la pantalla
        Espresso.onView(ViewMatchers.withId(R.id.txt_album_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.txt_album_genre))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.album_image))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.txt_album_comment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.ratingBar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.album_comment_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Ingresar Datos
        Espresso.onView(ViewMatchers.withId(R.id.txt_album_comment))
            .perform(ViewActions.replaceText("comment test 1"))

        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.ratingBar))
            .perform(RatingBarSetter(3))

        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.album_comment_button)).perform(ViewActions.click())

        Thread.sleep(2000)

        //Espresso.onView(ViewMatchers.withId(R.id.navigation_albums)).perform(ViewActions.click())

        //Thread.sleep(2000)

        //Espresso.pressBack()

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

    class RatingBarSetter(private val rating: Int) : ViewAction {
        override fun getConstraints():  org.hamcrest.Matcher<View> {
            return ViewMatchers.isAssignableFrom(RatingBar::class.java)
        }

        override fun getDescription(): String {
            return "Set rating on RatingBar"
        }

        override fun perform(uiController: UiController, view: View) {
            (view as RatingBar).rating = rating.toFloat()
        }
    }

}