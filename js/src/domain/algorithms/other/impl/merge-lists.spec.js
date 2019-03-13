import { mergeLists } from 'src/domain/algorithms/other/impl/merge-lists'

it('merges 2 sorted lists', () => {
    const a = [1, 4, 8]
    const b = [1, 2, 5, 6]

    const c = a.concat(b).sort()

    expect(mergeLists(a, b)).toEqual(c)
})
