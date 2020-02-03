package com.carlosgub.coroutines

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostViewModel
import com.carlosgub.coroutines.features.books.presentation.viewmodel.state.PostVS
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CoroutinesTest {

    @Mock
    private lateinit var context: Application

    private lateinit var viewModel: PostViewModel

    private lateinit var onRenderMuseumsObserver: Observer<PostVS>

    private val fakePostInteractor = GetPostsInteractor(mockk())

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`<Context>(context.applicationContext).thenReturn(context)
        Dispatchers.setMain(testDispatcher)
        setupObservers()
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `retrieve posts with ViewModel and Interactor returns empty data`() {
        viewModel = PostViewModel(fakePostInteractor)

        with(viewModel) {
            viewModel.getPosts()
            viewModel.posts.observeForever(onRenderMuseumsObserver)
        }

        runBlockingTest {
            every {
                fakePostInteractor.execute(Interactor.None)
            } returns DataFake.dataEmpty.asFlow()
            Assert.assertTrue(response is Flow<PostEntity>)
            Assert.assertTrue(response.toList().isEmpty())

            Assert.assertTrue(viewModel.posts.value == PostVS.AddPost)
        }
    }

    @Test
    fun `retrieve post with ViewModel and Interactor returns full data`() {
        viewModel = PostViewModel(fakePostInteractor)

        with(viewModel) {
            getPosts().observeForever(onRenderMuseumsObserver)
        }

        runBlockingTest {
            val response = fakePostInteractor.execute(Interactor.None)
            Assert.assertTrue(response is Flow<PostEntity>)
            Assert.assertTrue(response.toList().size == 3)
        }
    }

    @Test
    fun `retrieve posts with ViewModel and Interactor returns an error`() {
        viewModel = PostViewModel(fakeErrorPostInteractor)
        with(viewModel) {
            getPosts().observeForever(onRenderMuseumsObserver)
        }

        runBlockingTest {
            val response = fakeErrorPostInteractor.execute(Interactor.None)
            Assert.assertTrue(response is Flow<PostEntity>)
            response.catch {
                Assert.assertNotNull(it.message)
            }

        }
    }

    private fun setupObservers() {
        onRenderMuseumsObserver = mock(Observer::class.java) as Observer<PostVS>
    }

}