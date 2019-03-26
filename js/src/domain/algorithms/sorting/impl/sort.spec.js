import { subtract } from 'ramda'
import { List } from 'src/domain/data-structures/list'
import { randomArray } from 'src/domain/algorithms/sorting/data/dataGen'
import { sorts } from 'src/domain/algorithms/sorting/impl/index'

const testSort = (sort, arr) => {
    const expected = arr.slice()
    expected.sort(subtract)

    const lst = List(arr)

    expect(sort(lst).toArray()).toEqual(expected)
}

const sortTests = (name, impl) => {
    describe(name, () => {
        it('basic data', () => testSort(impl, [3, 1, 2]))

        it('random generated data', () => testSort(impl, randomArray(20)))
    })
}

describe('sort', () =>
    Object.entries(sorts).forEach(([name, impl]) => sortTests(name, impl)))
