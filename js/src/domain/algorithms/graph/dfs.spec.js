import { testGraphSearch } from 'src/domain/algorithms/graph/graphTest'
import dfs from 'src/domain/algorithms/graph/dfs'

describe('depth first search', () => {
    testGraphSearch(dfs)
})
