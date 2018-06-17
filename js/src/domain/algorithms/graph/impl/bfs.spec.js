import bfs from 'src/domain/algorithms/graph/impl/bfs'
import {testGraphSearch} from 'src/domain/algorithms/graph/impl/graphTest'

describe('breadth first search', () => {
    testGraphSearch(bfs)

})