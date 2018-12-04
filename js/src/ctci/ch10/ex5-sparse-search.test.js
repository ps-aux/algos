import { always, range } from 'ramda'

const search = (arr, val, start = 0, end = 100) => {

    let mid = start + Math.ceil((start + end) / 2)
}

describe('test', () => {

    const test = (val, arr, expected) => () =>
        expect(search(arr, val)).toBe(expected)

    const arr = ['at', '', '', '', 'ball', '', '', 'car', '', '', 'dad', '', '']
    it('case 1',
        test(5, arr, 2))

})

