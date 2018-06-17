import {testGraphSearch} from 'src/domain/algorithms/graph/impl/graphTest'
import dfs from 'src/domain/algorithms/graph/impl/dfs'

describe('depth first search', () => {

    testGraphSearch(dfs)
})