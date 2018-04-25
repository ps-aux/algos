export const mergeSort = list => {
    doMergeSort(list)
    return list
}


const doMergeSort = (list,
                     aux = new Array(list.length),
                     l = 0, h = list.length) => {
    const len = h - l
    if (len < 2)
        return // _Array of size 0 & 1 => Sorted already

    const m = Math.floor(l + len / 2)
    doMergeSort(list, aux, l, m)
    doMergeSort(list, aux, m, h)
    mergeArrays(list, aux, l, m, h)

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
