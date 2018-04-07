import React from 'react'
import 'd3-selection-multi'
import './d3.sass'
import bst from 'src/algo/data-structures/bst'
import treeDrawer from './treeDrawer'
import {clearSelection, render, select, switchNodes} from './treeActions'

const removeSelection = (tree, draw) => {
    tree.visit(n => n.marked = false)
    draw(tree)
}

const btn = (name, action) =>
    <button onClick={action}>{name}</button>


const ranNum = (max = 100) =>
    Math.floor(Math.random() * max)

const treeNodes = tree =>
    tree.reduce((a, n) => (a.push(n), a), [])

const ranNode = tree => treeNodes(tree)[ranNum(tree.size())]


const sndTree = {
    value: 5,
    path: [0],
    children: [
        {
            value: 8,
            path: [0, 0],
            children: [
                {},
                {
                    value: 123,
                    path: [0, 0, 0],
                    children: [{}, {}]
                }]
        },
        {
            value: 1,
            path: [0, 1],
            children: [{}, {}]
        }
    ]
}

const D3 = () => {
    let el
    let tree = bst().add(5).add(1).add(8)
    let draw

    const ref = _el => {
        el = _el
        draw = treeDrawer(el)
        draw(render(tree))
    }


    return <div className="d3">
        {btn('Add node', () => {
            tree = tree.add(ranNum())
            draw(render(tree))
        })}
        {btn('Select', () => {
            draw([
                clearSelection(),
                select([0])
            ])
        })}
        {btn('Switch 1 <-> 2', () => {
            draw([
                switchNodes({src: [0, 0], dst: [0, 1]}),
                render(sndTree)
            ])
        })}

        {btn('move',
            () => {
                const nodes = []
                tree.visit(n => nodes.push(n))
                const redraw = draw(tree, switchNodes(nodes[1], nodes[2]))
                setTimeout(() => {
                    redraw(dNodes => {
                        // dNodes[3].selected = true
                        // switchNodes(nodes[0], nodes[3])(dNodes)
                    })
                }, 1000)
            })}

        <svg ref={ref} width="1200" height="400">
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