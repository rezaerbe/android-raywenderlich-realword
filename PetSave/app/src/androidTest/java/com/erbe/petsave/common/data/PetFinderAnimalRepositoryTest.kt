package com.erbe.petsave.common.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.erbe.petsave.common.data.api.PetFinderApi
import com.erbe.petsave.common.data.api.model.mappers.ApiAnimalMapper
import com.erbe.petsave.common.data.api.model.mappers.ApiPaginationMapper
import com.erbe.petsave.common.data.api.utils.FakeServer
import com.erbe.petsave.common.data.cache.Cache
import com.erbe.petsave.common.data.cache.PetSaveDatabase
import com.erbe.petsave.common.data.cache.RoomCache
import com.erbe.petsave.common.data.di.CacheModule
import com.erbe.petsave.common.data.di.PreferencesModule
import com.erbe.petsave.common.data.di.TestPreferencesModule
import com.erbe.petsave.common.data.preferences.FakePreferences
import com.erbe.petsave.common.data.preferences.Preferences
import com.erbe.petsave.common.di.ActivityRetainedModule
import com.erbe.petsave.common.domain.repositories.AnimalRepository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.Instant
import retrofit2.Retrofit
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(
    PreferencesModule::class,
    TestPreferencesModule::class,
    CacheModule::class,
    ActivityRetainedModule::class
)
class PetFinderAnimalRepositoryTest {

    private val fakeServer = FakeServer()
    private lateinit var repository: AnimalRepository
    private lateinit var api: PetFinderApi
    private lateinit var cache: Cache

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: PetSaveDatabase

    @Inject
    lateinit var retrofitBuilder: Retrofit.Builder

    @Inject
    lateinit var apiAnimalMapper: ApiAnimalMapper

    @Inject
    lateinit var apiPaginationMapper: ApiPaginationMapper

    @BindValue
    @JvmField
    val preferences: Preferences = FakePreferences()

    @Before
    fun setup() {
        fakeServer.start()

        preferences.deleteTokenInfo()
        preferences.putToken("validToken")
        preferences.putTokenExpirationTime(Instant.now().plusSeconds(3600).epochSecond)
        preferences.putTokenType("Bearer")

        hiltRule.inject()

        api = retrofitBuilder
            .baseUrl(fakeServer.baseEndpoint)
            .build()
            .create(PetFinderApi::class.java)

        cache = RoomCache(database.animalsDao(), database.organizationsDao())

        repository = PetFinderAnimalRepository(
            api,
            cache,
            apiAnimalMapper,
            apiPaginationMapper
        )
    }

    @After
    fun teardown() {
        fakeServer.shutdown()
    }

    @Test
    fun requestMoreAnimals_success() = runBlocking {
        // Given
        val expectedAnimalId = 124L
        fakeServer.setHappyPathDispatcher()

        // When
        val paginatedAnimals = repository.requestMoreAnimals(1, 100)

        // Then
        val animal = paginatedAnimals.animals.first()
        assertThat(animal.id).isEqualTo(expectedAnimalId)
    }

    @Test
    fun insertAnimals_success() {
        // Given
        val expectedAnimalId = 124L

        runBlocking {
            fakeServer.setHappyPathDispatcher()

            val paginatedAnimals = repository.requestMoreAnimals(1, 100)
            val animal = paginatedAnimals.animals.first()

            // When
            repository.storeAnimals(listOf(animal))
        }

        // Then
        val testObserver = repository.getAnimals().test()

        testObserver.assertNoErrors()
        testObserver.assertNotComplete()
        testObserver.assertValue { it.first().id == expectedAnimalId }
    }
}