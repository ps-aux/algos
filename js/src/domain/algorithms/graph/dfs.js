import {getNeighbours, reconstructPath} from 'src/domain/algorithms/graph/graph'


const dfs = (g, from, to, visited = {}, srcMap = {}) => {
    if (from === to)
        return reconstructPath(g, srcMap, to)
    visited[from] = true

    const unvisitedNeighbours = getNeighbours(g, from)
        .filter(n => !visited[n.id])

    for (let n of unvisitedNeighbours) {
        srcMap[n.id] = from
        const res = dfs(g, n.id, to, visited, srcMap)
        if (res)
            return res
    }

}


export default dfs