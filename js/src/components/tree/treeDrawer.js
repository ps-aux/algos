import {complement, pick, prop, propEq, tail} from 'ramda'
import * as d3 from 'd3'
import {CLEAR_SELECTION, RENDER, SELECT, SWITCH} from './treeActions'
import {translate} from 'src/d3/utils'
import s from './Tree.global.sass'


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
            transform: n => translate(n.move)
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


const moveLNode = (n, dst) =>
    n.move = pick(['x', 'y', 'value'], dst)

const node = ({nodes}, path) =>
    path.reduce((a, v) => a.children[v],
        {children: [nodes[0]]}) // nodes[0] is the root node


const selectCircle = c => c.style('fill', 'black')

const deselectCirle = c => c.style('fill', 'white')

const applyOrElse = (p, t, f) => s => {
    s.filter(p)
        .call(t)
    s.filter(complement(p))
        .call(f)
}

const applySelected = applyOrElse(prop('selected'), selectCircle, deselectCirle)

// Can be generalized already
const selectAnim = (nodeSel, onEnd, {duration}) => {
    const circles = nodeSel.select('circle')

    let left = 0
    circles
        .transition()
        .duration(duration)
        .call(applySelected)
        .each(() => left++)
        .on('end', () => {
            if (--left === 0)
                onEnd()
        })
}


const drawLinks = (root, links) => {

    const linkSel = root.select('.links')
        .selectAll('.link')
        .data(links, d => nodeId(d.source) + '-' + nodeId(d.target))


    linkSel.enter()
        .append('path')
        .classed('link', true)
        .attr('d', d => link({source: d.source, target: d.source}))
        .merge(linkSel)
        .transition()
        .duration(150)
        .attr('d', link)

}


const createNewNodes = entered => {
    const newNodes = entered
        .append('g')
        .classed('node', true)

    newNodes
        .append('circle')
        .attrs({
            r: 10
        })

    newNodes
        .append('text')
        .text(prop('value'))

    return newNodes
}


const animNew = entered => {
    const circles = entered.select('circle')
    circles
        .attrs({
            r: 10
        })

    /*        .transition(30)
            .ease(d3.easeBounce)
            .delay(300)
            .attrs({
                r: 11
            })
            .transition(30)
            .attrs({
                r: 10
            })*/

    circles
        .call(selectCircle)
        .transition(300)
        .call(deselectCirle)
}


const updateAll = all => {
    all.attrs(({
        transform: n => {
            return translate(n)
        }
    }))
}

const animAll = all => {
    return
    all.transition()
        .duration(300)
        .ease(d3.easeBounce)

}

const drawNodes = (root, nodes, cb) => {
    const nodeSel = root.select('.nodes')
        .selectAll('.node')
        .data(nodes, prop('value'))

    const newNodes = createNewNodes(nodeSel.enter())
    animNew(newNodes)
    const all = newNodes.merge(nodeSel)
    updateAll(all)
    animAll(all)
    cb && cb(nodeSel)
}

const drawLayout = (el, {nodes, links}, anim) => {
    console.log('drawing', {nodes, links})
    const svg = d3.select(el)

    svg.select(".tree")
        .attrs({
            transform: translate({x: 400, y: 20})
        })

    drawNodes(svg, nodes, anim)
    drawLinks(svg, links)

}


let layout


const handlers = {
    [SELECT]: ({data: path, layout, draw, done, duration}) => {
        layout.nodes.forEach(n => n.selected = false)
        const ln = node(layout, path)
        ln.selected = true

        draw(nodeSel => selectAnim(nodeSel, () => {
            console.log('selected', path)
            done()
        }, {duration}))
    },
    [CLEAR_SELECTION]: ({layout, draw, done, duration}) => {
        layout.nodes.forEach(n => n.selected = false)
        draw(nodeSel => selectAnim(nodeSel, () => {
            console.log('cleared')
            done()
        }, {duration}))
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
    const {type, data, duration = 200} = action

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
        data,
        duration
    })
}


let busy = false
const queue = []
const onDone = () => {
    console.debug('Action done', queue)
    busy = false
    const next = queue.shift()
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
