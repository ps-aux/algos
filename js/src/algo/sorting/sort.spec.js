import {random} from 'faker'
import {mergeSort, sorts} from 'src/algo/sorting/index'
import {range, subtract} from "ramda"
import {List} from 'src/algo/list'


const randomArray = (size = 10) =>
    range(0, size).map(random.number)

const testSort = (sort, arr) => {

    const expected = arr.slice()
    expected.sort(subtract)

    const lst = List(arr)

    expect(sort(lst).toArray())
        .toEqual(expected)

}

const sortTests = (name, impl) => {
    describe(name, () => {
        it('basic data', () =>
            testSort(impl, [3, 1, 2]))

        it('random generated data', () =>
            testSort(impl, randomArray(20)))
    })
}

describe('sort', () =>
    Object.entries(sorts)
        .forEach(([name, impl]) =>
            sortTests(name, impl)))