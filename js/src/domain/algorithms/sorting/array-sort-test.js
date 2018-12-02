import { assertSorted } from 'src/domain/algorithms/sorting/asserts'
import { range, reverse } from 'ramda'
import { randomArray } from 'src/domain/algorithms/sorting/data/dataGen'

const reversed = reverse(range(0, 50))

export const testSort = (name, alg, {max, big = true, min}) => {

    const testSort = data => () => {
        const r = alg(data)
        expect(r).toHaveLength(data.length)
        assertSorted(r)
    }

    const testRandom = size => testSort(
        randomArray(size, {min, max}))

    describe(name, () => {
        describe('basic', () => {
            it('small array', testSort([2, 3, 1]))
            it('reversed', testSort(reversed))
            it('100 elements', testRandom(100))
        })

        big && describe(`bigger dataset, max=${max}`, () => {

            it('1000 elements', testRandom(1000))
            it('10 000 elements', testRandom(10000))
            it('100 000 elements', testRandom(100000))
            it('1 000 000 elements', testRandom(1000000))
            it('10 000 000 elements', testRandom(10000000))

        })

        describe('edge cases', () => {
            it('single', testSort([1]))
            it('empty', testSort([]))
            it('almost same values', testSort([2, 2, 2, 2, 1, 2, 2]))
            it('last value', testSort([1, 2, 4, 3]))
        })
    })
}
