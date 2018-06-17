import { allWords } from 'src/domain/algorithms/other/impl/all-words'

it('char combination are calculated properly', () => {
    expect(allWords('abc')).toEqual(
        expect.arrayContaining([
            '', 'a', 'b', 'c',
            'ab', 'ba', 'bc', 'cb', 'ac', 'ca',
            'abc', 'acb', 'bac', 'bca', 'cab', 'cba'
        ]))

})