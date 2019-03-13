import { range } from 'ramda'
import { link } from './graph'

const find = (nodes, x, y) => nodes.find(n => n.x === x && n.y === y)

const linkByCoords = (nodes, [x1, y1], [x2, y2], ...rest) =>
    link(find(nodes, x1, y1), find(nodes, x2, y2), ...rest)

const neighbours = (w, h, [x, y]) => {
    const res = []

    // Left
    if (x > 0) res.push([x - 1, y])
    // Right
    if (x < w - h) res.push([x + 1, y])
    // Top
    if (y > 0) res.push([x, y - 1])
    // Bottom
    if (y < h - 1) res.push([x, y + 1])

    return res
}

export const gridGraph = (h, w) => {
    const pairs = range(0, h).flatMap(y => range(0, w).map(x => [x, y]))

    const nodes = pairs.map(([x, y]) => ({
        x,
        y,
        id: y * w + x
    }))

    const links = pairs
        .flatMap(p => neighbours(w, h, p).map(n => [p, n]))
        .map(([c1, c2]) => linkByCoords(nodes, c1, c2, 4))

    return { nodes, links }
}
