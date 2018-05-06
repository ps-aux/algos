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
        .attrs({
            x1: path(['source', 'x']),
            y1: path(['source', 'y']),
            x2: path(['target', 'x']),
            y2: path(['target', 'y'])
        })
        .call(setSelected)

const node = sel =>
    sel.append('circle')
        .classed(s.node, true)
        .attrs({
            r: 5,
            cx: prop('x'),
            cy: prop('y')
        })
        .call(setSelected)
        .call(setSelectable)
        .call(s => s.on('click',
            function (d) {
                if (d.selectable) {
                    console.log('is selectable')
                    d.selected = !d.selected
                    setSelected(d3.select(this))
                }
            }
        ))


const setSelected = s =>
    s.classed('selected', prop('selected'))

const setSelectable = s =>
    s.classed('selectable', prop('selectable'))

const doRender = (el, layout) => {
    const svg = d3.select(el)

    const {links, nodes} = layout
    svg.append('g')
        .attr('class', 'links')
        .selectAll('line')
        .data(links)
        .enter()
        .call(link)

    svg.append('g')
        .attr('class', 'nodes')
        .classed('nodes', true)
        .selectAll('circle')
        .data(nodes)
        .enter()
        .call(node)
}


const selectable = true
const render = (el, data) => {
    const width = el.getAttribute('width')
    const height = el.getAttribute('height')

    data.nodes.forEach(n => n.selectable = selectable)
    // Data is actually modified...but we don't care for now
    layout(data, {width, height})
        .then(layout => doRender(el, layout))
}


const renderer = el =>
    (...args) => render(el, ...args)

export default renderer