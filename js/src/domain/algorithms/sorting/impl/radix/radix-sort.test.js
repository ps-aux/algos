import { testSort } from 'src/domain/algorithms/sorting/array-sort-test'
import {
    countDigits,
    getDigitAtPosition,
    radixSort,
    sortByDigitPosition
} from 'src/domain/algorithms/sorting/impl/radix/radix-sort'

it('digitAtPosition', () => {
    expect(getDigitAtPosition(123, 0)).toBe(3)
    expect(getDigitAtPosition(123, 1)).toBe(2)
    expect(getDigitAtPosition(123, 2)).toBe(1)
})

it('sortByDigitPosition', () => {
    expect(sortByDigitPosition([123, 451, 992], 0)).toEqual([451, 992, 123])
})

it('countDigits', () => {
    expect(countDigits(123456)).toBe(6)
})

testSort('radix-sort', radixSort, {
    maxNumber: 100,
    big: true,
    min: 100,
    max: 999
})
