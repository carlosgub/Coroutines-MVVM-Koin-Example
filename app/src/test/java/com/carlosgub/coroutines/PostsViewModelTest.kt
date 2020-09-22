package com.carlosgub.coroutines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostsViewModel
import com.carlosgub.coroutines.features.books.presentation.viewmodel.state.PostVS
import com.carlosgub.coroutines.features.books.presentation.viewmodel.state.PostsVS
import com.carlosgub.coroutines.utils.CoroutinesRule
import com.carlosgub.coroutines.utils.getOrAwaitValue
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import org.junit.*

class PostsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = CoroutinesRule()

    private val useCaseMock = mockk<GetPostsInteractor>()
    private lateinit var viewModel: PostsViewModel
    private val observer = mockk<Observer<PostsVS>>(relaxed = true)

    @Before
    fun setup() {
        viewModel = PostsViewModel(useCaseMock)
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `retrieve posts successful`() {
        coEvery { useCaseMock.execute(Interactor.None) } returns TestData.dataList.asFlow()
        viewModel.getPosts()
        viewModel.viewState.getOrAwaitValue()
        verify(exactly = 3) { observer.onChanged(any<PostsVS.AddPost>()) }
    }

    @Test
    fun `retrieve posts failure`() {
        coEvery { useCaseMock.execute(Interactor.None) } throws UnsupportedOperationException()
        viewModel.getPosts()
        viewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<PostsVS.Error>()) }
    }

    @Suppress("UNREACHABLE_CODE")
    @Test(expected = UnsupportedOperationException::class)
    fun `retrieve posts with exception`() {
        coEvery { useCaseMock.execute(Interactor.None) } returns throw UnsupportedOperationException()
        viewModel.getPosts()
    }
}
