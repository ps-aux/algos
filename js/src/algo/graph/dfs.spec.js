import {testGraphSearch} from 'src/algo/graph/graphTest'
import dfs from 'src/algo/graph/dfs'

describe('depth first search', () => {

    testGraphSearch(dfs)
})