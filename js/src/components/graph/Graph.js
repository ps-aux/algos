import React from 'react'
import s from './Graph.sass'
import * as d3 from 'd3'
import {path, prop, range} from 'ramda'
import {NavMenuItem} from 'src/components/NavMenu'
import {Menu} from 'semantic-ui-react'
import Chance from 'chance'
import graphRenderer from './renderer'
import Button from 'src/components/basic/Button'
import View from 'src/components/basic/View'

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

const calcGraph = type => {
    const graph = (types[type] || types.grid)()
    graph.nodes[0].selected = true
    graph.links[0].selected = true
    return graph
}

class Graph extends React.Component {

    onRef = el => {
        this.el = el
        if (!el)
            return

        this.setGraph(this.props.type, el)
    }

    setGraph = (type, el) => {
        const svg = d3.select(el)
        // Clear it
        svg.selectAll('*').remove()
        this.graph = calcGraph(type)

        const render = graphRenderer(el, {
            onNodeClick: n => {
                n.selected = !n.selected
                this.renderGraph()
            }
        })
        this.renderGraph = () => render(this.graph)
        this.renderGraph()
        this.type = type
    }

    componentDidUpdate() {
        const {type} = this.props
        if (type === this.type)
            return

        this.setGraph(type, this.el)
    }

    getSelection = () => {
        this.graph.nodes.forEach(n => n.selectable = true)
        this.renderGraph()
    }


    render() {
        return <View>
            <Menu>
                <NavMenuItem name="Grid" path="grid"/>
                <NavMenuItem name="Radial" path="radial"/>
                <NavMenuItem name="Random" path="random"/>
            </Menu>
            <Button label="Path" onClick={this.getSelection}/>
            <svg width="960" height="600">
                <g ref={this.onRef} id="target" transform="translate(480, 300)">
                </g>
            </svg>
        </View>

    }
}

export default Graph
