import {getNeighbours, getNode, reconstructPath} from 'src/domain/algorithms/graph/graph'


const bfs = (g, from, to) => {

    const visited = {}
    const markToVisit = {}

    const src = {}

    const root = getNode(g, from)
    visited[root.id] = true

    const toVisit =  [root]

    let found = false
    while (toVisit.length) {
        const n = toVisit.pop()
        const id = n.id

        if (id === to) {
            found = true
            break
        }

        visited[id] = true

        getNeighbours(g, id)
            .filter(({id}) => !visited[id])
            .filter(n => !toVisit.includes(n))
            .forEach(n => {
                src[n.id] = id
                markToVisit[n.id] = true
                toVisit.unshift(n)
            })

    }

    if (!found)
        return

    return reconstructPath(g, src, to)
}


export default bfs