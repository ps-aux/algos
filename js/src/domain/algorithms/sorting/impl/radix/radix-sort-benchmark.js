import { benchmarkSort } from 'src/domain/algorithms/sorting/array-sort-benchmark'
import { radixSort } from 'src/domain/algorithms/sorting/impl/radix/radix-sort'

benchmarkSort('counting sort (small range)', radixSort, {maxValue: 100})
benchmarkSort('counting sort (big range)', radixSort, {maxValue: 1000000})
