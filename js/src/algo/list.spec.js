import {list} from 'src/algo/list'

describe('list', () => {

    it('toArray()', () => {
        const l = list([1, 2, 3])
        expect(l.toArray()).toEqual([1, 2, 3])
    })

    it('swap()', () => {
        const l = list([1, 2, 3])
        l.swap(0, 1)
        expect(l.toArray()).toEqual([2, 1, 3])
    })

    it('clone()', () => {
        const l = list([1, 2, 3])
        expect(l.clone().toArray()).toEqual([1, 2, 3])
    })

})