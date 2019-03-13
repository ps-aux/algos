import { powerSet } from 'src/domain/algorithms/other/impl/power-set'
import { range } from 'ramda'

const set = (...items) => expect.arrayContaining(items)

describe('properly calculates powerSet', () => {
    it('of small set', () => {
        const ps = powerSet([1, 2, 3])
        expect(ps.length).toBe(Math.pow(2, 3))
        expect(ps).toEqual(
            set(
                set(),
                set(1),
                set(2),
                set(3),
                set(1, 2),
                set(2, 3),
                set(1, 3),
                set(1, 2, 3)
            )
        )
    })

    it('of big set', () => {
        expect(powerSet(range(0, 20)).length).toBe(Math.pow(2, 20))
    })
})
