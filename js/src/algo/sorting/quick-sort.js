import assert from 'assert'

/**
 * _Array on <l, h)
 */
export const quickSort = (list, l = 0, h = list.length) => {
    // _Array of 1 element or empty list is sorted
    if (h - l <= 1)
        return

    const pivot = partition(list, l, h)
    // Before pivot
    quickSort(list, l, pivot)

    // After pivot
    quickSort(list, pivot + 1, h)
}

export const partition = (list, l, h) => {
    assert(l != null)
    assert(h != null)
    // _Array of 1 element or empty list is partitioned
    if (h - l <= 1)
        return

    const pivot = l
    const pivotVal = list[pivot]
    let li = l + 1
    let hi = h - 1

    // While pointers are not crossed
    while (li <= hi) {
        while (list[li] <= pivotVal && li < h) // Prevent going outside of the bounds
            li++
        while (list[hi] > pivotVal && hi > l)
            hi--
        assert(li !== hi)
        if (li < hi &&
            li < h) // Do not swap if li is out of bounds (for every x in list  <= pivot )
            list.swap(li, hi)
    }

    // Put pivot to the border between left and right part
    list.swap(pivot, li - 1)
    return hi
}