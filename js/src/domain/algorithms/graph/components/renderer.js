import * as d3 from 'd3'
import {path, prop, range} from 'ramda'
import s from './Graph.sass'

const tickCount = 50


const layout = (graph, {height, width}) =>
    new Promise((resolve) => {
        const simulation = d3.forceSimulation()
            .alphaMin(1)
            .force('link', d3.forceLink()
                .id(d => d.id)
                .distance(prop('value')))
            .force('charge', d3.forceManyBody())
            .force('center', d3.forceCenter(width / 2, height / 2))


        simulation.nodes(graph.nodes)

        simulation.force('link').links(graph.links)

        simulation.on('end', resolve(graph))

        range(1, tickCount)
            .forEach(() => {
                simulation.tick()
            })
    })

const link = sel =>
    sel.append('line')
        .classed(s.link, true)
        .attrs({
            x1: path(['source', 'x']),
            y1: path(['source', 'y']),
            x2: path(['target', 'x']),
            y2: path(['target', 'y'])
        })
        .call(setSelected)

const node = (sel, {onNodeClick}) =>
    sel.append('circle')
        .classed(s.node, true)
        .attrs({
            r: 5,
            cx: prop('x'),
            cy: prop('y')
        })
        .call(setSelected)
        .call(setSelectable)
        .call(s => s.on('click', onNodeClick))


const setSelected = s =>
    s.classed('selected', prop('selected'))

const setSelectable = s =>
    s.classed('selectable', prop('selectable'))

const doRender = (el, layout, opts) => {
    const svg = d3.select(el)

    const {links, nodes} = layout

    svg.selectAll('line')
        .data(links)
        .enter()
        .call(link, opts)


    const old = svg.selectAll('circle')
        .data(nodes)

    old.enter().call(node, opts)

    old.call(setSelected)
        .call(setSelectable)

}


const render = (el, data, opts) => {
    const width = el.getAttribute('width')
    const height = el.getAttribute('height')

    // Data is actually modified...but we don't care for now
    layout(data, {width, height})
        .then(layout => doRender(el, layout, opts))
}


const renderer = (el, opts = {}) =>
    data => render(el, data, opts)

export default renderer