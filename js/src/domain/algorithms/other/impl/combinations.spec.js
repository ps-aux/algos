import { combinations } from 'src/domain/algorithms/other/impl/combinations'

it('all combinations calculated properly', () => {
    expect(combinations([1, 2, 3])).toEqual(
        expect.arrayContaining([
            [1, 2, 3],
            [1, 3, 2],
            [2, 1, 3],
            [2, 3, 1],
            [3, 2, 1],
            [3, 1, 2]
        ])
    )
})
