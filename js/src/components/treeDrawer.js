import {pick, prop, propEq, tail} from 'ramda'
import * as d3 from 'd3'
import {CLEAR_SELECTION, RENDER, SELECT, SWITCH} from './treeActions'

const translate = ({x, y}) => `translate(${x}, ${y})`

const link = d3.linkVertical()
    .x(prop('x'))
    .y(prop('y'))

const switchAnimation = (nodesSel, onEnd) => {
    console.log('animating move')
    const moved = nodesSel
        .filter(d => d.move)

    moved.transition()
        .duration(500)
        // .ease(d3.easeElastic)
        .attrs(({
            transform: n => {
                return translate(n.move)
            }
        }))
        .on('end', function (n) {
            onEnd(n, this)
        })
}


const nodeId = n => n.id


const calcLayout = tree => {
    const root = d3.hierarchy(tree)
    const treeLayout = d3.tree()
        .nodeSize([20, 50])
        .separation(() => 1)

    treeLayout(root)
    const nodes = root.descendants()
        .filter(n => n.data.value != null)
        .map(n => (n.id = n.data.path.join(','), n))


    const links = root.links()
        .filter(l => l.target.data.value != null)

    console.debug('nodes', nodes)
    return {nodes, links}
}


const moveLNode = (n, dst) => {
    // dn.x = dst.x
    // dn.y = dst.y
    // dn.move = true
    n.move = pick(['x', 'y', 'value'], dst)
}

const node = ({nodes}, path) =>
    path.reduce((a, v) => a.children[v],
        {children: [nodes[0]]}) // nodes[0] is the root node

const drawLayout = (el, {nodes, links}, anim) => {
    console.log('drawing', {nodes, links})
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
        .transition(30)
        .ease(d3.easeBounce)
        .delay(300)
        .attrs({
            r: 11
        })
        .transition(30)
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
        .ease(d3.easeBounce)
        .attrs(({
            transform: n => {
                return translate(n)
            }
        }))

    anim && anim(nodeSel)

    // Debug purposes only
    nodeSel
        .exit().remove()

    // Links
    const linkSel = svg.select('.links')
        .selectAll('.link')
        .data(links, d => {
            const r = nodeId(d.source) + '-' + nodeId(d.target)
            return r
        })

    // .data(links, d => d.source.value + '-' + d.target.value)
    // .data(links, d => d.source.x + '-' + d.source.y + '-' + d.target.x + '-' + d.target.y)

    // Debug purposes only
    linkSel
        .exit().remove()

    linkSel.enter()
        .append('path')
        .classed('link', true)
        .attr('d', d => link({source: d.source, target: d.source}))
        .merge(linkSel)
        .transition()
        .duration(300)
        .attr('d', link)
}


let layout


const handlers = {
    [SELECT]: ({data: path, layout, draw, done}) => {
        const ln = node(layout, path)
        ln.selected = true

        draw()
        done()
    },
    [CLEAR_SELECTION]: ({layout, draw, done}) => {
        layout.nodes.forEach(n => n.selected = false)

        draw()
        done()
    },
    [SWITCH]: ({data, layout, draw, done}) => {
        const src = node(layout, data.src)
        const dst = node(layout, data.dst)

        moveLNode(src, dst)
        moveLNode(dst, src)

        let latch = 2

        const anim = nodeSel =>
            switchAnimation(nodeSel,
                (n, el) => {
                    console.log('anim ended', n, el)
                    el.setAttribute('transform', translate(n))
                    n.value = n.move.value
                    delete n.move

                    if (--latch === 0) {
                        draw()
                        done()
                    }
                })

        draw(anim)
    }
}


const drawTree = (el, action, done) => {
    console.debug('action', action)
    const {type, data} = action

    if (type === RENDER) {
        layout = calcLayout(data)
        drawLayout(el, layout)
        done()
        return
    }

    handlers[type]({
        draw: anim => drawLayout(el, layout, anim),
        done,
        layout,
        data
    })
}


let busy = false
const queue = []
const onDone = () => {
    console.debug('Action done')
    busy = false
    const next = queue.pop()
    next && next()
}

const execAction = (el, action) => {

    const job = () => {
        drawTree(el, action, onDone)
    }

    if (!busy) {
        busy = true
        job()
    }
    else
        queue.push(job)
}

const create = el => arg => {
    if (typeof arg[Symbol.iterator] === 'function')
        arg.forEach(a => execAction(el, a))
    else
        execAction(el, arg)
}


export default create
