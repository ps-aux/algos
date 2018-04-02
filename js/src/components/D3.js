import React, {createRef} from 'react'
import 'd3-selection-multi'
import * as d3 from 'd3'
import {ascend, compose, descend, map, path, prop, range, sort} from 'ramda'
import './d3.sass'

let num = 1

const dummyNode = {
    dummy: true
}

const tree = {
    name: 'root node',
    value: num,
    children: [dummyNode, dummyNode]
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

const visit = (n, onVisit) => {
    if (n.dummy)
        return
    onVisit(n)
    n.children.forEach(c => visit(c, onVisit))
}

const find = (n, val) => {
    if (n.dummy)
        return
    if (n.value === val)
        return n

    for (let c of n.children) {
        const found = find(c, val)
        if (found)
            return found
    }
}

const addNode = (n, max, createNode) => {
    if (n.children.length < max) {
        n.children.push(createNode())
    } else {
        addNode(smallestChild(n), max, createNode)
    }
}


const bstNode = val => ({
    name: 'node ' + val,
    value: val,
    children: [dummyNode, dummyNode]
})

const bstAdd = (n, val, path = []) => {
    path.push(n)

    if (n.value === val)
        return path

    const [left, right] = n.children
    if (val > n.value) {
        if (!right.value)
            n.children[1] = bstNode(val)
        else
            bstAdd(right, val, path)
    } else {
        if (!left.value)
            n.children[0] = bstNode(val)
        else
            bstAdd(left, val, path)
    }

    return path
}

const addNewNode = tree => {
    addNode(tree, 2, nextNode)
}

const maxChildren = 2

range(0, 0)
    .forEach(() => addNode(tree, maxChildren))

const link = d3.linkVertical()
    .x(prop('x'))
    .y(prop('y'))

const d3Tree = (el, tree) => {
    const root = d3.hierarchy(tree)
    const treeLayout = d3.tree()
        .nodeSize([20, 50])
        .separation(() => 1)

    treeLayout(root)
    const nodes = root.descendants().filter(n => !n.data.dummy)
    const links = root.links().filter(l => !l.target.data.dummy)
    const svg = d3.select(el)


    const nodeSel = svg.select('.nodes')
        .selectAll('.node')
        .data(nodes, prop('value'))

    nodeSel.select('circle')
        .classed('selected', d => d.data.marked)
        .transition()
        .style('fill', d => d.data.marked ? 'blue' : undefined)

    nodeSel.select('text')
        .text(d => d.data.dummy ? '' : d.value)
    // .style('fill', d => d.data.marked ? 'blue' : 'none')
    // .attrs({
    //     fill: d => d.data.marked ? 'blue' : 'none'
    // })

    const newNodes = nodeSel.enter()
        .append('g')
        .classed('node', true)
        .attrs(({
            transform: n => n.parent ? `translate(${n.parent.x}, ${n.parent.y})` : ''
        }))

    console.log(nodes)
    newNodes
        .append('circle')
        .attrs({
            r: 10
        })

    newNodes
        .append('text')
        .text(prop('value'))
        .attrs({
            // y: 0,
            // x: 0
        })

    newNodes
        .merge(nodeSel)
        .transition()
        .duration(300)
        .attrs(({
            transform: n => `translate(${n.x}, ${n.y})`
        }))

// Links
    const linkSel = svg.select('.links')
        .selectAll('.link')
        .data(links, d => d.source.value + '-' + d.target.value)

    linkSel.enter()
        .append('path')
        .classed('link', true)
        .attr('d', d => link({source: d.source, target: d.source}))
        .merge(linkSel)
        .transition()
        .duration(300)
        .attr('d', link)
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

let marked = 1


const select = (val, el) => {
    // Reset
    visit(tree, n => n.marked = false)
    // Mark
    const f = find(tree, val)
    f.marked = true

    d3Tree(el, tree)
}

const D3 = () => {
    let el
    const ref = _el => {
        el = _el
        d3Tree(el, tree)
    }

    return <div className="d3">
        <button onClick={() => {
            // addNewNode(tree)
            const num = Math.floor(Math.random() * 100)
            const path = bstAdd(tree, num)
            d3Tree(el, tree)

            return
            const int = setTimeout(() => {
                if (!path.length)
                    clearInterval(int)
                const v = path.shift().value
                select(v, el)
            }, 1000)
        }}>go
        </button>
        <button onClick={() => {
            const vals = []
            visit(tree, n => vals.push(n.value))
            const v = vals[Math.floor(Math.random() * (vals.length - 1))]
        }}>mark 4
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