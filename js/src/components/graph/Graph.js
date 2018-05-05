import React from 'react'
import s from './Graph.sass'
import * as d3 from 'd3'
import {path, prop, range} from 'ramda'
import {NavMenuItem} from 'src/components/NavMenu'
import {Menu} from 'semantic-ui-react'
import Chance from 'chance'

const chance = new Chance()


const link = (n1, n2, value = 20) => ({
    source: n1.id,
    target: n2.id,
    value
})

const find = (nodes, x, y) =>
    nodes.find(n => n.x === x && n.y === y)

const linkByCoords = (nodes, [x1, y1], [x2, y2], ...rest) =>
    link(
        find(nodes, x1, y1),
        find(nodes, x2, y2),
        ...rest)

const neighbours = (w, h, [x, y]) => {
    const res = []

    // Left
    if (x > 0)
        res.push([x - 1, y])
    // Right
    if (x < w - h)
        res.push([x + 1, y])
    // Top
    if (y > 0)
        res.push([x, y - 1])
    // Bottom
    if (y < h - 1)
        res.push([x, y + 1])

    return res
}


const tickCount = 100


const layout = (svg, graph) => {

    const width = svg.attr('width')
    const height = svg.attr('height')

    return new Promise((resolve) => {
        const simulation = d3.forceSimulation()
            .alphaMin(1)
            .force('link', d3.forceLink()
                .id(d => d.id)
                .distance(prop('value')) )
            .force('charge', d3.forceManyBody())
            .force('center', d3.forceCenter(width / 2, height / 2))


        simulation.nodes(graph.nodes)

        simulation.force('link').links(graph.links)

        simulation.on('end', resolve(graph))

        range(1, tickCount)
            .forEach(() => {
                simulation.tick()
            })
    })
}

const render = (svg, graph) => {
    svg.append('g')
        .attr('class', 'links')
        .selectAll('line')
        .data(graph.links)
        .enter().append('line')
        .attr('x1', path(['source', 'x']))
        .attr('y1', path(['source', 'y']))
        .attr('x2', path(['target', 'x']))
        .attr('y2', path(['target', 'y']))

    svg.append('g')
        .attr('class', 'nodes')
        .selectAll('circle')
        .data(graph.nodes)
        .enter().append('circle')
        .attr('r', 5)
        .attr('cx', prop('x'))
        .attr('cy', prop('y'))
        .attr('fill', 'black')
}


const grid = (h, w) => {

    const pairs = range(0, h)
        .flatMap(y => range(0, w)
            .map(x => [x, y]))

    const nodes = pairs
        .map(([x, y]) => ({
            x, y, id: y * w + x
        }))

    const links = pairs
        .flatMap(p =>
            neighbours(w, h, p)
                .map(n => [p, n]))
        .map(([c1, c2]) => linkByCoords(nodes, c1, c2, 4))


    return {nodes, links}

}

const radial = count => {
    const nodes = range(0, count).map(id => ({id}))
    const [first, ...rest] = nodes
    const links = rest.map(n => link(first, n, 100))
    return {nodes, links}
}


const random = (nodeCount, linkCount) => {
    const nodes = range(0, nodeCount).map(id => ({id}))

    const rand = () => chance.integer({min: 0, max: nodes.length - 1})

    const links = range(0, linkCount)
        .map(() => link(nodes[rand()], nodes[rand()]))

    return {nodes, links}
}


const types = {
    grid: () => grid(8, 8),
    random: () => random(10, 10),
    radial: () => radial(30)
}

const ref = type => el => {
    if (!el)
        return

    const svg = d3.select(el)
    // Clear it
    svg.selectAll('*').remove()

    const graph = (types[type] || types.grid)()


    layout(svg, graph)
        .then(l => render(svg, l))

}

const Graph = ({type}) =>
    <div className={s.container}>
        <Menu>
            <NavMenuItem name="Grid" path="grid"/>
            <NavMenuItem name="Radial" path="radial"/>
            <NavMenuItem name="Random" path="random"/>
        </Menu>
        <svg width="960" height="600">
            <g ref={ref(type)} id="target" transform="translate(480, 300)">
            </g>
        </svg>
    </div>

export default Graph
