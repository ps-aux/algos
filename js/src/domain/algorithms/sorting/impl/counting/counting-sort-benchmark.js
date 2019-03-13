import { benchmarkSort } from 'src/domain/algorithms/sorting/array-sort-benchmark'
import { countingSort } from 'src/domain/algorithms/sorting/impl/counting/counting-sort'

benchmarkSort('counting sort (small range)', countingSort, { max: 100 })
benchmarkSort('counting sort (bigger range)', countingSort, { min: 10000 })
