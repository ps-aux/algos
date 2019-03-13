import { anyPass, propEq } from 'ramda'

export const getNode = ({ nodes }, id) => nodes.find(propEq('id', id))

export const reconstructPath = (g, srcMap, last) => {
    const path = [getNode(g, last)]
    let srcNode = getNode(g, srcMap[last])

    while (srcNode) {
        path.unshift(srcNode)
        srcNode = getNode(g, srcMap[srcNode.id])
    }

    return path
}

export const getNeighbours = (g, id) =>
    g.links
        .filter(anyPass([propEq('source', id), propEq('target', id)]))
        .map(({ source, target }) =>
            source === id ? getNode(g, target) : getNode(g, source)
        )
