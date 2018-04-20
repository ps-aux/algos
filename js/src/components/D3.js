import React from 'react'
import 'd3-selection-multi'
import './d3.sass'
import bst from 'src/algo/data-structures/bst'
import treeDrawer from './treeDrawer'
import {clearSelection, render, select, switchNodes} from './treeActions'
import * as d3 from 'd3'
import {translate} from 'src/d3/utils'

const treeActions = {
    select,
    switch: switchNodes
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

const addNodeButtons = el => {

}


const D3 = () => {
    let el
    let tree = bst().add(51).add(50).add(52)
    let draw

    const ref = _el => {
        el = _el
        draw = treeDrawer(el)
        draw(render(tree))
    }


    const nodesBtns = el => {
        if (!el)
            return

        console.log('eeel', el)
        const data = [1, 2, 3]
        const r = 10
        const nodes = d3.select(el)
            .selectAll("circle")
            .data(data)
            .enter()
            .append('g')
            .classed('node', true)
            .classed('btn', true)
            .attrs((_, i) => ({
                transform: translate({x: i * r * 3 + 2 * r, y: r * 2})
            }))
            .on('click', d => {
                console.log('click', d)
            })

        nodes.append('circle')
            .attrs((_, i) => ({
                r
            }))

        nodes.append('text')
            .text(d => d)

    }

    return <div className="d3">
        <div className="control-panel">
            <div className="buttons">
                {btn('Add node', () => {
                    const steps = []
                    tree = tree.add(ranNum(), {steps, action: treeActions})
                    console.log('steps', steps)
                    const s = [...steps, clearSelection(null, {duration: 0})]
                    draw(s)
                    draw(render(tree))
                })}
                {btn('Select', () => {
                    draw([
                        select([0], {duration: 2000}),
                        select([0, 0], {duration: 2000}),
                        select([0, 1], {duration: 2000})
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

            </div>
            <div className="new-nodes">
                <svg ref={nodesBtns}>
                </svg>
            </div>
        </div>
        <svg ref={ref} width="1200" height="800">
            <g className="tree">
                <g className="links"/>
                <g className="nodes"/>
            </g>
        </svg>
    </div>
}

export default D3