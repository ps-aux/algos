import { range } from 'ramda'
import { link } from './graph'

export const radialGraph = count => {
    const nodes = range(0, count).map(id => ({ id }))
    const [first, ...rest] = nodes
    const links = rest.map(n => link(first, n, { value: 100 }))
    return { nodes, links }
}
