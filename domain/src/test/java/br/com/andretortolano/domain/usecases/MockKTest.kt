package br.com.andretortolano.domain.usecases

import io.mockk.MockKAnnotations
import org.junit.Before

abstract class MockKTest {

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }
}