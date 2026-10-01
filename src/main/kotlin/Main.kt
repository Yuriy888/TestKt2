package com.ds


import java.util.Scanner
import kotlin.time.Duration
import kotlin.time.measureTime

fun main() {
    // Створення об'єкта для введення даних з консолі
    val scanner = Scanner(System.`in`)

    // Вводимо розмірність масиву
    println("Введіть розмірність масиву: ")
    val n = scanner.nextInt()
    var timeToRun : Duration = Duration.ZERO

    // Створення масиву double з введеного розміру
    val array = DoubleArray(n)
    var exitTo : Boolean = false
    // Вводимо елементи масиву
    println("Введіть $n чисел:")
    for (i in 0 until n) {
        array[i] = scanner.nextDouble()
    }
    while (!exitTo) {
        println("\n Оберіть метод сортування:")
        println("1 - Бульбашка")
        println("2 - Швидке сортування")
        println("3 - Гончарка (Heap Sort)")
        println("4 - Бульбашка моя")
        println("6 - Вставка")
        println("5 - Вихід")

        val choice = scanner.nextInt()

        when (choice) {
            1 -> bubbleSort(array)
            4 ->  timeToRun = bubbleSort2(array)
            2 -> quickSort(array, 0, n - 1)
            3 -> heapSort(array)
            6 -> insertSort(array)
            5 -> exitTo = true
            else -> {
                println("Неправильний вибір")
            }
        }

        // Виведення відсортованого масиву
        println("час на сотування ${timeToRun.inWholeMicroseconds} mc")
        println("Відсортований масив:")
        for (element in array) {
            print("$element ")
        }
    }
}

// Функція для сортування масив методом бульбашки
fun bubbleSort(array: DoubleArray) {
    val n = array.size

    // Проведення n-1 проходів через масив
    for (i in 0 until n - 1) {
        // На кожному проході найбільший елемент переміщується в кінець масиву
        for (j in 0 until n - i - 1) {
            if (array[j] > array[j + 1]) {
                // Обмін елементів
                val temp = array[j]
                array[j] = array[j + 1]
                array[j + 1] = temp
            }
        }
    }
}


// Сортування методом вставки.
// порівнюються сусідні значення
fun insertSort(array: DoubleArray) {
    val lenArrays = array.size
    for (i in 1 until lenArrays) {
        val key = array[i]
        var j = i-1
        while (j >=0 && array[j] > key) {
            array[j+1] = array[j]
            j--

        }
        array[j+1] = key

    }
}
fun bubbleSort2(array: DoubleArray) : kotlin.time.Duration {
    val time: kotlin.time.Duration = measureTime {
        val n = array.size
        for (i in 0 until n - 1) {
            for (j in i + 1 until n) {
                if (array[i] > array[j]) {
                    val temp = array[i]
                    array[i] = array[j]
                    array[j] = temp
                }
            }
        }
    }
    return time
}


// Функція для сортування масива методом швидкого сортування
fun quickSort(array: DoubleArray, low: Int, high: Int) {
    if (low < high) {
        val pivotIndex = partition(array, low, high)
        quickSort(array, low, pivotIndex - 1)
        quickSort(array, pivotIndex + 1, high)
    }
}

// Функція для розміщення елементів щодо опорного елемента
fun partition(array: DoubleArray, low: Int, high: Int): Int {
    val pivot = array[high]
    var i = low - 1

    for (j in low until high) {
        if (array[j] < pivot) {
            i++
            // Обмін елементів
            val temp = array[i]
            array[i] = array[j]
            array[j] = temp
        }
    }

    // Обмін опорного елемента з елементом на позиції i + 1
    val temp = array[i + 1]
    array[i + 1] = array[high]
    array[high] = temp

    return i + 1
}

// Функція для сортування масива методом гончарки (Heap Sort)
fun heapSort(array: DoubleArray) {
    val n = array.size

    // Створення будівництва багатов’язкого дерева
    for (i in (n / 2 - 1).downTo(0)) {
        heapify(array, n, i)
    }

    // Вибираємо елементи з кінця масиву
    for (i in n - 1 downTo 1) {
        // Переміщуємо root на місце
        val temp = array[0]
        array[0] = array[i]
        array[i] = temp

        // Викликаємо heapify для оновленого масиву
        heapify(array, i, 0)
    }
}

// Функція для опортування багатов’язкого дерева
fun heapify(array: DoubleArray, n: Int, i: Int) {
    var largest = i // Root
    val left = 2 * i + 1       // Левий дочірній
    val right = 2 * i + 2      // Правий дочірній

    // Якщо лівий дочірній більший за root
    if (left < n && array[left] > array[largest]) {
        largest = left
    }

    // Якщо правий дочірній більший за найбільший до теперішнього моменту
    if (right < n && array[right] > array[largest]) {
        largest = right
    }

    // Якщо найбільшим стала не root, змінюємо root та повторюємо heapify
    if (largest != i) {
        val swap = array[i]
        array[i] = array[largest]
        array[largest] = swap

        // Рекурсивний виклик для піддерева з заміненим root-ом
        heapify(array, n, largest)
    }
}
