import { always, range } from 'ramda'

const search = (arr, val, start = 0, end = arr.length) => {
    // singleton
    if (start === end - 1) {
        return arr[start] === val ? start : -1
    }

    const firstMid = start + Math.floor((end - start) / 2)

    // console.log('firstMid', firstMid)
    let mid = firstMid
    let v = arr[mid]
    while (v === '') {
        // Could go in both directions in one tick
        mid--
        if (mid < 0) return -1
        v = arr[mid]
    }

    // console.log('mid', mid)
    // console.log('val', v)
    if (v === val) return mid
    if (mid === start) return -1
    if (v < val) return search(arr, val, firstMid + 1, end)
    else return search(arr, val, start, mid)
}

describe('test', () => {
    const test = (val, arr, expected) => () =>
        expect(search(arr, val)).toBe(expected)

    const arr = [
        '',
        '',
        'at',
        '',
        '',
        '',
        'ball',
        '',
        '',
        'car',
        '',
        '',
        'dad',
        '',
        ''
    ]

    it('case 1', test('ball', arr, 6))
    it('case 2', test('at', arr, 2))
    it('case 3', test('not', arr, -1))
})
