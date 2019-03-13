import { always, range } from 'ramda'

const search = (arr, val, start = 0, end = 100) => {
    if (start > end) throw new Error('start > end')

    if (start === end) return -1

    const mid = start + Math.ceil((end - start) / 2)
    const v = arr[mid - 1]

    if (v === val) return mid - 1

    if (v === -1 || v > val) return search(arr, val, start, mid - 1)

    return search(arr, val, mid, end)
}

describe('test', () => {
    const test = (val, arr, expected) => () =>
        expect(search(arr, val)).toBe(expected)

    const arr = [3, 4, 5, 6, 7, 8, 9].concat(range(0, 300).map(always(-1)))
    it('case 1', test(5, arr, 2))

    it('case 2', test(9, arr, 6))

    it('case 2', test(3, arr, 0))
})
