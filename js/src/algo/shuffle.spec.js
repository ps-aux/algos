import {shuffle} from 'src/algo/shuffle'
import {List} from 'src/algo/list'

const isSorted = list => {
    for (let i = 0; i < list.length - 1; i++)
        if (list[i] > list[i + 1])
            return false
}

describe('isSorted()', () => {
    it('detects unsorted', () => {
        expect(isSorted([1, 3, 2])).toBeFalsy()
    })
})


describe('shuffle', () => {
    it('on sorted array', () => {
        const l = shuffle(List([1, 2, 3]))
        expect(isSorted(l)).toBeFalsy()
    })
})
