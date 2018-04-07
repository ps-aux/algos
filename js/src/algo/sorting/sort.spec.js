import {random} from 'faker'
import {mergeSort, sorts} from 'src/algo/sorting/index'
import {range, subtract} from "ramda"


const randomArray = (size = 10) =>
    range(0, size).map(random.number)

const testMutableSort = (sort, arr) => {
    const src = arr.slice()
    sort(src)

    const expected = arr.slice()
    expected.sort(subtract)

    expect(src)
        .toEqual(expected)

}

const mutableSortTests = (name, impl) => {
    describe(name, () => {
        it('basic data', () => {
            testMutableSort(impl, [3, 1, 2])
        })

        it('random generated data', () => {
            testMutableSort(impl, randomArray(20))
        })
    })
}

describe('sort', () => {
    Object.entries(sorts)
        .forEach(([name, impl]) =>
            mutableSortTests(name, impl))
})