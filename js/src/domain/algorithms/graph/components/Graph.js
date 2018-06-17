import React from 'react'
import stl from './Graph.sass'
import * as d3 from 'd3'
import {prop, range} from 'ramda'
import {NavMenuItem} from 'src/components/NavMenu'
import {Menu} from 'semantic-ui-react'
import Chance from 'chance'
import Button from 'src/components/basic/Button'
import View from 'src/components/basic/View'
import c from 'classnames'
import bfs from 'src/domain/algorithms/graph/impl/bfs'
import dfs from 'src/domain/algorithms/graph/impl/dfs'
import Collection from 'src/components/basic/Collection'

const chance = new Chance()


const link = (n1, n2, {value = 20, id} = {}) => ({
    source: n1.id,
    target: n2.id,
    id: id || (n1.id + '#' + n2.id),
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
    const links = rest.map(n => link(first, n, {value: 100}))
    return {nodes, links}
}


const random = (nodeCount, linkCount) => {
    const nodes = range(0, nodeCount).map(id => ({id}))

    const rand = () => chance.integer({min: 0, max: nodes.length - 1})

    let c = 0
    const links = range(0, linkCount)
        .map(() => link(nodes[rand()], nodes[rand()], {id: c++}))

    return {nodes, links}
}


const types = {
    grid: () => grid(8, 8),
    random: () => random(10, 10),
    radial: () => radial(30)
}

const calcGraph = type => {
    const {nodes, links} = types[type]()


    // Add indexes
    return {
        nodes: nodes.map((n, index) =>
            ({...n, index})),

        links: links.map((n, index) =>
            ({...n, index}))
    }
}

const layout = (graph, {height, width}) => {
    const simulation = d3.forceSimulation()
        .alphaMin(1)
        .force('link', d3.forceLink()
            .id(d => d.id)
            .distance(prop('value')))
        .force('charge', d3.forceManyBody())
        .force('center', d3.forceCenter(width / 2, height / 2))


    simulation.nodes(graph.nodes)

    simulation.force('link').links(graph.links)

    range(1, 100)
        .forEach(() => {
            simulation.tick()
        })

    return graph
}


const createClass = (...stls) => {
    const itemStls = stls.reduce((a, s) => (a[s] = true, a), {})
    return ({selectable, selected}) =>
        c(({
            selectable,
            selected,
            ...itemStls
        }))
}


const nodeClass = createClass(stl.node)

const linkClass = createClass(stl.link)

const Node = ({x, y, r = 5, onClick, ...rest}) =>
    <circle cx={x} cy={y} r={r}
            className={nodeClass(rest)}
            onClick={onClick}/>

const Link = ({source: s, target: t, onClick, ...rest}) =>
    <line x1={s.x} y1={s.y}
          onClick={onClick}
          className={linkClass(rest)}
          x2={t.x} y2={t.y}/>




class Graph extends React.Component {

    state = {
        selecting: false,
        selection: null
    }

    static getDerivedStateFromProps(p, s) {
        const {type = 'grid'} = p
        if (type === s.type)
            return null

        const graph = layout(calcGraph(type), {height: 600, width: 960})
        return {
            type,
            nodes: graph.nodes,
            links: graph.links
        }

    }


    startSelecting = () => {
        const {nodes, links} = this.state

        // Clear previous selection
        nodes.forEach(n => n.selected = false)
        links.forEach(n => n.selected = false)
        this.setState({
            nodes,
            links,
            selecting: true,
            selection: {
                from: null,
                to: null
            }
        })
    }

    onNodeSelect = n => {
        this.markSelected(n)
        const {selection: sel} = this.state


        if (!sel.from)
            sel.from = n
        else if (!sel.to)
            sel.to = n

        if (sel.to && sel.from) {
            const from = sel.from.id
            const to = sel.to.id
            const {links, nodes} = this.state
            const p = bfs({
                nodes,
                links: links.map(l => ({
                    source: l.source.id,
                    target: l.target.id
                }))
            }, from, to)


            p.forEach(n => n.selected = true)

            let n = p.pop()
            while (p.length > 1) {
                let prev = p.pop()

                console.log('to', n, 'from', prev)
                const l = links.find(l =>
                    l.source === prev && l.target === n ||
                    l.target === prev && l.source === n
                )
                if (!l) {
                    console.log(links)
                    console.log(links.filter(l => l.source === prev))
                }
                l.selected = true

                n = prev
            }


            this.setState({
                nodes: this.state.nodes,
                selection: null,
                selecting: false
            })
        }

        this.setState({
            nodes: this.state.nodes,
            selection: sel
        })

    }

    markSelected = (...ns) => {
        const {nodes} = this.state
        ns.forEach(n =>
            nodes[n.index].selected = !n.selected)

    }

    render() {
        const {nodes, links, selecting, selection} = this.state
        return <View>
            <Menu>
                <NavMenuItem name="Grid" path="grid"/>
                <NavMenuItem name="Radial" path="radial"/>
                <NavMenuItem name="Random" path="random"/>
            </Menu>
            <Button label="Path" onClick={this.startSelecting}/>
            <svg width="960" height="600">
                <Collection comp={Link}
                            items={links}/>
                <Collection comp={Node} items={nodes}
                            props={{
                                onClick: selecting && this.onNodeSelect,
                                selectable: selecting
                            }}
                />
            </svg>
            {selecting && <View>
                Pick a {selection.from ? 'target' : 'source'} node
            </View>}
        </View>

    }
}

export default Graph
