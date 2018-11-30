import { testSort } from 'src/domain/algorithms/sorting/array-sort-test'
import {
    alternativeCountingSort,
    countingSort
} from 'src/domain/algorithms/sorting/impl/counting/counting-sort'

testSort('counting sort', countingSort, {max: 100})
testSort('alternative counting sort', alternativeCountingSort, {max: 100})

