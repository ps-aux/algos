import React, {createRef} from 'react'
import 'd3-selection-multi'
import * as d3 from 'd3'
import {ascend, compose, descend, map, path, prop, range, sort} from 'ramda'
import './d3.sass'

let num = 1
const tree = {
    name: 'root node',
    value: num,
    children: []
}

const size = n =>
    n.children
        .reduce((a, c) => a + size(c), 0) + 1

const smallestChild = n => {
    const sortBySize = compose(
        map(prop('child')),
        sort(ascend(prop('size'))),
        map(c => ({child: c, size: size(c)}))
    )

    return sortBySize(n.children)[0]
}

const nextNode = () => {
    num++
    return {
        name: 'node ' + num,
        value: num,
        children: []
    }
}

const addNode = (n, max) => {
    if (n.children.length < max) {
        n.children.push(nextNode())
    } else {
        addNode(smallestChild(n), max)
    }
}

const maxChildren = 2

range(0, 0)
    .forEach(() => addNode(tree, maxChildren))

const link = d3.linkVertical()
    .x(prop('x'))
    .y(prop('y'))

const d3Tree = (el, tree) => {
    console.log(tree)
    const root = d3.hierarchy(tree)
    const treeLayout = d3.tree()
        .nodeSize([20, 40])
        .separation(() => 1)

    treeLayout(root)
    const nodes = root.descendants()
    const links = root.links()
    const svg = d3.select(el)

    // svg.select('.nodes').selectAll().remove()

    console.log(nodes)
    const attrs = {
        cx: prop('x'),
        cy: prop('y'),
        fill: d3.interpolateRdYlGn(num / 20),
        r: 10
    }

    console.log(num)
    const nodeSel = svg.select('.nodes')
        .selectAll('.node')
        .data(nodes, prop('value'))

    nodeSel.enter()
        .append('circle')
        .classed('node', true)

    nodeSel
        .transition()
        .attrs(attrs)

// Links
    const linkSel = svg.select('.links')
        .selectAll('.link')
        .data(links)

    linkSel.enter()
        .append('path')
        .classed('link', true)

    linkSel
        .transition()
        .attr('d', link)
    // .attrs({
    //     x1: path(['source', 'x']),
    //     y1: path(['source', 'y']),
    //     x2: path(['target', 'x']),
    //     y2: path(['target', 'y'])
    // })
}

const d3Go = (el, data) => {
    const c = d3.select(el)
        .selectAll('circle')
        .data(data)

    c.enter()
        .append('circle')
        .attrs((d, i) => ({
            cx: 100 + i * 100,
            // cy: 100,
            r: d
            // fill: 'green'
        }))

    c.transition().attrs((d, i) => ({
        cx: 100 + i * 100,
        cy: 200,
        r: d,
        fill: 'purple'
    }))


    // c.attrs((d, i) => ({
    //     cx: 100 + i * 100,
    //     cy: 200,
    //     r: d,
    //     fill: 'purple'
    // }))

    // p.enter().append('p')
    // .text(function (d) {
    //     return "I’m number " + d + "!"
    // })
}

const randomArray = n => range(0, n).map(() => Math.random() * 40)

const D3 = () => {
    let el
    const ref = _el => {
        el = _el
        d3Tree(el, tree)
    }

    return <div className="d3">
        <button onClick={() => {
            addNode(tree, maxChildren)
            d3Tree(el, tree)
        }}>go
        </button>
        <svg ref={ref} width="1200" height="600">
            <g className="tree">
                <g className="links"/>
                <g className="nodes"/>
            </g>
        </svg>
        {/*<svg width="50" height="50">*/}
        {/*<circle cx="25" cy="25" r="25" fill="purple"/>*/}
        {/*</svg>*/}
    </div>
}

export default D3