import {range} from 'ramda'
import bfs from 'src/algo/graph/bfs'

const link = (source, target) => ({source, target})

const createNodes = count => range(1, count + 1).map(id => ({id}))

const createLinks = (...links) => links.map(([n1, n2]) => link(n1, n2))


describe('breadth first search', () => {

    const nodes = createNodes(5)
    const links = createLinks(
        [1, 2],
        [2, 3],
        [3, 4],
        [4, 5]
    )

    const g = {nodes, links}

    console.log('nodes', nodes)
    console.log('links', links)

    it('find a node', () => {
        const r = bfs(g, 1, 5)

        expect(r[0]).toBe(nodes[0])
        expect(r[1]).toBe(nodes[1])

        expect(r[r.length -1]).toBe(nodes[4])

    })
})