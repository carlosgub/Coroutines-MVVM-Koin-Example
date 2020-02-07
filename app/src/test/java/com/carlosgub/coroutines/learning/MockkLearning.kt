package com.carlosgub.coroutines.learning

import io.mockk.*
import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * @author Carlos Ugaz
 * @see https://blog.kotlin-academy.com/mocking-is-not-rocket-science-mockk-features-e5d55d735a98
 */

class MockkLearning {
    class Divider {
        fun divide(a: Int, b: Int) = a / b
    }

    @Test
    fun `trying slot by mockK`() {
        val slot = slot<Int>() //Almacena un valor
        val mock = mockk<Divider>()

        /**
         * Si se usa la funcion divide de la clase Divider
         * este regresara 22, pero aparte el capture(slot) obtendra el valor
         * ingresado en el primer parametro y lo guardara en la variable slot
         * PD: el any() es que no importa el segundo valor traera 22, el capture(x)
         * tambien seria como un any() pero que captura el valor ingresado
         */
        every { mock.divide(capture(slot), any()) } returns 22

        /**
         * Se esta llamando la clase divide y cualquier valor que se ingrese
         * regresara 22, ahora como se ingreso 5 en el primer parametro se le asignara
         * ese valor a la variable slot
         * slot <- 5
         */
        mock.divide(5, 2)

        /**
         * Verificamos que el valor asignado a slot sea 5
         * Si es 5 el Test pasara sin problema
         */
        assertEquals(5, slot.captured)
    }

    @Test
    fun `trying slot by mockK with answers`() {
        val slot = slot<Int>() //Almacena un valor
        val mock = mockk<Divider>()

        /**
         * Es la misma explicacion del ejercicio anterior,
         * la diferencia que el valor a retornar si llamamos la
         * funcion divide seria el valor del primer parametro * 11
         */
        every {
            mock.divide(capture(slot), any())
        } answers {
            slot.captured * 11
        }

        /**
         * Es la misma explicacion del ejercicio anterior,
         * solo que ahora el valor que regresa es 55
         */
        val result = mock.divide(5, 2)

        /**
         * Verificamos que la variable result tenga asignado el valor 55
         */
        assertEquals(55, result)
    }

    @Test
    fun `trying slot like mutableList by mockK with `() {
        val slot = mutableListOf<Int>() //Almacena un conjunto de valores
        val mock = mockk<Divider>()

        /**
         * En este caso slot puede almacenar muchas variables
         */
        every {
            mock.divide(capture(slot), any())
        } answers {
            slot[0] * 11
        }

        /**
         * Es la misma explicacion del ejercicio anterior
         */
        val result = mock.divide(5, 2)

        /**
         * Verificamos que la variable result tenga asignado el valor 55
         */
        assertEquals(55, result)
    }

    @Test
    fun `trying relaxed mocks when we dont want to declare some return in every callback `() {
        /**
         * Usamos (relaxed = true) cuando no usaremos el every, ya que
         * no sabemos el comportamiento de la clase que estamos 'mockeando' y queremos que mockK
         * nos retorne un valor basico como 0 o null
         */
        val mock = mockk<Divider>(relaxed = true)

        /**
         * Verificamos que la division sea 0 (porque la funcion regresa un entero (INT) y el defecto es 0)
         * , ya que no hemos agregado el valor que deberia regrear anteriormente.
         */
        verify(exactly = 0) { mock.divide(5, 2) }
    }

    class Adder {
        fun magnify(a: Int) = a

        fun add(a: Int, b: Int) = a + magnify(b)
    }

    @Test
    fun `trying Spies `() {

        val spy = spyk(Adder())

        /**
         * Modifica la funcion magnify de la clase Adder regresando el valor de magnify * 2
         */
        every { spy.magnify(any()) } answers { firstArg<Int>() * 2 }

        /**
         * Verificamos si el valor 5 se multiplica por 2, por lo anterior dicho
         * y al sumarse con el 4 el resultado deberia ser 14.
         */
        assertEquals(14, spy.add(4, 5))
    }


}
