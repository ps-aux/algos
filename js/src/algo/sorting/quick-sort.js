import {swap} from 'src/old/sorting'
import assert from 'assert'

/**
 * Array on <l, h)
 */
export const quickSort = (array, l = 0, h = array.length) => {
    // Array of 1 element or empty array is sorted
    if (h - l <= 1)
        return

    const pivot = partition(array, l, h)
    quickSort(array, l, pivot)
    quickSort(array, pivot + 1, h)
}

export const partition = (array, l, h) => {
    assert(l != null)
    assert(h != null)
    // Array of 1 element or empty array is partitioned
    if (h - l <= 1)
        return

    const pivot = l
    const pivotVal = array[pivot]
    let li = l + 1
    let hi = h - 1

    while (li < hi) {
        while (array[li] <= pivotVal && li < h) // Prevent going outside of the bounds
            li++
        while (array[hi] > pivotVal && hi > l)
            hi--
        assert(li !== hi)
        if (li < hi &&
            li < h) // Do not swap if li is out of bounds (for every x in array  <= pivot )
            swap(array, li, hi)
    }
    swap(array, pivot, li - 1)
    return hi
}