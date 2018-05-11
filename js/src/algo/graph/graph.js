import {anyPass, propEq} from 'ramda'

export const getNode = ({nodes}, id) => nodes.find(propEq('id', id))

export const getNeighbours = (g, id) => g.links
    .filter(anyPass([
        propEq('source', id),
        propEq('target', id)
    ]))
    .map(({source, target}) => source === id ?
        getNode(g, target) : getNode(g, source))