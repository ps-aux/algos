import {partition} from 'src/algo/sorting/quick-sort'
import {list} from 'src/algo/list'

describe('quick-onSort', () => {
    describe('partition()', () => {

        it('basic case', () => {
            const l = list([4, 3, 1, 8, 5, 2])
            const pivotIdx = partition(l, 0, 6)

            expect(l.toArray()).toEqual([2, 3, 1, 4, 5, 8])
            expect(pivotIdx).toBe(3)
        })


        it('pivot is smallest', () => {
            const l = list([1, 3, 2])
            const pivotIdx = partition(l, 0, 3)

            expect(l.toArray()).toEqual([1, 3, 2])
            expect(pivotIdx).toBe(0)

        })

        it('pivot is largest', () => {
            const l = list([10, 3, 2])
            const pivotIdx = partition(l, 0, 3)

            expect(l.toArray()).toEqual([2, 3, 10])
            expect(pivotIdx).toBe(2)

        })

        it('list of size 2', () => {
            const l = list([3, 2])
            const pivotIdx = partition(l, 0, 2)

            expect(l.toArray()).toEqual([2, 3])
            expect(pivotIdx).toBe(1)
        })

        it('pivot is duplicated', () => {
            const l = list([3, 3, 1, 5, 7, 2])
            const pivotIdx = partition(l, 0, 6)

            expect(l.toArray()).toEqual([2, 3, 1, 3, 7, 5])
            expect(pivotIdx).toBe(3)

        })
    })


})