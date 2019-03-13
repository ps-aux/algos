import { range } from 'ramda'

export const swap = (array, i, j) => {
    const tmp = array[i]
    array[i] = array[j]
    array[j] = tmp
}

export const randomArray = n =>
    range(0, n).map(() => Math.floor(Math.random() * (n + 1)) + 1)
