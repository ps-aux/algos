import React from 'react'
import 'd3-selection-multi'
import * as d3 from 'd3'
import {prop, range} from 'ramda'
import './d3.sass'


const size = n => !isNode(n) ?
    0 : n.children.reduce((a, c) => a + size(c), 0) + 1

const isNode = n => n && n.value != null

const visit = (n, onVisit) => {
    if (!isNode(n))
        return
    onVisit(n)
    n.children.forEach(c => visit(c, onVisit))
}

const find = (n, val) => {
    if (!isNode(n))
        return
    if (n.value === val)
        return n

    for (let c of n.children) {
        const found = find(c, val)
        if (found)
            return found
    }
}

const bstAdd = (n, value, steps = []) => {
    if (value === n.value)
        return n
    if (!isNode(n))
        return {value, children: [{}, {}]}

    steps.push(n)
    const cp = {
        value: n.value,
        children: n.children.slice()
    }

    const [left, right] = cp.children
    if (value < n.value) {
        cp.children[0] = bstAdd(left, value, steps)
    } else {
        cp.children[1] = bstAdd(right, value, steps)
    }

    return cp
}


const link = d3.linkVertical()
    .x(prop('x'))
    .y(prop('y'))

const d3Tree = (el, tree) => {
    const root = d3.hierarchy(tree)
    const treeLayout = d3.tree()
        .nodeSize([20, 50])
        .separation(() => 1)

    treeLayout(root)
    const nodes = root.descendants().filter(n => n.data.value != null)
    const links = root.links().filter(l => l.target.data.value != null)
    const svg = d3.select(el)


    const nodeSel = svg.select('.nodes')
        .selectAll('.node')
        .data(nodes, prop('value'))

    nodeSel.select('circle')
        .classed('selected', d => d.data.marked)
        .transition()
        .style('fill', d => d.data.marked ? 'blue' : undefined)

    nodeSel.select('text')
        .text(prop('value'))

    const newNodes = nodeSel.enter()
        .append('g')
        .classed('node', true)
        .attrs(({
            transform: n => n.parent ? `translate(${n.parent.x}, ${n.parent.y})` : ''
        }))

    newNodes
        .append('circle')
        .attrs({
            r: 10
        })
        .transition(50)
        .delay(300)
        .attrs({
            r: 12
        })
        .transition(50)
        .attrs({
            r: 10
        })

    newNodes
        .append('text')
        .text(prop('value'))

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

const randomArray = n => range(0, n).map(() => Math.random() * 40)

const select = (val, el, tree) => {
    // Reset
    removeSelection(el, tree)
    // Mark
    const f = find(tree, val)
    f.marked = true

    d3Tree(el, tree)
}

const removeSelection = (el, tree) => {
    visit(tree, n => n.marked = false)
    d3Tree(el, tree)
}


const D3 = () => {
    let el
    let tree = {}
    const ref = _el => {
        el = _el
        d3Tree(el, tree)
    }


    return <div className="d3">
        <button onClick={() => {
            const num = Math.floor(Math.random() * 100)
            const steps = []
            const prevTree = tree
            tree = bstAdd(tree, num, steps)

            const int = setInterval(() => {
                if (!steps.length) {
                    clearInterval(int)
                    removeSelection(el, prevTree)
                    d3Tree(el, tree)
                    return
                }

                const v = steps.shift().value
                select(v, el, prevTree)
            }, 300)
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