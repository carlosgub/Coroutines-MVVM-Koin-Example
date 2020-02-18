package com.carlosgub.coroutines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.presentation.model.PostVM
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostViewModel
import com.carlosgub.coroutines.features.books.presentation.viewmodel.state.PostVS
import com.carlosgub.coroutines.utils.CoroutinesRule
import com.carlosgub.coroutines.utils.getOrAwaitValue
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import org.junit.*

class PostViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = CoroutinesRule()

    private val useCaseMock = mockk<GetPostsInteractor>()
    private lateinit var viewModel: PostViewModel
    private val observer = mockk<Observer<PostVS>>(relaxed = true)
    private val dataObserver = mockk<Observer<List<PostVM>>>(relaxed = true)

    @Before
    fun setup() {
        viewModel = PostViewModel(useCaseMock)
        viewModel.viewState.observeForever(observer)
        viewModel.data.observeForever(dataObserver)
    }

    @Test
    fun `retrieve posts successful`() {
        coEvery { useCaseMock.execute(Interactor.None) } returns TestData.dataList.asFlow()
        viewModel.getPosts()
        viewModel.data.getOrAwaitValue()
        verify(exactly = 1) { dataObserver.onChanged(any()) }
    }

    @Test
    fun `retrieve posts failure`() {
        coEvery { useCaseMock.execute(Interactor.None) } throws UnsupportedOperationException()
        viewModel.getPosts()
        viewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<PostVS.Error>()) }
    }

    @Suppress("UNREACHABLE_CODE")
    @Test(expected = UnsupportedOperationException::class)
    fun `retrieve posts with exception`() {
        coEvery { useCaseMock.execute(Interactor.None) } returns throw UnsupportedOperationException()
        viewModel.getPosts()
    }
}
