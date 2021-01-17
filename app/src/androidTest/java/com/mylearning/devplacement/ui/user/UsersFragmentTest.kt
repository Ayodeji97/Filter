package com.mylearning.devplacement.ui.user

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.mylearning.devplacement.R
import com.mylearning.devplacement.launchFragmentInHiltContainer
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.ui.detail.UserDetailsFragment
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

    private lateinit var colors: com.mylearning.devplacement.room.Colors

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickOnCardBody_navigateToDetailScreen () {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<UserDetailsFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.parent_card_view)).perform(ViewActions.click())

        verify(navController).navigate(R.id.userDetailsFragment)

       // onView(withId(R.id.nested_scroll))
        onView(withId(R.id.parent_card_view)).perform(click())

        val user = User(id = "1", name = "David", image = "https://randomuser.me/api/portraits/women/65.jpg",
            gender =  "male", countries = colors, colors = colors, date = "Wed, 04 Mar 2020 13:34:31 GMT")

        verify(navController).navigate(
            UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment(user)
        )

    }
}