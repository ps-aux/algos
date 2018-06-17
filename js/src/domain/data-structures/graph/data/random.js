import { range } from 'ramda'
import { link } from './graph'
import Chance from 'chance'

const chance = new Chance()

export const randomGraph = (nodeCount, linkCount) => {
    const nodes = range(0, nodeCount).map(id => ({id}))

    const rand = () => chance.integer({min: 0, max: nodes.length - 1})

    let c = 0
    const links = range(0, linkCount)
        .map(() => link(nodes[rand()], nodes[rand()], {id: c++}))

    return {nodes, links}
}