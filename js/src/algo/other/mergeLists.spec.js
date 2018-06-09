import { mergeLists } from 'src/algo/other/mergeLists'

it('merges 2 sorted lists', () => {
    const a = [1, 4, 8]
    const b = [1, 2, 5, 6]

    const c = a.concat(b).sort()

    expect(mergeLists(a, b)).toEqual(c)
})