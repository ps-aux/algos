import {partition} from 'src/domain/algorithms/sorting/impl/quick-sort'
import {List} from 'src/domain/data-structures/list'

describe('quick-onSort', () => {
    describe('partition()', () => {

        it('basic case', () => {
            const l = List([4, 3, 1, 8, 5, 2])
            const pivotIdx = partition(l, 0, 6)

            expect(l.toArray()).toEqual([2, 3, 1, 4, 5, 8])
            expect(pivotIdx).toBe(3)
        })


        it('pivot is smallest', () => {
            const l = List([1, 3, 2])
            const pivotIdx = partition(l, 0, 3)

            expect(l.toArray()).toEqual([1, 3, 2])
            expect(pivotIdx).toBe(0)

        })

        it('pivot is largest', () => {
            const l = List([10, 3, 2])
            const pivotIdx = partition(l, 0, 3)

            expect(l.toArray()).toEqual([2, 3, 10])
            expect(pivotIdx).toBe(2)

        })

        it('List of size 2', () => {
            const l = List([3, 2])
            const pivotIdx = partition(l, 0, 2)

            expect(l.toArray()).toEqual([2, 3])
            expect(pivotIdx).toBe(1)
        })

        it('pivot is duplicated', () => {
            const l = List([3, 3, 1, 5, 7, 2])
            const pivotIdx = partition(l, 0, 6)

            expect(l.toArray()).toEqual([2, 3, 1, 3, 7, 5])
            expect(pivotIdx).toBe(3)

        })
    })


})