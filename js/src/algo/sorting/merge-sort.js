export const mergeSort = (array) => {
    doMergeSort(array)
}


const doMergeSort = (array,
                     aux = new Array(array.length),
                     l = 0, h = array.length) => {
    const len = h - l
    if (len < 2)
        return // Array of size 0 & 1 => Sorted already

    const m = Math.floor(l + len / 2)
    doMergeSort(array, aux, l, m)
    doMergeSort(array, aux, m, h)
    mergeArrays(array, aux, l, m, h)
}


const mergeArrays = (array, aux, l, m, h) => {
    for (let i = l; i < h; i++)
        aux[i] = array[i]

    let i = l
    let j = m

    const moveFirst = k =>
        array[k] = aux[i++]

    const moveSecond = k =>
        array[k] = aux[j++]

    const isFirstDone = () => i === m

    const isSecondDone = () => j === h

    let firstDone = isFirstDone()
    let secondDone = isSecondDone()

    let k = l
    while (!firstDone || !secondDone) {
        if (firstDone)
            moveSecond(k)
        else if (secondDone)
            moveFirst(k)
        else if (aux[i] < aux[j])
            moveFirst(k)
        else
            moveSecond(k)
        k++

        firstDone = isFirstDone()
        secondDone = isSecondDone()
    }
}

export default {
    _mergeArrays: mergeArrays
}
