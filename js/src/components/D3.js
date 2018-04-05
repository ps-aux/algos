import React from 'react'
import 'd3-selection-multi'
import * as d3 from 'd3'
import {curry, curryN, prop, range} from 'ramda'
import './d3.sass'
import bst from 'src/algo/data-structures/bst'


const translate = ({x, y}) => `translate(${x}, ${y})`


const link = d3.linkVertical()
    .x(prop('x'))
    .y(prop('y'))


const calcLayout = tree => {
    const root = d3.hierarchy(tree)
    const treeLayout = d3.tree()
        .nodeSize([20, 50])
        .separation(() => 1)

    treeLayout(root)
    const nodes = root.descendants()
        .filter(n => n.data.value != null)


    const links = root.links()
        .filter(l => l.target.data.value != null)

    console.log('nodes', nodes)
    return {nodes, links}
}

const drawLayout = (el, {nodes, links}) => {
    const svg = d3.select(el)

    const nodeSel = svg.select('.nodes')
        .selectAll('.node')
        .data(nodes, prop('value'))

    nodeSel.select('circle')
        .classed('selected', d => d.data.marked)
        .transition()
        .style('fill', d => d.selected ? 'blue' : undefined)

    nodeSel.select('text')
        .text(prop('value'))

    const newNodes = nodeSel.enter()
        .append('g')
        .classed('node', true)
        .attrs(({
            transform: n => n.parent ? translate(n.parent) : ''
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
            transform: n => {
                return translate(n)
            }
        }))
        .on('end', () => {
            console.log('transition started')
        })

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

const drawTree = (el, tree, after) => {
    const l = calcLayout(tree)
    after && after(l.nodes)

    drawLayout(el, l)
    const redraw = change => {
        change && change(l.nodes)
        console.log('redrawing', l)
        drawLayout(el, l)
        return redraw
    }

    return redraw
}

const drawer = curryN(2, drawTree)

const removeSelection = (tree, draw) => {
    tree.visit(n => n.marked = false)
    draw(tree)
}


const btn = (name, action) =>
    <button onClick={action}>{name}</button>


const findDNode = (dNodes, n) =>
    dNodes.find(dn => dn.data === n)

const select = n => dNodes => {
    const dn = findDNode(dNodes, n)
    dn.selected = true
}

const moveDNode = (dn, dst) => {
    dn.x = dst.x
    dn.y = dst.y
    dn.move = true
}

const switchNodes = (n1, n2) => dNodes => {
    const dn1 = findDNode(dNodes, n1)
    const dn2 = findDNode(dNodes, n2)

    const dn1pos = {x: dn1.x, y: dn1.y}
    moveDNode(dn1, dn2)
    moveDNode(dn2, dn1pos)

}

const D3 = () => {
    let el
    let tree = bst()
    let draw

    const ref = _el => {
        el = _el
        draw = drawer(el)
        draw(tree)
    }


    return <div className="d3">
        <button onClick={() => {
            const num = Math.floor(Math.random() * 100)
            // const steps = []
            // const prevTree = tree
            // tree = bstAdd(tree, num, steps)
            tree = tree.add(num)
            console.log(tree)
            draw(tree)

            // const int = setInterval(() => {
            //     if (!steps.length) {
            //         clearInterval(int)
            //         removeSelection(el, prevTree)
            //         drawTree(el, tree)
            //         return
            //     }
            //
            //     const v = steps.shift().value
            //     select(v, el, prevTree)
            // }, 300)
        }}>go
        </button>

        {btn('selecr', () => {
            const vals = tree.reduce((acc, v) => (acc.push(v), acc), [])
            const idx = Math.floor(Math.random() * (vals.length - 1))
            removeSelection(tree, draw)
            const val = vals[0]
            // select(vals[idx], tree, draw)
            draw(tree, tree.find(val))
        })}
        {btn('visit', () => {
            const nodes = []
            tree.visit(n => nodes.push(n))


            const id = setInterval(() => {
                const n = nodes.shift()
                draw(tree, select(n))

                if (!nodes.length)
                    clearInterval(id)
            }, 500)
        })}

        {btn('move', () => {
            const nodes = []
            tree.visit(n => nodes.push(n))
            const redraw = draw(tree, switchNodes(nodes[3], nodes[4]))
            setTimeout(() => {
                redraw(dNodes => {
                    // dNodes[3].selected = true
                    switchNodes(nodes[0], nodes[3])(dNodes)
                })
            }, 500)
        })}

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