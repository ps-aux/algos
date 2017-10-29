import {swap} from 'src/old/sorting'

export const insertionSort = array => {

    for (let i = 0; i < array.length; i++) {
        // invariant: subarray <0, i) is sorted
        putInPlace(i)
    }

    function putInPlace (i) {
        while (i > 0 && array[i] < array[i - 1]) {
            swap(array, i, i - 1)
            i--
        }
    }
}

export const bubbleSort = array => {
    for (let i = array.length - 1; i > 0; i--) {
        // invariant: subarray <i, array.length) is sorted
        let j = 0
        while (j < i) {
            if (array[j] > array[j + 1])
                swap(array, j, j + 1)
            j++
        }
    }
}

export const selectionSort = array => {

    for (let i = 0; i < array.length; i++) {
        // invariant: subarray <0, i) is sorted and contains min elements
        const minIndex = findMin(i, array.length - 1)
        if (minIndex !== i)
            swap(array, i, minIndex)
    }

    function findMin(l, h) {
        let min = array[l]
        let minIndex = l
        for (let i = l + 1; i <= h; i++) {
            if (array[i] < min) {
                min = array[i]
                minIndex = i
            }
        }
        return minIndex
    }
}