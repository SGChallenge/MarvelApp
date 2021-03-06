package com.example.marvelchallenge

import com.example.marvelchallenge.data.di.ApiModule
import com.example.marvelchallenge.data.networking.MarvelService
import com.example.marvelchallenge.utils.toMD5
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class ServiceTests {
    private val service: MarvelService = ApiModule.provideMarvelService()
    private val testDispatcher = TestCoroutineDispatcher()
    private val managedCoroutineScope: ManagedCoroutineScope = TestScope(testDispatcher)
    private val ts = System.currentTimeMillis()
    private val hash = (ts.toString() + BuildConfig.PRIVATE_API_KEY + BuildConfig.API_KEY).toMD5()

    @Test
    fun getCharacters_test() {
        managedCoroutineScope.launch {
            assertTrue(service.getPagedCharacters(ts, 0, hash).data.results.isNotEmpty())
        }
    }

    @Test
    fun getEvents_test() {
        managedCoroutineScope.launch {
            assertTrue(service.getEvents(ts, hash).data.results.isNotEmpty())
        }
    }
}

interface ManagedCoroutineScope : CoroutineScope {
    fun launch(block: suspend CoroutineScope.() -> Unit): Job
}

@OptIn(ExperimentalCoroutinesApi::class)
class TestScope(override val coroutineContext: CoroutineContext) : ManagedCoroutineScope {
    private val scope = TestCoroutineScope(coroutineContext)
    override fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch {
            block.invoke(this)
        }
    }
}
