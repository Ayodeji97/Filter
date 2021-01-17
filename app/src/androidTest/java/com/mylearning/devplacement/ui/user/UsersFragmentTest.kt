package com.mylearning.devplacement.ui.user

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.mylearning.devplacement.R
import com.mylearning.devplacement.launchFragmentInHiltContainer
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.utils.Colors
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class UsersFragmentTest {

   // var colors : Colors()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun clickOnImage_navigateToAddShoppingItemFragment() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<UsersFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.image_card)).perform(click())

        val user = User("1", "John Obi", "https://randomuser.me/api/portraits/women/65.jpg"
        "male", )

        verify(navController).navigate(
            UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment()
        )
    }
}