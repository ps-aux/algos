import React from 'react'
import bst from 'src/algo/data-structures/bst'
import {clearSelection, render, select, switchNodes} from 'src/components/tree/treeActions'
import * as d3 from 'd3'
import {prop, range} from 'ramda'
import Tree from 'src/components/tree/Tree'
import './bst.global.sass'
import Button from 'src/components/basic/Button'
import HorizView from 'src/components/basic/HorizView'

const treeActions = {
    select,
    switch: switchNodes
}

const btn = (name, action) =>
    <Button onClick={action} label={name}/>


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


const addNodeButtons = (el, onClick) => {

    const _data = range(1, 50)
        .map(v => ({value: v}))

    const remove = d => {
        d.removed = true
        update(_data)
        onClick(d)
    }

    const update = data => {
        const r = 10
        const size = r * 2 + 5
        const nodes = d3.select(el)
            .selectAll('.node')
            .data(data)

        const entered = nodes.enter()
            .append('svg')
            .attrs({
                height: size,
                lidth: size,
                viewBox: `0 0 ${size/1} ${size/1}`
            })
            .classed('node', true)
            .classed('btn', true)
            .attrs((_, i) => ({
                // transform: translate({x: r, y: r})
            }))
            .on('click', d => {
                console.log('click', d)
                remove(d)
            })

        entered.append('circle')
            .attrs((_, i) => ({
                r,
                cx: '50%',
                cy: '50%'
            }))


        entered.append('text')
            .style('text-anchor', 'middle')
            .attrs({
                x: '50%',
                y: '50%'
            })
            .text(prop('value'))

        nodes.exit().remove()

        nodes.filter(prop('removed'))
            .each(a => {
                console.log('removed', a)
            })
            .classed('removed', true)
    }

    update(_data)
}


const Bst = () => {
    let tree = bst()
    let draw

    const renderRef = _draw => {
        draw = _draw
        draw(render(tree))
    }


    const addNode = val => {
        const steps = []
        tree = tree.add(val, {steps, action: treeActions})
        console.log('steps', steps)
        const s = [...steps, clearSelection(null, {duration: 0})]
        // draw(s)
        draw(render(tree))
    }

    const nodesBtns = el => {
        if (!el)
            return
        addNodeButtons(el, ({value}) => addNode(value))
    }

    return <div className="bst">
        <div className="control-panel">
            <HorizView>
                {btn('Add node', () => {
                    addNode(ranNum())
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
            </HorizView>
            <div className="new-nodes tree" ref={nodesBtns} // Hack with .tree to style as in tree
                 style={{
                     flexDirection: 'row'
                 }}>
            </div>
        </div>
        <Tree renderRef={renderRef}/>
    </div>
}

export default Bst