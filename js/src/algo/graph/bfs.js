import {anyPass, propEq} from 'ramda'


const getNode = ({nodes}, id) => nodes.find(propEq('id', id))

const getNeighbours = (g, id) => g.links
    .filter(anyPass([
        propEq('source', id),
        propEq('target', id)
    ]))
    .map(({source, target}) => source === id ?
        getNode(g, target) : getNode(g, source))

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

    // Reconstruct path
    const path = [getNode(g, to)]
    let srcNode = getNode(g, src[to])

    while (srcNode) {
        path.unshift(srcNode)
        srcNode = getNode(g, src[srcNode.id])
    }

    return path

}


export default bfs