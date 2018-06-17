import bfs from 'src/domain/algorithms/graph/bfs'
import {testGraphSearch} from 'src/domain/algorithms/graph/graphTest'

describe('breadth first search', () => {
    testGraphSearch(bfs)

})