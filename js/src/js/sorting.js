import assert from 'assert'
import ArrayRecorder from './arrayRecorder'

/**
 *
 * @param array native array
 * @param a first index
 * @param b second index
 */
export function swap(array, a, b) {
    console.debug('swapping', a, b)
    assert(a !== b)
    const tmp = array[b]
    array[b] = array[a]
    array[a] = tmp
}

/**
 * Knuth implementation of shuffle
 */
export function shuffle(nativeArray) {
    const array = new ArrayRecorder(nativeArray)

    for (let i = 0; i < array.size(); i++) {
        const ran = getRandomInt(0, i)
        array.select(i)
        array.select(ran)
        if (i !== ran)
            array.swap(i, ran)
    }

    return array.result()
}

function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}


export function bubbleSort(nativeArray) {
    const array = new ArrayRecorder(nativeArray)

    for (let i = array.size(); i > 0; i--) {
        for (let j = 0; j < i; j++) {
            array.select(j)
            if (array.get(j) > array.get(j + 1)) {
                array.swap(j, j + 1)
                array.select(j + 1)
            }

        }
    }

    return array.result()
}

export function selectionSort(nativeArray) {
    const array = new ArrayRecorder(nativeArray)

    for (let i = 0; i < array.size(); i++) {
        array.select(i) // Visualization call

        var minIndex = i
        var minVal = array.get(i)
        // Find min
        for (let j = i + 1; j < array.size(); j++) {
            array.examine(j) //Visualization call
            const val = array.get(j)
            if (val < minVal) {
                minIndex = j
                minVal = val
            }
        }

        if (minIndex !== i)
            array.swap(i, minIndex)
    }

    return array.result()
}

export function insertionSort(nativeArray) {
    const array = new ArrayRecorder(nativeArray)

    for (let i = 1; i < array.size(); i++) {
        array.select(i) // Visualization call
        const val = array.get(i)
        var prev = i - 1
        while (array.get(prev) > val && prev >= 0) {
            // prev + 1 = index of the selected element
            array.swap(prev, prev + 1)
            array.select(prev) // Visualization call. Previous is now this
            --prev
        }

    }

    return array.result()
}

export function mergeSort(nativeArray) {
    const array = new ArrayRecorder(nativeArray)
    const auxArray = []

    sort(array, 0, array.size() - 1, auxArray)

    function sort(array, l, h, auxArray) {
        if (h === l)
            return
        const m = l + Math.floor((h - l) / 2)
        sort(array, l, m, auxArray)
        sort(array, m + 1, h, auxArray)
        merge(array, l, m, h, auxArray)

    }

    function merge(array, l, m, h, auxArray) {
        // Copy to array
        for (let i = l; i <= h; i++) {
            auxArray[i] = array.get(i)
        }

        var i = l
        var j = m + 1
        var k = l

        while (i < m + 1 || j < h + 1) {
            let left = auxArray[i]
            let right = auxArray[j]
            let selected = null

            if (i == m + 1) {
                selected = right
                j++
            } else if (j == h + 1) {
                selected = left
                i++
            }
            else if (right < left) {
                selected = right
                ++j
            } else {
                selected = left
                ++i
            }

            array.select(k)
            array.put(k++, selected)
        }
    }


    return array.result()
}

export function quickSort(nativeArray) {
    const array = new ArrayRecorder(nativeArray)

    function partition(array) {
        const pivot = array.get(0)


    }

    return array.result()
}

