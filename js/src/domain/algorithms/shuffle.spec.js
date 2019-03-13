import { shuffle } from 'src/domain/algorithms/shuffle'
import { List } from 'src/domain/data-structures/list'

const isSorted = list => {
    for (let i = 0; i < list.length - 1; i++)
        if (list[i] > list[i + 1]) return false
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
